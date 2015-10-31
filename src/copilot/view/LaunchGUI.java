package copilot.view;

import copilot.controller.GUIController;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LaunchGUI {
    private final Font font;
    private Image screen;
    private int screenWidth, screenHeight;
    
    public static void main(String[] args) {
        LaunchGUI launchGUI = new LaunchGUI();
    }

    public LaunchGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            GUIController.showExceptionError(ex.toString());
        }
        
        this.screenWidth = 800;
        this.screenHeight = 500;
        
        JFrame frame = new JFrame("CO-Pilot Launch Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(this.screenWidth, this.screenHeight);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
                
        try {
            this.screen = ImageIO.read(this.getClass().getClassLoader().getResource("launch_screen_copilot.png"));
            this.screen = this.screen.getScaledInstance(this.screenWidth, this.screenHeight, 1);
        } catch (IOException ex) {
            GUIController.showExceptionError(ex.toString());
        }
        
        this.font = GUIController.loadFont(this.screenHeight / 25);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        
        JButton launchButton = new JButton("LAUNCH");
        launchButton.setBounds(this.screenWidth / 2 - this.screenWidth / 5 / 2, this.screenHeight / 36, this.screenWidth / 5, this.screenHeight / 9);
//        launchButton.setContentAreaFilled(false);
        launchButton.setFocusPainted(false);
        launchButton.setFont(this.font);
        panel.add(launchButton);

        launchButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
            LoginGUI loginGUI = new LoginGUI();
            frameToClose.dispose();
        });

        launchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                launchButton.setText(">LAUNCH");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                launchButton.setText("LAUNCH");
            }
        });
        
        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);
    }
}
