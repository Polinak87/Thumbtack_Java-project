package net.thumbtack.school.concert.dto.responce;

import java.util.UUID;

public class RegisterUserDtoResponce {

    private UUID token;

    public RegisterUserDtoResponce(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }


}
