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
public class Session {    

    /**
     * @return the maxUsers
     */
    public static int getMaxUsers() {
        return maxUsers;
    }

    /**
     * @param aMaxUsers the maxUsers to set
     */
    public static void setMaxUsers(int aMaxUsers) {
        maxUsers = aMaxUsers;
    }
    private ArrayList<User> users;
    private boolean isStarted;
    private static int maxUsers;
    private User host;
     
    /**
     * Initialize an instance of the Session class
     * @param host the host, may not be null 
     */
    public Session(User host) {
        this.users = new ArrayList<>();
        this.isStarted = false;
        this.host = host;
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
     * @param user the user to set
     */
    public void addUser(User user) {
        this.users.add(user);
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
     * @return the host
     */
    public User getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(User host) {
        this.host = host;
    }
}
