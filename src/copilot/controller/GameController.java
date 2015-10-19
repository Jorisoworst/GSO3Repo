/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 *
 * @author Joris
 */
public class GameController implements Initializable {

    //Main Menu
    @FXML
    Button startGameBtn;
    @FXML
    Button highscoresBtn;
    @FXML
    Button quitGameBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void startGame(Event evt) {
        System.out.println("START GAME");
    }

    public void quitGame(Event evt) {
        System.out.println("QUIT GAME");
    }
}
