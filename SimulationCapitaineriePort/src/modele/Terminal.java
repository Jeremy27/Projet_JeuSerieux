/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.util.ArrayList;

/**
 *
 * @author jeremy
 */
public class Terminal {
    private int _longueur;
    private ArrayList<TypeMarchandise> _typeChargement;
    
    public Terminal(int longueur) {
        _typeChargement = new ArrayList<>();
        _longueur = longueur;
    }

    public int getLongueur() {
        return _longueur;
    }

    public void setLongueur(int _longueur) {
        this._longueur = _longueur;
    }
    
}
