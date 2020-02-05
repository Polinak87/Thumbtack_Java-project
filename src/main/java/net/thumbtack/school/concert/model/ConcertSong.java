package net.thumbtack.school.concert.model;

import java.util.ArrayList;
import java.util.List;

public class ConcertSong {

    private String songName, composer, authorOfTheText, singer;
    private int sumRating;
    private String userFirstName;
    private String userLastName;
    private List<ConsertSongComment> consertSongComment = new ArrayList<>();

    public ConcertSong(String songName, String composer, String authorOfTheText, String singer, int sumRating, String userFirstName, String userLastName, List<ConsertSongComment> consertSongComment) {
        setSongName(songName);
        setComposer(composer);
        setAuthorOfTheText(authorOfTheText);
        setSinger(singer);
        setSumRating(sumRating);
        setUserFirstName(userFirstName);
        setUserLastName(userLastName);
        setConsertSongComment(consertSongComment);
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

    public int getSumRating() {
        return sumRating;
    }

    public void setSumRating(int sumRating) {
        this.sumRating = sumRating;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public List<ConsertSongComment> getConsertSongComment() {
        return consertSongComment;
    }

    public void setConsertSongComment(List<ConsertSongComment> consertSongComment) {
        this.consertSongComment = consertSongComment;
    }
}
