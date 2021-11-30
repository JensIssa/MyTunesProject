package com.jens.BLL.util;

import com.jens.BE.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song song : searchBase) {
            if(compareToMovieTitle(query, song) || compareToMovieYear(query, song))
            {
                searchResult.add(song);
            }
        }

        return searchResult;
    }

    private boolean compareToMovieYear(String query, Song song) {
        return Float.toString(song.getLentgh()).contains(query);
    }

    private boolean compareToMovieTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }
}
