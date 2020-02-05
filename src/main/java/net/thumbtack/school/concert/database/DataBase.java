package net.thumbtack.school.concert.database;

import net.thumbtack.school.concert.base.*;
import net.thumbtack.school.concert.dto.request.LoginUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.model.*;

import java.util.*;

public class DataBase {

    private static DataBase ourInstance = new DataBase();

    public static DataBase getInstance() {
        return ourInstance;
    }

    private DataBase() {}


    private static final int CONCERT_LENGTH_SEC = 3600;
    private static final int CONCERT_PAUSES_SEC = 10;

    private Map<String, User> users = new HashMap<>();
    private Map<UUID, TokenBox> tokenBoxes = new HashMap<>();
    private Map<String, Song> songs = new HashMap<>();
    private Map<String,RatingBox> ratingBoxes = new HashMap<>();
    private Map<String,SongComment> songComments = new HashMap<>();
    private Map<String,CommentToComment> commentsToComments = new HashMap<>();


    public UUID insert (User user) throws UserException {
        if (doubleLogin(user))
            throw new UserException(UserErrorCode.DOUBLE_LOGIN);
        users.put(user.getLogin(),user);
        UUID token = UUID.randomUUID();
        TokenBox tokenBox = new TokenBox(token,user.getLogin());
        tokenBoxes.put(token, tokenBox);
        return token;
    }

    public boolean doubleLogin (User user) {
        return users.containsKey(user.getLogin());
    }

    public UUID loginUser (LoginUserDtoRequest ludr) throws UserException {
        User user = users.get(ludr.getLogin());
        if (user == null || !user.getPassword().equals(ludr.getPassword()))
            throw new UserException (UserErrorCode.WRONG_LOGIN_OR_PASSWORD);
        UUID token = UUID.randomUUID();
        TokenBox tokenBox = new TokenBox(token, user.getLogin());
        tokenBoxes.put(token, tokenBox);
        return token;
    }

    public String logoutUser (LogoutUserDtoRequest ludr) throws UserException {
            tokenBoxes.get(ludr.getToken()).isInactiveState();
            return "";
    }

    public String deleteUser (UUID token) throws UserException {
        String login = getLoginByToken(token);
        users.remove(login);
        tokenBoxes.get(token).isInactiveState();
        Iterator<Song> iterS = getSongsList().iterator();
        Song song;
        while (iterS.hasNext()){
            song = iterS.next();
            if (song.getAuthorOfTheOffer().equals(login))
                delete(token,song);
        }
        Iterator<RatingBox> iterR = getRatingBoxeList().iterator();
        RatingBox ratingBox;
        while (iterR.hasNext()){
            ratingBox = iterR.next();
            if (ratingBox.getAuthorOfTheRating().equals(login)) {
                ratingBoxes.remove(keyBuilder(token, ratingBox.getSong().getSongId()));
                ratingBox.setAuthorOfTheRating("Сообщество радиослушателей");
                ratingBoxes.put(keyBuilder("Сообщество радиослушателей", ratingBox.getSong().getSongId()), ratingBox);
            }
        }
        Iterator<SongComment> iterSC = getSongCommentList().iterator();
        SongComment songComment;
        while (iterSC.hasNext()){
            songComment = iterSC.next();
            if (songComment.getAuthour().equals(login)) {
                songComments.remove(keyBuilder(token,songComment.getSongCommentId()));
                songComment.setAuthour("Сообщество радиослушателей");
                songComments.put(keyBuilder("Сообщество радиослушателей", songComment.getSongCommentId()), songComment);
            }
        }
        Iterator<CommentToComment> iterCC = getCommentToCommentList().iterator();
        CommentToComment commentToComment;
        while (iterCC.hasNext()){
            commentToComment = iterCC.next();
            if (commentToComment.getAuthour().equals(login)) {
                commentsToComments.remove(keyBuilder(token, commentToComment.getCommentToCommentId()));
                commentToComment.setAuthour("Сообщество радиослушателей");
                commentsToComments.put(keyBuilder("Сообщество радиослушателей", commentToComment.getCommentToCommentId()), commentToComment);
            }
        }
        return "";
    }

