package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.Song;

import java.util.UUID;

public class DeleteSongDtoRequest {

    private UUID token;
    private Song song;

    public DeleteSongDtoRequest(UUID token, Song song) {
        this.token = token;
        this.song = song;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
