package jeuserieux.metier;

import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Partie;
import jeuserieux.modele.Quai;
import jeuserieux.presentation.PanelMap;

/**
 *
 * @author Jérémy
 */
public class GestionSauvegarde {
    private PanelMap _map;

    public GestionSauvegarde(PanelMap map) {
        _map = map;
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
        
        for(Navire navire : GestionJeu._instance.getNavires()) {
            
//            if(!navireAQuai(navire) || !navireDejaTraite(navire)) {
                objetJson.add("nom", navire.getNom());
                objetJson.add("typeNavire", navire.getType().name());
                objetJson.add("typeMarchandise", navire.getTypeMachandise().name());
                objetJson.add("dateArrivee", navire.getNom());
                objetJson.add("longueur", navire.getLongueur());
                objetJson.add("largeur", navire.getLargeur());
                objetJson.add("tempsPriseEnCharge", navire.getTempsPriseEnCharge());
                objetJson.add("nom", navire.getNom());
                objetJson.add("nom", navire.getNom());
                objetJson.add("nom", navire.getNom());
                objetJson.add("nom", navire.getNom());
                objetJson.add("nom", navire.getNom());
                objetJson.add("nom", navire.getNom());
                
//            }
        }
        
        return objetJson.build();
    }
    
    private JsonObject getQuaisJson() {
        JsonObjectBuilder objetJson = Json.createObjectBuilder();
        
        //objetJson.add(null, BigDecimal.ZERO)
        
        return objetJson.build();
    }
    
    private boolean navireDejaTraite(Navire navire) {
        return navire.estAssigneQuai() && Partie._tempsCourant > navire.getDateArrivee();
    }
    
    private boolean navireAQuai(Navire navire) {
        ArrayList<Quai> listeQuais = _map.getMetierMap().getQuais();
        
        for(Quai quai : listeQuais) {
            for(Navire n : quai.getNaviresAQuai()) {
                if(n.equals(navire))
                    return true;
            }
        }
        return false;
    }
}
