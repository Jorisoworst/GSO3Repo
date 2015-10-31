/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.User;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IndyGames
 */
public class SettingsGUI {
    private Font font, sizedFont;
    
    public SettingsGUI(User userLoggedIn) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }
        
        JFrame frame = new JFrame("CO-Pilot Settings");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel bgLabel = new JLabel();
        try {
            Image bgImage = ImageIO.read(this.getClass().getClassLoader().getResource("bg_menu.png"));
            bgImage = bgImage.getScaledInstance(frame.getWidth(), frame.getHeight(), 1);
            bgLabel = new JLabel(new ImageIcon(bgImage));
            bgLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            
            // load font
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, 30);
            this.sizedFont = this.font.deriveFont(Font.PLAIN, 32);
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.add(bgLabel);
        frame.add(panel);
        
        placeComponents(panel, userLoggedIn);

        frame.setVisible(true);
    }
    
    private void placeComponents(JPanel panel, User user) {
        
        panel.setLayout(null); 
        
        JButton backButton = new JButton("back");
        backButton.setBounds(panel.getWidth() - 170, 10, 160, 50);
        panel.add(backButton);
        
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
