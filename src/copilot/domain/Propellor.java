package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
 */
public class Propellor extends AirplanePart {

    private double fuelConsumption;
    private int rpm;

    /**
     * Initialize an instance of the Propellor class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Propellor(Image image, Airplane airplane, User user) {
        super(image, airplane, user);
    }

    /**
     * @return the rpm
     */
    public int getRpm() {
        return this.rpm;
    }

    /**
     * RPM should be between 0 and 2600
     *
     * @param rpm the rpm to set
     */
    public void setRpm(int rpm) {
        if (rpm > 2600) {
            this.rpm = 2600;
        }

        if (rpm < 0) {
            this.rpm = 0;
        } else {
            this.rpm = rpm;
        }

        double airplanePitch = this.getAirplane().getPitch();
        double knotsSpeed = this.rpm * airplanePitch * 0.00822894;

        if (knotsSpeed < 0) {
            knotsSpeed = 0;
        }

        //speed should be adjusted slower for more realism
        int currentSpeed = this.getAirplane().getSpeed();
        double increasementSpeed = knotsSpeed - currentSpeed;
        int newAirplaneSpeed = currentSpeed + ((int) increasementSpeed);

        if (newAirplaneSpeed < 0) {
            newAirplaneSpeed = 0;
        }

        this.getAirplane().setSpeed(newAirplaneSpeed);
        this.fuelConsumption = calculateFuelConsumption(rpm);
    }

    private double calculateFuelConsumption(double rpm) {
        this.fuelConsumption = rpm / 260;

        return this.fuelConsumption;
    }

    /**
     * @return the fuelConsumption
     */
    public double getFuelConsumption() {
        return this.fuelConsumption;
    }

    /**
     * @param fuelConsumption the fuelConsumption to set
     */
    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }
}
