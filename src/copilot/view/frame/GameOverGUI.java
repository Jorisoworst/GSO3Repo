package copilot.view.frame;

import copilot.controller.GUIController;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author IndyGames
 */
public class GameOverGUI extends JPanel {
    private final int score;
    private final Font font;
    private final int screenWidth, screenHeight;

    /**
     * Initializes an instance of the GameOverGUI
     * @param userLoggedIn the user logged in
     * @param score the score achieved in the game
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param font the font used
     */
    public GameOverGUI(User userLoggedIn, int score, int screenWidth, int screenHeight, Font font) {
        
        this.score = score;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.font = font;
        
        this.setLayout(new BorderLayout());
        this.placeComponents(userLoggedIn);
    }

    private void placeComponents(User user) {
        
        this.setLayout(new BorderLayout());
        
        // add the gameOver label
        JLabel gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setLayout(new BorderLayout());
        gameOverLabel.setPreferredSize(new Dimension(this.screenWidth, (this.screenHeight / 7) * 3));
        gameOverLabel.setForeground(Color.WHITE);
        gameOverLabel.setFont(this.font);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(gameOverLabel, BorderLayout.NORTH);
        
        // add the score label
        JLabel scoreLabel = new JLabel("Score: " + this.score);
        scoreLabel.setLayout(new BorderLayout());
        scoreLabel.setPreferredSize(new Dimension(this.screenWidth, (this.screenHeight / 7) * 4));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(this.font);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(scoreLabel, BorderLayout.CENTER);

        // add the gameOver button and its listeners
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