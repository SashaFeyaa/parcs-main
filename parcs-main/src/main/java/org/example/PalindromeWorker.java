package org.example;
import org.example.Node;


import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import java.io.Serializable;

public class PalindromeWorker implements AM {

    public void run(AMInfo info) {
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
            if (isPalindrome(String.valueOf(i))) {
                System.out.println(i + " is a Palindrome");
                sum += i;
            }
        }

        System.out.println("[" + start + " " + end + "] Build finished.");
        info.parent.write(sum);
    }

    private boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
