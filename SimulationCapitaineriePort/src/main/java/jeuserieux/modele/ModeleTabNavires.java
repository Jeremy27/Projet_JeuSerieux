/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuserieux.modele;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import jeuserieux.metier.GestionJeu;

/**
 *
 * @author jeremy
 */
public class ModeleTabNavires extends AbstractTableModel {

    private String[]            _titres;
    private ArrayList<Navire>   _navires;

    public ModeleTabNavires(String[] titres) {
        _titres = titres;
        _navires = new ArrayList<>();
    }

    public ModeleTabNavires(String[] titres, ArrayList<Navire> navires) {
        this._titres = titres;
        this._navires = navires;
    }

    public String[] getTitres() {
        return _titres;
    }

    public void setTitres(String[] _titres) {
        this._titres = _titres;
    }

    public ArrayList<Navire> getNavires() {
        return _navires;
    }

    public Navire getNavire(int ligne) {
        return _navires.get(ligne);
    }

    public void setNavires(ArrayList<Navire> _navires) {
        this._navires = _navires;
    }

    @Override
    public int getColumnCount() {
        return _titres.length;
    }

    @Override
    public String getColumnName(int column) {
        return _titres[column];
    }

    @Override
    public int getRowCount() {
        return _navires.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Navire navire = _navires.get(rowIndex);
        if(!navire.estAssigneQuai()) {
            switch (columnIndex) {
                case 0:
                    return navire.getNom();
                case 1:
                    return GestionJeu.getDateFormate(navire.getDateArrivee());
            }
        }
        return null;
    }

    public void ajouterNavire(Navire n) {
        _navires.add(n);
        fireTableRowsInserted(_navires.size(), _navires.size());
    }

    public void supprimerNavire(int rowIndex) {
        _navires.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    public void supprimerNaviresArrives(int temps) {
        ArrayList<Navire> aSupprimer = new ArrayList<>();
        for(Navire navire : _navires)
            if(navire.getDateArrivee() <= temps)
                aSupprimer.add(navire);
        
        while(!aSupprimer.isEmpty()) {
            _navires.remove(aSupprimer.remove(0));
        }
    }
    
    public void supprimerNavires() {
        _navires = new ArrayList<>();
    }
}
