package models;

public class MyLinkedList<T> {
  private long elmCounter = 0;
  private MyNode<T> first = null;
  private MyNode<T> last = null;

  public MyLinkedList() {}


  /**
   * Checks if the list has no nodes.
   * 
   * @return {@code true} if list is empty, {@code false} if not
   */
  public boolean isEmpty() { return first == null || elmCounter == 0; }


  /**
   * Tries to create new element to determine if there is enough memory in the sandbox and if there
   *  are free positive indices left in item counter.
   * 
   * On return {@code true} prints the cause to the terminal.
   *
   * @return {@code false} if another node can be created, {@code true} if not
   * @apiNote unused variable warning suppressed, as this behavior is intended
   */
  @SuppressWarnings("unused")
  public boolean isFull() {
    if (elmCounter < 0) {
      System.out.println("\n\nElement counter overflow!\n\n");
      return true;
    }

    try {
      MyNode<T> temp = new MyNode<>();
      return false;
    } catch (OutOfMemoryError e) {
      System.out.println("\n\nOut of memory!\n\n");
      return true;
    }
  }


  /**
   * Returns node count currently linked to the list.
   * 
   * @param useCounter - {@code true } if to use counter variable, 
   * {@code false} if to count elements iteratively
   * @return element count in the list
   */
  public long nodeCount(boolean useCounter) {
    if (useCounter) return elmCounter;
    
    long i = 0;
    for (MyNode<T> cur = first; cur != null; i++, cur = cur.next);

    return i;
  }

  /**
   * Returns node object at given index, counting from the {@code first == 0}. Index must be in 
   *  list node count bounds. 
   * 
   * @param idx - index at which node must be returned
   * @return {@code MyNode} object
   * @throws Exception
   */
  public MyNode<T> nodeAtIdx(long idx) throws Exception {
    if (idx < 0 || idx >= elmCounter) {
      throw new Exception(String.format(
        "\n\nIndex %d must be between [0..elmCounter), in this case [0..%d)!\n\n", 
        idx, elmCounter)
      );
    }

    MyNode<T> result = first;
    for (long i = 0; i != idx; i++, result = result.next);

    return result;
  }


  /**
   * Searches for the first node occurence with given {@code value}.
   * 
   * @param value - value to search for
   * @return {@code  MyNode} object
   * @throws Exception
   */
  public MyNode<T> nodeOf(T value) throws Exception {

    // branches to anticipate NullReferenceException on equals() method
    if (value == null) {
      for (MyNode<T> cur = first; cur != null; cur = cur.next) {
        if (cur.value == value) return cur;
      }
    } else {
      for (MyNode<T> cur = first; cur != null; cur = cur.next) {
        if (cur.value.equals(value)) return cur;
      }
    }    

    throw new Exception(String.format("\n\nElement %s was not found!\n\n", value.toString()));
  }


  /**
   * Appends new element node to the end of the list
   * 
   * @param value - element to be added
   * @return {@code true} if element was added, {@code false} if not
   */
  public boolean addLast(T value) {
    if (isFull()) return false;

    final MyNode<T> tempLast = last;
    last = new MyNode<>(value);

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
   * Adds new element at specified index, counting from the {@code first == 0}. Index must be in 
   *  list node count bounds.
   * 
   * @param value - element to be added
   * @param idx - index at which to add element
   * @return {@code true} if element was added, {@code false} if not
   */
  public boolean addAtIdx(T value, long idx) {
    if (isFull()) return false;

    MyNode<T> cur;
    try {
      cur = nodeAtIdx(idx); // also checks if idx in bounds
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }

    final MyNode<T> tempCur = cur;
    cur = new MyNode<>(value);

    if (tempCur == null) {
      first = last = cur;
    } else {
      if (tempCur.prev != null) {
        tempCur.prev.next = cur;
        cur.prev = tempCur.prev;
      }

      tempCur.prev = cur;
      cur.next = tempCur;
    }

    elmCounter++;
    return true;
  }


  /**
   * Removes the first node occurence with given {@code value} from the list.
   * 
   * @param value
   * @return {@code true} if node was found and removed, {@code false} if not.
   */
  public boolean remove(T value) {
    MyNode<T> cur;

    try {
      cur = nodeOf(value);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
       
    if (cur.prev == null) {
      first = cur.next;
      first.prev = null;
    } else {
      cur.prev.next = cur.next;
    }

    if (cur.next == null) {
      last = cur.prev;
      last.next = null;
    } else {
      cur.next.prev = cur.prev;
    }
        
    cur.value = null;
    cur.next = null;
    cur.prev = null;

    return true;
  }

  /**
   * Returns the {@code value} linked next to the specified first found node {@code value}.
   * 
   * @param value - the value, after which to return next one
   * @return {@code T} object or {@code null} if specified value was in the last node or not found
   */
  public T nextOf(T value) {    
    try {
      return nodeOf(value).next.value;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }


}
