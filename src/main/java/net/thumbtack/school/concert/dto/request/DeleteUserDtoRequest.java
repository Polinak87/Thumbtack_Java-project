package net.thumbtack.school.concert.dto.request;

import java.util.UUID;

public class DeleteUserDtoRequest {

    UUID token;

    public DeleteUserDtoRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
