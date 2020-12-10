/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SM_CUSTOM_PACKET
/*     */   extends AionServerPacket
/*     */ {
/*     */   public enum PacketElementType
/*     */   {
/*  39 */     D('d')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  44 */         packet.writeD(buf, Integer.decode(value).intValue());
/*     */       }
/*     */     },
/*  47 */     H('h')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  52 */         packet.writeH(buf, Integer.decode(value).intValue());
/*     */       }
/*     */     },
/*  55 */     C('c')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  60 */         packet.writeC(buf, Integer.decode(value).intValue());
/*     */       }
/*     */     },
/*  63 */     F('f')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  68 */         packet.writeF(buf, Float.valueOf(value).floatValue());
/*     */       }
/*     */     },
/*  71 */     DF('e')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  76 */         packet.writeDF(buf, Double.valueOf(value).doubleValue());
/*     */       }
/*     */     },
/*  79 */     Q('q')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  84 */         packet.writeQ(buf, Long.decode(value).longValue());
/*     */       }
/*     */     },
/*  87 */     S('s')
/*     */     {
/*     */       
/*     */       public void write(SM_CUSTOM_PACKET packet, ByteBuffer buf, String value)
/*     */       {
/*  92 */         packet.writeS(buf, value);
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     private final char code;
/*     */     
/*     */     PacketElementType(char code) {
/* 100 */       this.code = code;
/*     */     }
/*     */ 
/*     */     
/*     */     public static PacketElementType getByCode(char code) {
/* 105 */       for (PacketElementType type : values()) {
/* 106 */         if (type.code == code)
/* 107 */           return type; 
/* 108 */       }  return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract void write(SM_CUSTOM_PACKET param1SM_CUSTOM_PACKET, ByteBuffer param1ByteBuffer, String param1String);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PacketElement
/*     */   {
/*     */     private final SM_CUSTOM_PACKET.PacketElementType type;
/*     */ 
/*     */ 
/*     */     
/*     */     private final String value;
/*     */ 
/*     */ 
/*     */     
/*     */     public PacketElement(SM_CUSTOM_PACKET.PacketElementType type, String value) {
/* 131 */       this.type = type;
/* 132 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeValue(SM_CUSTOM_PACKET packet, ByteBuffer buf) {
/* 145 */       this.type.write(packet, buf, this.value);
/*     */     }
/*     */   }
/*     */   
/* 149 */   private List<PacketElement> elements = new ArrayList<PacketElement>();
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_CUSTOM_PACKET(int opcode) {
/* 154 */     setOpcode(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElement(PacketElement packetElement) {
/* 164 */     this.elements.add(packetElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElement(PacketElementType type, String value) {
/* 175 */     this.elements.add(new PacketElement(type, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 182 */     for (PacketElement el : this.elements)
/*     */     {
/* 184 */       el.writeValue(this, buf);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CUSTOM_PACKET.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */