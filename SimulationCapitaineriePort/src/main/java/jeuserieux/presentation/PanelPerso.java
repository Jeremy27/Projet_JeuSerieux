/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jérémy
 */
public class PanelPerso extends JPanel {
    
    public static Color COULEUR_PANEL1              = new Color(78, 95, 150);
    public static Color COULEUR_PANEL2              = new Color(100, 120, 150);
    public static Color COULEUR_BACKGROUND_TITRE    = new Color(4, 47, 104);
    public static Color COULEUR_ECRITURE            = Color.white;
    public static Color COULEUR_RETARD              = new Color(235, 20, 0);
    
    protected JLabel _jlNomPanel;
    
    public PanelPerso(String nomPanel) {
        setPreferredSize(new Dimension(200, this.getHeight()));
        setLayout(new BorderLayout());
        
        initialisationLabelNomPanel(nomPanel);
    }
    
    private void initialisationLabelNomPanel(String nomPanel) {
        _jlNomPanel = new JLabel(nomPanel, JLabel.CENTER);
        _jlNomPanel.setOpaque(true);
        _jlNomPanel.setBackground(COULEUR_BACKGROUND_TITRE);
        _jlNomPanel.setForeground(COULEUR_ECRITURE);
        _jlNomPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        _jlNomPanel.setFont(new Font(this.getFont().getName(), Font.BOLD, 18));
        
        add(_jlNomPanel, BorderLayout.NORTH);
    }
    
    public void setNomPanel(String nom) {
        _jlNomPanel.setText(nom);
    }
}
