# Gym Application

## Table of Contents
1. [Introduction](#introduction)
2. [Technologies Used](#technologies-used)
3. [Challenges](#challenges)
4. [Getting Started](#getting-started)
5. [Additional Features](#additional-features)
6. [Contributing](#contributing)
7. [Pay attention](#pay-attention)

## Introduction

This console application is developed as a task application for Epam company Java Specialization Course.
The application handles gym CRM system.

## Technologies Used

- Java 21
- Spring Boot
- Log4j2
- JUnit
- Jackson
- Jacoco

## Challenges

Reading .json files with test data while running the application from .jar file.
To solve this problem I've done:
- switched from the relational file path, e.g. "/src/main/resources/json/trainee.json" 
to classpath e.g. classpath:json/trainee.json. But it didn't help me because of @PostConstruct annotation above init() method and classpath was invoked before its initialization in result, FileNotFound exception occurred.
- switched from @Value String path to @Value Resource resource and it's finally helped to initialize resource before @PostConstruct annotation came into play.

## Getting Started
- to build application the installed on your computer maven application is required
- please, download to your PC the Number gym-app folder from the GitHub repository and extract files from the archive: https://github.com/AndriiChipets/gym-app
- run the command line interface inside folder of the Number Processor application: gym-app
- to build an application jar file, please run in cmd following command: mvn clean package
- move to the target folder: cd target
- to run an application jar file, please execute following command: java -jar gym-app-1.0-SNAPSHOT.jar
- you should be able to see program interface:  
============ Please, choose the operation ============  
1 -> Create Trainee  
2 -> Update Trainee  
3 -> Delete Trainee  
4 -> Select Trainee  
5 -> Create Trainer  
6 -> Update Trainer  
7 -> Select Trainer  
8 -> Create Training  
9 -> Select Training  
10 -> Select All Trainees  
11 -> Select All Trainers  
12 -> Select All Trainings  
0 -> To exit from the program  


## Additional Features

- Readme file to guide developers and contributors

## Contributing

Contributions are welcome! If you have ideas or improvements, feel free to submit a pull request.

## Pay attention

### The application is still under development, so bugs and drawbacks are possible.