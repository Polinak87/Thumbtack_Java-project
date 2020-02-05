package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.daoimpl.UserDaoImpl;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.*;
import net.thumbtack.school.concert.dto.responce.LoginUserDtoResponce;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.server.Server;
import net.thumbtack.school.concert.service.UserService;
import net.thumbtack.school.concert.model.User;
import org.junit.Test;

import java.util.UUID;

import static net.thumbtack.school.concert.base.TokenState.ACTIVE;
import static net.thumbtack.school.concert.base.TokenState.INACTIVE;
import static org.junit.Assert.assertEquals;


public class TestUser extends TestBase {

    @Test
    public void testRegisterUser () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaS", "12345678");
        String requestJsonString = gson.toJson(rudr);
        server.registerUser(requestJsonString);
        assertEquals("Вася",DataBase.getInstance().getUsersList().get(0).getFirstName());
        assertEquals("Сидоров",DataBase.getInstance().getUsersList().get(0).getLastName());
        assertEquals("VasyaS",DataBase.getInstance().getUsersList().get(0).getLogin());
        assertEquals("12345678",DataBase.getInstance().getUsersList().get(0).getPassword());
    }

    @Test
    public void testFailRegisterUser_1 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("   ", "Сидоров", "VasyaSidorov", "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty first name", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_2 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest(null, "Сидоров", "VasyaSidorov", "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty first name", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_3 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "   ", "VasyaSidorov", "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty last name", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_4 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", null, "VasyaSidorov", "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty last name", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_5 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "   ", "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty login", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_6 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", null, "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty login", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_7 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasS", "12345678");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Your login is shorter then 6 characters", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_8 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaSidorov", "   ");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty password", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterUser_9 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaSidorov", null);
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Empty password", userException.getUserErrorCode().getErrorString());
    }


    @Test
    public void testFailRegisterUser_10 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaSidorov", "123456");
        String requestJsonString = gson.toJson(rudr);
        UserException userException = gson.fromJson(server.registerUser(requestJsonString), UserException.class);
        assertEquals("Your password is shorter then 8 characters", userException.getUserErrorCode().getErrorString());
    }

    @Test
    public void testFailRegisterDoubleUser () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr1 = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaSidorov", "12345678");
        String requestJsonString1 = gson.toJson(rudr1);
        server.registerUser(requestJsonString1);
        assertEquals(1, DataBase.getInstance().getUsersList().size());
        assertEquals("Вася",DataBase.getInstance().getUsersList().get(0).getFirstName());
        assertEquals("Сидоров",DataBase.getInstance().getUsersList().get(0).getLastName());
        assertEquals("VasyaSidorov",DataBase.getInstance().getUsersList().get(0).getLogin());
        assertEquals("12345678",DataBase.getInstance().getUsersList().get(0).getPassword());

        RegisterUserDtoRequest rudr2 = new RegisterUserDtoRequest("Василий", "Сидоров", "VasyaSidorov", "12345678910");
        String requestJsonString2 = gson.toJson(rudr2);
        server.registerUser(requestJsonString2);
        UserException userException2 = gson.fromJson(server.registerUser(requestJsonString2), UserException.class);
        assertEquals("This login has been already used", userException2.getUserErrorCode().getErrorString());
    }

    @Test
    public void testLoginUser () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaS", "12345678");
        String requestJsonString1 = gson.toJson(rudr);
        server.registerUser(requestJsonString1);
        LoginUserDtoRequest ludr = new LoginUserDtoRequest("VasyaS", "12345678");
        String requestJsonString2 = gson.toJson(ludr);
        assertEquals(UUID.class, gson.fromJson(server.loginUser(requestJsonString2), LoginUserDtoResponce.class).getToken().getClass());
        assertEquals(UUID.class, DataBase.getInstance().getTokenBoxeList().get(0).getToken().getClass());
        assertEquals("VasyaS", DataBase.getInstance().getTokenBoxeList().get(0).getLogin());
        assertEquals(ACTIVE, DataBase.getInstance().getTokenBoxeList().get(0).getState());
    }

    @Test
    public void testFailLoginUser () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaS", "12345678");
        String requestJsonString = gson.toJson(rudr);
        server.registerUser(requestJsonString);

        LoginUserDtoRequest ludr1 = new LoginUserDtoRequest("VasyaS", "123456789");
        String requestJsonString1 = gson.toJson(ludr1);
        UserException userException1 = gson.fromJson(server.loginUser(requestJsonString1), UserException.class);
        assertEquals("This login or password is wrong", userException1.getUserErrorCode().getErrorString());

        LoginUserDtoRequest ludr2 = new LoginUserDtoRequest("VasyaSid", "12345678");
        String requestJsonString2 = gson.toJson(ludr2);
        UserException userException2 = gson.fromJson(server.loginUser(requestJsonString2), UserException.class);
        assertEquals("This login or password is wrong", userException2.getUserErrorCode().getErrorString());
    }

    @Test
    public void testlLogoutUser () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaS", "12345678");
        String requestJsonString1 = gson.toJson(rudr);

        String requestJsonString2 = server.registerUser(requestJsonString1);
        server.logoutUser(requestJsonString2);
        assertEquals(gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), DataBase.getInstance().getTokenBoxeList().get(0).getToken());
        assertEquals("VasyaS", DataBase.getInstance().getTokenBoxeList().get(0).getLogin());
        assertEquals(INACTIVE, DataBase.getInstance().getTokenBoxeList().get(0).getState());
    }

    @Test
    public void testDeleteUser1() throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        RegisterUserDtoRequest rudr = new RegisterUserDtoRequest("Вася", "Сидоров", "VasyaS", "12345678");
        String requestJsonString = gson.toJson(rudr);
        String responceJsonString = server.registerUser(requestJsonString);
        UUID token = gson.fromJson(responceJsonString, RegisterUserDtoResponce.class).getToken();
        assertEquals(1, DataBase.getInstance().getUsersList().size());
        assertEquals("Вася", DataBase.getInstance().getUsersList().get(0).getFirstName());
        assertEquals("Сидоров", DataBase.getInstance().getUsersList().get(0).getLastName());
        assertEquals("VasyaS", DataBase.getInstance().getUsersList().get(0).getLogin());
        assertEquals("12345678", DataBase.getInstance().getUsersList().get(0).getPassword());

        AddSongDtoRequest asdr = new AddSongDtoRequest(token, "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", 429);
        server.addSong(gson.toJson(asdr));
        assertEquals(1, DataBase.getInstance().getRatingBoxeList().size());
        assertEquals(1, DataBase.getInstance().getSongsList().size());
        assertEquals("Я свободен...", DataBase.getInstance().getSongsList().get(0).getSongName());
        assertEquals("Кипелов", DataBase.getInstance().getSongsList().get(0).getSinger());
        assertEquals("Кипелов, Пушкина", DataBase.getInstance().getSongsList().get(0).getComposer());
        assertEquals("Кипелов", DataBase.getInstance().getSongsList().get(0).getAuthorOfTheText());

        server.deleteUser(gson.toJson(new DeleteUserDtoRequest(token)));
        assertEquals(0, DataBase.getInstance().getUsersList().size());
        assertEquals(0, DataBase.getInstance().getRatingBoxeList().size());
    }

    @Test
    public void testDeleteUser2() throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new RegisterUserDtoRequest("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asd2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        AddSongDtoRequest asd3 = new AddSongDtoRequest(rudResponce1.getToken(), "Ну вот исчезла дрожь в руках...", "Высоцкий", "Высоцкий", "Высоцкий", 170);
        server.addSong(gson.toJson(asd1));
        server.addSong(gson.toJson(asd2));
        server.addSong(gson.toJson(asd3));

        String requestJsonString2 = gson.toJson(new RegisterUserDtoRequest("Любовь", "Наумова", "LubaYm", "123456789"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd4 = new AddSongDtoRequest(rudResponce2.getToken(),"Бегущая по волнам", "МакSим", "Максимова", "Максимова", 183);
        server.addSong(gson.toJson(asd4));

        String requestJsonString3 = gson.toJson( new RegisterUserDtoRequest("Светлана", "Александрова", "SvetaA", "1234567890"));
        RegisterUserDtoResponce rudResponce3 = gson.fromJson((server.registerUser(requestJsonString3)), RegisterUserDtoResponce.class);

        AddRatingDtoRequest rdr2_1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0),rudResponce2.getToken(), 1);
        server.addOrChangeRating(gson.toJson(rdr2_1));
        AddRatingDtoRequest rdr2_2 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(1),rudResponce2.getToken(), 4);
        server.addOrChangeRating(gson.toJson(rdr2_2));

        AddSongCommentDtoRequest ascd1 = new AddSongCommentDtoRequest(rudResponce3.getToken(), DataBase.getInstance().getSongsList().get(0), "Не лучшая песня Высоцкого)");
        server.addSongComment(gson.toJson(ascd1));
        AddCommentToCommentDtoRequest accd2= new AddCommentToCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongCommentList().get(0), "Согласна");
        server.addCommentToComment(gson.toJson(accd2));
        AddSongCommentDtoRequest ascd3 = new AddSongCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(2), "Любимая песня всех альпинистов");
        server.addSongComment(gson.toJson(ascd3));
        AddCommentToCommentDtoRequest accd4 = new AddCommentToCommentDtoRequest(rudResponce3.getToken(), DataBase.getInstance().getSongCommentList().get(1), "Чертовски верно");
        server.addCommentToComment(gson.toJson(accd4));

        assertEquals(3, DataBase.getInstance().getUsersMap().size());
        assertEquals(4, DataBase.getInstance().getSongsMap().size());
        assertEquals(6, DataBase.getInstance().getRatingBoxesMap().size());
        assertEquals(2, DataBase.getInstance().getSongCommentsMap().size());
        assertEquals(2, DataBase.getInstance().getCommentsToCommentsMap().size());


        server.deleteUser(gson.toJson(new DeleteUserDtoRequest(rudResponce2.getToken())));
        assertEquals(2, DataBase.getInstance().getUsersMap().size());
        assertEquals(3, DataBase.getInstance().getSongsMap().size());
        assertEquals(5, DataBase.getInstance().getRatingBoxesMap().size());
        assertEquals(2, DataBase.getInstance().getSongCommentsMap().size());
        assertEquals(2, DataBase.getInstance().getCommentsToCommentsMap().size());

        assertEquals("DimaErmol", DataBase.getInstance().getSongsList().get(0).getAuthorOfTheOffer());
        assertEquals("DimaErmol", DataBase.getInstance().getSongsList().get(1).getAuthorOfTheOffer());
        assertEquals("DimaErmol", DataBase.getInstance().getSongsList().get(2).getAuthorOfTheOffer());

        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getRatingBoxeList().get(3).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(1), DataBase.getInstance().getRatingBoxeList().get(3).getRating());
        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getRatingBoxeList().get(4).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(4), DataBase.getInstance().getRatingBoxeList().get(4).getRating());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(0).getAuthorOfTheRating());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(1).getAuthorOfTheRating());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(2).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(0).getRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(1).getRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(2).getRating());

        assertEquals("SvetaA", DataBase.getInstance().getSongCommentList().get(0).getAuthour());
        assertEquals("Не лучшая песня Высоцкого)", DataBase.getInstance().getSongCommentList().get(0).getTextOfSongComment());
        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getSongCommentList().get(1).getAuthour());
        assertEquals("Любимая песня всех альпинистов", DataBase.getInstance().getSongCommentList().get(1).getTextOfSongComment());

        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getCommentToCommentList().get(0).getAuthour());
        assertEquals("Согласна", DataBase.getInstance().getCommentToCommentList().get(0).getTextOfCommentToComment());
        assertEquals("SvetaA", DataBase.getInstance().getCommentToCommentList().get(1).getAuthour());
        assertEquals("Чертовски верно", DataBase.getInstance().getCommentToCommentList().get(1).getTextOfCommentToComment());

    }
}
