package copilot.view.anim;

import java.awt.Image;

/**
 * @author Joris
 */
public class AnimationFrame {

    private Image frame;
    private int duration;

    public AnimationFrame(Image frame, int duration) {
        this.frame = frame;
        this.duration = duration;
    }

    public Image getAnimationFrame() {
        return this.frame;
    }

    public void setAnimationFrame(Image frame) {
        this.frame = frame;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
