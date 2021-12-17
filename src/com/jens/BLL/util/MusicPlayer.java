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

    /**
     * Calls a function on the mediaplayer to play the song it has been givin
     */
    public void playSong(){
        mediaPlayer.play();
    }

    /**
     * Calls a function on the mediaplayer to pause the song it is playing
     */
    public void pauseSong(){
        mediaPlayer.pause();
    }

    /**
     * a function when runned will set autoplay to true and select the next ellement in the tableview giving with songs.
     * @param tableView
     */
    public void  endOfMedia(TableView<Song> tableView){
        mediaPlayer.setAutoPlay(true);
        if (mediaPlayer.isAutoPlay()){
            tableView.getSelectionModel().selectNext();
            playSong();
        }
    }
}
