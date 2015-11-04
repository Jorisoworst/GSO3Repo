package copilot.controller;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 * @author IndyGames
 */
public final class GUIController {

    private static Clip backgroundClip, airplaneSound, gameSound;
    private static final Random random = new Random();
    private static final int min = 1;
    private static final int max = 5;
    private static int killCount, hitCount = 0;

    /**
     * Load the font for the game.
     *
     * @param size the size for the new font
     * @return the loaded font
     */
    public static Font loadFont(int size) {
        try {
            InputStream is = GUIController.class.getClassLoader().getResourceAsStream("fonts/Minecraftia-Regular.ttf");
            Font font;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException ex) {
            GUIController.showExceptionError(ex.toString());
            return null;
        }
    }

    /**
     * Load and play the click sound.
     */
    public static void playClick() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/click.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Load and play the hover sound.
     */
    public static void playHover() {
        try {
            Clip hover = AudioSystem.getClip();
            hover.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/hover.wav")));
            hover.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Show an error message with the given error.
     *
     * @param error the error that needs to be shown
     */
    public static void showExceptionError(String error) {
        JOptionPane.showMessageDialog(null, "Something went wrong, please try again, ERROR: " + error, "ALERT", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Load an loop the background music.
     */
    public static void playBackgroundMusic() {
        try {
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/main_song.wav")));
            FloatControl volume = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Stop the background music.
     */
    public static void stopBackgroundMusic() {
        backgroundClip.stop();
    }

    /**
     * Load a random collision sound and play it.
     */
    public static void playCollisionBullet() {
        try {
            int rndNum = random.nextInt(max - min) + min;
            Clip click = AudioSystem.getClip();
            switch (rndNum) {
                case 1:
                    click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Asshole 2.wav")));
                    break;
                case 2:
                    click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Woaw Jesus.wav")));
                    break;
                case 3:
                    click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Woaw.wav")));
                    break;
                case 4:
                    click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Oi 1.wav")));
                    break;
                case 5:
                    click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/scream.wav")));
                    break;
                default:
                    click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Asshole 2.wav")));
                    break;
            }
            killCount++;
            click.start();

            // If the player(s) killed 5 enemy's the kill frenzy sound is going to play //TODO  
//            if (killCount >= 5) {
//                Clip kill = AudioSystem.getClip();
//                kill.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Kill frenzy.wav")));
//                kill.start();
//                killCount = 0;
//            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Load a random explosion sound and play it.
     */
    public static void playCollisionBird() {
        try {
            Clip hit = AudioSystem.getClip();
            FloatControl volume;
            hitCount++;
            switch (hitCount) {
                case 1:
                    hit.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_SML_EXPLOSION.wav")));
                    volume = (FloatControl) hit.getControl(FloatControl.Type.MASTER_GAIN);
                    volume.setValue(-10.0f);
                    break;
                case 2:
                    hit.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_SML_EXPLOSION.wav")));
                    break;
                case 3:
                    hit.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_SML_EXPLOSION.wav")));
                    volume = (FloatControl) hit.getControl(FloatControl.Type.MASTER_GAIN);
                    volume.setValue(+5.0f);
                    break;
                default:
                    hit.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_SML_EXPLOSION.wav")));
                    break;
            }
            hit.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Load and play the airplane sound.
     */
    public static void playAirplane() {
        try {
            airplaneSound = AudioSystem.getClip();
            airplaneSound.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/airplane_sound_2.wav")));
            FloatControl volume = (FloatControl) airplaneSound.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-20.0f);
            airplaneSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Stop the airplane sound.
     */
    public static void stopAirplaneSound() {
        airplaneSound.stop();
    }

    /**
     * Load and play the game sound.
     */
    public static void playGameSound() {
        try {
            gameSound = AudioSystem.getClip();
            gameSound.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_NIGHTCLUB_LOOP.wav")));
            gameSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Stop the game sound.
     */
    public static void stopGameSound() {
        gameSound.stop();
    }

    /**
     * Load and play gun sound.
     */
    public static void playGun() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/singe machine gunshot.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Load and play oil pickup sound.
     */
    public static void playOilPickUp() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_OILDROP.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Load and play the start game sound.
     */
    public static void playStart() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Crowd cheer.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    /**
     * Load and play the game over sound.
     */
    public static void playGameOver() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/game_over.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }
}
