package org.fastcampus.domain.player;

import org.fastcampus.exception.UniqueConstraintException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {
    private static PlayerDao playerDao;
    private Connection connection;
    private PlayerDao(Connection connection) {
        this.connection = connection;
    }
    public static PlayerDao getInstance(Connection connection){
        if(playerDao==null){
            playerDao= new PlayerDao(connection);
        }
        return playerDao;
    }

    public int insert(Player player){
        int result;
        try{
            // 1. 포지션 제약조건 예외처리
            String checkUNQuery = "SELECT * FROM player_tb WHERE team_id = ? AND position = ?";
            try (PreparedStatement statement = connection.prepareStatement(checkUNQuery);) {
                statement.setInt(1, player.getTeamId());
                statement.setString(2, player.getPosition());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        throw new UniqueConstraintException("같은 포지션에 두 명의 선수가 등록될 수 없습니다");
                    }
                }
            }

            // 2. insert
            String insertQuery = "INSERT INTO player_tb VALUES (null, ?, ?, ?, now())";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, player.getTeamId());
                statement.setString(2, player.getName());
                statement.setString(3, player.getPosition());
                result=statement.executeUpdate();
            }

        }catch(UniqueConstraintException | SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
        return result;
    }

    public List<Player> findByTeamId(int teamId){
        List<Player> playerList = new ArrayList<>();
        try{
            String checkUNQuery = "SELECT * FROM player_tb WHERE team_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(checkUNQuery);) {
                statement.setInt(1, teamId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Player player = fromResultSet(resultSet);
                        playerList.add(player);
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return playerList;
    }

    private Player fromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int teamId = resultSet.getInt("team_id");
        String name = resultSet.getString("name");
        String position = resultSet.getString("position");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return Player.builder()
                .id(id)
                .teamId(teamId)
                .name(name)
                .position(position)
                .createdAt(createdAt)
                .build();
    }
}
