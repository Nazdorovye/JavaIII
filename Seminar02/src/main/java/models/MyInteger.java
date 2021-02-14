package models;

public class MyInteger extends Number implements Comparable<MyInteger> {
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

  /**
   * 
   * @param another
   * @return pos if this > another; neg if this < another; 0 if this == another
   */
  public int compareTo(MyInteger another) {
    return value - another.value;
  }

  /**
   * 
   */
  public String toString() {
    return Integer.toString(value);
  }

  public float floatValue() { return (float)value; }
  public long longValue() { return (long)value; }
  public int intValue() { return (int)value; }
  public double doubleValue() { return (double)value; }

  private static final long serialVersionUID = 10L;
}
