/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.awt.Color;
import modele.enumeration.TypeMarchandise;
import java.util.ArrayList;
import modele.enumeration.TypeShape;

/**
 *
 * @author jeremy
 */
public class Terminal extends Forme {
    
    private int _longueur;
    private ArrayList<TypeMarchandise> _typeChargement;
    
    public Terminal(String nom, int longueur) {
        super(nom);
        _typeChargement = new ArrayList<>();
        
    }
    
    public Terminal(String nom, boolean fill, Color couleur, long id, int longueur) {
        super(nom);
        _fill = fill;
        _couleur = couleur;
        _id = id;
        _longueur = longueur;
        _typeChargement = new ArrayList<>();
        _typeForme = TypeShape.TERMINAL;
    }
    
    public void ajoutType(TypeMarchandise t) {
        _typeChargement.add(t);
    }
    
    public ArrayList<TypeMarchandise> getTypesChargement() {
        return _typeChargement;
    }
    
    public boolean prendEnCharge(TypeMarchandise type) {
        for(TypeMarchandise t:_typeChargement) {
            if(type==t) {
                return true;
            }
        }
        return false;
    }
}
