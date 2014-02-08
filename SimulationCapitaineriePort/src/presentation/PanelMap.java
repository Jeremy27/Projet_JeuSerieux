package presentation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import metier.DeplacementBateaux;
import metier.MetierMap;
import modele.Forme;
import modele.Navire;
import modele.Quai;
import modele.Terminal;
import modele.enumeration.TypeMarchandise;
import modele.outils.PointPathFinding;

public class PanelMap extends JPanel{
    private static final double ZOOMMIN = 1.0;
    private static final double ZOOMMAX = 10.0;
    
    private final Color COULEUR_QUAI_DISPONIBLE = new Color(255, 0, 0);
    
    /**************** MAP *******************/
    private Point2D _coordCurseurModif;
    private double _pourcentGauche=0.5;
    private double _pourcentHaut=0.5;
    //dezoom max
    private final double MAX_DEPART_X=0.1900875;
    private final double MAX_DEPART_Y=49.488;
//    private final double MIN_DEPART_X=0.10;
    private final double MIN_DEPART_X=0.09;
    private final double MIN_DEPART_Y=49.448;
    
    //coefs
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
    
    private Navire[] _navires;
    
    private String _infosMouseOver="";
    private String _coordonneesMouseOver="";
    private Navire _navireSelectionne=null;
    
    ArrayList<PointPathFinding> resultat;
    
    public PanelMap(PanelInfoForme panelInfo) {
        _metier = new MetierMap(this, panelInfo);
        _metier.construireFormes();
        _panelInfoForme = panelInfo;
    }
    
    public void lierMapRealite() {
        _metier.lierQuaisTerminaux();
    }
    
    public void initEvents() {
        move();
        wheel();
        click();
    }
    
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
        for(Forme forme:_metier.getCoordonneesDessin()) {
            Color couleur = forme.getCouleur();
            
            Path2D path = forme.getPath(m);
            g2.setColor(couleur);
            if(path!=null) {
                boolean fill = forme.isFill();
                if(fill) {
                    if(_typeMarchandiseNavire != null && forme instanceof Quai) {
                        Quai q = (Quai) forme;
                        if(q.prendEnCharge(_typeMarchandiseNavire)) {
                            g2.setColor(COULEUR_QUAI_DISPONIBLE);
                        }
                    }
                    
                    g2.fill(path);
                    
                } else {
                    g2.draw(path);
                }
            } 
        }
        g2.setColor(Color.RED);
        for(Navire navire:_navires) {
            
            Path2D p = navire.getPath(m);
            if(p!=null) {
                g2.fill(p);
            }
            
        }
        
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString(_infosMouseOver, 50, (int)(h-30));
        g2.drawString(_coordonneesMouseOver, (int)(w-300), (int)(h-10));
//        int cpt = 0;
//        if(resultat!=null) {
//            for(PointPathFinding p:resultat) {
//                //_coordCurseurModif.setLocation(p.getX()/_coefX+_mapGauche, (h-p.getY())/_coefY+_mapHaut);
////                System.out.println("======================");
////                System.out.println(p.getPoint().getX());
////                System.out.println(h-p.getPoint().getY());
//                //(_pointClick.getX()/_coefX+_mapGauche) + ", " + (_pointClick.getY()/_coefY+_mapHaut);
//                //g2.drawRect((int)((p.getPoint().getX()-_mapGauche)*_coefX), (int)(h-((p.getPoint().getY()-_mapHaut)*_coefY)), 5, 5);
//                g2.setFont(new Font("SansSerif", Font.BOLD, (int)(10*_zoomEtat)));
//                g2.drawString(cpt+"", (float)((p.getPoint().getX()-_mapGauche)*_coefX), (float)(h-((p.getPoint().getY()-_mapHaut)*_coefY)));
////                System.out.println((p.getPoint().getX()-_mapGauche)*_coefX);
////                System.out.println(h-((p.getPoint().getY()-_mapHaut)*_coefY));
//                //System.out.println(h + " - " + p.getPoint().getY() + " * " + _coefY + " - " + _mapHaut);
//                cpt++;
//            }
//        }
    }
    
    public void setNavires(Navire[] navires) {
        _navires = navires;
        for(Navire n:_navires) {
            _metier.ajoutForme(n);
        }
    }
    
    public void augmenterZoom() {
        if(_zoomEtat<ZOOMMAX) {
            _zoomEtat += 0.5;
            refresh();
        }
    }
    
    public void baisserZoom() {
        if(_zoomEtat>ZOOMMIN) {
            _zoomEtat -= 0.5;
            refresh();
        }
    }
    
    public void dragCurseur(double directionX, double directionY) {
        Insets insets = getInsets();
        double h = getHeight() - insets.top - insets.bottom;
        _coordCurseurModif.setLocation(_coordCurseurModif.getX()+(0.0001*directionX/_zoomEtat), _coordCurseurModif.getY()+(0.0001*directionY/_zoomEtat));
        refresh();
    }
    
    public void setCurseur(Point p) {
        Insets insets = getInsets();
        double w = getWidth() - insets.left - insets.right;
        double h = getHeight() - insets.top - insets.bottom;
        _pourcentGauche = p.getX()/w;
        _pourcentHaut = p.getY()/h;
        _coordCurseurModif.setLocation(p.getX()/_coefX+_mapGauche, (h-p.getY())/_coefY+_mapHaut);
    }
    
    public void setTypeColorer(TypeMarchandise type) {
        _typeMarchandiseNavire = type;
    }
    
    public void wheel() {
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
    
    public void move() {
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
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _pointClick = e.getPoint();
            }
        });
    }
    
    public PanelMap getThis() {
        return this;
    }
    
    public void click() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Insets insets = getInsets();
                double h = getHeight() - insets.top - insets.bottom;
                Forme forme = _metier.getForme(e.getPoint());
                if(forme!=null) {
                    if(_navireSelectionne!=null) {
                        System.out.println("déplacement");
                        //(_pointClick.getX()/_coefX+_mapGauche) + ", " + ((h-_pointClick.getY())/_coefY+_mapHaut);
                        Thread t = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Insets insets = getInsets();
                                double h = getHeight() - insets.top - insets.bottom;
                                DeplacementBateaux.deplacer(_navireSelectionne, new Point2D.Double(_pointClick.getX()/_coefX+_mapGauche, (h-_pointClick.getY())/_coefY+_mapHaut), _metier.getCoordonneesDessin(), _coefX, _coefY, getThis());
                            }
                        });
                        t.start();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PanelMap.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //resultat = DeplacementBateaux.deplacer(_navireSelectionne, new Point2D.Double(_pointClick.getX()/_coefX+_mapGauche, (h-_pointClick.getY())/_coefY+_mapHaut), _metier.getCoordonneesDessin(), _coefX, _coefY);
