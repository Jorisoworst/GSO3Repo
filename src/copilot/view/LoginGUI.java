package copilot.view;

import copilot.domain.GameAdministration;
import copilot.domain.Player;
import copilot.domain.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.HeadlessException;
import java.awt.Image;
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

    private static Clip clip;
    private static URL bgMusic;
    private Font font, sizedFont = null;
    private Image loginScreen;
    private AudioInputStream backMusic;

    public LoginGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
        try {
            this.loginScreen = ImageIO.read(this.getClass().getClassLoader().getResource("bg_menu.png"));
            this.loginScreen = this.loginScreen.getScaledInstance(815, 530, 1);
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
            // load sound
            URL bgMusic = this.getClass().getResource("/sounds/game_sound.wav");

            //URL hover = this.getClass().getResource("/sounds/game_sound.wav");
            //URL click = this.getClass().getResource("/sounds/click.wav");
            this.backMusic = AudioSystem.getAudioInputStream(bgMusic);

            LoginGUI.clip = AudioSystem.getClip();

            LoginGUI.clip.open(backMusic);
            LoginGUI.clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (FontFormatException | IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(LoginGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.font = this.font.deriveFont(Font.PLAIN, 15);
        this.sizedFont = this.font.deriveFont(Font.PLAIN, 32);

        JFrame frame = new JFrame("CO-Pilot Login");
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

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50, 225, 100, 25);
        userLabel.setFont(font);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(50, 250, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 285, 100, 25);
        passwordLabel.setFont(font);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(50, 310, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("log in");
        loginButton.setBounds(130, 350, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(50, 350, 80, 25);
        panel.add(registerButton);

        JLabel backLogin = new JLabel();
        backLogin.setBounds(40, 200, 200, 200);
        backLogin.setOpaque(true);
        backLogin.setForeground(Color.GRAY);
        panel.add(backLogin);

        JLabel bg = new JLabel(new ImageIcon(loginScreen));
        bg.setBounds(0, 0, 815, 530);
        panel.add(bg);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                                Player user = new Player(userText.getText(), Arrays.toString(passwordText.getPassword()), birthday);
                                GameAdministration admin = GameAdministration.getInstance();
                                // HAS TO BE admin.getDatabaseState()
                                if (true) {
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
