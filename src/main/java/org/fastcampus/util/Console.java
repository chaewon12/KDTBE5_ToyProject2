package org.fastcampus.util;

import org.fastcampus.db.DBConnection;
import org.fastcampus.dto.outPlayer.OutPlayerReqDTO;
import org.fastcampus.dto.outPlayer.OutPlayerRespDTO;
import org.fastcampus.dto.player.PlayerReqDTO;
import org.fastcampus.dto.player.PlayerRespDTO;
import org.fastcampus.service.OutPlayerService;
import org.fastcampus.service.PlayerService;
import org.fastcampus.util.type.OutReason;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Console {
    private Connection connection;
    private PlayerService playerService;
    private OutPlayerService outPlayerService;
    private Scanner scanner;

    public Console() {
        connection = DBConnection.getInstance();
        playerService = PlayerService.getInstance(connection);
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

        String command = splitInput[0];

        if (command.equals("선수등록")) {
            Map<String, String> paramMap = parseParams(splitInput[1]);
            addPlayer(paramMap);
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


}


