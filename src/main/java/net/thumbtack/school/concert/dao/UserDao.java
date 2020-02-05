package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.model.User;

import java.util.UUID;

public interface UserDao {

    UUID insert (User user) throws UserException;

    UUID loginUser (LoginUserDtoRequest ludr) throws UserException;

    String logoutUser (LogoutUserDtoRequest ludr) throws UserException;

    String deleteUser (UUID token) throws UserException;

    boolean isActiveToken (UUID token) throws UserException;

    String getLoginByToken (UUID token);

}
