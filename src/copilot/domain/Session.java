package copilot.domain;

import java.util.ArrayList;

/**
 * @author IndyGames
 */
public class Session {

    /**
     * @return the maxUsers
     */
    public static int getMaxUsers() {
        return Session.maxUsers;
    }

    /**
     * @param aMaxUsers the maxUsers to set
     */
    public static void setMaxUsers(int aMaxUsers) {
        Session.maxUsers = aMaxUsers;
    }

    private ArrayList<User> users;
    private User host;
    private static int maxUsers;
    private boolean isStarted;

    /**
     * Initialize an instance of the Session class
     *
     * @param host the host, may not be null
     */
    public Session(User host) {
        if (host == null) {
            throw new IllegalArgumentException("No host set!");
        }

        this.users = new ArrayList<>();
        this.isStarted = false;
        this.host = host;
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
     * @param user the user to set
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("No user set!");
        }

        this.users.add(user);
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
     * @return the host
     */
    public User getHost() {
        return this.host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(User host) {
        if (host == null) {
            throw new IllegalArgumentException("No host set!");
        }

        this.host = host;
    }
}
