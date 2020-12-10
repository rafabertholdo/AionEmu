/*    */ package com.aionemu.gameserver.services;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTIONNAIRE;
/*    */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*    */ import com.aionemu.gameserver.world.World;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HTMLService
/*    */ {
/* 34 */   private static final Logger log = Logger.getLogger(HTMLService.class);
/*    */ 
/*    */   
/*    */   public static void pushSurvey(String html) {
/* 38 */     int messageId = IDFactory.getInstance().nextId();
/* 39 */     for (Player ply : World.getInstance().getAllPlayers()) {
/* 40 */       sendData(ply, messageId, html);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void showHTML(Player player, String html) {
/* 45 */     sendData(player, IDFactory.getInstance().nextId(), html);
/*    */   }
/*    */ 
/*    */   
/*    */   private static void sendData(Player player, int messageId, String html) {
/* 50 */     byte packet_count = (byte)(int)Math.ceil((html.length() / 32759 + 1));
/* 51 */     if (packet_count < 256) {
/*    */       byte i;
/* 53 */       for (i = 0; i < packet_count; i = (byte)(i + 1)) {
/*    */ 
/*    */         
/*    */         try {
/* 57 */           int from = i * 32759, to = (i + 1) * 32759;
/* 58 */           if (from < 0)
/* 59 */             from = 0; 
/* 60 */           if (to > html.length())
/* 61 */             to = html.length(); 
/* 62 */           String sub = html.substring(from, to);
/* 63 */           player.getClientConnection().sendPacket((AionServerPacket)new SM_QUESTIONNAIRE(messageId, i, packet_count, sub));
/*    */         }
/* 65 */         catch (Exception e) {
/*    */           
/* 67 */           log.error(e);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\HTMLService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */