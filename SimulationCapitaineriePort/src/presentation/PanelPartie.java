package presentation;

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
public class PanelPartie extends PanelPerso {
    
    private JLabel _jlTempsRestant;
    private JLabel _jlNbRetard;
    
    private JButton _jbValider;
    
    public static int _tempsCourant;
    public static int _tempsFin;
    public static int _nbRetard;

    public PanelPartie() {
        super("Partie en cours");
        _tempsCourant   = 0;
        _tempsFin       = 100;
        initialisationComposants();
    }
    
    private void initialisationComposants() {
        JPanel panelGrid = new JPanel(new GridLayout(3, 1));
        
        JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFlow1.setBackground(new Color(78, 95, 150));
        panelFlow2.setBackground(new Color(78, 95, 150));
        panelFlow3.setBackground(new Color(78, 95, 150));
        
        _jlTempsRestant = new JLabel("Temps : " + _tempsCourant);
        _jlNbRetard     = new JLabel("Retard cumulé : " + _nbRetard);
        _jbValider      = new JButton("Valider");
        
        _jlNbRetard.setForeground(Color.lightGray);
        _jlTempsRestant.setForeground(Color.lightGray);
        
        panelFlow1.add(_jlTempsRestant);
        panelFlow2.add(_jlNbRetard);
        panelFlow3.add(_jbValider);
        
        panelGrid.add(panelFlow1);
        panelGrid.add(panelFlow2);
        panelGrid.add(panelFlow3);
        
        add(panelGrid, BorderLayout.CENTER);
    }
    
    public void actualiser() {
        _jlTempsRestant.setText("Temps : " + _tempsCourant);
        _jlNbRetard.setText("Retard cumulé : " + _nbRetard);
        this.revalidate();
    }
}
