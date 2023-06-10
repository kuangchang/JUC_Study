package org.example;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author kuangchang
 * @date 2023/6/10 22:38
 * 循环栅栏，用来进行线程协作，等待线程满足某个计数。构造时设置『计数个数』
 * 每个线程执行到某个需要同步的时刻调用await()方法进行等待，当等待的线程数满足『计数个数』时，继续执行
 */
@Slf4j
public class CyclicBarrierExp {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(2, () -> {
            log.debug("task1、task2 finish...");
        });

        for (int i = 0; i < 2; i++) {
            service.submit(() -> {
                log.debug("task1 begin...");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });

            service.submit(() -> {
                log.debug("task2 begin...");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        service.shutdown();
    }
}
