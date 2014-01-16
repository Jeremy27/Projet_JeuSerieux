package modele;

/**
 *
 * @author Jérémy & Gary
 */
public class Navire extends Forme {
    
    private int  _type;
    private int  _typeMachandise;
    private int  _longueur;
    private int  _dateArrivee;
    private int  _tempsPriseEnCharge;
    

    public Navire(int type, int typeMarchandise, int longueur, int dateArrivee, int tempsPriseEnCharge) {
        this._type                  = type;
        this._typeMachandise        = typeMarchandise;
        this._longueur              = longueur;
        this._dateArrivee           = dateArrivee;
        this._tempsPriseEnCharge    = tempsPriseEnCharge;
    }
    
    /*
    ##### GETTER & SETTER #####
    */
    public int getType() {
        return _type;
    }

    public void setType(int _type) {
        this._type = _type;
    }

    public int getTypeMachandise() {
        return _typeMachandise;
    }

    public void setTypeMachandise(int typeMachandise) {
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
        return "Navire{nom" + _nom + "type=" + _type + ", typeChargement=" + _typeMachandise + ", longueur=" + _longueur 
                + ", dateArrivee=" + _dateArrivee + ", tempsPriseEnCharge=" + _tempsPriseEnCharge + ", coordonnées=" + _coordonnees + '}';
    }
    
    
}
