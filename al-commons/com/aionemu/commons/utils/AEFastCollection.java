package com.aionemu.commons.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import javolution.util.FastCollection;

public abstract class AEFastCollection<E> implements Collection<E> {
  public abstract FastCollection.Record head();

  public abstract FastCollection.Record tail();

  public abstract E valueOf(FastCollection.Record paramRecord);

  public abstract void delete(FastCollection.Record paramRecord);

  public abstract void delete(FastCollection.Record paramRecord, E paramE);

  public final E getFirst() {
    FastCollection.Record first = head().getNext();
    if (first == tail()) {
      return null;
    }
    return valueOf(first);
  }

  public final E getLast() {
    FastCollection.Record last = tail().getPrevious();
    if (last == head()) {
      return null;
    }
    return valueOf(last);
  }

  public final E removeFirst() {
    FastCollection.Record first = head().getNext();
    if (first == tail()) {
      return null;
    }
    E value = valueOf(first);
    delete(first, value);
    return value;
  }

  public final E removeLast() {
    FastCollection.Record last = tail().getPrevious();
    if (last == head()) {
      return null;
    }
    E value = valueOf(last);
    delete(last, value);
    return value;
  }

  public boolean addAll(E[] c) {
    boolean modified = false;

    for (E e : c) {
      if (add(e))
        modified = true;
    }
    return modified;
  }

  public boolean addAll(Collection<? extends E> c) {
    return addAll(c);
  }

  public boolean addAll(Iterable<? extends E> c) {
    if (c instanceof java.util.RandomAccess && c instanceof List) {
      return addAll((List<? extends E>) c);
    }
    if (c instanceof FastCollection) {
      return addAll((FastCollection<? extends E>) c);
    }
    if (c instanceof AEFastCollection) {
      return addAll((AEFastCollection<? extends E>) c);
    }
    boolean modified = false;

    for (E e : c) {
      if (add(e))
        modified = true;
    }
    return modified;
  }

  private boolean addAll(AEFastCollection<? extends E> c) {
    boolean modified = false;

    for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
      if (add(c.valueOf(r)))
        modified = true;
    }
    return modified;
  }

  private boolean addAll(FastCollection<? extends E> c) {
    boolean modified = false;

    for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
      if (add((E) c.valueOf(r)))
        modified = true;
    }
    return modified;
  }

  private boolean addAll(List<? extends E> c) {
    boolean modified = false;

    for (int i = 0, size = c.size(); i < size;) {
      if (add(c.get(i++)))
        modified = true;
    }
    return modified;
  }

  public boolean containsAll(Object[] c) {
    for (Object obj : c) {
      if (!contains(obj))
        return false;
    }
    return true;
  }

  public boolean containsAll(Collection<?> c) {
    return containsAll(c);
  }

  public boolean containsAll(Iterable<?> c) {
    if (c instanceof java.util.RandomAccess && c instanceof List) {
      return containsAll((List) c);
    }
    if (c instanceof FastCollection) {
      return containsAll((FastCollection) c);
    }
    if (c instanceof AEFastCollection) {
      return containsAll((AEFastCollection) c);
    }
    for (Object obj : c) {
      if (!contains(obj))
        return false;
    }
    return true;
  }

  private boolean containsAll(AEFastCollection<?> c) {
    for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
      if (!contains(c.valueOf(r)))
        return false;
    }
    return true;
  }

  private boolean containsAll(FastCollection<?> c) {
    for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
      if (!contains(c.valueOf(r)))
        return false;
    }
    return true;
  }

  private boolean containsAll(List<?> c) {
    for (int i = 0, size = c.size(); i < size;) {
      if (!contains(c.get(i++)))
        return false;
    }
    return true;
  }

  public boolean removeAll(Collection<?> c) {
    boolean modified = false;

    for (FastCollection.Record head = head(), r = tail().getPrevious(); r != head; r = previous) {

      FastCollection.Record previous = r.getPrevious();
      if (c.contains(valueOf(r))) {

        delete(r);
        modified = true;
      }
    }

    return modified;
  }

  public boolean retainAll(Collection<?> c) {
    boolean modified = false;

    for (FastCollection.Record head = head(), r = tail().getPrevious(); r != head; r = previous) {

      FastCollection.Record previous = r.getPrevious();
      if (!c.contains(valueOf(r))) {

        delete(r);
        modified = true;
      }
    }

    return modified;
  }

  public Object[] toArray() {
    return toArray(new Object[size()]);
  }

  public <T> T[] toArray(T[] array) {
    int size = size();

    if (array.length != size) {
      array = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
    }
    if (size == 0 && array.length == 0) {
      return array;
    }
    int i = 0;
    for (FastCollection.Record r = head(), end = tail(); (r = r.getNext()) != end;) {
      array[i++] = (T) valueOf(r);
    }
    return array;
  }
}
