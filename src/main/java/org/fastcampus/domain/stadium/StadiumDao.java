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
    public int registerStadium(String name) throws SQLException {
        int result = 0;
        String query = "insert into stadium_tb values(?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return result;
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
            e.printStackTrace();
        }
        return stadiumList;

    }
}
