package com.aionemu.commons.configuration;

import java.lang.reflect.Field;

public interface PropertyTransformer<T> {
  T transform(String paramString, Field paramField) throws TransformationException;
}
