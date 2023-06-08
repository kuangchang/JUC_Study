package org.example;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomRefer {
    static AtomicReference<String> ref = new AtomicReference<>("kk");

    static AtomicStampedReference<String> ref_stamped = new AtomicStampedReference<>("kk", 0);

    public static void main(String[] args) throws InterruptedException {
//        String prev = ref.get();
        String prev = ref_stamped.getReference();
        int stamp = ref_stamped.getStamp();
        other_stamp();
        Thread.sleep(1000);
        System.out.println(ref_stamped.compareAndSet(prev, "kc", stamp, stamp + 1));
    }

    /**
     * CAS的ABA问题：
     * main主线程想要修改ref值时，是无法感知到other()方法中线程将ref值从kk改为kc，再从kc改为kk的
     * 此时main的cas操作还是会成功的
     */
    private static void other() {
        String prev = ref.get();
        new Thread(() -> {
            System.out.println(ref.compareAndSet(prev, "kc"));
            System.out.println(ref.compareAndSet("kc", "kk"));
        }).start();
    }

    /**
     * 当使用AtomicStampedReference时，能够避免cas的ABA问题
     * 这是因为该原子应用会记录一个版本号，只有在值和版本号均匹配的时候
     * 才会修改成功
     * @throws InterruptedException
     */
    private static void other_stamp() throws InterruptedException {
        String prev = ref_stamped.getReference();
        int stamp = ref_stamped.getStamp();
        new Thread(() -> {
            System.out.println(ref_stamped.compareAndSet(prev, "kc", stamp, stamp + 1));
        }).start();
        Thread.sleep(500);
        new Thread(() -> {
            System.out.println(ref_stamped.compareAndSet(ref_stamped.getReference(), "kk", ref_stamped.getStamp(), stamp + 1));
        }).start();
    }
}
