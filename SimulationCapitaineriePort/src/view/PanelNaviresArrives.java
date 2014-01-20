/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import modele.Navire;

/**
 *
 * @author jeremy
 */
public class PanelNaviresArrives extends Panel {

    private Tableau _tableau;
    
    
    public PanelNaviresArrives() {
        super("Navires arrivés");
        
        ArrayList<String> test = new ArrayList<>();
        test.add("Nom");
        test.add("Temps d'arrivée"); 
        test.add("baz");
        
        _tableau = new Tableau(test);
        add(_tableau, BorderLayout.CENTER);
    }
    
    public void ajouterNavire(Navire n) {
        _tableau.ajouterNavire(n);
    }
}
