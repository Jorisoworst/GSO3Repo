/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import sun.security.x509.X400Address;

/**
 *
 * @author Ruud
 */
public class RMIBullet implements IrmiBullet{
    public static int nextBulletId = 1;
    private int id = 0, AimX = 0, AimY = 0, xPos = 0, yPos = 0;

    public RMIBullet()
    {
        this.id = nextBulletId;
        nextBulletId++;
    }
    
    
    public void setAimX(int AimX) {
        this.AimX = AimX;
    }

    public void setAimY(int AimY) {
        this.AimY = AimY;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    
     @Override
    public int getId() {
        return id;
    }
    
    
    @Override
    public int AimX() {
        return AimX;
    }

    @Override
    public int AimY() {
        return AimY;
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
