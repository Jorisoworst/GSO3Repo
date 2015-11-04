package copilot.domain;

import java.awt.Image;

/**
 * @author IndyGames
 */
public class Gun extends AirplanePart {

    private final int clipSize;
    private final int reloadSpeed;
    private final int fireRate;

    /**
     * Initialize an instance of the Gun class which extends ArplanePart
     *
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     * @param clipSize the clipSize, must be greater than 0
     * @param reloadSpeed the reloadSpeed, must be greater than 0
     * @param fireRate the fireRate, must be greater than 0
     */
    public Gun(Image image, Airplane airplane, User user, int clipSize, int reloadSpeed, int fireRate) {
        super(image, airplane, user);

        if (clipSize <= 0) {
            throw new IllegalArgumentException("Value of clip size too low!");
        } else if (reloadSpeed <= 0) {
            throw new IllegalArgumentException("Value of reload speed too low!");
        } else if (fireRate <= 0) {
            throw new IllegalArgumentException("Value of fire rate too low!");
        }

        this.clipSize = clipSize;
        this.reloadSpeed = reloadSpeed;
        this.fireRate = fireRate;
    }

    /**
     * @return the clipSize
     */
    public int getClipSize() {
        return this.clipSize;
    }

    /**
     * @return the reloadSpeed
     */
    public int getReloadSpeed() {
        return this.reloadSpeed;
    }

    /**
     * @return the fireRate
     */
    public int getFireRate() {
        return this.fireRate;
    }
}
