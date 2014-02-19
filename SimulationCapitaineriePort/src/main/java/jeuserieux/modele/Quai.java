package jeuserieux.modele;

import java.awt.Color;
import java.util.ArrayList;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeShape;

/**
 *
 * @author Jérémy & Gary
 */
public class Quai extends Forme{
    private Forme _zoneArrimage;
    private ArrayList<Navire> _naviresAQuai;
    private final ArrayList<Terminal> _terminaux;
    private int _longueur;
    
    public Quai(String nom, int longueur) {
        super(nom);
        _longueur       = longueur;
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
    
    public Quai(String nom, boolean fill, Color couleur, long id, int longueur) {
        super(nom);
        _nom            = nom;
        _fill           = fill;
        _couleur        = couleur;
        
        _id             = id;
        _longueur       = longueur;
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
        tabInfo.add("Longueur : " + _longueur);
        for(Terminal t:_terminaux) {
            for(TypeMarchandise type:t.getTypesChargement()) {
                tabInfo.add(type.name());
            }
        }
                
        return tabInfo;
    }
    
    public void ajoutTerminal(Terminal t) {
        _terminaux.add(t);
    }
    
    public int getLongueur() {
        return _longueur;
    }

    public void setLongueur(int _longueur) {
        this._longueur = _longueur;
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
    @Override
    public int getPriorite() {
        return 2;
    }
}
