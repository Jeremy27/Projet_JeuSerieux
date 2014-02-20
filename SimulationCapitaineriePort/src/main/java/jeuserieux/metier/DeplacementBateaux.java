package jeuserieux.metier;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import jeuserieux.modele.Forme;
import jeuserieux.modele.Navire;
import jeuserieux.modele.Partie;
import jeuserieux.modele.Quai;
import jeuserieux.modele.enumeration.TypeShape;
import jeuserieux.modele.outils.PointPathFinding;
import jeuserieux.presentation.PanelMap;

/**
 * Cette classe permet de calculer le déplacement d'un point de départ à un point d'arrivée sur la carte
 * @author gary
 */
public class DeplacementBateaux extends Thread{
    public static Point2D DEPART_DEFAUT = new Point2D.Double(0.09364530542986425, 49.45562308998302);
    public static Point2D SORTIE_DEFAUT = new Point2D.Double(0.09038447939866369, 49.4715725);
    public static double PASVOISIN = 0.0007;
    private final Navire _bateau;
    private Point2D _destination;
    private final ArrayList<Forme> _formes;
    private final PanelMap _map;
    private ArrayList<PointPathFinding> _resultat;
    private long _debut;
    private Quai _quai;
    
    public DeplacementBateaux(Navire bateau, Quai quai, ArrayList<Forme> formes, PanelMap map) {
        _quai = quai;
        _destination = quai.getZoneArrimage().getCentre();
        System.out.println("destination: " + _destination);
        _bateau = bateau;
        _formes = formes;
        _map = map;
        _resultat = new ArrayList<>();
    }
    
    public DeplacementBateaux(Navire bateau, Point2D destination, ArrayList<Forme> formes, PanelMap map) {
        
        _destination = destination;
        System.out.println("taille: " + formes.size());
        System.out.println("destination: " + _destination);
        _bateau = bateau;
        _formes = formes;
        _map = map;
        _resultat = new ArrayList<>();
    }
    
    @Override
    public void run() {
//        try {
//            sleep(10);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(DeplacementBateaux.class.getName()).log(Level.SEVERE, null, ex);
//        }
        _debut = System.currentTimeMillis();
        deplacer();
        while(Partie._tempsCourant>=_bateau.getHeurePriseEnCharge()+_bateau.getTempsPriseEnCharge()) {
            _destination = SORTIE_DEFAUT;
            deplacer();
        }
        
    }
    
