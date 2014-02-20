package jeuserieux.presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JComponent.TOOL_TIP_TEXT_KEY;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import jeuserieux.accesAuDonnees.ConnexionMySQL;

/**
 *
 * @author Jérémy
 */
public class Menu extends JMenuBar {
    
    private final IHM _ihm;
    
    private JMenu _menuFichier;
    private JMenu _menuPartie;
    private JMenu _menuAide;
    
    private JMenuItem _fichierSauvegarder;
    private JMenuItem _fichierCharger;
    private JMenuItem _fichierPreference;
    private JMenuItem _fichierFermer;
    
    private JMenuItem _partieNouvelle;
    private JMenuItem _partieAide;
    private JMenuItem _partieResoudre;
    private JMenuItem _partieScores;
    
    private JMenuItem _aideDidacticiel;
    private JMenuItem _aideDocumentation;
    private JMenuItem _aideAPropos;
    
    private JMenuItem _ajoutUtilisateur;

    public Menu(IHM ihm) {
        _ihm = ihm;
        initComposants();
        evenementsPartie();
        
        add(_menuFichier);
        add(_menuPartie);
        add(_menuAide);
        
        desactiverJMenuItemNonGere();
    }
    
    private void initComposants() {
        _menuFichier    = new JMenu("Fichier");
        _menuPartie     = new JMenu("Partie");
        _menuAide       = new JMenu("Aide");
        
        _fichierSauvegarder = new JMenuItem("Sauvegarder");
        _fichierCharger     = new JMenuItem("Charger");
        _fichierPreference  = new JMenuItem("Préférences");
        _fichierFermer      = new JMenuItem("Fermer");
        
        _partieNouvelle = new JMenuItem("Nouvelle...");
        _partieAide     = new JMenuItem("Aide");
        _partieResoudre = new JMenuItem("Résoudre");
        _partieScores   = new JMenuItem("Scores");
        _ajoutUtilisateur = new JMenuItem("Inscrire un nouvel utilisateur");
        
        _aideDidacticiel    = new JMenuItem("Didacticiel");
        _aideDocumentation  = new JMenuItem("Documentation");
        _aideAPropos        = new JMenuItem("A propos");
        
        _partieNouvelle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
        _partieScores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,InputEvent.CTRL_MASK));
        _partieAide.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
        _partieResoudre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        _fichierFermer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,InputEvent.CTRL_MASK));
        _fichierPreference.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
        _fichierSauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
        _fichierCharger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
        _aideDidacticiel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
        _aideAPropos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_MASK));
        _aideDocumentation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK));
        
        _menuFichier.add(_fichierSauvegarder);
        _menuFichier.add(_fichierCharger);
        _menuFichier.add(_fichierPreference);
        _menuFichier.add(_fichierFermer);
        
        _menuPartie.add(_partieNouvelle);
        _menuPartie.add(_partieAide);
        _menuPartie.add(_partieResoudre);
        _menuPartie.add(_partieScores);
        _menuPartie.add(_ajoutUtilisateur);
        
        _menuAide.add(_aideDidacticiel);
        _menuAide.add(_aideDocumentation);
        _menuAide.add(_aideAPropos);
    }
    
    private void evenementsPartie() {
        
        _partieNouvelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                NouvellePartie nouvellePartie = new NouvellePartie(_ihm);
            }
        });
        
        
        
        _partieScores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                new FenetreScores(null);
            }
        });
        
        _fichierFermer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        
        _aideDocumentation.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                new FenetreDocumentation();
            }
        });
        
        _ajoutUtilisateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new FenetreAjoutUtilisateur();
            }
        });
    }
    
    private void desactiverJMenuItemNonGere() {
        _aideAPropos.setEnabled(false);
        _aideDidacticiel.setEnabled(false);
        _partieAide.setEnabled(false);
        _partieResoudre.setEnabled(false);
        _fichierCharger.setEnabled(false);
        _fichierPreference.setEnabled(false);
        _fichierSauvegarder.setEnabled(false);
    }
}
