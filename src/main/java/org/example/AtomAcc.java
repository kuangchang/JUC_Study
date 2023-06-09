package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 原子累加器
 */
public class AtomAcc {

    /**
     * AtomicLong 和 LongAdder 累加器的性能比较
     * LongAdder的性能更好，原因很简单：
     * 在有竞争时，设置多个累加单元，t0累加cell[0]，t1累加cell[1]...
     * 累加时操作不同的cell变量，最后将结果汇总，因此减少了cas重试失败次数，提高了性能。
     * @param args
     */
    public static void main(String[] args) {
        demo(
                () -> new AtomicLong(0),
                (adder) -> adder.getAndIncrement()
        );
        demo(
                () -> new LongAdder(),
                (addr) -> addr.increment()
        );
    }

    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        // 4个线程，每人累加50w
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end - start)/1000_000);
    }

}
