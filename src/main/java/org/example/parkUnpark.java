package org.example;

import java.util.concurrent.locks.LockSupport;

/**
 * @author kuangchang
 * @date 2023/6/9 15:54
 */
public class parkUnpark {
    /**
     * park是将线程暂停——线程进入WAIT状态
     * unpark使得线程重新开始运行——线程重新进入RUNNABLE状态
     * 与wait/notify的区别：
     * 1. wait、notify和notifyall必须配合Object Monitor使用，而park、unpark不必
     * 2. park、unpark是以线程为单位来暂停和唤醒线程，而notify只能随机唤醒一个等待线程，notifyall是唤醒所有等待线程，不那么精确
     * 3. park、unpark可以先unpark，而wait、notify不能先notify
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("park");
            LockSupport.park();
            System.out.println("unpark");
        });
        t1.start();
        Thread.sleep(2000);
        LockSupport.unpark(t1);
    }
}
