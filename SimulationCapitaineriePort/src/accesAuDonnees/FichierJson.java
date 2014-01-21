package accesAuDonnees;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

public class FichierJson {
    private String _nomFichier;
    public FichierJson(String nomFichier) {
        _nomFichier = nomFichier;
    }
    
    public JsonArray getJson() {
        Fichier f = new Fichier(_nomFichier);
        String contenu = f.lire();
        JsonReader jr = Json.createReader(new StringReader(contenu));
        JsonArray ja = jr.readArray();
        jr.close();
        return ja;
    }
    
//    public static void main(String[] args) {
//        FichierJson fj = new FichierJson("C:\\Users\\brokep\\Downloads\\osm2json-master\\data\\map.json");
//        System.out.println(fj.getJson().size());
//    }
}
