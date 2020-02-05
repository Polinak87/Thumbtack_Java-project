package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.Song;

import java.util.UUID;

public class AddSongCommentDtoRequest {

    private UUID token;
    private Song song;
    private String textOfSongComment;


    public AddSongCommentDtoRequest(UUID token, Song song, String textOfSongComment) {
        setToken(token);
        setSong(song);
        setTextOfSongComment(textOfSongComment);

    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getTextOfSongComment() {
        return textOfSongComment;
    }

    public void setTextOfSongComment(String textOfSongComment) {
        this.textOfSongComment = textOfSongComment;
    }

}
