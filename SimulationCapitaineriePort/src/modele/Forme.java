package modele;

import java.util.ArrayList;

/**
 *
 * @author Jérémy & Gary
 */
public class Forme {
    protected String _nom;
    protected ArrayList<Integer []> _coordonnees;

    /*
    ##### GETTER & SETTER #####
    */
    public String getNom() {
        return _nom;
    }

    public void setNom(String nom) {
        this._nom = nom;
    }

    public ArrayList<Integer []> getCoordonnees() {
        return _coordonnees;
    }

    public void setCoordonnees(ArrayList<Integer []> coordonnees) {
        this._coordonnees = coordonnees;
    }
}
