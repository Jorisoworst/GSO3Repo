package copilot.view.panel;

import copilot.domain.Session;
import copilot.domain.User;
import copilot.view.gui.AllCopilotGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author IndyGames
 */
public class SessionPanel extends JPanel {

    /**
     * Initializes an instance of the SessionGUI
     *
     * @param session the session which is the gui for
     * @param userLoggedIn the user logged in
     */
    public SessionPanel(Session session, User userLoggedIn) {
        this.placeComponents(session, userLoggedIn);
    }

    /**
     * used to place all the components to the panel
     *
     * @param user the user logged in
     * @param session the session that has to be given to the game later
     * @param user the user logged in
     */
    private void placeComponents(Session session, User user) {
        this.setLayout(null);

        // add a backbutton and its listeners
        JButton backButton = new JButton("back");
        backButton.setBounds(610, 10, 160, 50);
        this.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllCopilotGUI.setPanel("lobby", user, null);
            }
        });
    }
}
