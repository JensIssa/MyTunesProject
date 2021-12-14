package com.jens.GUI.Model;

import com.jens.BE.Song;
import com.jens.BLL.SongManager;
import com.jens.BLL.util.MusicPlayer;
import com.jens.GUI.MainWindowController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class SongModel {

    private SongManager songManager = new SongManager();
    private MusicPlayer musicPlayer;
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
        List<Song> searchResults = songManager.searchSongs(query);
        songList.clear();
        songList.addAll(searchResults);
    }
    public String songImageUpdate(Song song){
        return songManager.updateSongImage(song);
    }
    public void updateSong (Song song) throws SQLException {
        songManager.updateSong(song);
    }
}
