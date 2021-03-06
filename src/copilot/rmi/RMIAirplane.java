/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Ruud
 */
public class RMIAirplane implements IrmiAirplane {
    
    private int id, speed, fuel, altitude, x , y;
    private double pitch;
    
    public RMIAirplane()
    {
      
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
        
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }
  
      @Override
    public int getId() {
        return id;
    }
    
    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public int getFuelAmount() {
        return fuel;
    }

    @Override
    public int getAltitude() {
        return altitude;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}
