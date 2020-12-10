package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;




























public class BooleanTransformer
  implements PropertyTransformer<Boolean>
{
  public static final BooleanTransformer SHARED_INSTANCE = new BooleanTransformer();
















  
  public Boolean transform(String value, Field field) throws TransformationException {
    if ("true".equalsIgnoreCase(value) || "1".equals(value))
    {
      return Boolean.valueOf(true);
    }
    if ("false".equalsIgnoreCase(value) || "0".equals(value))
    {
      return Boolean.valueOf(false);
    }

    
    throw new TransformationException("Invalid boolean string: " + value);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\BooleanTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
