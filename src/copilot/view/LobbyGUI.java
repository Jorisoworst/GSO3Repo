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
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IndyGames
 */
public class LobbyGUI {
    
    public LobbyGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }
        
        JFrame frame = new JFrame("CO-Pilot Lobby");
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
        
        JLabel sessionsLabel = new JLabel("sessions");
        sessionsLabel.setBounds(500, 50, 80, 14);
        panel.add(sessionsLabel);
        
        DefaultListModel modelSessions = new DefaultListModel();
        JList sessions = new JList(modelSessions);
        sessions.setBounds(270, 70, 500, 420);
        panel.add(sessions);
        
        JLabel usersLabel = new JLabel("users");
        usersLabel.setBounds(125, 50, 50, 14);
        panel.add(usersLabel);
        
        DefaultListModel modelUsers = new DefaultListModel();
        JList users = new JList(modelUsers);
        users.setBounds(10, 70, 250, 420);
        panel.add(users);
        
        JButton joinButton = new JButton("join");
        joinButton.setBounds(610, 500, 160, 50);
        panel.add(joinButton);
        
        JButton refreshButton = new JButton("refresh");
        refreshButton.setBounds(10, 500, 160, 50);
        panel.add(refreshButton);
        
        JButton backButton = new JButton("back");
        backButton.setBounds(610, 10, 160, 50);
        panel.add(backButton);
        
        GameAdministration admin = GameAdministration.getInstance();
        ArrayList<Session> sessionsList = admin.getSessions();
        ArrayList<User> usersList = admin.getUsers();
        int count = 0;
        
        for (Session session : sessionsList) {
            modelSessions.addElement(count + ": " + session.getHost().getUsername() + " Game, Amount of players in session: " + session.getUsers().size() + ", is started: " + session.isIsStarted());
        }
        
        for (User userInList : usersList) {
            modelUsers.addElement(userInList.getUsername());
        }
        
        // TODO kan pas echt gaan werken wanneer RMI goed werkzaam is
        joinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        refreshButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                GameAdministration admin = GameAdministration.getInstance();
                ArrayList<Session> sessionsList = admin.getSessions();
                ArrayList<User> usersList = admin.getUsers();
                int count = 0;

                for (Session session : sessionsList) {
                    modelSessions.addElement(count + ": " + session.getHost().getUsername() + " Game, Amount of players in session: " + session.getUsers().size() + ", is started: " + session.isIsStarted());
                }

                for (User userInList : usersList) {
                    modelUsers.addElement(userInList.getUsername());
                }
            }
        });
        
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
