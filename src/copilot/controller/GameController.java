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

    private final String[] keyIdentifiers;
    private final Integer[] keyValues;
    private String keyPressed;
    
    //Create a new game controller with Jpanel to observe.
    public GameController(JPanel panel) {
        
        
        //Add keys to the Dictionary.
        this.keyIdentifiers = new String[]{
            "UP",
            "DOWN",
            "LEFT",
            "RIGHT",
            "SPACE",
            "ESCAPE"
        };
        
        
        //Add the corresponding values to the Dictionary keys.
        this.keyValues = new Integer[]{
            KeyEvent.VK_W/*VK_UP*/,
            KeyEvent.VK_S/*VK_DOWN*/,
            KeyEvent.VK_A/*VK_LEFT*/,
            KeyEvent.VK_D/*VK_RIGHT*/,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_ESCAPE
        };
        
        
        //Create the inputmap and get the actionmap.
        InputMap im = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = panel.getActionMap();
        int limit = this.keyIdentifiers.length;

        //Set all pressed the keys in the dictionary.
        for (int i = 0; i < limit; i++) {
            im.put(KeyStroke.getKeyStroke(this.keyValues[i], 0), this.keyIdentifiers[i]);
            am.put(this.keyIdentifiers[i], new KeyAction(this.keyIdentifiers[i]));
        }
        
        //Identify key with a releaseIdentifier (this means this key becomes a 
        //temporary release key and then gets added to the actionmap and inputmap below).
        String releasedIdentifier = "";
        
        
        //Set all the released keys in the dictionary.
        for (int i = 0; i < limit - 1; i++) {
            releasedIdentifier = this.keyIdentifiers[i] + "_RELEASED";
            im.put(KeyStroke.getKeyStroke(this.keyValues[i], 0, true), releasedIdentifier);
            am.put(releasedIdentifier, new KeyAction(releasedIdentifier));
        }

        //The standard keyPressed value is NONE (so no unwanted movement occurs).
        this.keyPressed = "NONE";
    }

    //Check if one body collides with the other body and then return if the 
    //result is positive or negative.
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

    /**
     * @return the keyPressed
     */
    public String getKeyPressed() {
        return keyPressed;
    }

    /**
     * @param keyPressed the keyPressed to set
     */
    public void setKeyPressed(String keyPressed) {
        this.keyPressed = keyPressed;
    }

    //This is the key Action class which returns the abstract action that occurs 
    //(for example a keypress).
    public class KeyAction extends AbstractAction {

        //A key action (for example the "Up" Key).
        public KeyAction(String name) {
            putValue(Action.NAME, name);
            putValue(ACTION_COMMAND_KEY, "Command: " + name);
        }
        
        //If a key is pressed then perform a certain action based on that key.
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputKey = getValue(Action.NAME).toString();
            
            //Check if the key corresponds to the wanted result and then set 
            //the pressed key to that designated value.
            switch (inputKey) {
                case "UP":
                case "DOWN": {
                    if (getKeyPressed().equals("SPACE")) {
                        setKeyPressed(inputKey + "_" + getKeyPressed());
                    } else if (getKeyPressed().endsWith("_SPACE")) {
                        setKeyPressed(inputKey + "_SPACE");
                    } else {
                        setKeyPressed(inputKey);
                    }
                    break;
                }
                case "SPACE": {
                    if (getKeyPressed().equals("UP")
                            || getKeyPressed().equals("DOWN")) {
                        setKeyPressed(getKeyPressed() + "_" + inputKey);
                    } else if (!getKeyPressed().endsWith("_SPACE")) {
                        setKeyPressed(inputKey);
                    }
                    break;
                }
                case "ESCAPE": {
                    setKeyPressed(inputKey);
                    break;
                }
                default: {
                    setKeyPressed("NONE");
                    break;
                }
            }
        }
    }
}
