package org.example;

import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;

public class PalindromeWorker implements AM {
    @Override
    public void run(AMInfo info) {
        point p = info.createPoint(); // Створення точки для виконання завдання
        channel c = p.createChannel(); // Створення каналу для обміну даними

        Node node = (Node) info.parent.readObject(); // Читання об'єкту Node з керуючого простору

        int totalNumbers = node.r - node.l + 1;
        int numbersPerNode = totalNumbers / ParcsJob.NODES;
        int extra = totalNumbers % ParcsJob.NODES;

        int start = node.l + node.div * numbersPerNode + Math.min(node.div, extra);
        int end = start + numbersPerNode - 1;
        if (node.div < extra) {
            end += 1;
        }

        System.out.println("[" + start + " " + end + "] Build started.");

        long sum = 0L;
        for (int i = start; i <= end; i++) {
            long x = i;
            if (isPalindrome(Long.toString(x))) { // Перевірка на паліндром
                System.out.println(x + " is Palindrome");
                sum += x;
            }
        }

        System.out.println("[" + start + " " + end + "] Build finished.");
        c.write(sum); // Відправка суми паліндромів через канал
    }

    private boolean isPalindrome(String str) {
        int n = str.length();
        for (int i = 0; i < n / 2; i++) {
            if (str.charAt(i) != str.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }
}
