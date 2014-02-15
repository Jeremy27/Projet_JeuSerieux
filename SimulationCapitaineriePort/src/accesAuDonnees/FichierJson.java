package accesAuDonnees;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

/**
 * Cette classe lit un fichier json et retourne son contenu sous forme de JsonArray
 * @author gary
 */
public class FichierJson {
    private final String _nomFichier;
    public FichierJson(String nomFichier) {
        _nomFichier = nomFichier;
    }
    
    /**
     * Cette fonction lit un fichier json et retourne son contenu sous forme de JsonArray
     * @return le contenu sous forme d'array
     * @see javax.json.JsonArray
     */
    public JsonArray getJson() {
        Fichier f = new Fichier(_nomFichier);
        String contenu = f.lire();
        JsonArray ja;
        try (JsonReader jr = Json.createReader(new StringReader(contenu))) {
            ja = jr.readArray();
        }
        return ja;
    }
}
