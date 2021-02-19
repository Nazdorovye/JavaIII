package models;

public class MyNode<T> {
  public MyNode<T> prev = null;
  public MyNode<T> next = null;
  public T value = null;

  public MyNode() {}

  public MyNode(T value) {
    this.value = value;
  }
}
