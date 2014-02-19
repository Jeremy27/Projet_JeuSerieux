/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.metier;

import jeuserieux.accesAuDonnees.Fichier;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import jeuserieux.accesAuDonnees.FichierJson;
import jeuserieux.modele.Score;
import jeuserieux.modele.enumeration.TypeDifficulte;

/**
 *
 * @author jeremy
 */
public class GestionScores {
    
    private FichierJson _fichierScores;
    private Score       _score;
    
    public GestionScores() {
        _fichierScores  = new FichierJson(Fichier.CHEMIN + "scores.json");
        initialiserScore();
    }
    
    public GestionScores(String nomFichier) {
        _fichierScores  = new FichierJson(Fichier.CHEMIN + nomFichier);
        initialiserScore();
    }
    
    private void initialiserScore() {
        _score = Score.getScoreCourant();
    }
    
    public JsonObject genererObjetJson() {
        JsonBuilderFactory factory  = Json.createBuilderFactory(null);
        JsonObject objetJson        = factory.createObjectBuilder()
            .add("Pseudo", _score.getPseudo())
            .add("Difficulté", _score.getDifficulte().name())
            .add("Nombre de retard", _score.getNbRetard())
            .add("Temps", _score.getTempsMilliSec()).build();
        
        return objetJson;
    }
    
    public ArrayList<Score> getTableauScores() {
        ArrayList<Score> listeScores    = new ArrayList<>();
        JsonArray tableauScores         = _fichierScores.getJson();
        
        for (Object objet : tableauScores.toArray()) {
            JsonObject jo   = (JsonObject) objet;
            String pseudo   = jo.get("Pseudo").toString();
            int nbRetard    = Integer.parseInt(jo.get("Nombre de retard").toString());
            TypeDifficulte difficulte = TypeDifficulte.valueOf(jo.get("Difficulté").toString());
            long tempsMilliSec        = Long.parseLong(jo.get("Temps").toString());
            listeScores.add(new Score(pseudo, difficulte, nbRetard, tempsMilliSec));
        }
        
        return listeScores;
    }
    
    public Score sauvegarderScore() {
        JsonArrayBuilder nouveauTableau = Json.createArrayBuilder();
        JsonObject objetJson            = genererObjetJson();
        JsonArray tableauJson           = _fichierScores.getJson();
        
        for(int i=0; i<tableauJson.size(); i++) {
            nouveauTableau.add(tableauJson.get(i));
        }
        nouveauTableau.add(objetJson);
        _fichierScores.ecrire(nouveauTableau.build(), false);
        
        return _score;
    }
}
