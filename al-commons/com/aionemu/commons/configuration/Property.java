package com.aionemu.commons.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
  public static final String DEFAULT_VALUE = "DO_NOT_OVERWRITE_INITIALIAZION_VALUE";
  
  String key();
  
  Class<? extends PropertyTransformer> propertyTransformer() default PropertyTransformer.class;
  
  String defaultValue() default "DO_NOT_OVERWRITE_INITIALIAZION_VALUE";
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */