package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;

























public class StringTransformer
  implements PropertyTransformer<String>
{
  public static final StringTransformer SHARED_INSTANCE = new StringTransformer();













  
  public String transform(String value, Field field) throws TransformationException {
    return value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\StringTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
