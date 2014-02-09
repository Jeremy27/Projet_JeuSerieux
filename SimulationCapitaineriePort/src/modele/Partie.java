/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

/**
 *
 * @author jeremy
 */
public class Partie {
    
    public static String  _pseudo;
    public static String  _difficulte;
    public static int     _tempsCourant;
    public static int     _tempsFin;
    public static int     _nbRetards;
    public static int     _nbMaxNavires;
    public static int     _nbMinNavires;
    public static int     _tempsTour;
    public static boolean _abandon;
    
    public Partie(String difficulte) {
        _difficulte = difficulte;
        _abandon    = false;
        initialiserDifficulte();
    }
    
    public final void initialiserDifficulte() {
        switch (_difficulte) {
            case "Facile":
                initialiserFacile();
                break;
            case "Normal":
                initialiserNormal();
                break;
            case "Difficile":
                initialiserDifficile();
                break;
        }
    }
    
    private void initialiserFacile() {
        _nbMaxNavires   = 4;
        _nbMinNavires   = 0;
        _tempsTour      = 30000;
    }
    
    private void initialiserNormal() {
        _nbMaxNavires   = 6;
        _nbMinNavires   = 1;
        _tempsTour      = 30000;
    }
    
    private void initialiserDifficile() {
        _nbMaxNavires   = 8;
        _nbMinNavires   = 2;
        _tempsTour      = 30000;
    }
}
