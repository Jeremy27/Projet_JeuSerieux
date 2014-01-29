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
import javax.swing.JFrame;
import javax.swing.JPanel;
import modele.Navire;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeMessage;
import modele.enumeration.TypeNavire;
import modele.outils.ParamsNavire;

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
    
    private ParamsNavire getParamsNavire() {
        ParamsNavire params = new ParamsNavire();
        params.setDateArrivee(10);
        params.setDatePriseEnCharge(11);
        params.setLargeur(2);
        params.setLongueur(10);
        params.setNom("Navire 1");
        params.setPosition(new Point(2, 10));
        params.setTempsPriseEnCharge(5);
        params.setTypeMarchandise(TypeMarchandise.PASSAGER);
        params.setTypeNavire(TypeNavire.FERRY);
        return params;
    }
    
    private void creationPanels() {
        String[] titres1 = {"Nom", "Arrivé le"};
        String[] titres2 = {"Nom", "Arrive le"};
        
        ParamsNavire params = getParamsNavire();
        Navire n = new Navire(params);
        params.setDateArrivee(1);
        params.setNom("Navire 2");
        Navire n1 = new Navire(params);
        params.setDateArrivee(5);
        params.setNom("Navire 3");
        Navire n2 = new Navire(params);
        params.setDateArrivee(15);
        params.setNom("Navire 4");
        Navire n3 = new Navire(params);
        params.setDateArrivee(7);
        params.setNom("Navire 5");
        Navire n4 = new Navire(params);
        
        JPanel panelGauche  = new JPanel(new GridLayout(3, 1));
        JPanel panelDroit   = new JPanel(new GridLayout(2, 1));
        
        PanelPartie    p2 = new PanelPartie();
        PanelInfoForme p3 = new PanelInfoForme("Navire", n.getDonneesFormates());
        PanelNavires p1 = new PanelNavires("Navires arrivés", titres1, p3);
        PanelNavires p5 = new PanelNavires("Navires arrivant", titres2, p3);
        p1.ajouterNavire(n);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n2);
        p1.ajouterNavire(n);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n4);
        p1.ajouterNavire(n3);
        p1.ajouterNavire(n1);
        p1.ajouterNavire(n);
        p5.ajouterNavire(n2);
        p5.ajouterNavire(n3);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n);
        p5.ajouterNavire(n4);
        p5.ajouterNavire(n1);
        p5.ajouterNavire(n2);
        p5.ajouterNavire(n4);
        PanelInfoJeu   p4 = new PanelInfoJeu();
        
        PanelMap map = new PanelMap(p3);
        map.initEvents();
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
