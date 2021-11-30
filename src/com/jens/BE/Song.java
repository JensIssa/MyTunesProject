package com.jens.BE;

public class Song {

    private String title;
    private String artistName;
    private float songLength;
    private String catergory;
    private int id;

    public Song(String title, String artistName, float lentgh, String catergory, int id){
        this.title = title;
        this.artistName = artistName;
        this.songLength = lentgh;
        this.catergory = catergory;
        this.id = id;
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

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public float getsongLength()
    {
        return songLength;
    }

    public void setsongLength(float songLength)
    {
        this.songLength = songLength;
    }

    public String getCatergory()
    {
        return catergory;
    }

    public void setCatergory(String catergory)
    {
        this.catergory = catergory;
    }

    @Override public String toString()
    {
        return "Song{" +
                "title='" + title + '\'' +
                ", artistName='" + artistName + '\'' +
                ", lentgh=" + songLength +
                ", catergory='" + catergory + '\'' +
                '}';
    }
}
