package com.jens.BLL.util;

import com.jens.BE.Song;
import com.jens.GUI.MainWindowController;
import com.jens.GUI.Model.SongModel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer
{
    public MediaPlayer mediaPlayer;
    private MainWindowController mainWindowController;
    private SongModel songModel;

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
        songModel.isDone(false);
        songModel.beginTimer();
    }
    public void  endOfMedia(){
        songModel.cancelTimer();
        songModel.isPlaying(false);
        mediaPlayer.setAutoPlay(true);
        songModel.isDone(true);
        if (mediaPlayer.isAutoPlay()){
            songModel.selectNext();
            playSong();
        }
    }
}
