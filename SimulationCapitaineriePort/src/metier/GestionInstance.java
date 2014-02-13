/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.util.Random;
import modele.Instance;
import modele.Navire;
import modele.Partie;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeNavire;
import modele.outils.ParamsNavire;

/**
 *
 * @author Jérémy
 */
public class GestionInstance {
    private Random      _random;
    private Instance    _instance;

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
                navire = getNavireAleatoire(numNavire++);
                navire.setDateArrivee(i);
                _instance.ajouterNavire(navire);
            }
        }
    }
    
    public Navire getNavireAleatoire(int numNavire) {
        ParamsNavire params = new ParamsNavire();
        
        params.setNom("Navire "+numNavire);
        params.setLargeur(getLargeurAleatoire());
        params.setLongueur(getLongueurAleatoire());
        params.setTempsPriseEnCharge(getTempsPriseEnChargeAleatoire());
        params.setTypeMarchandise(getTypeMarchandiseAleatoire());
        params.setTypeNavire(getTypeNavireAleatoire());
        
        return new Navire(params);
    }
    
    public double getLargeurAleatoire() {
        return _random.nextDouble()/1000;
    }
    
    public double getLongueurAleatoire() {
        return _random.nextDouble()/200;
    }
    
    public int getTempsPriseEnChargeAleatoire() {
        return _random.nextInt(10);
    }
    
    public TypeMarchandise getTypeMarchandiseAleatoire() {
        int indice = _random.nextInt(TypeMarchandise.values().length);
        return TypeMarchandise.values()[indice];
    }
    
    public TypeNavire getTypeNavireAleatoire() {
        int indice = _random.nextInt(TypeNavire.values().length);
        return TypeNavire.values()[indice];
    }
    
    public Instance getInstance() {
        return _instance;
    }
}
