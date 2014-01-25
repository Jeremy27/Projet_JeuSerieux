package presentation;

import accesAuDonnees.FichierJson;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modele.Forme;
import modele.Navire;
import modele.Quai;
import modele.Terminal;
import modele.enumeration.TypeShape;

public class AfficherMap extends JPanel{
    private static final Color NATURALCOLOR = new Color(181, 208, 208);
    private static final Color TERMINALCOLOR = new Color(222, 208, 213);
    private static final Color QUAICOLOR = new Color(52, 203, 153);
    private static final Color ROUTECOLOR = new Color(182, 181, 146);
    private static final Color AUTRECOLOR = Color.GRAY;
    
    private final String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map_filtree.json";
    
    private final double LIMITE_X = 0.1900875;
    private final double LIMITE_Y = 49.488;
    
    private PanelInfoForme _panelInfoForme;
    
    private double _diffMinMaxX;
    private double _diffMinMaxY;
    
    private final ArrayList<Forme> _coordonneesDessin;
    private String _toolTip = "";
    
    private double _testMinX = Double.MAX_VALUE;
    private double _testMaxX = 0.0;
    private double _testMinY = Double.MAX_VALUE;
    private double _testMaxY = 0.0;
    
    public AfficherMap(PanelInfoForme panelInfo) {
        _panelInfoForme = panelInfo;
        _coordonneesDessin = new ArrayList<>();
        this.setLayout(null);
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
                            _toolTip = forme.getNom() + " ";
                        }
                        _toolTip += "Id:" + forme.getId();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                        break;
                    }
                }
            }
        });
        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                System.out.println("entré");
                for(Forme forme:_coordonneesDessin) {
                    if(forme.getPath().contains(p)) {
                        System.out.println(forme.getClass().toString());
                        if(forme instanceof Quai) {
                            System.out.println("quai");
                            Quai q = (Quai) forme;
                            _panelInfoForme.setNomPanel("QUAI");
                            _panelInfoForme.setInformations(q.getDonneesFormates());
                            _panelInfoForme.majInformations();
                        } else if(forme instanceof Navire) {
                            Navire n = (Navire) forme;
                            _panelInfoForme.setNomPanel("NAVIRE");
                            _panelInfoForme.setInformations(n.getDonneesFormates());
                        }
                        
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
            Forme forme;
            if(type.toString().equals(TypeShape.NATURAL.toString())) {
                c = NATURALCOLOR;
                fill = true;
                forme = new Forme(nom.toString(), fill, c, Integer.parseInt(id.toString()));
            } else if(type.toString().equals(TypeShape.TERMINAL.toString())) {
                c = TERMINALCOLOR;
                fill = true;
                forme = new Forme(nom.toString(), fill, c, Integer.parseInt(id.toString()));
            } else if(type.toString().equals(TypeShape.QUAI.toString())) {
                c = QUAICOLOR;
                fill = true;
                forme = new Quai(nom.toString(), fill, c, Integer.parseInt(id.toString()), new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.toString().equals(TypeShape.HIGHWAY.toString())){
                c = ROUTECOLOR;
                forme = new Forme(nom.toString(), fill, c, Integer.parseInt(id.toString()));
            } else {
                c = AUTRECOLOR;
                forme = new Forme(nom.toString(), fill, c, Integer.parseInt(id.toString()));
            }

            JsonArray nodes = (JsonArray)jo.get("_nodes");
            for(Object coordonnees:nodes.toArray()) {
                JsonObject node = (JsonObject) coordonnees;
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());

                if(_testMinX>x) {
                    _testMinX = x;
                }
                if(_testMinY>y) {
                    _testMinY = y;
                }
                if(_testMaxY<y) {
                    _testMaxY = y;
                }
                if(_testMaxX<x) {
                    _testMaxX = x;
                }
                
                forme.ajoutCoordonnee(new Point2D.Double(x, y));
            }
            _coordonneesDessin.add(forme);
        }
        
        for(Forme forme:_coordonneesDessin) {
            forme.corrigerCoordonnees(_testMinX, _testMinY);
        }
        _diffMinMaxX = LIMITE_X - _testMinX;
        //_diffMinMaxY = LIMITE_Y - _testMinY;
        _diffMinMaxY = _testMaxY - _testMinY;
        System.out.println(_coordonneesDessin.size());
        System.out.println("");
        System.out.println("min x "+_testMinX);
        System.out.println("max x "+_testMaxX);
        System.out.println("min y "+_testMinY);
        System.out.println("max y "+_testMaxY);
    }
    
    @Override
    public void paint(Graphics g) {
        
        Insets insets = getInsets();
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
      
        double coefMultX = w/_diffMinMaxX;
        double coefMultY = h/_diffMinMaxY;
        
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        for(Forme forme:_coordonneesDessin) {
            
            Color couleur = forme.getCouleur();
            //Path2D path = forme.getPath(h, coefMultX, coefMultY);
            Path2D path = null;
            boolean fill = forme.isFill();
            g2.setColor(couleur);
            if(fill) {
                g2.fill(path);
//                g2.setColor(Color.red);
//                for(Point2D p:forme.getCoordonnees()) {
//                    int x = (int)(p.getX()*coefMultX);
//                    int y = (int)(h - (p.getY()*coefMultY));
//                    g2.drawRect(x, y, 1, 1);
//                }
            } else {
                g2.draw(path);
//                g2.setColor(Color.WHITE);
//                g2.fill(path);
            }
        }
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString(_toolTip, 50, 50);
//        setBackground(Color.CYAN);
    }
    
    public JsonArray getContenu() {
        System.out.println(_nomFichier);
        FichierJson fj = new FichierJson(_nomFichier);
        return fj.getJson();
    }
    
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        
        AfficherMap am = new AfficherMap(new PanelInfoForme(""));
        JsonArray ja = am.getContenu();
        am.makePath2D(ja);
        am.eventsMap();
        
        jf.add(am);
        jf.setSize(780, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
