package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.Song;

import java.util.List;
import java.util.UUID;

public class AddManySongDtoRequest {

    private List<AddSongDtoRequest> songsForAdd;


    public AddManySongDtoRequest(List<AddSongDtoRequest> songsForAdd) {
        this.songsForAdd = songsForAdd;
    }

    public List<AddSongDtoRequest> getSongsForAdd() {
        return songsForAdd;
    }
}
