package com.jens.BE;
/**
 * Song object that has all the parameters getting used through the project
 * Also has all the getters and setters for all the stuff
 */
public class Song {
    private int id;
    private String title;
    private String artistName;
    private int songLength;
    private String category;
    private String url;
    private String urlImg;

    public Song(int id, String title, String artistName, int songLength, String category, String url, String urlImg){
        this.id = id;
        this.title = title;
        this.artistName = artistName;
        this.songLength = songLength;
        this.category = category;
        this.url = url;
        this.urlImg = urlImg;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getArtistName()
    {
        return artistName;
    }

    public int getSongLength()
    {
        return songLength;
    }

    public String getCategory()
    {
        return category;
    }

    public String getUrl(){
        return url;
    }

    public String getUrlImg(){
        return urlImg;
    }

    @Override public String toString()
    {
        return title;
    }
}
