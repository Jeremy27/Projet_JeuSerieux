package jeuserieux.metier;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jeuserieux.modele.Instance;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Partie;
import jeuserieux.modele.Score;
import jeuserieux.modele.enumeration.TypeMessage;
import jeuserieux.modele.enumeration.TypeNavire;
import jeuserieux.presentation.FenetreScores;
import jeuserieux.presentation.PanelInfoJeu;
import jeuserieux.presentation.PanelMap;
import jeuserieux.presentation.PanelNavires;
import jeuserieux.presentation.PanelPartie;

/**
 *
 * @author jeremy
 */
public class GestionJeu extends Thread {
    private Instance        _instance;
    private PanelNavires    _naviresArrives;
    private PanelNavires    _naviresArrivant;
    private PanelInfoJeu    _infoJeu;
    private PanelPartie     _partie;
    private PanelMap        _map;
    private final static String[]  _tabJour = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
    
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
    
    public JsonArray genererJson() {
        JsonArrayBuilder tableauJson = Json.createArrayBuilder();
        
        tableauJson.add(getPartieJson());
        tableauJson.add(getInstanceJson());
        tableauJson.add(getQuaisJson());
        
        return tableauJson.build();
    }
    
    private JsonObject getPartieJson() {
        JsonObjectBuilder objetJson = Json.createObjectBuilder();
        
        objetJson.add("pseudo", Partie._pseudo);
        objetJson.add("tempsCourant", Partie._tempsCourant);
        objetJson.add("nbRetards", Partie._nbRetards);
        objetJson.add("difficulte", Partie._difficulte.name());
        
        return objetJson.build();
    }
    
    private JsonObject getInstanceJson() {
        JsonObjectBuilder objetJson     = Json.createObjectBuilder();
        JsonArrayBuilder tableauJson    = Json.createArrayBuilder();
        //objetJson.add(null, BigDecimal.ZERO)
        
        return objetJson.build();
    }
    
    private JsonObject getQuaisJson() {
        JsonObjectBuilder objetJson = Json.createObjectBuilder();
        
        //objetJson.add(null, BigDecimal.ZERO)
        
        return objetJson.build();
    }
    
    public static String getDateFormate(int temps) {
        int jour    = temps/24;
        int heure   = temps%24;
        StringBuilder date = new StringBuilder(_tabJour[jour]);
        date.append(", ");
        if(heure < 10)
            date.append("0");
        date.append(heure);
        date.append("h");
        
        return date.toString();
    } 
    
    private void majNaviresArrives(ArrayList<Navire> naviresArrives) {
        _naviresArrives.ajouterNavires(naviresArrives);
    }
    
    private void afficherArrivesDansLog(ArrayList<Navire> naviresArrives) {
        if (!naviresArrives.isEmpty()) {
            for (Navire navire : naviresArrives) {
                if (navire.getType() == TypeNavire.FERRY) {
                    _infoJeu.ajoutMessage(navire.getNom() + "  " + navire.getType(), TypeMessage.AVERTISSEMENT);
                } else {
                    _infoJeu.ajoutMessage(navire.getNom() + "  " + navire.getType(), TypeMessage.NORMAL);
                }
            }
            _infoJeu.ajoutMessage("Navires arrivés le " + Partie._tempsCourant + " : ", TypeMessage.IMPORTANT);
        }
    }
    
    private void majNaviresArrivant(ArrayList<Navire> naviresArrivant) {
        _naviresArrivant.ajouterNavires(_instance.getNavires(Partie._tempsCourant+1));
        _naviresArrivant.supprimerNavires(Partie._tempsCourant);
    }
    
    private void majNbRetards() {
        Partie._nbRetards += _naviresArrives.getNbNaviresEnRetard();
    }
    
    private void actualiserPanels() {
        _partie.actualiser();
        _naviresArrivant.actualiser();
        _naviresArrives.actualiser();
    }
    
    private void incrementerTemps(int compteur) {
        if(compteur != 0) {
            Partie._tempsCourant++;
        }
    }
    
    private int pausethread(int sleep) {
        try {
            sleep(sleep);
        } catch (InterruptedException ex) {
            Logger.getLogger(GestionJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sleep;
    }

    @Override
    public void run() {
        ArrayList<Navire> naviresArrives;
        int compteur = 0;
        int sleep    = 200;
        
        while(Partie._tempsCourant <= Partie._tempsFin) {
            
            if(compteur%Partie._tempsTour == 0) {
                incrementerTemps(compteur);
                
                naviresArrives = _instance.getNavires(Partie._tempsCourant);
                
                majNaviresArrives(naviresArrives);
                afficherArrivesDansLog(naviresArrives);
                majNaviresArrivant(naviresArrives);
                majNbRetards();
                actualiserPanels();
            }
            
            compteur += pausethread(sleep);
            
            if(Partie._abandon) {
                break;
            }
        }
        
        if(!Partie._abandon) {
            GestionScores gScores = new GestionScores();
            Score score = gScores.sauvegarderScore();
            
            new FenetreScores(score);
        }
    }
}
