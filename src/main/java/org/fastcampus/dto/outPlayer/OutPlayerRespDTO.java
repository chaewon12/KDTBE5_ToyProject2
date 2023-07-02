package org.fastcampus.dto.outPlayer;

import lombok.*;
import org.fastcampus.util.type.OutReason;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class OutPlayerRespDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class OutBoardRespDTO{
        private Integer id;
        private String name;
        private String position;
        OutReason outReason;
        Timestamp createdAt;

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

            return String.format(
                    "%2d %15s %15s %15s %15s",
                    id,
                    name,
                    position,
                    outReason.getDescrition(),
                    createdAt==null?" ":sdf.format(createdAt)
            );
        }
    }
}
