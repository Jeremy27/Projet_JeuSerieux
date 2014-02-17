/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import jeuserieux.modele.Navire;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeMessage;
import jeuserieux.modele.enumeration.TypeNavire;
import jeuserieux.modele.outils.ParamsNavire;

/**
 *
 * @author Jérémy
 */
public class IHM extends JFrame {
    PanelInfoForme  _infoForme;
    PanelInfoJeu    _infoJeu;
    PanelNavires    _naviresArrives;
    PanelNavires    _naviresArrivant;
    PanelPartie     _partie;
    PanelMap        _map;

    public IHM() throws HeadlessException {
        setTitle("Simulation port du Havre");
        setSize(1300, 650);
        setLayout(new BorderLayout());
        
        setJMenuBar(new Menu(this));
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
        
        n.constructionNavire();
        
        JPanel panelGauche  = new JPanel(new GridLayout(3, 1));
        JPanel panelDroit   = new JPanel(new GridLayout(2, 1));
        
        _partie             = new PanelPartie();
        _infoForme          = new PanelInfoForme("Navire", n.getDonneesFormates());
        _naviresArrives     = new PanelNavires("Navires arrivés", titres1, _infoForme);
        _naviresArrivant    = new PanelNavires("Navires arrivant", titres2, _infoForme);
        _infoJeu            = new PanelInfoJeu();
        
        _infoForme.majInformations();
        _infoJeu.ajoutMessage("test", TypeMessage.NORMAL);
        
        _map = new PanelMap(_infoForme, _naviresArrives);
        _naviresArrives.setMap(_map);
        _map.ajoutNavire(n);
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
    
    public void remiseAZero() {
        _infoForme.remiseAZero();
        _naviresArrivant.remiseAZero();
        _naviresArrives.remiseAZero();
        _infoJeu.remiseAZero();
        _partie.remiseAZero();
        _map.remiseAZero();
    }
    
    public static void main(String[] args) {
        IHM ihm = new IHM();
    }
}
