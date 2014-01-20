package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jérémy
 */
public class PanelPartie extends Panel {
    
    private JLabel _jlTempsRestant;
    private JLabel _jlNbRetard;
    
    private JButton _jbValider;
    
    public static int _tempsRestant;
    public static int _nbRetard;

    public PanelPartie() {
        super("Partie en cours");
        initialisationComposants();
    }
    
    private void initialisationComposants() {
        JPanel panelGrid = new JPanel(new GridLayout(3, 1, 10, 10));
        panelGrid.setBackground(Color.gray);
        
        JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFlow1.setBackground(Color.gray);
        panelFlow2.setBackground(Color.gray);
        panelFlow3.setBackground(Color.gray);
        
        _jlTempsRestant = new JLabel("Temps restant : " + _tempsRestant);
        _jlNbRetard     = new JLabel("Retard cumulé : " + _nbRetard);
        _jbValider      = new JButton("Valider");
        
        _jlNbRetard.setBackground(Color.gray);
        _jlTempsRestant.setBackground(Color.gray);
        
        panelFlow1.add(_jlTempsRestant);
        panelFlow2.add(_jlNbRetard);
        panelFlow3.add(_jbValider);
        
        panelGrid.add(panelFlow1);
        panelGrid.add(panelFlow2);
        panelGrid.add(panelFlow3);
        
        add(panelGrid, BorderLayout.CENTER);
    }
}
