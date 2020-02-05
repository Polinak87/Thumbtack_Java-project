package net.thumbtack.school.concert.dto.responce;

public class AddSongDtoResponce {
    private String responce;

    public AddSongDtoResponce(String responce) {
        this.responce = responce;
    }

    public String getResponce() {
        return responce;
    }

    public void setResponce(String responce) {
        this.responce = responce;
    }
}
