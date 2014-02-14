package modele.outils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import metier.DeplacementBateaux;

/**
 * Cette classe est utilisée pour le pathfinding
 * @see java.awt.geom.Point2D.Double
 * @author gary
 */
public class PointPathFinding extends Point2D.Double implements Comparable<PointPathFinding>{
    private int _cout;
    private double _distance;
    private PointPathFinding _voisinGauche;
    private PointPathFinding _voisinDroit;
    private PointPathFinding _voisinHaut;
    private PointPathFinding _voisinBas;
    private PointPathFinding _voisinBasGauche;
    private PointPathFinding _voisinBasDroit;
    private PointPathFinding _voisinHautGauche;
    private PointPathFinding _voisinHautDroit;
    private final ArrayList<PointPathFinding> _voisins = new ArrayList<>();
    
    public PointPathFinding(double x, double y, int cout) {
        this.x = x;
        this.y = y;
        _cout = cout;
    }

    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    public double getDistance() {
        return _distance;
    }
    /**
     * @return the _cout
     */
    public int getCout() {
        return _cout;
    }

    /**
     * @param _cout the _cout to set
     */
    public void setCout(int _cout) {
        this._cout = _cout;
    }
    
    
    
    @Override
    public int compareTo(PointPathFinding p) {
//        if(_point.getX()==p._point.getX() && _point.getY()==p._point.getY()) {
//            return 0;
//        }
        if(_cout==p._cout) {
            if(this._distance>p._distance) {
                return -1;
            } else {
                return 1;
            }
        } else {
            if(this._cout>p._cout) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    
    /**
     * Teste si le point en paramètre est équivalent à l'objet en cours
     * @param p point dont l'équivalence avec l'objet courant est à tester
     * @return true si la distance entre les deux points est inférieure au pas de pathfinding (0.0008)
     * @see metier.DeplacementBateaux
     */
    public boolean egal(Point2D p) {
        //
        double x = Math.abs(p.getX()-this.x);
        double y = Math.abs(p.getY()-this.y);
        //
        return x<=DeplacementBateaux.PASVOISIN && y <=DeplacementBateaux.PASVOISIN;
    }
    
    /**
     * Met à jour les coûts du PointPathFinding courant selon le coût de ses voisins si nécessaire
     */
    public void majCouts() {
        int min = _cout;
        for(PointPathFinding voisin:_voisins) {
            if(min>voisin.getCout()+1) {
                min = voisin.getCout()+1;
            }
        }
        _cout = min;
    }

    /**
     * @return the _voisinGauche
     */
    public PointPathFinding getVoisinGauche() {
        return _voisinGauche;
    }

    /**
     * @param voisinGauche the _voisinGauche to set
     */
    public void setVoisinGauche(PointPathFinding voisinGauche) {
        this._voisinGauche = voisinGauche;
        if(!_voisins.contains(_voisinGauche)) {
            _voisins.add(_voisinGauche);
        }
    }

    /**
     * @return the _voisinDroit
     */
    public PointPathFinding getVoisinDroit() {
        return _voisinDroit;
    }

    /**
     * @param voisinDroit the _voisinDroit to set
     */
    public void setVoisinDroit(PointPathFinding voisinDroit) {
        this._voisinDroit = voisinDroit;
        if(!_voisins.contains(_voisinDroit)) {
            _voisins.add(_voisinDroit);
        }
    }

    /**
     * @return the _voisinHaut
     */
    public PointPathFinding getVoisinHaut() {
        return _voisinHaut;
    }

    /**
     * @param voisinHaut the _voisinHaut to set
     */
    public void setVoisinHaut(PointPathFinding voisinHaut) {
        this._voisinHaut = voisinHaut;
        if(!_voisins.contains(_voisinHaut)) {
            _voisins.add(_voisinHaut);
        }
    }

    /**
     * @return the _voisinBas
     */
    public PointPathFinding getVoisinBas() {
        return _voisinBas;
    }

    /**
     * @param voisinBas the _voisinBas to set
     */
    public void setVoisinBas(PointPathFinding voisinBas) {
        this._voisinBas = voisinBas;
        if(!_voisins.contains(_voisinBas)) {
            _voisins.add(_voisinBas);
        }
    }
    
    public ArrayList<PointPathFinding> getVoisins() {
        return _voisins;
    }

    /**
     * @return the _voisinBasGauche
     */
    public PointPathFinding getVoisinBasGauche() {
        return _voisinBasGauche;
    }

    /**
     * @param voisinBasGauche the _voisinBasGauche to set
     */
    public void setVoisinBasGauche(PointPathFinding voisinBasGauche) {
        _voisinBasGauche = voisinBasGauche;
        if(!_voisins.contains(_voisinBasGauche)) {
            _voisins.add(_voisinBasGauche);
        }
    }

    /**
     * @return the _voisinBasDroit
     */
    public PointPathFinding getVoisinBasDroit() {
        return _voisinBasDroit;
    }

    /**
     * @param voisinBasDroit the _voisinBasDroit to set
     */
    public void setVoisinBasDroit(PointPathFinding voisinBasDroit) {
        _voisinBasDroit = voisinBasDroit;
        if(!_voisins.contains(_voisinBasDroit)) {
            _voisins.add(_voisinBasDroit);
        }
    }

    /**
     * @return the _voisinHautGauche
     */
    public PointPathFinding getVoisinHautGauche() {
        return _voisinHautGauche;
    }

    /**
     * @param voisinHautGauche the _voisinHautGauche to set
     */
    public void setVoisinHautGauche(PointPathFinding voisinHautGauche) {
        _voisinHautGauche = voisinHautGauche;
        if(!_voisins.contains(_voisinHautGauche)) {
            _voisins.add(_voisinHautGauche);
        }
    }

    /**
     * @return the _voisinHautDroit
     */
    public PointPathFinding getVoisinHautDroit() {
        return _voisinHautDroit;
    }

    /**
     * @param voisinHautDroit the _voisinHautDroit to set
     */
    public void setVoisinHautDroit(PointPathFinding voisinHautDroit) {
        _voisinHautDroit = voisinHautDroit;
        if(!_voisins.contains(_voisinHautDroit)) {
            _voisins.add(_voisinHautDroit);
        }
    }
}
