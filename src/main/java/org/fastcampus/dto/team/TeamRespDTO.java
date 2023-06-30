package org.fastcampus.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@ToString
@Builder
@Getter
public class TeamRespDTO {
    private int id;
    private String name;
    private int stadiumId;
    private String stadium_name;
    private Timestamp createdAt;
}
