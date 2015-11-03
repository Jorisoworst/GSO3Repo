/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view.frame;

import copilot.controller.GUIController;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author IndyGames
 */
public class CreditsGUI extends JPanel {
    
    private Font font, sizedFont;
    private Image screen, logo;
    private int screenWidth, screenHeight;
    
    public CreditsGUI(User userLoggedIn, int screenWidth, int screenHeight, Font font, Font sizedFont, Image screen, Image logo) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.font = font;
        this.sizedFont = sizedFont;
        this.screen = screen;
        this.logo = logo;
        placeComponents(userLoggedIn);
    }
    
    private void placeComponents(User user) {
        this.setLayout(null); 
        
        JButton backButton = new JButton("BACK");
        backButton.setFont(font);
        backButton.setBounds(40, this.screenHeight - 60, 160, 40);
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
                GUIController.playClick();
                AllCopilotGUI.setPanel("menu", user, null);
            }
        });
        
        JLabel gameBroughtLabel = new JLabel("MADE BY");
        gameBroughtLabel.setBounds(this.screenWidth / 2 - 68, 180, 300, 80);
        gameBroughtLabel.setFont(sizedFont);
        this.add(gameBroughtLabel);
        
        JLabel indyLabel = new JLabel("INDY", SwingConstants.CENTER);
        indyLabel.setBounds(gameBroughtLabel.getX(), gameBroughtLabel.getY() + 55, 150, 150);
        indyLabel.setFont(font);
        
        this.add(indyLabel);
        
        JLabel jonneLabel = new JLabel("JONNE", SwingConstants.CENTER);
        jonneLabel.setBounds(gameBroughtLabel.getX(), indyLabel.getY() + 55, 150, 150);
        jonneLabel.setFont(font);
        this.add(jonneLabel);
        
        JLabel jorisLabel = new JLabel("JORIS", SwingConstants.CENTER);
        jorisLabel.setBounds(gameBroughtLabel.getX(), jonneLabel.getY() + 55, 150, 150);
        jorisLabel.setFont(font);
        this.add(jorisLabel);
  
        JLabel markLabel = new JLabel("MARK", SwingConstants.CENTER);
        markLabel.setBounds(gameBroughtLabel.getX(), jorisLabel.getY() + 55, 150, 150);
        markLabel.setFont(font);
        this.add(markLabel);

        JLabel nielsLabel = new JLabel("NIELS", SwingConstants.CENTER);
        nielsLabel.setBounds(gameBroughtLabel.getX(), markLabel.getY() + 55, 150, 150);
        nielsLabel.setFont(font);
        this.add(nielsLabel);
        
        JLabel ruudLabel = new JLabel("RUUD", SwingConstants.CENTER);
        ruudLabel.setBounds(gameBroughtLabel.getX(), nielsLabel.getY() + 55, 150, 150);
        ruudLabel.setFont(font);
        this.add(ruudLabel);
        
        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        this.add(logoImage);
        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        this.add(bg);
        
    }
}
