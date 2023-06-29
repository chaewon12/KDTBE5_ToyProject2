package org.fastcampus.service;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.player.Player;
import org.fastcampus.domain.player.PlayerDao;
import org.fastcampus.dto.player.PlayerRequestDTO;
import org.fastcampus.dto.player.PlayerResponseDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    Connection connection = DBConnection.getInstance();
    PlayerDao playerDao = PlayerDao.getInstance(connection);

    public String addPlayer(PlayerRequestDTO.PlayerAddReqDTO playerAddReqDTO) {
        Player player = Player.fromReqDTO(playerAddReqDTO);

        int result = playerDao.insert(player);
        return result == 1 ? "선수등록 성공" : "선수등록 실패";
    }

    public List<PlayerResponseDTO.PlayerListRespDTO> getPlayerList(int teamId){
        List<Player> playerList = playerDao.selectByTeamId(teamId);

        List<PlayerResponseDTO.PlayerListRespDTO> playerListRespDTOList = new ArrayList<>();
        for(Player player:playerList) {
            PlayerResponseDTO.PlayerListRespDTO playerRespDTO = PlayerResponseDTO.PlayerListRespDTO.fromEntity(player);
            playerListRespDTOList.add(playerRespDTO);
        }
        return playerListRespDTOList;
    }
}
