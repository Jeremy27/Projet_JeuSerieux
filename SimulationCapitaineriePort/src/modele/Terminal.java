/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import modele.enumeration.TypeMarchandise;
import java.util.ArrayList;

/**
 *
 * @author jeremy
 */
public class Terminal extends Forme {
    
    private ArrayList<TypeMarchandise> _typeChargement;
    
    public Terminal(String nom, int longueur) {
        super(nom);
        _typeChargement = new ArrayList<>();
        
    }
    
    public void ajoutType(TypeMarchandise t) {
        _typeChargement.add(t);
    }
    
    public ArrayList<TypeMarchandise> getTypesChargement() {
        return _typeChargement;
    }
}
