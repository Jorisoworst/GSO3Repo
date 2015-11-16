/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Ruud
 */
public class ClientService implements Remote, RemotePropertyListener {

    
    public ClientService()
    {
        
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
