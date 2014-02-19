package jeuserieux.presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;
import jeuserieux.metier.DeplacementBateaux;
import jeuserieux.metier.MetierMap;
import jeuserieux.modele.Forme;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Quai;
import jeuserieux.modele.Terminal;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.outils.PointPathFinding;

public class PanelMap extends JPanel{
    private static final double ZOOMMIN = 1.0;
    private static final double ZOOMMAX = 10.0;
    private static final double PAS_ZOOM = 0.5;
    private static final double COEF_DEPLACEMENT = 0.0001;
    
    private final Color COULEUR_QUAI_DISPONIBLE = new Color(255, 0, 0);
    
    /**************** MAP *******************/
    private Point2D _coordCurseurModif;
    private double _pourcentGauche=0.5;
    private double _pourcentHaut=0.5;
    
    //bornes limites de visibilité des coordonnées géographiques
    private final double MAX_DEPART_X=0.2050875;
    private final double MAX_DEPART_Y=49.4929;
    private final double MIN_DEPART_X=0.09;
    private final double MIN_DEPART_Y=49.448;
    
    //coefs coordonnée géographique -> coordonnée fenêtre
    private double _coefX;
    private double _coefY;
    
    //zoom
    private double _zoomEtat = ZOOMMIN;
    
    //limites affichage map
    private double _mapHaut=MIN_DEPART_Y;
    private double _mapBas=MAX_DEPART_Y;
    private double _mapGauche=MIN_DEPART_X;
    private double _mapDroite=MAX_DEPART_X;
    
    private final MetierMap _metier;
    private TypeMarchandise _typeMarchandiseNavire=null;
    private Point2D _pointClick;
    private final PanelInfoForme _panelInfoForme;
    private ArrayList<Navire> _navires = new ArrayList<>();
    private String _infosMouseOver="";
    private String _coordonneesMouseOver="";
    private Navire _navireSelectionne=null;
    private final PanelNavires _naviresArrives;
    ArrayList<PointPathFinding> resultat = new ArrayList<>();
    
    public PanelMap(PanelInfoForme panelInfo, PanelNavires naviresArrives) {
        _metier = new MetierMap(this, panelInfo);
        _metier.construireFormes();
        _panelInfoForme = panelInfo;
        _naviresArrives = naviresArrives;
    }
    
    /**
     * Lie les quais aux terminaux
     */
    public void lierMapRealite() {
        _metier.lierQuaisTerminaux();
    }
    
    /**
     * Initialise les évènements de la carte
     */
    public void initEvents() {
        move();
        wheel();
        click();
    }
    
