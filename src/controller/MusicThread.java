package controller;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class MusicThread{
    private String songFile;
    private AudioInputStream musicStream;
    private Clip clip;
    private boolean isLooping;

    public MusicThread(String songFile, boolean isLooping){
        this.songFile = songFile;
        this.isLooping = isLooping;
    }

    public void turnOn(){
        try {
            File file = new File(songFile);
            musicStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(musicStream);
            clip.setFramePosition(0);
            clip.start();
            if(isLooping){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void turnOff(){
        if(clip != null){
            clip.stop();
        }
    }
}
