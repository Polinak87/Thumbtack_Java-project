package net.thumbtack.school.concert.daoimpl;

import net.thumbtack.school.concert.dao.CommonDao;
import net.thumbtack.school.concert.database.DataBase;

public class CommonDaoImpl implements CommonDao {

    @Override
    public void clear () {
        DataBase.getInstance().getUsersMap().clear();
        DataBase.getInstance().getTokenBoxesMap().clear();
        DataBase.getInstance().getSongsMap().clear();
        DataBase.getInstance().getRatingBoxesMap().clear();
        DataBase.getInstance().getSongCommentsMap().clear();
        DataBase.getInstance().getCommentsToCommentsMap().clear();
    }
}
