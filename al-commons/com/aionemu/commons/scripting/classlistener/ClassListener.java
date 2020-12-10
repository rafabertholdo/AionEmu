package com.aionemu.commons.scripting.classlistener;

public interface ClassListener {
  void postLoad(Class<?>[] paramArrayOfClass);

  void preUnload(Class<?>[] paramArrayOfClass);
}
