package com.jens.BLL;

import com.jens.BE.Song;
import com.jens.DAL.SongDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongManager{

    SongDAO songDAO = new SongDAO();

    public SongManager() throws IOException
    {
    }

    public void createSong(String title, String artistName, int songLength, String category, String url) throws SQLException
    {
        songDAO.createSong(title, artistName, songLength, category, url);
    }

    public void updateSong(Song song) throws SQLException
    {
        songDAO.updateSong(song);
    }

    public void deleteSong(Song song){
        songDAO.deleteSong(song);
    }

    public List<Song> getAllSongs() throws SQLException, IOException
    {
        return songDAO.getAllSongs();
    }
}
