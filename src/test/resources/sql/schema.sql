DROP TABLE IF EXISTS tokens CASCADE;
DROP TABLE IF EXISTS trainee_trainer CASCADE;
DROP TABLE IF EXISTS user_roles CASCADE;
DROP TABLE IF EXISTS trainings CASCADE;
DROP TABLE IF EXISTS trainers CASCADE;
DROP TABLE IF EXISTS trainees CASCADE;
DROP TABLE IF EXISTS training_types CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE training_types (
  id bigint NOT NULL AUTO_INCREMENT,
  training_type_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE roles (
  id bigint NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  is_active boolean NOT NULL,
  id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  user_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tokens (
  id bigint NOT NULL AUTO_INCREMENT,
  token_name varchar(255) NOT NULL,
  is_logged_out boolean NOT NULL,
  user_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (user_id,role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE trainees (
  data_of_birth date DEFAULT NULL,
  id bigint NOT NULL,
  address varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE trainers (
  id bigint NOT NULL,
  training_type_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES users (id),
  FOREIGN KEY (training_type_id) REFERENCES training_types (id)
);

CREATE TABLE trainee_trainer (
  trainee_id bigint NOT NULL,
  trainer_id bigint NOT NULL,
  PRIMARY KEY (trainee_id,trainer_id),
  FOREIGN KEY (trainee_id) REFERENCES trainees (id),
  FOREIGN KEY (trainer_id) REFERENCES `trainers` (id)
);

CREATE TABLE trainings (
  training_date date NOT NULL,
  training_duration int NOT NULL,
  id bigint NOT NULL AUTO_INCREMENT,
  trainee_id bigint DEFAULT NULL,
  trainer_id bigint DEFAULT NULL,
  training_type_id bigint NOT NULL,
  training_name varchar(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (trainee_id) REFERENCES trainees (id),
  FOREIGN KEY (trainer_id) REFERENCES trainers (id),
  FOREIGN KEY (training_type_id) REFERENCES training_types (id)
);