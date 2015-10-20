/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import java.util.ArrayList;

/**
 *
 * @author Niels
 */
public class GameAdministration {
    private static GameAdministration instance;  

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
     * @param aInstance the instance to set
     */
    public static void setInstance(GameAdministration aInstance) {
        instance = aInstance;
    }
    
    private int requiredExperiencePoints;
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private ArrayList<Game> games;
    
    /**
     * Initialize an instance of the GameAdministration singleton
     */
    private GameAdministration() {
        this.users = new ArrayList<>();
        this.sessions = new ArrayList<>();
        this.games = new ArrayList<>();
    }
      
    /**
     * @return the requiredExperiencePoints
     */
    public int getRequiredExperiencePoints() {
        return requiredExperiencePoints;
    }

    /**
     * @param requiredExperiencePoints the requiredExperiencePoints to set
     */
    public void setRequiredExperiencePoints(int requiredExperiencePoints) {
        this.requiredExperiencePoints = requiredExperiencePoints;
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
        if (user == null) {
            return false;
        }
        
        this.users.add(user);
        return true;
    }
    
    /**
     * Method to get a user from the users
     * @param userId the user id, may not be negative
     * @return a User object
     */
    public User getUser(int userId) {
        // TODO
        if (userId < 0) {
            return null;
        }
        
        for (User user : this.users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Method to get a user from the users
     * @param username the username, may not be null or empty
     * @return a User object
     */
    public User getUser(String username) {
        // TODO
        if (username == null || username.equals("")) {
            return null;
        }
        
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Method to create a session and add it to the sessions
     * @param host the host, may not be null
     * @return a boolean whether adding the session went well or not
     */
    public boolean createSession(User host) {
        // TODO
        if (host == null) {
            return false;
        }
        
        Session session = new Session(host);      
        this.sessions.add(session);        
        return true;
    }
    
    /**
     * Method to get a session from the sessions
     * @param hostId the id of the host of a session, may not be negative
     * @return a Session object
     */
    public Session getSession(int hostId) {
        // TODO
        if (hostId < 0) {
            return null;
        }
        
        for (Session session : this.sessions) {
            if (session.getHost().getId() == hostId) {
                return session;
            }
        }
        return null;
    }
    
    /**
     * Method to create a game and add it to the games
     * @param game the game, may not be null
     * @return a boolean whether adding the game went well or not
     */
    public boolean addGame(Game game) {
        // TODO
        if (game == null) {
            return false;
        }
        
        this.games.add(game);
        return true;
    }
    
    /**
     * Method to get a game from the games
     * @param hostId the id of the host who hosts the session where the game started, may not be negative
     * @return a Game object
     */
    public Game getGame(int hostId) {
        // TODO
        if (hostId < 0) {
            return null;
        }
        
        for (Game game : this.games) {
            if (game.getSession().getHost().getId() == hostId) {
                return game;
            }
        }
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
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return false;
        }
        
        for (User user : this.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        
        return false;
    }
}
