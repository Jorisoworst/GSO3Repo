package copilot.view.panel;

import copilot.controller.GUIController;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author IndyGames
 */
public class SettingsPanel extends JPanel {

    private final Font font, sizedFont;
    private final Image screen, logo;
    private final int screenWidth, screenHeight;

    /**
     * Initializes an instance of the SettingsGUI Panel
     *
     * @param userLoggedIn the user logged in
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param font the smallest font used
     * @param sizedFont the larger font used
     * @param screen the background image
     * @param logo the logo image
     */
    public SettingsPanel(User userLoggedIn, int screenWidth, int screenHeight, Font font, Font sizedFont, Image screen, Image logo) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.font = font;
        this.sizedFont = sizedFont;
        this.screen = screen;
        this.logo = logo;

        this.placeComponents(userLoggedIn);
    }

    /**
     * used to place all the components to the panel
     *
     * @param user the user logged in
     */
    private void placeComponents(User user) {
        this.setLayout(null);

        // set the back button and its listeners
        JButton backButton = new JButton("BACK");
        backButton.setFont(font);
        backButton.setBounds(40, this.screenHeight - 60, 160, 40);
        backButton.setContentAreaFilled(false);
        this.add(backButton);

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                backButton.setFont(sizedFont);
                backButton.setText(">BACK");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                backButton.setFont(font);
                backButton.setText("BACK");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIController.playClick();
                AllCopilotGUI.setPanel("menu", user, null);
            }
        });

        // set the logo
        JLabel logoImage = new JLabel(new ImageIcon(this.logo));
        logoImage.setBounds(this.screenWidth / 2 - 75, 80, 158, 122);
        this.add(logoImage);

        // set the background
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, this.screenWidth, this.screenHeight);
        this.add(bg);
    }
}
