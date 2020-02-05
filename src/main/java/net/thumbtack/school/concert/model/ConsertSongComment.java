package net.thumbtack.school.concert.model;

import java.util.List;

public class ConsertSongComment {

    private String textOfSongComment;
    private String authour;
    private long creationTime;
    private List<ConsertCommentToComment> consertCommentsToComments;

    public ConsertSongComment(String textOfSongComment, String authour, long creationTime, List<ConsertCommentToComment> consertCommentsToComments) {
        setTextOfSongComment(textOfSongComment);
        setAuthour(authour);
        setCreationTime(creationTime);
        setConsertCommentsToComments(consertCommentsToComments);
    }

    public String getTextOfSongComment() {
        return textOfSongComment;
    }

    public void setTextOfSongComment(String textOfSongComment) {
        this.textOfSongComment = textOfSongComment;
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

    public List<ConsertCommentToComment> getConsertCommentsToComments() {
        return consertCommentsToComments;
    }

    public void setConsertCommentsToComments(List<ConsertCommentToComment> consertCommentsToComments) {
        this.consertCommentsToComments = consertCommentsToComments;
    }
}
