package org.fastcampus.service;

import org.fastcampus.domain.player.Player;
import org.fastcampus.domain.player.PlayerDao;
import org.fastcampus.dto.player.PlayerReqDTO;
import org.fastcampus.dto.player.PlayerRespDTO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private static PlayerService playerService;
    private PlayerDao playerDao;
    private PlayerService(Connection connection) {
        this.playerDao = PlayerDao.getInstance(connection);
    }
    public static PlayerService getInstance(Connection connection){
        if(playerService==null){
            playerService= new PlayerService(connection);
        }
        return playerService;
    }
    public String addPlayer(PlayerReqDTO.PlayerAddReqDTO playerAddReqDTO) {
        Player player = Player.fromReqDTO(playerAddReqDTO);

        int result = playerDao.insert(player);
        return result == 1 ? "선수등록 성공" : "선수등록 실패";
    }

    public List<PlayerRespDTO.PlayerListRespDTO> getPlayerList(int teamId){
        List<Player> playerList = playerDao.selectByTeamId(teamId);

        List<PlayerRespDTO.PlayerListRespDTO> playerListRespDTOList = new ArrayList<>();
        for(Player player:playerList) {
            PlayerRespDTO.PlayerListRespDTO playerRespDTO = PlayerRespDTO.PlayerListRespDTO.fromEntity(player);
            playerListRespDTOList.add(playerRespDTO);
        }
        return playerListRespDTOList;
    }
}