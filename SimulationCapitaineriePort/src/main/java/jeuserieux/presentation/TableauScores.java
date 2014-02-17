/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.presentation;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import jeuserieux.modele.ModeleTabScores;
import jeuserieux.modele.Score;

/**
 *
 * @author jeremy
 */
public class TableauScores extends JTable {
    
    private ModeleTabScores _modele;
    private Score           _scoreImportant;

    public TableauScores() {
        initialiserModele();
    }
    
    public TableauScores(ArrayList<String> titres) {
        initialiserModele(titres);
    }
    
    public TableauScores(Score scoreImportant) {
        initialiserModele();
        _scoreImportant = scoreImportant;
    }
    
    public TableauScores(Score scoreImportant, ArrayList<String> titres) {
        initialiserModele(titres);
        _scoreImportant = scoreImportant;
    }
    
    private void initialiserModele() {
        _modele = new ModeleTabScores();
        setModel(_modele);
    }
    
    private void initialiserModele(ArrayList<String> titres) {
        _modele = new ModeleTabScores(titres);
        setModel(_modele);
    }
    
    public void ajouterScore(Score score) {
        _modele.ajouterScore(score);
    }
    
    public void trierScores() {
        _modele.trierScores();
    }
    
    public void setScoreImportant(Score score) {
        _scoreImportant = score;
    }
    
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int ligne, int colonne) {
        Component c = super.prepareRenderer(renderer, ligne, colonne);
        
        if(getSelectedRow() == ligne) {
            c.setBackground(PanelPerso.COULEUR_BACKGROUND_TITRE);
            c.setForeground(PanelPerso.COULEUR_ECRITURE);
        } else {
            if(_modele.getScore(ligne).equals(_scoreImportant))
                c.setBackground(PanelPerso.COULEUR_RETARD);
            else if(ligne%2 == 0)
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
