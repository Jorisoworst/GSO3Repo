/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import static copilot.view.CopilotGUI.FULLSCREEN;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Joris
 */
public class GameOverGUI extends JFrame {

    private JPanel contentPane;
    private String gameOverText;
    private JLabel gameOverLabel;

    public GameOverGUI() {
        super("CoPilot - Game Over");
        this.createGUI();
        this.setContentPane(this.contentPane);
        this.setPreferredSize(new Dimension(800, 600));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.gameOverText = "Game Over";
    }

    public void createGUI() {
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension size;

        if (FULLSCREEN) {
            size = Toolkit.getDefaultToolkit().getScreenSize();
        } else {
            size = new Dimension(800, 600);
        }
        
        this.contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                
                
                gameOverLabel = new JLabel(gameOverText);
                gameOverLabel.setForeground(Color.WHITE);

                gameOverLabel.setLocation(((this.getWidth() / 2) - (gameOverLabel.getWidth() / 2)), ((this.getHeight() / 2) -  (gameOverLabel.getHeight() / 2)));
                contentPane.add((gameOverLabel));


//                g.setColor(Color.WHITE);
//                g.drawString(gameOverText, this.getWidth() / 2 - (gameOverText.length()), this.getHeight() / 2);
            }
        };
    }
}
