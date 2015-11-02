/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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

    public GameOverGUI(User userLoggedIn, int score, int screenWidth, int screenHeight, Font font) {
        this.gameOverText = "Game Over";
        this.score = score;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.font = font;
        
        this.setLayout(new BorderLayout());
        this.createGUI(userLoggedIn);
    }

    private void createGUI(User user) {
        this.setLayout(new BorderLayout());

        
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setLayout(new BorderLayout());
        gameOverLabel.setPreferredSize(new Dimension(this.screenWidth, (this.screenHeight / 7) * 3));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(this.font);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(gameOverLabel, BorderLayout.NORTH);
        
        
        JLabel scoreLabel = new JLabel("Score: " + this.score);
        scoreLabel.setLayout(new BorderLayout());
        scoreLabel.setPreferredSize(new Dimension(this.screenWidth, (this.screenHeight / 7) * 4));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(this.font);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(scoreLabel, BorderLayout.CENTER);

        
        JButton gameOverButton = new JButton("Main Menu");
        gameOverButton.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverButton.setContentAreaFilled(false);
        gameOverButton.setFocusPainted(false);
        gameOverButton.setFont(this.font);
        gameOverButton.setForeground(Color.WHITE);
        this.add(gameOverButton, BorderLayout.SOUTH);
        
        gameOverButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            AllCopilotGUI.setPanel("menu", user, null);
        });

        gameOverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                gameOverButton.setText(">Main Menu");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                gameOverButton.setText("Main Menu");
            }
        });
    }
}