/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.User;
import java.awt.CardLayout;
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
    private static Font font, sizedFont, sizedFont2, fontExtraSmall;
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

    public static void setPanel(String name, Object extraInformation) {
        switch (name) {
            case "login":
                JPanel panelLogin = new LoginGUI(screenHeight, screenWidth, font, sizedFont, sizedFont2, screen, logo);
                panel.add(panelLogin, "login");
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                layout.show(frame.getContentPane(), "login");
                break;
                
            case "menu":
                JPanel panelMenu = new MainMenuGUI((User)extraInformation, screenWidth, screenHeight, font, sizedFont, screen, logo);
                panel.add(panelMenu, "menu");
                layout.show(frame.getContentPane(), "menu");
                break;
                
            case "lobby":
                break;
                
            case "session":
                break;
                
            case "settings":
                break;
                
            case "credits":
                break;
                
            case "game":
                JPanel panelGame = new CopilotGUI(screenWidth, screenHeight);
                panel.add(panelGame, "game");
                layout.show(frame.getContentPane(), "game");
                break;
                
            case "gameover":
                break;
        }
    }
}
