package com.fengjr.javapoet;

import java.io.IOException;
import java.io.Serializable;
import java.lang.Comparable;
import java.lang.Integer;
import java.lang.Override;
import java.lang.Runnable;
import java.lang.RuntimeException;
import java.lang.String;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;

public final class Clazz<T> extends String implements Serializable, Comparable<String>, Comparable<? extends String> {
  private static final Map<String, T> mfoo = new HashMap();

  static {
    System.out.println("static code");
  }

  {
    System.out.println("block code");
  }

  @Override
  public <T> int method(String str, T t, Map<Integer, ? extends T> map) throws IOException,
      RuntimeException {
    String bar = "a string";
    int foo = 1;;
    Object obj = HashMap<Integer, ? extends T>;
    new Thread(new Runnable(String param) {
      @Override
      void run() {
      }
    }).start();
    for(int i=0;i<5;i++) {
      System.out.println(i);
    }
    while(false) {
      System.out.println("while");
    }
    do {
      System.out.println("do while");
    } while(false);
    if(false) {
      System.out.println("if");
    } else if(false) {
      System.out.println("else if");
    }
    try {
    } catch(IOException e) {
      e.printStatcktrace();
    } finally {
    }
    return 0;
  }
}
