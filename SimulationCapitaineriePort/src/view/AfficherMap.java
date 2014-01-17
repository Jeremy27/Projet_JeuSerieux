package view;

import acces_donnees.FichierJson;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AfficherMap extends JPanel{
    private String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    private final double _minX = 0.0532394;
    private final double _minY = 49.3494358;
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map.json";
    private Path2D _dessinMap;
    private ArrayList<Object[]> _coordonneesDessin;
    private double minX = Integer.MAX_VALUE;
    private double minY = Integer.MAX_VALUE;
    private double maxX = -1;
    private double maxY = -1;
    
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
            JsonValue type = jo.get("_type");
            boolean estPremier = true;
            JsonArray nodes = (JsonArray)jo.get("_nodes");
            for(Object coordonnees:nodes.toArray()) {
                JsonObject node = (JsonObject) coordonnees;
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());

                x-=_minX;
                y-=_minY;
                x*=2000;
                y*=2000;

                if(x<minX) {
                    minX = x;
                }
                if(y<minY) {
                    minY = y;
                }
                if(x>maxX) {
                    maxX = x;
                }
                if(y>maxY) {
                    maxY = y;
                }
                if(estPremier) {
                    estPremier = false;
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }

            }
            _coordonneesDessin.add(new Object[] {c, path});
                
        }
        System.out.println(minX);
        System.out.println(minY);
        System.out.println(maxX);
        System.out.println(maxY);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
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
