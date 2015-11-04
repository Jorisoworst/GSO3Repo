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
        if (spriteSheet == null) {
            throw new IllegalArgumentException("No sprite sheet set!");
        }

        if (tileSize <= 0) {
            throw new IllegalArgumentException("Value of tile size too low!");
        }

        this.spriteSheet = spriteSheet;
        this.tileSize = tileSize;
    }

    public Image getSprite(int xGrid, int yGrid) {
        if (this.spriteSheet == null) {
            throw new IllegalArgumentException("No sprite sheet set!");
        }

        return this.spriteSheet.getSubimage(xGrid * this.tileSize, yGrid * this.tileSize, this.tileSize, this.tileSize);
    }
}
