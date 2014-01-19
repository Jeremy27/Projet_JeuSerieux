package view;

import java.awt.FlowLayout;
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
    }
    
    private void initialisationComposants() {
        JPanel panel    = new JPanel(new FlowLayout(FlowLayout.CENTER));
        _jlTempsRestant = new JLabel("Temps restant : " + _tempsRestant);
        _jlNbRetard     = new JLabel("Retard cumulé : " + _nbRetard);
        _jbValider      = new JButton("Valider");
    }
}
