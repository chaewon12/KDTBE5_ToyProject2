package org.fastcampus.domain.stadium;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.player.Player;
import org.fastcampus.domain.player.PlayerDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StadiumDaoTest {
    Connection connection = DBConnection.getInstance();
    Savepoint savepoint;
    StadiumDao stadiumDao = StadiumDao.getInstance(connection);

    @BeforeEach
    void setUp() throws SQLException {
        connection.setAutoCommit(false); // 자동 커밋 비활성화
        savepoint = connection.setSavepoint(); // Savepoint 설정
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback(savepoint); // 롤백
        connection.setAutoCommit(true); // 자동 커밋 활성화
    }


    @Test
    void registerStadium() throws SQLException {
        // given
        String name = "사직야구장";

        // when
        int result = stadiumDao.registerStadium(name);

        // then
        Assertions.assertEquals(1,result);
    }


    @Test
    void selectAllStadium() {
        List<Stadium> result = stadiumDao.selectAllStadium();
        assertEquals(3, result.size());
    }
}

