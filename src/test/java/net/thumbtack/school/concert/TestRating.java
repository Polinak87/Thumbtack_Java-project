package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.DeleteRatingDtoRequest;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.server.Server;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestRating extends TestBase{

    @Test
    public void testAddRating () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asdr2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        AddSongDtoRequest asdr3 = new AddSongDtoRequest(rudResponce1.getToken(), "Ну вот исчезла дрожь в руках...", "Высоцкий", "Высоцкий", "Высоцкий", 170);
        server.addSong(gson.toJson(asdr1));
        server.addSong(gson.toJson(asdr2));
        server.addSong(gson.toJson(asdr3));

        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(0).getSong().getSongName());
        assertEquals("DimaErmol",DataBase.getInstance().getRatingBoxeList().get(0).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5),DataBase.getInstance().getRatingBoxeList().get(0).getRating());
        assertEquals("Вершина",DataBase.getInstance().getRatingBoxeList().get(1).getSong().getSongName());
        assertEquals("DimaErmol",DataBase.getInstance().getRatingBoxeList().get(1).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5),DataBase.getInstance().getRatingBoxeList().get(1).getRating());
        assertEquals("Ну вот исчезла дрожь в руках...",DataBase.getInstance().getRatingBoxeList().get(2).getSong().getSongName());
        assertEquals("DimaErmol",DataBase.getInstance().getRatingBoxeList().get(2).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5),DataBase.getInstance().getRatingBoxeList().get(2).getRating());

        String requestJsonString2 = gson.toJson( new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);

        AddRatingDtoRequest rdr2_1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0),rudResponce2.getToken(), 2);
        server.addOrChangeRating(gson.toJson(rdr2_1));
        assertEquals("Песня про Жирафа",DataBase.getInstance().getRatingBoxeList().get(3).getSong().getSongName());
        assertEquals("KirStrad",DataBase.getInstance().getRatingBoxeList().get(3).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(2),DataBase.getInstance().getRatingBoxeList().get(3).getRating());

        AddRatingDtoRequest rdr2_2 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(1),rudResponce2.getToken(), 4);
        server.addOrChangeRating(gson.toJson(rdr2_2));
        assertEquals("Вершина",DataBase.getInstance().getRatingBoxeList().get(4).getSong().getSongName());
        assertEquals("KirStrad",DataBase.getInstance().getRatingBoxeList().get(4).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(4),DataBase.getInstance().getRatingBoxeList().get(4).getRating());

        AddRatingDtoRequest rdr2_3 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(2),rudResponce2.getToken(), 0);
        String responce1 = server.addOrChangeRating(gson.toJson(rdr2_3));
        assertEquals(UserErrorCode.WRONG_RATING,  gson.fromJson(responce1, UserException.class).getUserErrorCode());

        AddRatingDtoRequest rdr2_4 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(2),rudResponce2.getToken(), null);
        String responce2 = server.addOrChangeRating(gson.toJson(rdr2_4));
        assertEquals(UserErrorCode.EMPTY_RATING,  gson.fromJson(responce2, UserException.class).getUserErrorCode());

        AddRatingDtoRequest rdr2_5 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(2),rudResponce2.getToken(), 1);
        server.addOrChangeRating(gson.toJson(rdr2_5));
        assertEquals("Ну вот исчезла дрожь в руках...",DataBase.getInstance().getRatingBoxeList().get(5).getSong().getSongName());
        assertEquals("KirStrad",DataBase.getInstance().getRatingBoxeList().get(5).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(1),DataBase.getInstance().getRatingBoxeList().get(5).getRating());

        assertEquals(6, DataBase.getInstance().getRatingBoxeList().size());
    }

    @Test
    public void testChangeRating () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        AddRatingDtoRequest rdr1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0), rudResponce2.getToken(), 2);
        server.addOrChangeRating(gson.toJson(rdr1));
        AddRatingDtoRequest rdr2 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0), rudResponce2.getToken(), 3);
        server.addOrChangeRating(gson.toJson(rdr2));

        assertEquals(2, DataBase.getInstance().getRatingBoxeList().size());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(0).getSong().getSongName());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(0).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(0).getRating());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(1).getSong().getSongName());
        assertEquals("KirStrad", DataBase.getInstance().getRatingBoxeList().get(1).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(3), DataBase.getInstance().getRatingBoxeList().get(1).getRating());
    }

    @Test
    public void testDeleteRating () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        AddRatingDtoRequest ardr1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0), rudResponce2.getToken(), 2);
        server.addOrChangeRating(gson.toJson(ardr1));
        assertEquals(2, DataBase.getInstance().getRatingBoxeList().size());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(0).getSong().getSongName());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(0).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(0).getRating());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(1).getSong().getSongName());
        assertEquals("KirStrad", DataBase.getInstance().getRatingBoxeList().get(1).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(2), DataBase.getInstance().getRatingBoxeList().get(1).getRating());

        DeleteRatingDtoRequest drdr1 = new DeleteRatingDtoRequest(rudResponce1.getToken(),DataBase.getInstance().getRatingBoxeList().get(0));
        String responce1 = server.deleteRating(gson.toJson(drdr1));
        assertEquals(UserErrorCode.RATING_DELETION_ABILITY_ERROR, gson.fromJson(responce1, UserException.class).getUserErrorCode());

        DeleteRatingDtoRequest drdr2 = new DeleteRatingDtoRequest(rudResponce1.getToken(),DataBase.getInstance().getRatingBoxeList().get(1));
        String responce2 = server.deleteRating(gson.toJson(drdr2));
        assertEquals(UserErrorCode.RATING_DELETION_ERROR, gson.fromJson(responce2, UserException.class).getUserErrorCode());

        DeleteRatingDtoRequest drdr3 = new DeleteRatingDtoRequest(rudResponce2.getToken(),DataBase.getInstance().getRatingBoxeList().get(1));
        server.deleteRating(gson.toJson(drdr3));
        assertEquals(1, DataBase.getInstance().getRatingBoxeList().size());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(0).getSong().getSongName());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(0).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(0).getRating());
    }

    @Test
    public void testFailDeleteRating () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        assertEquals(1, DataBase.getInstance().getRatingBoxeList().size());
        assertEquals("Песня про Жирафа", DataBase.getInstance().getRatingBoxeList().get(0).getSong().getSongName());
        assertEquals("DimaErmol", DataBase.getInstance().getRatingBoxeList().get(0).getAuthorOfTheRating());
        assertEquals(Integer.valueOf(5), DataBase.getInstance().getRatingBoxeList().get(0).getRating());

        DeleteRatingDtoRequest rdr1 = new DeleteRatingDtoRequest(rudResponce1.getToken(),DataBase.getInstance().getRatingBoxeList().get(0));
        UserException ux = gson.fromJson(server.deleteRating(gson.toJson(rdr1)),UserException.class);
        assertEquals("You can't remove rating of song added by you",ux.getUserErrorCode().getErrorString());
    }

    @Test
    public void testSumRating () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asd2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        AddSongDtoRequest asd3 = new AddSongDtoRequest(rudResponce1.getToken(), "Ну вот исчезла дрожь в руках...", "Высоцкий", "Высоцкий", "Высоцкий", 170);
        server.addSong(gson.toJson(asd1));
        server.addSong(gson.toJson(asd2));
        server.addSong(gson.toJson(asd3));

        String requestJsonString2 = gson.toJson( new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        String requestJsonString3 = gson.toJson( new User("Наташа", "Бровкина", "NatBrov", "pampam%4"));
        RegisterUserDtoResponce rudResponce3 = gson.fromJson((server.registerUser(requestJsonString3)), RegisterUserDtoResponce.class);

        AddRatingDtoRequest rdr2_1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0),rudResponce2.getToken(), 2);
        server.addOrChangeRating(gson.toJson(rdr2_1));
        AddRatingDtoRequest rdr3_1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0),rudResponce3.getToken(), 3);
        server.addOrChangeRating(gson.toJson(rdr3_1));
        AddRatingDtoRequest rdr2_2 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(1),rudResponce2.getToken(), 4);
        server.addOrChangeRating(gson.toJson(rdr2_2));
        AddRatingDtoRequest rdr3_2 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(1),rudResponce3.getToken(), 2);
        server.addOrChangeRating(gson.toJson(rdr3_2));
        AddRatingDtoRequest rdr2_3 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(2),rudResponce2.getToken(), 1);
        server.addOrChangeRating(gson.toJson(rdr2_3));
        AddRatingDtoRequest rdr3_3 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(2),rudResponce3.getToken(), 1);
        server.addOrChangeRating(gson.toJson(rdr3_3));

        DataBase.getInstance().sumRating();
        assertEquals(10 ,DataBase.getInstance().getSongsList().get(0).getSumRating());
        assertEquals(11 ,DataBase.getInstance().getSongsList().get(1).getSumRating());
        assertEquals(7,DataBase.getInstance().getSongsList().get(2).getSumRating());
    }
}
