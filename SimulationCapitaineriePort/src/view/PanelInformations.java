/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jérémy
 */
public class PanelInformations extends JPanel {
    
    private JLabel              _jlNomPanel;
    private ArrayList<String>   _informations;

    /**
     * 
     * @param nomPanel
     * @param informations format de l'ArrayList : {"nom : valeur", "nom : valeur", ...}
     */
    public PanelInformations(String nomPanel, ArrayList<String> informations) {
        _jlNomPanel    = new JLabel(nomPanel, JLabel.CENTER);
        _informations  = informations;
        
        setLayout(new GridLayout(informations.size()+1, 1));
        _jlNomPanel.setOpaque(true);
        _jlNomPanel.setBackground(Color.black);
        _jlNomPanel.setForeground(Color.white);
        _jlNomPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        _jlNomPanel.setAlignmentX((float) 10.0);
        _jlNomPanel.setFont(new Font(this.getFont().getName(), Font.BOLD, 18));
    }
    
    public void majInformations() {
        JLabel label;
        boolean pair = true;
        add(_jlNomPanel);
        for (String info : _informations) {
            label = new JLabel(info);
            label.setOpaque(true);
            if(pair = !pair)
                label.setBackground(Color.lightGray);
            else
                label.setBackground(Color.gray);
            add(label);
        }
    }
    
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        ArrayList<String> informations = new ArrayList<>();
        
        for (int i = 0; i < 10; i++)
            informations.add("  Test : " + i + "  ");
        
        PanelInformations pi = new PanelInformations("TEST", informations);
        pi.majInformations();
        
        jf.add(pi);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
