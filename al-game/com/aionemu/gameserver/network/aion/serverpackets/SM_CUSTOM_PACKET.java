package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


























public class SM_CUSTOM_PACKET
  extends AionServerPacket
{
  public enum PacketElementType
  {
    D('d')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeD(buf, Integer.decode(value).intValue());
      }
    },
    H('h')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeH(buf, Integer.decode(value).intValue());
      }
    },
    C('c')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeC(buf, Integer.decode(value).intValue());
      }
    },
    F('f')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeF(buf, Float.valueOf(value).floatValue());
      }
    },
    DF('e')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeDF(buf, Double.valueOf(value).doubleValue());
      }
    },
    Q('q')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeQ(buf, Long.decode(value).longValue());
      }
    },
    S('s')
    {
      
      public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
      {
        packet.writeS(buf, value);
      }
    };

    
    private final char code;
    
    PacketElementType(char code) {
      this.code = code;
    }

    
    public static PacketElementType getByCode(char code) {
      for (PacketElementType type : values()) {
        if (type.code == code)
          return type; 
      }  return null;
    }



    
    public abstract void write(SM_CUSTOM_PACKET param1SM_CUSTOM_PACKET, ByteBuffer param1ByteBuffer, String param1String);
  }



  
  public static class PacketElement
  {
    private final SM_CUSTOM_PACKET.PacketElementType type;


    
    private final String value;


    
    public PacketElement(SM_CUSTOM_PACKET.PacketElementType type, String value) {
      this.type = type;
      this.value = value;
    }









    
    public void writeValue(SM_CUSTOM_PACKET packet, ByteBuffer buf) {
      this.type.write(packet, buf, this.value);
    }
  }
  
  private List<PacketElement> elements = new ArrayList<PacketElement>();


  
  public SM_CUSTOM_PACKET(int opcode) {
    setOpcode(opcode);
  }






  
  public void addElement(PacketElement packetElement) {
    this.elements.add(packetElement);
  }







  
  public void addElement(PacketElementType type, String value) {
    this.elements.add(new PacketElement(type, value));
  }



  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    for (PacketElement el : this.elements)
    {
      el.writeValue(this, buf);
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CUSTOM_PACKET.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
