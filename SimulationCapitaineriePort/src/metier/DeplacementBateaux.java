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
    public static double PASVOISIN = 0.0008;
    public static ArrayList<PointPathFinding> deplacer(Navire bateau, Point2D destination, ArrayList<Forme> formes, double coefX, double coefY, PanelMap map) {
        double x = bateau.getPosition().getX();
        double y = bateau.getPosition().getY();
        
        //PointPathFinding pointEnCours = new PointPathFinding(bateau.getPosition(), 0);
        PointPathFinding depart = new PointPathFinding(new Point2D.Double(x, y), 0);
        PointPathFinding pointEnCours = depart;
        ArrayList<PointPathFinding> pile = new ArrayList<>();
        ArrayList<PointPathFinding> pointsVisites = new ArrayList<>();
        pile.add(pointEnCours);
        
        while(!pile.isEmpty()) {
            //
            pointEnCours = pile.get(0);
            
            pointsVisites.add(pointEnCours);
            //pointEnCours.majCouts();
            
            
            
            if(pointEnCours.egal(destination)) { //si on a trouvé
                pile.clear();
                break;
            }
            
            TreeSet<PointPathFinding> voisinsPriorises = trierVoisins(pointEnCours, destination, formes, bateau, pointsVisites);
            while(!voisinsPriorises.isEmpty()) {
                
                PointPathFinding voisin = voisinsPriorises.pollFirst();
                voisin.majCouts();
                if(!arrayContient(pointsVisites, voisin)) {
                    pile.add(0, voisin);
                }
            }
            pile.remove(pointEnCours);
        }
        
        for(PointPathFinding p:pointsVisites) {
            p.majCouts();
        }
        
        if(pointEnCours.egal(destination)) {
            //on a trouvé
            ArrayList<PointPathFinding> chemin = new ArrayList<>();
            chemin.add(pointEnCours);
            
            while(!pointEnCours.egal(bateau.getPosition())) {
                pointEnCours = trierVoisins(pointEnCours, destination);
                chemin.add(pointEnCours);
            }
            
            //return chemin;
            for(int i=chemin.size()-1;i>=0;i--) {
                PointPathFinding p = chemin.get(i);
                bateau.setPosition(p.getPoint());
                bateau.constructionNavire();
                map.refresh();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DeplacementBateaux.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return pointsVisites;
    }
    
    public static PointPathFinding trierVoisins(PointPathFinding pointEnCours, Point2D destination) {
        for(PointPathFinding p:pointEnCours.getVoisins()) {
//                
//                
                if(p.getCout()+1==pointEnCours.getCout()) {
                    return p;
                }
        }
//        int cpt = 0;
//        for(PointPathFinding voisin:pointEnCours.getVoisins()) {
//            if(voisin.getCout()+1==pointEnCours.getCout()) {
////                double distanceEnCours = destination.distance(pointEnCours.getPoint());
////                double distanceVoisin = destination.distance(voisin.getPoint());
////                
//                
//                if(pointEnCours.getDistance() < voisin.getDistance()) {
//                    
//                    return voisin; 
//                }
//            }
//            cpt++;
//        }
        return null;
    }
    
    public static TreeSet<PointPathFinding> trierVoisins(PointPathFinding pointEnCours, Point2D destination, ArrayList<Forme> formes, Navire bateau, ArrayList<PointPathFinding> visites) {
        TreeSet<PointPathFinding> voisinsPriorises = new TreeSet<>();
        double x = pointEnCours.getPoint().getX();
        double y = pointEnCours.getPoint().getY();
        int cout = pointEnCours.getCout();
        if(pointEnCours.getCout()==76 || pointEnCours.getCout()==51) {
            System.out.println("");  
        }
        
        if(pointEnCours.getVoisinGauche()==null) {
            PointPathFinding gauche = getPointExistant(x-PASVOISIN, y, visites);
            if(gauche==null) {
                gauche = new PointPathFinding(new Point2D.Double(x-PASVOISIN, y), cout+1);
                if(deplacementPossible(bateau, formes, gauche.getPoint())) {
                    if(gauche.getPoint().getX()>=0.09 && gauche.getPoint().getX()<=0.1900875 &&
                        gauche.getPoint().getY()>=49.448 && gauche.getPoint().getY()<=49.488) {
                            gauche.setDistance(gauche.getPoint().distance(destination));
                            pointEnCours.setVoisinGauche(gauche);
                            gauche.setVoisinDroit(pointEnCours);
                            gauche.setCout(pointEnCours.getCout()+1);

                            
                    }
                }
            }
            voisinsPriorises.add(gauche);  
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinGauche());
        }
            
        if(pointEnCours.getVoisinDroit()==null) {
            PointPathFinding droit = getPointExistant(x+PASVOISIN, y, visites);
            if(droit==null) {
                droit = new PointPathFinding(new Point2D.Double(x+PASVOISIN, y), cout+1);
                if(deplacementPossible(bateau, formes, droit.getPoint())) {
                    if(droit.getPoint().getX()>=0.09 && droit.getPoint().getX()<=0.1900875 &&
                        droit.getPoint().getY()>=49.448 && droit.getPoint().getY()<=49.488) {
                            droit.setDistance(droit.getPoint().distance(destination));
                            
                            droit.setVoisinGauche(pointEnCours);
                            droit.setCout(pointEnCours.getCout()+1);

                            voisinsPriorises.add(droit);
                    }
                }
            }
            pointEnCours.setVoisinDroit(droit); 
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinDroit());
        }
            
        
        if(pointEnCours.getVoisinBas()==null) {
            PointPathFinding bas = getPointExistant(x, y-PASVOISIN, visites);
            if(bas==null) {
                bas = new PointPathFinding(new Point2D.Double(x, y-PASVOISIN), cout+1);
                if(deplacementPossible(bateau, formes, bas.getPoint())) {
                    if(bas.getPoint().getX()>=0.09 && bas.getPoint().getX()<=0.1900875 &&
                        bas.getPoint().getY()>=49.448 && bas.getPoint().getY()<=49.488) {
                            bas.setDistance(bas.getPoint().distance(destination));
                            pointEnCours.setVoisinBas(bas);
                            bas.setVoisinHaut(pointEnCours);
                            bas.setCout(pointEnCours.getCout()+1);

                            
                    }
                }
            }
            voisinsPriorises.add(bas); 
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinBas());
        }
            
        
        if(pointEnCours.getVoisinHaut()==null) {
            PointPathFinding haut = getPointExistant(x, y+PASVOISIN, visites);
            if(haut==null) {
                haut = new PointPathFinding(new Point2D.Double(x, y+PASVOISIN), cout+1);
                if(deplacementPossible(bateau, formes, haut.getPoint())) {
                    if(haut.getPoint().getX()>=0.09 && haut.getPoint().getX()<=0.1900875 &&
                        haut.getPoint().getY()>=49.448 && haut.getPoint().getY()<=49.488) {
                            haut.setDistance(haut.getPoint().distance(destination));
                            pointEnCours.setVoisinHaut(haut);
                            haut.setVoisinBas(pointEnCours);
                            haut.setCout(pointEnCours.getCout()+1);
                    } 
                }
            }
            voisinsPriorises.add(haut);    
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinHaut());
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
    
    public static boolean deplacementPossible(Navire bateau, ArrayList<Forme> formes, Point2D point) {
        Path2D p = bateau.boundingsPosition(point);
        boolean dansEau = false;
        boolean dansShape = false;
        for(Forme forme:formes) {
            if(forme.getPathOriginal().intersects(p.getBounds2D())) {
                if (forme.getTypeForme()==TypeShape.NATURAL) {
                    dansEau = true;
                } else if(forme==bateau) {
                    return true;
                } else {
                    dansShape = true;
                }
            }
        }
        return dansEau && !dansShape;
    }
    
    private static PointPathFinding getPointExistant(double x, double y, ArrayList<PointPathFinding> arr) {
        for(PointPathFinding p:arr) {
            if(p.getPoint().getX()==x && p.getPoint().getY()==y) {
                return p;
            }
        }
        return null;
    }
    
