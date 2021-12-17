package com.jens.BLL;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.DAL.PlaylistDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager{

    PlaylistDAO playlistDAO = new PlaylistDAO();
    List<Song> songList = new ArrayList<>();

    public PlaylistManager() throws IOException
    {
    }

    public int time(float time)
    {
        int totaltime = 0;
        //for loop for songs in database JENS PLEASE FIX
        for(Song song : songList){
            totaltime = totaltime + song.getSongLength();
        }
        return totaltime;
    }

    /**
     * asks the dao to add this song to this playlist
     * @param playlistID
     * @param songID
     */
    public void addSong(int playlistID, int songID){
        playlistDAO.addSongToPlaylist(playlistID, songID);
    }

    /**
     * asks the dao to remove this song from this playlist
     * @param playlistId
     * @param songId
     * @throws SQLException
     */
    public void deleteSongFromPlaylist(int playlistId, int songId) throws SQLException {
        playlistDAO.deleteSongFromPlaylist(playlistId, songId);
    }

    /**
     * ask the dao to update the playlist
     * @param playlist
     */
    public void updatePlaylists(Playlist playlist)
    {
        playlistDAO.updatePlaylist(playlist);
    }

    /**
     * sends a string down to dao to create a song with that string as its name
     * @param playlistName
     */
    public void createPlaylist(String playlistName)
    {
        playlistDAO.createPlaylist(playlistName);
    }

    /**
     * ask the dao to delete this playlist
     * @param playlist
     */
    public void deletePlaylist(Playlist playlist){
        playlistDAO.deletePlaylist(playlist);
    }

    /**
     * asks dao to get a list of all the playlist to return
     * @return
     */
    public List<Playlist> getAllPlaylist()
    {
        return playlistDAO.getAllPlaylists();
    }

    /**
     * asks the dao to get all the songs in the given playlist with this ID
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public List<Song> getAllPlaylistSongs(int playlistId) throws SQLException {
        return playlistDAO.getAllPlaylistSongs(playlistId);
    }
}
