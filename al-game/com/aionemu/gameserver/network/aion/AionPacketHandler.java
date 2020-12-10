/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class AionPacketHandler
/*     */ {
/*  38 */   private static final Logger log = Logger.getLogger(AionPacketHandler.class);
/*     */   
/*  40 */   private Map<AionConnection.State, Map<Integer, AionClientPacket>> packetsPrototypes = new HashMap<AionConnection.State, Map<Integer, AionClientPacket>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AionClientPacket handle(ByteBuffer data, AionConnection client) {
/*  51 */     AionConnection.State state = client.getState();
/*  52 */     int id = data.get() & 0xFF;
/*     */ 
/*     */     
/*  55 */     data.position(data.position() + 2);
/*     */     
/*  57 */     return getPacket(state, id, data, client);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPacketPrototype(AionClientPacket packetPrototype, AionConnection.State... states) {
/*  62 */     for (AionConnection.State state : states) {
/*     */       
/*  64 */       Map<Integer, AionClientPacket> pm = this.packetsPrototypes.get(state);
/*  65 */       if (pm == null) {
/*     */         
/*  67 */         pm = new HashMap<Integer, AionClientPacket>();
/*  68 */         this.packetsPrototypes.put(state, pm);
/*     */       } 
/*  70 */       pm.put(Integer.valueOf(packetPrototype.getOpcode()), packetPrototype);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private AionClientPacket getPacket(AionConnection.State state, int id, ByteBuffer buf, AionConnection con) {
/*  76 */     AionClientPacket prototype = null;
/*     */     
/*  78 */     Map<Integer, AionClientPacket> pm = this.packetsPrototypes.get(state);
/*  79 */     if (pm != null)
/*     */     {
/*  81 */       prototype = pm.get(Integer.valueOf(id));
/*     */     }
/*     */     
/*  84 */     if (prototype == null) {
/*     */       
/*  86 */       unknownPacket(state, id, buf);
/*  87 */       return null;
/*     */     } 
/*     */     
/*  90 */     AionClientPacket res = prototype.clonePacket();
/*  91 */     res.setBuffer(buf);
/*  92 */     res.setConnection(con);
/*     */     
/*  94 */     return res;
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
/*     */   private void unknownPacket(AionConnection.State state, int id, ByteBuffer data) {
/* 106 */     if (OptionsConfig.LOG_PACKETS)
/* 107 */       log.warn(String.format("[UNKNOWN PACKET] : 0x%02X, state=%s %n%s", new Object[] { Integer.valueOf(id), state.toString(), Util.toHex(data) })); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionPacketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */