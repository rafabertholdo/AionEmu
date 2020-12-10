/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.EmotionType;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class CM_SUMMON_EMOTION
/*    */   extends AionClientPacket
/*    */ {
/* 35 */   private static final Logger log = Logger.getLogger(CM_SUMMON_EMOTION.class);
/*    */ 
/*    */   
/*    */   private int objId;
/*    */   
/*    */   private int emotionTypeId;
/*    */ 
/*    */   
/*    */   public CM_SUMMON_EMOTION(int opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 50 */     this.objId = readD();
/* 51 */     this.emotionTypeId = readC();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 57 */     EmotionType emotionType = EmotionType.getEmotionTypeById(this.emotionTypeId);
/*    */ 
/*    */     
/* 60 */     if (emotionType == EmotionType.UNK) {
/* 61 */       log.error("Unknown emotion type? 0x" + Integer.toHexString(this.emotionTypeId).toUpperCase());
/*    */     }
/* 63 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 64 */     if (activePlayer == null)
/*    */       return; 
/* 66 */     Summon summon = activePlayer.getSummon();
/* 67 */     if (summon == null)
/*    */       return; 
/* 69 */     switch (emotionType) {
/*    */       
/*    */       case FLY:
/*    */       case LAND:
/* 73 */         PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_EMOTION((Creature)summon, emotionType));
/*    */         break;
/*    */       case ATTACKMODE:
/* 76 */         summon.setState(CreatureState.WEAPON_EQUIPPED);
/* 77 */         PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_EMOTION((Creature)summon, emotionType));
/*    */         break;
/*    */       case NEUTRALMODE:
/* 80 */         summon.unsetState(CreatureState.WEAPON_EQUIPPED);
/* 81 */         PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_EMOTION((Creature)summon, emotionType));
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SUMMON_EMOTION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */