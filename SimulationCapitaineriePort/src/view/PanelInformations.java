/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jeremy
 */
public class PanelInformations extends JPanel {
    
    private JLabel              _jlNomPanel;
    private ArrayList<String>   _informations;
    private GridLayout          _layout;

    public PanelInformations(String nomPanel, ArrayList<String> informations) {
        this._jlNomPanel    = new JLabel(nomPanel);
        this._informations  = informations;
        this._layout        = new GridLayout(informations.size()+1, 1, 5, 5);
        
        this.setLayout(_layout);
        this._jlNomPanel.setBorder(BorderFactory.createLineBorder(Color.red, 10));
    }
    
    public void majInformations() {
        add(_jlNomPanel);
        for(String info : _informations)
            add(new JLabel(info));
    }
    
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        ArrayList<String> informations = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) {
            informations.add("Test : "+i);
        }
        
        PanelInformations pi = new PanelInformations("TEST", informations);
        pi.majInformations();
        
        jf.add(pi);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
}
