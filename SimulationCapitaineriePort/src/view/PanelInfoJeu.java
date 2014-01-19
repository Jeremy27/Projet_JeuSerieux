package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modele.enumeration.TypeMessage;

/**
 *
 * @author Jérémy 
 */
public class PanelInfoJeu extends JPanel {

    private JLabel _jlNomPanel;
    private JLabel _jlInformations;
    private String _log;
    
    public PanelInfoJeu() {
        setPreferredSize(new Dimension(250, 500));
        setLayout(new BorderLayout());
        
        setBackground(new Color(40, 100, 250));
        _log = "<p class=\"blanche\">En attente ...</p>";
        initialisationLabelNomPanel();
        initialisationLabelLog();
    }
    
    private void initialisationLabelNomPanel() {
        _jlNomPanel = new JLabel("Informations", JLabel.CENTER);
        _jlNomPanel.setOpaque(true);
        _jlNomPanel.setBackground(Color.black);
        _jlNomPanel.setForeground(Color.white);
        _jlNomPanel.setPreferredSize(new Dimension(this.getWidth(), 50));
        _jlNomPanel.setAlignmentX((float) 10.0);
        _jlNomPanel.setFont(new Font(this.getFont().getName(), Font.BOLD, 18));
        
        add(_jlNomPanel, BorderLayout.NORTH);
    }
    
    private void initialisationLabelLog() {
        _jlInformations = new JLabel(_log);
        JScrollPane jsp = new JScrollPane(_jlInformations);
        _jlInformations.setBackground(new Color(40, 100, 250));
        _jlInformations.setOpaque(true);
        
        
        add(jsp, BorderLayout.CENTER);
    }
    
    public void ajoutMessage(String message, TypeMessage typeMessage) {
        _log = "<p class=\"" + getCouleur(typeMessage) + "\">" + message + "</p>" + _log;
        _jlInformations.setText(getDebutHTML() + _log + getFinHTML());
    }

    private String getCouleur(TypeMessage typeMessage) {
        String classeCSS = null;
        
        if(typeMessage == TypeMessage.ERREUR)
            classeCSS = "rouge";
        else if(typeMessage == TypeMessage.NORMAL)
            classeCSS = "blanche";
        else if(typeMessage == TypeMessage.RETARD)
            classeCSS = "orange";
        else if(typeMessage == TypeMessage.AVERTISSEMENT)
            classeCSS = "jaune";
        
        return classeCSS;
    }
    
    private String getDebutHTML() {
        String debutHTML  = "";
        debutHTML +="<html><head><style type='text/css'>";
        debutHTML += ".rouge   {color: red;}";
        debutHTML += ".blanche {color: white;}";
        debutHTML += ".orange  {color: orange;}";
        debutHTML += ".jaune   {color: yellow;}";
        debutHTML += "</style></head><body>";
        return debutHTML;
    }
    
    private String getFinHTML() {
        return "</body></html>";
    }
}
