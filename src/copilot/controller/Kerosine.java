/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

/**
 *
 * @author Niels
 */
public class Kerosine extends Pickup {

    private int amount;

    /**
     * Initialize an instance of the Kerosine class which extends Pickup
     */
    public Kerosine() {
        this.amount = 0;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
