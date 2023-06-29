package org.fastcampus.domain.player;

import lombok.Builder;
import lombok.Getter;
import org.fastcampus.dto.player.PlayerReqDTO;

import java.sql.Timestamp;

@Getter
public class Player {
    private Integer id;
    private Integer teamId;
    private String name;
    private String position;
    private Timestamp createdAt;

    @Builder
    public Player(Integer id, Integer teamId, String name, String position, Timestamp createdAt) {
        this.id=id;
        this.teamId = teamId;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
    }

    public static Player fromReqDTO(PlayerReqDTO.PlayerAddReqDTO playerAddReqDTO){
        return Player.builder()
                .teamId(playerAddReqDTO.getTeamId())
                .name(playerAddReqDTO.getName())
                .position(playerAddReqDTO.getPosition())
                .build();
    }
}
