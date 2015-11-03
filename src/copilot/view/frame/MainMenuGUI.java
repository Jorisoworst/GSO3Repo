/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view.frame;

import copilot.controller.GUIController;
import copilot.domain.GameAdministration;
import copilot.domain.Session;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author IndyGames
 */
public class MainMenuGUI extends JPanel {
    private Font font, sizedFont;
    private Image screen, logo;
    private int screenWidth, screenHeight;

    public MainMenuGUI(User userLoggedIn, int screenWidth, int screenHeight, Font font, Font sizedFont, Image screen, Image logo) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.font = font;
        this.sizedFont = sizedFont;
        this.screen = screen;
        this.logo = logo;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        
        placeComponents(userLoggedIn);
    }

    private void placeComponents(User user) {
        this.setLayout(null);
        
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setBounds(this.screenWidth - 280, 40, 240, 50);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(this.font);
        logoutButton.setHorizontalAlignment(SwingConstants.RIGHT);
        this.add(logoutButton);
        
        logoutButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            GUIController.stopBackgroundMusic();
            AllCopilotGUI.setPanel("login", null, null);
        });

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setFont(sizedFont);
                logoutButton.setText(">LOGOUT");
                GUIController.playHover();                
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setFont(font);
                logoutButton.setText("LOGOUT");
            }
        });
        
        
        JButton joinButton = new JButton("JOIN");
        joinButton.setBounds(40, this.screenHeight - 300, 240, 50);
        joinButton.setContentAreaFilled(false);
        joinButton.setFocusPainted(false);
        joinButton.setFont(this.font);
        joinButton.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(joinButton);        

        joinButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            AllCopilotGUI.setPanel("lobby", user, null);
        });
        
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

        JButton hostButton = new JButton("HOST");
        hostButton.setBounds(40, this.screenHeight - 250, 240, 50);
        hostButton.setContentAreaFilled(false);
        hostButton.setFocusPainted(false);
        hostButton.setFont(this.font);
        hostButton.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(hostButton);

        hostButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            Session session = GameAdministration.getInstance().createSession(user);
            session.addUser(user);
            GUIController.stopBackgroundMusic();
            AllCopilotGUI.setPanel("session", user, session);
        });
        
        hostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hostButton.setFont(sizedFont);
                hostButton.setText(">HOST");
                GUIController.playHover();  
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hostButton.setFont(font);
                hostButton.setText("HOST");
            }
        });

        
        JButton settingsButton = new JButton("SETTINGS");
        settingsButton.setBounds(40, this.screenHeight - 200, 240, 50);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setFont(this.font);
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(settingsButton);

        settingsButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            AllCopilotGUI.setPanel("settings", user, null);
        });

        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsButton.setFont(sizedFont);
                settingsButton.setText(">SETTINGS");
                GUIController.playHover();  
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsButton.setFont(font);
                settingsButton.setText("SETTINGS");
            }
        });
        
        JButton creditsButton = new JButton("CREDITS");
        creditsButton.setBounds(40, this.screenHeight - 150, 240, 50);
        creditsButton.setContentAreaFilled(false);
        creditsButton.setFocusPainted(false);
        creditsButton.setFont(this.font);
        creditsButton.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(creditsButton);

        creditsButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            AllCopilotGUI.setPanel("credits", null, null);
        });

        creditsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                creditsButton.setFont(sizedFont);
                creditsButton.setText(">CREDITS");
                GUIController.playHover();  
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                creditsButton.setFont(font);
                creditsButton.setText("CREDITS");
            }
        });
        
        JButton singleplayerButton = new JButton("SINGLEPLAYER");
        singleplayerButton.setBounds(40, this.screenHeight - 350, 540, 50);
        singleplayerButton.setContentAreaFilled(false);
        singleplayerButton.setFocusPainted(false);
        singleplayerButton.setFont(this.font);
        singleplayerButton.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(singleplayerButton);
        
        singleplayerButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            GUIController.stopBackgroundMusic();
            AllCopilotGUI.setPanel("game", null, null);
        });

        singleplayerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                singleplayerButton.setFont(sizedFont);
                singleplayerButton.setText(">SINGLEPLAYER");
                GUIController.playHover();   
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                singleplayerButton.setFont(font);
                singleplayerButton.setText("SINGLEPLAYER");
            }
        });
        
        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        this.add(logoImage);
        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        this.add(bg);
    }
}
