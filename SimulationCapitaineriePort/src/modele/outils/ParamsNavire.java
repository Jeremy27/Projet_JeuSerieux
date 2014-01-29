package modele.outils;

import java.awt.Point;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeNavire;

public class ParamsNavire {
    private String _nom;
    private TypeNavire _typeNavire;
    private TypeMarchandise _typeMarchandise;
    private int _dateArrivee;
    private int _tempsPriseEnCharge;
    private Point _position;
    private int _datePriseEnCharge;
    private int _longueur;
    private int _largeur;
    
    public ParamsNavire() {}

    /**
     * @return the _nom
     */
    public String getNom() {
        return _nom;
    }

    /**
     * @param _nom the _nom to set
     */
    public void setNom(String _nom) {
        this._nom = _nom;
    }

    /**
     * @return the _typeNavire
     */
    public TypeNavire getTypeNavire() {
        return _typeNavire;
    }

    /**
     * @param _typeNavire the _typeNavire to set
     */
    public void setTypeNavire(TypeNavire _typeNavire) {
        this._typeNavire = _typeNavire;
    }

    /**
     * @return the _typeMarchandise
     */
    public TypeMarchandise getTypeMarchandise() {
        return _typeMarchandise;
    }

    /**
     * @param _typeMarchandise the _typeMarchandise to set
     */
    public void setTypeMarchandise(TypeMarchandise _typeMarchandise) {
        this._typeMarchandise = _typeMarchandise;
    }

    /**
     * @return the _dateArrivee
     */
    public int getDateArrivee() {
        return _dateArrivee;
    }

    /**
     * @param _dateArrivee the _dateArrivee to set
     */
    public void setDateArrivee(int _dateArrivee) {
        this._dateArrivee = _dateArrivee;
    }

    /**
     * @return the _tempsPriseEnCharge
     */
    public int getTempsPriseEnCharge() {
        return _tempsPriseEnCharge;
    }

    /**
     * @param _tempsPriseEnCharge the _tempsPriseEnCharge to set
     */
    public void setTempsPriseEnCharge(int _tempsPriseEnCharge) {
        this._tempsPriseEnCharge = _tempsPriseEnCharge;
    }

    /**
     * @return the _position
     */
    public Point getPosition() {
        return _position;
    }

    /**
     * @param _position the _position to set
     */
    public void setPosition(Point _position) {
        this._position = _position;
    }

    /**
     * @return the _datePriseEnCharge
     */
    public int getDatePriseEnCharge() {
        return _datePriseEnCharge;
    }

    /**
     * @param _datePriseEnCharge the _datePriseEnCharge to set
     */
    public void setDatePriseEnCharge(int _datePriseEnCharge) {
        this._datePriseEnCharge = _datePriseEnCharge;
    }

    /**
     * @return the _longueur
     */
    public int getLongueur() {
        return _longueur;
    }

    /**
     * @param _longueur the _longueur to set
     */
    public void setLongueur(int _longueur) {
        this._longueur = _longueur;
    }

    /**
     * @return the _largeur
     */
    public int getLargeur() {
        return _largeur;
    }

    /**
     * @param _largeur the _largeur to set
     */
    public void setLargeur(int _largeur) {
        this._largeur = _largeur;
    }
}