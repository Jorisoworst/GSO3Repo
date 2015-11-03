/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view.gui;

import copilot.view.frame.CopilotGUI;
import copilot.view.frame.LobbyGUI;
import copilot.view.frame.LaunchGUI;
import copilot.view.frame.CreditsGUI;
import copilot.view.frame.MainMenuGUI;
import copilot.view.frame.LoginGUI;
import copilot.view.frame.SettingsGUI;
import copilot.view.frame.GameOverGUI;
import copilot.view.frame.SessionGUI;
import copilot.controller.GUIController;
import copilot.domain.Session;
import copilot.domain.User;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author NielsPrasing
 */
public class AllCopilotGUI {

    private static int screenWidth, screenHeight, screenWidthLaunch, screenHeightLaunch;
    private static JFrame frame;
    private static JPanel panel;
    private static Image LaunchScreen, logo, screen;
    private static Font font, sizedFont, sizedFont2, sizedFont3, sizedFont4, sizedFont5, fontExtraSmall;
    private static CardLayout layout;

    public static void main(String[] args) {
        AllCopilotGUI gui = new AllCopilotGUI();
    }

    public AllCopilotGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            GUIController.showExceptionError(ex.toString());
        }

        GUIController.playBackgroundMusic();

        layout = new CardLayout();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = size.width;
        screenHeight = size.height;

        screenWidthLaunch = 805;
        screenHeightLaunch = 525;

        frame = new JFrame("CO-Pilot Launch");

        try {
            screen = ImageIO.read(this.getClass().getClassLoader().getResource("bg.png"));
            LaunchScreen = ImageIO.read(this.getClass().getClassLoader().getResource("launch_screen_copilot.png"));
            logo = ImageIO.read(this.getClass().getClassLoader().getResource("logo.png"));
            logo = logo.getScaledInstance(158, 122, 1);
        } catch (IOException ex) {
            GUIController.showExceptionError(ex.toString());
        }

        font = GUIController.loadFont(10);
        sizedFont = GUIController.loadFont(12);
        sizedFont2 = GUIController.loadFont(30);
        sizedFont3 = GUIController.loadFont(32);
        sizedFont4 = GUIController.loadFont(64);
        sizedFont5 = GUIController.loadFont(20);
        fontExtraSmall = GUIController.loadFont(5);

        JPanel panelLaunch = new LaunchGUI(sizedFont2, fontExtraSmall, LaunchScreen);
        panelLaunch.setPreferredSize(new Dimension(screenWidthLaunch, screenHeightLaunch));
        panel = new JPanel(layout);
        panel.add(panelLaunch, "launch");
        
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        layout.show(frame.getContentPane(), "launch");
    }

    public static void setPanel(String name, Object extraInformation, Object extraInformation2) {
        switch (name) {
            case "login":
                LoginGUI panelLogin = new LoginGUI(screenHeight, screenWidth, font, sizedFont, sizedFont2, screen, logo);
                panel.add(panelLogin, "login");
                
                if (!(frame.getExtendedState() == JFrame.MAXIMIZED_BOTH)) {
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.dispose();
                    frame.setUndecorated(true);
                    frame.setVisible(true);
                }
                layout.show(frame.getContentPane(), "login");
                break;
                
            case "menu":
                MainMenuGUI panelMenu = new MainMenuGUI((User)extraInformation, screenWidth, screenHeight, sizedFont2, sizedFont3, screen, logo);
                panel.add(panelMenu, "menu");
                layout.show(frame.getContentPane(), "menu");
                break;
                
            case "lobby":
                LobbyGUI panelLobby = new LobbyGUI((User)extraInformation, screenWidth, screenHeight, sizedFont2, sizedFont3, screen, logo);
                panel.add(panelLobby, "lobby");
                layout.show(frame.getContentPane(), "lobby");
                break;
                
            case "session":
                SessionGUI panelSession = new SessionGUI((Session)extraInformation2, (User)extraInformation);
                panel.add(panelSession, "session");
                layout.show(frame.getContentPane(), "session");
                break;
                
            case "settings":
                SettingsGUI panelSettings = new SettingsGUI((User)extraInformation, screenWidth, screenHeight, sizedFont2, sizedFont3, screen, logo);
                panel.add(panelSettings, "settings");
                layout.show(frame.getContentPane(), "settings");
                break;
                
            case "credits":
                CreditsGUI panelCredits = new CreditsGUI((User)extraInformation, screenWidth, screenHeight, sizedFont2, sizedFont3, screen, logo);
                panel.add(panelCredits, "credits");
                layout.show(frame.getContentPane(), "credits");
                break;
                
            case "game":
                CopilotGUI panelGame = new CopilotGUI(screenWidth, screenHeight, sizedFont5);
                panel.add(panelGame, "game");
                layout.show(frame.getContentPane(), "game");
                panelGame.start();
                break;
                
            case "gameover":
                GameOverGUI panelGameover = new GameOverGUI((User)extraInformation, (int)extraInformation2, screenWidth, screenHeight, sizedFont4);
                panelGameover.setBackground(Color.BLACK);
                panel.add(panelGameover, "gameover");
                layout.show(frame.getContentPane(), "gameover");
                break;
        }
    }
}
