package com.epam.gym.app.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("ViewProviderTest")
class ViewProviderTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("printMessage() prints String to console")
    void printMessage_shouldPrintString_whenIsOnlyOneInput() {

        String message = "Message";
        Scanner scanner = new Scanner(System.in);
        ViewProvider viewProvider = new ViewProvider(scanner);

        viewProvider.printMessage(message);

        assertEquals("Message", outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("printList() prints List to console")
    void printList_shouldPrintList_whenListIsCorrect() {

        List<String> list = List.of("One", "Two", "Tree");
        String expected =
                "One\r\n" +
                "Two\r\n" +
                "Tree";
        Scanner scanner = new Scanner(System.in);
        ViewProvider viewProvider = new ViewProvider(scanner);

        viewProvider.printList(list);

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("read() should return String when Console input is something correct")
    void read_shouldReturnString_whenConsoleInputIsSomethingCorrect() {

        String input = "Console message";
        String expected = "Console message";

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        ViewProvider viewProvider = new ViewProvider(scanner);

        assertEquals(expected, viewProvider.read());
    }

    @Test
    @DisplayName("readInt() should return int when console input is int")
    void readInt_shouldReturnInt_whenConsoleInputIsInt() {

        String input = "12";
        int expected = 12;

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        ViewProvider viewProvider = new ViewProvider(scanner);

        assertEquals(expected, viewProvider.readInt());
    }

    @Test
    @DisplayName("readLong() should return long when console input is int")
    void readLong_shouldReturnLong_whenConsoleInputIsLong() {

        String input = "12";
        long expected = 12L;

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner scanner = new Scanner(System.in);
        ViewProvider viewProvider = new ViewProvider(scanner);

        assertEquals(expected, viewProvider.readLong());
    }
}
