package copilot.view.frame;

import copilot.controller.GUIController;
import copilot.domain.GameAdministration;
import copilot.domain.Player;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author IndyGames
 */
public class LoginGUI extends JPanel {

    private final int screenHeight, screenWidth;
    private final Font font, sizedFont, sizedFont2;
    private final Image screen, logo;

    /**
     * Initializes an instance of the LoginGUI Panel
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param font the smallest font used
     * @param sizedFont the larger font used
     * @param sizedFont2 the largest font used
     * @param screen the background image
     * @param logo the logo image
     */
    public LoginGUI(int screenHeight, int screenWidth, Font font, Font sizedFont, Font sizedFont2, Image screen, Image logo) {

        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.font = font;
        this.sizedFont = sizedFont;
        this.sizedFont2 = sizedFont2;
        this.screen = screen;
        this.logo = logo;

        placeComponents();
    }

    /**
     * used to place all the components to the panel
     */
    private void placeComponents() {
        this.setLayout(null);

        // add a username label
        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50, this.screenHeight - (this.screenHeight / 2), 100, 25);
        userLabel.setFont(this.font);
        this.add(userLabel);

        // add a username textfield
        JTextField userText = new JTextField(20);
        userText.setBounds(50, userLabel.getY() + 25, 160, 25);
        userText.setFont(this.sizedFont);
        this.add(userText);

        // add a password label
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, userText.getY() + 25, 100, 25);
        passwordLabel.setFont(font);
        this.add(passwordLabel);

        // add a password field
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(50, passwordLabel.getY() + 25, 160, 25);
        passwordText.setFont(this.sizedFont);
        this.add(passwordText);

        // add a login button and its listeners
        JButton loginButton = new JButton("login");
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);
        loginButton.setBounds(50, passwordText.getY() + 30, 160, 25);
        loginButton.setFocusPainted(false);
        loginButton.setFont(this.sizedFont);
        this.add(loginButton);

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                GUIController.playClick();
                GameAdministration admin = GameAdministration.getInstance();

                // check the database connection
                if (admin.getDatabaseState()) {

                    // check the login and login or give a message
                    boolean login = admin.login(userText.getText(), Arrays.toString(passwordText.getPassword()));

                    if (login) {
                        User user = admin.getUser(userText.getText());
                        AllCopilotGUI.setPanel("menu", user, null);
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

        // add a register button and its listeners
        JButton registerButton = new JButton("register");
        registerButton.setHorizontalAlignment(SwingConstants.CENTER);
        registerButton.setBounds(50, loginButton.getY() + 30, 160, 25);
        registerButton.setFocusPainted(false);
        registerButton.setFont(this.sizedFont);
        this.add(registerButton);

        registerButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();

            try {
                // check the fields and try to register
                if (userText.getText().isEmpty()
                        || passwordText.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "please fill in your username and/or password to register");
                } else {
                    String dateAsString = JOptionPane.showInputDialog("please insert your birthday with the following format: yyyy-mm-dd");
                    if (dateAsString != null) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        format.setLenient(false);
                        Date date = format.parse(dateAsString);
                        Calendar birthday = Calendar.getInstance();
                        birthday.setTime(date);

                        // try to create a new player object and add it to the database and the admin
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

        // add a quit button and its listeners
        JButton quitButton = new JButton("QUIT");
        quitButton.setBounds(this.screenWidth - 250, 10, 250, 100);
        quitButton.setContentAreaFilled(false);
        quitButton.setFocusPainted(false);
        quitButton.setFont(this.sizedFont2);
        this.add(quitButton);

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

        // add a back label
        JLabel backLogin = new JLabel();
        backLogin.setBounds(userLabel.getX() - 10, userLabel.getY() - 10, 200, 200);
        backLogin.setOpaque(true);
        backLogin.setBackground(Color.WHITE);
        this.add(backLogin);

        // add the logo
        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        this.add(logoImage);

        // add the background
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        this.add(bg);
    }
}
