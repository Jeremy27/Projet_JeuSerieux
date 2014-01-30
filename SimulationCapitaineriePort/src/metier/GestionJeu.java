/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import modele.Instance;
import modele.Navire;
import modele.enumeration.TypeMessage;
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
    private PanelPartie  _partie;
    
    public GestionJeu(PanelNavires naviresArrives, PanelNavires naviresArrivant, PanelInfoJeu infoJeu, PanelPartie partie) {
        _naviresArrives     = naviresArrives;
        _naviresArrivant    = naviresArrivant;
        _infoJeu            = infoJeu;
        _partie             = partie;
        initialiserInstance();
    }
    
    private void initialiserInstance() {
        GestionInstance gInstance = new GestionInstance();
        gInstance.genererAleatoirement();
        _instance = gInstance.getInstance();
    }

    @Override
    public void run() {
        ArrayList<Navire> naviresArrives;
        ArrayList<Navire> naviresEnRetard;
        while(PanelPartie._tempsCourant <= PanelPartie._tempsFin) {
            
            // Panel navires arrivés
            naviresArrives = _instance.getNavires(PanelPartie._tempsCourant);
            _naviresArrives.ajouterNavires(_instance.getNavires(PanelPartie._tempsCourant));
            
            if(!naviresArrives.isEmpty()) {
                for(Navire navire : naviresArrives)
                    _infoJeu.ajoutMessage(navire.getNom() + "  " + navire.getType(), TypeMessage.NORMAL);
                _infoJeu.ajoutMessage("Navires arrivés le "+PanelPartie._tempsCourant+" : ", TypeMessage.AVERTISSEMENT);
            }
            
            // Panel navires arrivant
            _naviresArrivant.ajouterNavires(_instance.getNavires(PanelPartie._tempsCourant+1));
            _naviresArrivant.supprimerNavires(PanelPartie._tempsCourant);
            
            // Calcul des retards
            PanelPartie._nbRetard += _naviresArrives.getNbNaviresEnRetard();
            
            
            try {
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GestionJeu.class.getName()).log(Level.SEVERE, null, ex);
            }
            PanelPartie._tempsCourant++;
            _partie.actualiser();
        }
    }
    
    
}
