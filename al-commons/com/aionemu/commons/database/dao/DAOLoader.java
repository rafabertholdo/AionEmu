package com.aionemu.commons.database.dao;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
import com.aionemu.commons.utils.ClassUtils;
import java.lang.reflect.Modifier;

class DAOLoader extends DefaultClassListener implements ClassListener {
  public void postLoad(Class<?>[] classes) {
    for (Class<?> clazz : classes) {

      if (isValidDAO(clazz)) {

        try {

          DAOManager.registerDAO((Class) clazz);
        } catch (Exception e) {

          throw new Error("Can't register DAO class", e);
        }
      }
    }

    super.postLoad(classes);
  }

  public void preUnload(Class<?>[] classes) {
    super.postLoad(classes);

    for (Class<?> clazz : classes) {

      if (isValidDAO(clazz)) {

        try {

          DAOManager.unregisterDAO((Class) clazz);
        } catch (Exception e) {

          throw new Error("Can't unregister DAO class", e);
        }
      }
    }
  }

  public boolean isValidDAO(Class<?> clazz) {
    if (!ClassUtils.isSubclass(clazz, DAO.class)) {
      return false;
    }
    int modifiers = clazz.getModifiers();

    if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
      return false;
    }
    if (!Modifier.isPublic(modifiers)) {
      return false;
    }
    if (clazz.isAnnotationPresent((Class) DisabledDAO.class)) {
      return false;
    }
    return true;
  }
}
