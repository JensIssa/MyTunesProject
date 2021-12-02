package com.jens.BLL;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.DAL.PlaylistDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager implements Manager{

    PlaylistDAO playlistDAO = new PlaylistDAO();
    List<Song> songList = new ArrayList<>();

    public PlaylistManager() throws IOException
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
        float totaltime = 0;
        //for loop for songs in database JENS PLEASE FIX
        for(Song song : songList){
            totaltime = totaltime + song.getSongLength();
        }
        return totaltime;
    }

    public Song addSong(Song song){
        return song;
    }

    public Song removeSong(Song song){
        return song;
    }

    public void updatePlaylists(Playlist playlist) throws SQLException
    {
        playlistDAO.updatePlaylist(playlist);
    }

    public void createPlaylist(String playlistName) throws SQLException
    {
        playlistDAO.createPlaylist(playlistName);
    }

    public void deletePlaylist(Playlist playlist){
        playlistDAO.deletePlaylist(playlist);
    }

    public void getAllPlaylist() throws SQLException, IOException
    {
        playlistDAO.getAllPlaylists();
    }
}
