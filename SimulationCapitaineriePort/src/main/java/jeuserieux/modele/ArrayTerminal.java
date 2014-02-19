package jeuserieux.modele;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author gary
 */
public class ArrayTerminal extends ArrayList<Terminal>{
    /**
     * Cette fonction retrouve un Terminal à partir de son nom
     * @see modele.Forme
     * @param nom nom du Terminal à rechercher
     * @return Le Terminal si il est trouvé, null sinon
     */
    public Terminal getTerminal(String nom) {
        for(Terminal t:this) {
            if(t.getNom().equals(nom)) {
                return t;
            }
        }
        return null;
    }
    
    /**
     * Cette fonction tente de trouver la forme contenant le point en paramètre
     * @see modele.Terminal
     * @param p le point que le Terminal recherché doit contenir
     * @return le Terminal trouvé, sinon null
     */
    public Terminal getTerminal(Point2D p) {
        for(Terminal t:this) {
            if(t.getPath().contains(p)) {
                return t;
            }    
        }
        return null;
    }
}
