/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Point;
import java.util.ArrayList;
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
        setSize(1300, 650);
        setLayout(new BorderLayout());
        
        setJMenuBar(new Menu());
        creationPanels();
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void creationPanels() {
        AfficherMap map = new AfficherMap();
        JsonArray ja = map.getContenu();
        map.makePath2D(ja);
        map.eventsMap();
        add(map, BorderLayout.CENTER);
        
        Navire n = new Navire("CHANTACO", TypeNavire.PORTE_CONTENEURS, TypeMarchandise.CONTENEURS, 15, 1, 4, new Point(0, 2));
        
        JPanel panelGauche  = new JPanel(new GridLayout(2, 1));
        JPanel panelDroit   = new JPanel(new GridLayout(2, 1));
        
        PanelInfoForme p1 = new PanelInfoForme("Numero 1", n.getDonneesFormates());
        PanelPartie    p2 = new PanelPartie();
        PanelInfoForme p3 = new PanelInfoForme("Navire", n.getDonneesFormates());
        PanelInfoJeu   p4 = new PanelInfoJeu();
        
        p4.ajoutMessage("Message erreur", TypeMessage.ERREUR);
        p4.ajoutMessage("Message normal", TypeMessage.NORMAL);
        p4.ajoutMessage("Message normal", TypeMessage.NORMAL);
        p4.ajoutMessage("Message normal", TypeMessage.NORMAL);
        p4.ajoutMessage("Message retard", TypeMessage.RETARD);
        p4.ajoutMessage("Message avertissement", TypeMessage.AVERTISSEMENT);
        
        p1.majInformations();
        p3.majInformations();
        
        panelGauche.add(p1);
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
