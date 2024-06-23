package org.example;

import parcs.AMInfo;
import parcs.task;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParcsJob {
    public static void main(String[] args) {
        try {
            String serverFilePath = "server";
            System.out.println("Attempting to read server file from path: " + serverFilePath);
            String serverIP = Files.readString(Paths.get(serverFilePath)).trim();
            System.out.println("Server IP read from file: " + serverIP);

            // Створюємо завдання
            System.out.println("Creating task...");
            task curtask = new task();
            curtask.addJarFile("PalindromeSearch.jar");
            AMInfo info = new AMInfo(curtask, null);
            System.out.println("Running PalindromeSearch...");
            (new PalindromeSearch()).run(info);
            curtask.end();
            System.out.println("Task ended.");
        } catch (IOException e) {
            System.err.println("Error reading server file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
