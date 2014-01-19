/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jérémy
 */
public class PanelInfoForme extends JPanel {
    
    private JLabel              _jlNomPanel;
    private ArrayList<String>   _informations;

    /**
     * 
     * @param nomPanel
     * @param informations format de l'ArrayList : {"nom : valeur", "nom : valeur", ...}
     */
    public PanelInfoForme(String nomPanel, ArrayList<String> informations) {
        setPreferredSize(new Dimension(250, 500));
        setLayout(new BorderLayout());
        
        initialisationLabelNomPanel(nomPanel);
        _informations  = informations;
    }
    
    private void initialisationLabelNomPanel(String nomPanel) {
        _jlNomPanel = new JLabel(nomPanel, JLabel.CENTER);
        _jlNomPanel.setOpaque(true);
        _jlNomPanel.setBackground(Color.black);
        _jlNomPanel.setForeground(Color.white);
        _jlNomPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        _jlNomPanel.setAlignmentX((float) 10.0);
        _jlNomPanel.setFont(new Font(this.getFont().getName(), Font.BOLD, 18));
        
        add(_jlNomPanel, BorderLayout.NORTH);
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
