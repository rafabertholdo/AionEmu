/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.zone.ZoneInstance;
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
/*     */ public class CM_EMOTION
/*     */   extends AionClientPacket
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(CM_EMOTION.class);
/*     */ 
/*     */ 
/*     */   
/*     */   EmotionType emotionType;
/*     */ 
/*     */ 
/*     */   
/*     */   int emotion;
/*     */ 
/*     */ 
/*     */   
/*     */   float x;
/*     */ 
/*     */ 
/*     */   
/*     */   float y;
/*     */ 
/*     */   
/*     */   float z;
/*     */ 
/*     */   
/*     */   byte heading;
/*     */ 
/*     */ 
/*     */   
/*     */   public CM_EMOTION(int opcode) {
/*  67 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  77 */     int et = readC();
/*  78 */     this.emotionType = EmotionType.getEmotionTypeById(et);
/*     */     
/*  80 */     switch (this.emotionType) {
/*     */       case SELECT_TARGET:
/*     */       case JUMP:
/*     */       case SIT:
/*     */       case STAND:
/*     */       case LAND_FLYTELEPORT:
/*     */       case FLY:
/*     */       case LAND:
/*     */       case DIE:
/*     */       case ATTACKMODE:
/*     */       case NEUTRALMODE:
/*     */       case END_DUEL:
/*     */       case WALK:
/*     */       case RUN:
/*     */       case SWITCH_DOOR:
/*     */       case POWERSHARD_ON:
/*     */       case POWERSHARD_OFF:
/*     */       case ATTACKMODE2:
/*     */       case NEUTRALMODE2:
/*     */         return;
/*     */       
/*     */       case EMOTE:
/* 102 */         this.emotion = readH();
/*     */       
/*     */       case CHAIR_SIT:
/*     */       case CHAIR_UP:
/* 106 */         this.x = readF();
/* 107 */         this.y = readF();
/* 108 */         this.z = readF();
/* 109 */         this.heading = (byte)readC();
/*     */     } 
/*     */     
/* 112 */     log.error("Unknown emotion type? 0x" + Integer.toHexString(et).toUpperCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/*     */     ZoneInstance currentZone;
/* 123 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*     */     
/* 125 */     switch (this.emotionType) {
/*     */       case SELECT_TARGET:
/*     */         return;
/*     */       
/*     */       case SIT:
/* 130 */         player.setState(CreatureState.RESTING);
/*     */         break;
/*     */       case STAND:
/* 133 */         player.unsetState(CreatureState.RESTING);
/*     */         break;
/*     */       case CHAIR_SIT:
/* 136 */         player.unsetState(CreatureState.ACTIVE);
/* 137 */         player.setState(CreatureState.CHAIR);
/*     */         break;
/*     */       case CHAIR_UP:
/* 140 */         player.unsetState(CreatureState.CHAIR);
/* 141 */         player.setState(CreatureState.ACTIVE);
/*     */         break;
/*     */       case LAND_FLYTELEPORT:
/* 144 */         player.getController().onFlyTeleportEnd();
/*     */         break;
/*     */       
/*     */       case FLY:
/* 148 */         currentZone = player.getZoneInstance();
/* 149 */         if (currentZone != null) {
/*     */           
/* 151 */           boolean flightAllowed = currentZone.getTemplate().isFlightAllowed();
/* 152 */           if (!flightAllowed) {
/*     */             
/* 154 */             PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_FLYING_FORBIDDEN_HERE);
/*     */             return;
/*     */           } 
/*     */         } 
/* 158 */         player.getFlyController().startFly();
/*     */         break;
/*     */       case LAND:
/* 161 */         player.getFlyController().endFly();
/*     */         break;
/*     */       case ATTACKMODE:
/*     */       case ATTACKMODE2:
/* 165 */         player.setState(CreatureState.WEAPON_EQUIPPED);
/*     */         break;
/*     */       case NEUTRALMODE:
/*     */       case NEUTRALMODE2:
/* 169 */         player.unsetState(CreatureState.WEAPON_EQUIPPED);
/*     */         break;
/*     */       
/*     */       case WALK:
/* 173 */         if (player.getFlyState() > 0)
/*     */           return; 
/* 175 */         player.setState(CreatureState.WALKING);
/*     */         break;
/*     */       case RUN:
/* 178 */         player.unsetState(CreatureState.WALKING);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case POWERSHARD_ON:
/* 184 */         if (!player.getEquipment().isPowerShardEquipped()) {
/*     */           
/* 186 */           PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NO_POWER_SHARD_EQUIPPED());
/*     */           return;
/*     */         } 
/* 189 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.ACTIVATE_THE_POWER_SHARD());
/* 190 */         player.setState(CreatureState.POWERSHARD);
/*     */         break;
/*     */       case POWERSHARD_OFF:
/* 193 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.DEACTIVATE_THE_POWER_SHARD());
/* 194 */         player.unsetState(CreatureState.POWERSHARD);
/*     */         break;
/*     */     } 
/* 197 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION(player, this.emotionType, this.emotion, this.x, this.y, this.z, this.heading, (player.getTarget() == null) ? 0 : player.getTarget().getObjectId()), true);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_EMOTION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */