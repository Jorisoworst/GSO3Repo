/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import static copilot.view.frame.CopilotGUI.NANO_TO_BASE;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Panel;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author IndyGames
 */
public final class GUIController {

    private static Clip backgroundClip, airplaneSound, gameSound;
    private static Random random = new Random();
    private static int min = 1;
    private static int max = 5;
    private static int countKill, countHit = 0;
    
    public static Font loadFont(int size) {
        try {
            InputStream is = GUIController.class.getClassLoader().getResourceAsStream("Minecraftia-Regular.ttf");
            Font font;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException ex) {
            GUIController.showExceptionError(ex.toString());
            return null;
        }
    }

    public static void playClick() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/click.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    public static void playHover() {
        try {
            Clip hover = AudioSystem.getClip();
            hover.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/hover.wav")));
            hover.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    public static void showExceptionError(String error) {
        JOptionPane.showMessageDialog(null, "Something went wrong, please try again, ERROR: " + error, "ALERT", JOptionPane.ERROR_MESSAGE);
    }

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

    public static void stopBackgroundMusic() {
        backgroundClip.stop();
    }

    //sounds ingame
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
            countKill++;
            click.start();
            if (countKill >= 5) {
                Clip kill = AudioSystem.getClip();
                kill.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Kill frenzy.wav")));
                kill.start();
                countKill = 0;
            }

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    public static void playCollisionBird() {
        try {
            Clip hit = AudioSystem.getClip();
            FloatControl volume;
            countHit++;
            switch (countHit) {
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

    public static void stopAirplaneSound() {
        airplaneSound.stop();
    }

    public static void playGameSound() {
        try {
            gameSound = AudioSystem.getClip();
            gameSound.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_NIGHTCLUB_LOOP.wav")));
            gameSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    public static void stopGameSound() {
        gameSound.stop();
    }

    public static void playGun() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/singe machine gunshot.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    public static void playOilPickUp() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_OILDROP.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

    public static void playStart() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Crowd cheer.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }

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
