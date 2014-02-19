package jeuserieux.modele;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 *
 * @author gary
 */
public class ArrayForme extends ArrayList<Forme>{
    
    /**
     * Cette fonction retrouve une forme à partir de son nom
     * @see modele.Forme
     * @param nom nom de la forme à rechercher
     * @return la forme si elle est trouvée, null sinon
     */
    public Forme getForme(String nom) {
        for(Forme f:this) {
            if(f.getNom().equals(nom)) {
                return f;
            }
        }
        return null;
    }
    
    /**
     * Cette fonction tente de trouver la forme contenant le point en paramètre
     * @see modele.Forme
     * @param p le point que la forme recherchée doit contenir
     * @return la Forme trouvée, sinon null
     */
    public Forme getForme(Point2D p) {
        for(Forme forme:this) {
            if(forme.getPath().contains(p)) {
                return forme;
            }    
        }
        return null;
    }
}
