package org.fastcampus.util.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutReason {
    FAMILY_AFFAIRS("집안 사정"),
    INJURY("부상"),
    REGULATIONS_VIOLATION("규정 위반"),
    GAMBLING("도박"),
    CONTRACT_EXPIRATION("계약 만료"),
    UNDEFINED_REASON("정의되지 않은 사유입니다.");

    private final String descrition;

    public static OutReason fromDescrition(String descrition) {
        for (OutReason status : OutReason.values()) {
            if (status.getDescrition().equals(descrition)) {
                return status;
            }
        }
        return UNDEFINED_REASON;
    }
}
