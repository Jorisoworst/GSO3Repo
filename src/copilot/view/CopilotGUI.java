/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.view;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

/**
 *
 * @author indyspaan
 */

public class CopilotGUI extends Application {

    Image player;
    Image background;
    
    public int screenwidth;
    public int screenheight;
    private World World;

    
    @Override
    public void start(Stage stage) throws Exception {
        //LoadImages
        background = new Image(getClass().getResourceAsStream("achtergrond.png"));
        player = new Image(getClass().getResourceAsStream("Plane.png"));
        
           screenwidth = 720;
           screenheight = 480;
        
        stage.setTitle("COPILOT");
        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene(theScene);

        Canvas canvas = new Canvas(screenwidth, screenheight);
        root.getChildren().add(canvas);
        

        GraphicsContext gc = canvas.getGraphicsContext2D();
        

        gc.drawImage(background, 0, 0);
        gc.drawImage(player, (screenwidth / 2) - (player.getWidth() / 2), screenheight / 2 - (player.getHeight() / 2));
        
        
        World = new World();

        stage.show();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
