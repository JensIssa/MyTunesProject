package com.jens.BE;

import javafx.collections.ObservableList;

public class Playlist {
    private int id;
    private String playlistName;
    private float totalTime;

    public Playlist(int id, String playlistName, float totalTime){
        this.id = id;
        this.playlistName = playlistName;
        this.totalTime = totalTime;
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

    public int getId(){
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
}
