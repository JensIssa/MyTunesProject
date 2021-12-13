package com.jens.BLL.util;

import com.jens.BE.Song;
import com.jens.GUI.MainWindowController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer
{
    public MediaPlayer mediaPlayer;
    public MainWindowController mainWindowController;

    public MusicPlayer(Song song)
    {
        String bip = song.getUrl();
        Media hit = new Media(new File(bip).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
    }

    public void playSong(){
        mediaPlayer.play();
    }
    public void pauseSong(){
        mediaPlayer.pause();
    }
    public void autoPlay(){
        mediaPlayer.isAutoPlay();
    }

    public void playMedia(){
        mainWindowController.isDone = false;
        mainWindowController.beginTimer();
    }
    public void  endOfMedia(){
        mainWindowController.cancelTimer();
        mainWindowController.isPlaying = false;
        mediaPlayer.setAutoPlay(true);
        mainWindowController.isDone = true;
        if (mediaPlayer.isAutoPlay()){
            mainWindowController.songTable.getSelectionModel().selectNext();
            playSong();
        }
    }
}
