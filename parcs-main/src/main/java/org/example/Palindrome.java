package org.example;

import parcs.*;
import java.util.*;
import java.io.File;

public class Palindrome {
    private final static int NODES = 4;

    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("PalindromeTask.jar");
        ArrayList<Node> nodes = fromFile("words.txt");
        List<String> palindromes = new ArrayList<>();
        AMInfo info = new AMInfo(curtask, null);
        LinkedList<channel> channels = new LinkedList<>();
        for (Node n : nodes) {
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("PalindromeTask");
            c.write(n);
            channels.add(c);
        }
        System.out.println("Waiting for result...");
        for (channel c : channels) {
            String[] partialResult = (String[]) c.readObject();
            palindromes.addAll(Arrays.asList(partialResult));
        }
        System.out.println("Palindromes: " + palindromes);
        curtask.end();
    }

    private static ArrayList<Node> fromFile(String filename) throws Exception {
        Scanner sc = new Scanner(new File(filename));
        List<String> words = new ArrayList<>();
        while (sc.hasNext()) {
            words.add(sc.next());
        }
        int totalWords = words.size();
        ArrayList<Node> res = new ArrayList<>();
        int wordsPerNode = totalWords / NODES;
        int extra = totalWords % NODES;
        for (int i = 0; i < NODES; i++) {
            int start = i * wordsPerNode + Math.min(i, extra);
            int end = start + wordsPerNode - 1;
            if (i < extra) {
                end += 1;
            }
            res.add(new Node(start, end, i));
        }
        return res;
    }
}
