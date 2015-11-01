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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IndyGames
 */
public class MainMenuGUI {
    private Font font, sizedFont;
    private Image screen;
    private int screenWidth, screenHeight;

    public MainMenuGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            GUIController.showExceptionError(ex.toString());
        }
        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;
        
        JFrame frame = new JFrame("CO-Pilot Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(this.screenWidth, this.screenHeight);        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        try {
            this.screen = ImageIO.read(this.getClass().getClassLoader().getResource("bg_menu.png"));
            this.screen = this.screen.getScaledInstance(this.screenWidth, this.screenHeight, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.font = GUIController.loadFont(this.screenHeight / 36);
        this.sizedFont = GUIController.loadFont(this.screenHeight / 34);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, userLoggedIn);
        
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, User user) {
        panel.setLayout(null);
        
        
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setBounds(this.screenWidth - 280, 40, 240, 50);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setFont(this.font);
        logoutButton.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(logoutButton);
        
        logoutButton.addActionListener((ActionEvent e) -> {
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
//            LaunchGUI launchGUI = new LaunchGUI();
            GUIController.stopBackgroundMusic();
            LoginGUI loginGUI = new LoginGUI();
            frameToClose.dispose();
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
        panel.add(joinButton);        

        joinButton.addActionListener((ActionEvent e) -> {
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
            LobbyGUI lobbyGUI = new LobbyGUI(user);
            GUIController.stopBackgroundMusic();
            frameToClose.dispose();
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
        panel.add(hostButton);

        hostButton.addActionListener((ActionEvent e) -> {
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
            Session session = GameAdministration.getInstance().createSession(user);
            session.addUser(user);
            SessionGUI sessionGUI = new SessionGUI(session, user);
            GUIController.stopBackgroundMusic();
            frameToClose.dispose();
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
        panel.add(settingsButton);

        settingsButton.addActionListener((ActionEvent e) -> {
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
            SettingsGUI settingsGUI = new SettingsGUI(user);
            frameToClose.dispose();
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
        panel.add(creditsButton);

        creditsButton.addActionListener((ActionEvent e) -> {
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
            CreditsGUI credits = new CreditsGUI(user);
            frameToClose.dispose();
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
        
        
        JButton singleplayerButton = new JButton("SINGLEPLAYER DEBUG");
        singleplayerButton.setBounds(this.screenWidth - 480, this.screenHeight - 150, 540, 50);
        singleplayerButton.setContentAreaFilled(false);
        singleplayerButton.setFocusPainted(false);
        singleplayerButton.setFont(this.font);
        singleplayerButton.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(singleplayerButton);
        
        singleplayerButton.addActionListener((ActionEvent e) -> {
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
            CopilotGUI game = new CopilotGUI();
            game.start();
            GUIController.stopBackgroundMusic();
            frameToClose.dispose();
        });

        singleplayerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                singleplayerButton.setFont(sizedFont);
                singleplayerButton.setText(">SINGLEPLAYER DEBUG");
                GUIController.playHover();   
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                singleplayerButton.setFont(font);
                singleplayerButton.setText("SINGLEPLAYER DEBUG");
            }
        });

        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);
    }
}
