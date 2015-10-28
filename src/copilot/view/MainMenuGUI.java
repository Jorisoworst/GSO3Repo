/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.domain.GameAdministration;
import copilot.domain.Session;
import copilot.domain.User;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
 * @author NielsPrasing
 */
public class MainMenuGUI {

    private Clip clip;
    private Font font, sizedFont = null;
    private Image bgImage;
    protected int screenWidth, screenHeight;
    private AudioInputStream hover, click;

    public MainMenuGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;

        try {
            this.bgImage = ImageIO.read(this.getClass().getClassLoader().getResource("bg_menu.png"));
            this.bgImage = this.bgImage.getScaledInstance(this.screenWidth, this.screenHeight, 1);
            // load font
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, 30);
            this.sizedFont = this.font.deriveFont(Font.PLAIN, 32);

        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFrame frame = new JFrame("CO-Pilot Main Menu");
        frame.setSize(screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, userLoggedIn);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, User user) {

        panel.setLayout(null);

        JButton joinButton = new JButton("JOIN");
        joinButton.setHorizontalAlignment(SwingConstants.LEFT);
        joinButton.setBounds(40, screenHeight - 300, 240, 50);
        joinButton.setFont(font);
        joinButton.setContentAreaFilled(false);
        panel.add(joinButton);

        joinButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                joinButton.setFont(sizedFont);
                joinButton.setText(">JOIN");
                loadSound();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                joinButton.setFont(font);
                joinButton.setText("JOIN");
                stopSound();
            }
        });

        JButton hostButton = new JButton("HOST");
        hostButton.setHorizontalAlignment(SwingConstants.LEFT);
        hostButton.setBounds(40, screenHeight - 250, 240, 50);
        hostButton.setFont(font);
        hostButton.setContentAreaFilled(false);
        panel.add(hostButton);

        hostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hostButton.setFont(sizedFont);
                hostButton.setText(">HOST");
                loadSound();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                hostButton.setFont(font);
                hostButton.setText("HOST");
                stopSound();
            }
        });

        JButton settingsButton = new JButton("SETTINGS");
        settingsButton.setHorizontalAlignment(SwingConstants.LEFT);
        settingsButton.setBounds(40, screenHeight - 200, 240, 50);
        settingsButton.setFont(font);
        settingsButton.setContentAreaFilled(false);
        panel.add(settingsButton);

        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                settingsButton.setFont(sizedFont);
                settingsButton.setText(">SETTINGS");
                loadSound();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                settingsButton.setFont(font);
                settingsButton.setText("SETTINGS");
                stopSound();
            }
        });

        JButton creditsButton = new JButton("CREDITS");
        creditsButton.setHorizontalAlignment(SwingConstants.LEFT);
        creditsButton.setBounds(40, screenHeight - 150, 240, 50);
        creditsButton.setFont(font);
        creditsButton.setContentAreaFilled(false);
        panel.add(creditsButton);

        creditsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                creditsButton.setFont(sizedFont);
                creditsButton.setText(">CREDITS");
                loadSound();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                creditsButton.setFont(font);
                creditsButton.setText("CREDITS");
                stopSound();
            }
        });

        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setHorizontalAlignment(SwingConstants.RIGHT);
        logoutButton.setBounds(this.screenWidth - 280, 10, 240, 50);
        logoutButton.setFont(font);
        logoutButton.setContentAreaFilled(false);
        panel.add(logoutButton);

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setFont(sizedFont);
                logoutButton.setText(">LOGOUT");
                loadSound();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setFont(font);
                logoutButton.setText("LOGOUT");
                stopSound();
            }
        });

        JLabel bg = new JLabel(new ImageIcon(bgImage));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);

        joinButton.addActionListener(new ActionListener() {

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

        settingsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                SettingsGUI settings = new SettingsGUI(user);
                frameToClose.dispose();
            }
        });

        creditsButton.addActionListener(new ActionListener() {

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
                LaunchGUI logon = new LaunchGUI();
                frameToClose.dispose();
            }
        });
    }

    public void loadSound() {
        try {
            this.hover = AudioSystem.getAudioInputStream(this.getClass().getResource("/sounds/hover.wav"));
            this.clip = AudioSystem.getClip();
            this.clip.open(this.hover);
            this.clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stopSound() {
        try {
            this.clip.flush();
            this.clip.drain();
            this.clip.stop();
            this.clip.close();
            //this.hover.reset();
            this.hover.close();
            
            //this.hover = null;
        } catch (IOException ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
