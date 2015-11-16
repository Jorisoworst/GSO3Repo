/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.rmi;

import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruud
 */
public class HostService implements RemotePublisher
{        
    private RMIAirplane RMIAirplane;
   
    private BasicPublisher publisher;
    private Registry registry = null;
    public static final String registryKey = "hostServ";
    public static final String airplaneProperty = "airplaneProp";
    public static final String objectProperty = "objectProp";
    
    public static void main(String[] args) {
        System.out.println("TESTING RUN - Host starting");
        HostService service = new HostService(1099);
         System.out.println("Host starting");
         
    }
    
    
    public HostService(int portNumber) //default 1099?
    {
        try {
            //create remote objects
            this.RMIAirplane = new RMIAirplane();
            
            //create a publisher with property names.
            publisher = new BasicPublisher(new String[]{airplaneProperty, objectProperty}); //add bullet and obstacle
            //create registry with binding name.
            registry = LocateRegistry.createRegistry(portNumber);
            //bind this host to a registry key.
            registry.rebind(registryKey, this);
            System.out.println("Server/Host: Registry created on port number " + portNumber);
            System.out.println("Server/Host: Registry binded");
            
            //TODO: we now have a push system made, the host should (not tested) be able to push objects.
            //but now the host should be able to receive objects as well.
            
        } catch (RemoteException ex) {
            Logger.getLogger(HostService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void someMethod()
    {
        if(publisher != null)
        {
            System.out.println("inform clients");
            publisher.inform(this, airplaneProperty, null, RMIAirplane); //property, oldValue,newValue. Like sending RMIAirplane
        }
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        System.out.println("Server/Host: added listener");
        this.publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        System.out.println("Server/Host: removed listener");
        this.publisher.removeListener(listener, property);
    }
}
