/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain.Test;

import copilot.domain.Airplane;
import copilot.domain.Obstacle;
import copilot.domain.Kerosine;
import copilot.view.CopilotGUI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Image;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joris
 */
public class GameObjectTest {

    private Image image;
    private Airplane airplane;
    private Kerosine kerosine;
    private Obstacle obstacle;

    @Before
    public void setUp() {
        try {
            this.image = ImageIO.read(this.getClass().getResource("Plane.png"));
        } catch (IOException ex) {
            Logger.getLogger(CopilotGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.airplane = new Airplane(this.image);
        this.kerosine = new Kerosine(this.image);
        this.obstacle = new Obstacle(this.image);
    }

    /**
     * Test of onCollision method, of class GameObject.
     */
    @Test
    public void testOnCollisionPickup() {
        this.airplane.onCollision(this.kerosine);
        assertFalse(this.airplane.isDestroyed());
        assertTrue(this.kerosine.isPickedUp());
        assertTrue(this.kerosine.isDestroyed());
        assertEquals(this.airplane.getFuelAmount(), this.kerosine.getAmount());
    }

    /**
     * Test of onCollision method, of class GameObject.
     */
    @Test
    public void testOnCollisionObstacle() {
        this.airplane.onCollision(this.obstacle);
        assertTrue(this.airplane.isDestroyed());
        assertTrue(this.obstacle.isDestroyed());
    }
}
