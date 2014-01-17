package modele;

/**
 *
 * @author Jérémy & Gary
 */
public class Navire extends Forme {
    
    private TypeMarchandise  _typeMachandise;
    private int  _longueur;
    private int  _dateArrivee;
    private int  _tempsPriseEnCharge;
    private TypeNavire _typeNavire;
    
    

    public Navire(String nom, TypeNavire type, TypeMarchandise typeMarchandise, int longueur, int dateArrivee, int tempsPriseEnCharge) {
        super(nom);
        this._typeNavire            = type;
        this._typeMachandise        = typeMarchandise;
        this._longueur              = longueur;
        this._dateArrivee           = dateArrivee;
        this._tempsPriseEnCharge    = tempsPriseEnCharge;
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

    @Override
    public String toString() {
        return "Navire{" + "_typeMachandise=" + _typeMachandise + ", _longueur=" + _longueur + ", _dateArrivee=" + _dateArrivee + ", _tempsPriseEnCharge=" + _tempsPriseEnCharge + ", _typeNavire=" + _typeNavire + '}';
    }
}
