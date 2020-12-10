/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ public class CM_CHAT_MESSAGE_WHISPER
/*     */   extends AionClientPacket
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(CM_CHAT_MESSAGE_WHISPER.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String message;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CM_CHAT_MESSAGE_WHISPER(int opcode) {
/*  58 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  68 */     this.name = readS();
/*  69 */     this.message = readS();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  78 */     String formatname = Util.convertName(this.name);
/*     */     
/*  80 */     Player sender = ((AionConnection)getConnection()).getActivePlayer();
/*  81 */     Player receiver = World.getInstance().findPlayer(formatname);
/*     */     
/*  83 */     if (OptionsConfig.LOG_CHAT) {
/*  84 */       log.info(String.format("[MESSAGE] [%s] W: %s, Message: %s", new Object[] { sender.getName(), formatname, this.message }));
/*     */     }
/*  86 */     if (receiver == null) {
/*     */       
/*  88 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.PLAYER_IS_OFFLINE(formatname));
/*     */     }
/*  90 */     else if (sender.getLevel() < CustomConfig.LEVEL_TO_WHISPER) {
/*     */       
/*  92 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.LEVEL_NOT_ENOUGH_FOR_WHISPER(String.valueOf(CustomConfig.LEVEL_TO_WHISPER)));
/*     */     }
/*  94 */     else if (receiver.getBlockList().contains(sender.getObjectId())) {
/*     */       
/*  96 */       sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.YOU_ARE_BLOCKED_BY(receiver.getName()));
/*     */ 
/*     */     
/*     */     }
/* 100 */     else if (RestrictionsManager.canChat(sender)) {
/* 101 */       PacketSendUtility.sendPacket(receiver, (AionServerPacket)new SM_MESSAGE(sender, this.message, ChatType.WHISPER));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CHAT_MESSAGE_WHISPER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */