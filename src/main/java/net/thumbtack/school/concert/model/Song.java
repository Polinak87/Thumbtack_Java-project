package net.thumbtack.school.concert.model;

import java.util.*;

public class Song {

    private UUID songId;
    private String authorOfTheOffer;
    private String songName, composer, authorOfTheText, singer;
    private Integer songLength;
    private long creationTime;
    private int sumRating;



    public Song (String authorOfTheOffer,String songName,String singer, String composer, String authorOfTheText, Integer songLength) {
        this.songId = UUID.randomUUID();
        setAuthorOfTheOffer(authorOfTheOffer);
        setSongName(songName);
        setSinger(singer);
        setComposer(composer);
        setAuthorOfTheText(authorOfTheText);
        setSongLength(songLength);
        this.creationTime = System.nanoTime();
        this.sumRating = 5;
    }

    public UUID getSongId() {
        return songId;
    }

    public String getAuthorOfTheOffer() {
        return authorOfTheOffer;
    }

    public void setAuthorOfTheOffer(String authorOfTheOffer) {
        this.authorOfTheOffer = authorOfTheOffer;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getAuthorOfTheText() {
        return authorOfTheText;
    }

    public void setAuthorOfTheText(String authorOfTheText) {
        this.authorOfTheText = authorOfTheText;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getSongLength() {
        return songLength;
    }

    public void setSongLength(Integer songLength) {
        this.songLength = songLength;
    }

    public int getSumRating() {
        return sumRating;
    }

    public void setSumRating(int sumRating) {
        this.sumRating = sumRating;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return getCreationTime() == song.getCreationTime() &&
                getSumRating() == song.getSumRating() &&
                Objects.equals(getSongId(), song.getSongId()) &&
                Objects.equals(getAuthorOfTheOffer(), song.getAuthorOfTheOffer()) &&
                Objects.equals(getSongName(), song.getSongName()) &&
                Objects.equals(getComposer(), song.getComposer()) &&
                Objects.equals(getAuthorOfTheText(), song.getAuthorOfTheText()) &&
                Objects.equals(getSinger(), song.getSinger()) &&
                Objects.equals(getSongLength(), song.getSongLength());
    }

    public boolean isDouble(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return  Objects.equals(getSongName(), song.getSongName()) &&
                Objects.equals(getComposer(), song.getComposer()) &&
                Objects.equals(getAuthorOfTheText(), song.getAuthorOfTheText()) &&
                Objects.equals(getSinger(), song.getSinger());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSongId(), getAuthorOfTheOffer(), getSongName(), getComposer(), getAuthorOfTheText(), getSinger(), getSongLength(), getCreationTime(), getSumRating());
    }
}
