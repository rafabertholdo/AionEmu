/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class SM_MESSAGE
/*     */   extends AionServerPacket
/*     */ {
/*     */   private Player player;
/*     */   private int senderObjectId;
/*     */   private String message;
/*     */   private String senderName;
/*     */   private Race race;
/*     */   private ChatType chatType;
/*     */   private float x;
/*     */   private float y;
/*     */   private float z;
/*     */   
/*     */   public SM_MESSAGE(Player player, String message, ChatType chatType) {
/*  84 */     this.player = player;
/*  85 */     this.senderObjectId = player.getObjectId();
/*  86 */     this.senderName = player.getName();
/*  87 */     this.message = message;
/*  88 */     this.race = player.getCommonData().getRace();
/*  89 */     this.chatType = chatType;
/*  90 */     this.x = player.getX();
/*  91 */     this.y = player.getY();
/*  92 */     this.z = player.getZ();
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
/*     */   
/*     */   public SM_MESSAGE(int senderObjectId, String senderName, String message, ChatType chatType) {
/* 109 */     this.senderObjectId = senderObjectId;
/* 110 */     this.senderName = senderName;
/* 111 */     this.message = message;
/* 112 */     this.chatType = chatType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 121 */     boolean canRead = true;
/*     */     
/* 123 */     if (this.race != null)
/*     */     {
/* 125 */       canRead = (this.chatType.isSysMsg() || CustomConfig.FACTIONS_SPEAKING_MODE == 1 || this.player.getAccessLevel() > 0 || (con.getActivePlayer() != null && con.getActivePlayer().getAccessLevel() > 0));
/*     */     }
/*     */ 
/*     */     
/* 129 */     writeC(buf, this.chatType.toInteger());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     writeC(buf, canRead ? 0 : (this.race.getRaceId() + 1));
/* 135 */     writeD(buf, this.senderObjectId);
/*     */     
/* 137 */     switch (this.chatType) {
/*     */       
/*     */       case NORMAL:
/*     */       case UNKNOWN_0x18:
/*     */       case ANNOUNCEMENTS:
/*     */       case PERIOD_NOTICE:
/*     */       case PERIOD_ANNOUNCEMENTS:
/*     */       case SYSTEM_NOTICE:
/* 145 */         writeH(buf, 0);
/* 146 */         writeS(buf, this.message);
/*     */         break;
/*     */       case SHOUT:
/* 149 */         writeS(buf, this.senderName);
/* 150 */         writeS(buf, this.message);
/* 151 */         writeF(buf, this.x);
/* 152 */         writeF(buf, this.y);
/* 153 */         writeF(buf, this.z);
/*     */         break;
/*     */       case ALLIANCE:
/*     */       case GROUP:
/*     */       case GROUP_LEADER:
/*     */       case LEGION:
/*     */       case WHISPER:
/* 160 */         writeS(buf, this.senderName);
/* 161 */         writeS(buf, this.message);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_MESSAGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */