package modele;

import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeNavire;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Jérémy & Gary
 */
public class Navire extends Forme {
    
    private TypeMarchandise _typeMachandise;
    private int             _longueur;
    private int             _dateArrivee;
    private int             _tempsPriseEnCharge;
    private TypeNavire      _typeNavire;
    private Point           _position;
    

    public Navire(String nom, TypeNavire type, TypeMarchandise typeMarchandise, int longueur, int dateArrivee, int tempsPriseEnCharge, Point position) {
        super(nom);
        this._typeNavire            = type;
        this._typeMachandise        = typeMarchandise;
        this._longueur              = longueur;
        this._dateArrivee           = dateArrivee;
        this._tempsPriseEnCharge    = tempsPriseEnCharge;
        this._position              = position;
    }
    
    /*
    ##### GETTER & SETTER #####
    */
    public TypeNavire getType() {
        return _typeNavire;
    }

    public void setType(TypeNavire type) {
        this._typeNavire = type;
    }

    public TypeMarchandise getTypeMachandise() {
        return _typeMachandise;
    }

    public void setTypeMachandise(TypeMarchandise typeMachandise) {
        this._typeMachandise = typeMachandise;
    }

    public int getLongueur() {
        return _longueur;
    }

    public void setLongueur(int longueur) {
        this._longueur = longueur;
    }

    public int getDateArrivee() {
        return _dateArrivee;
    }

    public void setDateArrivee(int dateArrivee) {
        this._dateArrivee = dateArrivee;
    }

    public int getTempsPriseEnCharge() {
        return _tempsPriseEnCharge;
    }

    public void setTempsPriseEnCharge(int tempsPriseEnCharge) {
        this._tempsPriseEnCharge = tempsPriseEnCharge;
    }

    public Point getPosition() {
        return _position;
    }

    public void setPosition(Point _position) {
        this._position = _position;
    }
    
    public ArrayList<String> getDonneesFormates() {
        ArrayList<String> tabInfo = new ArrayList<>();
        
        tabInfo.add("Nom : " + _nom);
        tabInfo.add("Longueur : " + _longueur);
        tabInfo.add("Date d'arrivée : " + _dateArrivee);
        tabInfo.add("Temps à quai : " + _tempsPriseEnCharge);
        tabInfo.add("Marchandise : " + _typeMachandise.name());
        tabInfo.add("Type : " + _typeNavire.name());
        tabInfo.add("Coordonnées : [" + _position.getX() + ", " + _position.getY() + ']');
                
        return tabInfo;
    }

    @Override
    public String toString() {
        return "Navire{" + "nom" + _nom + "typeMachandise=" + _typeMachandise + ", longueur=" + _longueur + ", dateArrivee=" 
                + _dateArrivee + ", tempsPriseEnCharge=" + _tempsPriseEnCharge + ", typeNavire=" + _typeNavire 
                + "position[" + _position.getX() + ", " + _position.getY() + "]}";
    }
}
