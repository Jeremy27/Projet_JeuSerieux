/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.modele;

import java.util.Objects;
import jeuserieux.modele.enumeration.TypeDifficulte;

/**
 *
 * @author jeremy
 */
public class Score implements Comparable<Score>{
    
    private String          _pseudo;
    private TypeDifficulte  _difficulte;
    private int             _nbRetard;
    private long            _tempsMilliSec;

    public Score() {
    }
    
    public Score(String _pseudo, TypeDifficulte _difficulte, int _nbRetard) {
        this._pseudo        = _pseudo;
        this._difficulte    = _difficulte;
        this._nbRetard      = _nbRetard;
        _tempsMilliSec      = System.currentTimeMillis();
    }
    
    public Score(String _pseudo, TypeDifficulte _difficulte, int _nbRetard, long tempsMilliSec) {
        this._pseudo        = _pseudo;
        this._difficulte    = _difficulte;
        this._nbRetard      = _nbRetard;
        _tempsMilliSec      = tempsMilliSec;
    }

    public long getTempsMilliSec() {
        return _tempsMilliSec;
    }

    public void setTempsMilliSec(long tempsMilliSec) {
        this._tempsMilliSec = tempsMilliSec;
    }
    
    public static Score getScoreCourant() {
        return new Score(Partie._pseudo, Partie._difficulte, Partie._nbRetards);
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
        str.append("\n");
        str.append("Temps : ");
        str.append(_tempsMilliSec);
        
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this._pseudo);
        hash = 97 * hash + Objects.hashCode(this._difficulte);
        hash = 97 * hash + this._nbRetard;
        hash = 97 * hash + (int) (this._tempsMilliSec ^ (this._tempsMilliSec >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Score other = (Score) obj;
        if (!Objects.equals(this._pseudo, other._pseudo)) {
            return false;
        }
        if (this._difficulte != other._difficulte) {
            return false;
        }
        if (this._nbRetard != other._nbRetard) {
            return false;
        }
        if (this._tempsMilliSec != other._tempsMilliSec) {
            return false;
        }
        return true;
    }

   
}
