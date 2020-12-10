package com.aionemu.commons.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConfigurableProcessor {
  private static final Logger log = Logger.getLogger(ConfigurableProcessor.class);

  public static void process(Object object, Properties... properties) {
    Class<?> clazz;
    if (object instanceof Class) {

      clazz = (Class) object;
      object = null;
    } else {

      clazz = object.getClass();
    }

    process(clazz, object, properties);
  }

  private static void process(Class clazz, Object obj, Properties[] props) {
    processFields(clazz, obj, props);

    if (obj == null) {
      for (Class<?> itf : clazz.getInterfaces()) {
        process(itf, obj, props);
      }
    }

    Class<Object> superClass = clazz.getSuperclass();
    if (superClass != null && superClass != Object.class) {
      process(superClass, obj, props);
    }
  }

  private static void processFields(Class clazz, Object obj, Properties[] props) {
    for (Field f : clazz.getDeclaredFields()) {

      if (!Modifier.isStatic(f.getModifiers()) || obj == null) {

        if (Modifier.isStatic(f.getModifiers()) || obj != null) {

          if (f.isAnnotationPresent((Class) Property.class)) {

            if (Modifier.isFinal(f.getModifiers())) {

              RuntimeException re = new RuntimeException(
                  "Attempt to proceed final field " + f.getName() + " of class " + clazz.getName());

              log.error(re);
              throw re;
            }

            processField(f, obj, props);
          }
        }
      }
    }
  }

  private static void processField(Field f, Object obj, Properties[] props) {
    boolean oldAccessible = f.isAccessible();
    f.setAccessible(true);

    try {
      Property property = f.<Property>getAnnotation(Property.class);
      if (!"DO_NOT_OVERWRITE_INITIALIAZION_VALUE".equals(property.defaultValue())
          || isKeyPresent(property.key(), props)) {
        f.set(obj, getFieldValue(f, props));
      } else if (log.isDebugEnabled()) {
        log.debug("Field " + f.getName() + " of class " + f.getDeclaringClass().getName() + " wasn't modified");
      }

    } catch (Exception e) {

      RuntimeException re = new RuntimeException(
          "Can't transform field " + f.getName() + " of class " + f.getDeclaringClass(), e);

      log.error(re);
      throw re;
    }
    f.setAccessible(oldAccessible);
  }

  private static Object getFieldValue(Field field, Properties[] props) throws TransformationException {
    Property property = field.<Property>getAnnotation(Property.class);
    String defaultValue = property.defaultValue();
    String key = property.key();
    String value = null;

    if (key.isEmpty()) {

      log.warn("Property " + field.getName() + " of class " + field.getDeclaringClass().getName() + " has empty key");

    } else {

      value = findPropertyByKey(key, props);
    }

    if (value == null) {

      value = defaultValue;
      log.warn("Using default value for field " + field.getName() + " of class " + field.getDeclaringClass().getName());
    }

    PropertyTransformer pt = PropertyTransformerFactory.newTransformer(field.getType(), property.propertyTransformer());

    return pt.transform(value, field);
  }

  private static String findPropertyByKey(String key, Properties[] props) {
    for (Properties p : props) {

      if (p.containsKey(key)) {
        return p.getProperty(key);
      }
    }

    return null;
  }

  private static boolean isKeyPresent(String key, Properties[] props) {
    return (findPropertyByKey(key, props) != null);
  }
}
