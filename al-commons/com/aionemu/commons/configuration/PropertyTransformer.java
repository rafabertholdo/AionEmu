package com.aionemu.commons.configuration;

import java.lang.reflect.Field;

public interface PropertyTransformer<T> {
  T transform(String paramString, Field paramField) throws TransformationException;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\PropertyTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
