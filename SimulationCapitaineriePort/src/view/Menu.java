package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Jérémy
 */
public class Menu extends JMenuBar {
    
    private JMenu _menuFichier;
    private JMenu _menuPartie;
    private JMenu _menuAide;
    
    private JMenuItem _fichierSauvegarder;
    private JMenuItem _fichierCharger;
    private JMenuItem _fichierPreference;
    private JMenuItem _fichierFermer;
    
    private JMenuItem _partieAide;
    private JMenuItem _partieResoudre;
    private JMenuItem _partieScores;
    
    private JMenuItem _aideDidactitiel;
    private JMenuItem _aideDocumentation;
    private JMenuItem _aideAPropos;

    public Menu() {
        initComposants();
        
        add(_menuFichier);
        add(_menuPartie);
        add(_menuAide);
    }
    
    private void initComposants() {
        _menuFichier    = new JMenu("Fichier");
        _menuPartie     = new JMenu("Partie");
        _menuAide       = new JMenu("Aide");
        
        _fichierSauvegarder = new JMenuItem("Sauvegarder");
        _fichierCharger     = new JMenuItem("Charger");
        _fichierPreference  = new JMenuItem("Préférences");
        _fichierFermer      = new JMenuItem("Fermer");
        
        _partieAide     = new JMenuItem("Aide");
        _partieResoudre = new JMenuItem("Résoudre");
        _partieScores   = new JMenuItem("Scores");
        
        _aideDidactitiel    = new JMenuItem("Didactitiel");
        _aideDocumentation  = new JMenuItem("Documentation");
        _aideAPropos        = new JMenuItem("A propos");
        
        _menuFichier.add(_fichierSauvegarder);
        _menuFichier.add(_fichierCharger);
        _menuFichier.add(_fichierPreference);
        _menuFichier.add(_fichierFermer);
        
        _menuPartie.add(_partieAide);
        _menuPartie.add(_partieResoudre);
        _menuPartie.add(_partieScores);
        
        _menuAide.add(_aideDidactitiel);
        _menuAide.add(_aideDocumentation);
        _menuAide.add(_aideAPropos);
    }
}
