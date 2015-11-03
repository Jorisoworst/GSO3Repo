package copilot.view.frame;

import copilot.controller.GUIController;
import copilot.controller.GameController;
import copilot.domain.Airplane;
import copilot.domain.Bullet;
import copilot.domain.GameObject;
import copilot.domain.Kerosine;
import copilot.domain.Obstacle;
import copilot.view.gui.AllCopilotGUI;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author IndyGames
 */
public class CopilotGUI extends JPanel {

    public static final boolean DEBUG_MODE = false;
    public static final long NANO_TO_BASE = 1000000000;
    public static final int BULLET_FORCE = 25;
    public static final int FORCE = 7;
    public static final int TARGET_FPS = 60;
    private final GameController gameController;
    private final Font font;
    private final int screenWidth, screenHeight;
    private boolean stopped;
    private double elapsedTime, targetInterval, diff, actualInterval;
    private long last, lastTime, time;
    private int zebraForce, fps, lives, score, backgroundX, spawnTimer,
            fpsTimer, fuelTimer, speedTimer, animationTimer, bulletsFired,
            clipSize, reloadTimer, reloadProgress, reloadCooldown;
    private Canvas canvas;
    private World world;
    private Random rnd;
    private JPanel labelPanel;
    private JLabel scoreLabel, livesLabel, bulletsLabel, altLabel, speedLabel,
            fuelLabel, fpsLabel;
    private Image airplaneImage, backgroundImage, bulletImage, obstacleImage1,
            obstacleImage2, kerosineImage;

