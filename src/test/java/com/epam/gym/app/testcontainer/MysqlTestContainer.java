package com.epam.gym.app.testcontainer;

import org.testcontainers.containers.MySQLContainer;

public class MysqlTestContainer extends MySQLContainer<MysqlTestContainer> {

    private static final String IMAGE_VERSION = "mysql:8.0.36";
    private static MysqlTestContainer container;

    private MysqlTestContainer() {
        super(IMAGE_VERSION);
    }

    public static MysqlTestContainer getInstance() {
        if (container == null) {
            container = new MysqlTestContainer();
        }
        return container;
    }
}
