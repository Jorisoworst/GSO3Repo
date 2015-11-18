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
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ruud
 */
public class ClientService extends UnicastRemoteObject implements Remote, RemotePropertyListener { //maybe extend unicast remote

    private RemotePublisher publisher;
    private Registry registry;
    
    private List<IClientListener> listeners;
    
    //received remote objects.
    private IrmiAirplane airplane;
    
    public static void main(String[] args) throws RemoteException {
         System.out.println("TESTING RUN - Client starting");
        ClientService service = new ClientService("localhost",1099);
         System.out.println("Client started");
    }
    
    public ClientService(String serverAddress, int port) throws RemoteException
    {
        try {
            //look for a host on a given server address (localhost or ip address) and a given port.
            //RemoteException is thrown when no host is found on this address and port.
            registry = LocateRegistry.getRegistry(serverAddress,port);
            //if we got a host, get it's publisher.
            this.publisher = (RemotePublisher) registry.lookup(HostService.registryKey);
            //add listeners for properties.
            this.publisher.addListener(this, HostService.airplaneProperty);
            this.publisher.addListener(this, HostService.obstacleProperty);
            this.publisher.addListener(this, HostService.bulletProperty);
            System.out.println("Client: registry looked up, added listeners.");
        } catch (RemoteException ex) {
            System.out.println("Client: probably server not found.");
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
             System.out.println("Client: probably the publisher is not in the registry.");
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    public void AddClientListener(IClientListener listener)
    {
        if(listeners == null)
        {
            listeners = new ArrayList<IClientListener>();
        }
        listeners.add(listener);
    }
    
    public void RemoveClientListener(IClientListener listener)
    {
        if(listener != null)
        {
            listeners.remove(listener);//if it exit, it will be removed.
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        //when host informs listeners, this method will be executed.
        //evt contains the property and the newObject.
        System.out.println("Client: propertyChange.");
        if(evt.getPropertyName().endsWith(HostService.airplaneProperty))
        {
            System.out.println("Client: propertyChange. is a airplane");
            //received airplane value.
            if(evt.getNewValue() instanceof IrmiAirplane)
            {
                IrmiAirplane ap = (IrmiAirplane) evt.getNewValue();
                ReceivedAirplane(ap);
            }
        }
        else if(evt.getPropertyName().endsWith(HostService.obstacleProperty))
        {
            //received an obstacle.
            System.out.println("Client: propertyChange. is a object");
        }
        else if(evt.getPropertyName().endsWith(HostService.bulletProperty))
        {
            //received an bullet.
            System.out.println("Client: propertyChange. is a object");
        }
    }
    
    private void ReceivedAirplane(IrmiAirplane receivedAirplane)
    {
        boolean speedChanged = false, altitudeChanged = false, pitchChanged = false, fuelChanged = false;
        if(this.airplane == null)
        {
            this.airplane = receivedAirplane;
            for (IClientListener listener : listeners) {
                listener.AirplaneAltitudeChanged(0, receivedAirplane.getAltitude());
                listener.AirplaneFuelChanged(0, receivedAirplane.getFuelAmount());
                listener.AirplanePitchChanged(0, receivedAirplane.getPitch());
                listener.AirplaneSpeedChanged(0, receivedAirplane.getSpeed());
            }
            //we don't have to check for changes, just return here
            return;
        }
        //check for changes, if so, call the client listeners.
        if(this.airplane.getAltitude() != receivedAirplane.getAltitude()){altitudeChanged = true;}
        if(this.airplane.getFuelAmount() != receivedAirplane.getFuelAmount()){fuelChanged = true;}
        if(this.airplane.getPitch() != receivedAirplane.getPitch()){pitchChanged = true;}
        if(this.airplane.getSpeed() != receivedAirplane.getSpeed()){speedChanged = true;}
        
         for (IClientListener listener : listeners) {
            if(altitudeChanged)
            {
                listener.AirplaneAltitudeChanged(this.airplane.getAltitude(), receivedAirplane.getAltitude());
            }
            if(fuelChanged)
            {
               listener.AirplaneFuelChanged(this.airplane.getFuelAmount(), receivedAirplane.getFuelAmount()); 
            }
            if(pitchChanged)
            {
               listener.AirplanePitchChanged(this.airplane.getPitch(), receivedAirplane.getPitch());  
            }
            if(speedChanged)
            {
                listener.AirplaneSpeedChanged(this.airplane.getSpeed(), receivedAirplane.getSpeed());
            }
        }
         this.airplane = receivedAirplane;
    }
   
    
    
}