//    public static boolean deplacementPossible(Point2D p, ArrayList<Forme> formes) {
//        //boolean boolPossible = true;
//        for(Forme forme:formes) {
//            if(forme.getPathOriginal().contains(p)) {
//                return false;
//            }
//        }
//        return true;
//    }
//    
//    private static ArrayList<Point2D> voisinsOk(Point2D p, ArrayList<Forme> formes) {
//        ArrayList<Point2D> _voisins = new ArrayList<>();
//        double x = p.getX();
//        double y = p.getY();
//        Point2D gauche = new Point2D.Double(x-0.001, y);
//        Point2D droit = new Point2D.Double(x+0.001, y);
//        Point2D bas = new Point2D.Double(x, y-0.001);
//        Point2D haut = new Point2D.Double(x, y+0.001);
//        
//        if(deplacementPossible(gauche, formes)) {
//            if(gauche.getX()>=0.09 && gauche.getX()<=0.1900875 &&
//                gauche.getY()>=49.448 && gauche.getY()<=49.488) {
//                _voisins.add(gauche);
//            }
//            
//        }
//        if(deplacementPossible(droit, formes)) {
//            if(droit.getX()>=0.09 && droit.getX()<=0.1900875 &&
//                droit.getY()>=49.448 && droit.getY()<=49.488) {
//                _voisins.add(droit);
//            }
//            
//        }
//        if(deplacementPossible(bas, formes)) {
//            if(bas.getX()>=0.09 && bas.getX()<=0.1900875 &&
//                bas.getY()>=49.448 && bas.getY()<=49.488) {
//                _voisins.add(bas);
//            }
//            
//        }
//        if(deplacementPossible(haut, formes)) {
//            if(haut.getX()>=0.09 && haut.getX()<=0.1900875 &&
//                haut.getY()>=49.448 && haut.getY()<=49.488) {
//                _voisins.add(haut);
//            }
//            
//        }
//        return _voisins;
//    }
//    
//    public static boolean arrayContient(ArrayList<Point2D> pointsVisites, Point2D pointEnCours) {
//        for(Point2D p:pointsVisites) {
//            if(p.getX()==pointEnCours.getX() && p.getY()==pointEnCours.getY()) {
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    public static ArrayList<Point2D> ocean(ArrayList<Forme> formes) {
//        Point2D depart = new Point2D.Double(0.10067553733031673, 49.481389134125635);
//        double pas = 0.0001;
//        ArrayList<Point2D> pointsOcean = new ArrayList<>();
//        pointsOcean.add(depart);
//        ArrayList<Point2D> pile = new ArrayList<>();
//        pile.add(depart);
//        while(!pile.isEmpty()) {
//            Point2D pointEnCours = pile.get(0);
//            for(Point2D voisin:voisinsOk(pointEnCours, formes)) {
//                if(!arrayContient(pointsOcean, voisin)) {
//                    pointsOcean.add(voisin);
//                    pile.add(0, voisin);
//                }
//            }
//            pile.remove(pointEnCours);
//        }
//        
//        return pointsOcean;
//    }
}
