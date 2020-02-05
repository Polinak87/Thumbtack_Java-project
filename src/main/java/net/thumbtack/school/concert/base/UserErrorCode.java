package net.thumbtack.school.concert.base;

public enum UserErrorCode {

    EMPTY_FIRST_NAME("Empty first name"),
    EMPTY_LAST_NAME("Empty last name"),
    EMPTY_LOGIN("Empty login"),
    EMPTY_PASSWORD("Empty password"),
    DOUBLE_LOGIN("This login has been already used"),
    SHORT_PASSWORD("Your password is shorter then 8 characters"),
    SHORT_LOGIN("Your login is shorter then 6 characters"),
    WRONG_LOGIN_OR_PASSWORD("This login or password is wrong"),
    INACTIVE_TOKEN("Your session is over, please login again"),
    EMPTY_SONG_NAME("Empty song's name"),
    EMPTY_SINGER("Empty singer's name"),
    WRONG_SINGER("You should write only one name as a singer"),
    EMPTY_COMPOSER("Empty composer's name"),
    EMPTY_AUTHOR_OF_THE_TEXT("Empty author's of the text name"),
    EMPTY_SONG_LENGTH("Song's length can't be empty or equal 0"),
    DOUBLE_SONG("This song has been already added"),
    EMPTY_SONG_LIST("There is no songs corresponding with specified parameters"),
    WRONG_RATING("Rating can't be less then one or more then five"),
    EMPTY_RATING("Empty rating"),
    RATING_DELETION_ERROR("You can't remove rating added by another user"),
    RATING_DELETION_ABILITY_ERROR("You can't remove rating of song added by you"),
    COMMENT_REJECTION_ERROR("You can't reject of comment added by another user"),
    COMMENT_CHANGE_ERROR ("You can't change comment added by another user"),
    SONG_DELETION_ERROR("You can't remove song added by another user");

    private String message;

    private UserErrorCode(String message) {
        this.message = message;
    }

    public String getErrorString() {
        return message;
    }
}
