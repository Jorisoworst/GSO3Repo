package copilot.view.anim;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joris
 */
public class Animation {

    private final List<AnimationFrame> frames;      // Arraylist of AnimationFrames
    private final int animationDirection;           // animation direction (i.e counting forward or backward)
    private final int frameDelay;                   // frame delay 1-12 (You will have to play around with this)
    private final int totalFrames;                  // total amount of frames for your animation
    private boolean stopped;                        // has animations stopped
    private boolean looping;
    private int frameCount;                         // Counts ticks for change
    private int currentFrame;                       // animations current frame
    private int x;
    private int y;

    public Animation(Image[] frames, int frameDelay) {
        if (frames == null
                || frames.length == 0) {
            throw new IllegalArgumentException("No frames set!");
        }

        if (frameDelay <= 0) {
            frameDelay = 1;
        }

        this.frames = new ArrayList<>();
        this.animationDirection = 1;
        this.frameDelay = frameDelay;
        this.stopped = true;
        this.looping = false;
        this.frameCount = 0;
        this.currentFrame = 0;

        for (Image frame : frames) {
            this.addFrame(frame, frameDelay);
        }

        this.totalFrames = this.frames.size();
    }

    public void start() {
        if (this.isStopped() || this.totalFrames > 0) {
            this.stopped = false;
        }
    }

    public void stop() {
        if (this.totalFrames > 0) {
            this.stopped = true;
        }
    }

    public void restart() {
        if (this.totalFrames > 0) {
            this.stopped = false;
            this.currentFrame = 0;
        }
    }

    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    private void addFrame(Image frame, int duration) {
        if (frame == null) {
            throw new IllegalArgumentException("No frame set!");
        }

        if (duration <= 0) {
            duration = 1;
        }

        this.frames.add(new AnimationFrame(frame, duration));
        this.currentFrame = 0;
    }

    public Image getSprite() {
        return this.frames.get(this.currentFrame).getAnimationFrame();
    }

    public void update() {
        if (!this.isStopped()) {
            this.frameCount++;

            if (this.frameCount > this.frameDelay) {
                if (this.isLooping()) {
                    this.frameCount = 0;
                }

                this.currentFrame += this.animationDirection;

                if (this.currentFrame > this.totalFrames - 1) {
                    this.currentFrame = 0;
                } else if (this.currentFrame < 0) {
                    this.currentFrame = this.totalFrames - 1;
                }
            }

            if (!this.isLooping() && this.frameCount >= this.totalFrames) {
                this.stop();
            }
        }
    }

    /**
     * @return the x
     */
    public int getX() {
        return this.x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return this.y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the stopped
     */
    public boolean isStopped() {
        return this.stopped;
    }

    /**
     * @return the loops
     */
    public boolean isLooping() {
        return this.looping;
    }

    /**
     * @param looping the loops to set
     */
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}
