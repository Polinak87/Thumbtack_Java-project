package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.CommentToComment;

import java.util.UUID;

public class RejectionOfCommentToCommentDtoRequest {

    UUID token;
    CommentToComment commentToComment;

    public RejectionOfCommentToCommentDtoRequest(UUID token, CommentToComment commentToComment) {
        this.token = token;
        this.commentToComment = commentToComment;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public CommentToComment getCommentToComment() {
        return commentToComment;
    }

    public void setCommentToComment(CommentToComment commentToComment) {
        this.commentToComment = commentToComment;
    }
}
