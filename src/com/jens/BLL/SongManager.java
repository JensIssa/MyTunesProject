package com.jens.BLL;

import com.jens.BE.Song;
import com.jens.BLL.util.SongSearcher;
import com.jens.DAL.SongDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SongManager{

    SongDAO songDAO;
    SongSearcher songSearcher;

    public SongManager() throws IOException
    {
        songSearcher = new SongSearcher();
        songDAO = new SongDAO();
    }

    public void createSong(String title, String artistName, int songLength, String category, String url, String urlImg) throws SQLException
    {
        songDAO.createSong(title, artistName, songLength, category, url, urlImg);
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

    public List<Song> searchSongs(String query) throws SQLException, IOException
    {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }

    public String updateSongImage(Song song){
        return song.getUrlImg();
    }
}
