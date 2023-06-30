package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
import org.fastcampus.dto.outPlayer.OutPlayerReqDTO;
import org.fastcampus.util.type.OutReason;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

class OutPlayerServiceTest {
    Connection connection = DBConnection.getInstance();
    OutPlayerService outPlayerService = OutPlayerService.getInstance(connection);
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
    void outPlayerAdd_success_test() {
        // given
        OutPlayerReqDTO.OutPlayerAddReqDTO outPlayerAddReqDTO =
                OutPlayerReqDTO.OutPlayerAddReqDTO.builder()
                        .playerId(5)
                        .outReason(OutReason.CONTRACT_EXPIRATION)
                        .build();

        // when
        String result = outPlayerService.addOutPlayer(outPlayerAddReqDTO);

        // then
        Assertions.assertEquals("선수 퇴출 등록 성공",result);

    }

    @Test
    void outPlayerAdd_fail_test() {
        // given
        OutPlayerReqDTO.OutPlayerAddReqDTO outPlayerAddReqDTO =
                OutPlayerReqDTO.OutPlayerAddReqDTO.builder()
                        .playerId(30)   //  없는 선수
                        .outReason(OutReason.INJURY)
                        .build();

        // when
        String result = outPlayerService.addOutPlayer(outPlayerAddReqDTO);

        // then
        Assertions.assertEquals("선수 퇴출 등록 실패",result);

    }
}