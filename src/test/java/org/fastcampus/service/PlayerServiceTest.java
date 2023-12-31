package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
import org.fastcampus.dto.player.PlayerReqDTO;
import org.fastcampus.dto.player.PlayerRespDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

class PlayerServiceTest {
    Connection connection = DBConnection.getInstance();
    PlayerService playerService = PlayerService.getInstance(connection);
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
    void playerAdd() {
        // given
        PlayerReqDTO.PlayerAddReqDTO playerAddReqDTO
                = PlayerReqDTO.PlayerAddReqDTO.builder()
                .teamId(3)
                .name("나성범")
                .position("우익수")
                .build();

        // when
        String result = playerService.addPlayer(playerAddReqDTO);

        // then
        Assertions.assertEquals("선수등록 성공",result);
    }

    @Test
    void playerList() {
        // given
        int teamId=3;
        // when
        List<PlayerRespDTO.PlayerListRespDTO> playerListRespDTOList = playerService.getPlayerList(3);
        // then
        playerListRespDTOList.forEach(System.out::println);
    }
}