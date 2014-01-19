/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jeremy
 */
public class Panel extends JPanel {
    
    protected JLabel _jlNomPanel ;

    public Panel(String nomPanel) {
        setPreferredSize(new Dimension(200, 500));
        setLayout(new BorderLayout());
        setBackground(new Color(40, 100, 250));
        
        initialisationLabelNomPanel(nomPanel);
    }
    
    private void initialisationLabelNomPanel(String nomPanel) {
        _jlNomPanel = new JLabel(nomPanel, JLabel.CENTER);
        _jlNomPanel.setOpaque(true);
        _jlNomPanel.setBackground(Color.black);
        _jlNomPanel.setForeground(Color.white);
        _jlNomPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        _jlNomPanel.setFont(new Font(this.getFont().getName(), Font.BOLD, 18));
        
        add(_jlNomPanel, BorderLayout.NORTH);
    }
}
