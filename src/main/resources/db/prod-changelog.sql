--liquibase formatted sql

--changeset gym_app:init_schema
DROP TABLE IF EXISTS `tokens` CASCADE;
DROP TABLE IF EXISTS `trainee_trainer` CASCADE;
DROP TABLE IF EXISTS `user_roles` CASCADE;
DROP TABLE IF EXISTS `trainings` CASCADE;
DROP TABLE IF EXISTS `trainers` CASCADE;
DROP TABLE IF EXISTS `trainees` CASCADE;
DROP TABLE IF EXISTS `training_types` CASCADE;
DROP TABLE IF EXISTS `users` CASCADE;
DROP TABLE IF EXISTS `roles` CASCADE;

CREATE TABLE `training_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `training_type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `users` (
  `is_active` bit(1) NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token_name` varchar(255) NOT NULL,
  `is_logged_out` bit(1) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY (`user_id`),
  CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY (`user_id`),
  CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `trainees` (
  `data_of_birth` date DEFAULT NULL,
  `id` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT FOREIGN KEY (`id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `trainers` (
  `id` bigint NOT NULL,
  `training_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY (`training_type_id`),
  CONSTRAINT FOREIGN KEY (`id`) REFERENCES `users` (`id`),
  CONSTRAINT FOREIGN KEY (`training_type_id`) REFERENCES `training_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `trainee_trainer` (
  `trainee_id` bigint NOT NULL,
  `trainer_id` bigint NOT NULL,
  PRIMARY KEY (`trainee_id`,`trainer_id`),
  KEY (`trainer_id`),
  CONSTRAINT FOREIGN KEY (`trainee_id`) REFERENCES `trainees` (`id`),
  CONSTRAINT FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `trainings` (
  `training_date` date NOT NULL,
  `training_duration` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `trainee_id` bigint DEFAULT NULL,
  `trainer_id` bigint DEFAULT NULL,
  `training_type_id` bigint NOT NULL,
  `training_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY (`trainee_id`),
  KEY (`trainer_id`),
  KEY (`training_type_id`),
  CONSTRAINT FOREIGN KEY (`trainee_id`) REFERENCES `trainees` (`id`),
  CONSTRAINT FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`id`),
  CONSTRAINT FOREIGN KEY (`training_type_id`) REFERENCES `training_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--changeset gym_app:populate_data
INSERT INTO `training_types` (`training_type_name`)
VALUES ('Fitness'),
('Yoga'),
('Zumba'),
('Stretching,'),
('Resistance');

--password for Fn.Ln is 1234567890
--password for FirstName.LastName is 0987654321
--password for John.Doe is 1111111111
INSERT INTO `users` (`is_active`, `first_name`, `last_name`, `password`, `user_name`)
VALUES
        (1, 'Fn', 'Ln', '$2a$12$8gMyOnozLf0R7p5iuV.BuezHcm9vUpxRDIUtlEMA1kJzz7INV0jx2', 'Fn.Ln'),
        (1, 'FirstName', 'LastName', '$2a$12$kYGIfPtVfZJMcoYzzOz15.5UAkgdIwC7Rqee.bRQhYyjZjzi/M38q', 'FirstName.LastName'),
        (1, 'John', 'Doe', '$2a$12$L8lv1loQ3LZwl/SygmltO.Hzh.xZ3fP65LhHaX9Xmvwcf1.HfWW0a', 'John.Doe'),
        (0, 'Jane', 'Smith', '2222222222', 'Jane.Smith'),
        (1, 'Mike', 'Brown', '3333333333', 'Mike.Brown'),
        (0, 'Sara', 'Johnson', '4444444444', 'Sara.Johnson'),
        (1, 'Emily', 'Davis', '5555555555', 'Emily.Davis'),
        (0, 'James', 'Garcia', '6666666666', 'James.Garcia'),
        (1, 'David', 'Martinez', '7777777777', 'David.Martinez'),
        (0, 'Sophia', 'Rodriguez', '8888888888', 'Sophia.Rodriguez'),
        (1, 'Daniel', 'Wilson', '9999999999', 'Daniel.Wilson'),
        (0, 'Olivia', 'Anderson', '0000000000', 'Olivia.Anderson');

INSERT INTO `roles` (`role_name`)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 2),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 2),
(10, 2),
(11, 2),
(12, 2);

INSERT INTO `trainees` (`data_of_birth`, `id`, `address`)
VALUES ('2001-12-12', 1, 'Address1'),
('2002-12-12', 2, 'Address2'),
('2003-12-12', 3, 'Address3'),
('2004-12-12', 4, 'Address4'),
('2005-12-12', 5, 'Address5'),
('2006-12-12', 6, 'Address6'),
('2007-12-12', 7, 'Address7'),
('2008-12-12', 8, 'Address8');

INSERT INTO `trainers` (`id`, `training_type_id`)
VALUES (9, 1),
(10, 2),
(11, 3),
(12, 4);

INSERT INTO `trainee_trainer` (`trainee_id`, `trainer_id`)
VALUES (1, 9),
(1, 10),
(2, 10),
(3, 11),
(4, 12),
(5, 12),
(6, 11),
(7, 10),
(8, 9),
(8, 11),
(3, 12);

INSERT INTO `trainings` (`training_date`, `training_duration`, `trainee_id`, `trainer_id`, `training_type_id`, `training_name`)
VALUES ('2024-10-20', 30, 1, 9, 1, 'TestTraining1'),
('2024-10-21', 40, 2, 9, 2, 'TestTraining2'),
('2024-10-22', 50, 3, 10, 3, 'TestTraining3'),
('2024-10-23', 60, 4, 11, 4, 'TestTraining4'),
('2024-10-24', 70, 5, 11, 5, 'TestTraining5'),
('2024-10-25', 80, 6, 10, 5, 'TestTraining6'),
('2024-10-26', 90, 7, 12, 3, 'TestTraining7'),
('2024-10-27', 100, 8, 12, 1, 'TestTraining8');