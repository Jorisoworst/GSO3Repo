package copilot.view;

import copilot.domain.GameAdministration;
import copilot.domain.Player;
import copilot.domain.User;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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

public class LogonGUI {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        }

        JFrame frame = new JFrame("CO-Pilot Login");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }
    
    public LogonGUI() {
        LogonGUI.main(null);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        JButton registerButton = new JButton("register");
        registerButton.setBounds(180, 80, 80, 25);
        panel.add(registerButton);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameAdministration admin = GameAdministration.getInstance();
                
                // HAS TO BE admin.getDatabaseState()
                if (true) {
                    boolean login = admin.login(userText.getText(), Arrays.toString(passwordText.getPassword()));

                    // HAS TO BE login
                    if(true) {
                        User user = admin.getUser(userText.getText());
                        JFrame frameToClose = (JFrame) SwingUtilities.getWindowAncestor(panel);  
                        MainMenuGUI mainMenu = new MainMenuGUI(user);                 
                        frameToClose.dispose();  

                    } else {
                        passwordText.setText(null);
                        JOptionPane.showMessageDialog(panel,"Your information was not correct, try again or create an account", "ALERT", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel,"The Database connection could not be initialized, please check your network connection", "ALERT", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
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
                                JOptionPane.showMessageDialog(panel,"Your account has been created, you can now log in with your information", "USER CREATED", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(panel,"The Database connection could not be initialized, please check your network connection", "ALERT", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel,"Something went wrong, please try again, ERROR: " + ex.getMessage(), "ALERT", JOptionPane.ERROR_MESSAGE);   
                        }
                    }

                } catch (HeadlessException | ParseException ex) {
                    JOptionPane.showMessageDialog(panel,"Your information was not correct, try again and use the correct date format", "ALERT", JOptionPane.ERROR_MESSAGE); 
                }
            }                    
        });
    }
}