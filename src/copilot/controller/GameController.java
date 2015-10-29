/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.dyn4j.collision.manifold.Manifold;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.CollisionListener;
import org.dyn4j.dynamics.contact.ContactConstraint;

/**
 *
 * @author IndyGames
 */
public class GameController implements CollisionListener {

    public JPanel panel;
    public String KEY_PRESSED;

    public GameController(JPanel panel) {
        this.panel = panel;
        
        InputMap im = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = panel.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Space");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        
        am.put("RightArrow", new KeyAction("RightArrow"));
        am.put("LeftArrow", new KeyAction("LeftArrow"));
        am.put("UpArrow", new KeyAction("UpArrow"));
        am.put("DownArrow", new KeyAction("DownArrow"));
        am.put("Space", new KeyAction("Space"));
        am.put("Escape", new KeyAction("Escape"));

        KEY_PRESSED = "NONE";
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

    public class KeyAction extends AbstractAction {

        public KeyAction(String name) {
            putValue(Action.NAME, name);
            putValue(ACTION_COMMAND_KEY, "Command: " + name);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           switch (getValue(Action.NAME).toString()) {
                case "UpArrow": {
                    KEY_PRESSED = "UP";
                    break;
                }
                case "DownArrow": {
                    KEY_PRESSED = "DOWN";
                    break;
                }
                case "LeftArrow": {
//                    KEY_PRESSED = "LEFT";
                    KEY_PRESSED = "NONE";
                    break;
                }
                case "RightArrow": {    
//                    KEY_PRESSED = "RIGHT";
                    KEY_PRESSED = "NONE";
                    break;
                }
                case "Space": {
                    KEY_PRESSED = "SPACE";
                    break;
                }
                case "Escape": {
                    KEY_PRESSED = "ESCAPE";
                    break;
                }
                default: {
                    KEY_PRESSED = "NONE";
                    break;
                }
            }
        }
    }
}
