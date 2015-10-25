/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.domain;

import javafx.scene.image.Image;

/**
 *
 * @author Niels
 */
public class Propellor extends AirplanePart {
    private int rpm;
    private double fuelConsumption;
    private Airplane airplane;
    /**
     * Initialize an instance of the Propellor class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Propellor(Image image, Airplane airplane, User user) {
        super(image, airplane, user);
        this.airplane = airplane;
        
    }

    /**
     * @return the rpm
     */
    public int getRpm() {
        return rpm;
    }

    /**
     * RPM should be between 0 and 2600
     * @param rpm the rpm to set
     */
    public void setRpm(int rpm) {
        if(rpm > 2600)
        {
            this.rpm = 2600;
        }
        if(rpm < 0)
        {
            this.rpm = 0;
        }
        else
        {
            this.rpm = rpm;    
        }
        double airplanePitch = this.airplane.getPitch();
        double knotsSpeed = this.rpm * airplanePitch * 0.00822894;
        if(knotsSpeed < 0)
        {
            knotsSpeed = 0;
        }
        
        //speed should be adjusted slower for more realism
        int currentSpeed = airplane.getSpeed();
        double increasementSpeed = knotsSpeed - currentSpeed;
        int newAirplaneSpeed = currentSpeed + ((int)increasementSpeed);
        
        if(newAirplaneSpeed < 0)
        {
            newAirplaneSpeed = 0;
        }
        
        airplane.setSpeed(newAirplaneSpeed);
        double fuelConsumption = calculateFuelConsumption(rpm);
        this.fuelConsumption = fuelConsumption;
    }
    
    private double calculateFuelConsumption(double rpm)
    {
        double fuelConsumption = 0;
        fuelConsumption = rpm / 260;
        
        return fuelConsumption;
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
