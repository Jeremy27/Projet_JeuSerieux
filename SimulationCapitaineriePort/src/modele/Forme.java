package modele;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import modele.enumeration.TypeShape;

/**
 *
 * @author Jérémy & Gary
 */
public class Forme {
    protected String _nom;
    protected ArrayList<Point2D> _coordonnees;
    protected Path2D _path;
    protected boolean _fill = false;
    protected Color _couleur = Color.GRAY;
    protected long _id;
    protected TypeShape _typeForme;
    
    public Forme(String nom) {
        _nom = nom;
    }
    
    public Forme(String nom, boolean fill, Color couleur, long id, TypeShape typeForme) {
        _coordonnees = new ArrayList<>();
        _nom = nom;
        _fill = fill;
        _couleur = couleur;
        _id = id;
        _typeForme = typeForme;
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
    
    public void corrigerCoordonnees(double correctionX, double correctionY) {
        for(Point2D p:_coordonnees) {
            p.setLocation(p.getX()-correctionX, p.getY()-correctionY);
        }
    }
    
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
            _path = path;
            return path;
        } else {
            return null;
        }
            
    }
    
    public boolean contenuDansMap(HashMap<String, Double> map) {
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
    
    public void ajoutCoordonnee(Point2D p) {
        _coordonnees.add(p);
    }
}
