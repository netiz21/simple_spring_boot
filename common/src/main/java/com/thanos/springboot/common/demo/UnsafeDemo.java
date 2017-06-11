package com.thanos.springboot.common.demo;

import org.apache.commons.lang3.reflect.FieldUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

/**
 * @author solarknight created on 2016/11/27 11:52
 * @version 1.0
 */
public class UnsafeDemo {
  private static final boolean is64bit = true; // auto detect if possible.

  //    private Unsafe unsafe = Unsafe.getUnsafe();
  private static Unsafe unsafe;

  static {
    try {
      Field f = Unsafe.class.getDeclaredField("theUnsafe");
      f.setAccessible(true);
      unsafe = (Unsafe) f.get(null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static int addressSize() {
    return unsafe.addressSize();
  }

  public static int pageSize() {
    return unsafe.pageSize();
  }

  @SuppressWarnings("unchecked")
  public static <T> T allocateInstance(Class<T> cls) {
    try {
      return (T) unsafe.allocateInstance(cls);
    } catch (InstantiationException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> void putInt(T target, long offset, int value) {
    unsafe.putInt(target, offset, value);
  }

  public static long getFieldOffset(Field field) {
    return unsafe.objectFieldOffset(field);
  }

  private static class Guard {
    private int access = 1;

    public boolean checkAccess() {
      return access == 42;
    }
  }

  public static <T> long sizeOf(T target) {
    HashSet<Field> fields = new HashSet<Field>();
    Class c = target.getClass();
    while (c != Object.class) {
      for (Field f : c.getDeclaredFields()) {
        if ((f.getModifiers() & Modifier.STATIC) == 0) {
          fields.add(f);
        }
      }
      c = c.getSuperclass();
    }

    // get offset
    long maxSize = 0;
    for (Field f : fields) {
      long offset = unsafe.objectFieldOffset(f);
      if (offset > maxSize) {
        maxSize = offset;
      }
    }

    return ((maxSize / 8) + 1) * 8;   // padding
  }

  public static void printAddresses(String label, Object... objects) {
    System.out.print(label + ": 0x");
    long last = 0;
    int offset = unsafe.arrayBaseOffset(objects.getClass());
    int scale = unsafe.arrayIndexScale(objects.getClass());
    switch (scale) {
      case 4:
        long factor = is64bit ? 8 : 1;
        final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
        System.out.print(Long.toHexString(i1));
        last = i1;
        for (int i = 1; i < objects.length; i++) {
          final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
          if (i2 > last)
            System.out.print(", +" + Long.toHexString(i2 - last));
          else
            System.out.print(", -" + Long.toHexString(last - i2));
          last = i2;
        }
        break;
      case 8:
        throw new AssertionError("Not supported");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    UnsafeDemo demo = new UnsafeDemo();
    System.out.println(demo.addressSize());
    System.out.println(demo.pageSize());

    // 使用Unsafe类，打破枚举的约定
    Singleton instance = demo.allocateInstance(Singleton.class);
    assert instance != null;
    // 利用反射赋值，并与Singleton.INSTANCE比较
    try {
      FieldUtils.writeDeclaredField(instance, "name", "hihihi", true);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    System.out.println(instance.equals(Singleton.INSTANCE));
    System.out.println(instance.toString().equals(Singleton.INSTANCE.toString()));
    for (Singleton singleton : Singleton.values()) {
      System.out.println(singleton == instance);
      System.out.println(singleton == Singleton.INSTANCE);
    }

    // 修改字段
    System.out.println("==========================================");
    Guard[] guard = new Guard[2];
    for (int i = 0; i < guard.length; i++) {
      guard[i] = new Guard();
    }
    System.out.println(guard[0].checkAccess());
    System.out.println(guard[1].checkAccess());
    try {
      demo.putInt(guard[0], demo.getFieldOffset(Guard.class.getDeclaredField("access")), 42);
      demo.putInt(guard[0], 16 + demo.getFieldOffset(Guard.class.getDeclaredField("access")), 42);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    System.out.println(guard[0].checkAccess());
    System.out.println(guard[1].checkAccess());

    System.out.println(sizeOf(guard[0]));

    Object myObj = new Object();
    Object myObj2 = new Object();
    System.out.println(myObj);
    printAddresses("My obj", myObj);
    System.out.println(myObj2);
    printAddresses("My obj2", myObj2);

    System.out.println("==========================================");
  }
}
