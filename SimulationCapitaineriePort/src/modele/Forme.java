package modele;

import java.awt.Color;
import java.awt.geom.Path2D;

/**
 *
 * @author Jérémy & Gary
 */
public class Forme {
    protected String _nom;
    protected Path2D _coordonnees;
    protected boolean _fill = false;
    protected Color _couleur = Color.GRAY;
    
    public Forme(String nom) {
        _nom = nom;
    }
    
    public Forme(String nom, Path2D path, boolean fill, Color couleur) {
        _nom = nom;
        _coordonnees = path;
        _fill = fill;
        _couleur = couleur;
    }

    /*
    ##### GETTER & SETTER #####
    */
    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public Path2D getCoordonnees() {
        return _coordonnees;
    }

    public void setCoordonnees(Path2D coordonnees) {
        this._coordonnees = coordonnees;
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
    
    
}
