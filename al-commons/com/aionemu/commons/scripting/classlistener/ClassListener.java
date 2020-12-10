package com.aionemu.commons.scripting.classlistener;

public interface ClassListener {
  void postLoad(Class<?>[] paramArrayOfClass);
  
  void preUnload(Class<?>[] paramArrayOfClass);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\classlistener\ClassListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
