package org.example;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author kuangchang
 * @date 2023/6/13 16:42
 */
public class UnsafeExp {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe =(Unsafe) theUnsafe.get(null);

        Teacher teacher = new Teacher();
//        System.out.println(unsafe);
        // 获取对象域的偏移地址
        long idOffset = unsafe.objectFieldOffset(teacher.getClass().getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(teacher.getClass().getField("name"));

        // 对偏移地址的值执行cas操作

        unsafe.compareAndSwapInt(teacher, idOffset, 0, 25);
        unsafe.compareAndSwapObject(teacher, nameOffset, null, "kc");

        System.out.println(teacher);
    }
}

@Data
class Teacher {
    volatile int id;

    public volatile String name;
}
