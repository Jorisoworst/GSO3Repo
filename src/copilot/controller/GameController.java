/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import copilot.domain.Airplane;
import copilot.domain.Bullet;
import copilot.domain.Obstacle;
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
        Airplane airplane1 = null;
        Airplane airplane2 = null;
        Obstacle obstacle1 = null;
        Obstacle obstacle2 = null;
        Bullet bullet1 = null;
        Bullet bullet2 = null;

        if (body1 instanceof Airplane) {
            airplane1 = (Airplane) body1;
        } else if (body1 instanceof Obstacle) {
            obstacle1 = (Obstacle) body1;
        } else if (body1 instanceof Bullet) {
            bullet1 = (Bullet) body1;
        }

        if (body2 instanceof Airplane) {
            airplane2 = (Airplane) body2;
        } else if (body2 instanceof Obstacle) {
            obstacle2 = (Obstacle) body2;
        } else if (body2 instanceof Bullet) {
            bullet2 = (Bullet) body2;
        }

        if (airplane1 != null) {
            System.out.print("Airplane " + airplane1.getTransform() + " met ");
        } else if (obstacle1 != null) {
            System.out.print("Obstacle " + obstacle1.getTransform() + " met ");
        } else if (bullet1 != null) {
            System.out.print("Bullet " + bullet1.getTransform() + " met ");
        }

        if (airplane2 != null) {
            System.out.println("Airplane " + airplane2.getTransform());
        } else if (obstacle2 != null) {
            System.out.println("Obstacle " + obstacle2.getTransform());
        } else if (bullet2 != null) {
            System.out.println("Bullet " + bullet2.getTransform());
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
