package metier;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import modele.Forme;
import modele.Navire;
import modele.enumeration.TypeShape;
import modele.outils.PointPathFinding;
import presentation.PanelMap;

public class DeplacementBateaux{
    public static double PASVOISIN = 0.0005;
    public static ArrayList<PointPathFinding> deplacer(Navire bateau, Point2D destination, ArrayList<Forme> formes, double coefX, double coefY, PanelMap map) {
        double x = bateau.getPosition().getX();
        double y = bateau.getPosition().getY();
        System.out.println("x " + x);
        System.out.println("y " + y);
        System.out.println("x " + destination.getX());
        System.out.println("y " + destination.getY());
        //PointPathFinding pointEnCours = new PointPathFinding(bateau.getPosition(), 0);
        PointPathFinding depart = new PointPathFinding(new Point2D.Double(x, y), 0);
        PointPathFinding pointEnCours = depart;
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
            
            TreeSet<PointPathFinding> voisinsPriorises = trierVoisins(pointEnCours, destination, formes, pointsVisites, bateau);
            while(!voisinsPriorises.isEmpty()) {
                PointPathFinding voisin = voisinsPriorises.pollFirst();
                voisin.ajoutVoisin(pointEnCours);
                pointEnCours.ajoutVoisin(voisin);
                pile.add(0, voisin);
            }
            
            
            
            pile.remove(pointEnCours);
        }
        System.out.println("ok");
        if(pointEnCours.egal(destination)) {
            //on a trouvé
            ArrayList<PointPathFinding> chemin = new ArrayList<>();
            chemin.add(pointEnCours);
            
            while(!pointEnCours.egal(bateau.getPosition())) {
                pointEnCours = trierVoisins(pointEnCours, destination);
                chemin.add(pointEnCours);
            }
            System.out.println("tailles: " + chemin.size() + "  " + pointsVisites.size());
            //return chemin;
            for(int i=chemin.size()-1;i>=0;i--) {
                PointPathFinding p = chemin.get(i);
                bateau.setPosition(p.getPoint());
                bateau.constructionNavire();
                map.refresh();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DeplacementBateaux.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            for(PointPathFinding p:chemin) {
//                bateau.setPosition(p.getPoint());
//                bateau.constructionNavire();
//                map.refresh();
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(DeplacementBateaux.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                
//            }
        }
        return pointsVisites;
    }
    
    public static PointPathFinding trierVoisins(PointPathFinding pointEnCours, Point2D destination) {
        for(PointPathFinding p:pointEnCours.getVoisins()) {
//                System.out.println(p.getCout());
//                System.out.println(pointEnCours.getCout());
                if(p.getCout()+1==pointEnCours.getCout()) {
                    return p;
                }
        }
//        int cpt = 0;
//        for(PointPathFinding voisin:pointEnCours.getVoisins()) {
//            if(voisin.getCout()+1==pointEnCours.getCout()) {
////                double distanceEnCours = destination.distance(pointEnCours.getPoint());
////                double distanceVoisin = destination.distance(voisin.getPoint());
////                System.out.println(distanceEnCours  + " " + distanceVoisin);
//                System.out.println(pointEnCours.getDistance()  + " " + voisin.getDistance());
//                if(pointEnCours.getDistance() < voisin.getDistance()) {
//                    System.out.println(cpt);
//                    return voisin; 
//                }
//            }
//            cpt++;
//        }
        return null;
    }
    
    public static TreeSet<PointPathFinding> trierVoisins(PointPathFinding pointEnCours, Point2D destination, ArrayList<Forme> formes, ArrayList<PointPathFinding> visites, Navire bateau) {
        TreeSet<PointPathFinding> voisinsPriorises = new TreeSet<>();
        double x = pointEnCours.getPoint().getX();
        double y = pointEnCours.getPoint().getY();
        int cout = pointEnCours.getCout();
        
        PointPathFinding gauche, droit, bas, haut;
        gauche = new PointPathFinding(new Point2D.Double(x-PASVOISIN, y), cout+1);
        droit = new PointPathFinding(new Point2D.Double(x+PASVOISIN, y), cout+1);
        bas = new PointPathFinding(new Point2D.Double(x, y-PASVOISIN), cout+1);
        haut = new PointPathFinding(new Point2D.Double(x, y+PASVOISIN), cout+1);

        if(deplacementPossible(bateau.boundingsPosition(gauche.getPoint()), formes, bateau)) {
            if(gauche.getPoint().getX()>=0.09 && gauche.getPoint().getX()<=0.1900875 &&
                gauche.getPoint().getY()>=49.448 && gauche.getPoint().getY()<=49.488) {
                gauche.setDistance(gauche.getPoint().distance(destination));
                if(!arrayContient(visites, gauche)) {
                    voisinsPriorises.add(gauche);
                }
            }
        }
        if(deplacementPossible(bateau.boundingsPosition(droit.getPoint()), formes, bateau)) {
            if(droit.getPoint().getX()>=0.09 && droit.getPoint().getX()<=0.1900875 &&
                droit.getPoint().getY()>=49.448 && droit.getPoint().getY()<=49.488) {
                droit.setDistance(droit.getPoint().distance(destination));
                if(!arrayContient(visites, droit)) {
                    voisinsPriorises.add(droit);
                }
            }
        }
        if(deplacementPossible(bateau.boundingsPosition(bas.getPoint()), formes, bateau)) {
            if(bas.getPoint().getX()>=0.09 && bas.getPoint().getX()<=0.1900875 &&
                bas.getPoint().getY()>=49.448 && bas.getPoint().getY()<=49.488) {
                bas.setDistance(bas.getPoint().distance(destination));
                if(!arrayContient(visites, bas)) {
                    voisinsPriorises.add(bas);
                }
            }
        }
        
        if(deplacementPossible(bateau.boundingsPosition(haut.getPoint()), formes, bateau)) {
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
    
    public static boolean deplacementPossible(Path2D p, ArrayList<Forme> formes, Navire bateau) {
        for(Forme forme:formes) {
            if(forme!=bateau && forme.getPathOriginal().intersects(p.getBounds2D()) && forme.getTypeForme()!=TypeShape.NATURAL && forme.getTypeForme()!=TypeShape.HIGHWAY) {
//                System.out.println(forme.getNom());
//                System.out.println("FALSE");
                return false;
            }
        }
        return true;
    }
}
