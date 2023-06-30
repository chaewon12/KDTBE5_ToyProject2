package org.fastcampus.domain.team;


import org.fastcampus.domain.stadium.StadiumDao;
import org.fastcampus.dto.team.TeamRespDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {
    private static TeamDao teamDao;
    private Connection connection;

    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    public static TeamDao getInstance(Connection connection){
        if(teamDao==null){
            teamDao = new TeamDao(connection);
        }
        return teamDao;
    }


    //팀 등록
    public int registerTeam(int stadiumId, String name) throws SQLException {
        int result = 0;
        String query = "INSERT INTO team_tb (stadium_id, name, created_at) VALUES (?, ?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stadiumId);
            statement.setString(2, name);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return result;
    }

    //전체 야구장 목록
    public List<TeamRespDTO> selectAllTeam() {
        List<TeamRespDTO> teamList = new ArrayList<>();
        String query = "SELECT t.id, t.name, t.stadium_id, s.name AS stadium_name, t.created_at "
                + "FROM team_tb t "
                + "INNER JOIN stadium_tb s ON t.stadium_id = s.id";

        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                teamList.add(new TeamRespDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("stadium_id"),
                        resultSet.getString("stadium_name"),
                        resultSet.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamList;
    }
}
