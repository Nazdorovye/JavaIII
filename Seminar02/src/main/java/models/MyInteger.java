package models;

public class MyInteger extends Number {
  public int value;

  /**
   * 
   * @param value
   */
  public MyInteger(int value) {
    this.value = value;
  }

  /**
   * 
   */
  public boolean equals(Object obj) {
    if (!obj.getClass().equals(MyInteger.class)) return false;
    MyInteger temp = MyInteger.class.cast(obj);

    if (temp.value != value) return false;

    return true;
  }

  public float floatValue() { return (float)value; }
  public long longValue() { return (long)value; }
  public int intValue() { return (int)value; }
  public double doubleValue() { return (double)value; }

  private static final long serialVersionUID = 10L;
}
