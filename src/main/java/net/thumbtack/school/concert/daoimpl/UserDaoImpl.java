package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.model.User;
import java.util.UUID;



public class UserDaoImpl implements UserDao {

    @Override
    public  UUID insert (User user) throws UserException {
        return DataBase.getInstance().insert (user);
    }

    @Override
    public  UUID loginUser (LoginUserDtoRequest ludr) throws UserException {
        return DataBase.getInstance().loginUser(ludr);
    }

    @Override
    public  String logoutUser (LogoutUserDtoRequest ludr) throws UserException {
        return DataBase.getInstance().logoutUser(ludr);
    }

    @Override
    public String deleteUser (UUID token) throws UserException {
        return DataBase.getInstance().deleteUser(token);
    }

    @Override
    public boolean isActiveToken (UUID token) throws UserException {
        return DataBase.getInstance().isActiveToken(token);
    }

    @Override
    public String getLoginByToken (UUID token) {
        return DataBase.getInstance().getLoginByToken(token);
    }



}
