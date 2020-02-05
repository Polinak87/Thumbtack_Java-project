package net.thumbtack.school.concert.dto.request;

import java.util.UUID;

public class ConcertDtoRequest {

    private UUID token;

    public ConcertDtoRequest (UUID token)  {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}

