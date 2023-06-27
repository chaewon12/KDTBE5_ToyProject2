package org.fastcampus.domain.player;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Player {
    private Integer id;
    private Integer team_id;
    private String name;
    private String position;
    private Timestamp created_at;

    @Builder
    public Player(Integer team_id, String name, String position) {
        this.team_id = team_id;
        this.name = name;
        this.position = position;
    }
}
