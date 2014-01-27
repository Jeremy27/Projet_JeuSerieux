/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import javax.json.JsonArray;
import javax.swing.JFrame;
import javax.swing.JPanel;
import modele.Navire;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeMessage;
import modele.enumeration.TypeNavire;

/**
 *
 * @author Jérémy
 */
public class IHM extends JFrame {

    public IHM() throws HeadlessException {
        setTitle("Simulation port du Havre");
        setSize(1300, 650);
        setLayout(new BorderLayout());
        
        setJMenuBar(new Menu());
        creationPanels();
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void creationPanels() {
        String[] titres1 = {"Nom", "Arrivé le"};
        String[] titres2 = {"Nom", "Arrive le"};
        Navire n = new Navire("CHANTACO", TypeNavire.PORTE_CONTENEURS, TypeMarchandise.PASSAGER, 12, 1, 4, new Point(0, 2));
        Navire n1 = new Navire("test1", TypeNavire.PORTE_CONTENEURS, TypeMarchandise.CONTENEURS, 18, 10, 4, new Point(0, 2));
        Navire n2 = new Navire("test2", TypeNavire.RO_RO, TypeMarchandise.EOLIEN, 10, 15, 4, new Point(0, 2));
        Navire n3 = new Navire("test3", TypeNavire.PORTE_CONTENEURS, TypeMarchandise.ROULIER, 30, 1, 4, new Point(0, 2));
        Navire n4 = new Navire("test5", TypeNavire.FERRY, TypeMarchandise.CONTENEURS, 14, 16, 4, new Point(0, 2));
        
        JPanel panelGauche  = new JPanel(new GridLayout(3, 1));
        JPanel panelDroit   = new JPanel(new GridLayout(2, 1));
        
        PanelPartie    p2 = new PanelPartie();
        PanelInfoForme p3 = new PanelInfoForme("Navire", n.getDonneesFormates());
        PanelNavires p1 = new PanelNavires("Navires arrivés", titres1, p3);
        PanelNavires p5 = new PanelNavires("Navires arrivant", titres2, p3);
        p1.ajouterNavire(n);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n1);
        p5.ajouterNavire(n2);
        p5.ajouterNavire(n3);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n4);
        PanelInfoJeu   p4 = new PanelInfoJeu();
        
        PanelMap map = new PanelMap(p3);
        add(map, BorderLayout.CENTER);
        
        p4.ajoutMessage("Message erreur", TypeMessage.ERREUR);
        
        p3.majInformations();
        
        panelGauche.add(p1);
        panelGauche.add(p5);
        panelGauche.add(p2);
        
        panelDroit.add(p3);
        panelDroit.add(p4);
        
        add(panelGauche, BorderLayout.WEST);
        add(panelDroit, BorderLayout.EAST);
    }
    
    public static void main(String[] args) {
        new IHM();
    }
}
