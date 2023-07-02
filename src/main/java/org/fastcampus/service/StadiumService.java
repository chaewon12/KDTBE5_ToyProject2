package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.stadium.Stadium;
import org.fastcampus.domain.stadium.StadiumDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StadiumService {
    private static StadiumService stadiumService;
    private StadiumDao stadiumDao;

    private StadiumService(Connection connection) {
        this.stadiumDao = StadiumDao.getInstance(connection);
    }

    public static StadiumService getInstance(Connection connection){
        if(stadiumService == null){
            stadiumService = new StadiumService(connection);
        }
        return stadiumService;
    }

    public String registerStadium(String name) throws SQLException {
        int result = stadiumDao.registerStadium(name);
        return result == 1 ? "성공" : "실패";
    }

    public List<Stadium> selectAllStadium() {
        List<Stadium> stadiumList = stadiumDao.selectAllStadium();
        return stadiumList;
    }
}