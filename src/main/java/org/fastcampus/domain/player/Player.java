package org.fastcampus.domain.player;

import lombok.Builder;
import lombok.Getter;

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
}
