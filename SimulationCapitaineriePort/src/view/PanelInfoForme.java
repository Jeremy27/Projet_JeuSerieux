/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

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
public class PanelInfoForme extends Panel {
    
    private ArrayList<String> _informations;

    /**
     * 
     * @param nomPanel
     * @param informations format de l'ArrayList : {"nom : valeur", "nom : valeur", ...}
     */
    public PanelInfoForme(String nomPanel) {
        super(nomPanel);
        _informations  = new ArrayList<>();
    }
    
    public PanelInfoForme(String nomPanel, ArrayList<String> informations) {
        super(nomPanel);
        _informations  = informations;
    }
    
    public void setInformations(ArrayList<String> informations) {
        _informations = informations;
    }
    
    public void majInformations() {
        
        JLabel label;
        JPanel panelGrid = new JPanel(new GridLayout(_informations.size(), 1));
        boolean pair = true;
        
        for (String info : _informations) {
            label = new JLabel(info);
            label.setOpaque(true);
            if(pair = !pair)
                label.setBackground(Color.lightGray);
            else
                label.setBackground(Color.gray);
            panelGrid.add(label);
        }
        add(panelGrid, BorderLayout.CENTER);
    }
}
