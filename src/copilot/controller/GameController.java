/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Joris
 */
public class GameController implements KeyListener {

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

        System.out.println(KEY_PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
