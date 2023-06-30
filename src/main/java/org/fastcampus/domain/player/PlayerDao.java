package org.fastcampus.domain.player;

import org.fastcampus.dto.player.PlayerRespDTO;
import org.fastcampus.dto.team.TeamRespDTO;
import org.fastcampus.exception.UniqueConstraintException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDao {
    private static PlayerDao playerDao;
    private Connection connection;

    private PlayerDao(Connection connection) {
        this.connection = connection;
    }

    public static PlayerDao getInstance(Connection connection) {
        if (playerDao == null) {
            playerDao = new PlayerDao(connection);
        }
        return playerDao;
    }

    public int insert(Player player) {
        int result;
        try {
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
                result = statement.executeUpdate();
            }

        } catch (UniqueConstraintException | SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return result;
    }

    public List<Player> selectByTeamId(int teamId) {
        List<Player> playerList = new ArrayList<>();
        try {
            String query = "SELECT * FROM player_tb WHERE team_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query);) {
                statement.setInt(1, teamId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Player player = toPlayer(resultSet);
                        playerList.add(player);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return playerList;
    }

    public int updateOutById(int id) {
        int result;
        String query = "UPDATE player_tb SET team_id = null  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            result = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return result;
    }

    public List<PlayerRespDTO.positionBoardRespDTO> selectPositionByTeam(List<TeamRespDTO> teamList) {
        List<PlayerRespDTO.positionBoardRespDTO> positionBoard = new ArrayList<>();
        try{
            String teamQuery = teamList.stream()
                    .map(team -> String.format("\n  MAX(CASE WHEN team_name = '%s' THEN data.player_name ELSE ' ' END) AS '%s'",
                            team.getName(), team.getName()))
                    .collect(Collectors.joining(","));

            StringBuilder query = new StringBuilder("SELECT\n  position AS '포지션',");
            query.append(teamQuery);
            query.append("\nFROM\n  (SELECT t.name AS team_name, p.name AS player_name, p.position\n");
            query.append("FROM team_tb t LEFT JOIN player_tb p\n   ON t.id = p.team_id) AS data\n");
            query.append("GROUP BY position");

            try (PreparedStatement statement = connection.prepareStatement(query.toString());) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    positionBoard=toPositionBoard(resultSet);
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return positionBoard;
    }

    private Player toPlayer(ResultSet resultSet) throws SQLException {
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
    private List<PlayerRespDTO.positionBoardRespDTO> toPositionBoard(ResultSet resultSet) throws SQLException {
        List<PlayerRespDTO.positionBoardRespDTO> positionBoard = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        PlayerRespDTO.positionBoardRespDTO columnName = toPositionBoardRespDTO(metaData, columnCount);
        positionBoard.add(columnName);

        while (resultSet.next()) {
            PlayerRespDTO.positionBoardRespDTO columnValue = toPositionBoardRespDTO(resultSet, columnCount);
            positionBoard.add(columnValue);
        }

        return positionBoard;
    }

    private PlayerRespDTO.positionBoardRespDTO toPositionBoardRespDTO(ResultSetMetaData metaData, int columnCount) throws SQLException {
        String position = metaData.getColumnName(1);
        List<String> nameList = new ArrayList<>();

        for (int i = 2; i <= columnCount; i++) {
            nameList.add(metaData.getColumnName(i));
        }

        PlayerRespDTO.positionBoardRespDTO build = PlayerRespDTO.positionBoardRespDTO.builder()
                                                    .position(position)
                                                    .nameList(nameList)
                                                    .build();
        return build;
    }

    private PlayerRespDTO.positionBoardRespDTO toPositionBoardRespDTO(ResultSet resultSet, int columnCount) throws SQLException {
        String position = resultSet.getString(1);
        List<String> nameList = new ArrayList<>();

        for (int i = 2; i <= columnCount; i++) {
            nameList.add(resultSet.getString(i));
        }

        PlayerRespDTO.positionBoardRespDTO build = PlayerRespDTO.positionBoardRespDTO.builder()
                .position(position)
                .nameList(nameList)
                .build();

        return build;
    }
}
