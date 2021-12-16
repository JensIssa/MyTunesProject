package com.jens.BLL.util;

import com.jens.BE.Song;
import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song song : searchBase) {
            if(compareToTitle(query, song) || compareToArtist(query, song) || compareToCategory(query, song))
            {
                searchResult.add(song);
            }
        }

        return searchResult;
    }

    private boolean compareToArtist(String query, Song song) {
        return song.getArtistName().toLowerCase().contains(query.toLowerCase());
    }

    private boolean compareToTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }
    private boolean compareToCategory(String query, Song song){
        return song.getCategory().toLowerCase().contains(query.toLowerCase());
    }
}
