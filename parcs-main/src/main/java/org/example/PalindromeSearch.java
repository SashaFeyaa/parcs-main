package org.example;

import parcs.AM;
import parcs.AMInfo;
import java.util.Random;

public class PalindromeSearch implements AM {
    public void run(AMInfo info) {
        String[] array = generateRandomStringArray(10_000);  // Генерація масиву з 10,000 рядків
        System.out.println("Generated array (first 20 elements): ");
        printArray(array, 20);

        long startTime = System.currentTimeMillis();
        String[] palindromes = findPalindromes(array);
        long endTime = System.currentTimeMillis();

        System.out.println("Palindromes (first 20 elements): ");
        printArray(palindromes, 20);

        System.out.println("Array length: " + array.length);
        System.out.println("Number of palindromes: " + palindromes.length);
        System.out.println("Search took " + (endTime - startTime) + " milliseconds.");
    }

    private String[] generateRandomStringArray(int size) {
        Random rand = new Random();
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = generateRandomString(rand.nextInt(10) + 1);  // Рядки випадкової довжини від 1 до 10
        }
        return array;
    }

    private String generateRandomString(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + rand.nextInt(26));  // Генерація випадкових символів a-z
            sb.append(c);
        }
        return sb.toString();
    }

    public static String[] findPalindromes(String[] array) {
        return Arrays.stream(array)
                     .filter(PalindromeSearch::isPalindrome)
                     .toArray(String[]::new);
    }

    public static boolean isPalindrome(String str) {
        int len = str.length();
        for (int i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    private void printArray(String[] array, int limit) {
        for (int i = 0; i < Math.min(array.length, limit); i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
