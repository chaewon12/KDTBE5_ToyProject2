package org.fastcampus.service;

import org.fastcampus.domain.outPlayer.OutPlayer;
import org.fastcampus.domain.outPlayer.OutPlayerDao;
import org.fastcampus.domain.player.PlayerDao;
import org.fastcampus.dto.outPlayer.OutPlayerReqDTO;
import org.fastcampus.dto.outPlayer.OutPlayerRespDTO;

import java.sql.Connection;
import java.util.List;

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

    public String addOutPlayer(OutPlayerReqDTO.OutPlayerAddReqDTO outPlayerAddReqDTO) {
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

    public List<OutPlayerRespDTO.OutBoardRespDTO> getOutBoard(){
        List<OutPlayerRespDTO.OutBoardRespDTO> outBoard =  outPlayerDao.selectPlayerJoinOut();
        return outBoard;
    }
}
