/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Joris
 */
public class GameOverGUI extends JFrame {

    private JPanel contentPane;

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
    }

    public void createGUI() {
        this.contentPane = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());

                g.setColor(Color.WHITE);
                g.drawString("GAME OVER", this.getWidth() / 2, this.getHeight() / 2);
            }
        };
    }
}
