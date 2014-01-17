package view;

import acces_donnees.FichierJson;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Window;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AfficherMap extends JPanel{
    private String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map.json";
    private Path2D _dessinMap;
    private ArrayList<Object[]> _coordonneesDessin;
    private double _minX = Integer.MAX_VALUE;
    private double _minY = Integer.MAX_VALUE;
    private double _maxX = -1;
    private double _maxY = -1;
    
    public AfficherMap() {
        _coordonneesDessin = new ArrayList<>();
    }
    
    public String getNomFichier() {
        return _nomFichier;
        
    }
    
    public void makePath2D(JsonArray ja) {
        
        for(Object obj:ja.toArray()) {
            Color c = Color.red;
            JsonObject jo = (JsonObject) obj;
            Path2D path = new Path2D.Double();
            
            JsonValue id = jo.get("_id");
            JsonValue amenity = jo.get("amenity");
            JsonValue building = jo.get("building");
            JsonValue highway = jo.get("highway");
            JsonValue natural = jo.get("natural");
            //System.out.println(building);
            
            if(amenity!=null) {
                //amenity
            } else if(highway!=null) {
                //route
            } else if(natural!=null) {
                //water
            }else if( building==null) {
                boolean estPremier = true;
                JsonArray nodes = (JsonArray)jo.get("nodes");
                for(Object coordonnees:nodes.toArray()) {
                    JsonObject node = (JsonObject) coordonnees;
                    double x = Double.parseDouble(node.get("x").toString());
                    double y = Double.parseDouble(node.get("y").toString());
                    if(x<_minX) {
                        _minX = x;
                    }
                    if(y<_minY) {
                        _minY = y;
                    }
                    if(x>_maxX) {
                        _maxX = x;
                    }
                    if(y>_maxY) {
                        _maxY = y;
                    }
                    if(estPremier) {
                        estPremier = false;
                        path.moveTo((x-737)*5.37, (y-660)*5.37);
                    } else {
//                        Point2D point = path.getCurrentPoint();
                        path.lineTo((x-737)*5.37, (y-660)*5.37);
//                        
//                        double xMilieu = (point.getX()+(x-737)*5.37)/2.0;
//                        double yMilieu = (point.getY()+(y-737)*5.37)/2.0;
//                        path.curveTo(point.getX(), point.getY(), xMilieu, yMilieu, (x-737)*5.37, (y-660)*5.37);
                    }
                    
                }
                _coordonneesDessin.add(new Object[] {c, path});
            }
           
                
        }
        System.out.println(_minX);
        System.out.println(_minY);
        System.out.println(_maxX);
        System.out.println(_maxY);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        AffineTransform at = new AffineTransform();
        //at.translate(-((_minX+Window.WIDTH)/2), -(_minY+Window.HEIGHT)/2);
//        at.scale(5, 5);
//        at.translate(-_minX, -_minY);
        
        
        g2.transform(at);
        for(Object[] o:_coordonneesDessin) {
            Color couleur = (Color)o[0];
            Path2D path = (Path2D)o[1];
            g2.setColor(couleur);
            g2.draw(path);
        }
        
        
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
        
        jf.add(am);
        jf.setSize(780, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
