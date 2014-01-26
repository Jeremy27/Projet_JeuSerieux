/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import accesAuDonnees.ADMap;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.json.JsonArray;
import javax.json.JsonObject;
import modele.Forme;
import modele.Quai;
import modele.Terminal;
import modele.enumeration.TypeShape;
import presentation.PanelMap;
import presentation.PanelInfoForme;

/**
 *
 * @author brokep
 */
public class MetierMap {
    private final PanelMap _map;
    private Point2D _pointClick;
    
    private final PanelInfoForme _panelInfoForme;
    private final ArrayList<Forme> _coordonneesDessin;
    
    //couleurs des différentes formes
    private static final Color NATURALCOLOR = new Color(181, 208, 208);
    private static final Color TERMINALCOLOR = new Color(222, 208, 213);
    private static final Color QUAICOLOR = new Color(52, 203, 153);
    private static final Color ROUTECOLOR = new Color(182, 181, 146);
    private static final Color BATIMENTCOLOR = Color.GRAY;
    private static final Color AUTRECOLOR = Color.GRAY;
    
    private final ADMap _accesDonneesMap;
    
    public MetierMap(PanelMap map, PanelInfoForme panelInfo) {
        _panelInfoForme = panelInfo;
        _coordonneesDessin = new ArrayList<>();
        _map = map;
        _accesDonneesMap = new ADMap();
        
    }
    
    public void construireFormes() {
        makePath2D(_accesDonneesMap.getContenu());
    }
    
    public void initEvents() {
        wheel();
        click();
        move();
    }

    public ArrayList<Forme> getCoordonneesDessin() {
        return _coordonneesDessin;
    }
    
    public void makePath2D(JsonArray ja) {
        for(Object obj:ja.toArray()) {
            //variables indispensables
            boolean fill = false;
            Color c;
            JsonObject jo = (JsonObject) obj;
            long id = Long.parseLong(jo.get("_id").toString());
            String type = jo.get("_type").toString();
            String nom = jo.get("_nom").toString();
            Forme forme;

            //construction de l'objet
            if(type.equals(TypeShape.NATURAL.toString())) {
                c = NATURALCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id);
            } else if(type.equals(TypeShape.QUAI.toString())) {
                c = QUAICOLOR;
                fill = true;
                forme = new Quai(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.equals(TypeShape.TERMINAL.toString())) {
                c = TERMINALCOLOR;
                fill = true;
                forme = new Terminal(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.equals(TypeShape.HIGHWAY.toString())) {
                c = ROUTECOLOR;
                forme = new Forme(nom, fill, c, id);
            } else if(type.equals(TypeShape.BUILDING.toString())) {
                c = BATIMENTCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id);
            } else {
                c = AUTRECOLOR;
                forme = new Forme(nom, fill, c, id);
            }
            
            //traitement des coordonnées de la forme
            JsonArray nodes = (JsonArray)jo.get("_nodes");
            for(Object coordonnees:nodes.toArray()) {
                JsonObject node = (JsonObject) coordonnees;
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());
                forme.ajoutCoordonnee(new Point2D.Double(x, y));
            }
            _coordonneesDessin.add(forme);
        }
        
    }
    
    
    
    public void wheel() {
        _map.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                //le curseur doit correspondre au zoom déjà fait et pas situation de départ
                //il faut jouer avec les coef pour faire la correspondance
                _map.setCurseur(e.getPoint());
                if(e.getWheelRotation()<0) {
                    //vers le haut -> zoom
                    _map.augmenterZoom();
                    
                } else {
                    //dezoom
                    _map.baisserZoom();
                }
            }
        });
    }
    
    public void move() {
        _map.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                
                _map.dragCurseur(_pointClick.getX()-e.getPoint().getX(), e.getPoint().getY()-_pointClick.getY());
                _pointClick = e.getPoint();
            }
        });
        
        _map.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _pointClick = e.getPoint();
            }
        });
    }
    
    public void click() {
        
    }
}
