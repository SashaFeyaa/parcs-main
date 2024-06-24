package org.example;

import parcs.*;
import java.util.*;

public class PalindromeTask implements AM {
    private static final int NODES = 4;

    private static boolean isPalindrome(String word) {
        int len = word.length();
        for (int i = 0; i < len / 2; i++) {
            if (word.charAt(i) != word.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public void run(AMInfo info) {
        Node n = (Node) info.parent.readObject();

        int totalWords = n.r - n.l + 1;
        int wordsPerNode = totalWords / NODES;
        int extra = totalWords % NODES;

        int start = n.l + n.div * wordsPerNode + Math.min(n.div, extra);
        int end = start + wordsPerNode - 1;
        if (n.div < extra) {
            end += 1;
        }

        System.out.println("[" + start + " " + end + "] Build started.");

        List<String> words = readWordsFromFile("words.txt", start, end);

        List<String> palindromes = new ArrayList<>();
        for (String word : words) {
            if (isPalindrome(word)) {
                System.out.println(word + " is a palindrome");
                palindromes.add(word);
            }
        }

        System.out.println("[" + start + " " + end + "] Build finished.");
        info.parent.write(palindromes);
    }

    private List<String> readWordsFromFile(String filename, int start, int end) {
        List<String> words = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(filename))) {
            int index = 0;
            while (sc.hasNext() && index <= end) {
                String word = sc.next();
                if (index >= start && index <= end) {
                    words.add(word);
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }
}
