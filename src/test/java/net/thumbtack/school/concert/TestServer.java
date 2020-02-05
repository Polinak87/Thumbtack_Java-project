package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.dto.request.AddCommentToCommentDtoRequest;
import net.thumbtack.school.concert.dto.request.AddRatingDtoRequest;
import net.thumbtack.school.concert.dto.request.AddSongCommentDtoRequest;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.model.*;
import net.thumbtack.school.concert.server.Server;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestServer extends TestBase {

    @Test
    public void testStartServer () throws IOException {
        Server server = new Server();
        server.startServer(null);
        assertTrue(server.getServerIsActive());
        assertEquals(0, DataBase.getInstance().getUsersMap().size());
        assertEquals(0, DataBase.getInstance().getSongsMap().size());
        assertEquals(0, DataBase.getInstance().getTokenBoxesMap().size());
        assertEquals(0, DataBase.getInstance().getRatingBoxesMap().size());
        assertEquals(0, DataBase.getInstance().getSongCommentsMap().size());
        assertEquals(0, DataBase.getInstance().getCommentsToCommentsMap().size());
    }

    @Test
    public void testStopServer () throws IOException {
        Server server = new Server();
        server.stopServer(null);
        assertFalse(server.getServerIsActive());
    }

    @Test
    public void testStartAndStopServer () throws IOException, UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asd2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        AddSongDtoRequest asd3 = new AddSongDtoRequest(rudResponce1.getToken(), "Ну вот исчезла дрожь в руках...", "Высоцкий", "Высоцкий", "Высоцкий", 170);
        AddSongDtoRequest asd4 = new AddSongDtoRequest(rudResponce1.getToken(), "Баллада о любви", "Высоцкий", "Высоцкий", "Высоцкий", 305);
        server.addSong(gson.toJson(asd1));
        server.addSong(gson.toJson(asd2));
        server.addSong(gson.toJson(asd3));
        server.addSong(gson.toJson(asd4));

        String requestJsonString2 = gson.toJson( new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd8 = new AddSongDtoRequest(rudResponce2.getToken(),"Восьмиклассница", "Цой", "Цой, Рыбин", "Цой, Рыбин", 162);
        AddSongDtoRequest asd9 = new AddSongDtoRequest(rudResponce2.getToken(),"Видели ночь", "Цой", "Цой, Науменко", "Цой, Науменко", 131);
        server.addSong(gson.toJson(asd8));
        server.addSong(gson.toJson(asd9));

        String requestJsonString3 = gson.toJson( new User("Наташа", "Бровкина", "NatBrov", "pampam%4"));
        RegisterUserDtoResponce rudResponce3 = gson.fromJson((server.registerUser(requestJsonString3)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd12 = new AddSongDtoRequest(rudResponce3.getToken(),"Я уеду жить в Лондон", "Лепс", "Лепс, Тимати", "Лепс, Тимати", 264);
        AddSongDtoRequest asd13 = new AddSongDtoRequest(rudResponce3.getToken(),"Самый лучший день", "Лепс", "Шапиро", "Шапиро", 302);
        AddSongDtoRequest asd14 = new AddSongDtoRequest(rudResponce3.getToken(),"Я счастливый", "Лепс", "Дубинский", "Паренко", 222);
        server.addSong(gson.toJson(asd12));
        server.addSong(gson.toJson(asd13));
        server.addSong(gson.toJson(asd14));

        AddRatingDtoRequest rdr1_8 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(7),rudResponce1.getToken(), 5);
        server.addOrChangeRating(gson.toJson(rdr1_8));
        AddRatingDtoRequest rdr1_9 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(8),rudResponce1.getToken(), 5);
        server.addOrChangeRating(gson.toJson(rdr1_9));

        AddRatingDtoRequest rdr2_1 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(0),rudResponce2.getToken(), 2);
        server.addOrChangeRating(gson.toJson(rdr2_1));
        AddRatingDtoRequest rdr2_2 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(1),rudResponce2.getToken(), 4);
        server.addOrChangeRating(gson.toJson(rdr2_2));
        AddRatingDtoRequest rdr2_3 = new AddRatingDtoRequest(DataBase.getInstance().getSongsList().get(2),rudResponce2.getToken(), 4);
        server.addOrChangeRating(gson.toJson(rdr2_3));

        AddSongCommentDtoRequest ascd1 = new AddSongCommentDtoRequest(rudResponce1.getToken(), DataBase.getInstance().getSongsList().get(5), "Нравится очень"); //войдет в assert
        AddSongCommentDtoRequest ascd2 = new AddSongCommentDtoRequest(rudResponce2.getToken(), DataBase.getInstance().getSongsList().get(6), "Хрень");//войдет в assert
        server.addSongComment(gson.toJson(ascd1));
        server.addSongComment(gson.toJson(ascd2));
        AddCommentToCommentDtoRequest accd1 = new AddCommentToCommentDtoRequest(rudResponce3.getToken(), DataBase.getInstance().getSongCommentList().get(0) ,"И мне");//войдет в assert
        AddCommentToCommentDtoRequest accd3 = new AddCommentToCommentDtoRequest(rudResponce3.getToken(), DataBase.getInstance().getSongCommentList().get(1) ,"Хорошая песня из хорошего кино");
        server.addCommentToComment(gson.toJson(accd1));
        server.addCommentToComment(gson.toJson(accd3));

        String savedDataFileName = "savedDataBase.txt";
        server.stopServer(savedDataFileName);
        assertEquals(3,  DataBase.getInstance().getUsersMap().size());
        assertEquals(9,  DataBase.getInstance().getSongsMap().size());
        assertEquals(3,  DataBase.getInstance().getTokenBoxesMap().size());
        assertEquals(14,  DataBase.getInstance().getRatingBoxesMap().size());
        assertEquals(2,  DataBase.getInstance().getSongCommentsMap().size());
        assertEquals(2,  DataBase.getInstance().getCommentsToCommentsMap().size());

        Map<String, User> usersToWrite = new HashMap<>(DataBase.getInstance().getUsersMap());
        Map<UUID, TokenBox> tokenBoxesToWrite = new HashMap<>(DataBase.getInstance().getTokenBoxesMap());
        Map<String, Song> songsToWrite  = new HashMap<>(DataBase.getInstance().getSongsMap());
        Map<String, RatingBox> ratingBoxesToWrite  = new HashMap<>(DataBase.getInstance().getRatingBoxesMap());
        Map<String, SongComment> songCommentsToWrite  = new HashMap<>(DataBase.getInstance().getSongCommentsMap());
        Map<String,CommentToComment> commentsToCommentsToWrite  = new HashMap<>(DataBase.getInstance().getCommentsToCommentsMap());

        commonDao.clear();
        assertEquals(0, DataBase.getInstance().getUsersMap().size());
        assertEquals(0, DataBase.getInstance().getSongsMap().size());
        assertEquals(0, DataBase.getInstance().getTokenBoxesMap().size());
        assertEquals(0, DataBase.getInstance().getRatingBoxesMap().size());
        assertEquals(0, DataBase.getInstance().getSongCommentsMap().size());
        assertEquals(0, DataBase.getInstance().getCommentsToCommentsMap().size());

        server.startServer(savedDataFileName);
        assertEquals(3, DataBase.getInstance().getUsersMap().size());
        assertEquals(9, DataBase.getInstance().getSongsMap().size());
        assertEquals(3, DataBase.getInstance().getTokenBoxesMap().size());
        assertEquals(14, DataBase.getInstance().getRatingBoxesMap().size());
        assertEquals(2, DataBase.getInstance().getSongCommentsMap().size());
        assertEquals(2, DataBase.getInstance().getCommentsToCommentsMap().size());

        assertEquals(usersToWrite, DataBase.getInstance().getUsersMap());
        assertEquals(tokenBoxesToWrite, DataBase.getInstance().getTokenBoxesMap());
        assertEquals(songsToWrite, DataBase.getInstance().getSongsMap());
        assertEquals(ratingBoxesToWrite, DataBase.getInstance().getRatingBoxesMap());
        assertEquals(songCommentsToWrite, DataBase.getInstance().getSongCommentsMap());
        assertEquals(commentsToCommentsToWrite, DataBase.getInstance().getCommentsToCommentsMap());
    }
}
