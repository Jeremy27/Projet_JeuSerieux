/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.metier;

import java.util.Random;
import jeuserieux.modele.Instance;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Partie;
import jeuserieux.modele.enumeration.TypeMarchandise;
import jeuserieux.modele.enumeration.TypeNavire;
import jeuserieux.modele.outils.ParamsNavire;

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
        params.setLongueur(getLongueurAleatoire());
        params.setLargeur(params.getLongueur()/3);
        params.setTempsPriseEnCharge(getTempsPriseEnChargeAleatoire());
        params.setTypeMarchandise(getTypeMarchandiseAleatoire());
        params.setTypeNavire(getTypeNavireAleatoire());
        
        return new Navire(params);
    }
    
    public double getLargeurAleatoire() {
        return _random.nextDouble()/400;
    }
    
    public double getLongueurAleatoire() {
        return _random.nextDouble()/100;
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
