package net.thumbtack.school.concert.model;

import net.thumbtack.school.concert.model.Song;

import java.util.Objects;
import java.util.UUID;

public class RatingBox {
    private Song song;
    private String authorOfTheRating;
    private Integer rating;
    private long creationTime;
    private boolean abilityToBeDeleted;

    public RatingBox(Song song, String authorOfTheRating, Integer rating) {
        this(song, authorOfTheRating,rating, true);
    }

    public RatingBox(Song song, String authorOfTheRating, Integer rating, boolean abilityToBeDeleted) {
        setSong(song);
        setAuthorOfTheRating(authorOfTheRating);
        setRating(rating);
        this.creationTime = System.nanoTime();
        setAbilityToBeDeleted(abilityToBeDeleted);
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getAuthorOfTheRating() {
        return authorOfTheRating;
    }

    public void setAuthorOfTheRating(String authorOfTheRating) {
        this.authorOfTheRating = authorOfTheRating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isAbilityToBeDeleted() {
        return abilityToBeDeleted;
    }

    public void setAbilityToBeDeleted(boolean abilityToBeDeleted) {
        this.abilityToBeDeleted = abilityToBeDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingBox)) return false;
        RatingBox ratingBox = (RatingBox) o;
        return getCreationTime() == ratingBox.getCreationTime() &&
                isAbilityToBeDeleted() == ratingBox.isAbilityToBeDeleted() &&
                Objects.equals(getSong(), ratingBox.getSong()) &&
                Objects.equals(getAuthorOfTheRating(), ratingBox.getAuthorOfTheRating()) &&
                Objects.equals(getRating(), ratingBox.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSong(), getAuthorOfTheRating(), getRating(), getCreationTime(), isAbilityToBeDeleted());
    }
}
