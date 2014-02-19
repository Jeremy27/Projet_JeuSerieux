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
        
        creerZonesArrimage();
        
    }
    
    public void creerZonesArrimage() {
        Forme zoneArrimage = new Forme("zone arrimage quai Amériques", false, Color.RED, 897965451, TypeShape.NATURAL);
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.14911433784994924, 49.47094835956716));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.15819687360191256, 49.47513481570752));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.16855239024708898, 49.4759323069356));
        zoneArrimage.makePathOriginale();
        _formes.add(zoneArrimage);
        _coordonneesDessin.add(zoneArrimage);
        
        zoneArrimage = new Forme("zone arrimage quai de l'Europe", false, Color.RED, 897965451, TypeShape.NATURAL);
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.1751677876763177, 49.47767986430974));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.18312810226429097, 49.484476975420854));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.18355530159613953, 49.48500080875419));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.18425511599851516, 49.4865848087542));
        zoneArrimage.makePathOriginale();
        _formes.add(zoneArrimage);
        _coordonneesDessin.add(zoneArrimage);
        
        zoneArrimage = new Forme("zone arrimage quai TNMSC", false, Color.RED, 897965451, TypeShape.NATURAL);
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.12900696070633152, 49.46221298809523));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.14932139755011137, 49.45944784523808));
        zoneArrimage.makePathOriginale();
        _formes.add(zoneArrimage);
        _coordonneesDessin.add(zoneArrimage);
        
        zoneArrimage = new Forme("zone arrimage quai porte Océane", false, Color.RED, 897965451, TypeShape.NATURAL);
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.1631397518294623, 49.45781998809524));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.15343622414890232, 49.45916698809524));
        zoneArrimage.makePathOriginale();
        _formes.add(zoneArrimage);
        _coordonneesDessin.add(zoneArrimage);
        
        zoneArrimage = new Forme("zone arrimage quai de France", false, Color.RED, 897965451, TypeShape.NATURAL);
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.16339607142857143, 49.457862750000004));
        zoneArrimage.ajoutCoordonnee(new Point2D.Double(0.1783724594336621, 49.45574603571429));
        zoneArrimage.makePathOriginale();
        _formes.add(zoneArrimage);
        _coordonneesDessin.add(zoneArrimage);
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
        Forme zoneArrimageAmeriques = _formes.getForme("zone arrimage quai Amériques");
        lierQuaisTerminaux(quaiAmeriques, terminalAtlantique, zoneArrimageAmeriques, TypeMarchandise.CONTENEURS);
        
        Quai quaiEurope = _quais.getQuai("Quai de l'Europe");
        Terminal terminalEurope = _terminaux.getTerminal("Terminal de l'Europe");
        Forme zoneArrimageEurope = _formes.getForme("zone arrimage quai de l'Europe");
        lierQuaisTerminaux(quaiEurope, terminalEurope, zoneArrimageEurope, TypeMarchandise.CONTENEURS);
        
        Quai quaiDuHavre = _quais.getQuai("Quai du Havre");
        Terminal terminalTNMSC = _terminaux.getTerminal("Terminal TNMSC");
        Terminal terminalPorteOceane = _terminaux.getTerminal("Terminal Porte Océane");
        Terminal terminalDeFrance = _terminaux.getTerminal("Terminal de France");
        Forme zoneArrimageTNMSC= _formes.getForme("zone arrimage quai TNMSC");
        Forme zoneArrimagePorteOceane= _formes.getForme("zone arrimage quai porte Océane");
        Forme zoneArrimageDeFrance= _formes.getForme("zone arrimage quai de France");
        lierQuaisTerminaux(quaiDuHavre, terminalTNMSC, zoneArrimageTNMSC, TypeMarchandise.CONTENEURS);
        lierQuaisTerminaux(quaiDuHavre, terminalPorteOceane, zoneArrimagePorteOceane, TypeMarchandise.CONTENEURS);
        lierQuaisTerminaux(quaiDuHavre, terminalDeFrance, zoneArrimageDeFrance, TypeMarchandise.CONTENEURS);
