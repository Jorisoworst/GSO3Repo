package copilot.view;

import copilot.domain.GameAdministration;
import copilot.domain.Player;
import copilot.domain.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IndyGames
 */
public class LoginGUI {
    private static Clip clickClip, clip;
    private static URL bgMusic;
    private Font font, sizedFont;
    private Image loginScreen;
    private AudioInputStream backMusic, click;

    protected int screenWidth, screenHeight;
    
    public LoginGUI() {
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
            this.loginScreen = ImageIO.read(this.getClass().getClassLoader().getResource("bg_menu.png"));
            this.loginScreen = this.loginScreen.getScaledInstance(this.screenWidth, this.screenHeight, 1);
            
            // load font
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            this.font = this.font.deriveFont(Font.PLAIN, this.screenHeight / 50);
            this.sizedFont = this.font.deriveFont(Font.PLAIN, this.screenHeight / 100);
            
            // load sound
            URL clickURL = this.getClass().getResource("/sounds/click.wav");
            this.click = AudioSystem.getAudioInputStream(clickURL);
            this.clickClip = AudioSystem.getClip();
            this.clickClip.open(click);
            
            URL bgMusic = this.getClass().getResource("/sounds/game_sound.wav");
            this.backMusic = AudioSystem.getAudioInputStream(bgMusic);
            LoginGUI.clip = AudioSystem.getClip();
            LoginGUI.clip.open(backMusic);
            LoginGUI.clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (FontFormatException | IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(this.screenWidth / 5, (this.screenHeight / 20) * 6, this.screenWidth / 5, this.screenHeight / 5);
        userLabel.setFont(font);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(this.screenWidth / 5, (this.screenHeight / 40) * 17, this.screenWidth / 10, this.screenHeight / 40);
        userText.setFont(sizedFont);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(this.screenWidth / 5, (this.screenHeight / 20) * 8, this.screenWidth / 5, this.screenHeight / 5);
        passwordLabel.setFont(font);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(this.screenWidth / 5, (this.screenHeight / 40) * 21, this.screenWidth / 10, this.screenHeight / 40);
        passwordText.setFont(sizedFont);
        panel.add(passwordText);

        JButton loginButton = new JButton("log in");
        loginButton.setBounds(this.screenWidth / 5 + this.screenWidth / 20, (this.screenHeight / 40) * 23, this.screenWidth / 20, this.screenHeight / 40);
        loginButton.setFont(sizedFont);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(this.screenWidth / 5, (this.screenHeight / 40) * 23, this.screenWidth / 20, this.screenHeight / 40);
        registerButton.setFont(sizedFont);
        panel.add(registerButton);

        JLabel backLogin = new JLabel();
        backLogin.setBounds((this.screenWidth / 50) * 9, (this.screenHeight / 50) * 18, this.screenWidth / 7, (this.screenHeight / 40) * 12);
        backLogin.setOpaque(true);
        backLogin.setForeground(Color.GRAY);
        panel.add(backLogin);

        JLabel bg = new JLabel(new ImageIcon(loginScreen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickClip.start();
                GameAdministration admin = GameAdministration.getInstance();

                // HAS TO BE admin.getDatabaseState()
                if (true) {
                    boolean login = admin.login(userText.getText(), Arrays.toString(passwordText.getPassword()));

                    // HAS TO BE login
                    if (true) {
                        LoginGUI.clip.stop();
                        User user = admin.getUser(userText.getText());
                        JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                        MainMenuGUI mainMenu = new MainMenuGUI(user);
                        frameToClose.dispose();

                    } else {
                        passwordText.setText(null);
                        JOptionPane.showMessageDialog(panel, "Your information was not correct, try again or create an account", "ALERT", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "The Database connection could not be initialized, please check your network connection", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickClip.start();

                try {
                    if (userText.getText().isEmpty() || passwordText.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "please fill in your username and/or password to register");
                    } else {

                        String dateAsString = JOptionPane.showInputDialog("please insert your birthday with the following format: yyyy-mm-dd");
                        if (dateAsString != null) {
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            format.setLenient(false);
                            Date date = format.parse(dateAsString);
                            Calendar birthday = Calendar.getInstance();
                            birthday.setTime(date);

                            try {
                                Player user = new Player(userText.getText(), Arrays.toString(passwordText.getPassword()), "" /*DisplayName*/, birthday); // TODO add DisplayName textfield in the gui
                                GameAdministration admin = GameAdministration.getInstance();
                                // HAS TO BE admin.getDatabaseState()
                                if (admin.getDatabaseState()) {
                                    admin.addUser(user);
                                    JOptionPane.showMessageDialog(panel, "Your account has been created, you can now log in with your information", "USER CREATED", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(panel, "The Database connection could not be initialized, please check your network connection", "ALERT", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(panel, "Something went wrong, please try again, ERROR: " + ex.getMessage(), "ALERT", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } catch (HeadlessException | ParseException ex) {
                    JOptionPane.showMessageDialog(panel, "Your information was not correct, try again and use the correct date format", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