    public boolean isActiveToken (UUID token) throws UserException {
        if (tokenBoxes.get(token).getState() == TokenState.ACTIVE)
            return true;
        throw new UserException(UserErrorCode.INACTIVE_TOKEN);
    }

    public String getLoginByToken (UUID token) {
        return tokenBoxes.get(token).getLogin();
    }

    public String insert (Song song) throws UserException {
        if (doubleSong (song))
            throw new UserException(UserErrorCode.DOUBLE_SONG);
        songs.put(keyBuilder(song.getAuthorOfTheOffer(), song.getSongId()),song);
        ratingBoxes.put(keyBuilder(song.getAuthorOfTheOffer(), song.getSongId()), new RatingBox(song,song.getAuthorOfTheOffer(),5,false));
        return "";
    }

    public boolean doubleSong (Song song) {
        boolean result = false;
        for (String key : songs.keySet()) {
            if (songs.get(key).isDouble(song)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public String delete (UUID token, Song song) throws UserException {
        String key = keyBuilder(token,song.getSongId());
        if(!songs.containsKey(key))
            throw new UserException(UserErrorCode.SONG_DELETION_ERROR);
        sumRating();
        if (songs.get(key).getSumRating() == 5) {
            songs.remove(key);
            ratingBoxes.remove(key);
        } else {
            songs.remove(key);
            ratingBoxes.remove(key);
            song.setAuthorOfTheOffer("Сообщество радиослушателей");
            song.setSumRating(0);
            songs.put(keyBuilder(song.getAuthorOfTheOffer(),song.getSongId()),song);
            sumRating();
        }
        return "";
    }

    public String insert (RatingBox ratingBox){
        ratingBoxes.put(keyBuilder(ratingBox.getAuthorOfTheRating(),ratingBox.getSong().getSongId()), ratingBox);
        return "";
    }

    public String delete (UUID token, RatingBox ratingBox) throws UserException {
        if (!ratingBox.isAbilityToBeDeleted())
            throw new UserException(UserErrorCode.RATING_DELETION_ABILITY_ERROR);
        if (ratingBox.getAuthorOfTheRating().equals(getLoginByToken(token))) {
            ratingBoxes.remove(keyBuilder(ratingBox.getAuthorOfTheRating(), ratingBox.getSong().getSongId()));
        } else {
            throw new UserException(UserErrorCode.RATING_DELETION_ERROR);
        }
        return "";
    }

    public void sumRating () {
        int rating = 0;
        for (String key : songs.keySet()) {
            Song song = songs.get(key);
            for (String key1 : ratingBoxes.keySet()) {
                if (key1.contains(song.getSongId().toString())) {
                    rating += ratingBoxes.get(key1).getRating();
                }
            }
            song.setSumRating(rating);
            rating = 0;
        }
    }//

    public String insert (UUID token, Song song, String textOfSongComment) {
        SongComment songComment = new SongComment(song.getSongId(),textOfSongComment,getLoginByToken(token));
        songComments.put(keyBuilder (token, songComment.getSongCommentId()), songComment);
        return "";
    }

    public String changeSongComment (UUID token, SongComment songComment, String textOfSongComment) throws UserException {
        if (!songComment.getAuthour().equals(getLoginByToken(token)))
            throw new UserException(UserErrorCode.COMMENT_CHANGE_ERROR);
        songComments.remove(keyBuilder(token, songComment.getSongCommentId()));
        if (songComment.isCommToCommAvailability()) {
            songComment.setAuthour("Сообщество радиослушателей");
            songComments.put(keyBuilder("Сообщество радиослушателей", songComment.getSongCommentId()), songComment);
            songComments.put(keyBuilder(token, songComment.getSongCommentId()),
                    new SongComment(songComment.getSongId(), textOfSongComment, getLoginByToken(token)));
        } else {
            songComment.setTextOfSongComment(textOfSongComment);
            songComments.put(keyBuilder(token, songComment.getSongCommentId()),songComment);
        }
        return "";
    }

    public String insert (UUID token, SongComment songComment, String textOfCommentToComment) {
        CommentToComment commentToComment = new CommentToComment(songComment.getSongCommentId(), textOfCommentToComment, getLoginByToken(token));
        commentsToComments.put(keyBuilder(token,commentToComment.getCommentToCommentId()), commentToComment);
        songComment.setCommToCommAvailability(true);
        songComments.put(keyBuilder(songComment.getAuthour(), songComment.getSongCommentId()), songComment);
        return "";
    }

    public String rejectionOfCommentToComment (UUID token, CommentToComment commentToComment) throws UserException {
        if(!commentToComment.getAuthour().equals(getLoginByToken(token)))
            throw new UserException(UserErrorCode.COMMENT_REJECTION_ERROR);
        commentToComment.setAuthour("Сообщество радиослушателей");
        commentsToComments.put(keyBuilder(token, commentToComment.getCommentToCommentId()), commentToComment);
        return "";
    }

    public String keyBuilder (UUID token, UUID id) {
        return (new  StringBuilder(getLoginByToken(token) + "_" + id.toString()).toString());
    }

    public String keyBuilder (String author, UUID id) {
        return (new  StringBuilder(author + "_" + id.toString()).toString());
    }

    public  List<Song> getConcertSongs () {
        List<Song> concertSongs = new ArrayList<>();
        int concertLengthSec=-10;
        sumRating();
        List<Song> songs = getSongsList();
        songs.sort(Comparator.comparingInt(Song::getSumRating).reversed().thenComparingLong(Song::getCreationTime));
        Iterator<Song> iter = songs.iterator();
        while (iter.hasNext()) {
            Song a = iter.next();
            if ((concertLengthSec + a.getSongLength() + CONCERT_PAUSES_SEC) < CONCERT_LENGTH_SEC) {
                concertSongs.add(a);
                concertLengthSec += a.getSongLength() + CONCERT_PAUSES_SEC;
            }
        }
        return concertSongs;
    }

    public List<ConcertSong> getConcert () throws UserException {
        List<Song> concertSongs = getConcertSongs();
        List<ConcertSong> concert = new ArrayList<>();
        Song song = null;
        SongComment songComment;
        CommentToComment commentToComment;
        Iterator<Song> iterS = concertSongs.iterator();
        while (iterS.hasNext()) {
            song = iterS.next();
            List<ConsertSongComment> consertSongComments = new ArrayList<>();
            Iterator<SongComment> iterSC = getSongCommentList().iterator();
            while (iterSC.hasNext()) {
                songComment = iterSC.next();
                List<ConsertCommentToComment> consertCommentsToComments = new ArrayList<>();
                if (song.getSongId().equals(songComment.getSongId())) {
                    Iterator<CommentToComment> iterCC = getCommentToCommentList().iterator();
                    while (iterCC.hasNext()) {
                        commentToComment = iterCC.next();
                        if (commentToComment.getSongCommentId().equals(songComment.getSongCommentId()))
                            consertCommentsToComments.add(new ConsertCommentToComment(commentToComment.getTextOfCommentToComment(), commentToComment.getAuthour(), commentToComment.getCreationTime()));
                    }
                consertSongComments.add(new ConsertSongComment(songComment.getTextOfSongComment(), songComment.getAuthour(), songComment.getCreationTime(), consertCommentsToComments));
                }
            }
            ConcertSong concertSong = new ConcertSong(song.getSongName(), song.getComposer(),
                    song.getAuthorOfTheText(), song.getSinger(),
                    song.getSumRating(), users.get(song.getAuthorOfTheOffer()).getFirstName(),
                    users.get(song.getAuthorOfTheOffer()).getLastName(), consertSongComments);
            concert.add(concertSong);
        }
        return concert;
    }

    public List<Song> getSongsOfSinger(String singer) throws UserException {
        List<Song> songsOfSinger = new ArrayList<>();
        List<Song> songs = getSongsList();
        Iterator<Song> iter = songs.iterator();
        Song song;
        while (iter.hasNext()) {
            song = iter.next();
            if (song.getSinger().contains(singer))
                songsOfSinger.add(song);
        }
        if (songsOfSinger.size() == 0)
            throw new UserException(UserErrorCode.EMPTY_SONG_LIST);
        return songsOfSinger;
    }

    public List<Song> getSongsOfComposer(String composer) throws UserException  {
        List<Song> songsOfComposer = new ArrayList<>();
        List<Song> songs = getSongsList();
        Iterator<Song> iter = songs.iterator();
        Song song;
        while (iter.hasNext()) {
            song = iter.next();
            if (song.getComposer().contains(composer))
                songsOfComposer.add(song);
        }
        if (songsOfComposer.size() == 0)
            throw new UserException(UserErrorCode.EMPTY_SONG_LIST);
        return songsOfComposer;
    }

    public List<Song> getSongsOfAuthorOfTheText(String authorOfTheText) throws UserException  {
        List<Song> songsOfAuthorOfTheText = new ArrayList<>();
        List<Song> songs = getSongsList();
        Iterator<Song> iter = songs.iterator();
        Song song;
        while (iter.hasNext()) {
            song = iter.next();
            if (song.getAuthorOfTheText().contains(authorOfTheText))
                songsOfAuthorOfTheText.add(song);
        }
        if (songsOfAuthorOfTheText.size() == 0)
            throw new UserException(UserErrorCode.EMPTY_SONG_LIST);
        return songsOfAuthorOfTheText;
    }

    public List<User> getUsersList () {
        return new ArrayList<>(users.values());
    }

    public List<TokenBox> getTokenBoxeList() {
        return new ArrayList<>(tokenBoxes.values());
    }

    public List<RatingBox> getRatingBoxeList () {
        List<RatingBox> ratingBoxes1 = new ArrayList<>(ratingBoxes.values());
        ratingBoxes1.sort((Comparator.comparingLong(RatingBox::getCreationTime)));
        return ratingBoxes1;
    }

    public List<SongComment> getSongCommentList () {
        List<SongComment> songComments1 = new ArrayList<>(songComments.values());
        songComments1.sort((Comparator.comparingLong(SongComment::getCreationTime)));
        return songComments1;
    }

    public List<CommentToComment> getCommentToCommentList () {
        List<CommentToComment> commentToComments1 = new ArrayList<>(commentsToComments.values());
        commentToComments1.sort((Comparator.comparingLong(CommentToComment::getCreationTime)));
        return commentToComments1;
    }

    public List<Song> getSongsList () {
        List<Song> songs1 = new ArrayList<>(songs.values());
        songs1.sort((Comparator.comparingLong(Song::getCreationTime)));
        return songs1;
    }

    public Map<String, User> getUsersMap () {
        return users;
    }

    public Map<String, Song> getSongsMap() {
        return songs;
    }

    public Map<UUID, TokenBox> getTokenBoxesMap () {
        return tokenBoxes;
    }

    public Map<String, RatingBox> getRatingBoxesMap () {
        return ratingBoxes;
    }

    public Map<String,SongComment> getSongCommentsMap() {
        return songComments;
    }

    public Map<String,CommentToComment> getCommentsToCommentsMap () {
        return commentsToComments;
    }

    public void setOurInstance (DataBase dataBase) {
        this.ourInstance = dataBase;
    }
}
