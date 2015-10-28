/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

/**
 *
 * @author Joris
 */
public class GameController implements KeyListener, CollisionListener {

    public String KEY_PRESSED = "NONE";

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: {
                KEY_PRESSED = "UP";
                break;
            }
            case KeyEvent.VK_DOWN: {
                KEY_PRESSED = "DOWN";
                break;
            }
            case KeyEvent.VK_LEFT: {
                KEY_PRESSED = "LEFT";
                break;
            }
            case KeyEvent.VK_RIGHT: {
                KEY_PRESSED = "RIGHT";
                break;
            }
            case KeyEvent.VK_SPACE: {
                KEY_PRESSED = "SPACE";
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                KEY_PRESSED = "ESCAPE";
                break;
            }
            default: {
                KEY_PRESSED = "NONE";
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public boolean collision(Body body1, BodyFixture fixture1, Body body2, BodyFixture fixture2) {
        if (body1 != null && body2 != null) {
            body1.setUserData(body2);
            body2.setUserData(body1);
        }

        return false;
    }

    @Override
    public boolean collision(Body body1, BodyFixture fixture1, Body body2, BodyFixture fixture2, Penetration penetration) {
        return false;
    }

    @Override
    public boolean collision(Body body1, BodyFixture fixture1, Body body2, BodyFixture fixture2, Manifold manifold) {
        return false;
    }

    @Override
    public boolean collision(ContactConstraint contactConstraint) {
        return false;
    }
}