    /**
     * Constructor for this gui.
     *
     * @param screenWidth the screenwidth
     * @param screenHeight the screenheight
     * @param font the font
     */
    public CopilotGUI(int screenWidth, int screenHeight, Font font) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.font = font;
        this.initializeVariables();
        this.loadResources();
        this.createGUI();
        this.gameController = new GameController(this);
        this.initializeWorld();
    }

    /**
     * Initialize all variables
     */
    private void initializeVariables() {
        this.rnd = new Random();
        this.stopped = false;
        this.zebraForce = FORCE;
        this.lives = 3;
        this.actualInterval = 0;
        this.diff = 0;
        this.time = 0;
        this.targetInterval = 0;
        this.elapsedTime = 0;
        this.score = 0;
        this.backgroundX = 0;
        this.spawnTimer = 0;
        this.fpsTimer = 0;
        this.fuelTimer = 0;
        this.speedTimer = 0;
        this.animationTimer = 0;
        this.reloadTimer = 0;
        this.reloadProgress = 0;
        this.reloadCooldown = 50;
        this.bulletsFired = 0;
        this.clipSize = 15; // TODO
    }

    /**
     * Load and initialize all resources
     */
    private void loadResources() {
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

        } catch (IOException ex) {
            Logger.getLogger(CopilotGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Create the gui components.
     */
    private void createGUI() {
        this.setLayout(new BorderLayout());

        this.labelPanel = new JPanel();
        this.labelPanel.setLayout(new GridLayout(0, 7));
        this.labelPanel.setBackground(new Color(121, 201, 249));

        this.scoreLabel = new JLabel("Score: 0");
        this.labelPanel.add(this.scoreLabel);

        this.livesLabel = new JLabel("Lives: 0");
        this.labelPanel.add(this.livesLabel);

        this.bulletsLabel = new JLabel("Bullets: " + (this.clipSize - this.bulletsFired));
        this.labelPanel.add(this.bulletsLabel);

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

        this.add(this.labelPanel, BorderLayout.PAGE_START);

        Dimension size = new Dimension(this.screenWidth, this.screenHeight);

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);

        this.add(this.canvas, BorderLayout.PAGE_END);
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
        airplane.translate(this.screenWidth / 4 - airplane.getWidth() / 2, this.screenHeight / 2 - airplane.getHeight());

        this.world.addBody(airplane);
    }

    /**
     * Start the game.
     */
    public void start() {
        GUIController.playStart();
        GUIController.playAirplane();
        GUIController.playGameSound();

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
        this.time = System.nanoTime();
        this.diff = (double) this.time - (double) this.last;
        this.last = this.time;
        this.elapsedTime += this.diff;
        this.targetInterval = NANO_TO_BASE / TARGET_FPS;
        this.actualInterval = this.elapsedTime / this.targetInterval;

        if (this.elapsedTime >= this.targetInterval) {
            this.world.update(this.actualInterval);
            this.update(this.actualInterval);
            this.elapsedTime = 0;
        }

        BufferStrategy strategy = this.canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        this.render(g);
        g.dispose();

        if (!strategy.contentsLost()) {
            strategy.show();
        }

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Update the game and it's gameobjects.
     *
     * @param elapsedTime the total elapsed time since the last frame.
     */
    protected void update(double elapsedTime) {
        String key = this.gameController.getKeyPressed();

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
                    go.translate(new Vector2(-this.zebraForce * elapsedTime, 0));
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
                            GUIController.playCollisionBullet();
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

                if (this.speedTimer >= 500) {
                    this.zebraForce++;
                    this.score += elapsedTime * (this.zebraForce / 7);
                    this.speedTimer = 0;
                }

                this.fpsTimer += elapsedTime;

                if (this.fpsTimer >= 50) {
                    this.fpsLabel.setText("FPS: " + this.fps);
                    this.fpsTimer = 0;
                }

                if (this.bulletsFired >= this.clipSize) {
                    this.reloadTimer += elapsedTime;
                    this.reloadProgress = 100 / this.reloadCooldown * this.reloadTimer;

                    if (this.reloadTimer >= this.reloadCooldown) {
                        this.bulletsFired = 0;
                        this.reloadTimer = 0;
                        this.reloadProgress = 0;
                    }
                }

                airplane.setAltitude(this.screenHeight - (int) Math.round(airplaneY));

                if (key.startsWith("UP")) {
                    if (airplane.getFuelAmount() > 0 && airplaneY - (this.scoreLabel.getHeight() * 1.5) > 0) {
                        airplane.translate(new Vector2(0, -FORCE * elapsedTime));
                    }
                }

                if (key.startsWith("DOWN")) {
                    if (airplaneY + airplaneHeight < this.screenHeight) {
                        airplane.translate(new Vector2(0, FORCE * elapsedTime));
                    }
                }

                if (key.endsWith("SPACE") && (this.bulletsFired < this.clipSize)) {
                    Bullet bullet = new Bullet(this.bulletImage, new Vector2(airplaneX + (airplaneWidth - 20), airplaneY - 10 + airplaneHeight / 2));
                    Rectangle bulletShape = new Rectangle(bullet.getWidth(), bullet.getHeight());
                    bullet.addFixture(bulletShape);
                    bullet.setMass(MassType.FIXED_LINEAR_VELOCITY);
                    bullet.translate(bullet.getLocation());
                    this.world.addBody(bullet);
                    this.bulletsFired++;
                }

                Object o = airplane.getUserData();

                if (o != null) {
                    airplane.setUserData(null);

                    if (o instanceof Obstacle) {
                        this.world.removeBody((Obstacle) o);
                        this.lives--;
                        GUIController.playCollisionBird();
                    } else if (o instanceof Kerosine) {
                        Kerosine kerosine = (Kerosine) o;
                        this.world.removeBody(kerosine);
                        airplane.setFuelAmount(airplane.getFuelAmount() + kerosine.getAmount());
                        GUIController.playOilPickUp();
                    }
                }

                if (this.lives <= 0 || airplaneY >= this.screenHeight) {
                    this.world.removeBody(airplane);
                }

                this.fuelLabel.setText("Fuel: " + airplane.getFuelAmount());
                this.altLabel.setText("Alt: " + airplane.getAltitude());
                this.backgroundX -= elapsedTime * (this.zebraForce / 2);

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
     * End the current game and show the game over screen.
     */
    private void gameOver() {
        GUIController.playGameOver();
        GUIController.stopAirplaneSound();
        GUIController.stopGameSound();
        AllCopilotGUI.setPanel("gameover", null, this.score);
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

        if (this.bulletsFired >= this.clipSize) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(50, this.screenHeight - 100, 400, 50);

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(50, this.screenHeight - 100, 4 * this.reloadProgress, 50);

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(10));
            g.drawRect(50, this.screenHeight - 100, 400, 50);

            g.setColor(Color.WHITE);
            g.setFont(this.font);
            g.drawString("Reloading...", 195, this.screenHeight - 55);
        }

        this.scoreLabel.setText("Score: " + this.score);
        this.livesLabel.setText("Lives: " + this.lives);
        this.bulletsLabel.setText("Bullets: " + (this.clipSize - this.bulletsFired));
        this.speedLabel.setText("Speed: " + this.zebraForce);
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
