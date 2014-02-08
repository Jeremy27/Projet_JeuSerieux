package presentation;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import modele.enumeration.TypeMessage;

/**
 *
 * @author Jérémy 
 */
public class PanelInfoJeu extends PanelPerso {

    private JLabel _jlInformations;
    private String _log;
    
    public PanelInfoJeu() {
        super("Informations");
        _log = "<p class=\"gris\">En attente ...</p>";
        initialisationLabelLog();
    }
    
    private void initialisationLabelLog() {
        _jlInformations = new JLabel(_log);
        JScrollPane jsp = new JScrollPane(_jlInformations);
        _jlInformations.setBackground(COULEUR_PANEL1);
        _jlInformations.setOpaque(true);
        
        add(jsp, BorderLayout.CENTER);
    }
    
    public void ajoutMessage(String message, TypeMessage typeMessage) {
        _log = "<p class=\"" + getCouleur(typeMessage) + "\">" + message + "</p>" + _log;
        _jlInformations.setText(getDebutHTML() + _log + getFinHTML());
    }
    
    public void remiseAZero() {
        _log =  "<p class=\"gris\">En attente ...</p>";
        initialisationLabelLog();
    }

    private String getCouleur(TypeMessage typeMessage) {
        String classeCSS = null;
        
        if(typeMessage == TypeMessage.ERREUR)
            classeCSS = "rouge";
        else if(typeMessage == TypeMessage.NORMAL)
            classeCSS = "gris";
        else if(typeMessage == TypeMessage.RETARD)
            classeCSS = "orange";
        else if(typeMessage == TypeMessage.AVERTISSEMENT)
            classeCSS = "jaune";
        else if(typeMessage == TypeMessage.IMPORTANT)
            classeCSS = "important";
        
        return classeCSS;
    }
    
    private String getDebutHTML() {
        String debutHTML  = "";
        debutHTML +="<html><head><style type='text/css'>";
        debutHTML += ".rouge   {color: #8B0000;}";
        debutHTML += ".blanche {color: white;}";
        debutHTML += ".gris    {color: #D3D3D3;}";
        debutHTML += ".orange  {color: orange;}";
        debutHTML += ".jaune   {color: yellow;}";
        debutHTML += ".important   {color: white;}";
        debutHTML += "</style></head><body>";
        return debutHTML;
    }
    
    private String getFinHTML() {
        return "</body></html>";
    }
}
