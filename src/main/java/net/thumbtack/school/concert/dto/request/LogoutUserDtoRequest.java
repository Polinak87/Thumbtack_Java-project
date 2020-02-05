package net.thumbtack.school.concert.dto.request;

import java.util.UUID;

public class LogoutUserDtoRequest {
    private UUID token;

    public LogoutUserDtoRequest (UUID token)  {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
