package org.fastcampus.domain.player;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.team.Team;
import org.fastcampus.domain.team.TeamDao;
import org.fastcampus.dto.player.PlayerRespDTO;
import org.fastcampus.dto.team.TeamRespDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

class PlayerDaoTest {
    Connection connection = DBConnection.getInstance();
    Savepoint savepoint;
    PlayerDao playerDao = PlayerDao.getInstance(connection);
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
    void insertPlayer_success_test() {
        // given
        Player player = Player.builder()
                .teamId(3)
                .name("나성범")
                .position("우익수")
                .build();

        // when
        int result = playerDao.insert(player);

        // then
        Assertions.assertEquals(1,result);
    }
    @Test
    void insertPlayer_fail_test() {
        // given
        Player player = Player.builder()
                .teamId(3)
                .name("나성범")
                .position("중견수")
                .build();

        // when
        int result = playerDao.insert(player);

        // then
        Assertions.assertEquals(0,result);
    }

    @Test
    void findByTeamId() {
        // given
        int teamId = 1;

        // when
        List<Player> playerList = playerDao.selectByTeamId(teamId);

        // then
        Assertions.assertEquals(8,playerList.size());
    }

    @Test
    void updateOutById_success_test() {
        // given
        int playerId = 27;

        // when
        int result = playerDao.updateOutById(playerId);

        // then
        Assertions.assertEquals(1,result);
    }

    @Test
    void updateOutById_fail_test() {
        // given
        int playerId = 30;

        // when
        int result = playerDao.updateOutById(playerId);

        // then
        Assertions.assertEquals(0,result);
    }

    @Test
    void selectPositionByTeam() {
        // given
        List<TeamRespDTO> teams = teamDao.selectAllTeam();

        // when
        List<PlayerRespDTO.positionBoardRespDTO> positionBoard = playerDao.selectPositionByTeam(teams);

        // then
        positionBoard.forEach(System.out::println);
    }
}