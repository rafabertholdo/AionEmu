package com.aionemu.gameserver.network;

import com.aionemu.commons.utils.Rnd;
import java.nio.ByteBuffer;

































public class Crypt
{
  public static final byte staticClientPacketCode = 85;
  public static final byte staticServerPacketCode = 80;
  private static byte[] staticKey = "nKO/WctQ0AVLbpzfBkS6NevDYT8ourG5CRlmdjyJ72aswx4EPq1UgZhFMXH?3iI9".getBytes();




  
  private byte[] clientPacketKey;




  
  private byte[] serverPacketKey;



  
  private boolean isEnabled;




  
  public final int enableKey() {
    if (this.clientPacketKey != null) {
      throw new KeyAlreadySetException();
    }
    
    int key = Rnd.nextInt();
    
    this.clientPacketKey = new byte[] { (byte)(key & 0xFF), (byte)(key >> 8 & 0xFF), (byte)(key >> 16 & 0xFF), (byte)(key >> 24 & 0xFF), -95, 108, 84, -121 };

    
    this.serverPacketKey = new byte[this.clientPacketKey.length];
    System.arraycopy(this.clientPacketKey, 0, this.serverPacketKey, 0, this.clientPacketKey.length);

    
    return (key ^ 0xCD92E451) + 1072876679;
  }







  
  public final boolean decrypt(ByteBuffer buf) {
    if (!this.isEnabled) {
      return false;
    }
    byte[] data = buf.array();
    int size = buf.remaining();

    
    int arrayIndex = buf.arrayOffset() + buf.position();

    
    int prev = data[arrayIndex];

    
    data[arrayIndex++] = (byte)(data[arrayIndex++] ^ this.clientPacketKey[0] & 0xFF);

    
    for (int i = 1; i < size; i++, arrayIndex++) {
      
      int curr = data[arrayIndex] & 0xFF;
      data[arrayIndex] = (byte)(data[arrayIndex] ^ staticKey[i & 0x3F] & 0xFF ^ this.clientPacketKey[i & 0x7] & 0xFF ^ prev);
      prev = curr;
    } 

    
    long oldKey = (this.clientPacketKey[0] & 0xFFL) << 0L | (this.clientPacketKey[1] & 0xFFL) << 8L | (this.clientPacketKey[2] & 0xFFL) << 16L | (this.clientPacketKey[3] & 0xFFL) << 24L | (this.clientPacketKey[4] & 0xFFL) << 32L | (this.clientPacketKey[5] & 0xFFL) << 40L | (this.clientPacketKey[6] & 0xFFL) << 48L | (this.clientPacketKey[7] & 0xFFL) << 56L;




    
    oldKey += size;

    
    this.clientPacketKey[0] = (byte)(int)(oldKey >> 0L & 0xFFL);
    this.clientPacketKey[1] = (byte)(int)(oldKey >> 8L & 0xFFL);
    this.clientPacketKey[2] = (byte)(int)(oldKey >> 16L & 0xFFL);
    this.clientPacketKey[3] = (byte)(int)(oldKey >> 24L & 0xFFL);
    this.clientPacketKey[4] = (byte)(int)(oldKey >> 32L & 0xFFL);
    this.clientPacketKey[5] = (byte)(int)(oldKey >> 40L & 0xFFL);
    this.clientPacketKey[6] = (byte)(int)(oldKey >> 48L & 0xFFL);
    this.clientPacketKey[7] = (byte)(int)(oldKey >> 56L & 0xFFL);
    
    return validateClientPacket(buf);
  }







  
  private final boolean validateClientPacket(ByteBuffer buf) {
    return (buf.get(0) == (buf.get(2) ^ 0xFFFFFFFF) && buf.get(1) == 85);
  }






  
  public final void encrypt(ByteBuffer buf) {
    if (!this.isEnabled) {

      
      this.isEnabled = true;
      
      return;
    } 
    byte[] data = buf.array();
    int size = buf.remaining();

    
    int arrayIndex = buf.arrayOffset() + buf.position();

    
    data[arrayIndex] = (byte)(data[arrayIndex] ^ this.serverPacketKey[0] & 0xFF);

    
    int prev = data[arrayIndex++];

    
    for (int i = 1; i < size; i++, arrayIndex++) {
      
      data[arrayIndex] = (byte)(data[arrayIndex] ^ staticKey[i & 0x3F] & 0xFF ^ this.serverPacketKey[i & 0x7] & 0xFF ^ prev);
      prev = data[arrayIndex];
    } 

    
    long oldKey = (this.serverPacketKey[0] & 0xFFL) << 0L | (this.serverPacketKey[1] & 0xFFL) << 8L | (this.serverPacketKey[2] & 0xFFL) << 16L | (this.serverPacketKey[3] & 0xFFL) << 24L | (this.serverPacketKey[4] & 0xFFL) << 32L | (this.serverPacketKey[5] & 0xFFL) << 40L | (this.serverPacketKey[6] & 0xFFL) << 48L | (this.serverPacketKey[7] & 0xFFL) << 56L;




    
    oldKey += size;

    
    this.serverPacketKey[0] = (byte)(int)(oldKey >> 0L & 0xFFL);
    this.serverPacketKey[1] = (byte)(int)(oldKey >> 8L & 0xFFL);
    this.serverPacketKey[2] = (byte)(int)(oldKey >> 16L & 0xFFL);
    this.serverPacketKey[3] = (byte)(int)(oldKey >> 24L & 0xFFL);
    this.serverPacketKey[4] = (byte)(int)(oldKey >> 32L & 0xFFL);
    this.serverPacketKey[5] = (byte)(int)(oldKey >> 40L & 0xFFL);
    this.serverPacketKey[6] = (byte)(int)(oldKey >> 48L & 0xFFL);
    this.serverPacketKey[7] = (byte)(int)(oldKey >> 56L & 0xFFL);
  }







  
  public static final byte encodeOpcodec(int op) {
    return (byte)(op + 174 ^ 0xEE);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
