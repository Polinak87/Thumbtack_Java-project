package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.*;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.model.SongComment;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.server.Server;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestComments extends TestBase {

    @Test
    public void testAddSongComment () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();

        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        String requestJsonString3 = gson.toJson(new AddSongCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(0), "Отличная песня!!!"));
        server.addSongComment(requestJsonString3);

        List<SongComment> songComments = DataBase.getInstance().getSongCommentList();
        assertEquals(1, songComments.size());
        assertEquals("KirStrad", songComments.get(0).getAuthour());
        assertEquals("Отличная песня!!!", songComments.get(0).getTextOfSongComment());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), songComments.get(0).getSongId());
        assertFalse(songComments.get(0).isCommToCommAvailability());
    }

    @Test
    public void testAddAndRejectionOfCommentToComment () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();

        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        String requestJsonString3 = gson.toJson(new AddSongCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(0), "Отличная песня!!!"));
        server.addSongComment(requestJsonString3);

        String requestJsonString4 = gson.toJson( new User("Наташа", "Бровкина", "NatBrov", "pampam%4"));
        RegisterUserDtoResponce rudResponce4 = gson.fromJson((server.registerUser(requestJsonString4)), RegisterUserDtoResponce.class);
        String requestJsonString5 = gson.toJson(new AddCommentToCommentDtoRequest(rudResponce4.getToken(), DataBase.getInstance().getSongCommentList().get(0),"Хорошая, только старовата..."));
        server.addCommentToComment(requestJsonString5);

        assertEquals(1, DataBase.getInstance().getSongCommentList().size());
        assertEquals("KirStrad", DataBase.getInstance().getSongCommentList().get(0).getAuthour());
        assertEquals("Отличная песня!!!", DataBase.getInstance().getSongCommentList().get(0).getTextOfSongComment());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), DataBase.getInstance().getSongCommentList().get(0).getSongId());
        assertTrue(DataBase.getInstance().getSongCommentList().get(0).isCommToCommAvailability());

        assertEquals(1, DataBase.getInstance().getCommentToCommentList().size());
        assertEquals("NatBrov", DataBase.getInstance().getCommentToCommentList().get(0).getAuthour());
        assertEquals("Хорошая, только старовата...", DataBase.getInstance().getCommentToCommentList().get(0).getTextOfCommentToComment());
        assertEquals(DataBase.getInstance().getSongCommentList().get(0).getSongCommentId(), DataBase.getInstance().getCommentToCommentList().get(0).getSongCommentId());

        String requestJsonString6 = gson.toJson(new RejectionOfCommentToCommentDtoRequest(rudResponce1.getToken(), DataBase.getInstance().getCommentToCommentList().get(0)));
        String responce = server.rejectionOfCommentToComment(requestJsonString6);
        assertEquals(UserErrorCode.COMMENT_REJECTION_ERROR, gson.fromJson(responce, UserException.class).getUserErrorCode());

        String requestJsonString7 = gson.toJson(new RejectionOfCommentToCommentDtoRequest(rudResponce4.getToken(), DataBase.getInstance().getCommentToCommentList().get(0)));
        server.rejectionOfCommentToComment(requestJsonString7);

        assertEquals(1, DataBase.getInstance().getCommentToCommentList().size());
        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getCommentToCommentList().get(0).getAuthour());
        assertEquals("Хорошая, только старовата...", DataBase.getInstance().getCommentToCommentList().get(0).getTextOfCommentToComment());
        assertEquals(DataBase.getInstance().getSongCommentList().get(0).getSongCommentId(), DataBase.getInstance().getCommentToCommentList().get(0).getSongCommentId());
    }

    @Test
    public void testChangeSongComment1 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();

        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        String requestJsonString3 = gson.toJson(new AddSongCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(0), "Отличная песня!!!"));
        server.addSongComment(requestJsonString3);

        assertEquals(1, DataBase.getInstance().getSongCommentList().size());
        assertEquals("KirStrad", DataBase.getInstance().getSongCommentList().get(0).getAuthour());
        assertEquals("Отличная песня!!!", DataBase.getInstance().getSongCommentList().get(0).getTextOfSongComment());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), DataBase.getInstance().getSongCommentList().get(0).getSongId());
        assertFalse(DataBase.getInstance().getSongCommentList().get(0).isCommToCommAvailability());

        String requestJsonString6 = gson.toJson(new ChangeSongCommentDtoRequest(rudResponce1.getToken(),DataBase.getInstance().getSongCommentList().get(0),"Супер песня"));
        String responce = server.changeSongComment(requestJsonString6);
        assertEquals(UserErrorCode.COMMENT_CHANGE_ERROR, gson.fromJson(responce, UserException.class).getUserErrorCode());

        String requestJsonString7 = gson.toJson(new ChangeSongCommentDtoRequest(rudResponce2.getToken(),DataBase.getInstance().getSongCommentList().get(0),"Супер песня"));
        server.changeSongComment(requestJsonString7);
        assertEquals(1, DataBase.getInstance().getSongCommentList().size());
        assertEquals("Супер песня", DataBase.getInstance().getSongCommentList().get(0).getTextOfSongComment());
        assertEquals("KirStrad", DataBase.getInstance().getSongCommentList().get(0).getAuthour());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), DataBase.getInstance().getSongCommentList().get(0).getSongId());
        assertFalse( DataBase.getInstance().getSongCommentList().get(0).isCommToCommAvailability());
    }

    @Test
    public void testChangeSongComment2 () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();

        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asdr1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        server.addSong(gson.toJson(asdr1));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        String requestJsonString3 = gson.toJson(new AddSongCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(0), "Отличная песня!!!"));
        server.addSongComment(requestJsonString3);

        String requestJsonString4 = gson.toJson( new User("Наташа", "Бровкина", "NatBrov", "pampam%4"));
        RegisterUserDtoResponce rudResponce4 = gson.fromJson((server.registerUser(requestJsonString4)), RegisterUserDtoResponce.class);
        String requestJsonString5 = gson.toJson(new AddCommentToCommentDtoRequest(rudResponce4.getToken(), DataBase.getInstance().getSongCommentList().get(0),"Хорошая, только старовата..."));
        server.addCommentToComment(requestJsonString5);

        assertEquals(1, DataBase.getInstance().getSongCommentList().size());
        assertEquals("KirStrad", DataBase.getInstance().getSongCommentList().get(0).getAuthour());
        assertEquals("Отличная песня!!!", DataBase.getInstance().getSongCommentList().get(0).getTextOfSongComment());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), DataBase.getInstance().getSongCommentList().get(0).getSongId());
        assertTrue(DataBase.getInstance().getSongCommentList().get(0).isCommToCommAvailability());

        assertEquals(1, DataBase.getInstance().getCommentToCommentList().size());
        assertEquals("NatBrov", DataBase.getInstance().getCommentToCommentList().get(0).getAuthour());
        assertEquals("Хорошая, только старовата...", DataBase.getInstance().getCommentToCommentList().get(0).getTextOfCommentToComment());
        assertEquals(DataBase.getInstance().getSongCommentList().get(0).getSongCommentId(), DataBase.getInstance().getCommentToCommentList().get(0).getSongCommentId());

        String requestJsonString6 = gson.toJson(new ChangeSongCommentDtoRequest(rudResponce2.getToken(),DataBase.getInstance().getSongCommentList().get(0),"Супер песня"));
        server.changeSongComment(requestJsonString6);
        assertEquals(2, DataBase.getInstance().getSongCommentList().size());
        assertEquals("Отличная песня!!!", DataBase.getInstance().getSongCommentList().get(0).getTextOfSongComment());
        assertEquals("Сообщество радиослушателей", DataBase.getInstance().getSongCommentList().get(0).getAuthour());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), DataBase.getInstance().getSongCommentList().get(0).getSongId());
        assertTrue(DataBase.getInstance().getSongCommentList().get(0).isCommToCommAvailability());
        assertEquals("Супер песня", DataBase.getInstance().getSongCommentList().get(1).getTextOfSongComment());
        assertEquals("KirStrad", DataBase.getInstance().getSongCommentList().get(1).getAuthour());
        assertEquals(DataBase.getInstance().getSongsList().get(0).getSongId(), DataBase.getInstance().getSongCommentList().get(1).getSongId());
        assertFalse(DataBase.getInstance().getSongCommentList().get(1).isCommToCommAvailability());
    }
}
