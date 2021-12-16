package com.jens.GUI.Model;

import com.jens.BE.Song;
import com.jens.BLL.SongManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class SongModel {

    private SongManager songManager = new SongManager();
    private ObservableList<Song> songList;

    /**
     * Instantiates the songlist
     * @throws IOException
     */
    public SongModel() throws IOException {
        songList = FXCollections.observableArrayList();
    }

    /**
     * Converts the list of songs into an observable list
     * @return returns an observable list
     * @throws SQLException
     * @throws IOException
     */
    public ObservableList<Song> listToObservablelist() throws SQLException, IOException {

        List<Song> tempSong;
        tempSong = this.songManager.getAllSongs();
        for (Song song : tempSong)
        {
            songList.add(song);
        }
        return songList;
    }

    /**
     * Creates a song
     * @param title Name of the song
     * @param artistName Name of the artist
     * @param songLength Length of the song
     * @param category The song genre
     * @param url The songs local filePath
     * @param urlImg The songs image filePath
     * @throws SQLException
     */
    public void createSong(String title, String artistName, int songLength, String category, String url, String urlImg) throws SQLException {
        songManager.createSong(title, artistName, songLength, category, url, urlImg);
    }

    /**
     * Deletes the selected song
     * @param song The selected song
     */
    public void deleteSong(Song song){
        songManager.deleteSong(song);
    }

    /**
     *
     * @param query
     * @throws SQLException
     * @throws IOException
     */
    public void searchSongs(String query) throws SQLException, IOException
    {
        List<Song> searchResults = songManager.searchSongs(query);
        songList.clear();
        songList.addAll(searchResults);
    }

    /**
     *
     * @param song
     * @return
     */
    public String songImageUpdate(Song song){
        return songManager.updateSongImage(song);
    }

    /**
     * Updates the songs properties
     * @param song The selected song
     * @throws SQLException
     */
    public void updateSong (Song song) throws SQLException {
        songManager.updateSong(song);
    }
}
