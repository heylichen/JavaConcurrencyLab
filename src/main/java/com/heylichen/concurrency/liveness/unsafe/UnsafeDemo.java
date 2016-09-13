package com.heylichen.concurrency.liveness.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Chen Li on 2016/9/7.
 */
public class UnsafeDemo {
  public static void main(String[] args) throws Exception {
    //Unsafe unsafe = Unsafe.getUnsafe();
    Field f = Unsafe.class.getDeclaredField("theUnsafe");
    f.setAccessible(true);
    Unsafe unsafe = (Unsafe)f.get(null);
    System.out.println("ok");
  }
}
