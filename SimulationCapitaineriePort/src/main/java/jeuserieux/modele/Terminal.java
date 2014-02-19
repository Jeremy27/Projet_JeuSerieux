/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.modele;

import java.awt.Color;
import jeuserieux.modele.enumeration.TypeMarchandise;
import java.util.ArrayList;
import jeuserieux.modele.enumeration.TypeShape;

/**
 *
 * @author jeremy
 */
public class Terminal extends Forme {
    private int _longueur;
    private Quai _quaiLie;
    private final ArrayList<TypeMarchandise> _typeChargement;
    
    public Terminal(String nom, int longueur) {
        super(nom);
        _typeChargement = new ArrayList<>();
        _typeForme = TypeShape.TERMINAL;
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
    
    public void setQuai(Quai q) {
        _quaiLie = q;
    }
    
    public Quai getQuai() {
        return _quaiLie;
    }
    
    public void ajoutType(TypeMarchandise t) {
        _typeChargement.add(t);
    }
    
    public ArrayList<TypeMarchandise> getTypesChargement() {
        return _typeChargement;
    }
    
    /**
     * Cette fonction détermine si le terminal prend en charge le type de marchandise en paramètre
     * @param type le type de marchandise à tester
     * @return true si le Terminal prend en charge ce type de marchandise
     */
    public boolean prendEnCharge(TypeMarchandise type) {
        for(TypeMarchandise t:_typeChargement) {
            if(type==t) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Cette fonction retourne les informations relatives au Terminal pour l'affichage dans un PanelInfoForme
     * @see presentation.PanelInfoForme
     * @return les informations formatées
     */
    public ArrayList<String> getDonneesFormates() {
        ArrayList<String> tabInfo = new ArrayList<>();
        
        tabInfo.add("Nom : " + _nom);
        tabInfo.add("Longueur : " + _longueur);
        for(TypeMarchandise t:_typeChargement) {
            tabInfo.add("Type de marchandise: " + t.name());
        }
        
                
        return tabInfo;
    }
}
