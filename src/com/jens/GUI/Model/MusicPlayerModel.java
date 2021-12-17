package com.jens.GUI.Model;

import com.jens.BE.Song;
import com.jens.BLL.util.MusicPlayer;
import javafx.scene.control.TableView;
import java.util.Timer;

public class MusicPlayerModel
{
    private MusicPlayer musicPlayer;

    /**
     * creates a new musicplayer with the given song
     * @param song
     */
    public MusicPlayerModel(Song song)
    {
        musicPlayer = new MusicPlayer(song);
    }

    /**
     * stops the timer of a given timer
     * @param timer
     */
    public void cancelTimer(Timer timer){
        timer.cancel();
    }

    /**
     * calls on a fuction in musicplayer with a tableview with songs
     * @param tableView
     */
    public void endOfMedia(TableView<Song> tableView){
        musicPlayer.endOfMedia(tableView);
    }
}
