package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.daoimpl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.DeleteUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.dto.responce.DeleteUserDtoResponce;
import net.thumbtack.school.concert.dto.responce.LoginUserDtoResponce;
import net.thumbtack.school.concert.dto.responce.LogoutUserDtoResponce;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.model.User;


public class UserService {
    private static final int MIN_PASSWORD_LEN = 8;
    private static final int MIN_LOGIN_LEN = 6;
    protected static UserDao userDao = new UserDaoImpl();


//    private UserDaoImpl userDaoImpl;

    public static String registerUser (String requestJsonString) throws UserException {
        String gsonText = null;
        User user = null;
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = gson.fromJson(requestJsonString,RegisterUserDtoRequest.class);
        Gson gson2 = new Gson();
        try {
            checkFirstName (rudr.getFirstName());
        } catch (UserException e) {
            return gsonText = gson2.toJson(e);
        }
        try {
            checkLastName(rudr.getLastName());
        } catch (UserException e) {
            return gsonText = gson2.toJson(e);
        }
        try {
            checkLogin(rudr.getLogin());
        } catch (UserException e) {
            return gsonText = gson2.toJson(e);
        }
        try {
            checkPassword(rudr.getPassword());
        } catch (UserException e) {
            return gsonText = gson2.toJson(e);
        }
        user = new User(rudr.getFirstName(), rudr.getLastName(), rudr.getLogin(), rudr.getPassword());
        try {
            RegisterUserDtoResponce registerUserDtoResponce = new RegisterUserDtoResponce(userDao.insert(user));
            gsonText = gson2.toJson(registerUserDtoResponce);
        } catch (UserException e) {
            return gsonText = gson2.toJson(e);
        }

        return gsonText;
    }

    public static void checkFirstName(String firstName) throws UserException {
        if(firstName == null || firstName.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_FIRST_NAME);
    }

    public static void checkLastName(String lastName) throws UserException {
        if(lastName == null || lastName.replaceAll(" ", "").length() == 0 )
            throw new UserException(UserErrorCode.EMPTY_LAST_NAME);
    }

    public static void checkLogin(String login) throws UserException {
        if(login == null || login.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_LOGIN);
        if (login.length() < MIN_LOGIN_LEN)
        throw new UserException(UserErrorCode.SHORT_LOGIN);
    }

    public static void checkPassword(String password) throws UserException {
        if(password == null || password.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_PASSWORD);
        if (password.length() < MIN_PASSWORD_LEN)
            throw new UserException(UserErrorCode.SHORT_PASSWORD);
    }

    public static String loginUser (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        LoginUserDtoRequest ludr = gson.fromJson(requestJsonString,LoginUserDtoRequest.class);
        try {
            LoginUserDtoResponce loginUserDtoResponce = new LoginUserDtoResponce(userDao.loginUser(ludr));
            gsonText = gson.toJson(loginUserDtoResponce);
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String logoutUser (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        LogoutUserDtoRequest ludr = gson.fromJson(requestJsonString, LogoutUserDtoRequest.class);
        try {
            if (userDao.isActiveToken(ludr.getToken())) {
                LogoutUserDtoResponce logoutUserDtoResponce = new LogoutUserDtoResponce(userDao.logoutUser(ludr));
                gsonText = gson.toJson(logoutUserDtoResponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String deleteUser (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        DeleteUserDtoRequest request = gson.fromJson(requestJsonString, DeleteUserDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())){
                DeleteUserDtoResponce responce = new DeleteUserDtoResponce(userDao.deleteUser(request.getToken()));
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }
}
