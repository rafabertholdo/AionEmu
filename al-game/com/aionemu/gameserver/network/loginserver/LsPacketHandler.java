/*     */ package com.aionemu.gameserver.network.loginserver;
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
/*     */ 
/*     */ public class LsPacketHandler
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(LsPacketHandler.class);
/*     */   
/*  38 */   private static Map<LoginServerConnection.State, Map<Integer, LsClientPacket>> packetPrototypes = new HashMap<LoginServerConnection.State, Map<Integer, LsClientPacket>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LsClientPacket handle(ByteBuffer data, LoginServerConnection client) {
/*  49 */     LoginServerConnection.State state = client.getState();
/*  50 */     int id = data.get() & 0xFF;
/*     */     
/*  52 */     return getPacket(state, id, data, client);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPacketPrototype(LsClientPacket packetPrototype, LoginServerConnection.State... states) {
/*  57 */     for (LoginServerConnection.State state : states) {
/*     */       
/*  59 */       Map<Integer, LsClientPacket> pm = packetPrototypes.get(state);
/*  60 */       if (pm == null) {
/*     */         
/*  62 */         pm = new HashMap<Integer, LsClientPacket>();
/*  63 */         packetPrototypes.put(state, pm);
/*     */       } 
/*  65 */       pm.put(Integer.valueOf(packetPrototype.getOpcode()), packetPrototype);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private LsClientPacket getPacket(LoginServerConnection.State state, int id, ByteBuffer buf, LoginServerConnection con) {
/*  71 */     LsClientPacket prototype = null;
/*     */     
/*  73 */     Map<Integer, LsClientPacket> pm = packetPrototypes.get(state);
/*  74 */     if (pm != null)
/*     */     {
/*  76 */       prototype = pm.get(Integer.valueOf(id));
/*     */     }
/*     */     
/*  79 */     if (prototype == null) {
/*     */       
/*  81 */       unknownPacket(state, id);
/*  82 */       return null;
/*     */     } 
/*     */     
/*  85 */     LsClientPacket res = prototype.clonePacket();
/*  86 */     res.setBuffer(buf);
/*  87 */     res.setConnection(con);
/*     */     
/*  89 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void unknownPacket(LoginServerConnection.State state, int id) {
/* 100 */     log.warn(String.format("Unknown packet recived from Login Server: 0x%02X state=%s", new Object[] { Integer.valueOf(id), state.toString() }));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LsPacketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */