/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import modele.Instance;
import presentation.PanelInfoJeu;
import presentation.PanelNavires;
import presentation.PanelPartie;

/**
 *
 * @author jeremy
 */
public class GestionJeu extends Thread {
    private Instance     _instance;
    private PanelNavires _naviresArrives;
    private PanelNavires _naviresArrivant;
    private PanelInfoJeu _infoJeu;
    
    public GestionJeu(PanelNavires naviresArrives, PanelNavires naviresArrivant, PanelInfoJeu infoJeu) {
        _naviresArrives     = naviresArrives;
        _naviresArrivant    = naviresArrivant;
        _infoJeu            = infoJeu;
        initialiserInstance();
    }
    
    private void initialiserInstance() {
        GestionInstance gInstance = new GestionInstance();
        gInstance.genererAleatoirement();
        _instance = gInstance.getInstance();
    }

    @Override
    public void run() {
        while(PanelPartie._tempsCourant <= PanelPartie._tempsFin) {
            
            _naviresArrives.ajouterNavires(_instance.getNavires(PanelPartie._tempsCourant));
            _naviresArrivant.ajouterNavires(_instance.getNavires(PanelPartie._tempsCourant+1));
            _naviresArrivant.supprimerNavires(PanelPartie._tempsCourant);
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GestionJeu.class.getName()).log(Level.SEVERE, null, ex);
            }
            PanelPartie._tempsCourant++;
        }
    }
    
    
}
