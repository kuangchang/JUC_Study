package org.example;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author kuangchang
 * @date 2023/6/13 17:28
 */
public class UnsafeUnit {
    private static Unsafe unsafe;

    static {
        Field theUnsafe = null;
        try {
            theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        theUnsafe.setAccessible(true);
        try {
            unsafe =(Unsafe) theUnsafe.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}
