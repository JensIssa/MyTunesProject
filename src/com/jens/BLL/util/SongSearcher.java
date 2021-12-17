package com.jens.BLL.util;

import com.jens.BE.Song;
import java.util.ArrayList;
import java.util.List;

public class SongSearcher {
    /**
     * makes a list to return with all the songs that match with the string query.
     * @param searchBase
     * @param query
     * @return
     */
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

    /**
     * comapares if any songs matches what the user is typing to find the artist
     * @param query
     * @param song
     * @return
     */
    private boolean compareToArtist(String query, Song song) {
        return song.getArtistName().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * comapares if any songs matches what the user is typing to find the title
     * @param query
     * @param song
     * @return
     */
    private boolean compareToTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * comapares if any songs matches what the user is typing to find the category
     * @param query
     * @param song
     * @return
     */
    private boolean compareToCategory(String query, Song song){
        return song.getCategory().toLowerCase().contains(query.toLowerCase());
    }
}
