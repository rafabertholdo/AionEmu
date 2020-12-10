package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;























public class LongTransformer
  implements PropertyTransformer<Long>
{
  public static final LongTransformer SHARED_INSTANCE = new LongTransformer();














  
  public Long transform(String value, Field field) throws TransformationException {
    try {
      return Long.decode(value);
    }
    catch (Exception e) {
      
      throw new TransformationException(e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\LongTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
