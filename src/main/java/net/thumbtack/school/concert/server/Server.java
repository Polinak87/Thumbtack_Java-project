package net.thumbtack.school.concert.server;

import com.google.gson.Gson;
import net.thumbtack.school.concert.base.UserException;
import net.thumbtack.school.concert.database.DataBase;
import net.thumbtack.school.concert.service.SongService;
import net.thumbtack.school.concert.service.UserService;
import java.io.*;

public class Server {

    private boolean serverIsActive;
    private UserService userService;
    private SongService songService;


    public void startServer(String savedDataFileName) throws IOException {
        if (!serverIsActive) this.serverIsActive = true;
        if (savedDataFileName != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(new File(savedDataFileName)))){
                Gson gson = new Gson();
                DataBase.getInstance().setOurInstance(gson.fromJson(br, DataBase.class));
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

    public void stopServer(String savedDataFileName) throws IOException {
        if (serverIsActive) this.serverIsActive = false;
        if (savedDataFileName != null) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(savedDataFileName)))) {
                Gson gson = new Gson();
                gson.toJson(DataBase.getInstance(), bw);
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

    public boolean getServerIsActive() {
        return serverIsActive;
    }

    public void setServerIsActive() {
        this.serverIsActive = true;
    }

    public String registerUser (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = userService.registerUser(requestJsonString);
        }
        return result;
    }

    public String loginUser (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = userService.loginUser(requestJsonString);
        }
        return result;
    }

    public String logoutUser (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = userService.logoutUser(requestJsonString);
        }
        return result;
    }

    public String deleteUser (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = userService.deleteUser(requestJsonString);
        }
        return result;
    }

    public String addSong (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.addSong(requestJsonString);
        }
        return result;
    }

    public String deleteSong (String requestJsonString) throws UserException  {
        String result = null;
        if (getServerIsActive()) {
            result = songService.deleteSong(requestJsonString);
        }
        return result;
    }


    public String addManySong (String requestJsonString) throws UserException{
        String result = null;
        if (getServerIsActive()) {
            result = songService.addManySong(requestJsonString);
        }
        return result;
    }

    public String addOrChangeRating(String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.addOrChangeRating(requestJsonString);
        }
        return result;
    }

    public String deleteRating(String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.deleteRating(requestJsonString);
        }
        return result;
    }

    public String addSongComment(String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.addSongComment(requestJsonString);
        }
        return result;
    }

    public  String changeSongComment (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.changeSongComment(requestJsonString);
        }
        return result;
    }

    public String addCommentToComment (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.addCommentToComment(requestJsonString);
        }
        return result;
    }

    public String rejectionOfCommentToComment (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.rejectionOfCommentToComment(requestJsonString);
        }
        return result;
    }

    public String getSongsOfSinger (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.getSongsOfSinger(requestJsonString);
        }
        return result;
    }

    public String getSongsOfComposer (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.getSongsOfComposer(requestJsonString);
        }
        return result;
    }

    public String getSongsOfAuthorOfTheText (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.getSongsOfAuthorOfTheText(requestJsonString);
        }
        return result;
    }

    public String getSongsList (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.getSongsList(requestJsonString);
        }
        return result;
    }

    public String getConcert (String requestJsonString) throws UserException {
        String result = null;
        if (getServerIsActive()) {
            result = songService.getConcert(requestJsonString);
        }
        return result;
    }

}

