package org.fastcampus.dto.outPlayer;

import lombok.*;
import org.fastcampus.util.type.OutReason;

import java.sql.Timestamp;

public class OutPlayerRespDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class OutBoardRespDTO{
        private Integer id;
        private String name;
        private String position;
        OutReason outReason;
        Timestamp createdAt;
    }
}
