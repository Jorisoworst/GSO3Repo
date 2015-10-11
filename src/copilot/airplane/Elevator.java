/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.airplane;

import copilot.controller.User;

/**
 *
 * @author Niels
 */
public class Elevator extends AirplanePart {
    private double elevatorPitch;
    private Airplane airplane;
    private static double PITCH_INCREASMENT = 0.2;
    /**
     * Initialize an instance of the Elevator class which extends ArplanePart
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     */
    public Elevator(Airplane airplane, User user) {
        super(airplane, user);
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
        this.elevatorPitch = elevatorPitch;
        //the elevator pitch has changed, as a result, the airplane nose pitch will change.
        //Assumption: User will continuesly give input that calls this method.
        if(elevatorPitch > 0)
        {
            double currentPitch = airplane.getPitch();
            double newPitch = currentPitch + (PITCH_INCREASMENT * elevatorPitch);
            airplane.setPitch(newPitch);
        }
        else
        {
             double currentPitch = airplane.getPitch();
            double newPitch = currentPitch - (PITCH_INCREASMENT * elevatorPitch);
            airplane.setPitch(newPitch);
        }
        airplane.updateAirplane();
    }
	
    
}
