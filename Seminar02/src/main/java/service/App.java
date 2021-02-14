package service;

import java.io.File;
import java.util.Scanner;

import models.MyInteger;
import models.MyList;

public class App {
  public static void main( String[] args ) {
    System.out.println( "12.02.21 Seminar02" );

    MyList<MyInteger> list = new MyList<>(MyInteger.class);

    File f = new File(App.class.getResource("numbers.txt").getFile());
    Scanner scn;

    try { 
      scn = new Scanner(f);

      while (scn.hasNextLine()) {
        if (!scn.hasNextInt()) {
          scn.nextLine();
          continue;
        }

        list.add(new MyInteger(scn.nextInt()));
      }

      scn.close();

    } catch (Exception e) { 
      e.printStackTrace();
      System.exit(e.hashCode());
    }
    list.print();


    System.out.println("\n========================== add() method ==========================\n\t appending 150 random MyIntegers [-20..20] to the list");

    for (int i = 0; i < 150; i++) {
      list.add(new MyInteger((int)(Math.random()*(41)-20)));
    }
    list.print();


    System.out.println("\n========================== setAtIdx(), atIdx() methods ==========================\n\t set random MyIntegers at 10 random indices\n");

    int idx;
    MyInteger old;
    for (int i = 0; i < 10; i++) {
      idx = (int)(Math.random()*(list.elmCount() + 1));
      old = list.atIdx(idx);

      list.setAtIdx(idx, new  MyInteger((int)(Math.random()*(41)-20)));
      System.out.printf("list[%d] = %d => %d\n", idx, old.value, list.atIdx(idx).value);
    }
    list.print();


    MyInteger elm;
    System.out.println("\n========================== seekElement() method ==========================\n\t search for 20 random MyInteger elements\n");
    for (int i = 0; i < 20; i++) {
      elm = new MyInteger((int)(Math.random()*(41)-20));

      try {
        idx = list.seekElement(elm);
        System.out.printf("elm = %d <= at list[%d]\n", elm.value, idx);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    MyInteger elm2;
    System.out.println("\n========================== getNextAfter() method ==========================\n\t search for 20 random elements, and output next after\n");
    for (int i = 0; i < 20; i++) {
      elm = new MyInteger((int)(Math.random()*(41)-20));

      try {
        elm2 = list.getNextAfter(elm);
        System.out.printf("next after elm = %d is %d\n", elm.value, elm2.value);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }


    System.out.println("\n========================== delAtIdx() method ==========================\n\t delete elements at 100 random indices\n");
    for (int i = 0; i < 100; i++) {
      idx = (int)(Math.random()*(list.elmCount()));
      System.out.printf("del list[%d] = %d\n", idx, list.atIdx(idx).value);

      list.delAtIdx(idx);
    }
    list.print();


    System.out.println("\n========================== sort(true) method ==========================\n\t sort list in descending order");
    list.sort(true);
    list.print();


    System.out.println("\n========================== sort(false) method ==========================\n\t sort list in ascending order");
    list.sort(false);
    list.print();


    System.out.println("\n========================== free() method ==========================\n\t free list");
    list.free();
    list.print();
  }
}
