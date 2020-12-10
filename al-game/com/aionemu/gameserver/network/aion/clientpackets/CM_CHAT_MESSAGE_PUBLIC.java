/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.commons.objects.filter.ObjectFilter;
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceGroup;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.chathandlers.ChatHandler;
/*     */ import com.aionemu.gameserver.utils.chathandlers.ChatHandlerResponse;
/*     */ import com.aionemu.gameserver.utils.chathandlers.ChatHandlers;
/*     */ import javolution.util.FastList;
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
/*     */ public class CM_CHAT_MESSAGE_PUBLIC
/*     */   extends AionClientPacket
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(CM_CHAT_MESSAGE_PUBLIC.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChatType type;
/*     */ 
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
/*     */   public CM_CHAT_MESSAGE_PUBLIC(int opcode) {
/*  64 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  73 */     this.type = ChatType.getChatTypeByInt(readC());
/*  74 */     this.message = readS();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*  84 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/*  86 */     if (player == null) {
/*     */       return;
/*     */     }
/*  89 */     FastList<ChatHandler> chatHandlers = ChatHandlers.getInstance().getHandlers();
/*  90 */     for (FastList.Node<ChatHandler> n = chatHandlers.head(), end = chatHandlers.tail(); (n = n.getNext()) != end; ) {
/*     */       
/*  92 */       ChatHandlerResponse response = ((ChatHandler)n.getValue()).handleChatMessage(this.type, this.message, player);
/*  93 */       if (response.isBlocked()) {
/*     */         return;
/*     */       }
/*  96 */       this.message = response.getMessage();
/*     */     } 
/*     */     
/*  99 */     if (RestrictionsManager.canChat(player)) {
/*     */       
/* 101 */       switch (this.type) {
/*     */         
/*     */         case GROUP:
/* 104 */           if (player.getPlayerGroup() == null && player.getPlayerAlliance() == null) {
/*     */             return;
/*     */           }
/* 107 */           broadcastToGroupMembers(player);
/*     */           return;
/*     */         case ALLIANCE:
/* 110 */           if (player.getPlayerAlliance() == null) {
/*     */             return;
/*     */           }
/* 113 */           if (OptionsConfig.LOG_CHAT) {
/* 114 */             log.info(String.format("[MESSAGE] - A <%d>: [%s]> %s", new Object[] { Integer.valueOf(player.getPlayerAlliance().getObjectId()), player.getName(), this.message }));
/*     */           }
/* 116 */           broadcastToAllianceMembers(player);
/*     */           return;
/*     */         
/*     */         case GROUP_LEADER:
/* 120 */           if (!player.isInGroup() && !player.isInAlliance()) {
/*     */             return;
/*     */           }
/* 123 */           if (OptionsConfig.LOG_CHAT) {
/* 124 */             log.info(String.format("[MESSAGE] - LA: [%s]> %s", new Object[] { player.getName(), this.message }));
/*     */           }
/*     */           
/* 127 */           if (player.isInGroup()) {
/* 128 */             broadcastToGroupMembers(player);
/*     */           } else {
/* 130 */             broadcastToAllianceMembers(player);
/*     */           }  return;
/*     */         case LEGION:
/* 133 */           if (OptionsConfig.LOG_CHAT) {
/* 134 */             log.info(String.format("[MESSAGE] - L <%s>: [%s]> %s", new Object[] { player.getLegion().getLegionName(), player.getName(), this.message }));
/*     */           }
/* 136 */           broadcastToLegionMembers(player);
/*     */           return;
/*     */       } 
/* 139 */       if (OptionsConfig.LOG_CHAT) {
/* 140 */         log.info(String.format("[MESSAGE] - ALL: [%s]> %s", new Object[] { player.getName(), this.message }));
/*     */       }
/* 142 */       broadcastToNonBlockedPlayers(player);
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
/*     */   private void broadcastToNonBlockedPlayers(final Player player) {
/* 155 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_MESSAGE(player, this.message, this.type), true, new ObjectFilter<Player>()
/*     */         {
/*     */ 
/*     */           
/*     */           public boolean acceptObject(Player object)
/*     */           {
/* 161 */             return !object.getBlockList().contains(player.getObjectId());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void broadcastToGroupMembers(Player player) {
/* 174 */     if (player.isInGroup()) {
/*     */       
/* 176 */       if (OptionsConfig.LOG_CHAT)
/* 177 */         log.info(String.format("[MESSAGE] - G <%d>: [%s]> %s", new Object[] { Integer.valueOf(player.getPlayerGroup().getGroupId()), player.getName(), this.message })); 
/* 178 */       for (Player groupPlayer : player.getPlayerGroup().getMembers())
/*     */       {
/* 180 */         PacketSendUtility.sendPacket(groupPlayer, (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
/*     */       }
/*     */     }
/* 183 */     else if (player.isInAlliance()) {
/*     */       
/* 185 */       if (OptionsConfig.LOG_CHAT)
/* 186 */         log.info(String.format("[MESSAGE] - A <%d>: [%s]> %s", new Object[] { Integer.valueOf(player.getPlayerAlliance().getObjectId()), player.getName(), this.message })); 
/* 187 */       PlayerAllianceGroup allianceGroup = player.getPlayerAlliance().getPlayerAllianceGroupForMember(player.getObjectId());
/* 188 */       if (allianceGroup != null)
/*     */       {
/* 190 */         for (PlayerAllianceMember allianceMember : allianceGroup.getMembers())
/*     */         {
/* 192 */           if (allianceMember.isOnline()) {
/* 193 */             PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
/*     */           }
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 200 */       PacketSendUtility.sendMessage(player, "You are not in an alliance or group. (Error 105)");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void broadcastToAllianceMembers(Player player) {
/* 211 */     PlayerAlliance alliance = player.getPlayerAlliance();
/* 212 */     if (alliance != null)
/*     */     {
/* 214 */       for (PlayerAllianceMember allianceMember : alliance.getMembers()) {
/*     */         
/* 216 */         if (!allianceMember.isOnline())
/* 217 */           continue;  PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void broadcastToLegionMembers(Player player) {
/* 229 */     if (player.isLegionMember())
/*     */     {
/* 231 */       PacketSendUtility.broadcastPacketToLegion(player.getLegion(), (AionServerPacket)new SM_MESSAGE(player, this.message, this.type));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CHAT_MESSAGE_PUBLIC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */