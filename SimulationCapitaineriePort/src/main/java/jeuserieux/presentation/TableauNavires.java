/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuserieux.presentation;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import jeuserieux.modele.ModeleTabNavires;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Partie;

/**
 *
 * @author jeremy
 */
public class TableauNavires extends JTable {

    private ModeleTabNavires    _modele;
    private int             _ligneSelectionnee;
    private PanelInfoForme  _panelInfo;
    private PanelMap _map;
    

    public TableauNavires(String[] titres, PanelInfoForme panelInfo) {
        _panelInfo          = panelInfo;
        _modele             = new ModeleTabNavires(titres, new ArrayList<Navire>());
        _ligneSelectionnee  = -1;
        setModel(_modele);
        initialiserEvent();
    }
    
    public void setMap(PanelMap map) {
        _map = map;
    }
    
    public void setNavires(ArrayList<Navire> navires) {
        for(Navire navire : navires)
            _modele.ajouterNavire(navire);
    }
    
    public ArrayList<Navire> getNavires() {
        return _modele.getNavires();
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
            if(navire.getDateArrivee() < Partie._tempsCourant)
                nbRetards++;
        return nbRetards;
    }
    
    public void supprimerNavireArrives(int temps) {
        _modele.supprimerNaviresArrives(temps);
    }
    
    public void remiseAZero() {
        _modele.supprimerNavires();
    }
    
    private void majPanelNavire() {
        if(_ligneSelectionnee != getSelectedRow()) {
            _ligneSelectionnee = getSelectedRow();
            Navire n = _modele.getNavire(_ligneSelectionnee);
            _map.setNavireSelectionne(n);
            _map.refresh();
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
            c.setBackground(PanelPerso.COULEUR_BACKGROUND_TITRE);
            c.setForeground(PanelPerso.COULEUR_ECRITURE);
        } else {
            if(_modele.getNavire(ligne).getDateArrivee() < Partie._tempsCourant) {
                c.setBackground(PanelPerso.COULEUR_RETARD);
                c.setForeground(PanelPerso.COULEUR_ECRITURE);
            }
            else
                if(ligne%2 == 0)
                    c.setBackground(PanelPerso.COULEUR_PANEL1);
                else
                    c.setBackground(PanelPerso.COULEUR_PANEL2);
            c.setForeground(PanelPerso.COULEUR_ECRITURE);
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
                c.setBackground(PanelPerso.COULEUR_BACKGROUND_TITRE);
                c.setForeground(PanelPerso.COULEUR_ECRITURE);
                c.setFont(new Font(this.getFont().getName(), Font.BOLD, 13));
                return c;
            }
        });
        return titresTableau;
    }
}