    /**
     * Recharge l'affichage de la carte
     */
    public void refresh() {
        revalidate();
        repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        _mapBas = MIN_DEPART_Y;
        _mapHaut = MAX_DEPART_Y;
        _mapDroite = MAX_DEPART_X;
        _mapGauche = MIN_DEPART_X;
        
        Graphics2D g2 = (Graphics2D)g;
        
        //récupère la taille réelle de la fenêtre
        Insets insets = getInsets();
        double w = getWidth() - insets.left - insets.right;
        double h = getHeight() - insets.top - insets.bottom;
        
        //détermine les coordonnées à gérer
        double diffY = MAX_DEPART_Y - MIN_DEPART_Y;
        double diffX = MAX_DEPART_X - MIN_DEPART_X;
        
        
        double hauteur = diffY / _zoomEtat;
        double largeur = diffX / _zoomEtat;
        
        if(_coordCurseurModif==null) {
            _coordCurseurModif = new Point2D.Double(largeur/2.0, hauteur/2.0);
        }
        
        double ajoutDroite, ajoutBas;
        ajoutDroite = ajoutBas = 0.0;
        double calculGauche = _coordCurseurModif.getX()-largeur*_pourcentGauche;
        if(calculGauche>=MIN_DEPART_X) {
            _mapGauche = calculGauche;
        } else {
            ajoutDroite = MIN_DEPART_X - calculGauche;
            _mapGauche = MIN_DEPART_X;
        }
        
        double calculDroite = _coordCurseurModif.getX()+largeur*(1-_pourcentGauche);
        if(calculDroite<=MAX_DEPART_X) {
            _mapDroite = calculDroite;
            _mapDroite += ajoutDroite;
        } else {
            double diff = calculDroite-MAX_DEPART_X; //négatif
            _mapDroite = MAX_DEPART_X;
            _mapGauche -= diff;
        }
        
        double calculHaut = _coordCurseurModif.getY()-hauteur*(1-_pourcentHaut);
        if(calculHaut>=MIN_DEPART_Y) {
            _mapHaut = calculHaut;
        } else {
            ajoutBas = MIN_DEPART_Y-calculHaut;
            _mapHaut = MIN_DEPART_Y;
        }
        
        double calculBas = _coordCurseurModif.getY()+hauteur*_pourcentHaut;
        if(calculBas<=MAX_DEPART_Y) {
            _mapBas = calculBas;
            _mapBas += ajoutBas;
        } else {
            double diff = MAX_DEPART_Y-calculBas; //négatif
            _mapBas = MAX_DEPART_Y;
            _mapHaut += diff;
        }
        
        //recalcul diff
        diffY = _mapBas - _mapHaut;
        diffX = _mapDroite - _mapGauche;
        _coefX = w/diffX;
        _coefY = h/diffY;
        
        HashMap<String,Double> m = new HashMap<>();
        m.put("coefX", _coefX);
        m.put("coefY", _coefY);
        m.put("hauteurPanel", h);
        m.put("bas", _mapBas);
        m.put("haut", _mapHaut);
        m.put("gauche", _mapGauche);
        m.put("droite", _mapDroite);
        //trie les formes puis les paint
        mergeNavires();
        for(Forme forme:_metier.getCoordonneesDessin()) {
            Color couleur = forme.getCouleur();
            Path2D path = forme.getPath(m);
            
            if(forme instanceof Navire) {
                couleur = Color.red;
            }
            g2.setColor(couleur);
            if(path!=null) {
                boolean fill = forme.isFill();
                if(_typeMarchandiseNavire != null && forme instanceof Terminal) {
                    Terminal t = (Terminal) forme;
                    if(t.prendEnCharge(_typeMarchandiseNavire)) {
                        g2.setColor(COULEUR_QUAI_DISPONIBLE);
                    }
                }
                if(fill) {
                    g2.fill(path);
                } else {
                    g2.draw(path);
                }
            } 
        }
        
        g2.setColor(Color.RED);
        
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString(_infosMouseOver, 50, (int)(h-30));
        g2.drawString(_coordonneesMouseOver, (int)(w-300), (int)(h-10));
        
        int cpt = 0;
        if(resultat!=null) {
            for(PointPathFinding p:resultat) {
                g2.setFont(new Font("SansSerif", Font.BOLD, (int)(5*_zoomEtat)));
                g2.drawString(cpt+"", (float)((p.getX()-_mapGauche)*_coefX), (float)(h-((p.getY()-_mapHaut)*_coefY)));
                cpt++;
            }
        }
    }
    /**
     * fusionne les _navire et les navires connus de _naviresArrives
     * @see presentation.PanelNavires
     */
    private void mergeNavires() {
        for(Navire n:_naviresArrives.getNavires()) {
            if(!_navires.contains(n)) {
                if(n.getPosition()==null) {
                    n.setPosition(new Point2D.Double(-1, -1));
                    n.constructionNavire();
                }
                _navires.add(n);
                _metier.ajoutForme(n);
            }
        }
    }
    
    /**
     * Ajoute un nouveau navire sur la carte
     * @param navire navire à ajouter
     */
    public void ajoutNavire(Navire navire) {
        _navires.add(navire);
        _metier.ajoutForme(navire);
    }
    
    /**
     * Augmenter le zoom
     */
    private void augmenterZoom() {
        if(_zoomEtat<ZOOMMAX) {
            _zoomEtat += PAS_ZOOM;
            refresh();
        }
    }
    
    /**
     * Baisse le zoom
     */
    private void baisserZoom() {
        if(_zoomEtat>ZOOMMIN) {
            _zoomEtat -= PAS_ZOOM;
            refresh();
        }
    }
    
    /**
     * calcule le déplacement de la zone visible sur la carte
     * @param directionX déplacement sur l'axe x
     * @param directionY déplacement sur l'axe y
     */
    private void dragCurseur(double directionX, double directionY) {
        _coordCurseurModif.setLocation(_coordCurseurModif.getX()+(COEF_DEPLACEMENT*directionX/_zoomEtat), _coordCurseurModif.getY()+(COEF_DEPLACEMENT*directionY/_zoomEtat));
        refresh();
    }
    
