/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Instance;
import modele.Navire;
import modele.Partie;
import modele.enumeration.TypeMessage;
import modele.enumeration.TypeNavire;
import presentation.PanelInfoJeu;
import presentation.PanelMap;
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
    private PanelMap _map;
    
    public GestionJeu(PanelNavires naviresArrives, PanelNavires naviresArrivant, PanelInfoJeu infoJeu, PanelPartie partie, PanelMap map) {
        _naviresArrives     = naviresArrives;
        _naviresArrivant    = naviresArrivant;
        _infoJeu            = infoJeu;
        _partie             = partie;
        _map                = map;
    }
    
    public void setInstance(Instance instance) {
        _instance = instance;
    }

    @Override
    public void run() {
        ArrayList<Navire> naviresArrives;
        int compteur = 0;
        int sleep    = 200;
        
        while(Partie._tempsCourant <= Partie._tempsFin) {
            
            if(compteur%Partie._tempsTour == 0) {
                if(compteur != 0) {
                    Partie._tempsCourant++;
                }
                
                // Panel navires arrivés
                naviresArrives = _instance.getNavires(Partie._tempsCourant);
                _naviresArrives.ajouterNavires(_instance.getNavires(Partie._tempsCourant));

                if(!naviresArrives.isEmpty()) {
                    for(Navire navire : naviresArrives)
                        if(navire.getType() == TypeNavire.FERRY)
                            _infoJeu.ajoutMessage(navire.getNom() + "  " + navire.getType(), TypeMessage.AVERTISSEMENT);
                        else
                            _infoJeu.ajoutMessage(navire.getNom() + "  " + navire.getType(), TypeMessage.NORMAL);
                    _infoJeu.ajoutMessage("Navires arrivés le "+Partie._tempsCourant+" : ", TypeMessage.IMPORTANT);
                }

                // Panel navires arrivant
                _naviresArrivant.ajouterNavires(_instance.getNavires(Partie._tempsCourant+1));
                _naviresArrivant.supprimerNavires(Partie._tempsCourant);

                // Calcul des retards
                Partie._nbRetards += _naviresArrives.getNbNaviresEnRetard();
                
                _partie.actualiser();
                _naviresArrivant.actualiser();
                _naviresArrives.actualiser();
            }
            
            try {
                sleep(sleep);
                compteur+=sleep;
            } catch (InterruptedException ex) {
                Logger.getLogger(GestionJeu.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(Partie._abandon) {
                break;
            }
        }
        
        if(!Partie._abandon) {
            GestionScores gScores = new GestionScores("scores");
            gScores.sauvegarderScore();
            System.out.println("Sauvegarde des scores");
        }
    }
}
