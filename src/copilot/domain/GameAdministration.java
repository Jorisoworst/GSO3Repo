package copilot.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author IndyGames
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
        if (aInstance == null) {
            throw new IllegalArgumentException("No game administration set!");
        }

        GameAdministration.instance = aInstance;
    }

    private DatabaseAdministration dbAdmin;
    private ArrayList<User> users;
    private ArrayList<Session> sessions;
    private ArrayList<Game> games;

    /**
     * Initialize an instance of the GameAdministration singleton
     */
    private GameAdministration() throws NullPointerException {
        try {
            this.dbAdmin = new DatabaseAdministration();
        } catch (Exception ex) {
            // do nothing
        }

        if (this.dbAdmin != null) {
            try {
                this.users = this.dbAdmin.GetUsers();
            } catch (IOException ex) {
                Logger.getLogger(GameAdministration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            this.users = new ArrayList<>();
        }

        // add to database
        this.sessions = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public boolean getDatabaseState() {
        return this.dbAdmin != null;
    }

    /**
     * @return the users
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(ArrayList<User> users) {
        if (users == null
                || users.isEmpty()) {
            throw new IllegalArgumentException("No users set!");
        }

        this.users = users;
    }

    /**
     * @return the sessions
     */
    public ArrayList<Session> getSessions() {
        return this.sessions;
    }

    /**
     * @param sessions the sessions to set
     */
    public void setSessions(ArrayList<Session> sessions) {
        if (sessions == null
                || sessions.isEmpty()) {
            throw new IllegalArgumentException("No sessions set!");
        }

        this.sessions = sessions;
    }

    /**
     * @return the games
     */
    public ArrayList<Game> getGames() {
        return this.games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(ArrayList<Game> games) {
        if (games == null
                || games.isEmpty()) {
            throw new IllegalArgumentException("No games set!");
        }

        this.games = games;
    }

    /**
     * Method to add a user to the users
     *
     * @param user the user, may not be null
     * @return a boolean whether adding the user went well or not
     */
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }

        this.users.add(user);

        try {
            this.dbAdmin.AddUser(user);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    /**
     * Method to get a user from the users
     *
     * @param userId the user id, may not be negative
     * @return a User object
     */
    public User getUser(int userId) {
        if (userId < 0) {
            return null;
        }

        if (this.dbAdmin != null) {
            try {
                this.users = this.dbAdmin.GetUsers();
            } catch (IOException ex) {
                return null;
            }

            for (User user : this.users) {
                if (user.getId() == userId) {
                    return user;
                }
            }
        }

        return null;
    }

    /**
     * Method to get a user from the users
     *
     * @param username the username, may not be null or empty
     * @return a User object
     */
    public User getUser(String username) {
        if (username == null || username.equals("")) {
            return null;
        }

        if (this.dbAdmin != null) {
            try {
                this.users = this.dbAdmin.GetUsers();
            } catch (IOException ex) {
                return null;
            }
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
     *
     * @param host the host, may not be null
     * @return a boolean whether adding the session went well or not
     */
    public Session createSession(User host) {
        // TODO
        if (host == null) {
            return null;
        }

        Session session = new Session(host);
        this.sessions.add(session);

        return session;
    }

    /**
     * Method to get a session from the sessions
     *
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
     *
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
     *
     * @param hostId the id of the host who hosts the session where the game
     * started, may not be negative
     * @return a Game object
     */
    public Game getGame(int hostId) {
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
     *
     * @param username the username, may not be null or empty
     * @param password the username, may not be null or empty
     * @return a boolean whether logging in the user went well or not
     */
    public boolean login(String username, String password) {
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return false;
        }

        if (this.dbAdmin != null) {
            try {
                this.users = this.dbAdmin.GetUsers();
            } catch (IOException ex) {
                return false;
            }

            if (this.users != null) {
                if (this.users.stream().anyMatch((user) -> (user.getUsername().equals(username) && user.getPassword().equals(password)))) {
                    return true;
                }
            }
        }

        return false;
    }
}
