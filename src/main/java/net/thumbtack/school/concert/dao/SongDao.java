package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.model.*;
import net.thumbtack.school.concert.base.UserException;

import java.util.List;
import java.util.UUID;

public interface SongDao {

    String insert (Song song) throws UserException;

    String delete (UUID token, Song song) throws UserException;

    String insert (RatingBox ratingBox) throws UserException;

    String delete (UUID token, RatingBox ratingBox) throws UserException;

    String insert (UUID token, Song song, String textOfSongComment);

    String changeSongComment (UUID token, SongComment songComment, String textOfSongComment) throws UserException;

    String insert (UUID token, SongComment songComment, String textOfCommentToComment);

    String rejectionOfCommentToComment (UUID token, CommentToComment commentToComment) throws UserException;

    List<ConcertSong> getConcert () throws UserException;

    List<Song>  songsOfSinger (String singer) throws UserException;

    List<Song>  songsOfComposer (String composer) throws UserException;

    List<Song>  songsOfAuthorOfTheText (String authorOfTheText) throws UserException;

    List<Song> getSongsList();

}
