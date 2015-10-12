/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

/**
 *
 * @author Niels
 */
public class Propellor extends AirplanePart {
    private int rpm;
    private double fuelConsumption;
    
    /**
     * Initialize an instance of the Propellor class which extends ArplanePart
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Propellor(Airplane airplane, User user) {
        super(airplane, user);
    }

    /**
     * @return the rpm
     */
    public int getRpm() {
        return rpm;
    }

    /**
     * @param rpm the rpm to set
     */
    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    /**
     * @return the fuelConsumption
     */
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * @param fuelConsumption the fuelConsumption to set
     */
    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }
    
}
