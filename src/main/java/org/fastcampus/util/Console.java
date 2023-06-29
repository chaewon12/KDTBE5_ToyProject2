package org.fastcampus.util;

import org.fastcampus.db.DBConnection;
import org.fastcampus.domain.player.PlayerDao;
import org.fastcampus.dto.player.PlayerRequestDTO;
import org.fastcampus.service.PlayerService;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Console {
    private Connection connection;
    private PlayerDao playerDao;
    private PlayerService playerService;
    private Scanner scanner;

    public Console() {
        connection = DBConnection.getInstance();
        playerDao = PlayerDao.getInstance(connection);
        playerService = new PlayerService(playerDao);
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
            String params = splitInput[1];
            Map<String, String> paramMap = parseParams(params);
            String teamIdStr = paramMap.get("teamId");
            String name = paramMap.get("name");
            String position = paramMap.get("position");

            int teamId;
            try {
                teamId = Integer.parseInt(teamIdStr);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 팀 ID입니다.");
                return;
            }

            PlayerRequestDTO.PlayerAddReqDTO playerAddReqDTO = new PlayerRequestDTO.PlayerAddReqDTO(teamId, name, position);

            String result = playerService.playerAdd(playerAddReqDTO);
            System.out.println(result);
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
}


