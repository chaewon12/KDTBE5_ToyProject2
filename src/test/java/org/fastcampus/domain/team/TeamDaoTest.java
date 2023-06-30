package org.fastcampus.domain.team;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.stadium.Stadium;
import org.fastcampus.dto.team.TeamRespDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeamDaoTest {
    Connection connection = DBConnection.getInstance();
    Savepoint savepoint;
    TeamDao teamDao = TeamDao.getInstance(connection);

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
    void registerTeam() throws SQLException {
        int result = teamDao.registerTeam(1, "한화");
        Assertions.assertEquals(1,result);
    }

    @Test
    void selectAllTeam() {
        List<TeamRespDTO> result = teamDao.selectAllTeam();
        assertEquals(3, result.size());
    }
}