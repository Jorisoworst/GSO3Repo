/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruud
 */
public class ExampleHostImplementation implements IHostListener{
    //implements IHostListener for receiving client calls.
    
      public static void main(String[] args) throws InterruptedException, RemoteException {
    //Just to run it... remove this later
          ExampleHostImplementation hostImplementation = new ExampleHostImplementation();
        
    }
    
    
    public ExampleHostImplementation() throws RemoteException, InterruptedException
    {
        System.out.println("TESTING RUN - Host starting");
        //create the service on a given port. Catch the exceptions, usually occur when the given port is taken
        HostService service = new HostService(1099);
        //set listener to retrieve client update calls. (Remote Method Invocation).
        service.SetHostListener(this);
        System.out.println("Host started");
        
         //create objects. (You send an interface, but you have to create an object to set properties)
        //Not able to use domain.Airplane because classes can only extend one other class, domain.gameObject already extended Body.
         RMIAirplane airplane = new RMIAirplane();
         airplane.setAltitude(1000);
         
         RMIBullet bullet = new RMIBullet();
         bullet.setAimX(5);
         bullet.setAimY(6);
         bullet.setxPos(10);
         bullet.setyPos(11);
         
         RMIObstacle obstacle = new RMIObstacle();
         obstacle.setType("K"); //K kerosine or B for bird, or anything else what you like.
         obstacle.setxPos(10);
         obstacle.setyPos(10);

         for (int i = 0; i < 10; i++) {
             Thread.sleep(2000);
             //changing random value
             airplane.setAltitude(airplane.getAltitude()+500);
             System.out.println("Host send airplane.");
             //send the airplane to the clients.
             service.UpdateAirplaneToClients(airplane);
             //send bullet to cleints
             service.SendBulletToClients(bullet);
             //send obstacle (kerosine pickup or bird).
             service.SendObstacleToClients(obstacle);
             
         }

         
        
        
    }
    
    @Override
    public void BulletFire(IrmiBullet bullet) {
        System.out.println("HOST - Bullet received: AimX: " + bullet.AimX() + " AimY: " + bullet.AimY());
    }

    @Override
    public void AirplaneAltitudeKeyPress(boolean upKey) {
        System.out.println("HOST - altitude Keypress Received upKey: " + upKey);
    }

    @Override
    public void SpeedChanged(int newRPM) {
         System.out.println("HOST - Speed Change Received newRPM: " + newRPM);
    }

    @Override
    public void FuelTupePositionChanged(int oldX, int oldY, int newX, int newY) {
        System.out.println("HOST - FuelTupePositionChanged Received: oldX: " + oldX + " oldY: " + oldY + " newX: " + newX + " newY: " + newY);
    }
    
}
