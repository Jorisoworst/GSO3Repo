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
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author IndyGames
 */
public class CopilotGUI extends JFrame {

    public static final boolean DEBUG_MODE = false;
    public static final boolean FULLSCREEN = true;
    public static final double NANO_TO_BASE = 1.0e9;
    public static final double BULLET_FORCE = 20;
    public static final double ZEBRA_FORCE = 5;
    private double force;
    private int fps, lives, score;
    private Random rnd;
    private Timer timer;
    private JPanel contentPane, labelPanel;
    private JLabel scoreLabel, livesLabel, altLabel, speedLabel, fuelLabel, fpsLabel;
    private GameController gameController;
    private Image airplaneImage, backgroundImage, bulletImage, obstacleImage1, obstacleImage2;
    private Font font;
    protected Canvas canvas;
    protected World world;
    protected int screenWidth, screenHeight;
    protected boolean stopped;
    protected long last, lastTime;

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
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.start();
    }

    /**
     * Constructor for this gui.
     */
    public CopilotGUI() {
        super("CoPilot");
        this.rnd = new Random();
        this.timer = new Timer();
        this.stopped = false;
        this.lives = 3;
        this.score = 0;

        try {
            this.airplaneImage = ImageIO.read(this.getClass().getClassLoader().getResource("airplane.png"));
            this.airplaneImage = this.airplaneImage.getScaledInstance(103, 60, 1);
            this.backgroundImage = ImageIO.read(this.getClass().getClassLoader().getResource("background.png"));
            this.bulletImage = ImageIO.read(this.getClass().getClassLoader().getResource("bullet.png"));
            this.obstacleImage1 = ImageIO.read(this.getClass().getClassLoader().getResource("goose_wing_down.png"));
            this.obstacleImage1 = this.obstacleImage1.getScaledInstance(103, 60, 1);

            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, 20);
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(CopilotGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
                super.windowClosing(e);
            }
        });

        this.createGUI();
        this.setContentPane(this.contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.gameController = new GameController(this.contentPane);

        if (FULLSCREEN) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setUndecorated(true);
            this.setResizable(false);
            this.force = 10;
        } else {
            this.force = 3;
        }

        this.pack();
        this.initializeWorld();
    }

    /**
     * Creates game objects and adds them to the world.
     */
    protected void initializeWorld() {
        this.world = new World();
        this.world.setGravity(new Vector2(0.0, 9.81));
        this.world.addListener(this.gameController);

        GameObject airplane = new Airplane(this.airplaneImage);
        Rectangle airplaneShape = new Rectangle(airplane.getWidth(), airplane.getHeight());
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
                    lastTime = System.nanoTime();
                    gameLoop();
                    fps = Math.round(1000000000 / (System.nanoTime() - lastTime));
                    lastTime = System.nanoTime();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
        this.startTimer();
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
        this.world.updatev(elapsedTime);
        this.update(elapsedTime);
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

            if (DEBUG_MODE) {
                Vector2 v = go.getTransform().getTranslation();
                Double d1 = go.getWidth();
                Double d2 = go.getHeight();
                Double d3 = v.x;
                Double d4 = v.y;
                g.setColor(Color.MAGENTA);
                g.fillRect(d3.intValue(), d4.intValue(), d1.intValue(), d2.intValue());
            }
        }
    }

    /**
     * Update the game and it's gameobjects.
     *
     * @param elapsedTime the total elapsed time since the last frame.
     */
    protected void update(double elapsedTime) {
        String key = this.gameController.KEY_PRESSED;

        if (key.equals("ESCAPE")) {
            System.exit(0);
        }

        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);

            if (go instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) go;

                if (obstacle.getTransform().getTranslationX() + obstacle.getWidth() < 0) {
                    this.world.removeBody(obstacle);
                } else {
                    obstacle.translate(new Vector2(-ZEBRA_FORCE, 0));
                }
            }

            if (go instanceof Bullet) {
                Bullet bullet = (Bullet) go;

                if (bullet.getTransform().getTranslationX() - bullet.getWidth() > this.screenWidth) {
                    this.world.removeBody(bullet);
                } else {
                    bullet.translate(new Vector2(BULLET_FORCE, 0));
                    Object o = bullet.getUserData();

                    if (o != null) {
                        if (o instanceof Obstacle) {
                            this.world.removeBody(bullet);
                            this.world.removeBody((Obstacle) o);
                            this.score++;
                        }
                    }
                }
            }

            if (go instanceof Airplane) {
                Airplane airplane = (Airplane) go;
                double airplaneWidth = airplane.getWidth();
                double airplaneHeight = airplane.getHeight();

                Transform airplaneTransform = airplane.getTransform();
                double airplaneX = airplaneTransform.getTranslationX();
                double airplaneY = airplaneTransform.getTranslationY();

                this.altLabel.setText("Alt: " + (this.screenHeight - Math.round(airplaneY)));
                this.speedLabel.setText("Speed: " + ZEBRA_FORCE);
                this.fuelLabel.setText("Fuel: " + airplane.getFuelAmount());
                this.fpsLabel.setText("FPS:" + this.fps);

                switch (key) {
                    case "UP": {
                        if (airplaneY - (this.scoreLabel.getHeight() * 2) > 0) {
                            airplane.translate(new Vector2(0, -force));
                        }
                        break;
                    }
                    case "DOWN": {
                        if (airplaneY + airplaneHeight < this.screenHeight) {
                            airplane.translate(new Vector2(0, force));
                        }
                        break;
                    }
                    case "LEFT": {
                        if (airplaneX > 0) {
                            airplane.translate(new Vector2(-force, 0));
                        }
                        break;
                    }
                    case "RIGHT": {
                        if (airplaneX + airplaneWidth < this.screenWidth) {
                            airplane.translate(new Vector2(force, 0));
                        }
                        break;
                    }
                    case "SPACE": {
                        Bullet bullet = new Bullet(this.bulletImage, new Vector2(airplaneX + (airplaneWidth - 20), airplaneY + (airplaneHeight / 2) - 10));
                        Rectangle bulletShape = new Rectangle(bullet.getWidth(), bullet.getHeight());
                        bullet.addFixture(bulletShape);
                        bullet.setMass(MassType.FIXED_LINEAR_VELOCITY);
                        bullet.translate(bullet.getLocation());
                        this.world.addBody(bullet);
                        this.gameController.KEY_PRESSED = "NONE";
                        break;
                    }
                    default: {
                        break;
                    }
                }

                Object o = airplane.getUserData();

                if (o != null) {
                    if (o instanceof Obstacle) {
                        airplane.setUserData(null);
                        this.world.removeBody((Obstacle) o);
                        this.lives--;
                    }
                }

                if (this.lives <= 0 || airplaneY + airplaneHeight >= this.screenHeight) {
                    this.world.removeBody(airplane);
                }

                if (!this.world.containsBody(airplane)) {
                    this.stop();
                    this.gameOver();
                }
            }
        }

        this.scoreLabel.setText("Score: " + this.score);
        this.livesLabel.setText("Lives: " + this.lives);
    }

    /**
     * Start the time.
     */
    private void startTimer() {
        int timeinterval = 1500;

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spawnObject();
            }

        }, 0, timeinterval);
    }

    /**
     * Spawn an obstacle.
     */
    public void spawnObject() {
        Obstacle obstacle = new Obstacle(this.obstacleImage1);
        Rectangle objShape = new Rectangle(obstacle.getWidth(), obstacle.getHeight());
        obstacle.addFixture(objShape);
        obstacle.setMass(MassType.FIXED_LINEAR_VELOCITY);
        obstacle.translate(this.rnd.nextInt(this.screenWidth / 2) + this.screenWidth, this.rnd.nextInt(this.screenHeight - (this.scoreLabel.getHeight() * 2)));

        this.world.addBody(obstacle);
    }

    /**
     * Create the gui components.
     */
    private void createGUI() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new BorderLayout());

        this.labelPanel = new JPanel();
        this.labelPanel.setLayout(new GridLayout(0, 5));
        this.labelPanel.setBackground(new Color(121, 201, 249));

        this.scoreLabel = new JLabel("Score: 0");
        this.labelPanel.add(this.scoreLabel);

        this.livesLabel = new JLabel("Lives: 0");
        this.labelPanel.add(this.livesLabel);

        this.altLabel = new JLabel("Alt: 0");
        this.labelPanel.add(this.altLabel);

        this.speedLabel = new JLabel("Speed: 0");
        this.labelPanel.add(this.speedLabel);

        this.fuelLabel = new JLabel("Fuel: 0");
        this.labelPanel.add(this.fuelLabel);

        this.fpsLabel = new JLabel("FPS:" + this.fps);
        this.labelPanel.add(this.fpsLabel);

        for (Component comp : this.labelPanel.getComponents()) {
            JLabel lbl = (JLabel) comp;
            lbl.setFont(this.font);
            lbl.setForeground(Color.WHITE);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
        }

        this.contentPane.add(this.labelPanel, BorderLayout.PAGE_START);

        Dimension size;

        if (FULLSCREEN) {
            size = Toolkit.getDefaultToolkit().getScreenSize();
        } else {
            size = new Dimension(800, 600);
        }

        this.screenWidth = size.width;
        this.screenHeight = size.height;

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);

        this.contentPane.add(this.canvas, BorderLayout.PAGE_END);
    }

    public void gameOver() {
        GameOverGUI goGUI = new GameOverGUI(this);
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
