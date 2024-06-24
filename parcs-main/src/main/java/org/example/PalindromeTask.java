package org.example;

import parcs.*;
import java.util.*;

public class PalindromeTask implements AM {
    private static final int NODES = 4;

    private static boolean isPalindrome(int number) {
        String str = String.valueOf(number);
        int len = str.length();
        for (int i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public void run(AMInfo info) {
        Node n = (Node) info.parent.readObject();

        int totalNumbers = n.r - n.l + 1;
        int numbersPerNode = totalNumbers / NODES;
        int extra = totalNumbers % NODES;

        int start = n.l + n.div * numbersPerNode + Math.min(n.div, extra);
        int end = start + numbersPerNode - 1;
        if (n.div < extra) {
            end += 1;
        }

        System.out.println("[" + start + " " + end + "] Build started.");

        long sum = 0L;
        for (int i = start; i <= end; i++) {
            if (isPalindrome(i)) {
                System.out.println(i + " is a palindrome");
                sum += i;
            }
        }

        System.out.println("[" + start + " " + end + "] Build finished.");
        info.parent.write(sum);
    }
}
