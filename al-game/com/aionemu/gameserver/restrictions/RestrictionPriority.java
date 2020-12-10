package com.aionemu.gameserver.restrictions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface RestrictionPriority {
  public static final double DEFAULT_PRIORITY = 0.0D;

  double value() default 0.0D;
}
