package net.thumbtack.school.concert.dto.responce;

public class AddCommentToCommentDtoResponce {
    private String responce;

    public AddCommentToCommentDtoResponce(String responce) {
        this.responce = responce;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
