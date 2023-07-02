package org.fastcampus.util;

import org.fastcampus.db.DBConnection;
import org.fastcampus.service.TeamService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleTest {
    Connection connection = DBConnection.getInstance();
    TeamService teamService = TeamService.getInstance(connection);
    Savepoint savepoint;

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

        String result = teamService.registerTeam(1, "팀A");

        assertEquals("성공", result);
    }
}