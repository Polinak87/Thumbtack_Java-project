package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.model.RatingBox;

import java.util.UUID;

public class DeleteRatingDtoRequest {

    UUID token;
    RatingBox ratingBox;

    public DeleteRatingDtoRequest(UUID token, RatingBox ratingBox) {
        this.token = token;
        this.ratingBox = ratingBox;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public RatingBox getRatingBox() {
        return ratingBox;
    }

    public void setRatingBox(RatingBox ratingBox) {
        this.ratingBox = ratingBox;
    }
}
