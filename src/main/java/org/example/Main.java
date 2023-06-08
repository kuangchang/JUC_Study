package org.example;

/**
 * wait()的使用
 */
public class Main {
    // 建议作为锁的引用设置为final类型的，这表明当前锁的对象不可变，防止后续引用指向别的对象，锁住的对象变化
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                System.out.println("有烟没？" + hasCigarette);
                while (!hasCigarette) {
                    System.out.println("没烟，歇会儿");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (hasCigarette) {
                    System.out.println("开始干活");
                }
            }
        }, "小南").start();

//        for (int i = 0; i < 5; i++) {
//            new Thread(() -> {
//                synchronized (room) {
//                    System.out.println("其他人开始干活");
//                }
//            }, "线程" + i).start();
//        }

        new Thread(() -> {
            synchronized (room) {
                System.out.println("外卖到了没？" + hasTakeout);
                while (!hasTakeout) {
                    System.out.println("外卖没到，歇会儿");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (hasTakeout) {
                    System.out.println("外卖到了，开始干活");
                }
            }
        }, "小女").start();

        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                System.out.println("外卖来了");
                room.notifyAll();
            }
        }, "送烟的").start();
    }
}