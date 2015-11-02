/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.GameAdministration;
import copilot.domain.Session;
import copilot.domain.User;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author IndyGames
 */
public class LobbyGUI extends JPanel {
    
    private Font font, sizedFont;
    private Image screen, logo;
    private int screenWidth, screenHeight;
    
    public LobbyGUI(User userLoggedIn, int screenWidth, int screenHeight, Font font, Font sizedFont, Image screen, Image logo) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.font = font;
        this.sizedFont = sizedFont;
        this.screen = screen;
        this.logo = logo;
        placeComponents(userLoggedIn);
    }
    
    private void placeComponents(User user) {
        this.setLayout(null);

        JLabel usersLabel = new JLabel("users");
        usersLabel.setBounds(125, 50, 50, 14);
        this.add(usersLabel);
        
        DefaultListModel modelUsers = new DefaultListModel();
        JList users = new JList(modelUsers);
        users.setBounds(10, 70, 300, 500);
        this.add(users);
        
        JLabel sessionsLabel = new JLabel("sessions");
        sessionsLabel.setBounds(500, 50, 80, 14);
        this.add(sessionsLabel);
        
        DefaultListModel modelSessions = new DefaultListModel();
        JList sessions = new JList(modelSessions);
        sessions.setBounds(340, 70, this.screenWidth - 350, this.screenHeight - 130);
        this.add(sessions);
        
        JButton joinButton = new JButton("JOIN");
        joinButton.setFont(font);
        joinButton.setHorizontalAlignment(SwingConstants.LEFT);
        joinButton.setBounds(40, this.screenHeight - 150, 150, 60);
        joinButton.setContentAreaFilled(false);
        this.add(joinButton);
        
        joinButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                joinButton.setFont(sizedFont);
                joinButton.setText(">JOIN");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                joinButton.setFont(font);
                joinButton.setText("JOIN");
            }
        });
        
        // TODO kan pas echt gaan werken wanneer RMI goed werkzaam is
        joinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setFont(font);
        refreshButton.setHorizontalAlignment(SwingConstants.LEFT);
        refreshButton.setBounds(40, this.screenHeight - 200, 240, 60);
        refreshButton.setContentAreaFilled(false);
        this.add(refreshButton);
        
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setFont(sizedFont);
                refreshButton.setText(">REFRESH");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setFont(font);
                refreshButton.setText("REFRESH");
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
        
        JButton backButton = new JButton("BACK");
        backButton.setFont(font);
        backButton.setHorizontalAlignment(SwingConstants.LEFT);
        backButton.setBounds(40, this.screenHeight - 100, 160, 60);
        backButton.setContentAreaFilled(false);
        this.add(backButton);
        
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setFont(sizedFont);
                backButton.setText(">BACK");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setFont(font);
                backButton.setText("BACK");
            }
        });
        
        backButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AllCopilotGUI.setPanel("menu", user, null);
            }
        });
        
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

        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        this.add(logoImage);
        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        this.add(bg);
    }
}
