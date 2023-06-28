package org.fastcampus.domain.player;

import lombok.Builder;
import lombok.Getter;
import org.fastcampus.dto.player.PlayerRequestDTO;

import java.sql.Timestamp;

@Getter
public class Player {
    private Integer id;
    private Integer teamId;
    private String name;
    private String position;
    private Timestamp createdAt;

    @Builder
    public Player(Integer teamId, String name, String position) {
        this.teamId = teamId;
        this.name = name;
        this.position = position;
    }

    public static Player fromReqDTO(PlayerRequestDTO.PlayerAddReqDTO playerAddReqDTO){
        return Player.builder()
                .teamId(playerAddReqDTO.getTeamId())
                .name(playerAddReqDTO.getName())
                .position(playerAddReqDTO.getPosition())
                .build();
    }
}
