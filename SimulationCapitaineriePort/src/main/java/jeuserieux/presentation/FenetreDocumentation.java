/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.presentation;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author jeremy
 */
public class FenetreDocumentation extends JDialog {
    
    
    private JTabbedPane _jtpOnglets;
    private JLabel      _jlRaccourci;
    private JLabel      _jlIHM;

    public FenetreDocumentation() {
        setTitle("Documentation");
        setSize(700, 700);
        
        initialiserComposants();
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initialiserComposants() {
        _jtpOnglets = new JTabbedPane();
        initialiserJLRaccourci();
        initialiserJL_IHM();
        _jtpOnglets.addTab("Présentation de l'IHM", _jlIHM);
        _jtpOnglets.addTab("Raccourcis clavier", _jlRaccourci);
        
        add(_jtpOnglets);
    }
    
    private void initialiserJL_IHM() {
        _jlIHM    = new JLabel();
        _jlIHM.setBackground(PanelPerso.COULEUR_PANEL1);
        _jlIHM.setForeground(PanelPerso.COULEUR_ECRITURE);
        _jlIHM.setOpaque(true);
        StringBuilder s = new StringBuilder(getDebutHTML());
        s.append("<h1>Présentation de l'IHM</h1>");
        s.append("<h2>Cadre en haut à droite</h2>");
        s.append("Ce cadre sert à afficher les informations relatives aux quais, aux terminaux et bien entendu aux navires, il suffit pour cela de cliquer sur l'un de ces éléments pour que ses informations s'affichent dans ce cadre.");
        s.append("<h2>Cadre en bas à droite</h2>");
        s.append("Ce cadre sert à afficher les informations relatives au jeu comme par exemple lorsqu'un navire arrive, lorsqu'un navire est assigné à un quai, lorsqu'un navire à terminé de charger/décharger sa marchandise, ...");
        s.append("<h2>Cadre en haut à gauche</h2>");
        s.append("Ce cadre sert à afficher les navires déjà arrivé au temps actuel, si le navires est sur fond rouge alors celui-ci est en retard, lorsque l'utilisateur clic sur un navire il peut alors le diriger sur un des quais de la carte");
        s.append("<h2>Cadre au milieu à gauche</h2>");
        s.append("Ce cadre sert à afficher les navires qui arriveront prochainement, celui-ci sert à prendre de l'avance en sachant quel type de navire arrive et ou est-ce qu'il pourra être assigné (dans le but de cumulé le moins de retard possible)");
        s.append("<h2>Cadre en bas à gauche</h2>");
        s.append("Ce cadre sert à afficher les informations sur la partie courante, tel que le pseudo utilisé par le joueur, la date courante, le nombre de retard cumulé, la difficulté choisie par le joueur.");
        s.append("<h2>Carte du port du Havre</h2>");
        s.append("La carte permet de visualiser l'état du jeu, on peut zoomer dessus (grace à la molette), se déplacer en utilisant 'cliquer sur la map' + 'déplacer la souris', on peut aussi assigner un navire à un quai et à la position choisie sur celui-ci.");
        s.append(getFinHTML());
        _jlIHM.setText(s.toString());
    }
    
    private void initialiserJLRaccourci() {
        _jlRaccourci    = new JLabel();
        _jlRaccourci.setBackground(PanelPerso.COULEUR_PANEL1);
        _jlRaccourci.setForeground(PanelPerso.COULEUR_ECRITURE);
        _jlRaccourci.setOpaque(true);
        
        StringBuilder s = new StringBuilder(getDebutHTML());
        s.append("<h1>Les raccourcis</h1>");
        s.append("<ul>");
        s.append("<li><span class=\"jaune\">Ctrl + s </span>: Sauvegarde la partie courante si celle-ci avait déjà été sauvegardée, sinon une fenêtre s'affiche pour savoir ou enregistrer le fichier et comment le nommer ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + c </span>: Lance la fenêtre pour charger une partie, seul un fichier avec l'extension '.svg' peut être sélectionné ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + p </span>: Lance la fenêtre pour accéder aux préférences de l'utilisateur ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + q </span>: Quitte le jeu ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + n </span>: Lance la fenêtre pour créer une nouvelle partie ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + a </span>: Permet d'obtenir une indication sur le prochain coup à jouer ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + r </span>: Lance la résolution automatique de la partie courante ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + b </span>: Lance la fenêtre d'affichage des scores ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + t </span>: Lance le didacticiel pour que l'utilisateur apprenne à jouer au jeu ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + d </span>: Lance la fenêtre permettant de consulter la documentation du jeu ;</li><br/>");
        s.append("<li><span class=\"jaune\">Ctrl + i </span>: Lance la fenêtre 'A propos' permettant d'en savoir plus sur le logiciel, sa version, la licence sous laquelle il est distribué, sa dernière mise à jour, le nom des développeurs.</li><br/>");
        s.append("</ul>");
        
        s.append(getFinHTML());
        _jlRaccourci.setText(s.toString());
    }
    
    private String getDebutHTML() {
        StringBuilder debHTML = new StringBuilder();
        debHTML.append("<html><style type='text/css'>");
        debHTML.append(".jaune   {color: yellow;}");
        debHTML.append("h1   {color: dark; font-weight: bold;}");
        debHTML.append("h2   {color: dark; font-style: italic;}");
        debHTML.append("</style></head><body>");
        return debHTML.toString();
    }
    
    private String getFinHTML() {
        StringBuilder finHTML = new StringBuilder();
        finHTML.append("</body></html>");
        return finHTML.toString();
    }
}
