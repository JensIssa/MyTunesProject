package com.jens.BLL;

import com.jens.BE.Playlist;
import com.jens.BE.Song;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager implements Manager{

    List<Song> songList = new ArrayList<>();

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
            totaltime = totaltime + song.getLentgh();
        }
        return totaltime;
    }

    public Song addSong(Song song){
        return song;
    }

    public Song removeSong(Song song){
        return song;
    }
}
