/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import modele.ModeleTabScores;
import modele.Score;

/**
 *
 * @author jeremy
 */
public class TableauScores extends JTable {
    
    private ModeleTabScores _modele;

    public TableauScores() {
        _modele = new ModeleTabScores();
        
    }
    
    private void ajouterScore(Score score) {
        _modele.ajouterScore(score);
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int ligne, int colonne) {
        Component c = super.prepareRenderer(renderer, ligne, colonne);
        
        if(getSelectedRow() == ligne) {
            c.setBackground(PanelPerso.COULEUR_BACKGROUND_TITRE);
            c.setForeground(PanelPerso.COULEUR_ECRITURE);
        } else {
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
