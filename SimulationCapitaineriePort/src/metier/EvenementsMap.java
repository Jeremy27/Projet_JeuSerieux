/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metier;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import presentation.AfficherMapBis;

/**
 *
 * @author brokep
 */
public class EvenementsMap {
    private AfficherMapBis _map;
    private Point2D _pointClick;
    
    public EvenementsMap(AfficherMapBis map) {
        _map = map;
    }
    
    public void wheel() {
        _map.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                //le curseur doit correspondre au zoom déjà fait et pas situation de départ
                //il faut jouer avec les coef pour faire la correspondance
                _map.setCurseur(e.getPoint());
                if(e.getWheelRotation()<0) {
                    //vers le haut -> zoom
                    _map.augmenterZoom();
                    
                } else {
                    //dezoom
                    _map.baisserZoom();
                }
            }
        });
    }
    
    public void move() {
        _map.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                
                _map.dragCurseur(_pointClick.getX()-e.getPoint().getX(), e.getPoint().getY()-_pointClick.getY());
                _pointClick = e.getPoint();
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                _map.affPositionSouris(e.getPoint());
            }
        });
        
        _map.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                _pointClick = e.getPoint();
            }
        });
    }
}