//        
//        Terminal terminalNormandie = _terminaux.getTerminal("Terminal de Normandie");
//        terminalNormandie.ajoutType(TypeMarchandise.CONTENEURS);
//        
//        Terminal terminalCruise = _terminaux.getTerminal("Cruise Terminal");
//        terminalCruise.ajoutType(TypeMarchandise.PASSAGER);
    }
    
    public void setPositionInfos() {
        
        Quai quaiAmeriques = _quais.getQuai("Quai des Amériques");
        quaiAmeriques.setCoordInfo(new Point2D.Double(0.1556605373051225, 49.47354311111111));
        quaiAmeriques.setAngleInfo(-17);
        Quai quaiEurope = _quais.getQuai("Quai de l'Europe");
        quaiEurope.setCoordInfo(new Point2D.Double(0.17552924554565702, 49.48018733333333));
        quaiEurope.setAngleInfo(-55);
        Quai quaiBougainville = _quais.getQuai("Quai de Bougainville");
        quaiBougainville.setCoordInfo(new Point2D.Double(0.1866762722717149, 49.47093786666666));
        quaiBougainville.setAngleInfo(70);
        Quai quaiDuHavre = _quais.getQuai("Quai du Havre");
        quaiDuHavre.setCoordInfo(new Point2D.Double(0.14506599387527838, 49.45972388888889));
        quaiDuHavre.setAngleInfo(12);
        
        Terminal terminalAtlantique = _terminaux.getTerminal("Terminal de l'Atlantique");
        terminalAtlantique.setCoordInfo(new Point2D.Double(0.15342374999999994, 49.47570186666662));
        terminalAtlantique.setAngleInfo(-20);
        Terminal terminalEurope = _terminaux.getTerminal("Terminal de l'Europe");
        terminalEurope.setCoordInfo(new Point2D.Double(0.1721829275373846, 49.48035014285712));
        terminalEurope.setAngleInfo(-55);
        Terminal terminalOcean = _terminaux.getTerminal("Terminal de l'Océan");
        terminalOcean.setCoordInfo(new Point2D.Double(0.19052854677060133, 49.46961893333327));
        terminalOcean.setAngleInfo(70);
        Terminal terminalTNMSC = _terminaux.getTerminal("Terminal TNMSC");
        terminalTNMSC.setCoordInfo(new Point2D.Double(0.13443244710467694, 49.46469446666659));
        terminalTNMSC.setAngleInfo(13);
        Terminal terminalPorteOceane = _terminaux.getTerminal("Terminal Porte Océane");
        terminalPorteOceane.setCoordInfo(new Point2D.Double(0.15661426781737176, 49.46126613333329));
        terminalPorteOceane.setAngleInfo(13);
        Terminal terminalDeFrance = _terminaux.getTerminal("Terminal de France");
        terminalDeFrance.setCoordInfo(new Point2D.Double(0.16960646158129156, 49.45916786666662));
        terminalDeFrance.setAngleInfo(13);
        
        Forme bassinTheoDucrocq = _formes.getForme("Bassin Théophile Ducrocq");
        bassinTheoDucrocq.setCoordInfo(new Point2D.Double(0.12938955615653827, 49.47192213333333));
        bassinTheoDucrocq.setAngleInfo(13);
        Forme port2000 = _formes.getForme("Port 2000");
        port2000.setCoordInfo(new Point2D.Double(0.12908873886414254, 49.45885083333334));
        
        Forme manche = _formes.getForme("Manche");
        manche.setCoordInfo(new Point2D.Double(0.09435743318485523, 49.469477166666664));
        
        Forme darseOcean = _formes.getForme("Darse de l'Océan");
        darseOcean.setCoordInfo(new Point2D.Double(0.1849664114699332, 49.46783083333333));
        darseOcean.setAngleInfo(70);
    }
    
    public void lierQuaisTerminaux(Quai q, Terminal t, Forme f, TypeMarchandise tm) {
        q.ajoutTerminal(t);
        t.ajoutType(tm);
        t.setQuai(q);
        q.setZoneArrimage(f);
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
    
    public static void main(String[] args) {
        double coef = 84208.831372054;
//        System.out.println("TN " + (coef*0.020701805626342714));
//        System.out.println("Fr " + (coef*0.015229742251360576));
//        System.out.println("PO " + (coef*0.009720011094574427));
        Point2D p = new Point2D.Double(0.10482627572455382, 49.47732164317756);
        Point2D p2 = new Point2D.Double(0.10386507722789458, 49.47662569317756);
        //Point2D p3 = new Point2D.Double(0.16854303829939546, 49.47598018333332);
        double taille = p.distance(p2);
        System.out.println(taille);
        System.out.println(taille*coef);
        
    }
}
