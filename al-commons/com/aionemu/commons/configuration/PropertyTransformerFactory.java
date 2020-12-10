package com.aionemu.commons.configuration;

import com.aionemu.commons.configuration.transformers.BooleanTransformer;
import com.aionemu.commons.configuration.transformers.ByteTransformer;
import com.aionemu.commons.configuration.transformers.CharTransformer;
import com.aionemu.commons.configuration.transformers.ClassTransformer;
import com.aionemu.commons.configuration.transformers.DoubleTransformer;
import com.aionemu.commons.configuration.transformers.EnumTransformer;
import com.aionemu.commons.configuration.transformers.FileTransformer;
import com.aionemu.commons.configuration.transformers.FloatTransformer;
import com.aionemu.commons.configuration.transformers.InetSocketAddressTransformer;
import com.aionemu.commons.configuration.transformers.IntegerTransformer;
import com.aionemu.commons.configuration.transformers.LongTransformer;
import com.aionemu.commons.configuration.transformers.PatternTransformer;
import com.aionemu.commons.configuration.transformers.ShortTransformer;
import com.aionemu.commons.configuration.transformers.StringTransformer;
import com.aionemu.commons.utils.ClassUtils;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;









































public class PropertyTransformerFactory
{
  public static PropertyTransformer newTransformer(Class<Boolean> clazzToTransform, Class<? extends PropertyTransformer> tc) throws TransformationException {
    if (tc == PropertyTransformer.class)
    {
      tc = null;
    }
    
    if (tc != null) {
      
      try {
        
        return tc.newInstance();
      }
      catch (Exception e) {
        
        throw new TransformationException("Can't instantiate property transfromer", e);
      } 
    }

    
    if (clazzToTransform == Boolean.class || clazzToTransform == boolean.class)
    {
      return (PropertyTransformer)BooleanTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Byte.class || clazzToTransform == byte.class)
    {
      return (PropertyTransformer)ByteTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Character.class || clazzToTransform == char.class)
    {
      return (PropertyTransformer)CharTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Double.class || clazzToTransform == double.class)
    {
      return (PropertyTransformer)DoubleTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Float.class || clazzToTransform == float.class)
    {
      return (PropertyTransformer)FloatTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Integer.class || clazzToTransform == int.class)
    {
      return (PropertyTransformer)IntegerTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Long.class || clazzToTransform == long.class)
    {
      return (PropertyTransformer)LongTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Short.class || clazzToTransform == short.class)
    {
      return (PropertyTransformer)ShortTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == String.class)
    {
      return (PropertyTransformer)StringTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform.isEnum())
    {
      return (PropertyTransformer)EnumTransformer.SHARED_INSTANCE;
    }





    
    if (clazzToTransform == File.class)
    {
      return (PropertyTransformer)FileTransformer.SHARED_INSTANCE;
    }
    if (ClassUtils.isSubclass(clazzToTransform, InetSocketAddress.class))
    {
      return (PropertyTransformer)InetSocketAddressTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Pattern.class)
    {
      return (PropertyTransformer)PatternTransformer.SHARED_INSTANCE;
    }
    if (clazzToTransform == Class.class)
    {
      return (PropertyTransformer)ClassTransformer.SHARED_INSTANCE;
    }

    
    throw new TransformationException("Transformer not found for class " + clazzToTransform.getName());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\PropertyTransformerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
