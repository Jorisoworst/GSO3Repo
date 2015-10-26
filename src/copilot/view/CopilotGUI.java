/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GameController;
import copilot.domain.Airplane;
import copilot.domain.Bullet;
import copilot.domain.GameObject;
import copilot.domain.Obstacle;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.dyn4j.dynamics.Body;
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
    public static final double FORCE = 1000;
    public static final double BULLET_FORCE = 20;
    public static final double ZEBRA_FORCE = 5;
    protected int screenWidth, screenHeight;
    private int score = 0;
    private Random rnd;
    private Timer timer;
    private JPanel contentPane;
    private JLabel lblScore;
    private GameController gameController;
    private Image airplaneImage, backgroundImage, bulletImage, obstacleImage1, obstacleImage2;
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

        this.stopped = false;
        this.rnd = new Random();
        this.timer = new Timer();

        try {
            this.airplaneImage = ImageIO.read(this.getClass().getClassLoader().getResource("airplane.png"));
            this.airplaneImage = this.airplaneImage.getScaledInstance(103, 60, 1);
            this.backgroundImage = ImageIO.read(this.getClass().getClassLoader().getResource("background.png"));
            this.bulletImage = ImageIO.read(this.getClass().getClassLoader().getResource("bullet.png"));
            this.obstacleImage1 = ImageIO.read(this.getClass().getClassLoader().getResource("goose_wing_down.png"));
            this.obstacleImage1 = this.obstacleImage1.getScaledInstance(103, 60, 1);
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

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;

        this.createGUI();

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);
        this.add(this.canvas);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.pack();

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
        FireTimer();
    }

    public void FireTimer() {
        int timeinterval = 1500;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawnObject();
            }
        }, 0, timeinterval);
    }

    /**
     * The method calling the necessary methods to update the game, graphics,
     * and poll for input.
     */
    protected void gameLoop() {
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
        this.update(elapsedTime);

        this.lblScore.setText(
                "Score: " + this.score
                + " Speed: " + this.score
                + " Alt: " + this.score
                + " Fuel: " + this.score
                + " Objects: " + this.score
        );
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
     * @param elapsedTime the total elapsed time since the last frame.
     */
    protected void update(double elapsedTime) {
        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);

            if (go instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) go;
                obstacle.translate(new Vector2(-ZEBRA_FORCE, 0));
            }

            if (go instanceof Bullet) {
                Bullet bullet = (Bullet) go;
                bullet.translate(new Vector2(BULLET_FORCE, 0));

                for (Body b : bullet.getInContactBodies(true)) {
                    System.out.println("Bullet in contact with Body ^^");
                    
                    if (b instanceof Obstacle) {
                        bullet.setActive(false);
                        b.setActive(false);
                        
                        System.out.println("Obstacle destroyed by bullet :D");
                    }
                }
            }

            if (go instanceof Airplane) {
                Airplane airplane = (Airplane) go;

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
                        bullet.setMass(MassType.FIXED_LINEAR_VELOCITY);
                        bullet.translate(airplane.getTransform().getTranslation());
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
        }
    }

    public void spawnObject() {
        Rectangle objShape = new Rectangle(1.0, 1.0);
        Obstacle obstacle = new Obstacle(this.obstacleImage1);
        obstacle.addFixture(objShape);
        obstacle.setMass(MassType.FIXED_LINEAR_VELOCITY);
        obstacle.translate(this.rnd.nextInt(this.screenWidth / 2) + this.screenWidth, this.rnd.nextInt(this.screenHeight));
        this.world.addBody(obstacle);
    }

    /**
     * Create the gui components.
     */
    private void createGUI() {
        this.contentPane = new JPanel();
        this.lblScore = new JLabel(
                "Score: " + this.score
                + " Speed: " + this.score
                + " Alt: " + this.score
                + " Fuel: " + this.score
                + " Objects: " + this.score
        );
        this.lblScore.setFont(new Font("Verdana", 1, 20));
        this.contentPane.add(this.lblScore);
        this.setContentPane(this.contentPane);
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
