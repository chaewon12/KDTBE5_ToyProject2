package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.player.Player;
import org.fastcampus.domain.player.PlayerDao;
import org.fastcampus.dto.player.PlayerRequestDTO;

import java.sql.Connection;

public class PlayerService {
    Connection connection = DBConnection.getInstance();
    PlayerDao playerDao = PlayerDao.getInstance(connection);

    public String playerAdd(PlayerRequestDTO.PlayerAddReqDTO playerAddReqDTO) {
        Player player = Player.fromReqDTO(playerAddReqDTO);

        int result = playerDao.insert(player);
        return result == 1 ? "선수등록 성공" : "선수등록 실패";
    }
}
