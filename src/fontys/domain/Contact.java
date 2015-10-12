/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.domain;

import java.util.*;

/**
 *
 * @author Joris
 */
public class Contact {

    private String name;
    private ArrayList<Appointment> agenda;

    public Contact(String name) {
        this.name = name;
        this.agenda = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public boolean addAppointment(Appointment a) {
        if (!this.agenda.contains(a)) {
            this.agenda.add(a);
            return true;
        }

        return false;
    }

    public void removeAppointment(Appointment a) {
        this.agenda.remove(a);
    }

    //test werkt nog niet! 
    //geeft error "Collections$UnmodifiableRandomAccessList cannot be cast to java.util.ArrayList"
    public List<Appointment> appointments() {
        return (List<Appointment>) Collections.unmodifiableList(this.agenda);
    }

    /**
     * checked of de Appointment bestaat
     *
     * @param a Appointment die je wilt checken of die er in staat
     * @return true als Appointment bestaat, false als niet bestaat
     */
    public boolean getAppointment(Appointment a) {
        return this.agenda.contains(a);
    }
}
