/*     */ package com.aionemu.gameserver.controllers.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.GroupEvent;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABNORMAL_STATE;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Collections;
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
/*     */ public class PlayerEffectController
/*     */   extends EffectController
/*     */ {
/*     */   private int weaponEffects;
/*     */   private int armorEffects;
/*     */   private Effect foodEffect;
/*     */   
/*     */   public PlayerEffectController(Creature owner) {
/*  56 */     super(owner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEffect(Effect effect) {
/*  62 */     if (effect.isFood()) {
/*  63 */       addFoodEffect(effect);
/*     */     }
/*  65 */     if (checkDuelCondition(effect)) {
/*     */       return;
/*     */     }
/*  68 */     super.addEffect(effect);
/*  69 */     updatePlayerIconsAndGroup(effect);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearEffect(Effect effect) {
/*  75 */     if (effect.isFood()) {
/*  76 */       this.foodEffect = null;
/*     */     }
/*  78 */     super.clearEffect(effect);
/*  79 */     updatePlayerIconsAndGroup(effect);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getOwner() {
/*  85 */     return (Player)super.getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updatePlayerIconsAndGroup(Effect effect) {
/*  93 */     if (!effect.isPassive()) {
/*     */       
/*  95 */       updatePlayerEffectIcons();
/*  96 */       if (getOwner().isInGroup())
/*  97 */         getOwner().getPlayerGroup().updateGroupUIToEvent(getOwner(), GroupEvent.UPDATE); 
/*  98 */       if (getOwner().isInAlliance()) {
/*  99 */         AllianceService.getInstance().updateAllianceUIToEvent(getOwner(), PlayerAllianceEvent.UPDATE);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkDuelCondition(Effect effect) {
/* 110 */     Creature creature = effect.getEffector();
/* 111 */     if (creature instanceof Player)
/*     */     {
/* 113 */       if (getOwner().isFriend((Player)creature) && effect.getTargetSlot() == SkillTargetSlot.DEBUFF.ordinal()) {
/* 114 */         return true;
/*     */       }
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addFoodEffect(Effect effect) {
/* 125 */     if (this.foodEffect != null)
/* 126 */       this.foodEffect.endEffect(); 
/* 127 */     this.foodEffect = effect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWeaponMastery(int skillId) {
/* 135 */     this.weaponEffects = skillId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsetWeaponMastery() {
/* 140 */     this.weaponEffects = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeaponMastery() {
/* 145 */     return this.weaponEffects;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isWeaponMasterySet(int skillId) {
/* 150 */     return (this.weaponEffects == skillId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArmorMastery(int skillId) {
/* 158 */     this.armorEffects = skillId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsetArmorMastery() {
/* 163 */     this.armorEffects = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getArmorMastery() {
/* 168 */     return this.armorEffects;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isArmorMasterySet(int skillId) {
/* 173 */     return (this.armorEffects == skillId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSavedEffect(int skillId, int skillLvl, int currentTime) {
/* 184 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
/* 185 */     int duration = template.getEffectsDuration();
/* 186 */     int remainingTime = duration - currentTime;
/*     */     
/* 188 */     if (remainingTime <= 0) {
/*     */       return;
/*     */     }
/* 191 */     Effect effect = new Effect((Creature)getOwner(), (Creature)getOwner(), template, skillLvl, remainingTime);
/* 192 */     if (effect.isFood())
/* 193 */       addFoodEffect(effect); 
/* 194 */     this.abnormalEffectMap.put(effect.getStack(), effect);
/* 195 */     effect.addAllEffectToSucess();
/* 196 */     effect.startEffect(true);
/*     */     
/* 198 */     PacketSendUtility.sendPacket(getOwner(), (AionServerPacket)new SM_ABNORMAL_STATE(Collections.singletonList(effect), this.abnormals));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\effect\PlayerEffectController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */