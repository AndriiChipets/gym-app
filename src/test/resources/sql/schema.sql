DROP TABLE IF EXISTS `trainee_trainer`;
DROP TABLE IF EXISTS `trainings`;
DROP TABLE IF EXISTS `trainers`;
DROP TABLE IF EXISTS `trainees`;
DROP TABLE IF EXISTS `training_types`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `training_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `training_type_name` varchar(255) NOT NULL,
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