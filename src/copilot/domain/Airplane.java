package copilot.domain;

import java.util.ArrayList;
import java.awt.Image;

/**
 * @author IndyGames
 */
public class Airplane extends GameObject {

    private ArrayList<AirplanePart> airplaneParts;
    private final int minimumSpeed;
    private double pitch;
    private int altitude, speed, maxFuelCapacity, fuelAmount;

    /**
     * Initialize an instance of the Airplane class which extends GameObject
     *
     * @param image the image, may not be null
     */
    public Airplane(Image image) {
        super(image);

        this.airplaneParts = new ArrayList();
        this.maxFuelCapacity = 100;
        this.fuelAmount = this.maxFuelCapacity;
        this.minimumSpeed = -40;
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
        if (pitch > 90) {
            this.pitch = 90;
        } else if (pitch < -90) {
            this.pitch = -90;
        } else {
            this.pitch = pitch;
        }
    }

    /**
     * @return the altitude
     */
    public int getAltitude() {
        return this.altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(int altitude) {
        if (altitude < 0) {
            this.altitude = 0;
        } else {
            this.altitude = altitude;
        }
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        if (speed < 0) {
            this.speed = 0;
        } else {
            this.speed = speed;
        }
    }

    /**
     * @return the maxFuelCapacity
     */
    public int getMaxFuelCapacity() {
        return this.maxFuelCapacity;
    }

    /**
     * @param maxFuelCapacity the maxFuelCapacity to set
     */
    public void setMaxFuelCapacity(int maxFuelCapacity) {
        if (maxFuelCapacity < 0) {
            this.maxFuelCapacity = 0;
        } else {
            this.maxFuelCapacity = maxFuelCapacity;
        }
    }

    /**
     * @return the fuelAmount
     */
    public int getFuelAmount() {
        return this.fuelAmount;
    }

    /**
     * @param fuelAmount the fuelAmount to set
     */
    public void setFuelAmount(int fuelAmount) {
        if (fuelAmount <= this.maxFuelCapacity && fuelAmount >= 0) {
            this.fuelAmount = fuelAmount;
        } else if (fuelAmount > this.maxFuelCapacity) {
            this.fuelAmount = this.maxFuelCapacity;
        }
    }

    /**
     * @return the airplaneParts
     */
    public ArrayList<AirplanePart> getAirplaneParts() {
        return this.airplaneParts;
    }

    /**
     * @param airplaneParts the airplaneParts to set
     */
    public void setAirplaneParts(ArrayList<AirplanePart> airplaneParts) {
        if (airplaneParts == null
                || airplaneParts.isEmpty()) {
            throw new IllegalArgumentException("No airplane parts set!");
        }

        this.airplaneParts = airplaneParts;
    }

    /**
     * @return the gun
     */
    public Gun getGun() {
        for (AirplanePart ap : this.airplaneParts) {
            if (ap instanceof Gun) {
                return (Gun) ap;
            }
        }

        return null;
    }

    /**
     * Method to update the airplane and its airplaneParts SPEED: determined by
     * the propeller AIRPLANE PITCH: determined by elevator ALTITUDE: determined
     * and calculated in this method. Speed, pitch and fuel all influances the
     * altitude.
     *
     * @return a boolean whether updating the airplane went well or not
     */
    public boolean updateAirplane() {
        //get the airplane parts
        Elevator elevator = null;
        Propellor propeller = null;

        for (AirplanePart part : this.airplaneParts) {
            if (part instanceof Elevator) {
                elevator = (Elevator) part;
            } else if (part instanceof Propellor) {
                propeller = (Propellor) part;
            }
        }

        if (elevator == null || propeller == null) {
            throw new IllegalStateException("Not all airplane parts are configured!");
        }

        double fuelConsumption = propeller.getFuelConsumption();
        this.fuelAmount -= ((int) Math.round(fuelConsumption));

        //calculate the lift, to determen the vertical speed.
        //100 = squire feet wing span (could be adjusted)
        //0.002308 = air density at 1000f
        //-40 minimum.... speed? lift? idonno
        //THIS CALCULATION IS NOT CORRECT YET...
        double cl = 2 * Math.PI * (this.pitch / 100);
        double lift = 0.5 * 0.002308 * Math.pow(this.speed, 2) * 100 * cl;
        int liftInt = (int) Math.round(lift);
        int verticalSpeed = this.minimumSpeed + liftInt * 2;
        this.altitude = this.altitude + verticalSpeed;

        //if there is no fuel :(
        if (this.fuelAmount < 0) {
            this.fuelAmount = 0;
        } else {
            //reset values
        }

        return true;
    }
}
