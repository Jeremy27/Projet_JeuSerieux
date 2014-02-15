package accesAuDonnees;

import javax.json.JsonArray;

/**
 * Cette classe permet, à partir du fichier json source de la carte OSM, de renvoyer un array(JsonArray) d'objets json (JsonObject)
 * @author gary
 */
public class ADMap {
    //fichier source json
    private final String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map_filtree_4.json";
    
    /**
     * Cette fonction lit le fichier json de la carte OSM et retourne un array d'objets Json des formes
     * @return un JsonArray contenant les objets Json des formes de la carte
     */
    public JsonArray getContenu() {
        FichierJson fj = new FichierJson(_nomFichier);
        return fj.getJson();
    }
}
