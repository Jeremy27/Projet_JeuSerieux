package metier;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.TreeSet;
import modele.Forme;
import modele.Navire;
import modele.enumeration.TypeShape;
import modele.outils.PointPathFinding;

public class DeplacementBateaux {
    public static ArrayList<PointPathFinding> deplacer(Navire bateau, Point2D destination, ArrayList<Forme> formes) {
        PointPathFinding pointEnCours = new PointPathFinding(bateau.getPosition(), 0);
        ArrayList<PointPathFinding> pile = new ArrayList<>();
        ArrayList<PointPathFinding> pointsVisites = new ArrayList<>();
        pile.add(pointEnCours);
        
        while(!pile.isEmpty()) {
            pointEnCours = pile.get(0);
            pointsVisites.add(pointEnCours);
            if(pointEnCours.getPoint().equals(destination)) { //si on a trouvé
                pile.clear();
                break;
            }
            
            TreeSet<PointPathFinding> voisinsPriorises = trierVoisins(pointEnCours, destination, formes, pointsVisites);
            while(!voisinsPriorises.isEmpty()) {
                pile.add(0, voisinsPriorises.pollFirst());
            }
            
            pile.remove(pointEnCours);
        }
        
        if(pointEnCours.getPoint().equals(destination)) {
            //on a trouvé
            ArrayList<PointPathFinding> chemin = new ArrayList<>();
            chemin.add(pointEnCours);
            while(!pointEnCours.getPoint().equals(bateau.getPosition())) {
                pointEnCours = trierVoisins(pointEnCours, pointsVisites);
                chemin.add(pointEnCours);
            }
            return chemin;
        }
        return null;
    }
    
    public static PointPathFinding trierVoisins(PointPathFinding pointEnCours, ArrayList<PointPathFinding> pointsVisites) {
        for(PointPathFinding p:pointsVisites) {
            if(p.estVoisin(pointEnCours)) {
                if(p.getCout()+1==pointEnCours.getCout()) {
                    return p;
                }
            }
        }
        return null;
    }
    
    public static TreeSet<PointPathFinding> trierVoisins(PointPathFinding pointEnCours, Point2D destination, ArrayList<Forme> formes, ArrayList<PointPathFinding> visites) {
        TreeSet<PointPathFinding> voisinsPriorises = new TreeSet<>();
        double x = pointEnCours.getPoint().getX();
        double y = pointEnCours.getPoint().getY();
        int cout = pointEnCours.getCout();

        PointPathFinding gauche, droit, bas, haut;
        gauche = new PointPathFinding(new Point2D.Double(x-1, y), cout+1);
        droit = new PointPathFinding(new Point2D.Double(x+1, y), cout+1);
        bas = new PointPathFinding(new Point2D.Double(x, y-1), cout+1);
        haut = new PointPathFinding(new Point2D.Double(x, y+1), cout+1);

        if(deplacementPossible(gauche.getPoint(), formes)) {
            gauche.setDistance(gauche.getPoint().distance(destination));
            if(!arrayContient(visites, gauche)) {
                voisinsPriorises.add(gauche);
            }
        }
        if(deplacementPossible(droit.getPoint(), formes)) {
            droit.setDistance(droit.getPoint().distance(destination));
            if(!arrayContient(visites, droit)) {
                voisinsPriorises.add(droit);
            }
        }
        if(deplacementPossible(bas.getPoint(), formes)) {
            bas.setDistance(bas.getPoint().distance(destination));
            if(!arrayContient(visites, bas)) {
                voisinsPriorises.add(bas);
            }
        }
        if(deplacementPossible(haut.getPoint(), formes)) {
            haut.setDistance(haut.getPoint().distance(destination));
            if(!arrayContient(visites, haut)) {
                voisinsPriorises.add(haut);
            }
        }
        return voisinsPriorises;
    }
    
    public static boolean arrayContient(ArrayList<PointPathFinding> pointsVisites, PointPathFinding pointEnCours) {
        for(PointPathFinding p:pointsVisites) {
            if(p.getPoint().equals(pointEnCours.getPoint())) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean deplacementPossible(Point2D p, ArrayList<Forme> formes) {
        for(Forme forme:formes) {
            if(forme.getPath().contains(p) && forme.getTypeForme()!=TypeShape.NATURAL) {
                return false;
            }
        }
        return true;
    }
}
