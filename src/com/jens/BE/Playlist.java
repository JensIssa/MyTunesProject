package com.jens.BE;

import javafx.collections.ObservableList;

public class Playlist {

    private String playlistName;
    private float totalTime;
    private ObservableList<Song> songsInPlaylist;

    public Playlist(String playlistName, float totalTime, ObservableList<Song> songsInPlaylist){
        this.playlistName = playlistName;
        this.totalTime = totalTime;
        this.songsInPlaylist = songsInPlaylist;
    }

    public float getTotalTime()
    {
        return totalTime;
    }

    public void setPlaylistName(String playlistName)
    {
        this.playlistName = playlistName;
    }

    public String getPlaylistName()
    {
        return playlistName;
    }

    public ObservableList<Song> getSongsInPlaylist()
    {
        return songsInPlaylist;
    }
}
