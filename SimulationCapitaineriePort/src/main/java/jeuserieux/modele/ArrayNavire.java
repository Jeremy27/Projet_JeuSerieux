package jeuserieux.modele;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author gary
 */
public class ArrayNavire extends ArrayList<Navire>{
    /**
     * Cette fonction retrouve un Navire à partir de son nom
     * @see modele.Forme
     * @param nom nom du Navire à rechercher
     * @return Le Navire si il est trouvé, null sinon
     */
    public Navire getNavire(String nom) {
        for(Navire n:this) {
            if(n.getNom().equals(nom)) {
                return n;
            }
        }
        return null;
    }
    
    /**
     * Cette fonction tente de trouver la forme contenant le point en paramètre
     * @see modele.Navire
     * @param p le point que le Navire recherché doit contenir
     * @return le Navire trouvé, sinon null
     */
    public Navire getNavire(Point2D p) {
        for(Navire n:this) {
            if(n.getPath().contains(p)) {
                return n;
            }    
        }
        return null;
    }
}
