package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;























public class CharTransformer
  implements PropertyTransformer<Character>
{
  public static final CharTransformer SHARED_INSTANCE = new CharTransformer();














  
  public Character transform(String value, Field field) throws TransformationException {
    try {
      char[] chars = value.toCharArray();
      if (chars.length > 1)
      {
        throw new TransformationException("To many characters in the value");
      }
      
      return Character.valueOf(chars[0]);
    }
    catch (Exception e) {
      
      throw new TransformationException(e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\CharTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