    /**
     * Cette fonction déplace un navire vers un point d'arrivée en utilisant l'algorithme a*
     * @return Les points parcourus pour trouver le chemin
     */
    public ArrayList<PointPathFinding> deplacer() {
        System.out.println("Déplacement... " + _bateau);
        
        if(_bateau.getPosition().getX()==-1 && _bateau.getPosition().getY()==-1) {
            _bateau.setPosition(DEPART_DEFAUT);
            _bateau.setVisible(true);
            _map.refresh();
        }
        
        double x = _bateau.getPosition().getX();
        double y = _bateau.getPosition().getY();
        
        //PointPathFinding pointEnCours = new PointPathFinding(bateau.getPosition(), 0);
        PointPathFinding depart = new PointPathFinding(x, y, 0);
        PointPathFinding pointEnCours = depart;
        ArrayList<PointPathFinding> pile = new ArrayList<>();
        ArrayList<PointPathFinding> pointsVisites = new ArrayList<>();
        pile.add(pointEnCours);
        
        while(!pile.isEmpty()) {
            pointEnCours = pile.get(0);
            
            
            if(pointEnCours.egal(_destination)) { //si on a trouvé
                pile.clear();
                break;
            }
            
            TreeSet<PointPathFinding> voisinsPriorises = trierVoisins(pointEnCours, pointsVisites);
            while(!voisinsPriorises.isEmpty()) {
                
                PointPathFinding voisin = voisinsPriorises.pollFirst();
                voisin.majCouts();
                if(!pointsVisites.contains(voisin)) {
                    pile.add(0, voisin);
                    pointsVisites.add(voisin);
                }
            }
            pile.remove(pointEnCours);
        }
        
        if(pointEnCours.egal(_destination)) {
            if(_quai!=null) {
                _bateau.setAssigneQuai(true);
                _quai.ajouterNavireAQuai(_bateau);
            }
            //on a trouvé
            ArrayList<PointPathFinding> chemin = new ArrayList<>();
            chemin.add(pointEnCours);
            while(!pointEnCours.egal(_bateau.getPosition())) {
                pointEnCours = trierVoisins(pointEnCours);
                if(pointEnCours==null) {
                    System.out.println("NULL");
                    break;
                }
                chemin.add(pointEnCours);
            }
            long temps = System.currentTimeMillis()-_debut;
            System.out.println("temps pour trouver le chemin: " + temps);
            //return chemin;
            for(int i=chemin.size()-1;i>=0;i--) {
                PointPathFinding p = chemin.get(i);
                _bateau.setAngle(getAngle(_bateau.getPosition(), p));
                _bateau.setPosition(p);
                _bateau.constructionNavire();
                
                _map.refresh();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DeplacementBateaux.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            _bateau.setVisible(false);
        }
        _resultat=pointsVisites;
        return pointsVisites;
    }
    
    /**
     * Cette fonction retourne l'angle entre deux points par rapport à l'axe x
     * @param depart premier point de calcul de l'angle
     * @param arrivee second point de calcul de l'angle
     * @return l'angle entre les deux points en degré
     */
    public double getAngle(Point2D depart, Point2D arrivee) {
        double deltaX = arrivee.getX()-depart.getX();
        double deltaY = arrivee.getY()-depart.getY();
        return (Math.atan2(deltaY, deltaX) * 180 / Math.PI)*-1.0;
    }
    
    /**
     * Cette fonction sert à retrouver le prochain voisin du meilleur chemin trouvé
     * @param pointEnCours point courant
     * @return le voisin du meilleur chemin
     */
    public PointPathFinding trierVoisins(PointPathFinding pointEnCours) {
        PointPathFinding min = pointEnCours;
        for(PointPathFinding p:pointEnCours.getVoisins()) {
            if(p.getCout()<min.getCout()) {
                min = p;
            }
        }
        if(min==pointEnCours) {
            return null;
        } else {
            return min;
        }
    }
    
    /**
     * Cette fonction retrouve un point existant à partir de ses coordonnées
     * Si il n'existe pas, elle le créé
     * @param point point courant
     * @param diffX différence de l'axe x du point recherché par rapport au point courant
     * @param diffY différence de l'axe y du point recherché par rapport au point courant
     * @param visites points créés précédement durant la recherche de chemin
     * @return Le point trouvé ou créé
     */
    public PointPathFinding trouverPoint(PointPathFinding point, double diffX, double diffY, ArrayList<PointPathFinding> visites) {
        double x = point.getX();
        double y = point.getY();
        int cout = point.getCout();
        PointPathFinding trouve = getPointExistant(x+diffX, y+diffY, visites);
        if(trouve==null) {
            trouve = new PointPathFinding(x+diffX, y+diffY, cout+1);
            if(deplacementPossible(trouve)) {
                if(trouve.getX()>=0.09 && trouve.getX()<=0.1900875 &&
                    trouve.getY()>=49.448 && trouve.getY()<=49.488) {
                    trouve.setDistance(trouve.distance(_destination));
                    trouve.setCout(cout+1);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            trouve.setDistance(trouve.distance(_destination));
            if(trouve.getCout()+1<point.getCout()) {
                trouve.setCout(point.getCout()+1);
            }
            
        }
        return trouve;
    }
    
    /**
     * Cette fonction retourne un treeset classant les voisins du point en cours selon leur qualité par rapport au point de destination
     * @see java.util.TreeSet
     * @param pointEnCours point courant
     * @param visites points créés précédement durant la recherche de chemin
     * @return un treeset triant les voisins du point courant selon leur qualité
     */
    public TreeSet<PointPathFinding> trierVoisins(PointPathFinding pointEnCours, ArrayList<PointPathFinding> visites) {
        TreeSet<PointPathFinding> voisinsPriorises = new TreeSet<>();
        
        //haut
        if(pointEnCours.getVoisinHaut()==null) {
            PointPathFinding haut = trouverPoint(pointEnCours, 0, PASVOISIN, visites);
            if(haut!=null) {
                pointEnCours.setVoisinHaut(haut);
                haut.setVoisinBas(pointEnCours);
                voisinsPriorises.add(haut);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinHaut());
        }
        
        //bas
        if(pointEnCours.getVoisinBas()==null) {
            PointPathFinding bas = trouverPoint(pointEnCours, 0, -PASVOISIN, visites);
            if(bas!=null) {
                pointEnCours.setVoisinBas(bas);
                bas.setVoisinHaut(pointEnCours);
                voisinsPriorises.add(bas);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinBas());
        }
        
        //diag haut droit
        if(pointEnCours.getVoisinHautDroit()==null) {
            PointPathFinding hautDroit = trouverPoint(pointEnCours, PASVOISIN, PASVOISIN, visites);
            if(hautDroit!=null) {
                pointEnCours.setVoisinHautDroit(hautDroit);
                hautDroit.setVoisinBasGauche(pointEnCours);
                voisinsPriorises.add(hautDroit);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinHautDroit());
        }
        //diag haut gauche
        if(pointEnCours.getVoisinHautGauche()==null) {
            PointPathFinding hautGauche = trouverPoint(pointEnCours, -PASVOISIN, PASVOISIN, visites);
            if(hautGauche!=null) {
                pointEnCours.setVoisinHautGauche(hautGauche);
                hautGauche.setVoisinBasDroit(pointEnCours);
                voisinsPriorises.add(hautGauche);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinHautGauche());
        }
        //diag bas droit
        if(pointEnCours.getVoisinBasDroit()==null) {
            PointPathFinding basDroit = trouverPoint(pointEnCours, PASVOISIN, -PASVOISIN, visites);
            if(basDroit!=null) {
                pointEnCours.setVoisinBasDroit(basDroit);
                basDroit.setVoisinHautGauche(pointEnCours);
                voisinsPriorises.add(basDroit);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinBasDroit());
        }
        //diag bas gauche
        if(pointEnCours.getVoisinBasGauche()==null) {
            PointPathFinding basGauche = trouverPoint(pointEnCours, -PASVOISIN, -PASVOISIN, visites);
            if(basGauche!=null) {
                pointEnCours.setVoisinBasGauche(basGauche);
                basGauche.setVoisinHautDroit(pointEnCours);
                voisinsPriorises.add(basGauche);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinBasGauche());
        }
        
        //gauche
        if(pointEnCours.getVoisinGauche()==null) {
            PointPathFinding gauche = trouverPoint(pointEnCours, -PASVOISIN, 0, visites);
            if(gauche!=null) {
                pointEnCours.setVoisinGauche(gauche);
                gauche.setVoisinDroit(pointEnCours);
                voisinsPriorises.add(gauche);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinGauche());
        }
         
        //droit
        if(pointEnCours.getVoisinDroit()==null) {
            PointPathFinding droit = trouverPoint(pointEnCours, PASVOISIN, 0, visites);
            if(droit!=null) {
                pointEnCours.setVoisinDroit(droit);
                droit.setVoisinGauche(pointEnCours);
                voisinsPriorises.add(droit);
            }
        } else {
            voisinsPriorises.add(pointEnCours.getVoisinDroit());
        }
        
        return voisinsPriorises;
    }
    
    /**
     * Cette fonction vérifie si le déplacement du navire sur le point en paramètre est possible
     * @param point point de déplacement à tester
     * @return vrai si le déplacement et possible, faux sinon
     */
    public boolean deplacementPossible(Point2D point) {
        Path2D p = _bateau.boundingsPosition(point);
        boolean dansEau = false;
        boolean dansShape = false;
        for(Forme forme:_formes) {
            if(forme.getPathOriginal().intersects(p.getBounds2D())) {
                if (forme.getTypeForme()==TypeShape.NATURAL) {
                    dansEau = true;
                } else if(forme==_bateau) {
                    return true;
                } else {
                    if(forme.getTypeForme()!=TypeShape.HIGHWAY) {
                        dansShape = true;
                    }
                }
            }
        }
        return dansEau && !dansShape;
    }
    
    /**
     * Cette fonction vérifie si un point existe déjà dans les points créés précédement durant la recherche de chemin
     * @param x position x du point recherché
     * @param y position y du point recherché
     * @param arr liste des points créés précédement durant la recherche de chemin
     * @return null si le point n'existe pas déjà, sinon retourne ce point
     */
    private PointPathFinding getPointExistant(double x, double y, ArrayList<PointPathFinding> arr) {
        for(PointPathFinding p:arr) {
            if(p.getX()==x && p.getY()==y) {
                return p;
            }
        }
        return null;
    }
    
    /*
        #### Partie utilisée pour générer l'océan
    */
//    public static boolean arrayContient(ArrayList<PointPathFinding> pointsVisites, PointPathFinding pointEnCours) {
//        for(PointPathFinding p:pointsVisites) {
//            if(p.equals(pointEnCours)) {
//                return true;
//            }
//        }
//        return false;
//    }
    
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

    /**
     * @return the _resultat
     */
    public ArrayList<PointPathFinding> getResultat() {
        return _resultat;
    }
}
