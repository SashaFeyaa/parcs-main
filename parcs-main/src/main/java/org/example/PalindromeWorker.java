package org.example;

import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;

// Клас Node, що використовується для передачі даних між керуючим простором і воркерами
class Node implements java.io.Serializable {
    public final int l;
    public final int r;
    public final int div;

    public Node(int l, int r, int div) {
        this.l = l;
        this.r = r;
        this.div = div;
    }
}

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
