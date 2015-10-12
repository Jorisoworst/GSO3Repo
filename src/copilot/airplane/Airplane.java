/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.airplane;

import copilot.controller.GameObject;
import java.util.ArrayList;

/**
 *
 * @author Niels
 */
public class Airplane extends GameObject {
    private double pitch;
    private int altitude;
    private int speed;
    private int maxFuelCapacity;
    private int fuelAmount;
    private ArrayList<AirplanePart> airplaneParts;
    
    /**
     * Initialize an instance of the Airplane class which extends GameObject
     */
    public Airplane() {
    }

    /**
     * @return the pitch
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * @param pitch the pitch to set
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * @return the altitude
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the maxFuelCapacity
     */
    public int getMaxFuelCapacity() {
        return maxFuelCapacity;
    }

    /**
     * @param maxFuelCapacity the maxFuelCapacity to set
     */
    public void setMaxFuelCapacity(int maxFuelCapacity) {
        this.maxFuelCapacity = maxFuelCapacity;
    }

    /**
     * @return the fuelAmount
     */
    public int getFuelAmount() {
        return fuelAmount;
    }

    /**
     * @param fuelAmount the fuelAmount to set
     */
    public void setFuelAmount(int fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    /**
     * @return the airplaneParts
     */
    public ArrayList<AirplanePart> getAirplaneParts() {
        return airplaneParts;
    }

    /**
     * @param airplaneParts the airplaneParts to set
     */
    public void setAirplaneParts(ArrayList<AirplanePart> airplaneParts) {
        this.airplaneParts = airplaneParts;
    }
    
	 /**
     * Method to update the airplane and its airplaneParts
     * SPEED: determined by the propeller
     * AIRPLANE PITCH: determined by elevator
     * ALTITUDE: determined and calculated in this method.
     * Speed, pitch and fuel all influances the altitude.
     * @return a boolean whether updating the airplane went well or not
     */
    public boolean updateAirplane() {
        // TODO
        //get the airplane parts
//        Elevator elevator = null;
//        Propellor propeller = null;
//        for(AirplanePart part : airplaneParts)
//        {            
//            if(part instanceof Elevator)
//            {
//                elevator = (Elevator) part;
//            }
//            if(part instanceof Propellor)
//            {
//                propeller = (Propellor) part;
//            }
//        }
//        
//        if(elevator != null && propeller != null)
//        {
//            
//        }
        
        //calculate the lift, to determen the vertical speed.
        //100 = squire feet wing span (could be adjusted)
        //0.002308 = air density at 1000f
        //THIS CALCULATION IS NOT CORRECT YET...
        double cl = 2 * Math.PI * (this.pitch/100);
        double lift = 0.5 * 0.002308 * Math.exp(speed) * 100 * cl;
        int liftInt = (int) Math.round(lift);
        this.altitude = this.altitude + liftInt;
        return false;
    }
}
