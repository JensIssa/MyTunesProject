package com.jens.BE;

import java.util.List;

/**
 * Playlist object that has all the parameters getting used through the project
 * Also has all the getters and setters for all the stuff
 */
public class Playlist {
    private int id;
    private String playlistName;
    private float totalTime;
    private int totalSongs;
    private List<Song> playlistSongs;

    public Playlist(int id, String playlistName){
        this.id = id;
        this.playlistName = playlistName;
    }

    public float getTotalTime()
    {
        return totalTime;
    }
    public void setTotalTime(float totalTime)
    {
        this.totalTime = totalTime;
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
    public void setTotalSongs( int totalSongs)
    {
        this.totalSongs = totalSongs;
    }

    public List<Song> getPlaylistSongs() {
        return playlistSongs;
    }

    public void setPlaylistSongs(List<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }
}
