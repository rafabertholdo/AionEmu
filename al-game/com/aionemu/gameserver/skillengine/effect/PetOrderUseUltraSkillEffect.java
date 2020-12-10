/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_USESKILL;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "PetOrderUseUltraSkillEffect")
/*    */ public class PetOrderUseUltraSkillEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 40 */     Player effector = (Player)effect.getEffector();
/* 41 */     int effectorId = effector.getSummon().getObjectId();
/*    */     
/* 43 */     int npcId = effector.getSummon().getNpcId();
/* 44 */     int orderSkillId = effect.getSkillId();
/*    */     
/* 46 */     int petUseSkillId = DataManager.PET_SKILL_DATA.getPetOrderSkill(orderSkillId, npcId);
/* 47 */     int targetId = effect.getEffected().getObjectId();
/*    */     
/* 49 */     PacketSendUtility.sendPacket(effector, (AionServerPacket)new SM_SUMMON_USESKILL(effectorId, petUseSkillId, 1, targetId));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 56 */     if (effect.getEffector() instanceof Player && effect.getEffected() != null)
/* 57 */       effect.addSucessEffect(this); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\PetOrderUseUltraSkillEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */