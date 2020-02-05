package net.thumbtack.school.concert.dto.responce;

public class AddRatingDtoResponce {

    private String responce;

    public AddRatingDtoResponce(String responce) {
        this.responce = responce;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
