/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import metier.GestionJeu;
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
    private PanelInfoForme  _infoForme;
    private PanelInfoJeu    _infoJeu;
    private PanelNavires    _naviresArrives;
    private PanelNavires    _naviresArrivant;
    private PanelPartie     _partie;
    private PanelMap        _map;

    public IHM() throws HeadlessException {
        setTitle("Simulation port du Havre");
        setSize(1300, 650);
        setLayout(new BorderLayout());
        
        setJMenuBar(new Menu());
        creationPanels();
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        });
    }
    
    private ParamsNavire getParamsNavire() {
        ParamsNavire params = new ParamsNavire();
        params.setDateArrivee(10);
        params.setDatePriseEnCharge(11);
        params.setLargeur(0.00040);
        params.setLongueur(0.0020);
        params.setNom("Navire 1");
        params.setPosition(new Point2D.Double(0.093, 49.486));
        params.setTempsPriseEnCharge(5);
        params.setTypeMarchandise(TypeMarchandise.CONTENEURS);
        params.setTypeNavire(TypeNavire.PORTE_CONTENEURS);
        return params;
    }
    
    private void creationPanels() {
        String[] titres1 = {"Nom", "Arrivé le"};
        String[] titres2 = {"Nom", "Arrive le"};
        
        ParamsNavire params = getParamsNavire();
        Navire n = new Navire(params);
        params.setDateArrivee(1);
        params.setNom("Navire 2");
//        Navire n1 = new Navire(params);
//        params.setDateArrivee(5);
//        params.setNom("Navire 3");
//        Navire n2 = new Navire(params);
//        params.setDateArrivee(15);
//        params.setNom("Navire 4");
//        Navire n3 = new Navire(params);
//        params.setDateArrivee(7);
//        params.setNom("Navire 5");
//        Navire n4 = new Navire(params);
        
        n.constructionNavire();
//        n1.constructionNavire();
//        n2.constructionNavire();
//        n3.constructionNavire();
//        n4.constructionNavire();
        
        JPanel panelGauche  = new JPanel(new GridLayout(3, 1));
        JPanel panelDroit   = new JPanel(new GridLayout(2, 1));
        
        _partie             = new PanelPartie();
        _infoForme          = new PanelInfoForme("Navire", n.getDonneesFormates());
        _naviresArrives     = new PanelNavires("Navires arrivés", titres1, _infoForme);
        _naviresArrivant    = new PanelNavires("Navires arrivant", titres2, _infoForme);
        _infoJeu            = new PanelInfoJeu();
        
        _infoForme.majInformations();
        _infoJeu.ajoutMessage("test", TypeMessage.NORMAL);
        
        _map = new PanelMap(_infoForme);
        _map.setNavires(new Navire[]{n/*1, n2, n3, n4*/});
        _map.initEvents();
        _map.lierMapRealite();
        add(_map, BorderLayout.CENTER);
        
        panelGauche.add(_naviresArrives);
        panelGauche.add(_naviresArrivant);
        panelGauche.add(_partie);
        
        panelDroit.add(_infoForme);
        panelDroit.add(_infoJeu);
        
        add(panelGauche, BorderLayout.WEST);
        add(panelDroit, BorderLayout.EAST);
    }
    
    public void lancementJeu() {
        GestionJeu jeu = new GestionJeu(_naviresArrives, _naviresArrivant, _infoJeu, _partie);
        jeu.start();
        try {
            jeu.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        IHM ihm = new IHM();
//        NouvellePartie partie = new NouvellePartie();
        ihm.lancementJeu();
    }
}
