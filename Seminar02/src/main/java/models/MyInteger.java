package models;

public class MyInteger extends Number {
  public int value;

  public MyInteger(int value) {
    this.value = value;
  }

  public float floatValue() { return (float)value; }
  public long longValue() { return (long)value; }
  public int intValue() { return (int)value; }
  public double doubleValue() { return (double)value; }

  private static final long serialVersionUID = 10L;
}
