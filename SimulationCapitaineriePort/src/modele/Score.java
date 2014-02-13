/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele;

import modele.enumeration.TypeDifficulte;

/**
 *
 * @author jeremy
 */
public class Score implements Comparable<Score>{
    
    private String          _pseudo;
    private TypeDifficulte  _difficulte;
    private int             _nbRetard;

    public Score(String _pseudo, TypeDifficulte _difficulte, int _nbRetard) {
        this._pseudo        = _pseudo;
        this._difficulte    = _difficulte;
        this._nbRetard      = _nbRetard;
    }

    public TypeDifficulte getDifficulte() {
        return _difficulte;
    }

    public int getNbRetard() {
        return _nbRetard;
    }

    public String getPseudo() {
        return _pseudo;
    }

    public void setDifficulte(TypeDifficulte _difficulte) {
        this._difficulte = _difficulte;
    }

    public void setNbRetard(int _nbRetard) {
        this._nbRetard = _nbRetard;
    }

    public void setPseudo(String _pseudo) {
        this._pseudo = _pseudo;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        str.append("Pseudo : ");
        str.append(_pseudo);
        str.append("\n");
        str.append("Difficulté : ");
        str.append(_difficulte);
        str.append("\n");
        str.append("Nombre de retard : ");
        str.append(_nbRetard);
        
        return str.toString();
    }

    @Override
    public int compareTo(Score t) {
        if(this._nbRetard < t._nbRetard)
            return -1;
        else if (this._nbRetard > t._nbRetard)
            return 1;
        else
            return 0;
    }
    
    
}
