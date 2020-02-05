package net.thumbtack.school.concert.dto.responce;

import net.thumbtack.school.concert.model.Song;

import java.util.List;

public class GetListSongsDtoResponce {

    private List<Song> Songs;

    public GetListSongsDtoResponce(List<Song> songs) {
        Songs = songs;
    }

    public List<Song> getSongs() {
        return Songs;
    }

    public void setSongs(List<Song> songs) {
        Songs = songs;
    }
}
