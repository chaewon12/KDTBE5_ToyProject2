package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.stadium.Stadium;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StadiumServiceTest {
    Connection connection = DBConnection.getInstance();
    StadiumService stadiumService = StadiumService.getInstance(connection);
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
    void registerStadium() throws SQLException {
        String result = stadiumService.registerStadium("한화경기장");
        Assertions.assertEquals("성공", result);
    }

    @Test
    void selectAllStadium() {
        List<Stadium> result = stadiumService.selectAllStadium();
        result.forEach(System.out::println);
    }
}