    /**
     * Met à jour les variables nécessaires au calcul de la zone visible sur la carte
     * le curseur doit correspondre au zoom déjà fait et pas situation de départ
     * il faut jouer avec les coef pour faire la correspondance
     * @param p le point du curseur
     */
    private void setCurseur(Point p) {
        Insets insets = getInsets();
        double w = getWidth() - insets.left - insets.right;
        double h = getHeight() - insets.top - insets.bottom;
        _pourcentGauche = p.getX()/w;
        _pourcentHaut = p.getY()/h;
        _coordCurseurModif.setLocation(p.getX()/_coefX+_mapGauche, (h-p.getY())/_coefY+_mapHaut);
    }
    
    private void setTypeColorer(TypeMarchandise type) {
        _typeMarchandiseNavire = type;
    }
    
    /**
     * Gestion de l'évènement lié à la molette de la souris
     * -> zoom/dézoom
     */
    private void wheel() {
        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                //le curseur doit correspondre au zoom déjà fait et pas situation de départ
                //il faut jouer avec les coef pour faire la correspondance
                setCurseur(e.getPoint());
                if(e.getWheelRotation()<0) {
                    //vers le haut -> zoom
                    augmenterZoom();
                    
                } else {
                    //dezoom
                    baisserZoom();
                }
            }
        });
    }
    
    /**
     * Gère les évènements liés aux mouvements de la souris
     * -> navigation sur la carte
     * 
     */
    private void move() {
        this.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                
                dragCurseur(_pointClick.getX()-e.getPoint().getX(), e.getPoint().getY()-_pointClick.getY());
                _pointClick = e.getPoint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Insets insets = getInsets();
                double h = getHeight() - insets.top - insets.bottom;
                _pointClick = e.getPoint();
                _coordonneesMouseOver = (_pointClick.getX()/_coefX+_mapGauche) + ", " + ((h-_pointClick.getY())/_coefY+_mapHaut);
                Forme forme = _metier.getForme(_pointClick);
                
                if(forme != null) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    _infosMouseOver = forme.toString();
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    _infosMouseOver = "";
                }
                refresh();
            }
        });
    }
    
    private PanelMap getThis() {
        return this;
    }
    
    /**
     * Gère les évènements liés au clic de la souris
     * -> sélection des éléments de la carte
     */
    private void click() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Insets insets = getInsets();
                double h = getHeight() - insets.top - insets.bottom;
                Forme forme = _metier.getForme(e.getPoint());
                System.out.println(forme);
                if(forme!=null) {
                    if(_navireSelectionne!=null) {
                        DeplacementBateaux t = new DeplacementBateaux(_navireSelectionne, new Point2D.Double(_pointClick.getX()/_coefX+_mapGauche, (h-_pointClick.getY())/_coefY+_mapHaut), _metier.getCoordonneesDessin(), getThis());
                        t.start();
                    }
                    if(forme instanceof Navire) {
                        Navire n = (Navire) forme;
                        setNavireSelectionne(n);
                    } else  {
                        setTypeColorer(null);
                        if(forme instanceof Quai) {
                            
                            Quai q = (Quai) forme;
                            _panelInfoForme.setInformations(q.getDonneesFormates());
                            _panelInfoForme.setNomPanel("Quai");
                            _panelInfoForme.majInformations();
                        } else if(forme instanceof Terminal) {
                            
                            Terminal t = (Terminal) forme;
                            _panelInfoForme.setInformations(t.getDonneesFormates());
                            _panelInfoForme.setNomPanel("Terminal");
                            _panelInfoForme.majInformations();
                            if(_navireSelectionne!=null) {
                                if(t.prendEnCharge(_navireSelectionne.getTypeMachandise())) {
                                    //DeplacementBateaux.deplacer(_navireSelectionne, new Point2D.Double(0.109, 49.458), _metier.getCoordonneesDessin(), _coefX, _coefY);
                                }
                            }
                        }
                    }
                    
                    refresh();
                }
            }
        });
    }
    
    /**
     * Sélectionne un navire et met à jour l'affichage des informations pour correspondre à ce navire
     * @param n le navire sélectionné
     */
    public void setNavireSelectionne(Navire n) {
        _navireSelectionne = n;
        _panelInfoForme.setInformations(n.getDonneesFormates());
        _panelInfoForme.setNomPanel("Navire");
        _panelInfoForme.majInformations();
        setTypeColorer(n.getTypeMachandise());
    }
    
    /**
     * Remet la carte à zéro
     */
    public void remiseAZero() {
        // virer les navires sur la map
        _navires = new ArrayList<>();
        _navireSelectionne = null;
        _typeMarchandiseNavire = null;
        _metier.enleverNavires();
        refresh();
    }
}
