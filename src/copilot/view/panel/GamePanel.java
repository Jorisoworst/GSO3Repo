package copilot.view.panel;

import copilot.controller.GUIController;
import copilot.controller.GameController;
import copilot.domain.Airplane;
import copilot.domain.Bullet;
import copilot.domain.GameObject;
import copilot.domain.Kerosine;
import copilot.domain.Obstacle;
import copilot.view.anim.Animation;
import copilot.view.anim.Sprite;
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

/**
 * @author IndyGames
 */
public class GamePanel extends JPanel {

    private static final boolean DEBUG_MODE = false;
    private static final long NANO_TO_BASE = 1000000000;
    private static final int BULLET_FORCE = 25;
    private static final int FORCE = 7;
    private static final int TARGET_FPS = 60;
    private final GameController gameController;
    private final Font font;
    private final int screenWidth, screenHeight;
    private boolean stopped;
    private double elapsedTime, targetInterval, diff, actualInterval;
    private long last, lastTime, time;
    private int zebraForce, fps, lives, score, backgroundX, spawnTimer,
            fpsTimer, fuelTimer, speedTimer, bulletsFired, clipSize,
            reloadTimer, reloadProgress, reloadCooldown, totalKillCount;
    private Canvas canvas;
    private World world;
    private Random rnd;
    private JPanel labelPanel;
    private JLabel scoreLabel, livesLabel, bulletsLabel, altLabel, speedLabel,
            fuelLabel, fpsLabel;
    private Image airplaneImage, backgroundImage, bulletImage,
            kerosineImage, obstacleImage1, obstacleImage2, killSpreeImage,
            killFrenzyImage, runningRiotImage, rampageImage, untouchableImage,
            invincibleImage;

    // Animations
    private BufferedImage explosionImage, bloodImage;
    private Image[] explosionFrames, birdFrames, bloodFrames, killStreakImages;
    private List<Animation> animations;

    // Sounds
    private Clip double_Kill, extermination, killimanjaro, killing_Frenzy,
            killing_Spree, killionaire, killjoy, killpocalypse, killtacular,
            killtastrophe, killtrocity, overkill, perfection, rampage,
            running_Riot, triple_Kill, untouchable;

    /**
     * Initializes an instance of the CopilotGUI
     *
     * @param screenWidth the screenwidth
     * @param screenHeight the screenheight
     * @param font the font
     */
    public GamePanel(int screenWidth, int screenHeight, Font font) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.font = font;
        this.initializeVariables();
        this.loadResources();
        this.placeComponents();
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
        this.totalKillCount = 0;
        this.backgroundX = 0;
        this.spawnTimer = 0;
        this.fpsTimer = 0;
        this.fuelTimer = 0;
        this.speedTimer = 0;
        this.reloadTimer = 0;
        this.reloadProgress = 0;
        this.reloadCooldown = 50;
        this.bulletsFired = 0;
        this.clipSize = 15; // TODO

