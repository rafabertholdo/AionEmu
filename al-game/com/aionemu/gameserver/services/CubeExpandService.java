/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.templates.CubeExpandTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class CubeExpandService
/*     */ {
/*  38 */   private static final Logger log = Logger.getLogger(CubeExpandService.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_EXPAND = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_EXPAND = 9;
/*     */ 
/*     */ 
/*     */   
/*     */   public static void expandCube(final Player player, Npc npc) {
/*  51 */     CubeExpandTemplate expandTemplate = DataManager.CUBEEXPANDER_DATA.getCubeExpandListTemplate(npc.getNpcId());
/*     */     
/*  53 */     if (expandTemplate == null) {
/*     */       
/*  55 */       log.error("Cube Expand Template could not be found for Npc ID: " + npc.getObjectId());
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     if (npcCanExpandLevel(expandTemplate, player.getCubeSize() + 1) && validateNewSize(player.getCubeSize() + 1)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  65 */       final int price = getPriceByLevel(expandTemplate, player.getCubeSize() + 1);
/*     */       
/*  67 */       RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)npc)
/*     */         {
/*     */           public void acceptRequest(Creature requester, Player responder)
/*     */           {
/*  71 */             if (!ItemService.decreaseKinah(responder, price)) {
/*     */               
/*  73 */               PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.CUBEEXPAND_NOT_ENOUGH_KINAH);
/*     */               return;
/*     */             } 
/*  76 */             CubeExpandService.expand(responder);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void denyRequest(Creature requester, Player responder) {}
/*     */         };
/*  86 */       boolean result = player.getResponseRequester().putRequest(900686, responseHandler);
/*     */       
/*  88 */       if (result)
/*     */       {
/*  90 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(900686, 0, new Object[] { String.valueOf(price) }));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  95 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300430, new Object[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void expand(Player player) {
/* 104 */     if (!validateNewSize(player.getCubeSize() + 1))
/*     */       return; 
/* 106 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300431, new Object[] { "9" }));
/* 107 */     player.setCubesize(player.getCubeSize() + 1);
/* 108 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CUBE_UPDATE(player, 0));
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
/*     */   private static boolean validateNewSize(int level) {
/* 120 */     if (level < 0 || level > 9)
/* 121 */       return false; 
/* 122 */     return true;
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
/*     */   private static boolean npcCanExpandLevel(CubeExpandTemplate clist, int level) {
/* 135 */     if (!clist.contains(level))
/* 136 */       return false; 
/* 137 */     return true;
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
/*     */   private static int getPriceByLevel(CubeExpandTemplate clist, int level) {
/* 149 */     return clist.get(level).getPrice();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\CubeExpandService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */