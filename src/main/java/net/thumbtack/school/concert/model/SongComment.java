package net.thumbtack.school.concert.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class SongComment {

    private UUID songCommentId;
    private UUID songId;
    private String textOfSongComment;
    private String authour;
    private long creationTime;
    private boolean CommToCommAvailability;


    public SongComment (UUID songId, String textOfSongComment, String authour) {
        this.songCommentId = UUID.randomUUID();
        setSongId(songId);
        setTextOfSongComment(textOfSongComment);
        setAuthour(authour);
        this.creationTime = System.nanoTime();
        setCommToCommAvailability(false);
    }

    public UUID getSongCommentId() {
        return songCommentId;
    }

    public void setSongId(UUID songId) {
        this.songId = songId;
    }

    public UUID getSongId() {
        return songId;
    }

    public String getAuthour() {
        return authour;
    }

    public void setAuthour(String authour) {
        this.authour = authour;
    }

    public String getTextOfSongComment() {
        return textOfSongComment;
    }

    public void setTextOfSongComment(String textOfSongComment) {
        this.textOfSongComment = textOfSongComment;
    }

    public boolean isCommToCommAvailability() {
        return CommToCommAvailability;
    }

    public void setCommToCommAvailability(boolean commToCommAvailability) {
        CommToCommAvailability = commToCommAvailability;
    }

    public long getCreationTime() {
        return creationTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongComment)) return false;
        SongComment that = (SongComment) o;
        return creationTime == that.creationTime &&
                CommToCommAvailability == that.CommToCommAvailability &&
                Objects.equals(songCommentId, that.songCommentId) &&
                Objects.equals(songId, that.songId) &&
                Objects.equals(textOfSongComment, that.textOfSongComment) &&
                Objects.equals(authour, that.authour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songCommentId, songId, textOfSongComment, authour, creationTime, CommToCommAvailability);
    }
}
