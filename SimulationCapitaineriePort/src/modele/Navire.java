package modele;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeNavire;
import modele.enumeration.TypeShape;
import modele.outils.ParamsNavire;

/**
 *
 * @author Jérémy & Gary
 */
public class Navire extends Forme {
    private TypeMarchandise _typeMachandise;
    private final double       _longueur;
    private final double       _largeur;
    private int             _dateArrivee;
    private int             _tempsPriseEnCharge;
    private TypeNavire      _typeNavire;
    private Point2D           _position;
    private int             _heurePriseEnCharge;
    

    public Navire(ParamsNavire params) {
        super(params.getNom());
        _typeNavire             = params.getTypeNavire();
        _typeMachandise         = params.getTypeMarchandise();
        _longueur               = params.getLongueur();
        _largeur                = params.getLargeur();
        _dateArrivee            = params.getDateArrivee();
        _tempsPriseEnCharge     = params.getTempsPriseEnCharge();
        _position               = params.getPosition();
        _typeForme              = TypeShape.NAVIRE;
    }
    
    public void constructionNavire() {
        _coordonnees = new ArrayList<>();
        double moitieLongueur = _longueur * 0.50;
        double moitielargeur = getLargeur() * 0.50;
        double x = _position.getX();
        double y = _position.getY();
        double longueurTete = _longueur * 0.20;
        
        double haut = y-moitielargeur;
        double bas = y+moitielargeur;
        
        //point arrière gauche
        _coordonnees.add(new Point2D.Double(x-moitieLongueur, haut));
        //avant gauche
        _coordonnees.add(new Point2D.Double(x+moitieLongueur-longueurTete, haut));
        //tête
        _coordonnees.add(new Point2D.Double(x+moitieLongueur, y));
        //avant droit
        _coordonnees.add(new Point2D.Double(x+moitieLongueur-longueurTete, bas));
        //arrière droit
        _coordonnees.add(new Point2D.Double(x-moitieLongueur, bas));
        makePathOriginale();
    }
    
    public Path2D boundingsPosition(Point2D p) {
        double moitieLongueur = _longueur * 0.50;
        double moitielargeur = getLargeur() * 0.50;
        double x = p.getX();
        double y = p.getY();
        double longueurTete = _longueur * 0.20;
        
        double haut = y-moitielargeur;
        double bas = y+moitielargeur;
        Path2D path = new Path2D.Double();
        //point arrière gauche
        path.moveTo(x-moitieLongueur, haut);
        //avant gauche
        path.lineTo(x+moitieLongueur-longueurTete, haut);
        //tête
        path.lineTo(x+moitieLongueur, y);
        //avant droit
        path.lineTo(x+moitieLongueur-longueurTete, bas);
        //arrière droit
        path.lineTo(x-moitieLongueur, bas);
        return path;
        
    }
    
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

    public double getLongueur() {
        return _longueur;
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

    public Point2D getPosition() {
        return _position;
    }

    public void setPosition(Point2D _position) {
        this._position = _position;
    }

    public int getHeurePriseEnCharge() {
        return _heurePriseEnCharge;
    }

    public void setHeurePriseEnCharge(int _heurePriseEnCharge) {
        this._heurePriseEnCharge = _heurePriseEnCharge;
    }
    
    public ArrayList<String> getDonneesFormates() {
        ArrayList<String> tabInfo = new ArrayList<>();
        
        tabInfo.add("Nom : " + _nom);
        tabInfo.add("Longueur : " + _longueur);
        tabInfo.add("Date d'arrivée : " + _dateArrivee);
        tabInfo.add("Temps à quai : " + _tempsPriseEnCharge);
        tabInfo.add("Marchandise : " + _typeMachandise.name());
        tabInfo.add("Type : " + _typeNavire.name());
//        tabInfo.add("Coordonnées : [" + _position.getX() + ", " + _position.getY() + ']');
                
        return tabInfo;
    }

    @Override
    public String toString() {
        return "Navire{" + "nom" + _nom + "typeMachandise=" + _typeMachandise + ", longueur=" + _longueur + ", dateArrivee=" 
                + _dateArrivee + ", tempsPriseEnCharge=" + _tempsPriseEnCharge + ", typeNavire=" + _typeNavire 
                + ", position[" + _position.getX() + ", " + _position.getY() + "]}";
    }

    /**
     * @return the _largeur
     */
    public double getLargeur() {
        return _largeur;
    }
    
    @Override
    public int getPriorite() {
        return 3;
    }
}
