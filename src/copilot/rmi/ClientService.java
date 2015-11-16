/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.beans.PropertyChangeEvent;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruud
 */
public class ClientService implements Remote, RemotePropertyListener { //maybe extend unicast remote

    private RemotePublisher publisher;
    private Registry registry;
    
    
    public ClientService(String serverAddress, int port)
    {
        try {
            //look for a host on a given server address (localhost or ip address) and a given port.
            registry = LocateRegistry.getRegistry(serverAddress,port);
            this.publisher = (RemotePublisher) registry.lookup(HostService.registryKey);
           this.publisher.addListener(this, HostService.airplaneProperty);
           this.publisher.addListener(this, HostService.objectProperty);
           System.out.println("Client: registry looked up, added listeners.");
        } catch (RemoteException ex) {
            System.out.println("Client: probably server not found.");
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
             System.out.println("Client: probably the publisher is not in the registry.");
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        //when host informs listeners, this method will be executed.
        System.out.println("Client: propertyChange.");
        if(evt.getPropertyName().endsWith(HostService.airplaneProperty))
        {
            System.out.println("Client: propertyChange. is a airplane");
            //received airplane value.
            if(evt.getNewValue() instanceof IrmiAirplane)
            {
                IrmiAirplane ap = (IrmiAirplane) evt.getNewValue();
                
            }
        }
        else if(evt.getPropertyName().endsWith(HostService.objectProperty))
        {
            //received an object.
            System.out.println("Client: propertyChange. is a object");
        }
    }
   
    
    
}
