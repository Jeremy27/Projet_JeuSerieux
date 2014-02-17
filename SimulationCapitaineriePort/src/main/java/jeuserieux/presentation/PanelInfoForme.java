/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jérémy
 */
public class PanelInfoForme extends PanelPerso {
    
    private ArrayList<String> _informations;
    
    public PanelInfoForme(String nomPanel) {
        super(nomPanel);
        _informations  = new ArrayList<>();
    }
    
    /**
     * 
     * @param nomPanel
     * @param informations format de l'ArrayList : {"nom : valeur", "nom : valeur", ...}
     */
    public PanelInfoForme(String nomPanel, ArrayList<String> informations) {
        super(nomPanel);
        _informations  = informations;
    }
    
    public void setInformations(ArrayList<String> informations) {
        _informations = informations;
    }
    
    public void majInformations() {
        
        if(_informations == null || _informations.isEmpty()) {
            _informations = new ArrayList<>();
            _informations.add("Aucune information");
        }
        
        JLabel label;
        JPanel panelGrid = new JPanel(new GridLayout(_informations.size(), 1));
        boolean pair = true;
        
        for (String info : _informations) {
            label = new JLabel(info);
            label.setOpaque(true);
            if(pair = !pair)
                label.setBackground(COULEUR_PANEL1);
            else
                label.setBackground(COULEUR_PANEL2);
            label.setForeground(Color.white);
            panelGrid.add(label);
        }
        add(panelGrid, BorderLayout.CENTER);
        this.validate();
    }
    
    public void remiseAZero() {
        _informations = null;
        setNomPanel("Sélection");
        majInformations();
    }
}
