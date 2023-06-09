package org.example;

/**
 * @author kuangchang
 * @date 2023/6/9 16:30
 */
public class Sleep {
    public static void sleep(int second) throws InterruptedException {
        Thread.sleep(second * 1000L);
    }
}
