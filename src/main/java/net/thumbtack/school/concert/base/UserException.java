package net.thumbtack.school.concert.base;

public class UserException extends Exception {

    private UserErrorCode userErrorCode;

    public UserErrorCode getUserErrorCode() {
        return userErrorCode;
    }

    public  UserException (UserErrorCode userErrorCode) {
        this.userErrorCode = userErrorCode;
    }
}
