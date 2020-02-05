package net.thumbtack.school.concert.dto.responce;

public class AddSongCommentDtoResponce {

    private String responce;

    public AddSongCommentDtoResponce(String responce) {
        this.responce = responce;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
