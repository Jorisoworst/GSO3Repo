/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author IndyGames
 */
public class GameOverGUI{
    private final String gameOverText;
    private final int score;
    private JFrame frame;
    private JPanel contentPane;
    private Font font;
    private int screenWidth, screenHeight;

    public GameOverGUI(int score) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;
        
        this.frame = new JFrame("CoPilot - Game Over");
        this.gameOverText = "Game Over";
        this.score = score;
        this.createGUI();
        this.frame.setContentPane(this.contentPane);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setUndecorated(true);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setLayout(new BorderLayout());
    }

    private void createGUI() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, 64);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(LaunchGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        JLabel gameOverLabel = new JLabel(gameOverText);
        gameOverLabel.setLayout(new BorderLayout());
        gameOverLabel.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight / 2));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(this.font);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel scoreLabel = new JLabel("Score: " + this.score);
        scoreLabel.setLayout(new BorderLayout());
        scoreLabel.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight / 2));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(this.font);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        };

        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.add(gameOverLabel, BorderLayout.NORTH);
        this.contentPane.add(scoreLabel, BorderLayout.SOUTH);
    }
}