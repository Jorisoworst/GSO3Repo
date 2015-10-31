/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IndyGames
 */
public class CreditsGUI {
    
    public CreditsGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }
        
        JFrame frame = new JFrame("CO-Pilot Credits");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, userLoggedIn);

        frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel, User user) {
        
        panel.setLayout(null); 
        
        JButton backButton = new JButton("back");
        backButton.setBounds(610, 10, 160, 50);
        panel.add(backButton);
        
        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);  
                MainMenuGUI mainMenu = new MainMenuGUI(user);          
                frameToClose.dispose(); 
            }
        });
    }
}
