package org.fastcampus.domain.outPlayer;

import lombok.Builder;
import lombok.Getter;
import org.fastcampus.dto.outPlayer.OutPlayerRequestDTO;

import java.sql.Timestamp;

@Getter
public class OutPlayer {
    private Integer id;
    private Integer playerId;
    private Integer reason;
    private Timestamp createdAt;

    @Builder
    public OutPlayer(Integer id, Integer playerId, Integer reason, Timestamp createdAt) {
        this.id = id;
        this.playerId = playerId;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    public static OutPlayer fromReqDTO(OutPlayerRequestDTO.OutPlayerAddReqDTO outPlayerAddReqDTO) {
        return OutPlayer.builder()
                .playerId(outPlayerAddReqDTO.getPlayerId())
                .reason(outPlayerAddReqDTO.getOutReason().getCode())
                .build();
    }
}
