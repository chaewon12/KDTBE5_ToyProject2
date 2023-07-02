/*
CREATE TABLE `stadium_tb` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`));

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
    
create table player_tb(
	id int auto_increment primary key,
    team_id int,
	name varchar(20) not null,
	position varchar(20) not null,
	created_at timestamp not null,
	constraint player_tb_FK foreign key (team_id) references team_tb(id),
  constraint player_tb_UN unique (team_id,position));

create table out_player_tb(
	id int auto_increment primary key,
  player_id int unique not null,
	reason int not null,
	created_at timestamp not null,
	constraint out_player_tb_FK foreign key (player_id) references player_tb(id));
*/