package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;

public class FloatTransformer implements PropertyTransformer<Float> {
  public static final FloatTransformer SHARED_INSTANCE = new FloatTransformer();

  public Float transform(String value, Field field) throws TransformationException {
    try {
      return Float.valueOf(Float.parseFloat(value));
    } catch (Exception e) {

      throw new TransformationException(e);
    }
  }
}
