package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.AddManySongDtoRequest;
import net.thumbtack.school.concert.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.DeleteSongDtoRequest;
import net.thumbtack.school.concert.dto.responce.AddManySongDtoResponce;
import net.thumbtack.school.concert.dto.responce.AddSongDtoResponce;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.server.Server;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestSong extends TestBase {

    @Test
    public void testAddSong () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        User user = new User("Иван", "Петров", "IvanPetrov", "12345678");
        String requestJsonString1 = gson.toJson(user);
        RegisterUserDtoResponce rudr = gson.fromJson( server.registerUser(requestJsonString1), RegisterUserDtoResponce.class);

        AddSongDtoRequest asdr = new AddSongDtoRequest (rudr.getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", 429);
        String requestJsonString = gson.toJson(asdr);

        assertEquals("", gson.fromJson(server.addSong(requestJsonString), AddSongDtoResponce.class).getResponce());
        assertEquals("Я свободен...", DataBase.getInstance().getSongsList().get(0).getSongName());
        assertEquals("Кипелов",DataBase.getInstance().getSongsList().get(0).getSinger());
        assertEquals("Кипелов, Пушкина", DataBase.getInstance().getSongsList().get(0).getComposer());
        assertEquals("Кипелов",DataBase.getInstance().getSongsList().get(0).getAuthorOfTheText());
    }

    @Test
    public void testFailAddSong () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        User user1 = new User("Иван", "Петров", "IvanPetrov", "12345678");
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(user1);
        String requestJsonString2 = server.registerUser(requestJsonString1);

        AddSongDtoRequest asdr1 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), null, "Кипелов", "Кипелов, Пушкина", "Кипелов", 429);
        String responce1 = server.addSong(gson.toJson(asdr1));
        assertEquals(UserErrorCode.EMPTY_SONG_NAME, gson.fromJson(responce1, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr2 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "  ", "Кипелов, Пушкина", "Кипелов", 429);
        String responce2 = server.addSong(gson.toJson(asdr2));
        assertEquals(UserErrorCode.EMPTY_SINGER, gson.fromJson(responce2, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr3 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", null, "Кипелов", 429);
        String responce3 = server.addSong(gson.toJson(asdr3));
        assertEquals(UserErrorCode.EMPTY_COMPOSER, gson.fromJson(responce3, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr4 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "   ", 429);
        String responce4 = server.addSong(gson.toJson(asdr4));
        assertEquals(UserErrorCode.EMPTY_AUTHOR_OF_THE_TEXT, gson.fromJson(responce4, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr5 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", 0);
        String responce5 = server.addSong(gson.toJson(asdr5));
        assertEquals(UserErrorCode.EMPTY_SONG_LENGTH, gson.fromJson(responce5, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr6 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", null);
        String responce6 = server.addSong(gson.toJson(asdr6));
        assertEquals(UserErrorCode.EMPTY_SONG_LENGTH, gson.fromJson(responce6, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr7 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов, Маврин", "Кипелов, Пушкина", "Кипелов", 0);
        String responce7 = server.addSong(gson.toJson(asdr7));
        assertEquals(UserErrorCode.WRONG_SINGER, gson.fromJson(responce7, UserException.class).getUserErrorCode());

        AddSongDtoRequest asdr8 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", 429);
        String responce8 = server.addSong(gson.toJson(asdr8));
        AddSongDtoRequest asdr9 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", 429);
        String responce9 = server.addSong(gson.toJson(asdr9));
        assertEquals(UserErrorCode.DOUBLE_SONG, gson.fromJson(responce9, UserException.class).getUserErrorCode());

        server.logoutUser(requestJsonString2);
        AddSongDtoRequest asdr10 = new AddSongDtoRequest (gson.fromJson(requestJsonString2, RegisterUserDtoResponce.class).getToken(), "Я свободен...", "Кипелов", "Кипелов, Пушкина", "Кипелов", 429);
        String responce10 = server.addSong(gson.toJson(asdr10));
        assertEquals(UserErrorCode.INACTIVE_TOKEN,  gson.fromJson(responce10, UserException.class).getUserErrorCode());
    }



    @Test
    public void testDeleteSong () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asd2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        server.addSong(gson.toJson(asd1));
        server.addSong(gson.toJson(asd2));

        String requestJsonString2 = gson.toJson( new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd3 = new AddSongDtoRequest(rudResponce2.getToken(),"Восьмиклассница", "Цой", "Цой, Рыбин", "Цой, Рыбин", 162);
        server.addSong(gson.toJson(asd3));

        assertEquals(3, DataBase.getInstance().getSongsList().size());
        assertEquals(3, DataBase.getInstance().getRatingBoxeList().size());

        String requestJsonString3 = gson.toJson(new DeleteSongDtoRequest(rudResponce1.getToken(), DataBase.getInstance().getSongsList().get(0)));
        server.deleteSong(requestJsonString3);
        assertEquals(2, DataBase.getInstance().getSongsList().size());
        assertEquals("Вершина", DataBase.getInstance().getSongsList().get(0).getSongName());
        assertEquals("Восьмиклассница", DataBase.getInstance().getSongsList().get(1).getSongName());
        assertEquals(2, DataBase.getInstance().getRatingBoxeList().size());
        assertEquals("Вершина", DataBase.getInstance().getRatingBoxeList().get(0).getSong().getSongName());
        assertEquals("Восьмиклассница", DataBase.getInstance().getRatingBoxeList().get(1).getSong().getSongName());

        String requestJsonString4 = gson.toJson(new DeleteSongDtoRequest(rudResponce1.getToken(), DataBase.getInstance().getSongsList().get(1)));
        server.deleteSong(requestJsonString4);
        UserException ue = gson.fromJson(server.deleteSong(requestJsonString4), UserException.class);
        assertEquals("You can't remove song added by another user", ue.getUserErrorCode().getErrorString());

        AddRatingDtoRequest ardr = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(1),rudResponce1.getToken(), 4);
        server.addOrChangeRating(gson.toJson(ardr));
        assertEquals(3, DataBase.getInstance().getRatingBoxeList().size());
        String requestJsonString5 = gson.toJson(new DeleteSongDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(1)));
        server.deleteSong(requestJsonString5);
        assertEquals(2, DataBase.getInstance().getSongsList().size());
        assertEquals("Вершина", DataBase.getInstance().getSongsList().get(0).getSongName());
        assertEquals("Восьмиклассница", DataBase.getInstance().getSongsList().get(1).getSongName());
        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getSongsList().get(1).getAuthorOfTheOffer());
        assertEquals(4, DataBase.getInstance().getSongsList().get(1).getSumRating());
    }

    @Test
    public void testAddManySong () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);

        AddSongDtoRequest asd1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asd2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        AddSongDtoRequest asd3 = new AddSongDtoRequest(rudResponce1.getToken(), "Ну вот исчезла дрожь в руках...", "Высоцкий", "Высоцкий", "Высоцкий", 170);

        List<AddSongDtoRequest> songsForAdd = new ArrayList<>();
        songsForAdd.add(asd1);
        songsForAdd.add(asd2);
        songsForAdd.add(asd3);
        server.addManySong(gson.toJson(new AddManySongDtoRequest(songsForAdd)));

        assertEquals(3, DataBase.getInstance().getSongsList().size());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getSongsList().get(0).getSongName());
        assertEquals("Вершина", DataBase.getInstance().getSongsList().get(1).getSongName());
        assertEquals("Ну вот исчезла дрожь в руках...", DataBase.getInstance().getSongsList().get(2).getSongName());
    }

}
