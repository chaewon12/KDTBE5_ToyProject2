package org.fastcampus.service;

import org.fastcampus.domain.team.TeamDao;
import org.fastcampus.dto.team.TeamRespDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TeamService {
    private static TeamService teamservice;
    private TeamDao teamDao;

    private TeamService(Connection connection) {
        this.teamDao = TeamDao.getInstance(connection);
    }

    public String registerTeam(int stadiumId, String name) throws SQLException {
        int result = teamDao.registerTeam(stadiumId, name);
        return result > 0 ? "성공" : "실패";
    }

    public List<TeamRespDTO> selectAllTeam() {
        List<TeamRespDTO> teamList = teamDao.selectAllTeam();
        return teamList;
    }
}
