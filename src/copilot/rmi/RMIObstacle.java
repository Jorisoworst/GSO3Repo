/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

/**
 *
 * @author Ruud
 */
public class RMIObstacle implements IrmiObstacle{
    public static int nextObostacleId = 1;
    private int obstacleId = 0;
    private String type = "N"; //N = none, K = keroesine, B = bird 
    private int xPos = 0, yPos = 0;

    public  RMIObstacle()
    {
        this.obstacleId = nextObostacleId;
        nextObostacleId++;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    
    @Override
    public int GetObstacleId() {
        return obstacleId;
    }
    
    @Override
    public String ObsType() {
        return type;
    }

     @Override
    public int getId() {
        return obstacleId;
    }

    
    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

   
    
}
