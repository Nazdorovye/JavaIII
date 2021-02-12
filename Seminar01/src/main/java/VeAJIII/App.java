package VeAJIII;

import java.io.File;
import java.util.Scanner;

public class App {
  public static void main( String[] args ) {
    System.out.println( "12.02.21 Seminar01" );

    MyList list = new MyList();

    File f = new File(App.class.getResource("numbers.txt").getFile());
    Scanner scn;

    try { 
      scn = new Scanner(f);

      while (scn.hasNextLine()) {
        if (!scn.hasNextInt()) {
          scn.nextLine();
          continue;
        }

        list.add(scn.nextInt());
      }

      scn.close();

    } catch (Exception e) { 
      e.printStackTrace();
      System.exit(e.hashCode());
    }
    list.print();


    System.out.println("\n========================== add() method ==========================\n\t appending 150 random numbers [-20..20] to the list");

    for (int i = 0; i < 150; i++) {
      list.add((int)(Math.random()*(41)-20));
    }
    list.print();


    System.out.println("\n========================== setAtIdx(), atIdx() methods ==========================\n\t set random numbers at 10 random indices\n");

    int idx;
    int old;
    for (int i = 0; i < 10; i++) {
      idx = (int)(Math.random()*(list.elmCount() + 1));
      old = list.atIdx(idx);

      list.setAtIdx(idx, (int)(Math.random()*(41)-20));
      System.out.printf("list[%d] = %d => %d\n", idx, old, list.atIdx(idx));
    }
    list.print();


    int elm;
    System.out.println("\n========================== seekElement() method ==========================\n\t search for 20 random elements\n");
    for (int i = 0; i < 20; i++) {
      elm = (int)(Math.random()*(41)-20);

      try {
        idx = list.seekElement(elm);
        System.out.printf("elm = %d <= at list[%d]\n", elm, idx);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    int elm2;
    System.out.println("\n========================== getNextAfter() method ==========================\n\t search for 20 random elements, and output next after\n");
    for (int i = 0; i < 20; i++) {
      elm = (int)(Math.random()*(41)-20);

      try {
        elm2 = list.getNextAfter(elm);
        System.out.printf("next after elm = %d is %d\n", elm, elm2);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }


    System.out.println("\n========================== delAtIdx() method ==========================\n\t delete elements at 100 random indices\n");
    for (int i = 0; i < 100; i++) {
      idx = (int)(Math.random()*(list.elmCount() + 1));
      System.out.printf("del list[%d] = %d\n", idx, list.atIdx(idx));

      list.delAtIdx(idx);
    }
    list.print();


    System.out.println("\n========================== sort(true) method ==========================\n\t sort list in descending order");
    list.sort(true);
    list.print();


    System.out.println("\n========================== sort(false) method ==========================\n\t sort list in ascending order");
    list.sort(false);
    list.print();
  }
}
