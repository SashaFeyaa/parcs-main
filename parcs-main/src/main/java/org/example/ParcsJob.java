package org.example;

import parcs.AMInfo;
import parcs.task;
import parcs.point;
import parcs.channel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParcsJob {
    public static void main(String[] args) {
        int numWorkers = 2; // Змінна для визначення кількості воркерів

        try {
            String serverFilePath = "server";
            System.out.println("Attempting to read server file from path: " + serverFilePath);
            String serverIP = Files.readString(Paths.get(serverFilePath)).trim();
            System.out.println("Server IP read from file: " + serverIP);

            // Створюємо завдання
            System.out.println("Creating task...");
            task curtask = new task();
            curtask.addJarFile("PalindromeWorker.jar");

            // Генерація основного масиву
            String[] array = generateRandomArray(10000);
            System.out.println("Generated array (first 20 elements):");
            printArray(array, 20);

            // Розділення масиву на підмасиви
            List<String[]> subArrays = splitArray(array, numWorkers);

            List<point> points = new ArrayList<>();
            List<channel> channels = new ArrayList<>();

            for (String[] subArray : subArrays) {
                // Створення нової точки
                point p = curtask.createPoint();
                p.execute("org.example.PalindromeWorker");
                points.add(p);

                // Створення каналу для спілкування з воркером
                channel c = p.createChannel();
                channels.add(c);

                // Відправка підмасиву воркеру через канал
                c.write(subArray);
            }

            int totalPalindromes = 0;
            for (channel c : channels) {
                // Отримання результатів від воркерів
                int numPalindromes = c.readInt();
                totalPalindromes += numPalindromes;
                System.out.println("Number of palindromes from worker: " + numPalindromes);
            }

            System.out.println("Total number of palindromes: " + totalPalindromes);
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

    // Генерація випадкового масиву рядків
    private static String[] generateRandomArray(int size) {
        Random rand = new Random();
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = randomString(rand, 10);  // Створення випадкових рядків довжиною 10 символів
        }
        return array;
    }

    // Генерація випадкового рядка
    private static String randomString(Random rand, int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        return new String(text);
    }

    // Розділення масиву на підмасиви
    private static List<String[]> splitArray(String[] array, int numSubArrays) {
        int subArraySize = array.length / numSubArrays;
        List<String[]> subArrays = new ArrayList<>();
        for (int i = 0; i < numSubArrays; i++) {
            String[] subArray = new String[subArraySize];
            System.arraycopy(array, i * subArraySize, subArray, 0, subArraySize);
            subArrays.add(subArray);
        }
        return subArrays;
    }

    // Виведення масиву
    private static void printArray(String[] array, int limit) {
        for (int i = 0; i < Math.min(array.length, limit); i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
