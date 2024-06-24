package org.example;

import parcs.AM;
import parcs.AMInfo;
import parcs.channel;

public class PalindromeWorker implements AM {
    @Override
    public void run(AMInfo info) {
        channel c = info.createChannel(); // Отримання каналу через AMInfo
        String[] subArray = (String[]) c.readObject(); // Читання об'єкту з каналу

        int count = 0;
        for (String element : subArray) {
            if (isPalindrome(element)) {
                count++;
            }
        }

        // Відправка результату назад до керуючого простору
        c.write(count);
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
