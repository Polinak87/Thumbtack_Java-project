package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.model.*;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.database.DataBase;

import java.util.List;
import java.util.UUID;

public class SongDaoImpl implements SongDao {

    @Override
    public String insert (Song song) throws UserException {
        return DataBase.getInstance().insert (song);
    }

    @Override
    public String delete (UUID token, Song song) throws UserException {
        return DataBase.getInstance().delete (token, song);
    }

    @Override
    public String insert (RatingBox ratingBox) throws UserException {
        return DataBase.getInstance().insert(ratingBox);
    }

    @Override
    public String delete (UUID token, RatingBox ratingBox) throws UserException {
        return DataBase.getInstance().delete(token, ratingBox);
    }

    @Override
    public String insert (UUID token, Song song, String textOfSongComment) {
        return DataBase.getInstance().insert(token,song,textOfSongComment);
    }

    @Override
    public String changeSongComment (UUID token, SongComment songComment, String textOfSongComment) throws UserException {
        return DataBase.getInstance().changeSongComment(token, songComment,textOfSongComment);
    }

    @Override
    public String insert (UUID token, SongComment songComment, String textOfCommentToComment) {
        return DataBase.getInstance().insert(token, songComment, textOfCommentToComment);
    }

    @Override
    public String rejectionOfCommentToComment (UUID token, CommentToComment commentToComment) throws UserException {
        return DataBase.getInstance().rejectionOfCommentToComment(token, commentToComment);
    }

    @Override
    public List<ConcertSong> getConcert () throws UserException  {
        return DataBase.getInstance().getConcert();
    }

    @Override
    public List<Song>  songsOfSinger (String singer) throws UserException {
        return DataBase.getInstance().getSongsOfSinger(singer);
    }

    @Override
    public List<Song>  songsOfComposer (String composer) throws UserException {
        return DataBase.getInstance().getSongsOfComposer(composer);
    }

    @Override
    public List<Song>  songsOfAuthorOfTheText (String authorOfTheText) throws UserException {
        return DataBase.getInstance().getSongsOfAuthorOfTheText(authorOfTheText);
    }

    @Override
    public List<Song> getSongsList () {
        return DataBase.getInstance().getSongsList();
    }
}