        // Animations
        this.animations = new ArrayList<>();
    }

    /**
     * Load and initialize all resources
     */
    private void loadResources() {
        try {
            this.airplaneImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/airplane.png"));
            this.airplaneImage = this.airplaneImage.getScaledInstance(103, 60, 1);

            this.backgroundImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/background.png"));
            this.bulletImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/bullet.png"));

            this.obstacleImage1 = ImageIO.read(this.getClass().getClassLoader().getResource("images/goose_wing_down.png"));
            this.obstacleImage1 = this.obstacleImage1.getScaledInstance(103, 60, 1);

            this.obstacleImage2 = ImageIO.read(this.getClass().getClassLoader().getResource("images/goose_wing_up.png"));
            this.obstacleImage2 = this.obstacleImage2.getScaledInstance(103, 60, 1);

            this.kerosineImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/fuel.png"));
            this.kerosineImage = this.kerosineImage.getScaledInstance(80, 80, 1);

            this.explosionImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/spritesheets/explosion-sprite.png"));
            this.bloodImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/spritesheets/blood.png"));

            this.killSpreeImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/medals/03.png"));
            this.killSpreeImage = this.killSpreeImage.getScaledInstance(100, 100, 1);

            this.killFrenzyImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/medals/04.png"));
            this.killFrenzyImage = this.killFrenzyImage.getScaledInstance(100, 100, 1);

            this.runningRiotImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/medals/05.png"));
            this.runningRiotImage = this.runningRiotImage.getScaledInstance(100, 100, 1);

            this.rampageImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/medals/06.png"));
            this.rampageImage = this.rampageImage.getScaledInstance(100, 100, 1);

            this.untouchableImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/medals/07.png"));
            this.untouchableImage = this.untouchableImage.getScaledInstance(100, 100, 1);

            this.invincibleImage = ImageIO.read(this.getClass().getClassLoader().getResource("images/medals/08.png"));
            this.invincibleImage = this.invincibleImage.getScaledInstance(100, 100, 1);

            this.explosionFrames = this.setupAnimation(this.explosionImage, 96);
            this.bloodFrames = this.setupAnimation(this.bloodImage, 512);

            this.birdFrames = new Image[]{
                this.obstacleImage1,
                this.obstacleImage2
            };

            this.killStreakImages = new Image[]{
                this.killSpreeImage,
                this.killFrenzyImage,
                this.runningRiotImage,
                this.rampageImage,
                this.untouchableImage,
                this.invincibleImage
            };

            // Sounds
            this.killing_Spree = AudioSystem.getClip();
            this.killing_Spree.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/medals/Killing_Spree!.wav")));

            this.killing_Frenzy = AudioSystem.getClip();
            this.killing_Frenzy.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/medals/Killing_Frenzy!.wav")));

            this.running_Riot = AudioSystem.getClip();
            this.running_Riot.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/medals/Running_Riot!.wav")));

            this.rampage = AudioSystem.getClip();
            this.rampage.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/medals/Rampage!.wav")));

            this.untouchable = AudioSystem.getClip();
            this.untouchable.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/medals/Untouchable!.wav")));

            this.perfection = AudioSystem.getClip();
            this.perfection.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/medals/Perfection!.wav")));

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Setup all the possible animations.
     */
    private Image[] setupAnimation(BufferedImage image, int tileSize) {
        Sprite spriteSheet = new Sprite(image, tileSize);

        int frameCounter = 0;
        int horizontalFrames = image.getWidth() / tileSize;
        int verticalFrames = image.getHeight() / tileSize;

        Image[] animationFrames = new Image[horizontalFrames * verticalFrames];

        for (int i = 0; i < verticalFrames; i++) {
            for (int j = 0; j < horizontalFrames; j++) {
                if (tileSize > 96) {
                    animationFrames[frameCounter] = spriteSheet.getSprite(j, i).getScaledInstance(96, 96, 0);
                } else {
                    animationFrames[frameCounter] = spriteSheet.getSprite(j, i);
                }

                frameCounter++;
            }
        }

        return animationFrames;
    }

    /**
     * Place the gui components.
     */
    private void placeComponents() {
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

        // Set the Font, TextColor and the TextAlignment for each JLabel on the labelPanel
        for (Component comp : this.labelPanel.getComponents()) {
            JLabel lbl = (JLabel) comp;
            lbl.setFont(this.font);
            lbl.setForeground(Color.WHITE);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
        }

        this.add(this.labelPanel, BorderLayout.PAGE_START);

        // Create the size of the canvas
        Dimension size = new Dimension(this.screenWidth, this.screenHeight);

        // Create a canvas to paint to 
        this.canvas = new Canvas();
        this.canvas.setPreferredSize(size);
        this.canvas.setMinimumSize(size);
        this.canvas.setMaximumSize(size);

        // Add the Canvas to the JFrame (this)
        this.add(this.canvas, BorderLayout.PAGE_END);
    }

    /**
     * Creates game objects and adds them to the world.
     */
    private void initializeWorld() {
        // Create the world
        this.world = new World();

        // Set the gravity to positive
        // since (0, 0) is the top left instead of bottom left
        this.world.setGravity(new Vector2(0.0, 9.81));

        // Add the game controller as the collision listener
        this.world.addListener(this.gameController);

        // Create the Airplane GameObject and set it's default values
        // such as Mass, Translation (position) and it's Fixture (hit detection box)
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

        // Initialize the last update time
        this.last = System.nanoTime();
        // Don't allow AWT to paint the canvas since we are
        this.canvas.setIgnoreRepaint(true);
        // Enable double buffering (the JFrame has to be
        // visible before this can be done)
        this.canvas.createBufferStrategy(2);

        // Run a separate thread to do active rendering
        // because we don't want to do it on the EDT
        Thread thread = new Thread() {
            @Override
            public void run() {
                // Perform an infinite loop for the game loop
                // and calculate the current fps
                while (!isStopped()) {
                    lastTime = System.nanoTime();
                    gameLoop();
                    fps = (int) Math.round(NANO_TO_BASE / (System.nanoTime() - lastTime));
                    lastTime = System.nanoTime();
                }
            }
        };

        // Set the game loop thread to a daemon thread so that
        // it cannot stop the JVM from exiting
        thread.setDaemon(true);
        // Start the game loop
        thread.start();
    }

    /**
     * The method calling the necessary methods to update the game, graphics,
     * and poll for input.
     */
    protected void gameLoop() {
        // Get the current time
        this.time = System.nanoTime();

        // Get the elapsed time from the last iteration
        this.diff = (double) this.time - (double) this.last;

        // Set the last time
        this.last = this.time;
        this.elapsedTime += this.diff;
        this.targetInterval = NANO_TO_BASE / TARGET_FPS;
        this.actualInterval = this.elapsedTime / this.targetInterval;

        // Update the world with the actual interval
        if (this.elapsedTime >= this.targetInterval) {
            this.world.update(this.actualInterval);
            this.update(this.actualInterval);
            this.elapsedTime = 0;
        }

        // Blit/flip the buffer
        BufferStrategy strategy = this.canvas.getBufferStrategy();

        // Get the graphics object to render to
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        // Render anything about the game (will render the World objects)
        this.render(g);

        // Dispose of the graphics object
        g.dispose();

        if (!strategy.contentsLost()) {
            strategy.show();
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Update the game and it's gameobjects.
     *
     * @param elapsedTime the total elapsed time since the last frame.
     */
    protected void update(double elapsedTime) {
        // Animations
        for (int i = 0; i < this.animations.size(); i++) {
            Animation a = this.animations.get(i);

            if (!a.isStopped()) {
                a.update();
            } else {
                this.animations.remove(a);
            }
        }

        // Get the last key presses done by the user
        String key = this.gameController.getKeyPressed();

        // If the user hits escape, the game will stop
        if (key.equals("ESCAPE") || key.equals("ESCAPE_RELEASED")) {
            this.gameController.setKeyPressed("NONE");
            this.stop();
            AllCopilotGUI.setPanel("menu", null, null);
        }

        // Increase the spawn timer with the elapsed time and the zebra force
        // to make the game more difficult the further you get
        this.spawnTimer += (elapsedTime * this.zebraForce) / 2;

        // If the spawn timer reaches 150 or more, spawn a random GameObject
        // (10% chance for Kerosine, 90% chance for Obstacle)
        if (this.spawnTimer >= 150) {
            if ((rnd.nextInt(10) + 1) % 10 == 0) {
                spawnObject("P");
            } else {
                spawnObject("O");
            }

            // Reset the spawn timer
            this.spawnTimer = 0;
        }

        // Update every Body (GameObject) in the World
        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);

            // If the Body is an Obstacle or a Kerosine object,
            // move them left with the speed of zebra force multiplied by
            // the elapsed time
            if (go instanceof Obstacle || go instanceof Kerosine) {

                // Check if the GameObject is within bounds
                if (go.getTransform().getTranslationX() + go.getWidth() < 0) {
                    this.world.removeBody(go);
                } else {
                    go.translate(new Vector2(-this.zebraForce * elapsedTime, 0));
                }

                if (go instanceof Obstacle) {
                    Obstacle obstacle = (Obstacle) go;

                    // Animations
                    Animation bird = obstacle.getAnimation();
                    bird.setX((int) go.getTransform().getTranslationX());
                    bird.setY((int) go.getTransform().getTranslationY());
                }
            } else if (go instanceof Bullet) {
                // If the Body is a Bullet object, move them left with the speed
                // of bullet force, multiplied by the elapsed time
                Bullet bullet = (Bullet) go;

                // Check if the Bullet is within bounds
                if (bullet.getTransform().getTranslationX() - bullet.getWidth() > this.screenWidth) {
                    this.world.removeBody(bullet);
                } else {
                    bullet.translate(new Vector2(BULLET_FORCE * elapsedTime, 0));

                    // Get the User Data from the Bullet (for collision detection)
                    Object o = bullet.getUserData();

                    // If the Bullet collided with an Obstacle,
                    // remove them both from the World
                    if (o != null) {
                        if (o instanceof Obstacle) {
                            Obstacle obstacle = (Obstacle) o;

                            // Animations
                            obstacle.getAnimation().stop();

                            Animation blood = new Animation(this.bloodFrames, 3);
                            blood.setLooping(false);
                            blood.setX((int) obstacle.getTransform().getTranslationX());
                            blood.setY((int) obstacle.getTransform().getTranslationY());
                            blood.start();

                            this.animations.add(blood);

                            this.world.removeBody(bullet);
                            this.world.removeBody(obstacle);
                            this.score++;
                            this.totalKillCount++;
                            GUIController.playCollisionBullet();
                        }
                    }
                }
            } else if (go instanceof Airplane) {
                // If the Body is an Airplane object, get the Translation
                // (position) and the Width and Height
                Airplane airplane = (Airplane) go;
                double airplaneWidth = airplane.getWidth();
                double airplaneHeight = airplane.getHeight();

                Transform airplaneTransform = airplane.getTransform();
                double airplaneX = airplaneTransform.getTranslationX();
                double airplaneY = airplaneTransform.getTranslationY();

                // Increase the fuel timer with the elapsed time
                this.fuelTimer += elapsedTime;

                // If the fuel timer reaches 25 or more,
                // lower the fuel amount of the Airplane
                if (this.fuelTimer >= 25) {
                    airplane.setFuelAmount(airplane.getFuelAmount() - 1);

                    // Reset the fuel timer
                    this.fuelTimer = 0;
                }

                // Increase the speed timer with the elapsed time
                this.speedTimer += elapsedTime;

                // If the speed timer reaches 500 or more,
                // increase the zebra force to make the game go faster
                // and increase the score with the elapsed time mulitplied by
                // the zebra force divided by 7
                if (this.speedTimer >= 500) {
                    this.zebraForce++;
                    this.score += elapsedTime * (this.zebraForce / 7);

                    // Reset the speed timer
                    this.speedTimer = 0;
                }

                // Increase the fps timer with the elapsed time
                this.fpsTimer += elapsedTime;

                // If the fps timer reaches 50 or more,
                // update the fps label
                if (this.fpsTimer >= 50) {
                    this.fpsLabel.setText("FPS: " + this.fps);

                    // Reset the fps timer
                    this.fpsTimer = 0;
                }

                // If the amount of bullets fired is equal to or more than
                // the clip size, the reload timer starts and increases
                // with the elapsed time
                if (this.bulletsFired >= this.clipSize) {
                    this.reloadTimer += elapsedTime;

                    // Calculate the reloading progress
                    this.reloadProgress = 100 / this.reloadCooldown * this.reloadTimer;

                    // If the reloading progress is greather than or equal to
                    // the reload cooldown, reset all the corresponding variables
                    if (this.reloadTimer >= this.reloadCooldown) {
                        this.bulletsFired = 0;
                        this.reloadTimer = 0;
                        this.reloadProgress = 0;
                    }
                }

                // Update the altitude for the Airplane
                // (the screen height - the y coordinate of the Airplane)
                airplane.setAltitude(this.screenHeight - (int) Math.round(airplaneY));

                // If the user pressed up (W) on the keyboard,
                // move the Airplane up as long as the Airplane is within bounds
                if (key.startsWith("UP")) {
                    if (airplane.getFuelAmount() > 0 && airplaneY - (this.scoreLabel.getHeight() * 1.5) > 0) {
                        airplane.translate(new Vector2(0, -FORCE * elapsedTime));
                    }
                }

                // If the user pressed down (S) on the keyboard,
                // move the Airplane down as long as the Airplane is within bounds
                if (key.startsWith("DOWN")) {
                    if (airplaneY + airplaneHeight < this.screenHeight) {
                        airplane.translate(new Vector2(0, FORCE * elapsedTime));
                    }
                }

                // If the user pressed spacebar on the keyboard and the player
                // has ammo left, create a new Bullet object and set it's default values
                // such as Mass, Translation (position) and it's Fixture (hit detection box)
                if (key.endsWith("SPACE") && (this.bulletsFired < this.clipSize)) {
                    Bullet bullet = new Bullet(this.bulletImage, new Vector2(airplaneX + (airplaneWidth - 20), airplaneY - 10 + airplaneHeight / 2));
                    Rectangle bulletShape = new Rectangle(bullet.getWidth(), bullet.getHeight());
                    bullet.addFixture(bulletShape);
                    bullet.setMass(MassType.FIXED_LINEAR_VELOCITY);
                    bullet.translate(bullet.getLocation());

                    // Add the Bullet to the World
                    this.world.addBody(bullet);

                    // Increase the amount of Bullets fired
                    this.bulletsFired++;
                }

                // Get the User Data from the Airplane (for collision detection)
                Object o = airplane.getUserData();

                if (o != null) {
                    airplane.setUserData(null);

                    if (o instanceof Obstacle) {
                        // If the Airplane collided with an Obstacle, lower the amount
                        // of lives, remove the Obstacle from the World and reset the User Data
                        // of the Airplane
                        Obstacle obstacle = (Obstacle) o;

                        // Animations
                        obstacle.getAnimation().stop();

                        Animation explosion = new Animation(this.explosionFrames, 1);
                        explosion.setLooping(false);
                        explosion.setX((int) obstacle.getTransform().getTranslationX());
                        explosion.setY((int) obstacle.getTransform().getTranslationY());
                        explosion.start();

                        this.animations.add(explosion);

                        this.world.removeBody(obstacle);
                        this.lives--;
                        this.totalKillCount = 0;
                        GUIController.playCollisionBird();
                    } else if (o instanceof Kerosine) {
                        // If the Airplane collided with a Kerosine, increase the fuel
                        // amount, remove the Kerosine from the World and reset the User Data
                        // of the Airplane
                        Kerosine kerosine = (Kerosine) o;
                        this.world.removeBody(kerosine);
                        airplane.setFuelAmount(airplane.getFuelAmount() + kerosine.getAmount());
                        GUIController.playOilPickUp();
                    }
                }

                // Check if the Airplane crashed or if the player is out of lives
                // and remove the Airplane from the World
                if (this.lives <= 0 || airplaneY >= this.screenHeight) {
                    this.world.removeBody(airplane);
                }

                // Update the JLabels with the corresponding text
                this.fuelLabel.setText("Fuel: " + airplane.getFuelAmount());
                this.altLabel.setText("Alt: " + airplane.getAltitude());

                // Move the background to the left (a bit less than the rest)
                this.backgroundX -= elapsedTime * (this.zebraForce / 2);

                // If the World doesn't have an Airplane object, the player
                // is game over
                if (!this.world.containsBody(airplane)) {
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
        // Create an empty GameObject
        GameObject go = null;

        // Check whether the GameObject has to be an Obstacle or Kerosine
        switch (type) {
            case "O": {
                go = new Obstacle(this.obstacleImage1);
                Obstacle obstacle = (Obstacle) go;

                // Animations
                Animation bird = new Animation(this.birdFrames, 5);
                bird.setLooping(true);
                bird.setX((int) obstacle.getTransform().getTranslationX());
                bird.setY((int) obstacle.getTransform().getTranslationY());
                bird.start();

                obstacle.setAnimation(bird);
                this.animations.add(bird);
                break;
            }
            case "P": {
                go = new Kerosine(this.kerosineImage, this.rnd.nextInt(100) + 1);
                break;
            }
        }

        // Spawn the GameObject on a random location within the bounds
        if (go != null) {
            int randomY = this.rnd.nextInt(this.screenHeight - this.scoreLabel.getHeight() - (int) go.getHeight());

            if (randomY > this.screenHeight - go.getHeight()) {
                randomY = this.screenHeight - (int) go.getHeight();
            } else if (randomY < this.scoreLabel.getHeight()) {
                randomY = this.scoreLabel.getHeight();
            }

            // Create the Fixture (hit detection box) for the GameObject
            // and set the Mass and Translation (location)
            Rectangle objShape = new Rectangle(go.getWidth(), go.getHeight());
            go.addFixture(objShape);
            go.setMass(MassType.FIXED_LINEAR_VELOCITY);
            go.translate(
                    this.rnd.nextInt(this.screenWidth / 2) + this.screenWidth,
                    randomY
            );

            // Add it to the World
            this.world.addBody(go);
        }
    }

    /**
     * End the current game and show the game over screen.
     */
    private void gameOver() {
        this.stop();
        GUIController.playGameOver();
        AllCopilotGUI.setPanel("gameover", null, this.score);
    }

    /**
     * Renders the gameobjects.
     *
     * @param g the graphics object to render to
     */
    protected void render(Graphics2D g) {
        // Draw over everything with the background
        g.drawImage(this.backgroundImage, this.backgroundX, 0, null);

        // Draw a second background to the right of the previous one
        if (this.backgroundX <= 0) {
            g.drawImage(this.backgroundImage, this.backgroundX + this.backgroundImage.getWidth(null), 0, null);

            // If the previous background is out of bounds,
            // draw a new one to the right of the second one
            if (this.backgroundX <= -this.backgroundImage.getWidth(null)) {
                this.backgroundX = 0;
            }
        }

        // Draw every Body (GameObject) in the World
        for (int i = 0; i < this.world.getBodyCount(); i++) {
            GameObject go = (GameObject) this.world.getBody(i);

            if (!(go instanceof Obstacle)) {
                go.render(g);
            }

            // Draw the hit boxes in Debug Mode
            if (DEBUG_MODE) {
                Vector2 gameObjectLocation = go.getTransform().getTranslation();
                int gameObjectWidth = go.getWidth();
                int gameObjectHeight = go.getHeight();
                Double gameObjectX = gameObjectLocation.x;
                Double gameObjectY = gameObjectLocation.y;
                g.setColor(Color.MAGENTA);
                g.fillRect(gameObjectX.intValue(), gameObjectY.intValue(), gameObjectWidth, gameObjectHeight);
            }
        }

        // Update all the JLabels with the corresponding text
        this.scoreLabel.setText("Score: " + this.score);
        this.livesLabel.setText("Lives: " + this.lives);
        this.bulletsLabel.setText("Bullets: " + (this.clipSize - this.bulletsFired));
        this.speedLabel.setText("Speed: " + this.zebraForce);

        // Animations
        for (Animation a : this.animations) {
            if (!a.isStopped()) {
                g.drawImage(a.getSprite(), a.getX(), a.getY(), null);
            }
        }

        // Draw the reloading progress bar
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

        Image medal = null;

        switch (this.totalKillCount / 5) {
            case 1: {
                medal = this.killSpreeImage;
                this.killing_Spree.start();
                break;
            }
            case 2: {
                medal = this.killFrenzyImage;
                this.killing_Frenzy.start();
                break;
            }
            case 3: {
                medal = this.runningRiotImage;
                this.running_Riot.start();
                break;
            }
            case 4: {
                medal = this.rampageImage;
                this.rampage.start();
                break;
            }
            case 5: {
                medal = this.untouchableImage;
                this.untouchable.start();
                break;
            }
            case 6: {
                medal = this.invincibleImage;
                this.perfection.start();
                break;
            }
        }

        if (medal != null) {
            g.drawImage(medal, this.screenWidth - 150, this.screenHeight - 150, null);
        }
    }

    /**
     * Stops the game.
     */
    public synchronized void stop() {
        this.stopped = true;
        GUIController.stopAirplaneSound();
        GUIController.stopGameSound();
        GUIController.playBackgroundMusic();
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
