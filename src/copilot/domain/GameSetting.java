/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

/**
 *
 * @author IndyGames
 */
public class GameSetting {
    private int maxFuelCapacity, maxUser, requiredExperiencePoints;
    private double levelUp;
    
    public GameSetting()
    {
        
    }

    public int getMaxFuelCapacity() {
        return maxFuelCapacity;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public int getRequiredExperiencePoints() {
        return requiredExperiencePoints;
    }

    public double getLevelUp() {
        return levelUp;
    }

    public void setMaxFuelCapacity(int maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public void setRequiredExperiencePoints(int requiredExperiencePoints) {
        this.requiredExperiencePoints = requiredExperiencePoints;
    }

    public void setLevelUp(double levelUp) {
        this.levelUp = levelUp;
    }
}
