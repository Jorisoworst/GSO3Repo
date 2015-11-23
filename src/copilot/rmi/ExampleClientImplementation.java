/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Ruud
 */
public class ExampleClientImplementation implements IClientListener{
    //IClientListener is used to send updates from the host to the client. Below overrided methods will be called when the host sended something.
    
      public static void main(String[] args) throws RemoteException, InterruptedException, NotBoundException {
          //Just to run it... remove this later
        ExampleClientImplementation cleint = new ExampleClientImplementation();
        
    }
    
    
    public ExampleClientImplementation() throws RemoteException, InterruptedException, NotBoundException
    {
        //Create the client service, this will look for an host on a given address and port.
        //throws RemoteException if host is not available or something else I don't know yet.
        //throws NotBound if one if the interfaces on the host is not binded in the registry (will only happen if host is not available or there is a error on the host's computer).
         System.out.println("TESTING RUN - Client starting");
        ClientService service = new ClientService("localhost",1099);
        //add listener for getting server calls.
        service.AddClientListener(this);
         System.out.println("Client started");
         //example of sending an bullet that the client shoots (if he is the one controlling the gun).
         RMIBullet bullet = new RMIBullet();
         bullet.setAimX(50);
         bullet.setAimY(20);
         bullet.setxPos(10);
         bullet.setyPos(5);
         for (int i = 0; i < 10; i++) {
                Thread.sleep(2000);
                //randomness
                bullet.setAimX(i * 2);
                bullet.setAimY(i * 4);
                //fire bullet to server
                service.FireBullet(bullet);
                System.out.println("Client fired bullet");
                //other updates that the client can call.
                //update the position of the fuel tupe (end position, begin position is ofcourse the airplane).
                service.FuelTupePositionChanged(5, 10, 8, 12);
                //when the user that is controlling the altitude presses the up or down key (up = true, down = false.
                //continiously send when holding button
                service.AirplaneAltitudeKeyPress(true);
                //update the propellor RPM.
                service.SpeedChanged(400);
            }
    }

    @Override
    public void AirplaneAltitudeChanged(int oldAltitude, int newAltitude) {
        System.out.println("CLIENT - AirplaneAltitudeChanged: oldAltitude: " + oldAltitude + " newAltitude: " + newAltitude);
    }

    @Override
    public void AirplaneSpeedChanged(int oldSpeed, int newSpeed) {
        System.out.println("CLIENT - AirplaneSpeedChanged: oldSpeed: " + oldSpeed + " newSpeed: " + newSpeed);
    }

    @Override
    public void AirplanePitchChanged(double oldPitch, double newPitch) {
        System.out.println("CLIENT - AirplanePitchChanged: oldPitch: " + oldPitch + " newPitch: " + newPitch);
    }

    @Override
    public void AirplaneFuelChanged(int oldValue, int newValue) {
        System.out.println("CLIENT - AirplaneFuelChanged: oldValue: " + oldValue + " newValue: " + newValue);
    }

    @Override
    public void AirplanePositionUpdate(int oldX, int oldY, int newX, int newY) {
        System.out.println("CLIENT - AirplanePositionUpdate: oldX: " + oldX + " oldY: " + oldY + " newX: " + newX + " newY: " + newY);
    }

    @Override
    public void BulletAdded(IrmiBullet bullet) {
        System.out.println("CLIENT - BulletAdded: AimX: " + bullet.AimX() + " AimY: " + bullet.AimY());
    }

    @Override
    public void BulletRemoved(IrmiBullet bullet) {
        System.out.println("CLIENT - BulletRemoved: bulletId: " + bullet.getId());
    }

    @Override
    public void ObstacleAdded(IrmiObstacle obstacle) {
        System.out.println("CLIENT - ObstacleAdded: obstacle type: " + obstacle.ObsType() + " X: " + obstacle.getX() + " Y: " + obstacle.getY());
    }

    @Override
    public void ObstacleRemoved(IrmiObstacle obstacle) {
        System.out.println("CLIENT - ObstacleRemoved: obstacleId: " + obstacle.GetObstacleId());
    }
}
