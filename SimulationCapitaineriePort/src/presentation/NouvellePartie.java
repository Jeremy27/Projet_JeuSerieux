/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import metier.GestionJeu;

/**
 *
 * @author Jérémy
 */
public class NouvellePartie extends JDialog {
    
    private final IHM   _ihm;
    
    private JLabel      _jlPseudo;
    private JLabel      _jlDifficulte;
    private JTextField  _jtfPseuso;
    private JComboBox   _jcbDifficulte;
    private JButton     _jbAnnuler;
    private JButton     _jbCommencer;

    public NouvellePartie(IHM ihm) {
        setTitle("Nouvelle partie ...");
        setLayout(new GridLayout(3, 2));
        setSize(new Dimension(300, 200));
        
        _ihm = ihm;
        initialiserComposants();
        initialiserEvenements();
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        setVisible(true);
    }
    
    private void initialiserComposants() {
        _jlPseudo       = new JLabel("Pseudo : ");
        _jlDifficulte   = new JLabel("Difficulté : ");
        _jtfPseuso      = new JTextField("Joueur");
        _jcbDifficulte  = new JComboBox(new Object[] {"Facile", "Normal", "Difficile"});
        _jbAnnuler      = new JButton("Annuler");
        _jbCommencer    = new JButton("Commencer");
        
        
        JPanel hautGauche   = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel hautDroit    = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel centreGauche = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel centreDroit  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel basGauche    = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel basDroit     = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        hautGauche.setBackground(PanelPerso.COULEUR_PANEL1);
        _jlPseudo.setForeground(PanelPerso.COULEUR_ECRITURE);
        hautDroit.setBackground(PanelPerso.COULEUR_PANEL1);
        _jtfPseuso.setPreferredSize(new Dimension(100, 20));
        centreGauche.setBackground(PanelPerso.COULEUR_PANEL1);
        _jlDifficulte.setForeground(PanelPerso.COULEUR_ECRITURE);
        centreDroit.setBackground(PanelPerso.COULEUR_PANEL1);
        basDroit.setBackground(PanelPerso.COULEUR_PANEL1);
        basGauche.setBackground(PanelPerso.COULEUR_PANEL1);
        
        hautGauche.add(_jlPseudo);
        hautDroit.add(_jtfPseuso);
        centreGauche.add(_jlDifficulte);
        centreDroit.add(_jcbDifficulte);
        basGauche.add(_jbAnnuler);
        basDroit.add(_jbCommencer);
        
        add(hautGauche);
        add(hautDroit);
        add(centreGauche);
        add(centreDroit);
        add(basGauche);
        add(basDroit);
    }
    
    private void initialiserEvenements() {
        
        _jbAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        
        _jbCommencer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                PanelPartie._tempsCourant = PanelPartie._tempsFin;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NouvellePartie.class.getName()).log(Level.SEVERE, null, ex);
                }
                _ihm.remiseAZero();
                GestionJeu gestionJeu = new GestionJeu(_ihm._naviresArrives, _ihm._naviresArrivant, _ihm._infoJeu, _ihm._partie);
                gestionJeu.start();
                dispose();
            }
        });
    }
}
