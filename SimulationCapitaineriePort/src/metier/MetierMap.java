/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import accesAuDonnees.ADMap;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import modele.Forme;
import modele.Navire;
import modele.Quai;
import modele.Terminal;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeShape;
import presentation.PanelInfoForme;
import presentation.PanelMap;

/**
 *
 * @author brokep
 */
public class MetierMap {
    
    
    
    private final ArrayList<Forme> _coordonneesDessin;
    private final ArrayList<Forme> _littoraux;
    
    //couleurs des différentes formes
    private static final Color NATURALCOLOR = new Color(181, 208, 208);
    private static final Color TERMINALCOLOR = new Color(222, 208, 213);
    private static final Color QUAICOLOR = new Color(52, 203, 153);
    private static final Color ROUTECOLOR = new Color(182, 181, 146);
    private static final Color BATIMENTCOLOR = Color.GRAY;
    private static final Color AUTRECOLOR = Color.GRAY;
    private static final Color FORETCOLOR = new Color(0, 80, 0);
    
    private final ADMap _accesDonneesMap;
    
    public MetierMap(PanelMap map, PanelInfoForme panelInfo) {
        _coordonneesDessin = new ArrayList<>();
        _littoraux = new ArrayList<>();
        _accesDonneesMap = new ADMap();
        
    }
    
    public void construireFormes() {
        makePath2D(_accesDonneesMap.getContenu());
    }

    public ArrayList<Forme> getCoordonneesDessin() {
        return _coordonneesDessin;
    }
    
    public ArrayList<Forme> getCoordonneesLittoraux() {
        return _littoraux;
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
            JsonValue littoral = jo.get("_littoral");
            
            Forme forme;

            //construction de l'objet
            if(type.equals(TypeShape.NATURAL.name())) {
                c = NATURALCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id, TypeShape.NATURAL);
            } else if(type.equals(TypeShape.QUAI.name())) {
                c = QUAICOLOR;
                fill = true;
                forme = new Quai(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.equals(TypeShape.TERMINAL.name())) {
                c = TERMINALCOLOR;
                fill = true;
                forme = new Terminal(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.equals(TypeShape.HIGHWAY.name())) {
                c = ROUTECOLOR;
                forme = new Forme(nom, fill, c, id, TypeShape.HIGHWAY);
            } else if(type.equals(TypeShape.BUILDING.name())) {
                c = BATIMENTCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id, TypeShape.BUILDING);
            } else if(type.equals(TypeShape.FORET.name())) {
                c = FORETCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id, TypeShape.NATURAL);
            } else if(type.equals("TEST")) {
                c = new Color(255, 0, 0);
                fill = false;
                forme = new Forme(nom, fill, c, id, TypeShape.NULL);
            } else {
                c = AUTRECOLOR;
                forme = new Forme(nom, fill, c, id, TypeShape.NULL);
            }
            
            //traitement des coordonnées de la forme
            JsonArray nodes = (JsonArray)jo.get("_nodes");
            for(Object coordonnees:nodes.toArray()) {
                JsonObject node = (JsonObject) coordonnees;
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());
                forme.ajoutCoordonnee(new Point2D.Double(x, y));
            }
            forme.makePathOriginale();
            if(littoral!=null) {
                _littoraux.add(forme);
            }
            //if(forme.getId()==71155277) {
                _coordonneesDessin.add(forme);
            //}
            
        }
        
        
        Forme forme = new Forme("CONNARD", true, Color.BLUE, 564641653, TypeShape.BUILDING);
        
        forme.ajoutCoordonnee(new Point2D.Double(0.1065890219049898, 49.46740831623104));
        forme.ajoutCoordonnee(new Point2D.Double(0.10933301555274476, 49.466411450617876));
        forme.ajoutCoordonnee(new Point2D.Double(0.10696957576854706, 49.46519175622059));
        forme.ajoutCoordonnee(new Point2D.Double(0.1044258736279275, 49.46634108363342));
//        forme.ajoutCoordonnee(new Point2D.Double(0.09195284219457013, 49.48870730050934));
//        forme.ajoutCoordonnee(new Point2D.Double(0.10627368495475113, 49.48329490662139));
//        forme.ajoutCoordonnee(new Point2D.Double(0.13374366515837105, 49.47849235993209));
//        forme.ajoutCoordonnee(new Point2D.Double(0.1399927601809955, 49.47506196943973));
//        forme.ajoutCoordonnee(new Point2D.Double(0.16850425622171944, 49.478873514431235));
//        forme.ajoutCoordonnee(new Point2D.Double(0.18087225678733032, 49.48764006791171));
//        forme.ajoutCoordonnee(new Point2D.Double(0.191027036199095, 49.486953989813244));
//        forme.ajoutCoordonnee(new Point2D.Double(0.1894647624434389, 49.480779286926996));
//        forme.ajoutCoordonnee(new Point2D.Double(0.20144219457013574, 49.47849235993209));
//        forme.ajoutCoordonnee(new Point2D.Double(0.20196295248868779, 49.469573344651955));
//        forme.ajoutCoordonnee(new Point2D.Double(0.17553448812217193, 49.449753310696096));
//        forme.ajoutCoordonnee(new Point2D.Double(0.1001547794117647, 49.45249762308998));
//        forme.ajoutCoordonnee(new Point2D.Double(0.09104151583710407, 49.483980984719864));
//        forme.ajoutCoordonnee(new Point2D.Double(0.09130189479638008, 49.488554838709675));
        forme.makePathOriginale();
//        _coordonneesDessin.add(forme);
        int cpt=0;
        for(Forme f:_coordonneesDessin) {
            if(forme.getPathOriginal().intersects(f.getPathOriginal().getBounds2D())) {
                
                cpt++;
            }
        }
        
    }
    
    public void ajoutForme(Forme f) {
        _coordonneesDessin.add(f);
    }
    
    public Forme getForme(String nom) {
        for(Forme forme:_coordonneesDessin) {
            if(forme.getNom().equals(nom)) {
                return forme;
            }
        }
        return null;
    }
    
    public Forme getForme(Point2D p) {
        Forme selectionnee = null;
        for(Forme forme:_coordonneesDessin) {
            if(forme.getPath().contains(p)) {
                if(selectionnee == null) {
                selectionnee = forme;
                } else {
                    if(forme instanceof Quai ||
                        forme instanceof Terminal ||
                        forme instanceof Navire ||
                        forme.getTypeForme().name().equals(TypeShape.NATURAL.name())) {
                        selectionnee = forme;
                    }
                }
            }    
        }
        return selectionnee;
    }
    
    public void lierQuaisTerminaux() {
        Quai quaiAmeriques = (Quai)getForme("Quai des Amériques");
        Terminal terminalAtlantique = (Terminal)getForme("Terminal de l'Atlantique");
        quaiAmeriques.ajoutTerminal(terminalAtlantique);
        terminalAtlantique.ajoutType(TypeMarchandise.CONTENEURS);
        
        Quai quaiEurope = (Quai)getForme("Quai de l'Europe");
        Terminal terminalEurope = (Terminal)getForme("Terminal de l'Europe");
        quaiEurope.ajoutTerminal(terminalEurope);
        terminalEurope.ajoutType(TypeMarchandise.CONTENEURS);
        
        Terminal terminalNormandie = (Terminal)getForme("Terminal de Normandie");
        terminalNormandie.ajoutType(TypeMarchandise.CONTENEURS);
        
        Terminal terminalCruise = (Terminal)getForme("Cruise Terminal");
        terminalCruise.ajoutType(TypeMarchandise.PASSAGER);
    }
}
