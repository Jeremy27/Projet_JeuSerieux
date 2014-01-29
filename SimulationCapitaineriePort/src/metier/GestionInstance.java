/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.util.Random;
import modele.Instance;
import modele.Navire;
import modele.enumeration.TypeMarchandise;
import modele.enumeration.TypeNavire;
import modele.outils.ParamsNavire;
import presentation.PanelPartie;

/**
 *
 * @author Jérémy
 */
public class GestionInstance {
    private Random      _random;
    private Instance    _instance;

    public GestionInstance() {
        _instance = new Instance();
    }
    
    public void genererAleatoirement() {
        Navire navire;
        int numNavire = 0;
        for (int i = 0; i < PanelPartie._tempsFin; i++) {
            for (int j = 0; j < _random.nextInt(10); j++) {
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
        return _random.nextDouble();
    }
    
    public double getLongueurAleatoire() {
        return _random.nextDouble();
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
