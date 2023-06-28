package org.fastcampus.domain.outPlayer;

import org.fastcampus.db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

class OutPlayerDaoTest {
    Connection connection = DBConnection.getInstance();
    Savepoint savepoint;
    OutPlayerDao OutplayerDao = OutPlayerDao.getInstance(connection);

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
    void insert() {
        // given
        OutPlayer outPlayer = OutPlayer.builder()
                                        .playerId(27)
                                        .reason("부상")
                                        .build();

        // when
        int result = OutplayerDao.insert(outPlayer);

        // then
        Assertions.assertEquals(1,result);
    }
}