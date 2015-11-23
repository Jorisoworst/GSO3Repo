/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import java.io.Serializable;

/**
 *
 * @author Ruud
 */
public interface IrmiObject extends Serializable {
    
    public int getId();
    public int getX();
    public int getY();
}
