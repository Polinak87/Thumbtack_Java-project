package net.thumbtack.school.concert.dto.request;

import java.util.UUID;

public class GetListSongsOfDtoRequest {

    private UUID token;
    private String person;

    public GetListSongsOfDtoRequest(UUID token, String person) {
        this.token = token;
        this.person = person;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
