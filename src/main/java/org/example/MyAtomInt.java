package org.example;

import sun.misc.Unsafe;

import java.util.concurrent.CountDownLatch;

/**
 * @author kuangchang
 * @date 2023/6/13 17:27
 */
public class MyAtomInt {
    public static void main(String[] args) throws InterruptedException {
        MyAtomInt myAtomInt = new MyAtomInt(100000);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    myAtomInt.decrement(1);
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println(myAtomInt.getValue());
    }

    private volatile int value;
    private static Unsafe unsafe;
    private static long valueOffset;

    static {
        unsafe = UnsafeUnit.getUnsafe();
        try {
            valueOffset = unsafe.objectFieldOffset(MyAtomInt.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MyAtomInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void decrement(int delta) {
        while (true) {
            int prev = value;
            int next = prev - delta;
            if(unsafe.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }
}
