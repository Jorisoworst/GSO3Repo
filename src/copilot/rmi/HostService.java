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
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruud
 */
public class HostService  extends UnicastRemoteObject implements RemotePublisher 
{        
    private RMIAirplane RMIAirplane;
    private BasicPublisher publisher;
    private Registry registry = null;
    public static final String registryKey = "hostServ";
    public static final String airplaneProperty = "airplaneProp";
    public static final String objectProperty = "objectProp";
    
    public static void main(String[] args) throws InterruptedException {
        
         
        try {
            System.out.println("TESTING RUN - Host starting");
            HostService service = new HostService(1099);
            System.out.println("Host starting");
            RMIAirplane airplane = new RMIAirplane();
            airplane.setAltitude(1000);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(2000);
                airplane.setAltitude(airplane.getAltitude()+500);
                System.out.println("Host send airplane.");
                service.UpdateAirplaneToClients(airplane);
            }
        } catch (RemoteException ex) {
            System.out.println("HOST ERROR: RemoteException creating RMIAairplane");
            Logger.getLogger(HostService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    
    public HostService(int portNumber) throws RemoteException//default 1099?
    {
        //create remote objects
        this.RMIAirplane = new RMIAirplane();
        publisher = new BasicPublisher(new String[]{airplaneProperty, objectProperty}); //add bullet and obstacle
        //create registry with binding name.
        registry = LocateRegistry.createRegistry(portNumber);
        //bind this host to a registry key.
        registry.rebind(registryKey, this);
        System.out.println("Server/Host: Registry created on port number " + portNumber);
        System.out.println("Server/Host: Registry binded");

        //TODO: we now have a push system made, the host should (not tested) be able to push objects.
        //but now the host should be able to receive objects as well.


    }   
    
    
    public void UpdateAirplaneToClients(RMIAirplane airplane)
    {
        this.RMIAirplane = airplane;
        if(publisher != null)
        {
            System.out.println("inform airplane to clients");
             publisher.inform(this, airplaneProperty, null, airplane); //property, oldValue,newValue. Like sending RMIAirplane
        }
    }
    
    public void SendObstacleToClients(RMIAirplane airplane)
    {
        if(publisher != null)
        {
            System.out.println("inform obstacle clients");
            publisher.inform(this, airplaneProperty, null, airplane); //property, oldValue,newValue. Like sending RMIAirplane
        }
    }

   @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
       this.publisher.removeListener(listener, property);
    }

}
