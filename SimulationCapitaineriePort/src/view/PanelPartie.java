package view;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author jeremy
 */
public class PanelPartie extends Panel {
    
    private JLabel _jlTempsRestant;
    private JLabel _jlNbRetard;
    
    private JButton _jbTourSuivant;
    
    public static int _tempsRestant;
    public static int _nbRetard;

    public PanelPartie() {
        super("Partie en cours");
    }
}
