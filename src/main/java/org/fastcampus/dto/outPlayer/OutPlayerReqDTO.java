package org.fastcampus.dto.outPlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.fastcampus.util.type.OutReason;

public class OutPlayerReqDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OutPlayerAddReqDTO{
        Integer playerId;
        OutReason outReason;
    }
}
