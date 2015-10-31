/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GameController;
import copilot.domain.Airplane;
import copilot.domain.Bullet;
import copilot.domain.GameObject;
import copilot.domain.Kerosine;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
public class CopilotGUI {

    public static final boolean DEBUG_MODE = false;
    public static final boolean FULLSCREEN = false;
    public static final double NANO_TO_BASE = 1000000000;
    public static final int BULLET_FORCE = 25;
    public static final int FORCE = 7;
    public static final int MINIMAL_FPS = 20;
    public static double TARGET_FPS = 60;
    private final GameController gameController;
    private boolean stopped;
    private long last, lastTime;
    private int screenWidth, screenHeight, zebraForce, fps, lives, score, backgroundX, spawnTimer, fpsTimer, fuelTimer, speedTimer, animationTimer;
    private Canvas canvas;
    private World world;
    private Random rnd;
    private JFrame frame;
    private JPanel contentPane, labelPanel;
    private JLabel scoreLabel, livesLabel, altLabel, speedLabel, fuelLabel, fpsLabel;
    private Image airplaneImage, backgroundImage, bulletImage, obstacleImage1, obstacleImage2, kerosineImage;
    private Font font;
    
    private double testTime = 0;

    /**
     * Constructor for this gui.
     */
    public CopilotGUI() {
        this.frame = new JFrame("CoPilot");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        this.rnd = new Random();
        this.stopped = false;
        this.zebraForce = FORCE;
        this.lives = 3;
        this.score = 0;
        this.backgroundX = 0;
        this.spawnTimer = 0;
        this.fpsTimer = 0;
        this.fuelTimer = 0;
        this.speedTimer = 0;
        this.animationTimer = 0;

        try {
            this.airplaneImage = ImageIO.read(this.getClass().getClassLoader().getResource("airplane.png"));
            this.airplaneImage = this.airplaneImage.getScaledInstance(103, 60, 1);

            this.backgroundImage = ImageIO.read(this.getClass().getClassLoader().getResource("background.png"));
            this.bulletImage = ImageIO.read(this.getClass().getClassLoader().getResource("bullet.png"));

            this.obstacleImage1 = ImageIO.read(this.getClass().getClassLoader().getResource("goose_wing_down.png"));
            this.obstacleImage1 = this.obstacleImage1.getScaledInstance(103, 60, 1);

            this.obstacleImage2 = ImageIO.read(this.getClass().getClassLoader().getResource("goose_wing_up.png"));
            this.obstacleImage2 = this.obstacleImage2.getScaledInstance(103, 60, 1);

            this.kerosineImage = ImageIO.read(this.getClass().getClassLoader().getResource("fuel.png"));
            this.kerosineImage = this.kerosineImage.getScaledInstance(80, 80, 1);

            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, 20);
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(CopilotGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
                super.windowClosing(e);
            }
        });

        if (FULLSCREEN) {
            this.frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.frame.setUndecorated(true);
        }

        this.createGUI();
        this.gameController = new GameController(this.contentPane);
        this.frame.setContentPane(this.contentPane);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.pack();
        this.initializeWorld();
    }

    /**
     * Creates game objects and adds them to the world.
     */
    private void initializeWorld() {
        this.world = new World();
        this.world.setGravity(new Vector2(0.0, 9.81));
        this.world.addListener(this.gameController);

        GameObject airplane = new Airplane(this.airplaneImage);
        Rectangle airplaneShape = new Rectangle(airplane.getWidth(), airplane.getHeight());
        airplane.addFixture(airplaneShape);
        airplane.setMass(MassType.NORMAL);
        airplane.translate(this.screenWidth / 4 - (airplane.getWidth() / 2), this.screenHeight / 2 - (airplane.getHeight()));

        this.world.addBody(airplane);
    }

    /**
     * Start rendering the game.
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
                    fps = (int) Math.round(NANO_TO_BASE / (System.nanoTime() - lastTime));
                    if (fps <= TARGET_FPS && fps >= MINIMAL_FPS) {
                        TARGET_FPS = fps;
                    } else if (fps >= TARGET_FPS) {
                        if (fps <= 60) {
                            TARGET_FPS = fps;
                        } else {
                            TARGET_FPS = 60;
                        }
                    }
                    lastTime = System.nanoTime();
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
        Toolkit.getDefaultToolkit().sync();
        BufferStrategy strategy = this.canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        this.render(g);
        g.dispose();

        if (!strategy.contentsLost()) {
            strategy.show();
        }
            
        long time = System.nanoTime();
        double diff = (double)time - (double)this.last;
        this.last = time;
        //double elapsedTime = diff / (NANO_TO_BASE / TARGET_FPS);
        testTime += diff;
        if (testTime >= (NANO_TO_BASE / TARGET_FPS)) {
            
            System.out.println("DEBUG: testTime for refresh: " + testTime + " , needed time for refresh: " + (NANO_TO_BASE / TARGET_FPS));
            
            this.world.update(testTime / (NANO_TO_BASE / 60));
            this.update(testTime / (NANO_TO_BASE / 60));
            testTime = 0 + (testTime - (NANO_TO_BASE / TARGET_FPS));
            System.out.println("DEBUG: testTime after is: " + testTime);
        }
    }

    /**
     * Renders the gameobjects.
     *
     * @param g the graphics object to render to
     */
    protected void render(Graphics2D g) {
        g.drawImage(this.backgroundImage, this.backgroundX, 0, null);

        if (this.backgroundX <= 0) {
            g.drawImage(this.backgroundImage, this.backgroundX + this.backgroundImage.getWidth(null), 0, null);

            if (this.backgroundX <= -this.backgroundImage.getWidth(null)) {
                this.backgroundX = 0;
            }
        }

        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);
            go.render(g);

            if (DEBUG_MODE) {
                Vector2 gameObjectLocation = go.getTransform().getTranslation();
                Double gameObjectWidth = go.getWidth();
                Double gameObjectHeight = go.getHeight();
                Double gameObjectX = gameObjectLocation.x;
                Double gameObjectY = gameObjectLocation.y;
                g.setColor(Color.MAGENTA);
                g.fillRect(gameObjectX.intValue(), gameObjectY.intValue(), gameObjectWidth.intValue(), gameObjectHeight.intValue());
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

        this.spawnTimer += (elapsedTime * this.zebraForce) / 2;
        
        if (this.spawnTimer >= 150) {
            if ((rnd.nextInt(10) + 1) % 10 == 0) {
                spawnObject("P");
            } else {
                spawnObject("O");
            }

            this.spawnTimer = 0;
        }

        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);

            if (go instanceof Obstacle || go instanceof Kerosine) {
                if (go.getTransform().getTranslationX() + go.getWidth() < 0) {
                    this.world.removeBody(go);
                } else {
                    go.translate(new Vector2((-this.zebraForce * elapsedTime) / 2, 0));
                }

                if (go instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) go;

                    this.animationTimer += elapsedTime;

                    if (this.animationTimer >= 25) {
                        if (obstacle.getImage() == this.obstacleImage2) {
                            obstacle.setImage(this.obstacleImage1);
                        } else {
                            obstacle.setImage(this.obstacleImage2);
                        }

                        this.animationTimer = 0;
                    }
                }
            } else if (go instanceof Bullet) {
                Bullet bullet = (Bullet) go;

                if (bullet.getTransform().getTranslationX() - bullet.getWidth() > this.screenWidth) {
                    this.world.removeBody(bullet);
                } else {
                    bullet.translate(new Vector2(BULLET_FORCE * elapsedTime, 0));
                    Object o = bullet.getUserData();

                    if (o != null) {
                        if (o instanceof Obstacle) {
                            this.world.removeBody(bullet);
                            this.world.removeBody((Obstacle) o);
                            this.score++;
                        }
                    }
                }
            } else if (go instanceof Airplane) {
                Airplane airplane = (Airplane) go;
                double airplaneWidth = airplane.getWidth();
                double airplaneHeight = airplane.getHeight();

                Transform airplaneTransform = airplane.getTransform();
                double airplaneX = airplaneTransform.getTranslationX();
                double airplaneY = airplaneTransform.getTranslationY();

                this.fuelTimer += elapsedTime;

                if (this.fuelTimer >= 25) {
                    airplane.setFuelAmount(airplane.getFuelAmount() - 1);
                    this.fuelTimer = 0;
                }

                this.speedTimer += elapsedTime;

                if (this.speedTimer >= 100) {
                    this.zebraForce++;
                    this.score += elapsedTime * (this.zebraForce / 7);
                    this.speedTimer = 0;
                }

                this.fpsTimer += elapsedTime;

                if (this.fpsTimer >= 50) {
                    this.fpsLabel.setText("FPS: " + this.fps);
                    this.fpsTimer = 0;
                }

                airplane.setAltitude(this.screenHeight - (int) Math.round(airplaneY));

                switch (key) {
                    case "UP": {
                        if (airplane.getFuelAmount() > 0 && airplaneY - (this.scoreLabel.getHeight() * 1.5) > 0) {
                            airplane.translate(new Vector2(0, (-FORCE * elapsedTime) / 2));
                        }
                        break;
                    }
                    case "DOWN": {
                        if (airplaneY + airplaneHeight < this.screenHeight) {
                            airplane.translate(new Vector2(0, (FORCE * elapsedTime) / 2));
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
                    airplane.setUserData(null);

                    if (o instanceof Obstacle) {
                        this.world.removeBody((Obstacle) o);
                        this.lives--;
                    } else if (o instanceof Kerosine) {
                        Kerosine kerosine = (Kerosine) o;
                        this.world.removeBody(kerosine);
                        airplane.setFuelAmount(airplane.getFuelAmount() + kerosine.getAmount());
                    }
                }

                if (this.lives <= 0 || airplaneY >= this.screenHeight) {
                    this.world.removeBody(airplane);
                }

                this.fuelLabel.setText("Fuel: " + airplane.getFuelAmount());
                this.altLabel.setText("Alt: " + airplane.getAltitude());
                this.scoreLabel.setText("Score: " + this.score);
                this.livesLabel.setText("Lives: " + this.lives);
                this.speedLabel.setText("Speed: " + this.zebraForce);
                this.backgroundX -= (elapsedTime * this.zebraForce) / 2;

                if (!this.world.containsBody(airplane)) {
                    this.stop();
                    this.gameOver();
                }
            }
        }
    }

    /**
     * Spawn an object.
     *
     * @param type object type to spawn
     */
    public void spawnObject(String type) {
        GameObject go = null;

        switch (type) {
            case "O": {
                go = new Obstacle(this.obstacleImage1);
                break;
            }
            case "P": {
                go = new Kerosine(this.kerosineImage, this.rnd.nextInt(100) + 1);
                break;
            }
        }

        if (go != null) {
            int randomY = this.rnd.nextInt(this.screenHeight - this.scoreLabel.getHeight() - (int) go.getHeight());

            if (randomY > this.screenHeight - go.getHeight()) {
                randomY = this.screenHeight - (int) go.getHeight();
            } else if (randomY < this.scoreLabel.getHeight()) {
                randomY = this.scoreLabel.getHeight();
            }

            Rectangle objShape = new Rectangle(go.getWidth(), go.getHeight());
            go.addFixture(objShape);
            go.setMass(MassType.FIXED_LINEAR_VELOCITY);
            go.translate(
                    this.rnd.nextInt(this.screenWidth / 2) + this.screenWidth,
                    randomY
            );
            
            this.world.addBody(go);
        }
    }

    /**
     * Create the gui components.
     */
    private void createGUI() {
        this.contentPane = new JPanel();
        this.contentPane.setLayout(new BorderLayout());

        this.labelPanel = new JPanel();
        this.labelPanel.setLayout(new GridLayout(0, 6));
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

        this.fpsLabel = new JLabel("FPS: " + this.fps);
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
        GameOverGUI goGUI = new GameOverGUI(this.score);
        this.frame.dispose();
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
