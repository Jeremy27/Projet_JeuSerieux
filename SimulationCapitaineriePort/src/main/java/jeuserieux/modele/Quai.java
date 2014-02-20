package jeuserieux.modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeShape;
import jeuserieux.presentation.PanelMap;

/**
 *
 * @author Jérémy & Gary
 */
public class Quai extends Forme{
    private Forme _zoneArrimage;
    private ArrayList<Navire> _naviresAQuai;
    private final ArrayList<Terminal> _terminaux;
    private double _longueurGeographique;
    private double _longueurMetre;
    
    public Quai(String nom, double longueur) {
        super(nom);
        _longueurGeographique       = longueur;
        _terminaux      = new ArrayList<>();
        _naviresAQuai   = new ArrayList<>();
        
        _typeForme      = TypeShape.QUAI;
    }
    
    public void setZoneArrimage(Forme f) {
        _zoneArrimage = f;
    }
    
    public Forme getZoneArrimage() {
        return _zoneArrimage;
    }
    
    public Quai(String nom, boolean fill, Color couleur, long id) {
        super(nom);
        _nom            = nom;
        _fill           = fill;
        _couleur        = couleur;
        _id             = id;
        _terminaux      = new ArrayList<>();
        _naviresAQuai   = new ArrayList<>();
        _coordonnees    = new ArrayList<>();
        _typeForme      = TypeShape.QUAI;
    }
    
    /**
     * Cette fonction retourne les informations relatives au Quai pour l'affichage dans un PanelInfoForme
     * @see presentation.PanelInfoForme
     * @return les informations formatées
     */
    public ArrayList<String> getDonneesFormates() {
        ArrayList<String> tabInfo = new ArrayList<>();
        
        tabInfo.add("Nom : " + _nom);
        tabInfo.add("Longueur : " + getLongueurMetre());
        tabInfo.add("Nb Navires : " + getNbNavireAQuai());
        tabInfo.add("Place restante : " + getPlaceRestante());
        for(Terminal t:_terminaux) {
            for(TypeMarchandise type:t.getTypesChargement()) {
                tabInfo.add("<html>"+ t.getNom() +"<br />Type :" + type.name() + "</html>");
            }
        }
                
        return tabInfo;
    }
    
    public double getPlaceRestante() {
        double longueurTotale = 0;
        for(Navire navireAQuai:_naviresAQuai) {
            longueurTotale+=navireAQuai.getLongueurMetre();
        }
        return _longueurMetre-longueurTotale;
    }
    
    public void ajoutTerminal(Terminal t) {
        _terminaux.add(t);
    }
    
    public double getLongueurGeographique() {
        return _longueurGeographique;
    }

    public void setLongueurGeographique(int longueur) {
        this._longueurGeographique = longueur;
    }
    
    public int getNbNavireAQuai() {
        return _naviresAQuai.size();
    }
    
    public ArrayList<Navire> getNaviresAQuai() {
        return _naviresAQuai;
    }
    
    public void setNavireAQuai(ArrayList<Navire> naviresAQuai) {
        _naviresAQuai = naviresAQuai;
    }
    
    public void ajouterNavireAQuai(Navire navire) {
        _naviresAQuai.add(navire);
    }
    
    public boolean supprimerNavireAQuai(Navire navire) {
        System.out.println(_naviresAQuai.size());
        return _naviresAQuai.remove(navire);
    }
    
    public Navire supprimerNavireAQuai(int indiceNavire) {
        return _naviresAQuai.remove(indiceNavire);
    }
    
    /**
     * Cette fonction détermine si le quai prend en charge le type de marchandise en paramètre
     * @param type le type de marchandise à tester
     * @return true si le Quai prend en charge ce type de marchandise
     */
    public boolean prendEnCharge(TypeMarchandise type) {
        for(Terminal terminal:_terminaux) {
            if(terminal.prendEnCharge(type)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cette fonction vérifie si le navire peut être pris en charge par le quai courant
     * @param n le navire voulant entrer dans le quai
     * @return true si il peut le prendre en charge
     */
    public boolean peutPrendreEnCharge(Navire n) {
        //vérifie le type
        if(prendEnCharge(n.getTypeMachandise())) {
            //Vérifie qu'il y a assez de place pour le prendre
            if(_naviresAQuai.isEmpty()) {
                if(_longueurMetre>=n.getLongueurMetre()) {
                    return true;
                }
            } else {
//                Collections.sort(_naviresAQuai);
//                int indice = 0;
                int longueurTotale = 0;
                for(Navire navireAQuai:_naviresAQuai) {
                    longueurTotale+=navireAQuai.getLongueurMetre();
//                    int position = navireAQuai.getPositionAQuai();
//                    if(position-indice>=n.getTempsPriseEnCharge()) {
//                        return true;
//                    }
//                    indice = position+navireAQuai.getTempsPriseEnCharge();
                }
                return longueurTotale<=_longueurMetre;
            }
                
        }
        return false;
    }
    
    /**
     * Cette fonction fait partir un navire du quai
     * @param n Navire à faire partir du quai
     */
    public void enleverNavire(Navire n) {
        _naviresAQuai.remove(n);
        n.setPositionAQuai(-1);
        n.setAssigneQuai(false);
        n.setHeurePriseEnCharge(-1);
    }

    /**
     * @return the _longueurMetre
     */
    public double getLongueurMetre() {
        return _longueurMetre;
    }

    /**
     * @param longueurMetre the _longueurMetre to set
     */
    public void setLongueurMetre(double longueurMetre) {
        this._longueurMetre = longueurMetre;
        _longueurGeographique = _longueurMetre/PanelMap.COEF_GEO_METRE;
    }
}
