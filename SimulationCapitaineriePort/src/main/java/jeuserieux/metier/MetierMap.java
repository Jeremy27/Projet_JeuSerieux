package jeuserieux.metier;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import jeuserieux.accesAuDonnees.ADMap;
import jeuserieux.modele.ArrayForme;
import jeuserieux.modele.ArrayNavire;
import jeuserieux.modele.ArrayQuai;
import jeuserieux.modele.ArrayTerminal;
import jeuserieux.modele.Forme;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Quai;
import jeuserieux.modele.Terminal;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeShape;
import jeuserieux.presentation.PanelInfoForme;
import jeuserieux.presentation.PanelMap;

/**
 * Cette classe gère la création des Forme à partir du fichier json des formes de la carte
 * Elle stocke ensuite ces formes et gère leur fonctionnement
 * @author gary
 */
public class MetierMap {
    
    private final ArrayForme _formes;
    private final ArrayForme _littoraux;
    private final ArrayForme _coordonneesDessin;
    
    private final ArrayNavire _navires;
    private final ArrayQuai _quais;
    private final ArrayTerminal _terminaux;
    
    
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
        _formes = new ArrayForme();
        _littoraux = new ArrayForme();
        _coordonneesDessin = new ArrayForme();
        _navires = new ArrayNavire();
        _quais = new ArrayQuai();
        _terminaux = new ArrayTerminal();
        _accesDonneesMap = new ADMap();
        
    }
    
    /**
     * Cette fonction lance la création des formes à partir du JsonArray
     * @see javax.json.JsonArray
     */
    public void construireFormes() {
        makePath2D(_accesDonneesMap.getContenu());
    }

    /**
     * Cette fonction retourne la liste des formes affichées sur la carte
     * @return la liste des formes affichées sur la carte
     */
    public ArrayList<Forme> getCoordonneesDessin() {
        return _coordonneesDessin;
    }
    
    /**
     * Cette fonction retourne la liste des formes présentes sur les littoraux de la carte
     * @return la liste des formes présentes sur les littoraux de la carte
     */
    public ArrayList<Forme> getCoordonneesLittoraux() {
        return _littoraux;
    }
    
    /**
     * Cette fonction créé, à partir des données Json de la carte, les objets Forme correspondants
     * @see javax.json.JsonArray
     * @param ja un array d'objets Json contenant toutes les formes de la carte
     */
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
           if(type.equals(TypeShape.QUAI.name())) {
                c = QUAICOLOR;
                fill = false;
                forme = new Quai(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
                _quais.add((Quai)forme);
            } else if(type.equals(TypeShape.TERMINAL.name())) {
                c = TERMINALCOLOR;
                fill = true;
                forme = new Terminal(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
                _terminaux.add((Terminal)forme);
            } else {
                if(type.equals(TypeShape.NATURAL.name())) {
                    c = NATURALCOLOR;
                    fill = true;
                    forme = new Forme(nom, fill, c, id, TypeShape.NATURAL);
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
                getFormes().add(forme);
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
            _coordonneesDessin.add(forme);
        }
        
    }
    
    /**
     * Cette fonction ajoute une forme à la carte
     * @param f la forme à ajouter
     */
    public void ajoutForme(Forme f) {
        if(f instanceof Terminal) {
            _terminaux.add((Terminal)f);
        } else if(f instanceof Quai) {
            _quais.add((Quai)f);
        } else if (f instanceof Navire) {
            _navires.add((Navire)f);
        } else {
            _formes.add(f);
        }
        _coordonneesDessin.add(f);
    }
    
    /**
     * Cette fonction vide la liste des formes de tous les Navires
     */
    public void enleverNavires() {
        _coordonneesDessin.removeAll(_navires);
        _navires.clear();
    }
    
    /**
     * Lie les quais aux terminaux comme ils le sont en réalité sur le port du Havre
     */
    public void lierQuaisTerminaux() {
        Quai quaiAmeriques = _quais.getQuai("Quai des Amériques");
        Terminal terminalAtlantique = _terminaux.getTerminal("Terminal de l'Atlantique");
        quaiAmeriques.ajoutTerminal(terminalAtlantique);
        terminalAtlantique.ajoutType(TypeMarchandise.CONTENEURS);
        
        Quai quaiEurope = _quais.getQuai("Quai de l'Europe");
        Terminal terminalEurope = _terminaux.getTerminal("Terminal de l'Europe");
        quaiEurope.ajoutTerminal(terminalEurope);
        terminalEurope.ajoutType(TypeMarchandise.CONTENEURS);
        
        Terminal terminalNormandie = _terminaux.getTerminal("Terminal de Normandie");
        terminalNormandie.ajoutType(TypeMarchandise.CONTENEURS);
        
        Terminal terminalCruise = _terminaux.getTerminal("Cruise Terminal");
        terminalCruise.ajoutType(TypeMarchandise.PASSAGER);
    }
    
    /**
     * Cette fonction retrouve une forme à partir de son nom
     * @see modele.Forme
     * @param nom nom de la forme à rechercher
     * @return la forme si elle est trouvée, null sinon
     */
    public Forme getForme(String nom) {
        Forme selectionnee;
        selectionnee = _navires.getNavire(nom);
        if(selectionnee!=null) return selectionnee;
        selectionnee = _quais.getQuai(nom);
        if(selectionnee!=null) return selectionnee;
        selectionnee = _terminaux.getTerminal(nom);
        if(selectionnee!=null) return selectionnee;
        selectionnee = getFormes().getForme(nom);
        return selectionnee;
    }
    
    /**
     * Cette fonction tente de trouver la forme contenant le point en paramètre
     * Elle utilise un système de priorité dans le cas où plusieurs formes contiennent ce point
     * Navire > Quai, Terminal > Forme
     * @see modele.Forme
     * @see modele.Terminal
     * @see modele.Quai
     * @see modele.Navire
     * @param p le point que la forme recherchée doit contenir
     * @return la Forme trouvée, sinon null
     */
    public Forme getForme(Point2D p) {
        Forme selectionnee;
        selectionnee = _navires.getNavire(p);
        if(selectionnee!=null) return selectionnee;
        selectionnee = _quais.getQuai(p);
        if(selectionnee!=null) return selectionnee;
        selectionnee = _terminaux.getTerminal(p);
        if(selectionnee!=null) return selectionnee;
        selectionnee = getFormes().getForme(p);
        return selectionnee;
    }

    /**
     * @return the _navires
     */
    public ArrayList<Navire> getNavires() {
        return _navires;
    }

    /**
     * @return the _quais
     */
    public ArrayList<Quai> getQuais() {
        return _quais;
    }

    /**
     * @return the _terminaux
     */
    public ArrayList<Terminal> getTerminaux() {
        return _terminaux;
    }

    /**
     * @return the _formes
     */
    public ArrayForme getFormes() {
        return _formes;
    }
}
