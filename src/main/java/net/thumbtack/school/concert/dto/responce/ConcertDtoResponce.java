package net.thumbtack.school.concert.dto.responce;

import net.thumbtack.school.concert.model.ConcertSong;

import java.util.List;

public class ConcertDtoResponce {
    private List<ConcertSong> concert;

    public ConcertDtoResponce(List<ConcertSong> concert) {
        setConcert(concert);
    }

    public List<ConcertSong> getConcert() {
        return concert;
    }

    public void setConcert(List<ConcertSong> concert) {
        this.concert = concert;
    }
}
