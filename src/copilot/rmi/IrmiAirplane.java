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
public interface IrmiAirplane extends IrmiObject {
    public int getSpeed();
    public double getPitch();
    public int getFuelAmount();
    public int getAltitude();
}
