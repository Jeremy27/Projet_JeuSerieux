package metier;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.TreeSet;
import modele.Forme;
import modele.Navire;
import modele.enumeration.TypeShape;
import modele.outils.PointPathFinding;

public class DeplacementBateaux {
    public static ArrayList<PointPathFinding> deplacer(Navire bateau, Point2D destination, ArrayList<Forme> formes, double coefX, double coefY) {
        double x = bateau.getPosition().getX();
        double y = bateau.getPosition().getY();
        System.out.println("x " + x);
        System.out.println("x " + y);
        System.out.println("x " + destination.getX());
        System.out.println("x " + destination.getY());
        //PointPathFinding pointEnCours = new PointPathFinding(bateau.getPosition(), 0);
        PointPathFinding pointEnCours = new PointPathFinding(new Point2D.Double(x, y) {}, 0);
        ArrayList<PointPathFinding> pile = new ArrayList<>();
        ArrayList<PointPathFinding> pointsVisites = new ArrayList<>();
        pile.add(pointEnCours);
        System.out.println("on commence");
        while(!pile.isEmpty()) {
            //System.out.println(pile.size());
            pointEnCours = pile.get(0);
            pointsVisites.add(pointEnCours);
            if(pointEnCours.egal(destination)) { //si on a trouvé
                pile.clear();
                break;
            }
            
            TreeSet<PointPathFinding> voisinsPriorises = trierVoisins(pointEnCours, destination, formes, pointsVisites);
            while(!voisinsPriorises.isEmpty()) {
                pile.add(0, voisinsPriorises.pollFirst());
            }
            
            pile.remove(pointEnCours);
        }
        System.out.println("ok");
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
        return pointsVisites;
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
        gauche = new PointPathFinding(new Point2D.Double(x-0.001, y), cout+1);
        droit = new PointPathFinding(new Point2D.Double(x+0.001, y), cout+1);
        bas = new PointPathFinding(new Point2D.Double(x, y-0.001), cout+1);
        haut = new PointPathFinding(new Point2D.Double(x, y+0.001), cout+1);

        if(deplacementPossible(gauche.getPoint(), formes)) {
            if(gauche.getPoint().getX()>=0.09 && gauche.getPoint().getX()<=0.1900875 &&
                gauche.getPoint().getY()>=49.448 && gauche.getPoint().getY()<=49.488) {
                gauche.setDistance(gauche.getPoint().distance(destination));
                if(!arrayContient(visites, gauche)) {
                    voisinsPriorises.add(gauche);
                }
            }
        }
        if(deplacementPossible(droit.getPoint(), formes)) {
            if(droit.getPoint().getX()>=0.09 && droit.getPoint().getX()<=0.1900875 &&
                droit.getPoint().getY()>=49.448 && droit.getPoint().getY()<=49.488) {
                droit.setDistance(droit.getPoint().distance(destination));
                if(!arrayContient(visites, droit)) {
                    voisinsPriorises.add(droit);
                }
            }
        }
        if(deplacementPossible(bas.getPoint(), formes)) {
            if(bas.getPoint().getX()>=0.09 && bas.getPoint().getX()<=0.1900875 &&
                bas.getPoint().getY()>=49.448 && bas.getPoint().getY()<=49.488) {
                bas.setDistance(bas.getPoint().distance(destination));
                if(!arrayContient(visites, bas)) {
                    voisinsPriorises.add(bas);
                }
            }
        }
        if(deplacementPossible(haut.getPoint(), formes)) {
            if(haut.getPoint().getX()>=0.09 && haut.getPoint().getX()<=0.1900875 &&
                haut.getPoint().getY()>=49.448 && haut.getPoint().getY()<=49.488) {
                haut.setDistance(haut.getPoint().distance(destination));
                if(!arrayContient(visites, haut)) {
                    voisinsPriorises.add(haut);
                }
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
