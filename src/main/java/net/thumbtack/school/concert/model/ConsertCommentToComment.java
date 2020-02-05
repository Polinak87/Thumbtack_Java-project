package net.thumbtack.school.concert.model;

public class ConsertCommentToComment {

    private String textOfCommentToComment;
    private String authour;
    private long creationTime;

    public ConsertCommentToComment(String textOfCommentToComment, String authour, long creationTime) {
        setTextOfCommentToComment(textOfCommentToComment);
        setAuthour(authour);
        setCreationTime(creationTime);
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
}
