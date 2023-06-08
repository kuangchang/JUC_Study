package org.example;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 有时候并不关心引用变量更改了几次，而是单纯的关心引用变量是否更改过
 * 这时候便引入了AtomMarkableReference
 */
public class AtomMarkable {
    public static void main(String[] args) throws InterruptedException {
        GarbageBag garbageBag = new GarbageBag("垃圾满了");
        AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(garbageBag, true);
        System.out.println(ref.getReference());
        new Thread(() -> {
            ref.compareAndSet(garbageBag, garbageBag, true, false);
        }).start();
        Thread.sleep(1000);
        System.out.println(ref.compareAndSet(garbageBag, new GarbageBag("空垃圾袋"), true, false));
        System.out.println(ref.getReference());
    }
}

class GarbageBag {
    private String str;

    public GarbageBag(String str) {
        this.str = str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void check() {
        System.out.println(str);
    }
}
