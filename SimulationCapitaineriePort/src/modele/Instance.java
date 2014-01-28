/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import java.util.ArrayList;


/**
 *
 * @author Jérémy
 */
public class Instance {
    
    private ArrayList<Navire> _listeNavires;
    
    public Instance() {
        _listeNavires = new ArrayList<>();
    }
    
    public Instance(ArrayList<Navire> listeNavires) {
        _listeNavires = listeNavires;
    }
    
    public void ajouterNavire(Navire navire) {
        _listeNavires.add(navire);
    }
    
    public boolean supprimerNavire(Navire navire) {
        return _listeNavires.remove(navire);
    }
    
    public Navire supprimerNavire(int indiceNavire) {
        return _listeNavires.remove(indiceNavire);
    }
    
    public ArrayList<Navire> getNavires(int heureArrivee) {
        ArrayList<Navire> listeNavire = new ArrayList<>();
        for(Navire navire : _listeNavires)
            if(navire.getDateArrivee() == heureArrivee)
                listeNavire.add(navire);
        return listeNavire;
    }
    
}
