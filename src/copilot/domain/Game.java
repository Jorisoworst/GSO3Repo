package copilot.domain;

import copilot.rmi.ClientService;
import copilot.rmi.HostService;
import copilot.rmi.IClientListener;
import copilot.rmi.IHostListener;
import copilot.rmi.IrmiBullet;
import copilot.rmi.IrmiObstacle;
import copilot.view.panel.GamePanel;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

/**
 * @author IndyGames
 */
public class Game extends World implements IHostListener, IClientListener {

    private HostService hostService;
    private ClientService clientService;
    private Session session;
    private boolean isStarted;
    private int score, difficulty, changeInterval;

    /**
     * Initialize an instance of the Session class
     *
     * @param session the session, may not be null
     * @param hostService the hostService
     * @param clientService the clientService
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public Game(Session session, HostService hostService, ClientService clientService) {
        if (session == null) {
            throw new IllegalArgumentException("No session set!");
        }

        this.setGravity(new Vector2(0.0, 9.81));

        this.session = session;
        this.isStarted = false;
        this.score = 0;
        this.difficulty = 0;
        this.changeInterval = 10;

        if (hostService != null) {
            this.hostService = hostService;
            this.hostService.SetHostListener(this);
            System.out.println("Host started");
//        } else if (clientService != null) { TODO
            this.clientService = clientService;
            this.clientService.AddClientListener(this);
            System.out.println("Client started");
        } else {
            throw new IllegalArgumentException("No service set!");
        }
    }

    /**
     * @return the isStarted
     */
    public boolean isStarted() {
        return this.isStarted;
    }

    /**
     * @param isStarted the isStarted to set
     */
    public void setStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        if (score < 0) {
            this.score = 0;
        } else {
            this.score = score;
        }
    }

    /**
     * @param score the score to set
     */
    public void increaseScore(int score) {
        if (score > 0) {
            this.score += score;
        }
    }

    /**
     * @return the difficulty
     */
    public int getDifficulty() {
        return this.difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(int difficulty) {
        if (difficulty < 0) {
            this.difficulty = 0;
        } else {
            this.difficulty = difficulty;
        }
    }

    /**
     * @return the changeInterval
     */
    public int getChangeInterval() {
        return this.changeInterval;
    }

    /**
     * @param changeInterval the changeInterval to set
     */
    public void setChangeInterval(int changeInterval) {
        if (changeInterval < 0) {
            this.changeInterval = 0;
        } else {
            this.changeInterval = changeInterval;
        }
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return this.session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("No session set!");
        }

        this.session = session;
    }

    /**
     * Method to start the game
     *
     * @return a boolean whether starting the game went well or not
     */
    public boolean start() {
        // TODO
        return false;
    }

    /**
     * Method to stop the game
     *
     * @return a boolean whether stopping the game went well or not
     */
    public boolean stop() {
        // TODO
        return false;
    }

    /**
     * Method to change the controller role for every player in the game
     *
     * @return a boolean whether changing the roles went well or not
     */
    public boolean changeRoles() {
        // TODO
        return false;
    }

    /**
     * Method to generate a part of the level
     *
     * @return a boolean whether generating the level went well or not
     */
    public boolean generateLevel() {
        // TODO
        return false;
    }

    public void fireBullet(IrmiBullet bullet) {
        try {
            this.clientService.FireBullet(bullet);
            System.out.println("CLIENT - fired bullet");
        } catch (RemoteException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //-------------HOST-------------
    @Override
    public void BulletFire(IrmiBullet bullet) {
        this.addBody(new Bullet(GamePanel.bulletImage, new Vector2(bullet.getX(), bullet.getY()))); // TODO

        System.out.println("HOST - " + this.getBodyCount());

        this.hostService.SendBulletToClients(bullet);
    }

    @Override
    public void AirplaneAltitudeKeyPress(boolean upKey) {
    }

    @Override
    public void SpeedChanged(int newRPM) {
    }

    @Override
    public void FuelTupePositionChanged(int oldX, int oldY, int newX, int newY) {
    }

    //-------------CLIENT-------------
    @Override
    public void AirplaneAltitudeChanged(int oldAltitude, int newAltitude) {
    }

    @Override
    public void AirplaneSpeedChanged(int oldSpeed, int newSpeed) {
    }

    @Override
    public void AirplanePitchChanged(double oldPitch, double newPitch) {
    }

    @Override
    public void AirplaneFuelChanged(int oldValue, int newValue) {
    }

    @Override
    public void AirplanePositionUpdate(int oldX, int oldY, int newX, int newY) {
    }

    @Override
    public void BulletAdded(IrmiBullet bullet) {
        Bullet b = new Bullet(GamePanel.bulletImage, new Vector2(bullet.getX(), bullet.getY()));
        Rectangle bulletShape = new Rectangle(b.getWidth(), b.getHeight());
        b.addFixture(bulletShape);
        b.setMass(MassType.FIXED_LINEAR_VELOCITY);
        b.translate(b.getLocation());

        this.addBody(b); // TODO

        System.out.println("CLIENT - " + this.getBodyCount());
    }

    @Override
    public void BulletRemoved(IrmiBullet bullet) {
        Bullet b = new Bullet(GamePanel.bulletImage, new Vector2(bullet.getX(), bullet.getY()));
        Rectangle bulletShape = new Rectangle(b.getWidth(), b.getHeight());
        b.addFixture(bulletShape);
        b.setMass(MassType.FIXED_LINEAR_VELOCITY);
        b.translate(b.getLocation());

        this.removeBody(b); // TODO

        System.out.println("CLIENT - " + this.getBodyCount());
    }

    @Override
    public void ObstacleAdded(IrmiObstacle obstacle) {
    }

    @Override
    public void ObstacleRemoved(IrmiObstacle obstacle) {
    }
}
