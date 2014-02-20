package jeuserieux.accesAuDonnees;

import javax.json.JsonArray;

/**
 * Cette classe permet, à partir du fichier json source de la carte OSM, de renvoyer un array(JsonArray) d'objets json (JsonObject)
 * @author gary
 */
public class ADMap {
    //fichier source json src/main/resources
    private final String _nomFichier = Fichier.CHEMIN + "map.json";
    
    /**
     * Cette fonction lit le fichier json de la carte OSM et retourne un array d'objets Json des formes
     * @return un JsonArray contenant les objets Json des formes de la carte
     */
    public JsonArray getContenu() {
        FichierJson fj = new FichierJson(_nomFichier);
        return fj.getJson();
    }
}