package com.example.saurav.iphonetop10freeapps;

/**
 * Created by saura on 03-12-2016.
 */

public class Application {
    private String name;
    private String artist;
    private String releaseDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "name='" + name + "\n" +
                ", artist='" + artist + "\n" +
                ", releaseDate='" + releaseDate+"\n" ;
    }
}
