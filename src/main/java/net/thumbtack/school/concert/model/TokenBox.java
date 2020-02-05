package net.thumbtack.school.concert.model;

import net.thumbtack.school.concert.base.TokenState;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.model.User;

import java.util.Objects;
import java.util.UUID;

public class TokenBox {

    private UUID token;
    private String login;
    private TokenState state;

    public TokenBox (UUID token, String login) {
        setToken(token);
        setLogin(login);
        this.state = TokenState.ACTIVE;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public TokenState getState() {
        return state;
    }

    public void isInactiveState() throws UserException {
        if (state == TokenState.INACTIVE)
            throw new UserException(UserErrorCode.INACTIVE_TOKEN);
        this.state = TokenState.INACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenBox)) return false;
        TokenBox tokenBox = (TokenBox) o;
        return Objects.equals(token, tokenBox.token) &&
                Objects.equals(login, tokenBox.login) &&
                state == tokenBox.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, login, state);
    }
}
