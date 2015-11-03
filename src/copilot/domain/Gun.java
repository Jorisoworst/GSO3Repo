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
     * @param image the image, may not be null
     * @param airplane the airplane, may not be null
     * @param user the user, may not be null
     * @param clipSize the clipSize
     * @param reloadSpeed the reloadSpeed
     * @param fireRate the fireRate
     */
    public Gun(Image image, Airplane airplane, User user, int clipSize, int reloadSpeed, int fireRate) {
        
        super(image, airplane, user);
        this.clipSize = clipSize;
        this.reloadSpeed = reloadSpeed;
        this.fireRate = fireRate;
    }

    /**
     * @return the clipSize
     */
    public int getClipSize() {
        return clipSize;
    }

    /**
     * @return the reloadSpeed
     */
    public int getReloadSpeed() {
        return reloadSpeed;
    }

    /**
     * @return the fireRate
     */
    public int getFireRate() {
        return fireRate;
    }
}
