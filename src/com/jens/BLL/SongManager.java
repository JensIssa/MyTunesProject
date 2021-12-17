package com.jens.BLL;

import com.jens.BE.Song;
import com.jens.BLL.util.SongSearcher;
import com.jens.DAL.SongDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SongManager{

    SongDAO songDAO;
    SongSearcher songSearcher;

    public SongManager() throws IOException
    {
        songSearcher = new SongSearcher();
        songDAO = new SongDAO();
    }

    /**
     * calls the function in dao to create a song with the given params.
     * @param title
     * @param artistName
     * @param songLength
     * @param category
     * @param url
     * @param urlImg
     */
    public void createSong(String title, String artistName, int songLength, String category, String url, String urlImg)
    {
        songDAO.createSong(title, artistName, songLength, category, url, urlImg);
    }

    /**
     * calls dao to update a song in the database
     * @param song
     */
    public void updateSong(Song song)
    {
        songDAO.updateSong(song);
    }

    /**
     * calls Dao to remove a song from the database
     * @param song
     */
    public void deleteSong(Song song){
        songDAO.deleteSong(song);
    }

    /**
     * calls dao to get a list with all song in it to return.
     * @return
     */
    public List<Song> getAllSongs()
    {
        return songDAO.getAllSongs();
    }

    /**
     * makes a list of the songs comming from songSeacher and retuns them.
     * @param query
     * @return
     */
    public List<Song> searchSongs(String query)
    {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }

    /**
     * updates the cover art for a song.
     * @param song
     * @return
     */
    public String updateSongImage(Song song){
        return song.getUrlImg();
    }
}
