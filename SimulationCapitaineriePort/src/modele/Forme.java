package modele;

import java.awt.Color;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

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
    
    public Forme(String nom) {
        _nom = nom;
    }
    
    public Forme(String nom, boolean fill, Color couleur, long id) {
        _coordonnees = new ArrayList<>();
        _nom = nom;
        _fill = fill;
        _couleur = couleur;
        _id = id;
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
    
    public Path2D getPath(int hauteurPanel, double coefX, double coefY) {
        Path2D path = new Path2D.Double();
        boolean premier = true;
        for(Point2D p:_coordonnees) {
            double x = (p.getX()*coefX);
            double y = hauteurPanel - (p.getY()*coefY);
            //System.out.println(y);
            if(premier) {
                path.moveTo(x, y);
                premier = false;
            } else {
                path.lineTo(x, y);
            }
        }
        _path = path;
        return path;
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
