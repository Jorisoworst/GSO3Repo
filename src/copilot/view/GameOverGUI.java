/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author IndyGames
 */
public class GameOverGUI extends JFrame {

    private JPanel contentPane;
    private final String gameOverText;
    private JLabel gameOverLabel;

    public GameOverGUI(CopilotGUI copilotGUI) {
        super("CoPilot - Game Over");
        this.createGUI();
        this.setContentPane(this.contentPane);
        this.setPreferredSize(new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, java.awt.Toolkit.getDefaultToolkit().getScreenSize().height));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new GridBagLayout());
        
        this.gameOverText = "Game Over";
        
        copilotGUI.dispose();
    }

    public void createGUI() {
        this.contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                gameOverLabel = new JLabel(gameOverText);
                gameOverLabel.setForeground(Color.WHITE);
                
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
                try {
                    gameOverLabel.setFont(Font.createFont(Font.TRUETYPE_FONT, is));
                    gameOverLabel.setFont(new Font(gameOverLabel.getName(), Font.PLAIN, 64));
                } catch (FontFormatException | IOException ex) {
                    Logger.getLogger(LaunchGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                gameOverLabel.setLocation(((this.getWidth() / 2) - (gameOverLabel.getWidth() / 2)), ((this.getHeight() / 2) -  (gameOverLabel.getHeight() / 2)));
                this.add((gameOverLabel));
            }
        };
    }
}
