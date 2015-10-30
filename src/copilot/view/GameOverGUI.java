/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridBagLayout;
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
public class GameOverGUI extends JFrame {

    private final String gameOverText;
    private JPanel contentPane;
    private JLabel gameOverLabel, scoreLabel;
    private Font font;
    private int score;

    public GameOverGUI(int score) {
        super("CoPilot - Game Over");
        this.gameOverText = "Game Over";
        this.score = score;
        this.createGUI();
        this.setContentPane(this.contentPane);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLayout(new GridBagLayout());
    }

    public void createGUI() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, 64);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(LaunchGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.gameOverLabel = new JLabel(gameOverText);
        this.gameOverLabel.setForeground(Color.WHITE);
        this.gameOverLabel.setFont(this.font);
        this.gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.scoreLabel = new JLabel("Score: " + this.score);
        this.scoreLabel.setForeground(Color.WHITE);
        this.scoreLabel.setFont(this.font);
        this.scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
            }
        };

        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.add(this.gameOverLabel, BorderLayout.CENTER);
        this.contentPane.add(this.scoreLabel, BorderLayout.SOUTH);
    }
}
