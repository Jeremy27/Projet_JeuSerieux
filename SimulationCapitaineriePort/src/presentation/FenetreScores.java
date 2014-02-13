/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author jeremy
 */
public class FenetreScores extends JDialog {
    
    private JTabbedPane _jtpOnglets;
    private JTable      _jtTableau1;
    private JTable      _jtTableau2;

    public FenetreScores() {
        setTitle("Affichage des scores");
        setSize(200, 600);
        
        initialiserComposants();
        
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void initialiserComposants() {
        _jtpOnglets = new JTabbedPane();
        _jtTableau1 = new JTable();
        _jtTableau2 = new JTable();
        
        _jtpOnglets.addTab("Facile", _jtTableau1);
        _jtpOnglets.addTab("Normal", _jtTableau2);
        
        add(_jtpOnglets);
    }
}
