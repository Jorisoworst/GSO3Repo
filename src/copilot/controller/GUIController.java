/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import static copilot.view.CopilotGUI.NANO_TO_BASE;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author IndyGames
 */
public final class GUIController {

    private static Clip backgroundClip, airplaneSound, gameSound;
    private static boolean stopped;
    
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
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/Asshole 2.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }
    
     public static void playCollisionBird() {
        try {
            Clip click = AudioSystem.getClip();
            click.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/SFX_SML_EXPLOSION.wav")));
            click.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }
    
     public static void playAirplane() {
        try {
            airplaneSound = AudioSystem.getClip();
            airplaneSound.open(AudioSystem.getAudioInputStream(GUIController.class.getResource("/sounds/airplane_sound_2.wav")));
            FloatControl volume= (FloatControl) airplaneSound.getControl(FloatControl.Type.MASTER_GAIN); 
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
    
    

//    public void startBackground() {
//        GUIController.stopped = false;
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                while (!isStopped()) {
//                    
//                }
//            }
//        };
//
//        thread.setDaemon(true);
//        thread.start();
//    }
//
//    public static synchronized boolean isStopped() {
//        return GUIController.stopped;
//    }
}
