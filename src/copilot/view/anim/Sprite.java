package copilot.view.anim;

import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * @author Joris
 */
public class Sprite {

    private final BufferedImage spriteSheet;
    private final int tileSize;

    public Sprite(BufferedImage spriteSheet, int tileSize) {
        this.spriteSheet = spriteSheet;
        this.tileSize = tileSize;
    }

    public Image getSprite(int xGrid, int yGrid) {
        if (this.spriteSheet == null) {
            System.err.println("Invalid spriteSheet");
            throw new RuntimeException("Invalid spriteSheet");
        }

        return this.spriteSheet.getSubimage(xGrid * tileSize, yGrid * tileSize, tileSize, tileSize);
    }
}
