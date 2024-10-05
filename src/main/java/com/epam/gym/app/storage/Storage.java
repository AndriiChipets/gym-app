package com.epam.gym.app.storage;

import java.util.Map;

public interface Storage<ID, E> {
    Map<ID, E> getData();
}
