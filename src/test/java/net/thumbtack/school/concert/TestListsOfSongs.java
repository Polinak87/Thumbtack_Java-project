package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.GetListSongsDtoRequest;
import net.thumbtack.school.concert.dto.request.GetListSongsOfDtoRequest;
import net.thumbtack.school.concert.dto.responce.GetListSongsDtoResponce;
import net.thumbtack.school.concert.dto.responce.RegisterUserDtoResponce;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.server.Server;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestListsOfSongs extends TestBase {

    @Test
    public void testListsOfSongs () throws UserException {
        Server server = new Server();
        server.setServerIsActive();
        Gson gson = new Gson();
        String requestJsonString1 = gson.toJson(new User("Дмитрий", "Ермоленко", "DimaErmol", "likeacat"));
        RegisterUserDtoResponce rudResponce1 = gson.fromJson((server.registerUser(requestJsonString1)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd1 = new AddSongDtoRequest(rudResponce1.getToken(), "Песня про Жирафа", "Высоцкий", "Высоцкий", "Высоцкий", 137);
        AddSongDtoRequest asd2 = new AddSongDtoRequest(rudResponce1.getToken(), "Вершина", "Высоцкий", "Высоцкий", "Высоцкий", 158);
        AddSongDtoRequest asd3 = new AddSongDtoRequest(rudResponce1.getToken(), "Ну вот исчезла дрожь в руках...", "Высоцкий", "Высоцкий", "Высоцкий", 170);
        AddSongDtoRequest asd4 = new AddSongDtoRequest(rudResponce1.getToken(), "Баллада о любви", "Высоцкий", "Высоцкий", "Высоцкий", 305);
        AddSongDtoRequest asd5 = new AddSongDtoRequest(rudResponce1.getToken(), "Алюминевые огурцы", "Цой", "Цой, Рыбин", "Цой, Рыбин,", 155);
        server.addSong(gson.toJson(asd1));
        server.addSong(gson.toJson(asd2));
        server.addSong(gson.toJson(asd3));
        server.addSong(gson.toJson(asd4));
        server.addSong(gson.toJson(asd5));

        String requestJsonString2 = gson.toJson(new User("Кирилл", "Страдов", "KirStrad", "tuztuz@2"));
        RegisterUserDtoResponce rudResponce2 = gson.fromJson((server.registerUser(requestJsonString2)), RegisterUserDtoResponce.class);
        AddSongDtoRequest asd6 = new AddSongDtoRequest(rudResponce2.getToken(), "Восьмиклассница", "Цой", "Цой, Рыбин", "Цой, Рыбин", 162);
        AddSongDtoRequest asd7 = new AddSongDtoRequest(rudResponce2.getToken(), "Видели ночь", "Цой", "Цой, Науменко", "Цой, Науменко", 131);
        AddSongDtoRequest asd8 = new AddSongDtoRequest(rudResponce2.getToken(), "Звезда", "Цой", "Цой", "Цой,", 270);
        AddSongDtoRequest asd9 = new AddSongDtoRequest(rudResponce2.getToken(), "Когда твоя девушка больна", "Цой", "Цой", "Цой,", 260);
        AddSongDtoRequest asd10 = new AddSongDtoRequest(rudResponce2.getToken(), "Я уеду жить в Лондон", "Лепс", "Лепс, Тимати", "Лепс, Тимати", 264);
        AddSongDtoRequest asd11 = new AddSongDtoRequest(rudResponce2.getToken(), "Самый лучший день", "Лепс", "Шапиро", "Шапиро", 302);
        server.addSong(gson.toJson(asd6));
        server.addSong(gson.toJson(asd7));
        server.addSong(gson.toJson(asd8));
        server.addSong(gson.toJson(asd9));
        server.addSong(gson.toJson(asd10));
        server.addSong(gson.toJson(asd11));

        String requestJsonString3 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Цой"));
        GetListSongsDtoResponce lsResponce1 = gson.fromJson(server.getSongsOfSinger(requestJsonString3), GetListSongsDtoResponce.class) ;
        List<Song> songsOfSinger =  lsResponce1.getSongs();
        assertEquals(5, songsOfSinger.size());
        assertEquals("Алюминевые огурцы", songsOfSinger.get(0).getSongName());
        assertEquals("Восьмиклассница", songsOfSinger.get(1).getSongName());
        assertEquals("Видели ночь", songsOfSinger.get(2).getSongName());
        assertEquals("Звезда", songsOfSinger.get(3).getSongName());
        assertEquals("Когда твоя девушка больна", songsOfSinger.get(4).getSongName());

        String requestJsonString4 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Цой, Рыбин"));
        GetListSongsDtoResponce lsResponce2 = gson.fromJson(server.getSongsOfComposer(requestJsonString4), GetListSongsDtoResponce.class) ;
        List<Song> songsOfComposer1 =  lsResponce2.getSongs();
        assertEquals(2, songsOfComposer1.size());
        assertEquals("Алюминевые огурцы", songsOfComposer1.get(0).getSongName());
        assertEquals("Восьмиклассница", songsOfComposer1.get(1).getSongName());

        String requestJsonString5 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Цой"));
        GetListSongsDtoResponce lsResponce3 = gson.fromJson(server.getSongsOfComposer(requestJsonString5), GetListSongsDtoResponce.class) ;
        List<Song> songsOfComposer2 =  lsResponce3.getSongs();
        assertEquals(5, songsOfComposer2.size());
        assertEquals("Алюминевые огурцы", songsOfComposer2.get(0).getSongName());
        assertEquals("Восьмиклассница", songsOfComposer2.get(1).getSongName());
        assertEquals("Видели ночь", songsOfComposer2.get(2).getSongName());
        assertEquals("Звезда", songsOfComposer2.get(3).getSongName());
        assertEquals("Когда твоя девушка больна", songsOfComposer2.get(4).getSongName());

        String requestJsonString6 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Шапиро"));
        GetListSongsDtoResponce lsResponce4 = gson.fromJson(server.getSongsOfAuthorOfTheText(requestJsonString6), GetListSongsDtoResponce.class) ;
        List<Song> songsOfAuthorOfTheText1 =  lsResponce4.getSongs();
        assertEquals(1, songsOfAuthorOfTheText1.size());
        assertEquals("Самый лучший день", songsOfAuthorOfTheText1.get(0).getSongName());

        String requestJsonString7 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Лепс, Тимати"));
        GetListSongsDtoResponce lsResponce5 = gson.fromJson(server.getSongsOfAuthorOfTheText(requestJsonString7), GetListSongsDtoResponce.class) ;
        List<Song> songsOfAuthorOfTheText2 =  lsResponce5.getSongs();
        assertEquals(1, songsOfAuthorOfTheText2.size());
        assertEquals("Я уеду жить в Лондон", songsOfAuthorOfTheText2.get(0).getSongName());

        String requestJsonString8 = gson.toJson(new GetListSongsDtoRequest(rudResponce1.getToken()));
        GetListSongsDtoResponce lsResponce6 = gson.fromJson(server.getSongsList(requestJsonString8), GetListSongsDtoResponce.class) ;
        List<Song> songsList =  lsResponce6.getSongs();
        assertEquals(11, songsList.size());

        String requestJsonString9 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Киркоров"));
        String responce1 = server.getSongsOfSinger(requestJsonString9);
        assertEquals(UserErrorCode.EMPTY_SONG_LIST,  gson.fromJson(responce1, UserException.class).getUserErrorCode() );

        String requestJsonString10 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Максимова"));
        String responce2 = server.getSongsOfComposer(requestJsonString10);
        assertEquals(UserErrorCode.EMPTY_SONG_LIST,  gson.fromJson(responce2, UserException.class).getUserErrorCode() );

        String requestJsonString11 = gson.toJson(new GetListSongsOfDtoRequest(rudResponce1.getToken(),"Есенин"));
        String responce3 = server.getSongsOfAuthorOfTheText(requestJsonString11);
        assertEquals(UserErrorCode.EMPTY_SONG_LIST,  gson.fromJson(responce3, UserException.class).getUserErrorCode() );
    }
}
