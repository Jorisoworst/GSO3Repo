package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
 */
public class Elevator extends AirplanePart {

    public static final double PITCH_INCREASMENT = 0.3;
    private double elevatorPitch;

    /**
     * Initialize an instance of the Elevator class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Elevator(Image image, Airplane airplane, User user) {
        super(image, airplane, user);
    }

    /**
     * @return the elevatorPitch
     */
    public double getElevatorPitch() {
        return this.elevatorPitch;
    }

    /**
     * @param elevatorPitch the elevatorPitch to set
     */
    public void setElevatorPitch(double elevatorPitch) {
        //the elevator pitch has changed, as a result, the airplane nose pitch will change.
        //Assumption: User will continuesly give input that calls this method.
        double currentPitch = this.getAirplane().getPitch();
        double newPitch = currentPitch + (PITCH_INCREASMENT * elevatorPitch);
        this.getAirplane().setPitch(newPitch);

        this.elevatorPitch = elevatorPitch;
    }
}
