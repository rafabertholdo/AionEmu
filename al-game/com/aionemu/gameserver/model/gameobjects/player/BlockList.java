/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockList
/*     */ {
/*     */   public static final int MAX_BLOCKS = 10;
/*     */   private final Map<Integer, BlockedPlayer> blockedList;
/*     */   
/*     */   public BlockList() {
/*  46 */     this((Map<Integer, BlockedPlayer>)new FastMap());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockList(Map<Integer, BlockedPlayer> initialList) {
/*  57 */     this.blockedList = (Map<Integer, BlockedPlayer>)new FastMap(initialList);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(BlockedPlayer plr) {
/*  73 */     this.blockedList.put(Integer.valueOf(plr.getObjId()), plr);
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
/*     */   public void remove(int objIdOfPlayer) {
/*  86 */     this.blockedList.remove(Integer.valueOf(objIdOfPlayer));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockedPlayer getBlockedPlayer(String name) {
/*  97 */     for (BlockedPlayer entry : getBlockedList()) {
/*  98 */       if (entry.getName().equalsIgnoreCase(name))
/*  99 */         return entry; 
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockedPlayer getBlockedPlayer(int playerObjId) {
/* 106 */     return this.blockedList.get(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int playerObjectId) {
/* 111 */     return this.blockedList.containsKey(Integer.valueOf(playerObjectId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 121 */     return this.blockedList.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFull() {
/* 126 */     return (getSize() >= 10);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<BlockedPlayer> getBlockedList() {
/* 131 */     return this.blockedList.values();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\BlockList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */