/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.domain.Airplane;
import copilot.domain.GameObject;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javafx.scene.image.Image;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;

/**
 *
 * @author indyspaan
 */
public class CopilotGUI extends JFrame {

    private final int screenWidth = 800;
    private final int screenHeight = 600;
    private final Image background;
    private final Image airplane;
    private final Canvas canvas;
    private World world;
    private boolean stopped;
    private long last;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        CopilotGUI window = new CopilotGUI();
        window.setVisible(true);
        window.start();
    }

    public CopilotGUI() {
        super("CoPilot");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.background = new Image(getClass().getResourceAsStream("achtergrond.png"));
        this.airplane = new Image(getClass().getResourceAsStream("Plane.png"));

        Dimension size = new Dimension(screenWidth, screenHeight);

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);

        this.add(this.canvas);

        this.setResizable(false);
        this.pack();

        this.stopped = false;

        this.initializeWorld();
    }

    /**
     * Creates game objects and adds them to the world.
     */
    private void initializeWorld() {
        this.world = new World();

        Rectangle rectShape = new Rectangle(1.0, 1.0);
        GameObject rectangle = new Airplane(this.airplane);
        rectangle.addFixture(rectShape);
        rectangle.setMass(MassType.NORMAL);
        rectangle.translate(0.0, 2.0);
        rectangle.getLinearVelocity().set(-5.0, 0.0);
        this.world.addBody(rectangle);
    }

    /**
     * Start active rendering the example.
     */
    public void start() {
        this.last = System.nanoTime();

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isStopped()) {
                    gameLoop();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
    }

    /**
     * The method calling the necessary methods to update the game, graphics,
     * and poll for input.
     */
    private void gameLoop() {

    }

    /**
     * Renders the example.
     *
     * @param g the graphics object to render to
     */
    private void render(Graphics2D g) {

    }

    /**
     * Stops the game.
     */
    public synchronized void stop() {
        this.stopped = true;
    }

    /**
     * Returns true if the game is stopped.
     *
     * @return boolean true if stopped
     */
    public synchronized boolean isStopped() {
        return this.stopped;
    }
}
