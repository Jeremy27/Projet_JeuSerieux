package presentation;

import accesAuDonnees.FichierJson;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.swing.JPanel;
import modele.Forme;
import modele.Quai;
import modele.Terminal;
import modele.enumeration.TypeShape;

public class AfficherMapBis extends JPanel{
    private static final double ZOOMMIN = 1.0;
    
    //couleurs des différentes formes
    private static final Color NATURALCOLOR = new Color(181, 208, 208);
    private static final Color TERMINALCOLOR = new Color(222, 208, 213);
    private static final Color QUAICOLOR = new Color(52, 203, 153);
    private static final Color ROUTECOLOR = new Color(182, 181, 146);
    private static final Color BATIMENTCOLOR = Color.GRAY;
    private static final Color AUTRECOLOR = Color.GRAY;
    
    //fichier source json
    private final String separateur = java.nio.file.FileSystems.getDefault().getSeparator();
    private final String _nomFichier = "src" + separateur + "data" + separateur + "map_filtree.json";
    
    /**************** MAP *******************/
    private Point2D _curseur;
    //dezoom max
    private final double MAX_DEPART_X=0.0;
    private final double MAX_DEPART_Y=0.0;
    private final double MIN_DEPART_X=0.0;
    private final double MIN_DEPART_Y=0.0;
    
    //zoom
    private double _zoomEtat = ZOOMMIN;
    private double _zoomMax = 10.0;
    
    //limites affichage map
    private double _mapHaut;
    private double _mapBas;
    private double _mapGauche;
    private double _mapDroite;
    
    private final PanelInfoForme _panelInfoForme;
    private final ArrayList<Forme> _coordonneesDessin;
    
    public AfficherMapBis(PanelInfoForme panelInfo) {
        _panelInfoForme = panelInfo;
        _coordonneesDessin = new ArrayList<>();
    }
    
    //mettre dans accès aux données
    public void makePath2D(JsonArray ja) {
        for(Object obj:ja.toArray()) {
            //variables indispensables
            boolean fill = false;
            Color c;
            JsonObject jo = (JsonObject) obj;
            long id = Long.parseLong(jo.get("_id").toString());
            String type = jo.get("_type").toString();
            String nom = jo.get("_nom").toString();
            Forme forme;

            //construction de l'objet
            if(type.equals(TypeShape.NATURAL.toString())) {
                c = NATURALCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id);
            } else if(type.equals(TypeShape.QUAI.toString())) {
                c = QUAICOLOR;
                fill = true;
                forme = new Quai(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.equals(TypeShape.TERMINAL.toString())) {
                c = TERMINALCOLOR;
                fill = true;
                forme = new Terminal(nom, fill, c, id, new Random(System.currentTimeMillis()).nextInt(100));
            } else if(type.equals(TypeShape.HIGHWAY.toString())) {
                c = ROUTECOLOR;
                forme = new Forme(nom, fill, c, id);
            } else if(type.equals(TypeShape.BUILDING.toString())) {
                c = BATIMENTCOLOR;
                fill = true;
                forme = new Forme(nom, fill, c, id);
            } else {
                c = AUTRECOLOR;
                forme = new Forme(nom, fill, c, id);
            }
            
            //traitement des coordonnées de la forme
            JsonArray nodes = (JsonArray)jo.get("_nodes");
            for(Object coordonnees:nodes.toArray()) {
                JsonObject node = (JsonObject) coordonnees;
                double x = Double.parseDouble(node.get("x").toString());
                double y = Double.parseDouble(node.get("y").toString());
                forme.ajoutCoordonnee(new Point2D.Double(x, y));
            }
            _coordonneesDessin.add(forme);
        }
        
    }
    
    public void refresh() {
        repaint();
        revalidate();
    }
    
    public JsonArray getContenu() {
        FichierJson fj = new FichierJson(_nomFichier);
        return fj.getJson();
    }
    
    @Override
    public void paint(Graphics g) {
        _mapBas = MIN_DEPART_Y;
        _mapHaut = MAX_DEPART_Y;
        _mapDroite = MAX_DEPART_X;
        _mapGauche = MIN_DEPART_X;
        
        Graphics2D g2 = (Graphics2D)g;
        
        //récupère la taille réelle de la fenêtre
        Insets insets = getInsets();
        int w = getWidth() - insets.left - insets.right;
        int h = getHeight() - insets.top - insets.bottom;
        
        
        
        //détermine les coordonnées à gérer
        double diffY = MAX_DEPART_Y - MIN_DEPART_Y;
        double diffX = MAX_DEPART_X - MIN_DEPART_X;
        
        double hauteur = diffX / _zoomEtat;
        double largeur = diffY / _zoomEtat;
        
        double ajoutDroite, ajoutBas;
        ajoutDroite = ajoutBas = 0.0;
        double calculGauche = _curseur.getX()-largeur/2.0;
        if(calculGauche>=MIN_DEPART_X) {
            _mapGauche = calculGauche;
        } else {
            ajoutDroite = MIN_DEPART_X - calculGauche;
            _mapGauche = MIN_DEPART_X;
        }
        
        double calculDroite = _curseur.getX()+largeur/2.0;
        if(calculDroite<=MAX_DEPART_X) {
            _mapDroite = calculDroite;
            _mapDroite += ajoutDroite;
        } else {
            double diff = calculDroite-MAX_DEPART_X; //négatif
            _mapDroite = MAX_DEPART_X;
            _mapGauche -= diff;
        }
        
        double calculHaut = _curseur.getY()-hauteur/2.0;
        if(calculHaut>=MIN_DEPART_Y) {
            _mapHaut = calculHaut;
        } else {
            ajoutBas = calculHaut - MIN_DEPART_Y;
            _mapHaut = MIN_DEPART_Y;
        }
        
        double calculBas = _curseur.getY()+hauteur/2.0;
        if(calculBas<=MAX_DEPART_Y) {
            _mapBas = calculBas;
            _mapBas += ajoutBas;
        } else {
            double diff = MAX_DEPART_Y-calculBas; //négatif
            _mapBas = MAX_DEPART_Y;
            _mapHaut += diff;
        }
        
        
        //trie les formes puis les paint
        for(Forme forme:_coordonneesDessin) {
            Color couleur = forme.getCouleur();
            boolean fill = forme.isFill();
            Path2D path = null;
            g2.setColor(couleur);
            if(fill) {
                g2.fill(path);
            } else {
                g2.draw(path);
            }
        }
        g2.setFont(new Font("SansSerif", Font.BOLD, 20));
        g2.drawString("", 50, 50);
    }
}
