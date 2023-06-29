package org.fastcampus.domain.stadium;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StadiumDao {
    private Connection connection;

    public StadiumDao(Connection connection) {
        this.connection = connection;
    }

    //야구장 등록
    public void registerStadium(String name) throws SQLException {
        String query = "insert into stadium_tb values(?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //전체 야구장 목록
    public List<Stadium> selectAllStadium() {
        List<Stadium> stadiumList = new ArrayList<>();
        String query = "select * from stadium_tb";
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                stadiumList.add(new Stadium(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getTimestamp("account_created_at")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return stadiumList;

    }
}

