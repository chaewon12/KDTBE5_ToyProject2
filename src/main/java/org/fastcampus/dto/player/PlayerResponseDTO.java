package org.fastcampus.dto.player;

import lombok.*;
import org.fastcampus.domain.player.Player;

public class PlayerResponseDTO {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class PlayerListRespDTO{
        private Integer id;
        private String name;
        private String position;

        public static PlayerListRespDTO fromEntity(Player player){
            return PlayerListRespDTO.builder()
                    .id(player.getId())
                    .name(player.getName())
                    .position(player.getPosition())
                    .build();
        }
    }
}
