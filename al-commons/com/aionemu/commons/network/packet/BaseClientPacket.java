/*     */ package com.aionemu.commons.network.packet;
/*     */ 
/*     */ import com.aionemu.commons.network.AConnection;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ public abstract class BaseClientPacket<T extends AConnection>
/*     */   extends BasePacket
/*     */   implements Runnable
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(BaseClientPacket.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T client;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ByteBuffer buf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseClientPacket(ByteBuffer buf, int opcode) {
/*  57 */     this(opcode);
/*  58 */     this.buf = buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseClientPacket(int opcode) {
/*  69 */     super(BasePacket.PacketType.CLIENT, opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuffer(ByteBuffer buf) {
/*  79 */     this.buf = buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setConnection(T client) {
/*  89 */     this.client = client;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean read() {
/*     */     try {
/* 102 */       readImpl();
/*     */       
/* 104 */       if (getRemainingBytes() > 0) {
/* 105 */         log.debug("Packet " + this + " not fully readed!");
/*     */       }
/* 107 */       return true;
/*     */     }
/* 109 */     catch (Exception re) {
/*     */       
/* 111 */       log.error("Reading failed for packet " + this, re);
/* 112 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void readImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getRemainingBytes() {
/* 126 */     return this.buf.remaining();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int readD() {
/*     */     try {
/* 138 */       return this.buf.getInt();
/*     */     }
/* 140 */     catch (Exception e) {
/*     */       
/* 142 */       log.error("Missing D for: " + this);
/*     */       
/* 144 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int readC() {
/*     */     try {
/* 156 */       return this.buf.get() & 0xFF;
/*     */     }
/* 158 */     catch (Exception e) {
/*     */       
/* 160 */       log.error("Missing C for: " + this);
/*     */       
/* 162 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int readH() {
/*     */     try {
/* 174 */       return this.buf.getShort() & 0xFFFF;
/*     */     }
/* 176 */     catch (Exception e) {
/*     */       
/* 178 */       log.error("Missing H for: " + this);
/*     */       
/* 180 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final double readDF() {
/*     */     try {
/* 192 */       return this.buf.getDouble();
/*     */     }
/* 194 */     catch (Exception e) {
/*     */       
/* 196 */       log.error("Missing DF for: " + this);
/*     */       
/* 198 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final float readF() {
/*     */     try {
/* 210 */       return this.buf.getFloat();
/*     */     }
/* 212 */     catch (Exception e) {
/*     */       
/* 214 */       log.error("Missing F for: " + this);
/*     */       
/* 216 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long readQ() {
/*     */     try {
/* 228 */       return this.buf.getLong();
/*     */     }
/* 230 */     catch (Exception e) {
/*     */       
/* 232 */       log.error("Missing Q for: " + this);
/*     */       
/* 234 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final String readS() {
/* 244 */     StringBuffer sb = new StringBuffer();
/*     */     
/*     */     try {
/*     */       char ch;
/* 248 */       while ((ch = this.buf.getChar()) != '\000') {
/* 249 */         sb.append(ch);
/*     */       }
/* 251 */     } catch (Exception e) {
/*     */       
/* 253 */       log.error("Missing S for: " + this);
/*     */     } 
/* 255 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final byte[] readB(int length) {
/* 266 */     byte[] result = new byte[length];
/*     */     
/*     */     try {
/* 269 */       this.buf.get(result);
/*     */     }
/* 271 */     catch (Exception e) {
/*     */       
/* 273 */       log.error("Missing byte[] for: " + this);
/*     */     } 
/* 275 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void runImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final T getConnection() {
/* 288 */     return this.client;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\packet\BaseClientPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */