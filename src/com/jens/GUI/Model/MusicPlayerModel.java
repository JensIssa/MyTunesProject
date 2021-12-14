package com.jens.GUI.Model;

import com.jens.BE.Song;
import com.jens.BLL.util.MusicPlayer;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Timer;

public class MusicPlayerModel
{
    private MusicPlayer musicPlayer;

    public MusicPlayerModel(Song song) throws IOException
    {
        musicPlayer = new MusicPlayer(song);
    }

    public void cancelTimer(Timer timer){
        timer.cancel();
    }
    public void endOfMedia(TableView<Song> tableView){
        musicPlayer.endOfMedia(tableView);
    }
}
