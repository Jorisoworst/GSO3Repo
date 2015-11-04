package copilot.view.anim;

import java.awt.Image;

/**
 * @author Joris
 */
public class AnimationFrame {

    private Image frame;
    private int duration;

    public AnimationFrame(Image frame, int duration) {
        if (frame == null) {
            throw new IllegalArgumentException("No frame set!");
        }

        if (duration <= 0) {
            duration = 1;
        }

        this.frame = frame;
        this.duration = duration;
    }

    public Image getAnimationFrame() {
        return this.frame;
    }

    public void setAnimationFrame(Image frame) {
        if (frame == null) {
            throw new IllegalArgumentException("No frame set!");
        }

        this.frame = frame;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            duration = 1;
        }

        this.duration = duration;
    }
}
