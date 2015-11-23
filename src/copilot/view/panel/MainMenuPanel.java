package copilot.view.panel;

import copilot.controller.GUIController;
import copilot.domain.GameAdministration;
import copilot.domain.Session;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author IndyGames
 */
public class MainMenuPanel extends JPanel {

    private final Font font, sizedFont;
    private final Image screen, logo;
    private final int screenWidth, screenHeight;

    /**
     * Initializes an instance of the MainMenuGUI
     *
     * @param userLoggedIn the user logged in
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param font the smallest font used
     * @param sizedFont the larger font used
     * @param screen the background image
     * @param logo the logo image
     */
    public MainMenuPanel(User userLoggedIn, int screenWidth, int screenHeight, Font font, Font sizedFont, Image screen, Image logo) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.font = font;
        this.sizedFont = sizedFont;
        this.screen = screen;
        this.logo = logo;

//        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // TODO
        this.placeComponents(userLoggedIn);
    }

    /**
     * used to place all the components to the panel
     *
     * @param user the user logged in
     */
    private void placeComponents(User user) {
        this.setLayout(null);

        // add a logout button and its listeners
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

        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                logoutButton.setFont(sizedFont);
                logoutButton.setText(">LOGOUT");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                logoutButton.setFont(font);
                logoutButton.setText("LOGOUT");
            }
        });

        // add a join button and its listeners
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

        joinButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                joinButton.setFont(sizedFont);
                joinButton.setText(">JOIN");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                joinButton.setFont(font);
                joinButton.setText("JOIN");
            }
        });

        // add a host button and its listeners
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

        hostButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                hostButton.setFont(sizedFont);
                hostButton.setText(">HOST");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                hostButton.setFont(font);
                hostButton.setText("HOST");
            }
        });

        // add a settings button and its listeners
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

        settingsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                settingsButton.setFont(sizedFont);
                settingsButton.setText(">SETTINGS");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                settingsButton.setFont(font);
                settingsButton.setText("SETTINGS");
            }
        });

        // add a credits button and its listeners
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

        creditsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                creditsButton.setFont(sizedFont);
                creditsButton.setText(">CREDITS");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                creditsButton.setFont(font);
                creditsButton.setText("CREDITS");
            }
        });

        // add a singleplayer button and its listeners
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
            AllCopilotGUI.setPanel("game", user, null);
        });

        singleplayerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                singleplayerButton.setFont(sizedFont);
                singleplayerButton.setText(">SINGLEPLAYER");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                singleplayerButton.setFont(font);
                singleplayerButton.setText("SINGLEPLAYER");
            }
        });

        // add the logo
        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        this.add(logoImage);

        // add the background
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        this.add(bg);
    }
}
