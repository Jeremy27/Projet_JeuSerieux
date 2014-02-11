/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonGeneratorFactory;
import modele.Partie;

/**
 *
 * @author jeremy
 */
public class SauvegarderScore {
    
    private String _nomFichier;
    
    public SauvegarderScore(String nomFichier) {
        _nomFichier = nomFichier;
    }
    
    public SauvegarderScore() {
        _nomFichier = "score";
    }
    
    public JsonObject genererObjetJson() {
        JsonBuilderFactory factory  = Json.createBuilderFactory(null);
        JsonObject objetJson        = factory.createObjectBuilder()
            .add("Pseudo", Partie._pseudo)
            .add("Difficulté", Partie._difficulte)
            .add("Nombre de retard", Partie._nbRetards+"").build();
        
        return objetJson;
    }
}
