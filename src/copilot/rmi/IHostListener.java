/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

/**
 *
 * @author Ruud
 */
public interface IHostListener {
    
    //when a client fires a new bullet
    public void BulletFire(IrmiBullet bullet);
    
    //when a client that controls the altitude presses upKey (true = up, false = down).
    public void AirplaneAltitudeKeyPress(boolean upKey);
    
    //when the user that controls the speed changes the speed (RPM of the propellor).
    public void SpeedChanged(int newRPM);
    
    //when the user that controls the fuel tupe updates his position.
    public void FuelTupePositionChanged(int oldX, int oldY, int newX, int newY);
}
