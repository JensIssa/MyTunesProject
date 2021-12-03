package com.jens.BLL.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer
{
    public MediaPlayer mediaPlayer;

    public MusicPlayer()
    {
        String bip = "src/com/jens/GUI/2nd_song.wav";
        Media hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }


    public void playSong(){
        mediaPlayer.play();
        System.out.println(mediaPlayer.getVolume());
    }
}
