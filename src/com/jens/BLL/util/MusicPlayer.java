package com.jens.BLL.util;

import com.jens.BE.Song;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer
{
    public MediaPlayer mediaPlayer;

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

    public void  endOfMedia(TableView<Song> tableView){
        mediaPlayer.setAutoPlay(true);
        if (mediaPlayer.isAutoPlay()){
            tableView.getSelectionModel().selectNext();
            playSong();
        }
    }
}
