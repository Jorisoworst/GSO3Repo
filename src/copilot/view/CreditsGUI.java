/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.User;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class CreditsGUI {
    
    private Font font, sizedFont;
    private Image screen, logo;
    private int screenWidth, screenHeight;
    
    public CreditsGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }
        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;
        
        JFrame frame = new JFrame("CO-Pilot Credits");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        try {
            this.screen = ImageIO.read(this.getClass().getClassLoader().getResource("bg.png"));
            this.screen = this.screen.getScaledInstance(this.screenWidth, this.screenHeight, 1);
            this.logo = ImageIO.read(this.getClass().getClassLoader().getResource("logo.png"));
            this.logo = this.logo.getScaledInstance(158, 122, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.font = GUIController.loadFont(30);
        this.sizedFont = GUIController.loadFont(32);
        
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel, userLoggedIn);

        frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel, User user) {
        
        panel.setLayout(null); 
        
        JButton backButton = new JButton("BACK");
        backButton.setFont(font);
        backButton.setBounds(40, this.screenHeight - 60, 160, 40);
        backButton.setContentAreaFilled(false);
        panel.add(backButton);
        
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
                GUIController.playClick();
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);  
                //MainMenuGUI mainMenu = new MainMenuGUI(user);          
                frameToClose.dispose(); 
            }
        });
        
        JLabel gameBroughtLabel = new JLabel("MADE BY");
        gameBroughtLabel.setBounds(this.screenWidth / 2 - 68, 180, 300, 80);
        gameBroughtLabel.setFont(sizedFont);
        panel.add(gameBroughtLabel);
        
        JLabel indyLabel = new JLabel("INDY", SwingConstants.CENTER);
        indyLabel.setBounds(gameBroughtLabel.getX(), gameBroughtLabel.getY() + 55, 150, 150);
        indyLabel.setFont(font);
        
        panel.add(indyLabel);
        
        JLabel jonneLabel = new JLabel("JONNE", SwingConstants.CENTER);
        jonneLabel.setBounds(gameBroughtLabel.getX(), indyLabel.getY() + 55, 150, 150);
        jonneLabel.setFont(font);
        panel.add(jonneLabel);
        
        JLabel jorisLabel = new JLabel("JORIS", SwingConstants.CENTER);
        jorisLabel.setBounds(gameBroughtLabel.getX(), jonneLabel.getY() + 55, 150, 150);
        jorisLabel.setFont(font);
        panel.add(jorisLabel);
  
        JLabel markLabel = new JLabel("MARK", SwingConstants.CENTER);
        markLabel.setBounds(gameBroughtLabel.getX(), jorisLabel.getY() + 55, 150, 150);
        markLabel.setFont(font);
        panel.add(markLabel);

        JLabel nielsLabel = new JLabel("NIELS", SwingConstants.CENTER);
        nielsLabel.setBounds(gameBroughtLabel.getX(), markLabel.getY() + 55, 150, 150);
        nielsLabel.setFont(font);
        panel.add(nielsLabel);
        
        JLabel ruudLabel = new JLabel("RUUD", SwingConstants.CENTER);
        ruudLabel.setBounds(gameBroughtLabel.getX(), nielsLabel.getY() + 55, 150, 150);
        ruudLabel.setFont(font);
        panel.add(ruudLabel);
        
        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        panel.add(logoImage);
        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);
        
    }
}
