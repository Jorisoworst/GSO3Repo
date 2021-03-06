package copilot.view.panel;

import copilot.controller.GUIController;
import copilot.view.gui.AllCopilotGUI;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author IndyGames
 */
public class LaunchPanel extends JPanel {

    private final Font font, fontExtraSmall;
    private final Image screen;

    /**
     * Initializes an instance of the LaunchGUI
     *
     * @param font the larger font used
     * @param fontExtraSmall the smallest font possible
     * @param screen the background image
     */
    public LaunchPanel(Font font, Font fontExtraSmall, Image screen) {
        this.font = font;
        this.fontExtraSmall = fontExtraSmall;
        this.screen = screen;

        this.placeComponents();
    }

    private void placeComponents() {
        this.setLayout(null);

        // add a launch button and its listeners
        JButton launchButton = new JButton("LAUNCH");
        launchButton.setBounds(290, 10, 244, 50);
        launchButton.setFocusPainted(false);
        launchButton.setFont(this.font);
        this.add(launchButton);

        launchButton.addActionListener((ActionEvent e) -> {
            GUIController.playClick();
            AllCopilotGUI.setPanel("login", null, null);
        });

        launchButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                launchButton.setText("LAUNCH");
                GUIController.playHover();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                launchButton.setText("LAUNCH");
            }
        });

        // add a copyright label
        JLabel copyRightLabel = new JLabel("Build 1.0000 | COPYRIGHT ALL RIGHTS RESERVED | INDYGAMES INC. ", SwingConstants.LEFT);
        copyRightLabel.setBounds(4, 476, 200, 30);
        copyRightLabel.setFont(fontExtraSmall);
        this.add(copyRightLabel);

        // add a background
        JLabel bg = new JLabel(new ImageIcon(this.screen));
        bg.setBounds(0, 0, 800, 500);
        this.add(bg);
    }
}
