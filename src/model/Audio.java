package model;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Audio {
    private Channel c;
    private AudioPlayer MGP = AudioPlayer.player;
    private AudioStream BGM;
    private AudioData MD;
    private ContinuousAudioDataStream loop = null;

    public Audio(Channel c){
        this.c = c;
        buildMediaPlayer();
    }

    private void buildMediaPlayer(){


        try{
            BGM = new AudioStream(new URL(c.getLiveAudioURL()).openStream());
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAudio(){
        MGP.start(loop);
    }
}
