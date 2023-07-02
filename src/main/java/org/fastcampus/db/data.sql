/*
insert into stadium_tb values(null,'잠실', now());
insert into stadium_tb values(null,'사직', now());
insert into stadium_tb values(null,'챔피언스 필드', now());

insert into team_tb values(null,'1','LG트윈스', now());
insert into team_tb values(null,'2','롯데 자이언트', now());
insert into team_tb values(null,'3','KIA타이거즈', now());

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
	# KIA 타이거즈 우익수는 넣지 않는다. 개발 시 테스트 데이터로 사용
	# insert into player_tb values(null,3,'나성범','우익수',now());
    
insert into out_player_tb values(null,1,1,now());
update player_tb set team_id = null where id = 1;

insert into out_player_tb values(null,13,2,now());
update player_tb set team_id = null where id = 13;

insert into out_player_tb values(null,25,3,now());
update player_tb set team_id = null where id = 25;

 */