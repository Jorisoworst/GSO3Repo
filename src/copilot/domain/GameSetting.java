package copilot.domain;

/**
 * @author IndyGames
 */
public class GameSetting {
    private int maxFuelCapacity, maxUser, requiredExperiencePoints;
    private double levelUp;
    
    /**
     * Initializes an instance of the GameSetting class
     */
    public GameSetting()
    {
      // EMPTY  
    }

    /**
     * get the max fuel capacity
     * @return the maxFuelCapacity
     */
    public int getMaxFuelCapacity() {
        return maxFuelCapacity;
    }

    /**
     * get the max user number
     * @return the maxUser
     */
    public int getMaxUser() {
        return maxUser;
    }

    /**
     * get the required experience points to level up
     * @return the requiredExperiencePoints
     */
    public int getRequiredExperiencePoints() {
        return requiredExperiencePoints;
    }

    /**
     * get the level up
     * @return the levelUp
     */
    public double getLevelUp() {
        return levelUp;
    }

    /**
     * set the max fuel capacity
     * @param maxFuelCapacity the maxFuelCapacity to set 
     */
    public void setMaxFuelCapacity(int maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }

    /**
     * set the max users
     * @param maxUser the maxUser to set
     */
    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    /**
     * set the required experience points to level up
     * @param requiredExperiencePoints the requiredExperiencePoints to set
     */
    public void setRequiredExperiencePoints(int requiredExperiencePoints) {
        this.requiredExperiencePoints = requiredExperiencePoints;
    }

    /**
     * set the level up
     * @param levelUp the levelUp to set
     */
    public void setLevelUp(double levelUp) {
        this.levelUp = levelUp;
    }
}
