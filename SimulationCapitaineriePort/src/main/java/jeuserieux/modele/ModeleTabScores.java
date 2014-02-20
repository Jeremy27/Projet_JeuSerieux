/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.modele;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jeremy
 */
public class ModeleTabScores extends AbstractTableModel {
    
    private ArrayList<String>   _titres;
    private ArrayList<Score>    _scores;

    public ModeleTabScores() {
        this._titres = new ArrayList<>();
        _titres.add("Pseudo");
        _titres.add("Retards (heures)");
        this._scores = new ArrayList<>();
    }
    
    public ModeleTabScores(ArrayList<String> titres) {
        _titres = titres;
        _scores = new ArrayList<>();
    }
    
    public void ajouterScore(Score score) {
        _scores.add(score);
    }
    
    public void trierScores() {
        Collections.sort(_scores);
    }

    @Override
    public String getColumnName(int colonne) {
        return _titres.get(colonne);
    }

    @Override
    public int getRowCount() {
        return _scores.size();
    }

    @Override
    public int getColumnCount() {
        return _titres.size();
    }
    
    public Score getScore(int ligne) {
        return _scores.get(ligne);
    }

    @Override
    public Object getValueAt(int ligne, int colonne) {
       switch (colonne) {
            case 0:
                return _scores.get(ligne).getPseudo();
            case 1:
                return _scores.get(ligne).getNbRetard();
            case 2:
                return _scores.get(ligne).getDifficulte();
        }
       return _scores.get(ligne);
    }
    
    public ArrayList<Score> getScores() {
        return _scores;
    }
}
