/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ruud
 */
public interface IHostTaskReceiver extends Remote{
    
    //when a client fires a new bullet
    public void BulletFire(IrmiBullet bullet) throws RemoteException;
    
    //when a client that controls the altitude presses upKey (true = up, false = down).
    public void AirplaneAltitudeKeyPress(boolean upKey)  throws RemoteException;
    
    //when the user that controls the speed changes the speed (RPM of the propellor).
    public void SpeedChanged(int newRPM)  throws RemoteException;
    
    //when the user that controls the fuel tupe updates his position.
    public void FuelTupePositionChanged(int oldX, int oldY, int newX, int newY) throws RemoteException;
}
