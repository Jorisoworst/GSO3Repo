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
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author IndyGames
 */
public class GameOverGUI extends JPanel {
    private final String gameOverText;
    private final int score;
    private Font font;
    private int screenWidth, screenHeight;

    public GameOverGUI(int score, int screenWidth, int screenHeight) {
        this.gameOverText = "Game Over";
        this.score = score;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        this.setLayout(new BorderLayout());
        this.createGUI();
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
        
        this.setLayout(new BorderLayout());
        this.add(gameOverLabel, BorderLayout.NORTH);
        this.add(scoreLabel, BorderLayout.SOUTH);
    }
}