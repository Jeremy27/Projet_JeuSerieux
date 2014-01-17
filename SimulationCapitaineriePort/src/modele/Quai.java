package modele;

import java.util.ArrayList;

/**
 *
 * @author Jérémy & Gary
 */
public class Quai {
    private ArrayList<Terminal> _terminaux;
    private String _nom;
    
    public Quai(String nom) {
        _terminaux = new ArrayList<>();
        _nom = nom;
    }

    public String getNom() {
        return _nom;
    }

    public void setNom(String _nom) {
        this._nom = _nom;
    }
    
    
}
