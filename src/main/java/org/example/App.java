package org.example;

/**
 * join()的使用
 */
public class App {
    static int r = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            r = 10;
        });

        t1.start();
        t1.join();
        System.out.println(r);
    }
}
