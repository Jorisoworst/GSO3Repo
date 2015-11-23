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
public class HostService  extends UnicastRemoteObject implements RemotePublisher , IHostTaskReceiver
{        
    public static final String registryKey = "hostServ";
    public static final String hostListenerKey = "hostListener";
    public static final String airplaneProperty = "airplaneProp";
    public static final String obstacleAddProperty = "obstacleAddProp";
    public static final String bulletAddProperty = "bulletAddProp";
    public static final String obstacleDeleteProperty = "obstacleDeleteProp";
    public static final String bulletDeleteProperty = "bulletDeleteProp";
    
    private BasicPublisher publisher;
    private Registry registry = null;
    
    private IHostListener listener;
      
    public HostService(int portNumber) throws RemoteException//default 1099?
    {
        //instantiate publisher, set properties that can be used to listen to.
        publisher = new BasicPublisher(new String[]{airplaneProperty, obstacleAddProperty, bulletAddProperty, obstacleDeleteProperty, bulletDeleteProperty}); 
        //create registry with a port.
        registry = LocateRegistry.createRegistry(portNumber);
        System.out.println("Server/Host: Registry created on port number " + portNumber);
        //bind this host to a registry key, this can be looked up on the client.
        registry.rebind(registryKey, this);
         System.out.println("Server/Host: Registry binded");
        registry.rebind(hostListenerKey, this);
        System.out.println("Server/Host: binded IHostTaskReceiver");
        
       

        //TODO: we now have a push system made, the host is able to push objects.
        //but now the host should be able to receive objects as well.
    }   
    
    public void SetHostListener(IHostListener listener)
    {
        this.listener = listener;
    }
    
    //UPDATE METHODS: Sends interfaces to clients that are listening to this host.
    public void UpdateAirplaneToClients(IrmiAirplane airplane)
    {
        if(publisher != null)
        {
            System.out.println("inform airplane to clients");
             publisher.inform(this, airplaneProperty, null, airplane); //property, oldValue,newValue. Like sending RMIAirplane
        }
    }
    
    public void SendObstacleToClients(IrmiObstacle obstacle)
    {
        if(publisher != null)
        {
            System.out.println("inform obstacle clients");
            publisher.inform(this, obstacleAddProperty, null, obstacle); //property, oldValue,newValue. Like sending RMIAirplane
        }
    }
    
    public void SendBulletToClients(IrmiBullet bullet)
    {
        if(publisher != null)
        {
            System.out.println("inform obstacle clients");
            publisher.inform(this, bulletAddProperty, null, bullet); //property, oldValue,newValue. Like sending RMIAirplane
        }
        
    }

    //AddListeners (RemotePublisher implementation): Client is able to add itself as a listener on a specific property, listener is added to BasicPublisher.
   @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        this.publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
       this.publisher.removeListener(listener, property);
    }

    @Override
    public void BulletFire(IrmiBullet bullet) throws RemoteException {
        if(listener != null)
        {
            listener.BulletFire(bullet);
        }
    }

    @Override
    public void AirplaneAltitudeKeyPress(boolean upKey) throws RemoteException {
        if(listener != null)
        {
            listener.AirplaneAltitudeKeyPress(upKey);
        }
    }

    @Override
    public void SpeedChanged(int newRPM) throws RemoteException {
        if(listener != null)
        {
            listener.SpeedChanged(newRPM);
        }
    }

    @Override
    public void FuelTupePositionChanged(int oldX, int oldY, int newX, int newY) throws RemoteException {
        if(listener != null)
        {
            listener.FuelTupePositionChanged(oldX, oldY, newX, newY);
        }
    }

}
