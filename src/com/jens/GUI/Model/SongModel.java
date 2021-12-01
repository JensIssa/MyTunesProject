package com.jens.GUI.Model;

import com.jens.BE.Song;
import com.jens.BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongModel {

    private SongManager songManager = new SongManager();

    public SongModel() throws IOException {
    }

    public ObservableList<Song> listToObservableList() throws SQLException, IOException {

        List<Song> tempSong = new ArrayList<>();
        ObservableList<Song> songs = FXCollections.observableArrayList();
        tempSong = this.songManager.getAllSongs();
        for (Song song : tempSong)
        {
            songs.add(song);
        }
        return songs;
    }
}
