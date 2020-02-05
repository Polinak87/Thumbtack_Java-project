package net.thumbtack.school.concert.dto.responce;

import java.util.UUID;

public class LoginUserDtoResponce {

    private UUID token;

    public LoginUserDtoResponce(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}
