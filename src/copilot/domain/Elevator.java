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
public class Elevator extends AirplanePart {
    private double elevatorPitch;
    private Airplane airplane;
    public static double PITCH_INCREASMENT = 0.3;
    /**
     * Initialize an instance of the Elevator class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Elevator(Image image, Airplane airplane, User user) {
        super(image, airplane, user);
        this.airplane = airplane;
    }

    /**
     * @return the elevatorPitch
     */
    public double getElevatorPitch() {
        return elevatorPitch;
    }

 /**
     * @param elevatorPitch the elevatorPitch to set
     */
    public void setElevatorPitch(double elevatorPitch) {
        
        //the elevator pitch has changed, as a result, the airplane nose pitch will change.
        //Assumption: User will continuesly give input that calls this method.
        
        double currentPitch = airplane.getPitch();
        double newPitch = currentPitch + (PITCH_INCREASMENT * elevatorPitch);
        airplane.setPitch(newPitch);

        this.elevatorPitch = elevatorPitch;
        //airplane.updateAirplane();
    }
	
    
}
