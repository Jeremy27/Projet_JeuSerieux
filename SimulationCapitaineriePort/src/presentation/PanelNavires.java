/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import modele.Navire;

/**
 *
 * @author jeremy
 */
public class PanelNavires extends PanelPerso {

    private Tableau _tableau;
    
    public PanelNavires(String nomPanel, String[] titres, PanelInfoForme panelInfo) {
        super(nomPanel);
        
        _tableau = new Tableau(titres, panelInfo);
        add(new JScrollPane(_tableau), BorderLayout.CENTER);
    }
    
    public void ajouterNavires(ArrayList<Navire> listeNavires) {
        for(Navire n : listeNavires)
            _tableau.ajouterNavire(n);
    }
    
    public void supprimerNavires(int temps) {
        _tableau.supprimerNavireArrives(temps);
    }
    
    public int getNbNaviresEnRetard() {
        return _tableau.getNbNaviresEnRetard();
    }
    
    public void remiseAZero() {
        _tableau.remiseAZero();
    }
    
    public void actualiser() {
        revalidate();
        repaint();
    }
}
