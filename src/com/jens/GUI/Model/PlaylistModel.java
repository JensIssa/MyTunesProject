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

        List<Playlist> tempPlaylist = new ArrayList<>();
        ObservableList<Playlist> playlists = FXCollections.observableArrayList();
        tempPlaylist = this.playlistManager.getAllPlaylist();
        for (Playlist playlist : tempPlaylist)
        {
            playlists.add(playlist);
        }
        return playlists;
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
}
