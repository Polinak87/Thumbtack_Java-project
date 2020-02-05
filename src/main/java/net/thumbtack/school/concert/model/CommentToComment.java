package net.thumbtack.school.concert.model;

import java.util.Objects;
import java.util.UUID;

public class CommentToComment {

    private UUID commentToCommentId;
    private UUID songCommentId;
    private String textOfCommentToComment;
    private String authour;
    private long creationTime;

    public CommentToComment(UUID songCommentId, String textOfCommentToComment, String authour) {
        this.commentToCommentId = UUID.randomUUID();
        setSongCommentId(songCommentId);
        setTextOfCommentToComment(textOfCommentToComment);
        setAuthour(authour);
        this.creationTime = System.nanoTime();
    }

    public UUID getCommentToCommentId() {
        return commentToCommentId;
    }

    public UUID getSongCommentId() {
        return songCommentId;
    }

    public void setSongCommentId(UUID songCommentId) {
        this.songCommentId = songCommentId;
    }

    public String getTextOfCommentToComment() {
        return textOfCommentToComment;
    }

    public void setTextOfCommentToComment(String textOfCommentToComment) {
        this.textOfCommentToComment = textOfCommentToComment;
    }

    public String getAuthour() {
        return authour;
    }

    public void setAuthour(String authour) {
        this.authour = authour;
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
        if (!(o instanceof CommentToComment)) return false;
        CommentToComment that = (CommentToComment) o;
        return creationTime == that.creationTime &&
                Objects.equals(commentToCommentId, that.commentToCommentId) &&
                Objects.equals(songCommentId, that.songCommentId) &&
                Objects.equals(textOfCommentToComment, that.textOfCommentToComment) &&
                Objects.equals(authour, that.authour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentToCommentId, songCommentId, textOfCommentToComment, authour, creationTime);
    }
}


