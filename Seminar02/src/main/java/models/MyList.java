package models;

import java.lang.reflect.Array;

public class MyList<T> {
  private final Class<T> elm_class;
  private static int elm_counter = 0;
  private T[] elements;
  private final int defaultLength;

  /**
   * Initializes with list length 0
   * 
   * @param element_class - elements' generic class
   * @apiNote suppressed unchecked cast warning
   */
  @SuppressWarnings("unchecked")
  public MyList(Class<T> element_class) {
    defaultLength = 0;
    elm_class = element_class;
    elements = (T[]) Array.newInstance(elm_class, defaultLength); // unchecked cast
  }

  /**
   * 
   * @param element_class - elements' generic class
   * @param listSize - list length
   * @throws IndexOutOfBoundsException - when listSize < 0
   * @apiNote suppressed unchecked cast warning
   */
  @SuppressWarnings("unchecked")
  public MyList(Class<T> element_class, int listSize) throws IndexOutOfBoundsException {
    if (listSize < 0) throw new IndexOutOfBoundsException(listSize);

    defaultLength = listSize;
    elm_class = element_class;
    elements = (T[]) Array.newInstance(elm_class, listSize); // unchecked cast
  }

  public boolean isFull() { return elm_counter == elements.length; }
  public boolean isEmpty() { return elm_counter == 0; }
  public long elmCount() { return elm_counter; }

  @SuppressWarnings("unchecked")
  private boolean widen() {
    long newLength = (long)(elements.length * ((elm_counter < 100) ? 2 : 1.5));

    if (newLength > Integer.MAX_VALUE) {
      int remaining = Integer.MAX_VALUE - elements.length;

      if (remaining <= 0) {
        return false;
      } else {
        newLength = elements.length + remaining;
      }
    } else if (newLength == 0) newLength = 1;

    T[] temp = (T[]) Array.newInstance(elm_class, (int)newLength);
    System.arraycopy(elements, 0, temp, 0, elements.length);

    elements = temp;    
    return true;
  }

  /**
   * Attempts to append element to the end of the list.
   * If list is full, attempts to widen it using private widen() method.
   *
   * @param elm - element to add
   * @return true on success.
   * @throws IndexOutOfBoundsException
   */
  public boolean add(T elm) throws IndexOutOfBoundsException {
    if (isFull() && !widen()) throw new IndexOutOfBoundsException("List reached MAX_INT length.");

    elements[elm_counter++] = elm;
    return true;
  }

  /**
   * Attempts to change element by index.
   * Index must be in current list bounds.
   * 
   * @param idx - index at which element will be changed
   * @param elm - element to add
   * @return true on success
   * @throws IndexOutOfBoundsException
   */
  public boolean setAtIdx(int idx, T elm) throws IndexOutOfBoundsException {
    if (idx > elm_counter || idx < 0) throw new IndexOutOfBoundsException(idx);

    elements[idx] = elm;
    return true;
  }

  /**
   * Attempts to delete element by index. Sweeps all elements over idx down.
   * Index must be in current list bounds.
   * 
   * @param idx - index at which elements will be deleted
   * @return true on success
   * @throws IndexOutOfBoundsException
   */
  public boolean delAtIdx(int idx) throws IndexOutOfBoundsException {
    if (idx >= elm_counter || idx < 0) throw new IndexOutOfBoundsException(idx);

    for (int i = idx; i < elm_counter;) {
      elements[i] = elements[++i];
    }

    elm_counter--;
    return true;
  }

  /**
   * Attempts to return element by index.
   * 
   * @param idx - index at which is required element
   * @return element
   * @throws IndexOutOfBoundsException
   */
  public T atIdx(int idx) throws IndexOutOfBoundsException {
    if (idx > elm_counter || idx < 0) throw new IndexOutOfBoundsException(idx);

    return elements[idx];
  }

  /**
   * Searches for element in the list.
   * 
   * @param elm - element to search
   * @return index if element found in the list
   * @throws IllegalArgumentException if element was not found
   */
  public int seekElement(T elm) throws IllegalArgumentException {
    for (int i = 0; i < elm_counter; i++) {
      if (elements[i].equals(elm)) 
        return i;
    }

    throw new IllegalArgumentException("elm = " + elm.toString() + " not found in the list");
  }

  /**
   * Searches for element in the list to return element right next after it.
   * 
   * @param elm - element to search
   * @return element right after element
   * @throws IllegalArgumentException if element was not found
   */
  public T getNextAfter(T elm) throws IllegalArgumentException {
    int idx;
    
    try {
      idx = seekElement(elm);
    } catch (IllegalArgumentException e) { throw e; }    

    if (idx < elm_counter - 1) {
      return elements[idx + 1];
    } else {
      throw new IllegalArgumentException("elm = " + elm + " is last in the list");
    }
  }

  /**
   * Sorts list in ascending or descending order ( Bubble sort algo )
   * 
   * @param descending - sets if to sort in descending order
   * @apiNote suppressed unchecked cast warning
   */
  @SuppressWarnings("unchecked")
  public void sort(boolean descending) {    
    if (Comparable.class.isAssignableFrom(elm_class)) {

      for (int i = 0; i < elm_counter; i++) {
        for (int j = i + 1; j < elm_counter; j++) {
          if (
            (
              (((Comparable<T>)elements[j]).compareTo((T)elements[i])) > 0 
              && descending
            ) || (
              (((Comparable<T>)elements[j]).compareTo((T)elements[i])) < 0 
              && !descending
            )) {

            T temp = elements[i];
            elements[i] = elements[j];
            elements[j] = temp;
          }
        }
      }

    } else {

      for (int i = 0; i < elm_counter; i++) {
        for (int j = i + 1; j < elm_counter; j++) {
          if (   (elements[j].hashCode() > elements[i].hashCode() && descending)
              || (elements[j].hashCode() < elements[i].hashCode() && !descending)) {
            T temp = elements[i];
            elements[i] = elements[j];
            elements[j] = temp;
          }
        }
      }

    }
  }

  /**
   * Prints list contents in terminal
   */
  public void print() {
    System.out.printf("\nMyList(elmCount = %d, allocLength = %d) {\n", 
        elm_counter, elements.length);

    for (int i = 0; i < elm_counter; i++) {
      System.out.print(elements[i].toString());
      if (i < elm_counter - 1) System.out.print(", ");
    }

    System.out.print("\n}\n");
  }

  /**
   * Empties list
   * @apiNote suppressed unchecked cast warning
   */
  @SuppressWarnings("unchecked")
  public void free() {
    elm_counter = 0;
    elements = (T[]) Array.newInstance(elm_class, defaultLength);
  }
}
