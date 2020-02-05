package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.Song;

import java.util.UUID;

public class AddRatingDtoRequest {

    private Song song;
    private UUID authorOfTheRating;
    private Integer rating;

    public AddRatingDtoRequest(Song song, UUID authorOfTheRating, Integer rating) {
        setSong(song);
        setAuthorOfTheRating(authorOfTheRating);
        setRating(rating);
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public UUID getAuthorOfTheRating() {
        return authorOfTheRating;
    }

    public void setAuthorOfTheRating(UUID authorOfTheRating) {
        this.authorOfTheRating = authorOfTheRating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
