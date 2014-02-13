/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
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

    public FenetreScores() {
        setTitle("Affichage des scores");
        setSize(400, 400);
        
        initialiserComposants();
        
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void initialiserComposants() {
        _jtpOnglets     = new JTabbedPane();
        _jtTabFacile    = new TableauScores();
        _jtTabNormal    = new TableauScores();
        _jtTabDifficile = new TableauScores();
        
        initialiserTableaux();
        trierTableaux();
        
        _jtpOnglets.addTab(TypeDifficulte.FACILE.name(), new JScrollPane(_jtTabFacile));
        _jtpOnglets.addTab(TypeDifficulte.NORMAL.name(), new JScrollPane(_jtTabNormal));
        _jtpOnglets.addTab(TypeDifficulte.DIFFICILE.name(), new JScrollPane(_jtTabDifficile));
        
        add(_jtpOnglets);
    }
    
    private void trierTableaux() {
        _jtTabDifficile.trierScores();
        _jtTabFacile.trierScores();
        _jtTabNormal.trierScores();
    }
    
    private void initialiserTableaux() {
        GestionScores gScores       = new GestionScores("scores");
        ArrayList<Score> tabScores  = gScores.getTableauScores();
        
        for(Score score : tabScores)
            traitementScore(score);
    }
    
    private void traitementScore(Score score) {
        
        if(score.getDifficulte().equals(TypeDifficulte.DIFFICILE)) {
            _jtTabDifficile.ajouterScore(score);
        }else if(score.getDifficulte().equals(TypeDifficulte.FACILE)){
            _jtTabFacile.ajouterScore(score);
        }else if(score.getDifficulte().equals(TypeDifficulte.NORMAL)) {
            _jtTabNormal.ajouterScore(score);
        }
    }
    
    public void actualiser() {
        revalidate();
        repaint();
    }
    
    public static void main(String [] args) {
        FenetreScores test = new FenetreScores();
        
        test.initialiserTableaux();
    }
}
