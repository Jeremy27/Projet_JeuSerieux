/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    
    public void setNavires(ArrayList<Navire> navires) {
        for(Navire navire : navires)
            _modele.ajouterNavire(navire);
    }
    
    public void ajouterNavire(Navire n) {
        _modele.ajouterNavire(n);
    }
    
    public Navire getNavireSelectionne() {
        return _modele.getNavire(this.getSelectedRow());
    }
    
    public int getNbNaviresEnRetard() {
        int nbRetards = 0;
        for(Navire navire : _modele.getNavires())
            if(navire.getDateArrivee() < PanelPartie._tempsCourant)
                nbRetards++;
        return nbRetards;
    }
    
    public void supprimerNavireArrives(int temps) {
        _modele.supprimerNaviresArrives(temps);
    }
    
    private void majPanelNavire() {
        if(_ligneSelectionnee != getSelectedRow()) {
            _ligneSelectionnee = getSelectedRow();
            _panelInfo.setNomPanel("Navire");
            _panelInfo.setInformations(_modele.getNavire(_ligneSelectionnee).getDonneesFormates());
            _panelInfo.majInformations();
        }
    }
    
    private void initialiserEvent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                majPanelNavire();
            }
        });
        
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                majPanelNavire();
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                majPanelNavire();
            }
        });
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int ligne, int colonne) {
        Component c = super.prepareRenderer(renderer, ligne, colonne);
        
        if(getSelectedRow() == ligne) {
            c.setBackground(Color.BLACK);
            c.setForeground(Color.WHITE);
        } else {
            if(_modele.getNavire(ligne).getDateArrivee() < PanelPartie._tempsCourant)
                c.setBackground(new Color(235, 20, 0));
            else
                if(ligne%2 == 0)
                    c.setBackground(Color.GRAY);
                else
                    c.setBackground(Color.LIGHT_GRAY);
            c.setForeground(Color.BLACK);
        }
        return c;
    }

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
                c.setForeground(Color.LIGHT_GRAY);
                c.setFont(new Font(this.getFont().getName(), Font.BOLD, 13));
                return c;
            }
        });
        return titresTableau;
    }
}
