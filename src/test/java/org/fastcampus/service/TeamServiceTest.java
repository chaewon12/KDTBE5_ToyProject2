package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
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

class TeamServiceTest {
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
    void registerTeam() {
        String result;
        try {
            result = teamService.registerTeam(1, "경기장");
        } catch (SQLException e) {
            e.printStackTrace();
            result = "실패";
        }
        Assertions.assertEquals("성공", result);
    }

    @Test
    void selectAllTeam() {
        List<TeamRespDTO> result = teamService.selectAllTeam();
        result.forEach(System.out::println);

    }
}
