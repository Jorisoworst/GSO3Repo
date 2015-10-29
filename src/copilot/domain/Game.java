/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.util.ArrayList;

/**
 *
 * @author IndyGames
 */
public class Game {
    private boolean isStarted;
    private int score, difficulty, changeInterval;
    private Session session;
    private ArrayList<GameObject> objects;
    
    /**
     * Initialize an instance of the Session class
     * @param session the session, may not be null
     */
    public Game(Session session) {
        this.isStarted = false;
        this.score = 0;
        this.difficulty = 0;
        if(session != null){
        this.session = session;
        }
        else
        {
            System.out.println("Session cannot be null, Session not found. 404");
        }
    }

    /**
     * @return the isStarted
     */
    public boolean isIsStarted() {
        return isStarted;
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
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
       
        this.score = score;
    }

    /**
     * @return the difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty the difficulty to set
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
       
    /**
     * @return the changeInterval
     */
    public int getChangeInterval() {
        return changeInterval;
    }

    /**
     * @param changeInterval the changeInterval to set
     */
    public void setChangeInterval(int changeInterval) {
        this.changeInterval = changeInterval;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }
    
    /**
     * @return the objects
     */
    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    /**
     * @param objects the objects to set
     */
    public void setObjects(ArrayList<GameObject> objects) {
        this.objects = objects;
    }
    
    /**
     * Method to start the game
     * @return a boolean whether starting the game went well or not
     */
    public boolean start() {
        // TODO
        return false;
    }
    
    /**
     * Method to stop the game
     * @return a boolean whether stopping the game went well or not 
     */
    public boolean stop() {
        // TODO
        return false;
    }
    
    /**
     * Method to change the controller role for every player in the game
     * @return a boolean whether changing the roles went well or not
     */
    public boolean changeRoles() {
        // TODO
        return false;
    }
    
    /**
     * Method to generate a part of the level
     * @return a boolean whether generating the level went well or not
     */
    public boolean generateLevel() {
        // TODO
        return false;
    }
}
