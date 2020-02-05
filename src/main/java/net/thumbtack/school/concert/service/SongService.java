package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserErrorCode;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.daoimpl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.*;
import net.thumbtack.school.concert.dto.request.RejectionOfCommentToCommentDtoRequest;
import net.thumbtack.school.concert.dto.responce.*;
import net.thumbtack.school.concert.model.RatingBox;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.daoimpl.SongDaoImpl;
import net.thumbtack.school.concert.model.Song;


public class SongService {


    private static SongDao songDao = new SongDaoImpl();
    private static UserDao userDao = new UserDaoImpl();


    public static String addSong (String requestJsonString) {
        String gsonText = null;
        Gson gson = new Gson();
        AddSongDtoRequest asdRequest = gson.fromJson(requestJsonString, AddSongDtoRequest.class);
        try {
            if (userDao.isActiveToken(asdRequest.getAuthorOfTheOffer())) {
                try {
                    checkSongName (asdRequest.getSongName());
                } catch (UserException e) {
                    return gsonText = gson.toJson(e);
                }
                try {
                    checkSinger (asdRequest.getSinger());
                } catch (UserException e) {
                    return gsonText = gson.toJson(e);
                }
                try {
                    checkComposer (asdRequest.getComposer());
                } catch (UserException e) {
                    return gsonText = gson.toJson(e);
                }
                try {
                    checkAuthorOfTheText (asdRequest.getAuthorOfTheText());
                } catch (UserException e) {
                    return gsonText = gson.toJson(e);
                }
                try {
                    checkSongLength(asdRequest.getSongLength());
                } catch (UserException e) {
                    return gsonText = gson.toJson(e);
                }

                Song song = new Song(userDao.getLoginByToken(asdRequest.getAuthorOfTheOffer()), asdRequest.getSongName(), asdRequest. getSinger(),
                                 asdRequest.getComposer(), asdRequest.getAuthorOfTheText(), asdRequest.getSongLength());
                AddSongDtoResponce asdResponce = new AddSongDtoResponce(songDao.insert(song));
                gsonText = gson.toJson(asdResponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }

        return gsonText;
    }

    public static void checkSongName(String firstName) throws UserException {
        if(firstName == null || firstName.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_SONG_NAME);
    }

    public static void checkSinger(String singer) throws UserException {
        if(singer == null || singer.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_SINGER);
        if(singer.contains(","))
            throw new UserException(UserErrorCode.WRONG_SINGER);
    }

    public static void checkComposer(String composer) throws UserException {
        if(composer == null || composer.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_COMPOSER);
    }

    public static void checkAuthorOfTheText(String authorOfTheText) throws UserException {
        if(authorOfTheText == null || authorOfTheText.replaceAll(" ", "").length() == 0)
            throw new UserException(UserErrorCode.EMPTY_AUTHOR_OF_THE_TEXT);
    }

    public static void checkSongLength(Integer songLength) throws UserException {
        try {
            if (songLength == 0)
                throw new UserException(UserErrorCode.EMPTY_SONG_LENGTH);
        } catch (NullPointerException ex) {
            throw new UserException(UserErrorCode.EMPTY_SONG_LENGTH);
        }
    }


    public static String deleteSong (String requestJsonString)  {
        String gsonText = null;
        Gson gson = new Gson();
        DeleteSongDtoRequest request = gson.fromJson(requestJsonString, DeleteSongDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())) {
                DeleteSongDtoResponce responce = new DeleteSongDtoResponce(songDao.delete(request.getToken(),request.getSong()));
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String addManySong (String requestJsonString) {
        String gsonText = null;
        Gson gson = new Gson();
        AddManySongDtoRequest asdRequest = gson.fromJson(requestJsonString, AddManySongDtoRequest.class);
        try {
            if (userDao.isActiveToken(asdRequest.getSongsForAdd().get(0).getAuthorOfTheOffer())) {
                for (int i = 0; i < asdRequest.getSongsForAdd().size(); i++) {
                    AddManySongDtoResponce asdResponce = new AddManySongDtoResponce(addSong(gson.toJson(asdRequest.getSongsForAdd().get(i))));
                    gsonText = gson.toJson(asdResponce);
                }
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String addOrChangeRating(String requestJsonString)  {
        String gsonText = null;
        Gson gson = new Gson();
        AddRatingDtoRequest rdRequest = gson.fromJson(requestJsonString, AddRatingDtoRequest.class);
        try {
            if (userDao.isActiveToken(rdRequest.getAuthorOfTheRating())) {
                if(rdRequest.getRating() > 5 || rdRequest.getRating() < 1) {
                    throw new UserException(UserErrorCode.WRONG_RATING);
                }
                RatingBox ratingBox = new RatingBox(rdRequest.getSong(),userDao.getLoginByToken(rdRequest.getAuthorOfTheRating()), rdRequest.getRating());
                AddRatingDtoResponce rsdResponce = new AddRatingDtoResponce(songDao.insert(ratingBox));
                gsonText = gson.toJson(rsdResponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        } catch (NullPointerException ex) {
            return gsonText = gson.toJson(new UserException(UserErrorCode.EMPTY_RATING));
        }
        return gsonText;
    }

    public static String deleteRating(String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        DeleteRatingDtoRequest drdrequest = gson.fromJson(requestJsonString, DeleteRatingDtoRequest.class);
        try {
            if (userDao.isActiveToken(drdrequest.getToken())){
                DeleteRatingDtoResponce drdresponce = new DeleteRatingDtoResponce(songDao.delete(drdrequest.getToken(), drdrequest.getRatingBox()));
                gsonText = gson.toJson(drdresponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String addSongComment (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        AddSongCommentDtoRequest ascdrequest = gson.fromJson(requestJsonString, AddSongCommentDtoRequest.class);
        try {
            if (userDao.isActiveToken(ascdrequest.getToken())) {
                AddSongCommentDtoResponce ascdresponce = new AddSongCommentDtoResponce(songDao.insert(ascdrequest.getToken(),ascdrequest.getSong(),
                                                                                        ascdrequest.getTextOfSongComment()));
                gsonText = gson.toJson(ascdresponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String changeSongComment (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        ChangeSongCommentDtoRequest cscdrequest = gson.fromJson(requestJsonString, ChangeSongCommentDtoRequest.class);
        try {
            if (userDao.isActiveToken(cscdrequest.getToken())) {
                ChangeSongCommentDtoResponce cscdresponce = new ChangeSongCommentDtoResponce(songDao.changeSongComment(cscdrequest.getToken(), cscdrequest.getSongComment(), cscdrequest.getTextOfSongComment()));
                gsonText = gson.toJson(cscdresponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String addCommentToComment (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        AddCommentToCommentDtoRequest accdrequest = gson.fromJson(requestJsonString, AddCommentToCommentDtoRequest.class);
        try {
            if (userDao.isActiveToken(accdrequest.getToken())) {
                AddCommentToCommentDtoResponce ascdresponce = new AddCommentToCommentDtoResponce(songDao.insert(accdrequest.getToken(),accdrequest.getSongComment(),
                                                                                                 accdrequest.getTextOfCommentToComment()));
                gsonText = gson.toJson(ascdresponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String rejectionOfCommentToComment (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        RejectionOfCommentToCommentDtoRequest request = gson.fromJson(requestJsonString, RejectionOfCommentToCommentDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())) {
                RejectionOfCommentToCommentDtoResponce responce = new RejectionOfCommentToCommentDtoResponce(songDao.rejectionOfCommentToComment(request.getToken(), request.getCommentToComment()));
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }
        return gsonText;
    }

    public static String getSongsOfSinger  (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        GetListSongsOfDtoRequest request = gson.fromJson(requestJsonString, GetListSongsOfDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())) {
                GetListSongsDtoResponce responce = new GetListSongsDtoResponce(songDao.songsOfSinger(request.getPerson()));
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }

        return gsonText;
    }

    public static String getSongsOfComposer  (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        GetListSongsOfDtoRequest request = gson.fromJson(requestJsonString, GetListSongsOfDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())) {
                GetListSongsDtoResponce responce = new GetListSongsDtoResponce(songDao.songsOfComposer(request.getPerson()));
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }

        return gsonText;
    }

    public static String getSongsOfAuthorOfTheText  (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        GetListSongsOfDtoRequest request = gson.fromJson(requestJsonString, GetListSongsOfDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())) {
                GetListSongsDtoResponce responce = new GetListSongsDtoResponce(songDao.songsOfAuthorOfTheText(request.getPerson()));
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }

        return gsonText;
    }

    public static String getSongsList  (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        GetListSongsDtoRequest request = gson.fromJson(requestJsonString, GetListSongsDtoRequest.class);
        try {
            if (userDao.isActiveToken(request.getToken())) {
                GetListSongsDtoResponce responce = new GetListSongsDtoResponce(songDao.getSongsList());
                gsonText = gson.toJson(responce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }

        return gsonText;
    }

    public static String getConcert (String requestJsonString) throws UserException {
        String gsonText = null;
        Gson gson = new Gson();
        ConcertDtoRequest concertDtoRequest = gson.fromJson(requestJsonString, ConcertDtoRequest.class);
        try {
            if (userDao.isActiveToken(concertDtoRequest.getToken())) {
                ConcertDtoResponce concertDtoResponce = new ConcertDtoResponce(songDao.getConcert());
                gsonText = gson.toJson(concertDtoResponce);
            }
        } catch (UserException e) {
            return gsonText = gson.toJson(e);
        }

        return gsonText;
    }
}
