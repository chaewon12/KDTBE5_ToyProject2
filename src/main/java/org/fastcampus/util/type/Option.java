package org.fastcampus.util.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.fastcampus.service.OutPlayerService;
import org.fastcampus.service.PlayerService;
import org.fastcampus.service.StadiumService;
import org.fastcampus.service.TeamService;

@Getter
@AllArgsConstructor
public enum Option {
    STADIUM_LIST("야구장목록", 0, StadiumService.class),
    TEAM_LIST("팀목록", 0, TeamService.class),
    STADIUM_CREATE("야구장등록", 1, StadiumService.class),
    TEAM_CREATE("팀등록", 2, TeamService.class),
    PLAYER_CREATE("선수등록", 3, PlayerService.class),
    PLAYER_LIST("선수목록", 1, PlayerService.class),
    OUTPLAYER_CREATE("퇴출등록", 2, OutPlayerService.class),
    OUTPLAYER_LIST("퇴출목록", 1, OutPlayerService.class),
    POSITION_LIST("포지션별목록", 0, PlayerService.class),
    EXIT("종료", -1, null);

    private final String option;
    private final int numParameters;
    private final Class<?> serviceClass;

    public String getOption() {
        return option;
    }
}


