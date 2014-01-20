/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import modele.Navire;

/**
 *
 * @author jeremy
 */
public class Tableau extends JTable {

    private ModeleJTable modele;

    public Tableau(ArrayList<String> titres) {
        modele = new ModeleJTable(titres);
        setModel(modele);
    }
    
    public void setNavires(ArrayList<Navire> _navires) {
        for(Navire navire : _navires)
            modele.ajouterNavire(navire);
    }
    
    public void ajouterNavire(Navire n) {
        modele.ajouterNavire(n);
    }

    
    
    public class ModeleJTable extends AbstractTableModel {

        private ArrayList<String> _titres;
        private ArrayList<Navire> _navires;

        public ModeleJTable(ArrayList<String> titres) {
            _titres = titres;
            _navires = new ArrayList<>();
        }

        public ModeleJTable(ArrayList<String> titres, ArrayList<Navire> navires) {
            this._titres = titres;
            this._navires = navires;
        }

        public ArrayList<String> getTitres() {
            return _titres;
        }

        public void setTitres(ArrayList<String> _titres) {
            this._titres = _titres;
        }

        public ArrayList<Navire> getNavires() {
            return _navires;
        }

        public void setNavires(ArrayList<Navire> _navires) {
            this._navires = _navires;
        }

        @Override
        public int getColumnCount() {
            return _titres.size();
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
                case 3:
                    break;
                case 4:
                    break;
            }
            return _navires.get(rowIndex);
        }

        @Override
        public String getColumnName(int column) {
            return _titres.get(column);
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

}
