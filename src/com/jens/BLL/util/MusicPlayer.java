package com.jens.BLL.util;

import com.jens.BE.Song;
import com.jens.GUI.MainWindowController;
import com.jens.GUI.Model.MusicPlayerModel;
import com.jens.GUI.Model.SongModel;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class MusicPlayer
{
    public MediaPlayer mediaPlayer;

    public MusicPlayer(Song song) throws IOException
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

    public void  endOfMedia(TableView<Song> tableView){
        mediaPlayer.setAutoPlay(true);
        if (mediaPlayer.isAutoPlay()){
            tableView.getSelectionModel().selectNext();
            playSong();
        }
    }
}
