# KDTBE_ToyProject2

## 야구 관리 프로그램
[과제 링크](https://drive.google.com/file/d/1F7-pRcg6YXaaaGq625NEr535a2Ehvn2i/view)

## 역할 분담
### 최해솔
- 야구장 테이블, 팀 테이블 설계
- BaseBallApp 구현
- DBConnection 클래스 생성
- 구현 기능
  - 야구장 등록
  - 전체 야구장 목록 보기
  - 팀 등록
  - 전체 팀 목록

### 엄채원
- 선수 테이블, 퇴출선수 테이블 설계
- 구현 기능
  - 선수 등록
  - 팀별 선수 목록
  - 선수 퇴출 등록
  - 선수 퇴출 목록
  - 포지션별 팀 야구 선수 페이지
---
## 데이터베이스
#### 1. 야구장 테이블(총 3개 야구장)

> 요청 : `야구장등록?name=잠실야구장` , `야구장목록`
- 테이블 생성 쿼리

    ```sql
    CREATE TABLE `stadium_tb` (
      `id` INT NOT NULL AUTO_INCREMENT,
      `name` VARCHAR(20) NOT NULL,
      `created_at` TIMESTAMP NOT NULL,
      PRIMARY KEY (`id`));
    ```

- 더미 데이터 쿼리

    ```sql
    insert into stadium_tb values(null,'잠실', now());
    insert into stadium_tb values(null,'사직', now());
    insert into stadium_tb values(null,'챔피언스 필드', now());
    ```


#### 2. 팀 테이블(총 3개 팀)

>요청: `팀등록?stadiumId=4&name=두산베어스`, `팀목록`
- 테이블 생성 쿼리

    ```sql
    CREATE TABLE `team_tb` (
      `id` INT NOT NULL AUTO_INCREMENT,
      `stadium_id` INT NOT NULL,
      `name` VARCHAR(20) NOT NULL,
      `created_at` TIMESTAMP NOT NULL,
      PRIMARY KEY (`id`),
      INDEX `team_tb_FK_idx` (`stadium_id` ASC) VISIBLE,
      CONSTRAINT `team_tb_FK`
        FOREIGN KEY (`stadium_id`)
        REFERENCES `stadium_tb` (`id`));
    ```

- 더미 데이터 쿼리

    ```sql
    insert into team_tb values(null,'1','LG트윈스', now());
    insert into team_tb values(null,'2','롯데 자이언트', now());
    insert into team_tb values(null,'3','KIA타이거즈', now());
    ```


#### 3. 선수 테이블(각 9명)

> 요청: `선수등록?teamId=3&name=나성범&position=우익수`, `선수목록?teamId=3` , `포지션별목록`
- 테이블 생성 쿼리

    ```sql
    create table player_tb(
    	id int auto_increment primary key,
      team_id int,
    	name varchar(20) not null,
    	position varchar(20) not null,
    	created_at timestamp not null,
    	constraint player_tb_FK foreign key (team_id) references team_tb(id),
      constraint player_tb_UN unique (team_id,position));
    ```

- 더미 데이터 쿼리

    ```sql
    # LG 트윈스
    insert into player_tb values(null,1,'손호영','1루수',now());
    insert into player_tb values(null,1,'신민재','2루수',now());
    insert into player_tb values(null,1,'문보경','3루수',now());
    insert into player_tb values(null,1,'박동원','포수',now());
    insert into player_tb values(null,1,'고우석','투수',now());
    insert into player_tb values(null,1,'오지환','유격수',now());
    insert into player_tb values(null,1,'이재원','좌익수',now());
    insert into player_tb values(null,1,'박해민','중견수',now());
    insert into player_tb values(null,1,'문성주','우익수',now());
    # 롯데 자이언츠
    insert into player_tb values(null,2,'고승민','1루수',now());
    insert into player_tb values(null,2,'안치홍','2루수',now());
    insert into player_tb values(null,2,'한동희','3루수',now());
    insert into player_tb values(null,2,'손성빈','포수',now());
    insert into player_tb values(null,2,'댄 스트레일리','투수',now());
    insert into player_tb values(null,2,'박승욱','유격수',now());
    insert into player_tb values(null,2,'황성빈','좌익수',now());
    insert into player_tb values(null,2,'김민석','중견수',now());
    insert into player_tb values(null,2,'윤동희','우익수',now());
    # KIA 타이거즈
    insert into player_tb values(null,3,'변우혁','1루수',now());
    insert into player_tb values(null,3,'류지혁','2루수',now());
    insert into player_tb values(null,3,'김도영','3루수',now());
    insert into player_tb values(null,3,'신범수','포수',now());
    insert into player_tb values(null,3,'양현종','투수',now());
    insert into player_tb values(null,3,'박찬호','유격수',now());
    insert into player_tb values(null,3,'이우성','좌익수',now());
    insert into player_tb values(null,3,'소크라테스 브리토','중견수',now());
    ```


#### 4. 퇴출선수 테이블

> 요청: `퇴출등록?playerId=27&reason=도박`, `퇴출목록`
- 테이블 생성 쿼리

    ```sql
    create table out_player_tb(
    	id int auto_increment primary key,
      player_id int unique not null,
    	reason int not null,
    	created_at timestamp not null,
    	constraint out_player_tb_FK foreign key (player_id) references player_tb(id));
    ```

- 더미 데이터 쿼리

    ```sql
    insert into out_player_tb values(null,1,1,now());
    update player_tb set team_id = null where id = 1;
    
    insert into out_player_tb values(null,13,2,now());
    update player_tb set team_id = null where id = 13;
    
    insert into out_player_tb values(null,25,3,now());
    update player_tb set team_id = null where id = 25;
    ```