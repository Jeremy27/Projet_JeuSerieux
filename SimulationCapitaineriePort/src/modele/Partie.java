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
    public static String  _difficulte   = "Facile";
    public static int     _tempsCourant = 0;
    public static int     _tempsFin     = 100;
    public static int     _nbRetards;
    public static int     _nbMaxNavires;
    public static int     _nbMinNavires;
    public static int     _tempsTour;
    public static boolean _abandon;
    
    public Partie(String difficulte) {
        _difficulte     = difficulte;
        _abandon        = false;
        _tempsCourant   = 0;
        _tempsFin       = 100;
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
    
    public static String toStringFacile() {
        StringBuilder str = new StringBuilder();
        
        str.append("Niveau de difficulté : Facile");
        str.append("<br/><br/>");
        str.append("Nombre de navires minimum par tour : 0");
        str.append("<br/><br/>");
        str.append("Nombre de navires maximum par tour : 3");
        str.append("<br/><br/>");
        str.append("Temps entre deux tours : 30s");
        
        return str.toString();
    }
    
    public static String toStringNormal() {
        StringBuilder str = new StringBuilder();
        
        str.append("Niveau de difficulté : Normal");
        str.append("<br/><br/>");
        str.append("Nombre de navires minimum par tour : 1");
        str.append("<br/><br/>");
        str.append("Nombre de navires maximum par tour : 5");
        str.append("<br/><br/>");
        str.append("Temps entre deux tours : 30s");
        
        return str.toString();
    }
    
    public static String toStringDifficile() {
        StringBuilder str = new StringBuilder();
        
        str.append("Niveau de difficulté : Difficile");
        str.append("<br/><br/>");
        str.append("Nombre de navires minimum par tour : 2");
        str.append("<br/><br/>");
        str.append("Nombre de navires maximum par tour : 7");
        str.append("<br/><br/>");
        str.append("Temps entre deux tours : 30s");
        
        return str.toString();
    }
}