//                        for(PointPathFinding p:resultat) {
//                            System.out.println("x: " + p.getPoint().getX() + " y: " + p.getPoint().getY());
//                        }
                        System.out.println("fin déplacement");
                    }
                    _navireSelectionne = null;
                    if(forme instanceof Navire) {
                        Navire n = (Navire) forme;
                        _navireSelectionne = n;
                        setTypeColorer(n.getTypeMachandise());
                    } else  {
                        setTypeColorer(null);
                        _navireSelectionne = null;
                        if(forme instanceof Quai) {
                            System.out.println("QUAI");
                            Quai q = (Quai) forme;
                            _panelInfoForme.setInformations(q.getDonneesFormates());
                            _panelInfoForme.setNomPanel("Quai");
                            _panelInfoForme.majInformations();
                        } else if(forme instanceof Terminal) {
                            System.out.println("TERMINAL");
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
                } else {
                    if(_navireSelectionne!=null) {
                        System.out.println("déplacement");
                        //(_pointClick.getX()/_coefX+_mapGauche) + ", " + ((h-_pointClick.getY())/_coefY+_mapHaut);
                        //resultat = DeplacementBateaux.deplacer(_navireSelectionne, new Point2D.Double(e.getPoint().getX()/_coefX+_mapGauche, (h-e.getPoint().getY())/_coefY+_mapHaut), _metier.getCoordonneesDessin(), _coefX, _coefY);
//                        for(PointPathFinding p:resultat) {
//                            System.out.println("x: " + p.getPoint().getX() + " y: " + p.getPoint().getY());
//                        }
                        System.out.println("fin déplacement");
                    }
                    refresh();
                }
            }
        });
    }
    
    public void remiseAZero() {
        // virer les navires sur la map
    }
}
