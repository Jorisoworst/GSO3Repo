package copilot.view;

import copilot.controller.GUIController;
import copilot.domain.GameAdministration;
import copilot.domain.Player;
import copilot.domain.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author IndyGames
 */
public class LoginGUI {
    private final Font font, sizedFont, sizedFont2;
    private Image screen, logo;
    private int screenWidth, screenHeight;
    
    public LoginGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            GUIController.showExceptionError(ex.toString());
        }
        
        GUIController.playBackgroundMusic();
        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = size.width;
        this.screenHeight = size.height;
        
        JFrame frame = new JFrame("CO-Pilot Login Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);

        try {
            this.screen = ImageIO.read(this.getClass().getClassLoader().getResource("bg.png"));
            this.screen = this.screen.getScaledInstance(this.screenWidth, this.screenHeight, 1);
            this.logo = ImageIO.read(this.getClass().getClassLoader().getResource("logo.png"));
            this.logo = this.logo.getScaledInstance(158, 122, 1);
        } catch (IOException ex) {
            GUIController.showExceptionError(ex.toString());
        }
        
        this.font = GUIController.loadFont(10);
        this.sizedFont = GUIController.loadFont(12);
        this.sizedFont2 = GUIController.loadFont(30);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50, this.screenHeight - (this.screenHeight / 2), 100, 25);
        userLabel.setFont(this.font);
        panel.add(userLabel);

        
        JTextField userText = new JTextField(20);
        userText.setBounds(50, userLabel.getY() + 25, 160, 25);
        userText.setFont(this.sizedFont);
        panel.add(userText);

        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, userText.getY() + 25, 100, 25);
        passwordLabel.setFont(font);
        panel.add(passwordLabel);

        
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(50, passwordLabel.getY() + 25, 160, 25);
        passwordText.setFont(this.sizedFont);
        panel.add(passwordText);

        
        
        JButton loginButton = new JButton("login");
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.setBounds(50, passwordText.getY() + 30, 160, 25);
        //loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setFont(this.sizedFont);
        panel.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GUIController.playClick();
                GameAdministration admin = GameAdministration.getInstance();
                // HAS TO BE admin.getDatabaseState()
                if (true) {
                    boolean login = admin.login(userText.getText(), Arrays.toString(passwordText.getPassword()));
                    
                    // HAS TO BE login
                    if (true) {
                        User user = admin.getUser(userText.getText());
                        JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);
                        MainMenuGUI mainMenuGUI = new MainMenuGUI(user);
                        frameToClose.dispose();
                    } else {
                        passwordText.setText(null);
                        JOptionPane.showMessageDialog(null, "Your username and/or password is not correct, try again or create an account", "ALERT", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "The Database connection could not be initialized, please check your network connection", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setText(">login");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setText("login");
            }
        });
        
        JButton registerButton = new JButton("register");
        registerButton.setHorizontalAlignment(SwingConstants.CENTER);
        registerButton.setBounds(50, loginButton.getY() + 30, 160, 25);
        //registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setFont(this.sizedFont);
        panel.add(registerButton);
        
        registerButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            
            try {
                if (userText.getText().isEmpty() ||
                        passwordText.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "please fill in your username and/or password to register");
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
                            
                            if (admin.getDatabaseState()) {
                                admin.addUser(user);
                                JOptionPane.showMessageDialog(null, "Your account has been created, you can now log in with your information", "USER CREATED", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "The Database connection could not be initialized, please check your network connection", "ALERT", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            GUIController.showExceptionError(ex.toString());
                        }
                    }
                }
            } catch (HeadlessException | ParseException ex) {
                GUIController.showExceptionError(ex.toString());
            }
        });

        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setText(">register");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setText("register");
            }
        });

        
        JButton quitButton = new JButton("QUIT");
        quitButton.setBounds(this.screenWidth - 250, 10, 250, 100);
        quitButton.setContentAreaFilled(false);
        quitButton.setFocusPainted(false);
        quitButton.setFont(this.sizedFont2);
        panel.add(quitButton);
        
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        quitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                quitButton.setText(">QUIT");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                quitButton.setText("QUIT");
            }
        });

        
        JLabel backLogin = new JLabel();
        backLogin.setBounds(userLabel.getX() - 10, userLabel.getY() - 10, 200, 200);
        backLogin.setOpaque(true);
        backLogin.setBackground(Color.WHITE);
        panel.add(backLogin);

        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        panel.add(logoImage);
        
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        panel.add(bg);
    }
}
