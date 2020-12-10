/*     */ package com.aionemu.gameserver.network.chatserver;
/*     */ 
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
/*     */ public class CsPacketHandler
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(CsPacketHandler.class);
/*     */   
/*  37 */   private Map<ChatServerConnection.State, Map<Integer, CsClientPacket>> packetPrototypes = new HashMap<ChatServerConnection.State, Map<Integer, CsClientPacket>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CsClientPacket handle(ByteBuffer data, ChatServerConnection client) {
/*  48 */     ChatServerConnection.State state = client.getState();
/*  49 */     int id = data.get() & 0xFF;
/*     */     
/*  51 */     return getPacket(state, id, data, client);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPacketPrototype(CsClientPacket packetPrototype, ChatServerConnection.State... states) {
/*  61 */     for (ChatServerConnection.State state : states) {
/*     */       
/*  63 */       Map<Integer, CsClientPacket> pm = this.packetPrototypes.get(state);
/*  64 */       if (pm == null) {
/*     */         
/*  66 */         pm = new HashMap<Integer, CsClientPacket>();
/*  67 */         this.packetPrototypes.put(state, pm);
/*     */       } 
/*  69 */       pm.put(Integer.valueOf(packetPrototype.getOpcode()), packetPrototype);
/*     */     } 
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
/*     */   
/*     */   private CsClientPacket getPacket(ChatServerConnection.State state, int id, ByteBuffer buf, ChatServerConnection con) {
/*  83 */     CsClientPacket prototype = null;
/*     */     
/*  85 */     Map<Integer, CsClientPacket> pm = this.packetPrototypes.get(state);
/*  86 */     if (pm != null)
/*     */     {
/*  88 */       prototype = pm.get(Integer.valueOf(id));
/*     */     }
/*     */     
/*  91 */     if (prototype == null) {
/*     */       
/*  93 */       unknownPacket(state, id);
/*  94 */       return null;
/*     */     } 
/*     */     
/*  97 */     CsClientPacket res = prototype.clonePacket();
/*  98 */     res.setBuffer(buf);
/*  99 */     res.setConnection(con);
/*     */     
/* 101 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void unknownPacket(ChatServerConnection.State state, int id) {
/* 112 */     log.warn(String.format("Unknown packet recived from Chat Server: 0x%02X state=%s", new Object[] { Integer.valueOf(id), state.toString() }));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\CsPacketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */