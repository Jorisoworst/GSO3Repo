/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.domain.GameAdministration;
import copilot.domain.Session;
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
 * @author NielsPrasing
 */
public class MainMenuGUI {
    
    public MainMenuGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }
        
        JFrame frame = new JFrame("CO-Pilot Main Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, userLoggedIn);

        frame.setVisible(true);
    }
    
    private static void placeComponents(JPanel panel, User user) {
        
        panel.setLayout(null); 
        
        JButton joinButton = new JButton("join");
        joinButton.setBounds(20, 300, 160, 50);
        panel.add(joinButton);
        
        JButton hostButton = new JButton("host");
        hostButton.setBounds(20, 365, 160, 50);
        panel.add(hostButton);
        
        JButton settingsButton = new JButton("settings");
        settingsButton.setBounds(20, 430, 160, 50);
        panel.add(settingsButton);
        
        JButton creditsButton = new JButton("credits");
        creditsButton.setBounds(20, 495, 160, 50);
        panel.add(creditsButton);
        
        JButton logoutButton = new JButton("logout");
        logoutButton.setBounds(610, 10, 160, 50);
        panel.add(logoutButton);
        
        joinButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel); 
                LobbyGUI lobby = new LobbyGUI(user);                
                frameToClose.dispose();  
            }
        });
        
        hostButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                Session session = GameAdministration.getInstance().createSession(user);
                session.addUser(user);
                SessionGUI sessionGUI = new SessionGUI(session);
                frameToClose.dispose();
            }
        });
        
        settingsButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                SettingsGUI settings = new SettingsGUI(user);
                frameToClose.dispose();
            }
        });
        
        creditsButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                CreditsGUI credits = new CreditsGUI(user);
                frameToClose.dispose(); 
            }
        });
        
        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                LogonGUI logon = new LogonGUI();
                frameToClose.dispose();  
            }
        });
    }
}
