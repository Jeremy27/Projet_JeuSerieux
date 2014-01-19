package view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import modele.enumeration.TypeMessage;

/**
 *
 * @author Jérémy 
 */
public class PanelInfoJeu extends Panel {

    private JLabel _jlInformations;
    private String _log;
    
    public PanelInfoJeu() {
        super("Informations");
        _log = "<p class=\"blanche\">En attente ...</p>";
        initialisationLabelLog();
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
