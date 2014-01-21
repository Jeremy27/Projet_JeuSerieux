/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import modele.Navire;

/**
 *
 * @author jeremy
 */
public class PanelNavires extends Panel {

    private Tableau _tableau;
    
    public PanelNavires(String nomPanel, String[] titres, PanelInfoForme panelInfo) {
        super(nomPanel);
        
        _tableau = new Tableau(titres, panelInfo);
        add(_tableau, BorderLayout.CENTER);
    }
    
    public void ajouterNavire(Navire n) {
        _tableau.ajouterNavire(n);
    }
}
