package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;

























public class DoubleTransformer
  implements PropertyTransformer<Double>
{
  public static final DoubleTransformer SHARED_INSTANCE = new DoubleTransformer();














  
  public Double transform(String value, Field field) throws TransformationException {
    try {
      return Double.valueOf(Double.parseDouble(value));
    }
    catch (Exception e) {
      
      throw new TransformationException(e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\DoubleTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
