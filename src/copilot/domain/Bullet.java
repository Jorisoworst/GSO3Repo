package copilot.domain;

import java.awt.Image;
import org.dyn4j.geometry.Vector2;

/**
 * @author IndyGames
 */
public class Bullet extends GameObject {
    private final Vector2 location;

    public Bullet(Image image, Vector2 location) {
       
        super(image);
        this.location = location;
    }

    public Vector2 getLocation() {
        return this.location;
    }
}
