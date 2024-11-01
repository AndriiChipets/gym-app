INSERT INTO `training_types` (`training_type_name`)
VALUES ('Fitness'),
('Yoga'),
('Zumba'),
('Stretching,'),
('Resistance');

INSERT INTO `users` (`is_active`, `first_name`, `last_name`, `password`, `user_name`)
VALUES  (1, 'Fn', 'Ln', '1234567890', 'Fn.Ln'),
        (0, 'FirstName1', 'LastName1', '0987654321', 'FirstName1.LastName1'),
        (1, 'John', 'Doe', '1111111111', 'John.Doe'),
        (0, 'Jane', 'Smith', '2222222222', 'Jane.Smith'),
        (1, 'Mike', 'Brown', '3333333333', 'Mike.Brown'),
        (0, 'Sara', 'Johnson', '4444444444', 'Sara.Johnson'),
        (1, 'Emily', 'Davis', '5555555555', 'Emily.Davis'),
        (0, 'James', 'Garcia', '6666666666', 'James.Garcia'),
        (1, 'David', 'Martinez', '7777777777', 'David.Martinez'),
        (0, 'Sophia', 'Rodriguez', '8888888888', 'Sophia.Rodriguez'),
        (1, 'Daniel', 'Wilson', '9999999999', 'Daniel.Wilson'),
        (0, 'Olivia', 'Anderson', '0000000000', 'Olivia.Anderson');

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