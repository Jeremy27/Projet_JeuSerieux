package jeuserieux.modele;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import jeuserieux.modele.enumeration.TypeShape;

/**
 * @author Jérémy & Gary
 */
public class Forme {
    protected String _nom;
    protected ArrayList<Point2D> _coordonnees;
    protected Path2D _path;
    private final Path2D _pathOriginal;
    protected boolean _fill = false;
    protected Color _couleur = Color.GRAY;
    protected long _id;
    protected TypeShape _typeForme;
    protected Point2D _coordInfo;
    protected boolean _visible=true;
    private double _angleInfo;
    private int _tailleBreakInfo=100;
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("nom ");
        s.append(_nom);
        s.append(" id ");
        s.append(_id);
        s.append(" type ");
        s.append(_typeForme.name());
        return s.toString();
    }
    
    public Forme(String nom) {
        _pathOriginal = new Path2D.Double();
        _nom = nom;
        _coordonnees = new ArrayList<>();
    }
    
    public Forme(String nom, boolean fill, Color couleur, long id, TypeShape typeForme) {
        _pathOriginal = new Path2D.Double();
        _coordonnees = new ArrayList<>();
        _nom = nom;
        _fill = fill;
        _couleur = couleur;
        _id = id;
        _typeForme = typeForme;
    }
    
    public Point2D getCoordInfo() {
        return _coordInfo;
    }
    
    public void setCoordInfo(Point2D p) {
        _coordInfo = p;
    }
    
    public Point2D getCentre() {
        Rectangle2D r = _pathOriginal.getBounds2D();
        return new Point2D.Double(r.getCenterX(), r.getCenterY());
    }

    /*
    ##### GETTER & SETTER #####
     */
    public long getId() {
        return _id;
    }
    
    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }
    
    public ArrayList<Point2D> getCoordonnees() {
        return _coordonnees;
    }
    
    /**
     * Cette fonction corrige les coordonnées (elle les annule afin de faciliter les traitements)
     * @param correctionX correction à apporter à l'axe x
     * @param correctionY correction à apporter à l'axe y
     */
    public void corrigerCoordonnees(double correctionX, double correctionY) {
        for(Point2D p:_coordonnees) {
            p.setLocation(p.getX()-correctionX, p.getY()-correctionY);
        }
    }
    
    /**
     * Construit la path par rapport aux données actuelles de l'application
     * @see java.awt.geom.Path2D
     * @param map contient les informations indispensable au calcul de la path
     * @return la path2D construite
     */
    public Path2D getPath(HashMap<String, Double> map) {
        
        if(contenuDansMap(map)) {
            double coefX = map.get("coefX");
            double coefY = map.get("coefY");
            double hauteurPanel = map.get("hauteurPanel");
            double gauche, droite, bas, haut;
            gauche = map.get("gauche");
            droite = map.get("droite");
            bas = map.get("bas");
            haut = map.get("haut");

            Path2D path = new Path2D.Double();
            boolean premier = true;
            for(Point2D p:_coordonnees) {
                double x = p.getX();
                double y = p.getY();
                x = x-gauche;
                y = y-haut;
                //vérifie si le point est à dessiner
                x = (x*coefX);
                y = hauteurPanel - (y*coefY);
                if(premier) {
                    path.moveTo(x, y);
                    premier = false;
                } else {
                    path.lineTo(x, y);
                }
            }
            //path.closePath();
            _path = path;
            return path;
        } else {
            return null;
        }
            
    }
    
    /**
     * Cette fonction calcule si la forme est contenue dans la carte
     * @param map paramètres indispensables aux calculs
     * @return true si la forme est contenu dans la carte affichée dans l'application
     */
    private boolean contenuDansMap(HashMap<String, Double> map) {
        double gauche, droite, bas, haut;
        gauche = map.get("gauche");
        droite = map.get("droite");
        bas = map.get("bas");
        haut = map.get("haut");
        for(Point2D p:_coordonnees) {
            double x = p.getX();
            double y = p.getY();
            if((x>=gauche || x<=droite) && (y>=haut || y<=bas)) {
                return true;
            }
        }
        return false;
    }
    
    public Path2D getPath() {
        return _path;
    }

    public boolean isFill() {
        return _fill;
    }

    public Color getCouleur() {
        return _couleur;
    }

    public void setFill(boolean _fill) {
        this._fill = _fill;
    }

    public void setCouleur(Color _couleur) {
        this._couleur = _couleur;
    }
    
    /**
     * Ajout une coordonnée à la forme
     * @param p un point de coordonnées
     */
    public void ajoutCoordonnee(Point2D p) {
        _coordonnees.add(p);
    }
    
    /**
     * Cette fonction construit une Path2D à partir des coordonnées géographiques (latitude/longitude)
     * Sert au pathfinding
     * @see metier.DeplacementBateaux
     */
    public void makePathOriginale() {
        boolean premier = true;
        for(Point2D p:_coordonnees) {
            if(premier) {
                getPathOriginal().moveTo(p.getX(), p.getY());
                premier = false;
            } else {
                getPathOriginal().lineTo(p.getX(), p.getY());
            }
        }
    }

    /**
     * @return the _typeForme
     */
    public TypeShape getTypeForme() {
        return _typeForme;
    }

    /**
     * @return the _pathOriginal
     */
    public Path2D getPathOriginal() {
        return _pathOriginal;
    }

    /**
     * @return the _angleInfo
     */
    public double getAngleInfo() {
        return _angleInfo;
    }

    /**
     * @param _angleInfo the _angleInfo to set
     */
    public void setAngleInfo(double _angleInfo) {
        this._angleInfo = _angleInfo;
    }

    /**
     * @return the _tailleBreakInfo
     */
    public int getTailleBreakInfo() {
        return _tailleBreakInfo;
    }

    /**
     * @param _tailleBreakInfo the _tailleBreakInfo to set
     */
    public void setTailleBreakInfo(int _tailleBreakInfo) {
        this._tailleBreakInfo = _tailleBreakInfo;
    }

    /**
     * @return the _visible
     */
    public boolean estVisible() {
        return _visible;
    }

    /**
     * @param _visible the _visible to set
     */
    public void setVisible(boolean _visible) {
        this._visible = _visible;
    }
}
