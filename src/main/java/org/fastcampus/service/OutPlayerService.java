package org.fastcampus.service;

import org.fastcampus.domain.outPlayer.OutPlayer;
import org.fastcampus.domain.outPlayer.OutPlayerDao;
import org.fastcampus.domain.player.PlayerDao;
import org.fastcampus.dto.outPlayer.OutPlayerRequestDTO;

import java.sql.Connection;

public class OutPlayerService {
    private static OutPlayerService outPlayerService;
    private Connection connection;
    private PlayerDao playerDao;
    private OutPlayerDao outPlayerDao;

    private OutPlayerService(Connection connection) {
        this.connection=connection;
        this.playerDao = PlayerDao.getInstance(connection);
        this.outPlayerDao = OutPlayerDao.getInstance(connection);
    }

    public static OutPlayerService getInstance(Connection connection){
        if(outPlayerService ==null){
            outPlayerService = new OutPlayerService(connection);
        }
        return outPlayerService;
    }

    //@Todo 정의되지 않은 이유 입력 시 예외처리(위치 정해야함. 아마 main에서?)
    public String outPlayerAdd(OutPlayerRequestDTO.OutPlayerAddReqDTO outPlayerAddReqDTO) {
        int resultInsert = 0;
        int resultUpdate = 0;

        try {
            // ---------------------------------------  트랜잭션 시작
            connection.setAutoCommit(false);

            // 1. out_player에 퇴출 선수를 insert
            OutPlayer outPlayer = OutPlayer.fromReqDTO(outPlayerAddReqDTO);
            resultInsert = outPlayerDao.insert(outPlayer);

            // 2. player 테이블에서 해당 선수의 team_id를 null로 변경
            resultUpdate = playerDao.updateOutById(outPlayer.getPlayerId());

            connection.commit();
            // --------------------------------------- 트랜잭션 종료
        } catch (Exception e) {
            try {
                connection.rollback();
                System.out.println("[rollback] "+e.getMessage());
            }catch (Exception innerEx){
                innerEx.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return (resultInsert == 1 && resultUpdate == 1) ? "선수 퇴출 등록 성공" : "선수 퇴출 등록 실패";
    }
}
