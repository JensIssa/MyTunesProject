package com.jens.GUI.Model;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.BLL.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistModel {

    private PlaylistManager playlistManager = new PlaylistManager();

    public PlaylistModel() throws IOException {
    }

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

    public ObservableList<Song> playlistSongsToObservablelist(int id) throws SQLException {
        List<Song> tempSongList;
        ObservableList<Song> songs = FXCollections.observableArrayList();
        tempSongList = this.playlistManager.getAllPlaylistSongs(id);
        for (Song songList : tempSongList)
        {
            songs.add(songList);
        }
        System.out.println(tempSongList.size());
        return songs;
    }

    public void createPlaylist(String name) throws SQLException {
        playlistManager.createPlaylist(name);
    }

    public void deletePlaylist(Playlist playlist){
        playlistManager.deletePlaylist(playlist);
    }

    public void addSongToPlaylist(int playlistId, int songId){
        playlistManager.addSong(playlistId, songId);
    }

    public void getAllPlaylistSongs(int playlistId) throws SQLException {
        playlistManager.getAllPlaylistSongs(playlistId);
    }

    public void removeSong(Song song){
        playlistManager.removeSong(song);
    }
}
