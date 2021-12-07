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
    private ObservableList<Song> songList;

    public SongModel() throws IOException {
        songList = FXCollections.observableArrayList();
    }

    public ObservableList<Song> listToObservablelist() throws SQLException, IOException {

        List<Song> tempSong = new ArrayList<>();
        tempSong = this.songManager.getAllSongs();
        for (Song song : tempSong)
        {
            songList.add(song);
        }
        return songList;
    }

    public void createSong(String title, String artistName, int songLength, String category, String url, String urlImg) throws SQLException {
        songManager.createSong(title, artistName, songLength, category, url, urlImg);
    }

    public void deleteSong(Song song){
        songManager.deleteSong(song);
    }

    public void searchSongs(String query) throws SQLException, IOException
    {
        List<Song> seachedSongs = songManager.search(listToObservablelist(), query);
        songList.clear();
        songList.addAll(seachedSongs);
    }
}
