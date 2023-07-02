package org.fastcampus.util.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutReason {
    BEFORE_OUT("",0),
    FAMILY_AFFAIRS("집안사정",1),
    INJURY("부상",2),
    REGULATIONS_VIOLATION("규정위반",3),
    GAMBLING("도박",4),
    CONTRACT_EXPIRATION("계약만료",5),
    UNDEFINED_REASON("정의되지 않은 사유입니다.",-1);

    private final String descrition;
    private final int code;

    public static OutReason fromDescrition(String descrition) {
        for (OutReason status : OutReason.values()) {
            if (status.getDescrition().equals(descrition)) {
                return status;
            }
        }
        return UNDEFINED_REASON;
    }

    public static OutReason fromCode(int code) {
        for (OutReason status : OutReason.values()) {
            if (status.getCode()==code) {
                return status;
            }
        }
        return UNDEFINED_REASON;
    }
}
