package jeuserieux.modele;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeNavire;
import jeuserieux.modele.enumeration.TypeShape;
import jeuserieux.modele.outils.ParamsNavire;
import jeuserieux.presentation.PanelMap;

/**
 *
 * @author Jérémy & Gary
 */
public class Navire extends Forme {

    private TypeMarchandise _typeMachandise;
    private final double _longueurGeographique;
    private final double _largeurGeographique;
    private final double _longueurMetres;
    private final double _largeurMetres;
    private int _dateArrivee;
    private int _tempsPriseEnCharge;
    private TypeNavire _typeNavire;
    private Point2D _position;
    private int _heurePriseEnCharge;
    private double _angle;
    private boolean _assigneQuai;

    public Navire(ParamsNavire params) {
        super(params.getNom());
        _typeNavire = params.getTypeNavire();
        _typeMachandise = params.getTypeMarchandise();
        _longueurMetres = params.getLongueur();
        _largeurMetres = params.getLargeur();
        _longueurGeographique = _longueurMetres/PanelMap.COEF_GEO_METRE;
        _largeurGeographique = _largeurMetres/PanelMap.COEF_GEO_METRE;
        _dateArrivee = params.getDateArrivee();
        _tempsPriseEnCharge = params.getTempsPriseEnCharge();
        _position = params.getPosition();
        _typeForme = TypeShape.NAVIRE;
        _fill = true;
        _angle = 0;
        _assigneQuai = false;
    }

    /**
     * Cette fonction construit la bounding box du navire
     */
    public void constructionNavire() {
        _coordonnees = new ArrayList<>();
        double moitieLongueur = _longueurGeographique * 0.50;
        double moitielargeur = _largeurGeographique * 0.50;
        double x = _position.getX();
        double y = _position.getY();
        double longueurTete = _longueurGeographique * 0.20;

        double haut = y - moitielargeur;
        double bas = y + moitielargeur;

        //point arrière gauche
        _coordonnees.add(new Point2D.Double(x - moitieLongueur, haut));
        //avant gauche
        _coordonnees.add(new Point2D.Double(x + moitieLongueur - longueurTete, haut));
        //tête
        _coordonnees.add(new Point2D.Double(x + moitieLongueur, y));
        //avant droit
        _coordonnees.add(new Point2D.Double(x + moitieLongueur - longueurTete, bas));
        //arrière droit
        _coordonnees.add(new Point2D.Double(x - moitieLongueur, bas));
        makePathOriginale();
    }
    
    /**
     * Cette fonction retourne le point de tête du navire
     * @return le point de tête du navire
     */
    public Point2D getTete() {
        return _coordonnees.get(2);
    }

    /**
     * Construit une Path2D avec comme position du bateau le point en paramètre, ceci afin de tester si
     * le bateau peut se déplacer à cet endroit dans le pathfinding
     * @see metier.DeplacementBateaux
     * @param p nouveau point du bateau
     * @return la Path2D correspondant à la nouvelle position
     */
    public Path2D boundingsPosition(Point2D p) {
        double moitieLongueur = _longueurGeographique * 0.50;
        double moitielargeur = _largeurGeographique * 0.50;
        double x = p.getX();
        double y = p.getY();
        double longueurTete = _longueurGeographique * 0.20;

        double haut = y - moitielargeur;
        double bas = y + moitielargeur;
        Path2D path = new Path2D.Double();
        //point arrière gauche
        path.moveTo(x - moitieLongueur, haut);
        //avant gauche
        path.lineTo(x + moitieLongueur - longueurTete, haut);
        //tête
        path.lineTo(x + moitieLongueur, y);
        //avant droit
        path.lineTo(x + moitieLongueur - longueurTete, bas);
        //arrière droit
        path.lineTo(x - moitieLongueur, bas);
        return path;
    }
    
    /**
     * Construit la path par rapport aux données actuelles de l'application
     * Prend en compte l'angle de rotation du navire
     * @see java.awt.geom.Path2D
     * @param map contient les informations indispensable au calcul de la path
     * @return la path2D construite
     */
    @Override
    public Path2D getPath(HashMap<String, Double> map) {
        Path2D path = super.getPath(map);
        if(path!=null) {
            Rectangle bounds = path.getBounds();
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(_angle), bounds.width / 2.0 + bounds.x, bounds.height / 2.0 + bounds.y);
            path = new Path2D.Double(path.createTransformedShape(transform));
        }
        _path = path;
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
        return _longueurGeographique;
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

    /**
     * Cette fonction retourne les informations relatives au navire pour affichage dans un PanelInfoForme
     * @see presentation.PanelInfoForme
     * @return Les informations relatives au navire
     */
    public ArrayList<String> getDonneesFormates() {
        ArrayList<String> tabInfo = new ArrayList<>();

        tabInfo.add("Nom : " + _nom);
        tabInfo.add("Longueur : " + _longueurGeographique);
        tabInfo.add("Date d'arrivée : " + _dateArrivee);
        tabInfo.add("Temps à quai : " + _tempsPriseEnCharge);
        tabInfo.add("Marchandise : " + _typeMachandise.name());
        tabInfo.add("Type : " + _typeNavire.name());
//        tabInfo.add("Coordonnées : [" + _position.getX() + ", " + _position.getY() + ']');

        return tabInfo;
    }

    @Override
    public String toString() {
        return "Navire{" + "nom" + _nom + "typeMachandise=" + _typeMachandise + ", longueur=" + _longueurGeographique + ", dateArrivee="
                + _dateArrivee + ", tempsPriseEnCharge=" + _tempsPriseEnCharge + ", typeNavire=" + _typeNavire
                + ", position[" + _position.getX() + ", " + _position.getY() + "]}";
    }

    /**
     * @return the _largeurGeographique
     */
    public double getLargeurGeographique() {
        return _largeurGeographique;
    }

    /**
     * @return the _angle
     */
    public double getAngle() {
        return _angle;
    }

    /**
     * @param _angle the _angle to set
     */
    public void setAngle(double _angle) {
        this._angle = _angle;
    }

    /**
     * @return the _assigneQuai
     */
    public boolean estAssigneQuai() {
        return _assigneQuai;
    }

    /**
     * @param _assigneQuai the _assigneQuai to set
     */
    public void setAssigneQuai(boolean _assigneQuai) {
        this._assigneQuai = _assigneQuai;
    }
    
    
}
