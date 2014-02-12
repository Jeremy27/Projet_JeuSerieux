/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modele.outils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import metier.DeplacementBateaux;

/**
 *
 * @author brokep
 */
public class PointPathFinding implements Comparable<PointPathFinding>{
    private Point2D _point;
    private int _cout;
    private double _distance;
    private PointPathFinding _voisinGauche;
    private PointPathFinding _voisinDroit;
    private PointPathFinding _voisinHaut;
    private PointPathFinding _voisinBas;
    private final ArrayList<PointPathFinding> _voisins = new ArrayList<>();
    
    public PointPathFinding(Point2D p, int cout) {
        _point = p;
        _cout = cout;
    }

    public void setDistance(double _distance) {
        this._distance = _distance;
    }

    public double getDistance() {
        return _distance;
    }

    /**
     * @return the _point
     */
    public Point2D getPoint() {
        return _point;
    }

    /**
     * @param _point the _point to set
     */
    public void setPoint(Point2D _point) {
        this._point = _point;
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
//    
//    public boolean estVoisin(PointPathFinding p) {
//        double diffX = _point.getX() - p._point.getX(); //différence des axes X
//        double diffY = _point.getY() - p._point.getY(); //différence des axes Y
//        
//        return Math.round((Math.abs(diffY)+Math.abs(diffX))*10000)/10000.0==DeplacementBateaux.PASVOISIN;
//    }
    
    
    
    @Override
    public int compareTo(PointPathFinding p) {
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
    
    public boolean egal(Point2D p) {
        //
        double x = Math.abs(p.getX()-_point.getX());
        double y = Math.abs(p.getY()-_point.getY());
        //
        return x<=DeplacementBateaux.PASVOISIN && y <=DeplacementBateaux.PASVOISIN;
    }
    
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
     * @param _voisinGauche the _voisinGauche to set
     */
    public void setVoisinGauche(PointPathFinding _voisinGauche) {
        this._voisinGauche = _voisinGauche;
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
     * @param _voisinDroit the _voisinDroit to set
     */
    public void setVoisinDroit(PointPathFinding _voisinDroit) {
        this._voisinDroit = _voisinDroit;
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
     * @param _voisinHaut the _voisinHaut to set
     */
    public void setVoisinHaut(PointPathFinding _voisinHaut) {
        this._voisinHaut = _voisinHaut;
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
     * @param _voisinBas the _voisinBas to set
     */
    public void setVoisinBas(PointPathFinding _voisinBas) {
        this._voisinBas = _voisinBas;
        if(!_voisins.contains(_voisinBas)) {
            _voisins.add(_voisinBas);
        }
    }
    
    public ArrayList<PointPathFinding> getVoisins() {
        return _voisins;
    }
}
