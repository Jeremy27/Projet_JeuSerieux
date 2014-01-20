/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTable;

/**
 *
 * @author jeremy
 */
public class PanelNaviresArrives extends Panel {

    private JTable _tableau;
    private ModeleJTable modele;
    
    
    public PanelNaviresArrives() {
        super("Navires arrivés");
       
        _tableau = new JTable(new ModeleJTable(new ArrayList<>(Arrays.asList("Nom", "Temps d'arrivée", "baz"))));
        add(_tableau, BorderLayout.CENTER);
    }
    
    
}
