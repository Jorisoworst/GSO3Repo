package copilot.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LaunchGUI {
    private Clip clickClip;
    private Font font;
    private Image launchScreen;
    private AudioInputStream click;
    
    protected int screenWidth, screenHeight;
    
    public static void main(String[] args) {
        LaunchGUI l = new LaunchGUI();
    }

    public LaunchGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;
        
        JFrame frame = new JFrame("CO-Pilot Launch Screen");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        try {
            this.launchScreen = ImageIO.read(this.getClass().getClassLoader().getResource("launch_screen_copilot.png"));
            this.launchScreen = this.launchScreen.getScaledInstance(this.screenWidth, this.screenHeight, 1);
            
            // load font
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, this.screenHeight / 25);
            
            // load sound
            URL clickURL = this.getClass().getResource("/sounds/click.wav");
            this.click = AudioSystem.getAudioInputStream(clickURL);
            this.clickClip = AudioSystem.getClip();
            this.clickClip.open(click);
        } catch (FontFormatException | IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(LaunchGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton launchButton = new JButton("LAUNCH");
        launchButton.setFont(font);
        launchButton.setBounds(this.screenWidth / 2 - this.screenWidth / 5 / 2, this.screenHeight / 36, this.screenWidth / 5, this.screenHeight / 9);
        panel.add(launchButton);
        
        JLabel bg = new JLabel(new ImageIcon(launchScreen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickClip.start();
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                LoginGUI login = new LoginGUI();
                frameToClose.dispose();
            }
        });
    }
}
