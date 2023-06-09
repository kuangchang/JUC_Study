package org.example;

/**
 * @author kuangchang
 * @date 2023/6/9 16:12
 * new、blocked、waiting、timed-waiting、runnabl、terminated
 */
public class ThreadStatus {
    private static final Object obj = new Object();

    public static void main(String[] args) {
        run2waitblock();
        synchronized (obj) {
            obj.notifyAll();
            System.out.println();
        }
    }

    /**
     * 调用wait()进入waiting状态
     * t1、t2均在waitset后等待，当主线程调用notifyall之后，获取到锁的线程进入runnable状态
     * 未获取倒锁的线程进入wait状态
     */
    public static void run2waitblock() {
        new Thread(() -> {
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t2").start();
    }

    /**
     * 调用以下方法进入waiting状态
     */
    public static void join2Waiting() {
        /**
         * wait()
         * join()
         * park()
         */
    }

    /**
     * 调用下面方法进入timed-waiting状态
     */
    public static void toTimedWaiting() {
        /**
         * wait(long n);
         * join(long n);
         * Thread.sleep(long n);
         * LockSupport.parkNanos(long nanos)
         * LockSupport.parkUntil(long millis)
         */
    }

    /**
     * 竞争锁失败线程进入Blocked状态
     */
    public static void toBlocked() {
        /**
         * 线程竞争锁失败，会从runnable进入blocked状态
         * 持锁线程代码同步代码块执行完毕，会唤醒该对象上所有blocked的线程重新竞争
         * 如果其中t线程竞争成功，则进入runnable状态，其他竞争失败的线程仍然blocked
         */
    }
}
