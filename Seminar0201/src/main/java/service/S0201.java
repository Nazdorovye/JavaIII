package service;

import models.MyLinkedList;
import models.Student;
import models.MyLinkedList.SortOrder;
import models.Student.Gender;

public class S0201 {
  static void addStud(MyLinkedList<Student> where, String name, String surname, Gender gender) {
    try {
      where.addLast(new Student(name, surname, gender));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  static void addStud(MyLinkedList<Student> where, long idx, String name, String surname, Gender gender) {
    try {
      where.addAtIdx(new Student(name, surname, gender), idx);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main( String[] args ) {
    MyLinkedList<Student> studs = new MyLinkedList<>();

    addStud(studs, "Gunnar", "Gunnarson", Gender.MALE);
    addStud(studs, "Gunnar", "Gunnarsonson", Gender.MALE);
    addStud(studs, "Elsa", "Gvedhir", Gender.FEMALE);
    addStud(studs, "Rhinda", "Grudgenbirh", Gender.DEFAULT);
    addStud(studs, "John", "Cotton Eye", Gender.DEFAULT);

    for (Student stud = studs.getFirst().value; stud != null; stud = studs.nextOf(stud)) {
      stud.getGrades().add((byte)(Math.random()*(10)+1));
      stud.getGrades().add((byte)(Math.random()*(10)+1));
      stud.getGrades().add((byte)(Math.random()*(10)+1));
      stud.getGrades().add((byte)(Math.random()*(10)+1));
      stud.getGrades().add((byte)(Math.random()*(10)+1));
      stud.getGrades().add((byte)(Math.random()*(10)+1));
    }
    System.out.print(studs.toString());




    addStud(studs, 0, "Idx", "Zero", Gender.DEFAULT);
    try { studs.nodeAtIdx(0).value.getGrades().add((byte)(Math.random()*(10)+1)); } catch (Exception e) {}

    addStud(studs, 5, "Idx", "Five", Gender.DEFAULT);
    try { studs.nodeAtIdx(5).value.getGrades().add((byte)(Math.random()*(10)+1)); } catch (Exception e) {}

    System.out.print(studs.toString());


    

    studs.mergeSort(SortOrder.DESCENDING);
    System.out.print(studs.toString());
  }
}
