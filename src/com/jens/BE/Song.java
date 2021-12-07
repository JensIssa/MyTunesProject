package com.jens.BE;

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

    public void setArtistName(String artistName)
    {
        this.artistName = artistName;
    }

    public int getSongLength()
    {
        return songLength;
    }

    public void setSongLength(int songLength)
    {
        this.songLength = songLength;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getUrlImg(){
        return urlImg;
    }
    public void setUrlImg(String urlImg){
        this.urlImg = urlImg;
    }

    @Override public String toString()
    {
        return "Song{" +
                "title='" + title + '\'' +
                ", artistName='" + artistName + '\'' +
                ", lentgh=" + songLength +
                ", catergory='" + category + '\'' +
                '}';
    }
}
