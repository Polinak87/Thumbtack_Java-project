package net.thumbtack.school.concert.dto.responce;

public class DeleteRatingDtoResponce {

    private String responce;

    public DeleteRatingDtoResponce(String responce) {
        this.responce = responce;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
