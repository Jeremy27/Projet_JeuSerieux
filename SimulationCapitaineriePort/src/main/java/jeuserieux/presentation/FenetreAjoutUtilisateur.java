/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeuserieux.presentation;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import jeuserieux.accesAuDonnees.ConnexionMySQL;

/**
 *
 * @author kadoc
 */
public class FenetreAjoutUtilisateur extends JDialog{
    public static int ID_UTIL=0;
    private JLabel _labMailUtil;
    private JLabel _labNomUtil;
    private JLabel _labMdpUtil;
    
    private JTextField _textMailUtil;
    private JTextField _textNomUtil;
    private JPasswordField _textMdpUtil;
    
    private JPanel _panelNomUtil;
    private JPanel _panelMailUtil;
    private JPanel _panelMdpUtil;
    
    private JButton _btnAjouterUtil;
    
    public FenetreAjoutUtilisateur() {
        setTitle("Documentation");
        setSize(300, 200);
        
        initialiserComposants();
        evenements();
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    private void initialiserComposants() {
        _btnAjouterUtil = new JButton("Inscrire un utilisateur");
        
        _labNomUtil = new JLabel("Nom: ");
        _labMailUtil = new JLabel("Mail: ");
        _labMdpUtil = new JLabel("Mot de passe: ");
        
        _textNomUtil = new JTextField(15);
        _textMailUtil = new JTextField(15);
        _textMdpUtil = new JPasswordField(15);
        
        _panelNomUtil = new JPanel(new FlowLayout());
        _panelMailUtil = new JPanel(new FlowLayout());
        _panelMdpUtil = new JPanel(new FlowLayout());
        
        _panelNomUtil.add(_labNomUtil);
        _panelNomUtil.add(_textNomUtil);
        
        _panelMailUtil.add(_labMailUtil);
        _panelMailUtil.add(_textMailUtil);
        
        _panelMdpUtil.add(_labMdpUtil);
        _panelMdpUtil.add(_textMdpUtil);
        
        this.add(_panelNomUtil);
        this.add(_panelMailUtil);
        this.add(_panelMdpUtil);
        this.add(_btnAjouterUtil);
    }
    
    public void evenements() {
        _btnAjouterUtil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String mdp = new String(_textMdpUtil.getPassword());
                if(!_textMailUtil.getText().equals("") && !_textNomUtil.getText().equals("") && !mdp.equals("")) {
                    System.out.println("ok");
                    ConnexionMySQL con = new ConnexionMySQL("partie", "root", "");
                    try {
                        con.connect("partie");
                        con.insert_Utilisateurs("partie", "utilisateurs", _textMailUtil.getText(), ID_UTIL, new String(_textMdpUtil.getPassword()), _textNomUtil.getText());
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    
}
