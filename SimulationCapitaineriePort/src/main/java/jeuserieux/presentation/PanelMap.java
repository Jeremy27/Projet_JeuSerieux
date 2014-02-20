package jeuserieux.presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
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

public class PanelMap extends JPanel{
    public static final double COEF_GEO_METRE = 84208.831372054;
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
//    DeplacementBateaux resultat;
    
    ArrayList<Point2D> _clics;
    
    public PanelMap(PanelInfoForme panelInfo, PanelNavires naviresArrives) {
        _metier = new MetierMap(this, panelInfo);
        _metier.construireFormes();
        _panelInfoForme = panelInfo;
        _naviresArrives = naviresArrives;
        _clics = new ArrayList<>();
    }
    
    /**
     * Lie les quais aux terminaux
     */
    public void lierMapRealite() {
        _metier.lierQuaisTerminaux();
    }
    
    public void afficherNoms() {
        _metier.setPositionInfos();
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
        ArrayList<Forme> array = new ArrayList<>();
        array.addAll(_metier.getCoordonneesDessin());
        for(Forme forme:array) {
            Color couleur = forme.getCouleur();
            Path2D path = forme.getPath(m);
            
            if(forme instanceof Navire) {
                couleur = Color.red;
            }
            g2.setColor(couleur);
            if(path!=null) {
                boolean visible = forme.estVisible();
                if(visible) {
                    boolean fill = forme.isFill();
                    if(_typeMarchandiseNavire != null && forme instanceof Terminal) {
                        Terminal t = (Terminal) forme;
                        Quai q = t.getQuai();
                        if(q!=null) { //terminaux rouliers à gérer
                            if(q.peutPrendreEnCharge(_navireSelectionne)) {
                                g2.setColor(COULEUR_QUAI_DISPONIBLE);
                            }
                        }

                    }
                    if(fill) {
                        g2.fill(path);
                    } else {
                        g2.draw(path);
                    }
//                    if(forme.getNom().equals("Quai du Havre")) {
//                        Quai q = (Quai)forme;
//
//                        Point2D p = q.getZoneArrimage().getCentre();
//                        double x = p.getX();
//                        double y = p.getY();
//                        x = x-_mapGauche;
//                        y = y-_mapHaut;
//                        //vérifie si le point est à dessiner
//                        x = (x*_coefX);
//                        y = h - (y*_coefY);
//                        g2.drawRect((int)x, (int)y, 10, 10);
//                    }
                }
                    
//                if(forme.getTypeForme() == TypeShape.NATURAL ||
//                   forme.getTypeForme() == TypeShape.TERMINAL ||
//                   forme.getTypeForme() == TypeShape.QUAI) {
//                    Point2D p = forme.getCoordInfo();
//                    if(p!=null) {
//                        double x = p.getX();
//                        double y = p.getY();
//                        x = x-_mapGauche;
//                        y = y-_mapHaut;
//                        //vérifie si le point est à dessiner
//                        x = (x*_coefX);
//                        y = h - (y*_coefY);
//                        g2.setColor(Color.BLACK);
//                        TextLayout t = new TextLayout(forme.getNom(), new Font("SansSerif", Font.BOLD, (int)(10*_zoomEtat)), g2.getFontRenderContext());
//                        t.draw(g2, (float)(x), (float)(y));
//                        g2.setFont(new Font("SansSerif", Font.BOLD, (int)(10*_zoomEtat)));
//                        
//                    }
//                }
            } 
        }
        Font fontInfos = new Font("SansSerif", Font.BOLD, (int)(9*_zoomEtat));
        g2.setColor(Color.GRAY);
        for(Forme forme:array) {
            Point2D p = forme.getCoordInfo();
            if(p!=null) {
                
                double x = p.getX();
                double y = p.getY();
                x = x-_mapGauche;
                y = y-_mapHaut;
                //vérifie si le point est à dessiner
                x = (x*_coefX);
                y = h - (y*_coefY);
                
                AttributedString nom = new AttributedString(forme.getNom());
                nom.addAttribute(TextAttribute.FONT, fontInfos);
                
                AttributedCharacterIterator paragraph = nom.getIterator();
                int paragraphStart = paragraph.getBeginIndex();
                int paragraphEnd = paragraph.getEndIndex();
                LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, g2.getFontRenderContext());
                lineMeasurer.setPosition(paragraphStart);
                float drawPosY = 0;
                while (lineMeasurer.getPosition() < paragraphEnd) {
                    TextLayout layout = lineMeasurer.nextLayout((float)(forme.getTailleBreakInfo()*_zoomEtat));
                    drawPosY += layout.getAscent();
                    
                    Rectangle2D bounds = layout.getBounds();
                    AffineTransform transform = new AffineTransform();
                    transform.rotate(Math.toRadians(forme.getAngleInfo()), x, y);
                    g2.setTransform(transform);
                    
                    layout.draw(g2, (float)(x), (float)(y+drawPosY));
                    drawPosY += layout.getDescent() + layout.getLeading();
                }
            }
        }
        
