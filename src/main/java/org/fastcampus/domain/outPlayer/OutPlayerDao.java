package org.fastcampus.domain.outPlayer;

import org.fastcampus.domain.player.Player;
import org.fastcampus.dto.outPlayer.OutPlayerRespDTO;
import org.fastcampus.util.type.OutReason;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String query = "INSERT INTO out_player_tb VALUES (null, ?, ?, now())";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
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

    public List<OutPlayerRespDTO.OutBoardRespDTO> getOutBoard(){
        List<OutPlayerRespDTO.OutBoardRespDTO> outBoardRespDTOList = new ArrayList<>();
        try{
            String query = "SELECT p.id, p.name, p.position, ifnull(o.reason,0) as reason, o.created_at\n" +
                    "FROM  player_tb AS p LEFT OUTER JOIN out_player_tb AS o \n" +
                    "ON o.player_id = p.id;";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        OutPlayerRespDTO.OutBoardRespDTO outBoardRespDTO = toOutBoardRespDTO(resultSet);
                        outBoardRespDTOList.add(outBoardRespDTO);
                    }
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return outBoardRespDTOList;
    }

    private OutPlayerRespDTO.OutBoardRespDTO toOutBoardRespDTO(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String position = resultSet.getString("position");
        int reason = resultSet.getInt("reason");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        OutReason outReason = OutReason.fromCode(reason);

        return OutPlayerRespDTO.OutBoardRespDTO.builder()
                .id(id)
                .name(name)
                .position(position)
                .outReason(outReason)
                .createdAt(createdAt)
                .build();
    }
}
