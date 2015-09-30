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

    boolean addAppointment(Appointment a) {
        if (!this.agenda.contains(a)) {
            this.agenda.add(a);
            return true;
        }
        
        return false;
    }

    void removeAppointment(Appointment a) {
        this.agenda.remove(a);
    }

    public ArrayList<Appointment> appointments() {
        return (ArrayList<Appointment>) Collections.unmodifiableList(this.agenda);
    }
}
