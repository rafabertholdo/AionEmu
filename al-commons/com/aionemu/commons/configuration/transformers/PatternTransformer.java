package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;
import java.util.regex.Pattern;


























public class PatternTransformer
  implements PropertyTransformer
{
  public static final PatternTransformer SHARED_INSTANCE = new PatternTransformer();














  
  public Pattern transform(String value, Field field) throws TransformationException {
    try {
      return Pattern.compile(value);
    }
    catch (Exception e) {
      
      throw new TransformationException("Not valid RegExp: " + value, e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\PatternTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
