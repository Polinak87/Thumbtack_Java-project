package net.thumbtack.school.concert.dto.request;

import java.util.UUID;

public class AddSongDtoRequest {
    private  UUID authorOfTheOffer;
    private String songName, composer, authorOfTheText, singer;
    private Integer songLength;

    public AddSongDtoRequest (UUID authorOfTheOffer,String songName,String singer, String composer, String authorOfTheText, Integer songLength) {
        setAuthorOfTheOffer(authorOfTheOffer);
        setSongName(songName);
        setSinger(singer);
        setComposer(composer);
        setAuthorOfTheText(authorOfTheText);
        setSongLength(songLength);
    }

    public UUID getAuthorOfTheOffer() {
        return authorOfTheOffer;
    }

    public void setAuthorOfTheOffer(UUID token) {
        this.authorOfTheOffer = token;
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
}
