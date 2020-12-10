package com.aionemu.commons.utils;

import java.util.Iterator;
import java.util.Set;
import javolution.util.FastCollection;
import javolution.util.FastMap;

public class AEFastSet<E> extends AEFastCollection<E> implements Set<E> {
  private static final Object NULL = new Object();

  private final FastMap<E, Object> map;

  public AEFastSet() {
    this.map = new FastMap();
  }

  public AEFastSet(int capacity) {
    this.map = new FastMap(capacity);
  }

  public AEFastSet(Set<? extends E> elements) {
    this.map = new FastMap(elements.size());

    addAll(elements);
  }

  public boolean isShared() {
    return this.map.isShared();
  }

  public FastCollection.Record head() {
    return (FastCollection.Record) this.map.head();
  }

  public FastCollection.Record tail() {
    return (FastCollection.Record) this.map.tail();
  }

  public E valueOf(FastCollection.Record record) {
    return (E) ((FastMap.Entry) record).getKey();
  }

  public void delete(FastCollection.Record record) {
    this.map.remove(((FastMap.Entry) record).getKey());
  }

  public void delete(FastCollection.Record record, E value) {
    this.map.remove(value);
  }

  public boolean add(E value) {
    return (this.map.put(value, NULL) == null);
  }

  public void clear() {
    this.map.clear();
  }

  public boolean contains(Object o) {
    return this.map.containsKey(o);
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public Iterator<E> iterator() {
    return this.map.keySet().iterator();
  }

  public boolean remove(Object o) {
    return (this.map.remove(o) != null);
  }

  public int size() {
    return this.map.size();
  }

  public String toString() {
    return super.toString() + "-" + this.map.keySet().toString();
  }
}
