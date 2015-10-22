/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.awt.Point;
import javafx.scene.image.Image;

/**
 *
 * @author Ruud
 */
public class Bullet extends GameObject{

    private Point bulletDirection;
    
    public Bullet(Image image, Point mousePoint) {
        super(image);
        this.bulletDirection = mousePoint;
        
    }
    
}
