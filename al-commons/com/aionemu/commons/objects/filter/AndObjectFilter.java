package com.aionemu.commons.objects.filter;

public class AndObjectFilter<T> implements ObjectFilter<T> {
  private ObjectFilter<? super T>[] filters;

  public AndObjectFilter(ObjectFilter<? super T>... filters) {
    this.filters = filters;
  }

  public boolean acceptObject(T object) {
    for (ObjectFilter<? super T> filter : this.filters) {

      if (filter != null && !filter.acceptObject(object))
        return false;
    }
    return true;
  }
}
