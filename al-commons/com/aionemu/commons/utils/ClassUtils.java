package com.aionemu.commons.utils;

public class ClassUtils {
  public static boolean isSubclass(Class<?> a, Class<?> b) {
    if (a == b) {
      return true;
    }
    if (a == null || b == null) {
      return false;
    }
    for (Class<?> x = a; x != null; x = x.getSuperclass()) {

      if (x == b) {
        return true;
      }
      if (b.isInterface()) {

        Class[] interfaces = x.getInterfaces();
        for (Class<?> anInterface : interfaces) {

          if (isSubclass(anInterface, b)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static boolean isPackageMember(Class<?> clazz, String packageName) {
    return isPackageMember(clazz.getName(), packageName);
  }

  public static boolean isPackageMember(String className, String packageName) {
    if (!className.contains(".")) {
      return (packageName == null || packageName.isEmpty());
    }

    String classPackage = className.substring(0, className.lastIndexOf('.'));
    return packageName.equals(classPackage);
  }
}
