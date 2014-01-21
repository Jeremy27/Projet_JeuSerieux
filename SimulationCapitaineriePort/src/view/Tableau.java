/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import modele.ModeleJTable;
import modele.Navire;

/**
 *
 * @author jeremy
 */
public class Tableau extends JTable {

    private ModeleJTable    _modele;
    private int             _ligneSelectionnee;
    private PanelInfoForme  _panelInfo;
    

    public Tableau(String[] titres, PanelInfoForme panelInfo) {
        _panelInfo          = panelInfo;
        _modele             = new ModeleJTable(titres, new ArrayList<Navire>());
        _ligneSelectionnee  = -1;
        setModel(_modele);
        initialiserEvent();
    }
    
    public void setNavires(ArrayList<Navire> _navires) {
        for(Navire navire : _navires)
            _modele.ajouterNavire(navire);
    }
    
    public void ajouterNavire(Navire n) {
        _modele.ajouterNavire(n);
    }
    
    public Navire getNavireSelectionne() {
        return _modele.getNavire(this.getSelectedRow());
    }
    
    private void initialiserEvent() {
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(_ligneSelectionnee != getSelectedRow()) {
                    _ligneSelectionnee = getSelectedRow();
                    _panelInfo.setNomPanel("Navire");
                    _panelInfo.setInformations(_modele.getNavire(_ligneSelectionnee).getDonneesFormates());
                    _panelInfo.majInformations();
                }
            }
        });
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int ligne, int colonne) {
        Component c = super.prepareRenderer(renderer, ligne, colonne);
        if(_modele.getNavire(ligne).getDateArrivee() < PanelPartie._temps)
            c.setBackground(new Color(235, 50, 0));
        c.setForeground(Color.black);
        
        // Colorer d'une autre facon les lignes sélectionnées
        if(getSelectedRow() == ligne) {
            c.setBackground(Color.BLACK);
            c.setForeground(Color.WHITE);
        } else {
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
        }
        return c;
    }
    
    /**
     * Méthode gérant l'affichage des titres du tableau
     * @return JTableHeader modifié
     */
    @Override
    public JTableHeader getTableHeader()
    {
        JTableHeader titresTableau = super.getTableHeader();
        titresTableau.setDefaultRenderer(new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(Color.BLACK);
                c.setForeground(Color.WHITE);
                c.setFont(new Font("TimesRoman", Font.BOLD, 13));
                return c;
            }
        });
        return titresTableau;
    }
}
