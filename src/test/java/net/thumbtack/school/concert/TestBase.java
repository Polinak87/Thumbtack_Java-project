package net.thumbtack.school.concert;

import net.thumbtack.school.concert.dao.CommonDao;
import net.thumbtack.school.concert.daoimpl.CommonDaoImpl;
import org.junit.Before;

public class TestBase {


    protected CommonDao commonDao = new CommonDaoImpl();


    @Before()
    public void clearDatabase() {
        commonDao.clear();
    }

}
