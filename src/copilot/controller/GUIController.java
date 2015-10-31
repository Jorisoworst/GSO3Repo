/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copilot.controller;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author IndyGames
 */
public final class GUIController {
    private static Clip backgroundClip;
    
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
            backgroundClip.open(AudioSystem.getAudioInputStream(GUIController.class.getClass().getResource("/sounds/game_sound.wav")));
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            GUIController.showExceptionError(ex.toString());
        }
    }
    
    public static void stopBackgroundMusic() {
        backgroundClip.stop();
    }
}
