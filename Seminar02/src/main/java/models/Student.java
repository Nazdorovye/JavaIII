package models;

/**
 * No gender checks as required in the task - Latvia is not the only country in the world
 */
public class Student {
  private static long id_counter = 0;

  public enum Gender {
    DEFAULT, MALE, FEMALE;
  }

  public final long id;
  public Gender gender;
  private String name;
  private String surname;
  private byte[] grades;


  /**
   * 
   * @throws Exception
   */
  public Student() throws Exception {
    try {
      verifyId();
      this.id = id_counter++;
      
      setName("Name");
      setSurname("surname");
      gender = Gender.DEFAULT;
    } catch (Exception e) {
      throw new Exception(e + "; new Student instance was not created!");
    }
  }

  /**
   * 
   * @param name
   * @param surname
   * @param gender
   * @throws Exception
   */
  public Student(String name, String surname, Gender gender) throws Exception {
    try {
      verifyId();
      this.id = id_counter++;
      
      setName(name);
      setSurname(surname);
      this.gender = gender;
    } catch (Exception e) {
      throw new Exception(e + "; new Student instance was not created!");
    }
  }

  /**
   * 
   */
  public float getMeanGrade() {
    byte result = 0;

    for (byte grade : grades) {
      result += grade;
    }

    return (float)result / (float)grades.length;
  }

  /**
   * To make sure long id_counter did not overflow
   * 
   * @throws IndexOutOfBoundsException
   */
  private void verifyId() throws IndexOutOfBoundsException {
    if (id_counter < 0) {
      throw new IndexOutOfBoundsException("Student count reached MAX_LONG");    
    }
  }

  /**
   * 
   */
  public String toString() {
    return 
      "\tId: " + id + 
      "\n\tName: " + name + 
      "\n\tSurname: " + surname + 
      "\n\tGender: " + gender.toString() +
      String.format("\n\tMean grade: %.1f", getMeanGrade()) +
      "\n";
  }

  /**
   * 
   */
  public boolean equals(Object obj) {
    if (!obj.getClass().equals(this.getClass())) return false;
    Student temp = Student.class.cast(obj);

    if(temp.id != id) return false;
    if(!temp.name.equals(name)) return false;
    if(!temp.surname.equals(surname)) return false;

    return true;
  }

  /* ======== GETTERS / SETTERS =========================================================== */
  /**
   * 
   * @param name - accepts only Unicode "letter" characters
   * @throws IllegalArgumentException
   */
  public void setName(String name) throws IllegalArgumentException {
    if (!name.matches("[\\p{L}]")) {
      throw new IllegalArgumentException(name+ " has invalid symbols");
    }

    this.name = name;
  }

  /**
   * 
   * @return name
   */
  public String getName() { return name; }

  /**
   * 
   * @param surname - accepts only Unicode "letter" characters
   * @throws IllegalArgumentException
   */
  public void setSurname(String surname) throws IllegalArgumentException {
    try { 
      setName(surname); 
    } catch (Exception e) { 
      throw e; 
    }

    this.surname = surname;
  }

  /**
   * 
   * @return surname
   */
  public String getSurname() { return surname; }

  /**
   * 
   * @return grade array
   */
  public byte[] getGrades() { return grades; }
}
