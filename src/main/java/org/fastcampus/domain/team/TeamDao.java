package org.fastcampus.domain.team;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {
    private Connection connection;

    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    //팀 등록
    public void registerTeam(int stadiumId, String name) throws SQLException {
        String query = "insert into team_tb values(?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, stadiumId);
            statement.setString(2, name);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            throw new RuntimeException(e);
        }
        return teamList;
    }
}
