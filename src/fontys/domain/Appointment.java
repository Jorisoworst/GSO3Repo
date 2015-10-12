/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.domain;

import fontys.time.*;
import java.util.*;

/**
 *
 * @author Joris
 */
public class Appointment {

    private String subject;
    private ITimeSpan timeSpan;
    private ArrayList<Contact> invitees;

    public Appointment(String subject, ITimeSpan timeSpan) {
        this.subject = subject;
        this.timeSpan = timeSpan;
        this.invitees = new ArrayList<>();
    }

    public ITimeSpan getTimeSpan() {
        return this.timeSpan;
    }

    public boolean addContact(Contact c) {
        if (!this.invitees.contains(c)) {
            this.invitees.add(c);
            return true;
        }

        return false;
    }

    public void removeContact(Contact c) {
        this.invitees.remove(c);
    }

    public List<Contact> getInvitees() {
        return (List<Contact>) Collections.unmodifiableList(this.invitees);
    }
    
    /**
     * checked of het Contact bestaat
     *
     * @param c Contact die je wilt checken of die er in staat
     * @return true als Contact bestaat, false als niet bestaat
     */
    public boolean getContact(Contact c) {
        return this.invitees.contains(c);
    }
}
