/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jeremy
 */
public class ModeleJTable extends AbstractTableModel {

    private String[] _titres;
    private ArrayList<Navire> _navires;

    public ModeleJTable(String[] titres) {
        _titres = titres;
        _navires = new ArrayList<>();
    }

    public ModeleJTable(String[] titres, ArrayList<Navire> navires) {
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
        switch (columnIndex) {
            case 0:
                return _navires.get(rowIndex).getNom();
            case 1:
                return _navires.get(rowIndex).getDateArrivee();
            case 2:
                break;
        }
        return _navires.get(rowIndex);
    }

    public void ajouterNavire(Navire n) {
        _navires.add(n);
        fireTableRowsInserted(_navires.size(), _navires.size());
    }

    public void supprimerNavire(int rowIndex) {
        _navires.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

}
