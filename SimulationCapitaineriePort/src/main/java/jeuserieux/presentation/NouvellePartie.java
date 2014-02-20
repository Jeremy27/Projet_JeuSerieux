package jeuserieux.presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jeuserieux.metier.GestionInstance;
import jeuserieux.metier.GestionJeu;
import jeuserieux.modele.Instance;
import jeuserieux.modele.Partie;
import jeuserieux.modele.enumeration.TypeDifficulte;

/**
 *
 * @author Jérémy
 */
public class NouvellePartie extends JDialog {
    
    private final IHM   _ihm;
    
    private JLabel      _jlPseudo;
    private JLabel      _jlDifficulte;
    private JLabel      _jlInfos;
    private JTextField  _jtfPseuso;
    private JComboBox   _jcbDifficulte;
    private JButton     _jbAnnuler;
    private JButton     _jbCommencer;
    private JComboBox   _jcbChoixListeNavires;
    private JLabel      _jlChoix;

    public NouvellePartie(IHM ihm) {
        setTitle("Nouvelle partie ...");
        
        _ihm = ihm;
        initialiserComposants();
        initialiserEvenements();
        majInfos();
        
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
        _jcbDifficulte  = new JComboBox(new Object[] {TypeDifficulte.FACILE, TypeDifficulte.NORMAL, TypeDifficulte.DIFFICILE});
        _jbAnnuler      = new JButton("Annuler");
        _jbCommencer    = new JButton("Commencer");
        _jlInfos        = new JLabel();
        _jlChoix        = new JLabel("Choix liste navires : ");
        _jcbChoixListeNavires = new JComboBox(new Object[] {"Base réelle", "Aléatoire"});
        
        JPanel panelG       = new JPanel(new GridLayout(4, 2));
        JPanel hautGauche   = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel hautDroit    = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel centreGauche = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel centreDroit  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pComboBox    = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel plComboBox   = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
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
        _jlInfos.setBackground(PanelPerso.COULEUR_PANEL1);
        _jlInfos.setForeground(PanelPerso.COULEUR_ECRITURE);
        _jlInfos.setOpaque(true);
        _jlChoix.setForeground(PanelPerso.COULEUR_ECRITURE);
        pComboBox.setBackground(PanelPerso.COULEUR_PANEL1);
        plComboBox.setBackground(PanelPerso.COULEUR_PANEL1);
        
        hautGauche.add(_jlPseudo);
        hautDroit.add(_jtfPseuso);
        centreGauche.add(_jlDifficulte);
        centreDroit.add(_jcbDifficulte);
        basGauche.add(_jbAnnuler);
        basDroit.add(_jbCommencer);
        pComboBox.add(_jcbChoixListeNavires);
        plComboBox.add(_jlChoix);
        
        panelG.add(hautGauche);
        panelG.add(hautDroit);
        panelG.add(centreGauche);
        panelG.add(centreDroit);
        panelG.add(plComboBox);
        panelG.add(pComboBox);
        panelG.add(basGauche);
        panelG.add(basDroit);
        
        add(panelG, BorderLayout.WEST);
        add(_jlInfos, BorderLayout.CENTER);
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
                demarrerNouvellePartie();
            }
        });
        
        _jcbDifficulte.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                majInfos();
            }
        });
    }
    
    private void majInfos() {
        StringBuilder str = new StringBuilder();
        str.append("<html><body>");
        if(_jcbDifficulte.getSelectedItem().equals(TypeDifficulte.FACILE)){
            str.append(Partie.toStringFacile());
        } else if(_jcbDifficulte.getSelectedItem().equals(TypeDifficulte.NORMAL)) {
            str.append(Partie.toStringNormal());
        } else if (_jcbDifficulte.getSelectedItem().equals(TypeDifficulte.DIFFICILE)) {
            str.append(Partie.toStringDifficile());
        }
        str.append("</body></html>");
        _jlInfos.setText(str.toString());
        pack();
    }
    
    void arretPartieEnCours() {
        Partie._abandon = true;

        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Logger.getLogger(NouvellePartie.class.getName()).log(Level.SEVERE, null, ex);
        }
        _ihm.remiseAZero();
        
    }
    
    void demarrerNouvellePartie() {
        arretPartieEnCours();
        
        GestionJeu gestionJeu = new GestionJeu(_ihm._naviresArrives, _ihm._naviresArrivant, _ihm._infoJeu, _ihm._partie, _ihm._map);
        
        Partie nouvellePartie = new Partie((TypeDifficulte)_jcbDifficulte.getSelectedItem(), getPseudo());
        
        gestionJeu.setInstance(creerInstance());
        gestionJeu.start();
        
        dispose();
    }
    
    Instance creerInstance() {
        GestionInstance gInstance = new GestionInstance();
        
        if(_jcbChoixListeNavires.getSelectedIndex() == 0)
            gInstance.genererInstance();
        else
            gInstance.genererAleatoirement();
        
        if(null == gInstance.getInstance()) {
            gInstance.genererAleatoirement();
        }
        
        return gInstance.getInstance();
    }
    
    private String getPseudo() {
        String pseudo = _jtfPseuso.getText();
        if(pseudo.isEmpty()) {
            pseudo = "Anonyme";
        }
        return pseudo;
    }
}
