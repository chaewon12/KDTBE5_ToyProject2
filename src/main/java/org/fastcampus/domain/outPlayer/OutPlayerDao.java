package org.fastcampus.domain.outPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OutPlayerDao {
    private static OutPlayerDao outPlayerDao;
    private Connection connection;
    private OutPlayerDao(Connection connection) {
        this.connection = connection;
    }
    public static OutPlayerDao getInstance(Connection connection){
        if(outPlayerDao==null){
            outPlayerDao= new OutPlayerDao(connection);
        }
        return outPlayerDao;
    }
    public int insert(OutPlayer outPlayer){
        int result;
        try{
            String insertQuery = "INSERT INTO out_player_tb VALUES (null, ?, ?, now())";
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, outPlayer.getPlayerId());
                statement.setInt(2, outPlayer.getReason());
                result=statement.executeUpdate();
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return 0;
        }
        return result;
    }
}
