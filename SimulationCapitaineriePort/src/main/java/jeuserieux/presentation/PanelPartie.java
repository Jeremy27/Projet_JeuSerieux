package jeuserieux.presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jeuserieux.metier.GestionJeu;
import jeuserieux.modele.Partie;

/**
 *
 * @author Jérémy
 */
public class PanelPartie extends PanelPerso {
    
    private JLabel _jlTempsCourant;
    private JLabel _jlNbRetard;
    private JLabel _jlPseudo;
    private JLabel _jlDifficulte;

    public PanelPartie() {
        super("Partie en cours");
        initialisationComposants();
    }
    
    private void initialisationComposants() {
        JPanel panelGrid = new JPanel(new GridLayout(4, 1));
        
        JPanel panelFlow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelFlow2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelFlow3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelFlow4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        panelFlow1.setBackground(COULEUR_PANEL1);
        panelFlow2.setBackground(COULEUR_PANEL1);
        panelFlow3.setBackground(COULEUR_PANEL1);
        panelFlow4.setBackground(COULEUR_PANEL1);
        
        _jlTempsCourant = new JLabel(getTempCourant());
        _jlNbRetard     = new JLabel(getRetards());
        _jlPseudo       = new JLabel(getPseudo());
        _jlDifficulte   = new JLabel(getDifficulte());
        
        _jlNbRetard.setForeground(COULEUR_ECRITURE);
        _jlTempsCourant.setForeground(COULEUR_ECRITURE);
        _jlDifficulte.setForeground(COULEUR_ECRITURE);
        _jlPseudo.setForeground(COULEUR_ECRITURE);
        
        panelFlow1.add(_jlTempsCourant);
        panelFlow2.add(_jlNbRetard);
        panelFlow3.add(_jlDifficulte);
        panelFlow4.add(_jlPseudo);
        
        panelGrid.add(panelFlow4);
        panelGrid.add(panelFlow3);
        panelGrid.add(panelFlow1);
        panelGrid.add(panelFlow2);
        
        add(panelGrid, BorderLayout.CENTER);
    }
    
    private String getPseudo() {
        return "Pseudo : " + Partie._pseudo;
    }
    
    private String getDifficulte() {
        return "Difficulté : " + Partie._difficulte.name();
    }
    
    private String getTempCourant() {
        return "Date : " + GestionJeu.getDateFormate(Partie._tempsCourant);
    }
    
    private String getRetards() {
        StringBuilder retards = new StringBuilder("Retards cumulé : ");
        retards.append(Partie._nbRetards);
        retards.append("h");
        
        return retards.toString();
    }
    
    public void actualiser() {
        _jlTempsCourant.setText(getTempCourant());
        _jlNbRetard.setText(getRetards());
        _jlPseudo.setText(getPseudo());
        _jlDifficulte.setText(getDifficulte());
        this.revalidate();
    }
    
    public void remiseAZero() {
        Partie._tempsCourant   = 0;
        Partie._nbRetards      = 0;
    }
}
