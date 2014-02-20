package jeuserieux.metier;

import java.util.ArrayList;
import java.util.Random;
import javax.json.JsonArray;
import javax.json.JsonObject;
import jeuserieux.accesAuDonnees.Fichier;
import jeuserieux.accesAuDonnees.FichierJson;
import jeuserieux.modele.Instance;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Partie;
import jeuserieux.modele .enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeNavire;
import jeuserieux.modele.outils.ParamsNavire;

/**
 *
 * @author Jérémy
 */
public class GestionInstance {
    private Random      _random;
    private Instance    _instance;
    private boolean     _aleatoire;

    public GestionInstance() {
        _instance = new Instance();
        _random   = new Random();
    }
    
    public void genererAleatoirement() {
        Navire navire;
        int numNavire = 0;
        int nbNavires;
        for (int i = 0; i < Partie._tempsFin; i++) {
            nbNavires = _random.nextInt(Partie._nbMaxNavires) + Partie._nbMinNavires;
            for (int j = 0; j < nbNavires; j++) {
                navire = genererNavireAleatoire(numNavire++);
                navire.setDateArrivee(i);
                _instance.ajouterNavire(navire);
            }
        }
    }
    
    public void genererInstance() {
        ArrayList<Navire> listeNavires = getListeNaviresReels();
        Navire navire;
        int nbNavires;
        for (int i = 0; i < Partie._tempsFin; i++) {
            nbNavires = _random.nextInt(Partie._nbMaxNavires) + Partie._nbMinNavires;
            for (int j = 0; j < nbNavires; j++) {
                navire = listeNavires.get(_random.nextInt(listeNavires.size()));
                navire.setDateArrivee(i);
                navire = new Navire(navire);
                _instance.ajouterNavire(navire);
            }
        }
    }
    
    private ArrayList<Navire> getListeNaviresReels() {
        String nomFichier = Fichier.CHEMIN + "navires.json";
        System.out.println(nomFichier);
        FichierJson fichier = new FichierJson(nomFichier);
        ArrayList<Navire> listeNavires = new ArrayList<>();
        
        JsonArray tableauNavires = fichier.getJson();
        
        for (Object objet : tableauNavires.toArray()) {
            JsonObject jo           = (JsonObject) objet;
            String nom              = jo.getString("nom");
            TypeMarchandise type    = TypeMarchandise.valueOf(jo.getString("type"));
            int longueur            = Integer.parseInt(jo.get("longueur").toString());
            int largeur             = Integer.parseInt(jo.get("largeur").toString());
            
            listeNavires.add(creerNavire(nom, type, longueur, largeur));
        }
        
        return listeNavires;
    }
    
    private Navire genererNavireAleatoire(int numNavire) {
        ParamsNavire params = new ParamsNavire();
        
        params.setNom("Navire "+numNavire);
        params.setLongueur(getLongueurAleatoire());
        params.setLargeur(params.getLongueur()/3);
        params.setTempsPriseEnCharge(getTempsPriseEnChargeAleatoire());
        TypeMarchandise typeMarchandise = getTypeMarchandiseAleatoire();
        params.setTypeMarchandise(typeMarchandise);
        params.setTypeNavire(getTypeNavire(typeMarchandise));
        
        return new Navire(params);
    }
    
    private Navire creerNavire(String nom, TypeMarchandise typeMarchandise, int longueur, int largeur) {
        ParamsNavire params = new ParamsNavire();
        
        params.setNom(nom);
        params.setLongueur(longueur);
        params.setLargeur(largeur);
        params.setTempsPriseEnCharge(getTempsPriseEnChargeAleatoire());
        params.setTypeMarchandise(typeMarchandise);
        params.setTypeNavire(getTypeNavire(typeMarchandise));
        
        return new Navire(params);
    }
    
    private double getLargeurAleatoire() {
        return _random.nextInt(55)+5;
    }
    
    private double getLongueurAleatoire() {
        return _random.nextInt(330)+30;
    }
    
    private int getTempsPriseEnChargeAleatoire() {
        return _random.nextInt(9)+1;
    }
    
    private TypeMarchandise getTypeMarchandiseAleatoire() {
        int indice = _random.nextInt(TypeMarchandise.values().length);
        return TypeMarchandise.values()[indice];
    }
    
    private TypeNavire getTypeNavire(TypeMarchandise typeMarchandise) {
        TypeNavire typeNavire = TypeNavire.PETROLIER;
        
        if(typeMarchandise.equals(TypeMarchandise.CHARBON)) {
            typeNavire = TypeNavire.VRAC_SOLIDE;
        } else if(typeMarchandise.equals(TypeMarchandise.CONTENEURS)) {
            typeNavire = TypeNavire.PORTE_CONTENEURS;
        } else if(typeMarchandise.equals(TypeMarchandise.EOLIEN)) {
            typeNavire = TypeNavire.EOLIEN;
        } else if(typeMarchandise.equals(TypeMarchandise.PASSAGER)) {
            typeNavire = TypeNavire.FERRY;
        } else if(typeMarchandise.equals(TypeMarchandise.PETROLIER)) {
            typeNavire = TypeNavire.PETROLIER;
        } else if(typeMarchandise.equals(TypeMarchandise.ROULIER)) {
            typeNavire = TypeNavire.RO_RO;
        }
        return typeNavire;
    }
    
    public Instance getInstance() {
        return _instance;
    }
}
