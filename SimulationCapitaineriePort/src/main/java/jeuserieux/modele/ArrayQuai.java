/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.modele;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import jeuserieux.modele.enumeration.TypeMarchandise;

/**
 *
 * @author brokep
 */
public class ArrayQuai extends ArrayList<Quai>{
    /**
     * Cette fonction retrouve un Quai à partir de son nom
     * @see modele.Forme
     * @param nom nom du Quai à rechercher
     * @return Le Quai si il est trouvé, null sinon
     */
    public Quai getQuai(String nom) {
        for(Quai q:this) {
            if(q.getNom().equals(nom)) {
                return q;
            }
        }
        return null;
    }
    
    /**
     * Cette fonction tente de trouver la forme contenant le point en paramètre
     * @see modele.Quai
     * @param p le point que le Quai recherché doit contenir
     * @return le Quai trouvé, sinon null
     */
    public Quai getQuai(Point2D p) {
        for(Quai q:this) {
            if(q.getPath().contains(p)) {
                return q;
            }    
        }
        return null;
    }
}
