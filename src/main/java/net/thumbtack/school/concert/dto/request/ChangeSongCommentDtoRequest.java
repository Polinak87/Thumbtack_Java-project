package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.SongComment;

import java.util.UUID;

public class ChangeSongCommentDtoRequest {

    UUID token;
    SongComment songComment;
    String textOfSongComment;

    public ChangeSongCommentDtoRequest(UUID token, SongComment songComment, String textOfSongComment) {
        this.token = token;
        this.songComment = songComment;
        this.textOfSongComment = textOfSongComment;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public SongComment getSongComment() {
        return songComment;
    }

    public void setSongComment(SongComment songComment) {
        this.songComment = songComment;
    }

    public String getTextOfSongComment() {
        return textOfSongComment;
    }

    public void setTextOfSongComment(String textOfSongComment) {
        this.textOfSongComment = textOfSongComment;
    }
}
