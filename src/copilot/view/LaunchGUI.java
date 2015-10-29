package copilot.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
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
    private Clip clip;
    private Font font, sizedFont = null;
    private Image launchScreen;
    private AudioInputStream launchClick;
    
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

//        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
//        this.screenWidth = size.width;
//        this.screenHeight = size.height;
        
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
        try {
            this.launchScreen = ImageIO.read(this.getClass().getClassLoader().getResource("launch_screen_copilot.png"));
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            
            URL launch = this.getClass().getResource("/sounds/click.wav");
            this.launchClick = AudioSystem.getAudioInputStream(launch);

            this.clip = AudioSystem.getClip();
            this.clip.open(launchClick);
            
        } catch (FontFormatException | IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(LaunchGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.font = this.font.deriveFont(Font.PLAIN, 30);
        this.sizedFont = this.font.deriveFont(Font.PLAIN, 32);

        JFrame frame = new JFrame("CO-Pilot launch screen");
        frame.setSize(815, 530);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JButton launchButton = new JButton("LAUNCH");
        launchButton.setFont(font);
        launchButton.setBounds(290, 10, 240, 50);
        panel.add(launchButton);

        
        JLabel bg = new JLabel(new ImageIcon(launchScreen));
        bg.setBounds(0, 0, 800, 500);
        panel.add(bg);
        

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clip.start();
                JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                LoginGUI login = new LoginGUI();
                frameToClose.dispose();
            }
        });
    }
}
