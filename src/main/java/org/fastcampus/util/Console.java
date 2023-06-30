package org.fastcampus.util;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.stadium.Stadium;
import org.fastcampus.dto.outPlayer.OutPlayerReqDTO;
import org.fastcampus.dto.outPlayer.OutPlayerRespDTO;
import org.fastcampus.dto.player.PlayerReqDTO;
import org.fastcampus.dto.player.PlayerRespDTO;
import org.fastcampus.dto.team.TeamRespDTO;
import org.fastcampus.service.OutPlayerService;
import org.fastcampus.service.PlayerService;
import org.fastcampus.service.StadiumService;
import org.fastcampus.service.TeamService;
import org.fastcampus.util.type.Option;
import org.fastcampus.util.type.OutReason;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Console {
    private StadiumService stadiumService;
    private TeamService teamService;
    private Connection connection;
    private PlayerService playerService;
    private OutPlayerService outPlayerService;
    private Scanner scanner;

    public Console() {
        connection = DBConnection.getInstance();
        teamService = TeamService.getInstance(connection);
        playerService = PlayerService.getInstance(connection);
        stadiumService = StadiumService.getInstance(connection);
        outPlayerService = OutPlayerService.getInstance(connection);
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("어떤 기능을 요청하시겠습니까?");
        String input = scanner.nextLine();
        parseInput(input);
    }

    private void parseInput(String input) {
        String[] splitInput = input.split("\\?");

        String optionStr = splitInput[0];
        Option option = getOption(optionStr);
        if (option != null) {
            if (option == Option.STADIUM_LIST) {
                List<Stadium> stadiumList = stadiumService.selectAllStadium();
                stadiumList(stadiumList);
            } else if (option == Option.TEAM_LIST) {
                List<TeamRespDTO> teamList = teamService.selectAllTeam();
                teamList(teamList);
            } else {
                Map<String, String> paramMap = parseParams(splitInput[1]);
                switch (option) {
                    case STADIUM_CREATE:
                        try {
                            registerStadium(paramMap);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case TEAM_CREATE:
                        registerTeam(paramMap);
                        break;
                    case PLAYER_CREATE:
                        addPlayer(paramMap);
                        break;
                    case PLAYER_LIST:
                        playerList(paramMap);
                        break;
                    case OUTPLAYER_CREATE:
                        addOutPlayer(paramMap);
                        break;
                    case OUTPLAYER_LIST:
                        outPlayerList();
                        break;
                    case POSITION_LIST:
                        positionBoard();
                        break;
                    default:
                        System.out.println("잘못된 옵션입니다.");
                        break;
                }
            }
        } else {
            System.out.println("잘못된 명령어입니다.");
        }
    }

    private Map<String, String> parseParams(String params) {
        Map<String, String> paramMap = new HashMap<>();
        String[] paramPairs = params.split("&");
        for (String pair : paramPairs) {
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            paramMap.put(key, value);
        }
        return paramMap;
    }

    private Option getOption(String commandStr) {
        for (Option option : Option.values()) {
            if (option.getOption().equals(commandStr)) {
                return option;
            }
        }
        return null;
    }


    //야구장등록
    private void registerStadium(Map<String, String> paramMap) throws SQLException {
        String name = paramMap.get("name");
        String result = stadiumService.registerStadium(name);
        System.out.println(result);
    }

    //야구장목록
    private void stadiumList(List<Stadium> stadiumList) {
            System.out.printf("%2s %17s %17s%n","ID", "NAME", "CREATED AT");
            System.out.println("------------------------------------------------------------");
            for (Stadium stadium : stadiumList) {
                System.out.printf("%2d %17s %17s%n", stadium.getId(), stadium.getName(), stadium.getCreatedAt());
            }
            System.out.println("------------------------------------------------------------");
        }

    //팀등록
    private void registerTeam(Map<String, String> paramMap) {
        String stadiumIdStr = paramMap.get("stadiumId");
        String teamName = paramMap.get("name");

        try {
            int stadiumId = Integer.parseInt(stadiumIdStr);
            String result = teamService.registerTeam(stadiumId, teamName);
            System.out.println(result);
        } catch (NumberFormatException | SQLException e) {
            System.out.println("잘못된 입력 형식입니다.");
            return;
        }
    }

    //팀목록
    private void teamList(List<TeamRespDTO> teamList) {
        System.out.printf("%2s %17s %17s %17s %17s%n", "ID", "NAME", "STADIUM_ID", "STADIUM_NAME", "CREATED_AT");
        System.out.println("------------------------------------------------------------");
        for (TeamRespDTO team : teamList) {
            System.out.printf("%2d %17s %17d %17s %17s%n", team.getId(), team.getName(), team.getStadiumId(), team.getStadium_name(), team.getCreatedAt());
        }
        System.out.println("------------------------------------------------------------");
    }

    // 선수등록
    private void addPlayer(Map<String, String> paramMap) {
        String teamIdStr = paramMap.get("teamId");
        String name = paramMap.get("name");
        String position = paramMap.get("position");

        int teamId;
        try {
            teamId = Integer.parseInt(teamIdStr);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력 형식입니다.");
            return;
        }

        PlayerReqDTO.PlayerAddReqDTO playerAddReqDTO = new PlayerReqDTO.PlayerAddReqDTO(teamId, name, position);

        String result = playerService.addPlayer(playerAddReqDTO);
        System.out.println(result);
    }

    // 선수목록
    private void playerList(Map<String, String> paramMap) {
        String teamIdStr = paramMap.get("teamId");

        int teamId;
        try {
            teamId = Integer.parseInt(teamIdStr);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력 형식입니다.");
            return;
        }

        List<PlayerRespDTO.PlayerListRespDTO> playerList = playerService.getPlayerList(teamId);

        System.out.println("------------------------------------------------------------");
        System.out.printf("%2s %17s %17s", "ID", "NAME", "POSITION");
        System.out.println("\n------------------------------------------------------------");
        playerList.forEach(System.out::println);
        System.out.println("------------------------------------------------------------");
    }

    // 퇴출등록
    private void addOutPlayer(Map<String, String> paramMap) {
        String playerIdStr = paramMap.get("playerId");
        String reason = paramMap.get("reason");

        int playerId;
        try {
            playerId = Integer.parseInt(playerIdStr);
        } catch (NumberFormatException e) {
            System.out.println("잘못된 입력 형식입니다.");
            return;
        }

        OutReason outReason = OutReason.fromDescrition(reason);
        if(outReason==OutReason.UNDEFINED_REASON){
            System.out.println(outReason.getDescrition());
            return;
        }

        OutPlayerReqDTO.OutPlayerAddReqDTO outPlayerAddReqDTO = new OutPlayerReqDTO.OutPlayerAddReqDTO(playerId,outReason);

        String result = outPlayerService.addOutPlayer(outPlayerAddReqDTO);
        System.out.println(result);
    }

    // 퇴출목록
    private void outPlayerList() {
        List<OutPlayerRespDTO.OutBoardRespDTO> outBoard = outPlayerService.getOutBoard();

        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%2s %17s %17s %17s %15s", "ID", "NAME", "POSITION", "REASON(이유)", "DAY(퇴출일)");
        System.out.println("\n-----------------------------------------------------------------------------");
        outBoard.forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------");
    }

    // 포지션별목록
    private void positionBoard() {
        List<PlayerRespDTO.positionBoardRespDTO> positionBoard = playerService.getPositionBoard();

        System.out.println("-----------------------------------------------------------------------------");
        System.out.print(positionBoard.get(0));
        System.out.println("-----------------------------------------------------------------------------");
        positionBoard.stream().skip(1).forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------");
    }
}

