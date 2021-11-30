package com.jens.BE;

import javafx.collections.ObservableList;

public class Playlist {

    private String playlistName;
    private float totalTime;
    private int id;

    public Playlist(String playlistName, float totalTime, int id){
        this.playlistName = playlistName;
        this.totalTime = totalTime;
        this.id = id;
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
}
