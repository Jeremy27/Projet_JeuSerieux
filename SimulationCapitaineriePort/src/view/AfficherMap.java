package view;

import acces_donnees.FichierJson;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modele.Forme;
import modele.enumeration.TypeShape;

public class AfficherMap extends JPanel{
    private static final Color NATURALCOLOR = Color.BLUE;
    private static final Color TERMINALCOLOR = Color.BLACK;
    private static final Color QUAICOLOR = Color.WHITE;
    private static final Color AUTRECOLOR = Color.GRAY;
    private static final Color FONDCOLOR = Color.CYAN;
    
    private static final int COEF_MULT_COORDONEES = 2000;
    
    private final String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    private final double _minX = 0.0532394;
    private final double _minY = 49.3494358;
    private double _maxY = 0.0;
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map.json";
    private final ArrayList<Forme> _coordonneesDessin;
    private String _toolTip = "";
    
    public AfficherMap() {
        _coordonneesDessin = new ArrayList<>();
        this.setLayout(null);
    }
    
    public String getNomFichier() {
        return _nomFichier;
        
    }
    
    public void eventsMap() {
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                Point p = me.getPoint();
                //System.out.println("x: " + p.getX() + " y: " + p.getY());
                for(Forme forme:_coordonneesDessin) {
                    if(forme.getPath().contains(p)) {
                        String nom = forme.getNom();
                        if(nom!=null) {
                            _toolTip = forme.getNom() + "\nId:" + forme.getId();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    refresh();
                                }
                            });
                        }
                        break;
                    }
                }
            }
        });
        
    }
    
    public void refresh() {
        repaint();
        revalidate();
    }
    
    public void makePath2D(JsonArray ja) {
        
        for(Object obj:ja.toArray()) {
            boolean fill = false;
            Color c;
            JsonObject jo = (JsonObject) obj;
            //Path2D path = new Path2D.Double();
            
            JsonValue id = jo.get("_id");
            JsonValue type = jo.get("_type");
            JsonValue nom = jo.get("_nom");
            
            if(!type.toString().equals(TypeShape.HIGHWAY.toString())) {
                if(type.toString().equals(TypeShape.NATURAL.toString())) {
                    c = NATURALCOLOR;
                    fill = true;
                } else if(type.toString().equals(TypeShape.TERMINAL.toString())) {
                    c = TERMINALCOLOR;
                    fill = true;
                } else if(type.toString().equals(TypeShape.QUAI.toString())) {
                    c = QUAICOLOR;
                    fill = true;
                } else {
                    c = AUTRECOLOR;
                }
                Forme forme = new Forme(nom.toString(), fill, c, Integer.parseInt(id.toString()));
                _coordonneesDessin.add(forme);

                boolean estPremier = true;
                JsonArray nodes = (JsonArray)jo.get("_nodes");
                for(Object coordonnees:nodes.toArray()) {
                    JsonObject node = (JsonObject) coordonnees;
                    double x = Double.parseDouble(node.get("x").toString());
                    double y = Double.parseDouble(node.get("y").toString());
                    x-=_minX;
                    y-=_minY;
                    x*=COEF_MULT_COORDONEES;
                    y*=COEF_MULT_COORDONEES;
                    
                    if(_maxY<y) {
                        _maxY = y;
                    }
                    forme.ajoutCoordonnee(new Point2D.Double(x, y));
//                    if(estPremier) {
//                        estPremier = false;
//                        path.moveTo(x, y);
//                    } else {
//                        path.lineTo(x, y);
//                    }
                }
                
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        Insets insets = getInsets();
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        double ajout = h/2.0 - _maxY/2.0; //car la minY = 0
        for(Forme forme:_coordonneesDessin) {
            Color couleur = forme.getCouleur();
            Path2D path = forme.getPath(h, ajout);
            boolean fill = forme.isFill();
            g2.setColor(couleur);
            if(fill) {
                g2.fill(path);
            } else {
                g2.draw(path);
            }
        }
        g2.setBackground(FONDCOLOR);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.drawString(_toolTip, 400, 50);
    }
    
    public JsonArray getContenu() {
        System.out.println(_nomFichier);
        FichierJson fj = new FichierJson(_nomFichier);
        return fj.getJson();
    }
    
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        
        AfficherMap am = new AfficherMap();
        JsonArray ja = am.getContenu();
        am.makePath2D(ja);
        am.eventsMap();
        
        jf.add(am);
        jf.setSize(780, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
