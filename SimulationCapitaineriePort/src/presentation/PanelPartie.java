package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Partie;

/**
 *
 * @author Jérémy
 */
public class PanelPartie extends PanelPerso {
    
    private JLabel _jlTempsRestant;
    private JLabel _jlNbRetard;
    
    private JButton _jbValider;

    public PanelPartie() {
        super("Partie en cours");
        initialisationComposants();
    }
    
    private void initialisationComposants() {
        JPanel panelGrid = new JPanel(new GridLayout(3, 1));
        
        JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFlow1.setBackground(COULEUR_PANEL1);
        panelFlow2.setBackground(COULEUR_PANEL1);
        panelFlow3.setBackground(COULEUR_PANEL1);
        
        _jlTempsRestant = new JLabel("Temps : " + Partie._tempsCourant);
        _jlNbRetard     = new JLabel("Retard cumulé : " + Partie._nbRetards);
        _jbValider      = new JButton("Valider");
        
        _jlNbRetard.setForeground(COULEUR_ECRITURE);
        _jlTempsRestant.setForeground(COULEUR_ECRITURE);
        
        panelFlow1.add(_jlTempsRestant);
        panelFlow2.add(_jlNbRetard);
        panelFlow3.add(_jbValider);
        
        panelGrid.add(panelFlow1);
        panelGrid.add(panelFlow2);
        panelGrid.add(panelFlow3);
        
        add(panelGrid, BorderLayout.CENTER);
    }
    
    public void actualiser() {
        _jlTempsRestant.setText("Temps : " + Partie._tempsCourant);
        _jlNbRetard.setText("Retard cumulé : " + Partie._nbRetards);
        this.revalidate();
    }
    
    public void remiseAZero() {
        Partie._tempsCourant   = 0;
        Partie._nbRetards      = 0;
    }
}
