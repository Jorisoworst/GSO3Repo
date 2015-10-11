/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.util.ArrayList;

/**
 *
 * @author Niels
 */
public class GameAdministration {
    private static GameAdministration instance;

    /**
     * @param aInstance the instance to set
     */
    public static void setInstance(GameAdministration aInstance) {
        instance = aInstance;
    }
    
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private ArrayList<Game> games;
    
    /**
     * Initialize an instance of the GameAdministration singleton
     */
    private GameAdministration() {
        // EMPTY  
    }
    
    /**
     * @return the instance of the singleton
     */
    public static GameAdministration getInstance() {
        if (GameAdministration.instance == null) {
            GameAdministration.instance = new GameAdministration();
        }
        
        return GameAdministration.instance;
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    /**
     * @return the sessions
     */
    public ArrayList<Session> getSessions() {
        return sessions;
    }

    /**
     * @param sessions the sessions to set
     */
    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    /**
     * @return the games
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
    
    /**
     * Method to add a user to the users
     * @param user the user, may not be null
     * @return a boolean whether adding the user went well or not
     */
    public boolean addUser(User user) {
        // TODO
        return false;
    }
    
    /**
     * Method to get a user from the users
     * @param userId the user id, may not be 0 or negative
     * @return a User object
     */
    public User getUser(int userId) {
        // TODO
        return null;
    }
    
    /**
     * Method to get a user from the users
     * @param username the username, may not be null or empty
     * @return a User object
     */
    public User getUser(String username) {
        // TODO
        return null;
    }
    
    /**
     * Method to create a session and add it to the sessions
     * @param host the host, may not be null
     * @return a boolean whether adding the session went well or not
     */
    public boolean createSession(User host) {
        // TODO
        return false;
    }
    
    /**
     * Method to get a session from the sessions
     * @param sessionId the id of the session, may not be 0 or negative
     * @return a Session object
     */
    public Session getSession(int sessionId) {
        // TODO
        return null;
    }
    
    /**
     * Method to create a game and add it to the games
     * @param game the game, may not be null
     * @return a boolean whether adding the game went well or not
     */
    public boolean addGame(Game game) {
        // TODO
        return false;
    }
    
    /**
     * Method to get a game from the games
     * @param gameId the id of the game, may not be 0 or negative
     * @return a Game object
     */
    public Game getGame(int gameId) {
        // TODO
        return null;
    }
    
    /**
     * Method to check a user login
     * @param username the username, may not be null or empty
     * @param password the username, may not be null or empty
     * @return a boolean whether logging in the user went well or not
     */
    public boolean login(String username, String password) {
        // TODO
        return false;
    }
}
