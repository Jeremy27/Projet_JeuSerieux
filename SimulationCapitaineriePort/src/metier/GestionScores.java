/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import accesAuDonnees.Fichier;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;
import modele.Partie;

/**
 *
 * @author jeremy
 */
public class GestionScores {
    
    private Fichier _fichierScores;
    
    public GestionScores() {
        _fichierScores = new Fichier("scores");
    }
    
    public GestionScores(String nomFichier) {
        _fichierScores = new Fichier(nomFichier);
    }
    
    public JsonObject genererObjetJson() {
        JsonBuilderFactory factory  = Json.createBuilderFactory(null);
        JsonObject objetJson        = factory.createObjectBuilder()
            .add("Pseudo", Partie._pseudo)
            .add("Difficulté", Partie._difficulte)
            .add("Nombre de retard", Partie._nbRetards+"").build();
        
        return objetJson;
    }
    
    public JsonArray lireFichierScores() {
        JsonArray tableauJson = null;
        
        String scores = _fichierScores.lire();
        
        try (JsonReader jsonReader = Json.createReader(new StringReader(scores))) {
            tableauJson = jsonReader.readArray();
        }
        
        return tableauJson;
    }
    
    public void sauvegarderScore() {
        JsonBuilderFactory factory      = Json.createBuilderFactory(null);
        JsonArrayBuilder nouveauTableau = Json.createArrayBuilder();
        
        JsonObject objetJson            = genererObjetJson();
        JsonArray tableauJson           = lireFichierScores();
        
        for(int i=0; i<tableauJson.size(); i++) {
            nouveauTableau.add(tableauJson.get(i));
        }
        nouveauTableau.add(objetJson);
        
        _fichierScores.ecrire(nouveauTableau.build().toString(), false);
    }
}
