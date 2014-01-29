package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import javax.swing.JPanel;
import metier.MetierMap;
import modele.Forme;
import modele.Navire;
import modele.Quai;
import modele.enumeration.TypeMarchandise;

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
    private double thisHaut=MIN_DEPART_Y;
    private double thisBas=MAX_DEPART_Y;
    private double thisGauche=MIN_DEPART_X;
    private double thisDroite=MAX_DEPART_X;
    
    private final MetierMap _metier;
    
    private TypeMarchandise _typeMarchandiseNavire=null;
    
    private Point2D _pointClick;
    
    private final PanelInfoForme _panelInfoForme;
    
    public PanelMap(PanelInfoForme panelInfo) {
        _metier = new MetierMap(this, panelInfo);
        _metier.construireFormes();
        _panelInfoForme = panelInfo;
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
        thisBas = MIN_DEPART_Y;
        thisHaut = MAX_DEPART_Y;
        thisDroite = MAX_DEPART_X;
        thisGauche = MIN_DEPART_X;
        
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
            thisGauche = calculGauche;
        } else {
            ajoutDroite = MIN_DEPART_X - calculGauche;
            thisGauche = MIN_DEPART_X;
        }
        
        double calculDroite = _coordCurseurModif.getX()+largeur*(1-_pourcentGauche);
        if(calculDroite<=MAX_DEPART_X) {
            thisDroite = calculDroite;
            thisDroite += ajoutDroite;
        } else {
            double diff = calculDroite-MAX_DEPART_X; //négatif
            thisDroite = MAX_DEPART_X;
            thisGauche -= diff;
        }
        
        double calculHaut = _coordCurseurModif.getY()-hauteur*(1-_pourcentHaut);
        if(calculHaut>=MIN_DEPART_Y) {
            thisHaut = calculHaut;
        } else {
            ajoutBas = MIN_DEPART_Y-calculHaut;
            thisHaut = MIN_DEPART_Y;
        }
        
        double calculBas = _coordCurseurModif.getY()+hauteur*_pourcentHaut;
        if(calculBas<=MAX_DEPART_Y) {
            thisBas = calculBas;
            thisBas += ajoutBas;
        } else {
            double diff = MAX_DEPART_Y-calculBas; //négatif
            thisBas = MAX_DEPART_Y;
            thisHaut += diff;
        }
        
        //recalcul diff
        diffY = thisBas - thisHaut;
        diffX = thisDroite - thisGauche;
        _coefX = w/diffX;
        _coefY = h/diffY;
        
        HashMap<String,Double> m = new HashMap<>();
        m.put("coefX", _coefX);
        m.put("coefY", _coefY);
        m.put("hauteurPanel", h);
        m.put("bas", thisBas);
        m.put("haut", thisHaut);
        m.put("gauche", thisGauche);
        m.put("droite", thisDroite);
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
        
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("", 50, 50);
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
        _coordCurseurModif.setLocation(p.getX()/_coefX+thisGauche, (h-p.getY())/_coefY+thisHaut);
        //System.out.println("curseur x: " + _coordCurseurModif.getX() + " y: " + _coordCurseurModif.getY());
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
        this.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                
                dragCurseur(_pointClick.getX()-e.getPoint().getX(), e.getPoint().getY()-_pointClick.getY());
                _pointClick = e.getPoint();
            }
        });
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _pointClick = e.getPoint();
            }
        });
    }
    
    public void click() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Forme forme = _metier.getForme(e.getPoint());
                if(forme!=null) {
                    if(forme instanceof Navire) {
                        Navire n = (Navire) forme;
                        colorerQuai(n.getTypeMachandise());
                    } else if(forme instanceof Quai) {
                        
                    }
                }
            }
        });
    }
    
    private void colorerQuai(TypeMarchandise type) {
        
    }
}
