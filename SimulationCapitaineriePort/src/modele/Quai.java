package modele;

import java.awt.Color;
import java.util.ArrayList;
import modele.enumeration.TypeMarchandise;

/**
 *
 * @author Jérémy & Gary
 */
public class Quai extends Forme{
    private ArrayList<Navire> _naviresAQuai;
    private ArrayList<Terminal> _terminaux;
    private int _longueur;
    
    public Quai(String nom, int longueur) {
        super(nom);
        _longueur       = longueur;
        _terminaux      = new ArrayList<>();
        _naviresAQuai   = new ArrayList<>();
    }
    
    public Quai(String nom, boolean fill, Color couleur, int id, int longueur) {
        super(nom);
        _nom            = nom;
        _fill           = fill;
        _couleur        = couleur;
        _id             = id;
        _longueur       = longueur;
        _terminaux      = new ArrayList<>();
        _naviresAQuai   = new ArrayList<>();
        _coordonnees    = new ArrayList<>();
    }
    
    public ArrayList<String> getDonneesFormates() {
        ArrayList<String> tabInfo = new ArrayList<>();
        
        tabInfo.add("Nom : " + _nom);
        tabInfo.add("Longueur : " + _longueur);
        String typesMarchandise = "";
        for(Terminal t:_terminaux) {
            for(TypeMarchandise type:t.getTypesChargement()) {
                typesMarchandise += " " + type.name();
            }
        }
                
        return tabInfo;
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
}
