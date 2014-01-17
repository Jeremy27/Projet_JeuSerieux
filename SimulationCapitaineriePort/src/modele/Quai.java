package modele;

import java.util.ArrayList;

/**
 *
 * @author Jérémy & Gary
 */
public class Quai extends Forme{
    private ArrayList<Terminal> _terminaux;
    
    public Quai(String nom) {
        super(nom);
        _terminaux = new ArrayList<>();
    }
    
}
