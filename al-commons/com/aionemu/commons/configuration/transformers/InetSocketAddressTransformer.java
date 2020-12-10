package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;





























public class InetSocketAddressTransformer
  implements PropertyTransformer<InetSocketAddress>
{
  public static final InetSocketAddressTransformer SHARED_INSTANCE = new InetSocketAddressTransformer();













  
  public InetSocketAddress transform(String value, Field field) throws TransformationException {
    String[] parts = value.split(":");
    
    if (parts.length != 2)
    {
      throw new TransformationException("Can't transform property, must be in format \"address:port\"");
    }

    
    try {
      if ("*".equals(parts[0]))
      {
        return new InetSocketAddress(Integer.parseInt(parts[1]));
      }

      
      InetAddress address = InetAddress.getByName(parts[0]);
      int port = Integer.parseInt(parts[1]);
      return new InetSocketAddress(address, port);
    
    }
    catch (Exception e) {
      
      throw new TransformationException(e);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\InetSocketAddressTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
