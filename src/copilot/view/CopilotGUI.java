/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GameController;
import copilot.domain.Airplane;
import copilot.domain.Bullet;
import copilot.domain.GameObject;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author indyspaan
 */
public class CopilotGUI extends JFrame {

    public static final double NANO_TO_BASE = 1.0e9;
    public static final double FORCE = 100;
    public static final double BULLET_FORCE = 10000000;
    protected int screenWidth = 800;
    protected int screenHeight = 600;
    private GameController gameController;
    private Image airplaneImage;
    private Image backgroundImage;
    private Image bulletImage;
    protected Canvas canvas;
    protected World world;
    protected boolean stopped;
    protected long last;

    /**
     * Main method.
     *
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

    /**
     * Constructor for this gui.
     */
    public CopilotGUI() {
        super("CoPilot");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            this.airplaneImage = ImageIO.read(new File("C:\\Users\\Joris\\Documents\\School\\Proftaak\\CoPilot\\src\\copilot\\view\\Plane.png"));
            this.backgroundImage = ImageIO.read(new File("C:\\Users\\Joris\\Documents\\School\\Proftaak\\CoPilot\\src\\copilot\\view\\achtergrond.png"));
            this.bulletImage = ImageIO.read(new File("C:\\Users\\Joris\\Documents\\School\\Proftaak\\CoPilot\\src\\copilot\\view\\bullet.png"));
        } catch (IOException ex) {
            Logger.getLogger(CopilotGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
                super.windowClosing(e);
            }
        });

        this.gameController = new GameController();
        this.addKeyListener(this.gameController);

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
    protected void initializeWorld() {
        this.world = new World();
        this.world.setGravity(new Vector2(0.0, 9.81));

        Rectangle airplaneShape = new Rectangle(1.0, 1.0);
        GameObject airplane = new Airplane(this.airplaneImage);
        airplane.addFixture(airplaneShape);
        airplane.setMass(MassType.NORMAL);
        airplane.translate(this.screenWidth / 2 - (airplane.getWidth() / 2), this.screenHeight / 2 - (airplane.getHeight()));

        this.world.addBody(airplane);
    }

    /**
     * Start active rendering the game.
     */
    public void start() {
        this.last = System.nanoTime();
        this.canvas.setIgnoreRepaint(true);
        this.canvas.createBufferStrategy(2);

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
    protected void gameLoop() {
        Airplane airplane = null;

        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);

            if (go instanceof Airplane) {
                airplane = (Airplane) go;
            }
        }

        if (airplane != null) {
            switch (this.gameController.KEY_PRESSED) {
                case "UP": {
                    airplane.applyForce(new Vector2(0, -FORCE));
                    break;
                }
                case "DOWN": {
                    airplane.applyForce(new Vector2(0, FORCE));
                    break;
                }
                case "LEFT": {
                    airplane.applyForce(new Vector2(-FORCE, 0));
                    break;
                }
                case "RIGHT": {
                    airplane.applyForce(new Vector2(FORCE, 0));
                    break;
                }
                case "SPACE": {
                    Rectangle bulletShape = new Rectangle(1.0, 1.0);
                    Bullet bullet = new Bullet(this.bulletImage, airplane.getTransform().getTranslation());
                    bullet.addFixture(bulletShape);
                    bullet.setMass(MassType.NORMAL);
                    bullet.translate(airplane.getTransform().getTranslation());
                    bullet.applyForce(new Vector2(BULLET_FORCE, 0));
                    this.world.addBody(bullet);
                    this.gameController.KEY_PRESSED = "NONE";
                    break;
                }
                case "ESCAPE": {
                    System.exit(0);
                    break;
                }
                default: {
                    airplane.clearForce();
                    break;
                }
            }
        }

        BufferStrategy strategy = this.canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        this.render(g);
        g.dispose();

        if (!strategy.contentsLost()) {
            strategy.show();
        }

        Toolkit.getDefaultToolkit().sync();

        long time = System.nanoTime();
        long diff = time - this.last;
        this.last = time;
        double elapsedTime = diff / NANO_TO_BASE;
        this.world.update(elapsedTime);
    }

    /**
     * Renders the gameobjects.
     *
     * @param g the graphics object to render to
     */
    protected void render(Graphics2D g) {
        g.drawImage(this.backgroundImage, 0, 0, null);

        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);
            go.render(g);
        }
    }

    /**
     * Update the game and it's gameobjects.
     *
     * @param elapsedTime the total elapsed time since starting the game.
     */
    protected void update(double elapsedTime) {

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
