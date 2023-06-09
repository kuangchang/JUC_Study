package org.example;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * AtomicReferenceFieldUpdater
 * AtomicIntegerFieldUpdater
 * AtomicLongFieldUpdater
 * 利用字段更新器，能够针对对象的某个域（成员变量）进行原子操作，只能配合volatile修饰的字段使用，否则会出现异常
 */
public class AtomFieldUp {
    public static void main(String[] args) {
        Student stu = new Student();
        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");
        System.out.println(updater.compareAndSet(stu, null, "kc"));
        System.out.println(stu);
    }
}

class Student {
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
