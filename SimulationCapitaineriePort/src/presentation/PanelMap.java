package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import metier.MetierMap;
import modele.Forme;

public class PanelMap extends JPanel{
    private static final double ZOOMMIN = 1.0;
    private static final double ZOOMMAX = 10.0;
    
    /**************** MAP *******************/
    private Point2D _coordCurseurModif;
    private double _pourcentGauche=0.5;
    private double _pourcentHaut=0.5;
    //dezoom max
    private final double MAX_DEPART_X=0.1900875;
    private final double MAX_DEPART_Y=49.488;
    private final double MIN_DEPART_X=0.10;
    private final double MIN_DEPART_Y=49.448;
    
    //coefs
    private double _coefX;
    private double _coefY;
    
    //zoom
    private double _zoomEtat = ZOOMMIN;
    
    //limites affichage map
    private double _mapHaut=49.448;
    private double _mapBas=49.488;
    private double _mapGauche=0.10;
    private double _mapDroite=0.1900875;
    
    private MetierMap _metier;
    
    public PanelMap(PanelInfoForme panelInfo) {
        _metier = new MetierMap(this, panelInfo);
        _metier.construireFormes();
        _metier.initEvents();
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
//        double calculGauche = _coordCurseurModif.getX()-largeur/2.0;
//        double calculDroite = _coordCurseurModif.getX()+largeur/2.0;
//        double calculHaut = _coordCurseurModif.getY()-hauteur/2.0;
//        double calculBas = _coordCurseurModif.getY()+hauteur/2.0;
//        
//        _mapGauche = calculGauche;
//        _mapDroite = calculDroite;
//        _mapHaut = calculHaut;
//        _mapBas = calculBas;
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
        //System.out.println(m);
        for(Forme forme:_metier.getCoordonneesDessin()) {
            Color couleur = forme.getCouleur();
            g2.setColor(couleur);
            
            Path2D path = forme.getPath(m);
            if(path!=null) {
                boolean fill = forme.isFill();
                if(fill) {
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
        _coordCurseurModif.setLocation(p.getX()/_coefX+_mapGauche, (h-p.getY())/_coefY+_mapHaut);
        //System.out.println("curseur x: " + _coordCurseurModif.getX() + " y: " + _coordCurseurModif.getY());
    }
    
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        
        
        PanelMap am = new PanelMap(new PanelInfoForme(""));
//        JsonArray ja = am.getContenu();
//        am.makePath2D(ja);
//        
//        EvenementsMap e = new EvenementsMap(am);
//        e.wheel();
//        e.move();
        
        jf.add(am);
        jf.setSize(780, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
