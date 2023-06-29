package org.fastcampus.domain.team;


import org.fastcampus.domain.stadium.StadiumDao;

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
        String query = "insert into team_tb values(?, ?, ?, now())";
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
    public List<Team> selectAllTeam() {
        List<Team> teamList = new ArrayList<>();
        String query = "select * from team_tb";
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                teamList.add(new Team(
                        resultSet.getInt("id"),
                        resultSet.getInt("stadiumId"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("account_created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamList;
    }
}
