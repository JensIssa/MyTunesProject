package com.jens.BE;

import javafx.collections.ObservableList;

public class Playlist {
    private int id;
    private String playlistName;
    private float totalTime;
    private int totalSongs;

    public Playlist(int id, String playlistName,){
        this.id = id;
        this.playlistName = playlistName;
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
    public int getTotalSongs(){
        return totalSongs;
    }
    public void setTotalSongs()
    {
        this.totalSongs = totalSongs;
    }
}
