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
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
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
        // A virer
        new Partie("Facile");
        Partie._pseudo = "Claudy";
        // ------
        
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
        catch(JsonException ex) {
            System.out.println("lol");
        }
        
        return tableauJson;
    }
    
    public void sauvegarderScore() {
        JsonObject objetJson = genererObjetJson();
        JsonArray tableauJson = lireFichierScores();
        
        // MOI PAS COMPRENDRE COMMENT FAIRE O_O
        
        _fichierScores.ecrire(tableauJson.toString(), false);
    }
    
    public static void main(String[] args) {
        GestionScores scores = new GestionScores();
        scores.sauvegarderScore();
        JsonArray tab = scores.lireFichierScores();
        System.out.println(tab.toString());
    }
}
