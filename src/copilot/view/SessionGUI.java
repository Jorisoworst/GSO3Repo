/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.domain.Session;
import copilot.domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author IndyGames
 */
public class SessionGUI extends JPanel {
    
    public SessionGUI(Session session, User userLoggedIn) {
        placeComponents(session, userLoggedIn);
    }
    
    private void placeComponents(Session session, User user) {
        this.setLayout(null);
        
        JButton backButton = new JButton("back");
        backButton.setBounds(610, 10, 160, 50);
        this.add(backButton);
        
        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AllCopilotGUI.setPanel("lobby", user);
            }
        });
    }
    
}
