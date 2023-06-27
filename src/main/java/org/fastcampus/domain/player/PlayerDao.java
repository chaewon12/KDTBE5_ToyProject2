package org.fastcampus.domain.player;

import org.fastcampus.exception.UniqueConstraintException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public int insertPlayer(Player player){
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
}
