package com.jens.BLL;

import com.jens.BE.Song;
import com.jens.DAL.SongDAO;

import java.io.IOException;
import java.sql.SQLException;

public class SongManager implements Manager{

    SongDAO songDAO = new SongDAO();

    public SongManager() throws IOException
    {
    }

    @Override public String setName(String name)
    {
        return name;
    }

    @Override public String getName(String name)
    {
        return name;
    }

    @Override public float time(float time)
    {
        return time;
    }

    public void createSong(String title, String artistName, float songLength, String category, String url) throws SQLException
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

    public void getAllSongs() throws SQLException, IOException
    {
        songDAO.getAllSongs();
    }
}
