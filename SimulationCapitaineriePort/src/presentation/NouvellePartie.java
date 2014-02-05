/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package presentation;

import javax.swing.JDialog;

/**
 *
 * @author jeremy
 */
public class NouvellePartie extends JDialog {

    public NouvellePartie() {
        setTitle("Nouvelle partie ...");
        
        setModal(true);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
}
