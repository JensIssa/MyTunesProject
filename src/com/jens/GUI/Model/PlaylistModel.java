package com.jens.GUI.Model;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.BLL.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PlaylistModel {

    private PlaylistManager playlistManager = new PlaylistManager();

    public PlaylistModel() throws IOException {
    }

    /**
     * Converts the data from the database into an observable list
     * @return returns the observable list
     * @throws SQLException
     * @throws IOException
     */
    public ObservableList<Playlist> listToObservablelist() throws SQLException, IOException {

        List<Playlist> tempPlaylist;
        ObservableList<Playlist> playlists = FXCollections.observableArrayList();
        tempPlaylist = this.playlistManager.getAllPlaylist();
        for (Playlist playlist : tempPlaylist)
        {
            playlists.add(playlist);
        }
        return playlists;
    }

    /**
     * Converts the list of songs in a playlist into an observable list
     * @param id the id of the playlist the songs are in
     * @return returns an observable list of song in a playlist
     * @throws SQLException
     */
    public ObservableList<Song> playlistSongsToObservablelist(int id) throws SQLException {
        List<Song> tempSongList;
        ObservableList<Song> songs = FXCollections.observableArrayList();
        tempSongList = this.playlistManager.getAllPlaylistSongs(id);
        for (Song songList : tempSongList)
        {
            songs.add(songList);
        }
        return songs;
    }

    /**
     * Creates a playlist
     * @param name name of the playlist
     * @throws SQLException
     */
    public void createPlaylist(String name) throws SQLException {
        playlistManager.createPlaylist(name);
    }

    /**
     * Deletes a playlist
     * @param playlist the selected playlist to be deleted
     */
    public void deletePlaylist(Playlist playlist){
        playlistManager.deletePlaylist(playlist);
    }

    /**
     * Adds a song to the playlist
     * @param playlistId Selected playlists id
     * @param songId Selecte songs id
     */
    public void addSongToPlaylist(int playlistId, int songId){
        playlistManager.addSong(playlistId, songId);
    }

    /**
     * Deletes the Selected song from a playlist
     * @param playlistId The playlists id
     * @param songId The songs id
     * @throws SQLException
     */
    public void deleteSongFromPlaylist(int playlistId, int songId) throws SQLException {
        playlistManager.deleteSongFromPlaylist(playlistId, songId);
    }

    /**
     * Updates the playlists properties
     * @param playlist The Selected playlist
     * @throws SQLException
     */
    public void updatePlaylist(Playlist playlist) throws SQLException {
        playlistManager.updatePlaylists(playlist);
    }
}
