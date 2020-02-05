package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.SongComment;
import java.util.UUID;

public class AddCommentToCommentDtoRequest {

    UUID token;
    SongComment songComment;
    String textOfCommentToComment;


    public AddCommentToCommentDtoRequest(UUID token, SongComment songComment, String textOfCommentToComment) {
        setToken(token);
        setSongComment(songComment);
        setTextOfCommentToComment(textOfCommentToComment);
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

    public String getTextOfCommentToComment() {
        return textOfCommentToComment;
    }

    public void setTextOfCommentToComment(String textOfCommentToComment) {
        this.textOfCommentToComment = textOfCommentToComment;
    }
}
