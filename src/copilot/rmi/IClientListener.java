/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import java.util.List;

/**
 *
 * @author Ruud
 */
public interface IClientListener {
    //airplane updates
    public void AirplaneAltitudeChanged(int oldAltitude, int newAltitude);
    public void AirplaneSpeedChanged(int oldSpeed, int newSpeed);
    public void AirplanePitchChanged(double oldPitch, double newPitch);
    public void AirplaneFuelChanged(int oldValue, int newValue);
    public void AirplanePositionUpdate(int oldX, int oldY, int newX, int newY);
    
    //bullet updates
    public void BulletAdded(IrmiBullet bullet);
    public void BulletRemoved(IrmiBullet bullet);
    
    //obstacle updates
    public void ObstacleAdded(IrmiObstacle obstacle);
    public void ObstacleRemoved(IrmiObstacle obstacle);
}
