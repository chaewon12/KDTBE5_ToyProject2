package org.fastcampus.service;

import org.fastcampus.dto.outPlayer.OutPlayerRequestDTO;
import org.fastcampus.util.type.OutReason;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OutPlayerServiceTest {
    OutPlayerService outPlayerService = new OutPlayerService();
    @Test
    void outPlayerAdd_success_test() {
        // given
        OutPlayerRequestDTO.OutPlayerAddReqDTO outPlayerAddReqDTO =
                OutPlayerRequestDTO.OutPlayerAddReqDTO.builder()
                        .playerId(5)
                        .outReason(OutReason.CONTRACT_EXPIRATION)
                        .build();

        // when
        String result = outPlayerService.outPlayerAdd(outPlayerAddReqDTO);

        // then
        Assertions.assertEquals("선수 퇴출 등록 성공",result);

    }

    @Test
    void outPlayerAdd_fail_test() {
        // given
        OutPlayerRequestDTO.OutPlayerAddReqDTO outPlayerAddReqDTO =
                OutPlayerRequestDTO.OutPlayerAddReqDTO.builder()
                        .playerId(30)   //  없는 선수
                        .outReason(OutReason.INJURY)
                        .build();

        // when
        String result = outPlayerService.outPlayerAdd(outPlayerAddReqDTO);

        // then
        Assertions.assertEquals("선수 퇴출 등록 실패",result);

    }
}