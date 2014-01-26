package accesAuDonnees;

import javax.json.JsonArray;

public class ADMap {
    //fichier source json
    private final String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map_filtree.json";
    
    public JsonArray getContenu() {
        FichierJson fj = new FichierJson(_nomFichier);
        return fj.getJson();
    }
}
