package com.epam.gym.app.view;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ViewProvider {

    private final Scanner scanner;

    public ViewProvider(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String read() {
        return scanner.nextLine();
    }

    public int readInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    public long readLong() {
        return Long.parseLong(scanner.nextLine());
    }
}
