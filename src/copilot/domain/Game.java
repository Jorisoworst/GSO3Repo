package copilot.domain;

import java.util.ArrayList;

/**
 * @author IndyGames
 */
public class Game {

    private ArrayList<GameObject> objects;
    private Session session;
    private boolean isStarted;
    private int score, difficulty, changeInterval;

    /**
     * Initialize an instance of the Session class
     *
     * @param session the session, may not be null
     */
    public Game(Session session) {
        if (session == null) {
            throw new IllegalArgumentException("No session set!");
        }

        this.objects = new ArrayList<>();
        this.session = session;
        this.isStarted = false;
        this.score = 0;
        this.difficulty = 0;
        this.changeInterval = 10;
    }

    /**
     * @return the isStarted
     */
    public boolean isIsStarted() {
        return this.isStarted;
    }

    /**
     * @param isStarted the isStarted to set
     */
    public void setIsStarted(boolean isStarted) {
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
     * @return the objects
     */
    public ArrayList<GameObject> getObjects() {
        return this.objects;
    }

    /**
     * @param objects the objects to set
     */
    public void setObjects(ArrayList<GameObject> objects) {
        if (objects == null
                || objects.isEmpty()) {
            throw new IllegalArgumentException("No objects set!");
        }

        this.objects = objects;
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
}
