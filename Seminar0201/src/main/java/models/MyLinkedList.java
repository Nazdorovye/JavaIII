package models;

public class MyLinkedList<T> {
  private long elmCounter = 0;
  private MyNode<T> first = null;
  private MyNode<T> last = null;

  /**
   * Initializes empty
   */
  public MyLinkedList() {}

  /**
   * 
   * @return {@code true} if list is empty, {@code false} if not
   */
  public boolean isEmpty() { return first == null; }


  /**
   * Tries to create new element to determine if there is enough memory in the sandbox
   * 
   * @return {@code true} if another element can be created, {@code false} if not
   */
  public boolean isFull() {
    if (elmCounter < 0) return true; // on long overflow

    try {
      MyNode<T> temp = new MyNode<>();
      return true;
    } catch (OutOfMemoryError e) {
      return false;
    }
  }


  /**
   * 
   * @param useCounter - {@code true } if to use counter variable, 
   * {@code false} if to count elements iteratively
   * @return element count in the list
   */
  public long elementCount(boolean useCounter) {
    if (useCounter) return elmCounter;
    
    long i = 0;
    for (MyNode<T> cur = first; cur != null; i++, cur = cur.next);

    return i;
  }


  /**
   * Appends new element to the list end
   * 
   * @param val - element to be added
   * @return {@code true} if element was added, {@code false} if not
   */
  public boolean addLast(T val) {
    if (isFull() || elmCounter < 0) return false;

    final MyNode<T> tempLast = last;
    last = new MyNode<>(val);

    if (tempLast == null) {
      first = last;
    } else {
      tempLast.next = last;
      last.prev = tempLast;
    }

    elmCounter++;
    return true;
  }


  /**
   * Adds new element at index, beginning from first element
   * 
   * @param val - element to be added
   * @param idx - index at which to add element
   * @return {@code true} if element was added, {@code false} if not
   */
  public boolean addAtIdx(T val, long idx) {
    if (isFull() || idx < 0 || idx < elmCounter - 1) return false;

    MyNode<T> cur = nodeAtIdx(idx);

    if (cur == null) return false;

    MyNode<T> prev = cur.prev;
    if (prev != null) {
      prev.next = new MyNode<>(val);
      prev.next.next = cur;
    } else {
      first = new MyNode<>(val);
      first.next = cur;
    }

    elmCounter++;
    return true;
  }

  public long indexOf(T val) {
    long result = 0;

    for (MyNode<T> n = first; n != null; n = n.next) {
      if (val.equals(n.value)) {
          return result;
      }

      result++;
    }

    return -1;
  }

  public MyNode<T> nodeOf(T val) {
    for (MyNode<T> cur = first; cur != null; cur = cur.next) {
      if (cur.value.equals(val)) return cur;
    }

    return null;
  }

  public MyNode<T> nodeAtIdx(long idx) {
    if (idx < 0 || idx < elmCounter - 1) return null;

    MyNode<T> result = first;
    for (long i = 0; i != idx; i++, result = result.next);

    return result;
  }

  public boolean remove(T val) {
    MyNode<T> cur = nodeOf(val);
    
    if (cur == null) return false;
       
    if (cur.prev != null) {
      cur.prev.next = cur.next;
    } else {
      first = cur.next;
    }

    if (cur.next != null) {
      cur.next.prev = cur.prev;
    } else {
      last = cur.prev;
    }
        
    cur.value = null;
    cur.next = null;

    return true;
  }

  public T nextOf(T val) {
    MyNode<T> cur = nodeOf(val);

    if (cur == null || cur.next == null) return null;

    return cur.next.value;
  }


}
