package org.example;

public class Guarded {
    private Object object;

    public Object get() {
        synchronized (this) {
            while (object == null) {
                try {
                    System.out.println("等待结果...");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return object;
        }
    }

    public void set(Object o) {
        synchronized (this) {
            System.out.println("传输结果...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            object = o;
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        Guarded guarded = new Guarded();
        new Thread(() -> {
            Object o = guarded.get();
            System.out.println("结果为：" + o);
        }).start();

        new Thread(() -> {
            guarded.set(0);
        }).start();
    }
}