        g2.setTransform(new AffineTransform());
        g2.setColor(Color.RED);
        
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString(_infosMouseOver, 50, (int)(h-30));
        g2.drawString(_coordonneesMouseOver, (int)(w-300), (int)(h-10));
        
        
        
//        int cpt = 0;
//        if(resultat!=null) {
//            for(PointPathFinding p:resultat.getResultat()) {
//                g2.setFont(new Font("SansSerif", Font.BOLD, (int)(5*_zoomEtat)));
//                g2.drawString(cpt+"", (float)((p.getX()-_mapGauche)*_coefX), (float)(h-((p.getY()-_mapHaut)*_coefY)));
//                cpt++;
//            }
//        }
        
//        for(Point2D p:_clics) {
//            g2.drawRect((int)p.getX(), (int)p.getY(), 3, 3);
//        }
        //affichage de l'échelle sur la carte
        Point2D debut = new Point2D.Double(0.11986123329621381, 49.44919733333333);
        double km = 1000/COEF_GEO_METRE;
        Point2D fin = new Point2D.Double(0.11986123329621381+km, 49.44919733333333);
        double xD = debut.getX()-_mapGauche;
        double yD = debut.getY()-_mapHaut;
        double xF = fin.getX()-_mapGauche;
        double yF = fin.getY()-_mapHaut;
        xD = (xD*_coefX);
        yD = h - (yD*_coefY);
        xF = (xF*_coefX);
        yF = h - (yF*_coefY);
        g2.drawLine((int)xD, (int)yD, (int)xF, (int)yF);
        g2.drawLine((int)xD, (int)(yD-10), (int)xD, (int)(yD+10));
        g2.drawLine((int)xF, (int)(yF-10), (int)xF, (int)(yF+10));
        g2.drawString("1 KM", (float)(xD+((xF-xD)/2.0f)-10), (float)(yF+10));
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
    
    public void supprimerNavire(Navire navire) {
        _navires.remove(navire);
        _metier.supprimerNavire(navire);
        //_naviresArrives.supprimerNavires(navire);
    }
    
    public void supprimerNavirePanelArrives(Navire navire) {
        _naviresArrives.supprimerNavires(navire);
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
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                refresh();
            }
        });
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
                
                if(forme instanceof Terminal ||
                        forme instanceof Quai ||
                        forme instanceof Navire) {
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
                _clics.add(e.getPoint());
                Insets insets = getInsets();
                double h = getHeight() - insets.top - insets.bottom;
                System.out.println((_pointClick.getX()/_coefX+_mapGauche) + ", " + ((h-_pointClick.getY())/_coefY+_mapHaut));
                Forme forme = _metier.getForme(e.getPoint());
                if(forme!=null) {
                    if(_navireSelectionne!=null) {
//                        DeplacementBateaux t = new DeplacementBateaux(_navireSelectionne, new Point2D.Double(_pointClick.getX()/_coefX+_mapGauche, (h-_pointClick.getY())/_coefY+_mapHaut), _metier.getLittoraux(), getThis());
//                        t.start();
//                        resultat = t;
                        if(forme instanceof Terminal) {
                            Terminal terminal = (Terminal)forme;
                            Quai q = terminal.getQuai();
                            if(q.peutPrendreEnCharge(_navireSelectionne)) {
                                DeplacementBateaux t = new DeplacementBateaux(_navireSelectionne, q, _metier.getLittoraux(), getThis());
                                t.start();
                            }
//                            DeplacementBateaux t = new DeplacementBateaux(_navireSelectionne, new Point2D.Double(_pointClick.getX()/_coefX+_mapGauche, (h-_pointClick.getY())/_coefY+_mapHaut), _metier.getCoordonneesDessin(), getThis());
                                
                        } 
                    }
                    if(forme instanceof Navire) {
                        Navire n = (Navire) forme;
                        if(!n.estAssigneQuai()) {
                            setNavireSelectionne(n);
                        }
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
//                            if(_navireSelectionne!=null) {
//                                if(t.prendEnCharge(_navireSelectionne.getTypeMachandise())) {
//                                    //DeplacementBateaux.deplacer(_navireSelectionne, new Point2D.Double(0.109, 49.458), _metier.getCoordonneesDessin(), _coefX, _coefY);
//                                }
//                            }
                        }
                        _navireSelectionne = null;
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
    
    public MetierMap getMetierMap() {
        return _metier;
    }
}
