/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import metier.GestionScores;
import modele.Score;
import modele.enumeration.TypeDifficulte;

/**
 *
 * @author jeremy
 */
public class FenetreScores extends JDialog {
    
    private JTabbedPane     _jtpOnglets;
    private TableauScores   _jtTabFacile;
    private TableauScores   _jtTabNormal;
    private TableauScores   _jtTabDifficile;
    private TableauScores   _jtTabTousLesScores;

    public FenetreScores(Score score) {
        setTitle("Affichage des scores");
        setSize(400, 400);
        
        initialiserComposants(score);
        
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void initialiserComposants(Score score) {
        _jtpOnglets         = new JTabbedPane();
        _jtTabFacile        = new TableauScores();
        _jtTabNormal        = new TableauScores();
        _jtTabDifficile     = new TableauScores();
        _jtTabTousLesScores = new TableauScores();
        
        
        definirScoreImportant(score);
        remplirTableaux();
        trierTableaux();
        
        _jtpOnglets.addTab("Tous les scores", new JScrollPane(_jtTabTousLesScores));
        _jtpOnglets.addTab(TypeDifficulte.FACILE.name(), new JScrollPane(_jtTabFacile));
        _jtpOnglets.addTab(TypeDifficulte.NORMAL.name(), new JScrollPane(_jtTabNormal));
        _jtpOnglets.addTab(TypeDifficulte.DIFFICILE.name(), new JScrollPane(_jtTabDifficile));
        
        add(_jtpOnglets);
    }
    
    private void definirScoreImportant(Score score) {
        if(score != null) {
            if(score.getDifficulte().equals(TypeDifficulte.DIFFICILE)) {
                _jtTabDifficile = new TableauScores(score);
            } else if(score.getDifficulte().equals(TypeDifficulte.FACILE)) {
                _jtTabFacile = new TableauScores(score);
            } else if(score.getDifficulte().equals(TypeDifficulte.NORMAL)) {
                _jtTabNormal = new TableauScores(score);
            }
            _jtTabTousLesScores = new TableauScores(score);
        }
    }
    
    private void trierTableaux() {
        _jtTabTousLesScores.trierScores();
        _jtTabDifficile.trierScores();
        _jtTabFacile.trierScores();
        _jtTabNormal.trierScores();
    }
    
    private void remplirTableaux() {
        GestionScores gScores       = new GestionScores("scores");
        ArrayList<Score> tabScores  = gScores.getTableauScores();
        
        for(Score score : tabScores) {
            traitementScore(score);
        }
    }
    
    private void traitementScore(Score score) {
        
        if(score.getDifficulte().equals(TypeDifficulte.DIFFICILE)) {
            _jtTabDifficile.ajouterScore(score);
        } else if(score.getDifficulte().equals(TypeDifficulte.FACILE)){
            _jtTabFacile.ajouterScore(score);
        } else if(score.getDifficulte().equals(TypeDifficulte.NORMAL)) {
            _jtTabNormal.ajouterScore(score);
        }
        _jtTabTousLesScores.ajouterScore(score);
    }
    
    public void actualiser() {
        revalidate();
        repaint();
    }
    
    public static void main(String [] args) {
        FenetreScores test = new FenetreScores(null);
        
        test.remplirTableaux();
    }
}
