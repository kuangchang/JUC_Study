package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author kuangchang
 * @date 2023/6/10 15:53
 * <p>
 * CountDownLatch
 * 用来进行线程同步协作，等待所有线程完成倒计时
 * 其中构造参数用来初始化等待计数值，await()用来等待计数归零，countDown()用来让计数减一
 */

public class CountDownLatchExp {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CountDownLatch latch = new CountDownLatch(10);
        test3();
//        test2(latch);
//        new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            latch.countDown();
//        }).start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            latch.countDown();
//        }).start();
//
//        latch.await();
//        System.out.println("等待结束");
    }

    /**
     * CountDownLatch配合线程池使用
     */
    private static void test1(CountDownLatch latch) {
        ExecutorService service = Executors.newFixedThreadPool(4);

        service.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });

        service.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });

        service.submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });

        service.submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("等待结束");
        });

        service.shutdown();
    }

    /**
     * CountDownLatch的应用——王者荣耀
     */
    private static void test2(CountDownLatch latch) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        String[] array = new String[10];
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int k = i;
            service.submit(() -> {
                for (int j = 0; j <= 100; j++) {
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    array[k] = j + "%";
                    System.out.print("\r" + Arrays.toString(array));
                }
                latch.countDown();
            });
        }
        latch.await();
        service.shutdown();
        System.out.println("\n开始游戏");
    }

    /**
     * 主线程如何获取从线程中的结果
     * 使用future
     */
    private static void test3() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Random random = new Random();
        Future<Integer> f1 = service.submit(() -> {
            System.out.println("线程内");
            return 1;
        });
        Future<Integer> f2 = service.submit(() -> {
            System.out.println("线程内");
            return 2;
        });
        Future<Integer> f3 = service.submit(() -> {
            System.out.println("线程内");
            return 3;
        });
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());
    }
}
