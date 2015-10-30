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
    public static final double BULLET_FORCE = 2000;
    public static final double FORCE = 500;
    private final GameController gameController;
    private double zebraForce;
    private int fps, lives, score, backgroundX, fpsTimer, fuelTimer, speedTimer, animationTimer;
    private Random rnd;
    private Timer timer;
    private JPanel contentPane, labelPanel;
    private JLabel scoreLabel, livesLabel, altLabel, speedLabel, fuelLabel, fpsLabel;
    private Image airplaneImage, backgroundImage, bulletImage, obstacleImage1, obstacleImage2, kerosineImage;
    private Font font;
    protected Canvas canvas;
    protected World world;
    protected int screenWidth, screenHeight;
    protected boolean stopped;
    protected long last, lastTime;

    /**
     * Constructor for this gui.
     */
    public CopilotGUI() {
        super("CoPilot");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        this.rnd = new Random();
        this.timer = new Timer();
        this.stopped = false;
        this.zebraForce = FORCE;
        this.lives = 3;
        this.score = 0;
        this.fuelTimer = 0;
        this.fpsTimer = 0;
        this.speedTimer = 0;
        this.animationTimer = 0;
        this.backgroundX = 0;

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
            this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setUndecorated(true);
        }

        this.pack();
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
        this.world.update(elapsedTime);
        this.update(elapsedTime);
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

            if (go instanceof Obstacle || go instanceof Kerosine) {
                if (go.getTransform().getTranslationX() + go.getWidth() < 0) {
                    this.world.removeBody(go);
                } else {
                    go.translate(new Vector2(-this.zebraForce * elapsedTime, 0));
                }

                if (go instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) go;

                    this.animationTimer += 1 * (elapsedTime * this.fps);

                    if (this.animationTimer >= this.fps / 5) {
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

                this.fuelTimer += 1 * (elapsedTime * this.fps);

                if (this.fuelTimer >= this.fps / 4) {
                    airplane.setFuelAmount(airplane.getFuelAmount() - 1);
                    this.fuelTimer = 0;
                }

                this.speedTimer += 1 * (elapsedTime * this.fps);

                if (this.speedTimer >= this.fps / 4) {
                    this.zebraForce++;
                    this.speedTimer = 0;
                    this.score += 1 * (elapsedTime * this.zebraForce);
                }

                this.fpsTimer += 1 * (elapsedTime * this.fps);

                if (this.fpsTimer >= this.fps / 2) {
                    this.fpsTimer = 0;
                    this.fpsLabel.setText("FPS:" + this.fps);
                }

                airplane.setAltitude(this.screenHeight - (int) Math.round(airplaneY));

                switch (key) {
                    case "UP": {
                        if (airplane.getFuelAmount() > 0 && airplaneY - (this.scoreLabel.getHeight() * 1.5) > 0) {
                            airplane.translate(new Vector2(0, -FORCE * elapsedTime));
                        }
                        break;
                    }
                    case "DOWN": {
                        if (airplaneY + airplaneHeight < this.screenHeight) {
                            airplane.translate(new Vector2(0, FORCE * elapsedTime));
                        }
                        break;
                    }
                    case "LEFT": {
                        if (airplaneX > 0) {
                            airplane.translate(new Vector2(-FORCE * elapsedTime, 0));
                        }
                        break;
                    }
                    case "RIGHT": {
                        if (airplaneX + airplaneWidth < this.screenWidth) {
                            airplane.translate(new Vector2(FORCE * elapsedTime, 0));
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
                this.backgroundX -= 1 * (elapsedTime * (this.zebraForce / 2));

                if (!this.world.containsBody(airplane)) {
                    this.stop();
                    this.gameOver();
                }
            }
        }
    }

    /**
     * Start the time.
     */
    private void startTimer() {
        int timeinterval = 750;

        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if ((rnd.nextInt(5) + 1) % 5 == 0) {
                    spawnObject("P");
                } else {
                    spawnObject("O");
                }
            }

        }, 0, timeinterval);
    }

    /**
     * Spawn an object.
     *
     * @param type object type to spawn
     */
    public void spawnObject(String type) {
        switch (type) {
            case "O": {
                Obstacle obstacle = new Obstacle(this.obstacleImage1);
                Rectangle objShape = new Rectangle(obstacle.getWidth(), obstacle.getHeight());
                obstacle.addFixture(objShape);
                obstacle.setMass(MassType.FIXED_LINEAR_VELOCITY);
                obstacle.translate(
                        this.rnd.nextInt(this.screenWidth / 2) + this.screenWidth,
                        this.rnd.nextInt(this.screenHeight - (int) (obstacle.getHeight() * 2)) + obstacle.getHeight() + this.scoreLabel.getHeight()
                );
                this.world.addBody(obstacle);
                break;
            }
            case "P": {
                Kerosine kerosine = new Kerosine(this.kerosineImage, this.rnd.nextInt(100) + 1);
                Rectangle objShape = new Rectangle(kerosine.getWidth(), kerosine.getHeight());
                kerosine.addFixture(objShape);
                kerosine.setMass(MassType.FIXED_LINEAR_VELOCITY);
                kerosine.translate(
                        this.rnd.nextInt(this.screenWidth / 2) + this.screenWidth,
                        this.rnd.nextInt(this.screenHeight - (int) (kerosine.getHeight() * 2)) + kerosine.getHeight() + this.scoreLabel.getHeight()
                );
                this.world.addBody(kerosine);
                break;
            }
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
