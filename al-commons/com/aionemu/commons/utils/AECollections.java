package com.aionemu.commons.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class AECollections {
  private static final Object[] EMPTY_ARRAY = new Object[0];

  private static final class EmptyListIterator implements ListIterator<Object> {
    private static final ListIterator<Object> INSTANCE = new EmptyListIterator();

    public boolean hasNext() {
      return false;
    }

    public Object next() {
      throw new UnsupportedOperationException();
    }

    public boolean hasPrevious() {
      return false;
    }

    public Object previous() {
      throw new UnsupportedOperationException();
    }

    public int nextIndex() {
      return 0;
    }

    public int previousIndex() {
      return -1;
    }

    public void add(Object obj) {
      throw new UnsupportedOperationException();
    }

    public void set(Object obj) {
      throw new UnsupportedOperationException();
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private static class EmptyCollection implements Collection<Object> {
    private EmptyCollection() {
    }

    private static final Collection<Object> INSTANCE = new EmptyCollection();

    public boolean add(Object e) {
      throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends Object> c) {
      throw new UnsupportedOperationException();
    }

    public void clear() {
    }

    public boolean contains(Object o) {
      return false;
    }

    public boolean containsAll(Collection<?> c) {
      return false;
    }

    public boolean isEmpty() {
      return true;
    }

    public Iterator<Object> iterator() {
      return (Iterator) AECollections.emptyListIterator();
    }

    public boolean remove(Object o) {
      return false;
    }

    public boolean removeAll(Collection<?> c) {
      return false;
    }

    public boolean retainAll(Collection<?> c) {
      return false;
    }

    public int size() {
      return 0;
    }

    public Object[] toArray() {
      return AECollections.EMPTY_ARRAY;
    }

    public <T> T[] toArray(T[] a) {
      if (a.length != 0) {
        a = (T[]) Array.newInstance(a.getClass().getComponentType(), 0);
      }
      return a;
    }

    public final String toString() {
      return "[]";
    }
  }

  private static final class EmptySet extends EmptyCollection implements Set<Object> {
    private static final Set<Object> INSTANCE = new EmptySet();
  }

  private static final class EmptyMap implements Map<Object, Object> {
    private static final Map<Object, Object> INSTANCE = new EmptyMap();

    public void clear() {
    }

    public boolean containsKey(Object key) {
      return false;
    }

    public boolean containsValue(Object value) {
      return false;
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
      return AECollections.emptySet();
    }

    public Object get(Object key) {
      return null;
    }

    public boolean isEmpty() {
      return true;
    }

    public Set<Object> keySet() {
      return AECollections.emptySet();
    }

    public Object put(Object key, Object value) {
      throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends Object, ? extends Object> m) {
      throw new UnsupportedOperationException();
    }

    public Object remove(Object key) {
      return null;
    }

    public int size() {
      return 0;
    }

    public Collection<Object> values() {
      return (Collection) AECollections.emptyCollection();
    }

    public String toString() {
      return "{}";
    }
  }

  private static <T> ListIterator<T> emptyListIterator() {
    return (ListIterator) EmptyListIterator.INSTANCE;
  }

  private static <T> Collection<T> emptyCollection() {
    return (Collection) EmptyCollection.INSTANCE;
  }

  public static <T> Set<T> emptySet() {
    return (Set) EmptySet.INSTANCE;
  }

  public static <K, V> Map<K, V> emptyMap() {
    return (Map) EmptyMap.INSTANCE;
  }
}
