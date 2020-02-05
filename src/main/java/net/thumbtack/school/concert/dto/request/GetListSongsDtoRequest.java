package net.thumbtack.school.concert.dto.request;

import java.util.UUID;

public class GetListSongsDtoRequest {

    private UUID token;

    public GetListSongsDtoRequest(UUID token) {
        this.token = token;
    }

    public UUID getToken() {
        return token;
    }
}
