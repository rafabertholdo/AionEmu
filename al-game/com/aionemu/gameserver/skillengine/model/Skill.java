/*     */ package com.aionemu.gameserver.skillengine.model;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.controllers.movement.StartMovingListener;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CASTSPELL;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CASTSPELL_END;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.skillengine.SkillEngine;
/*     */ import com.aionemu.gameserver.skillengine.action.Action;
/*     */ import com.aionemu.gameserver.skillengine.action.Actions;
/*     */ import com.aionemu.gameserver.skillengine.condition.Condition;
/*     */ import com.aionemu.gameserver.skillengine.condition.Conditions;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectId;
/*     */ import com.aionemu.gameserver.skillengine.properties.Properties;
/*     */ import com.aionemu.gameserver.skillengine.properties.Property;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class Skill
/*     */ {
/*     */   private List<Creature> effectedList;
/*     */   private Creature firstTarget;
/*     */   private Creature effector;
/*     */   private int skillLevel;
/*     */   private int skillStackLvl;
/*     */   private StartMovingListener conditionChangeListener;
/*     */   private SkillTemplate skillTemplate;
/*     */   private boolean firstTargetRangeCheck = true;
/*     */   private ItemTemplate itemTemplate;
/*     */   private int targetType;
/*     */   private boolean chainSuccess = true;
/*     */   private float x;
/*     */   private float y;
/*     */   private float z;
/*     */   private int changeMpConsumptionValue;
/*     */   private float firstTargetRange;
/*     */   private int duration;
/*     */   
/*     */   public enum SkillType
/*     */   {
/*  87 */     CAST,
/*  88 */     ITEM,
/*  89 */     PASSIVE;
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
/*     */   public Skill(SkillTemplate skillTemplate, Player effector, Creature firstTarget) {
/* 102 */     this(skillTemplate, (Creature)effector, effector.getSkillList().getSkillLevel(skillTemplate.getSkillId()), firstTarget);
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
/*     */   public Skill(SkillTemplate skillTemplate, Creature effector, int skillLvl, Creature firstTarget) {
/* 114 */     this.effectedList = new ArrayList<Creature>();
/* 115 */     this.conditionChangeListener = new StartMovingListener();
/* 116 */     this.firstTarget = firstTarget;
/* 117 */     this.skillLevel = skillLvl;
/* 118 */     this.skillStackLvl = skillTemplate.getLvl();
/* 119 */     this.skillTemplate = skillTemplate;
/* 120 */     this.effector = effector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUseSkill() {
/* 131 */     if (!setProperties(this.skillTemplate.getInitproperties())) {
/* 132 */       return false;
/*     */     }
/* 134 */     if (!preCastCheck()) {
/* 135 */       return false;
/*     */     }
/* 137 */     if (!setProperties(this.skillTemplate.getSetproperties())) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     this.effector.setCasting(this);
/* 142 */     Iterator<Creature> effectedIter = this.effectedList.iterator();
/* 143 */     while (effectedIter.hasNext()) {
/*     */       
/* 145 */       Creature effected = effectedIter.next();
/* 146 */       if (effected == null) {
/* 147 */         effected = this.effector;
/*     */       }
/* 149 */       if (this.effector instanceof Player) {
/*     */         
/* 151 */         if (!RestrictionsManager.canAffectBySkill((Player)this.effector, (VisibleObject)effected) && this.skillTemplate.getSkillId() != 1968) {
/* 152 */           effectedIter.remove();
/*     */         }
/*     */         continue;
/*     */       } 
/* 156 */       if (this.effector.getEffectController().isAbnormalState(EffectId.CANT_ATTACK_STATE) && this.skillTemplate.getSkillId() != 1968) {
/* 157 */         effectedIter.remove();
/*     */       }
/*     */     } 
/* 160 */     this.effector.setCasting(null);
/*     */ 
/*     */     
/* 163 */     if (this.targetType == 0 && this.effectedList.size() == 0)
/*     */     {
/* 165 */       return false;
/*     */     }
/* 167 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useSkill() {
/* 175 */     if (!canUseSkill()) {
/*     */       return;
/*     */     }
/* 178 */     this.changeMpConsumptionValue = 0;
/*     */     
/* 180 */     this.effector.getObserveController().notifySkilluseObservers(this);
/*     */ 
/*     */     
/* 183 */     this.effector.setCasting(this);
/*     */     
/* 185 */     checkSkillSetException();
/*     */     
/* 187 */     int skillDuration = this.skillTemplate.getDuration();
/* 188 */     int currentStat = this.effector.getGameStats().getCurrentStat(StatEnum.BOOST_CASTING_TIME);
/* 189 */     this.duration = skillDuration + Math.round((skillDuration * (100 - currentStat)) / 100.0F);
/*     */     
/* 191 */     int cooldown = this.skillTemplate.getCooldown();
/* 192 */     if (cooldown != 0) {
/* 193 */       this.effector.setSkillCoolDown(this.skillTemplate.getSkillId(), (cooldown * 100 + this.duration) + System.currentTimeMillis());
/*     */     }
/* 195 */     if (this.duration < 0) {
/* 196 */       this.duration = 0;
/*     */     }
/* 198 */     if (this.skillTemplate.isActive() || this.skillTemplate.isToggle())
/*     */     {
/* 200 */       startCast();
/*     */     }
/*     */     
/* 203 */     this.effector.getObserveController().attach((ActionObserver)this.conditionChangeListener);
/*     */     
/* 205 */     if (this.duration > 0) {
/*     */       
/* 207 */       schedule(this.duration);
/*     */     }
/*     */     else {
/*     */       
/* 211 */       endCast();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startPenaltySkill() {
/* 220 */     if (this.skillTemplate.getPenaltySkillId() == 0) {
/*     */       return;
/*     */     }
/* 223 */     Skill skill = SkillEngine.getInstance().getSkill(this.effector, this.skillTemplate.getPenaltySkillId(), 1, (VisibleObject)this.firstTarget);
/* 224 */     skill.useSkill();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startCast() {
/* 232 */     int targetObjId = (this.firstTarget != null) ? this.firstTarget.getObjectId() : 0;
/*     */     
/* 234 */     switch (this.targetType) {
/*     */       
/*     */       case 0:
/* 237 */         PacketSendUtility.broadcastPacketAndReceive((VisibleObject)this.effector, (AionServerPacket)new SM_CASTSPELL(this.effector.getObjectId(), this.skillTemplate.getSkillId(), this.skillLevel, this.targetType, targetObjId, this.duration));
/*     */         break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 248 */         PacketSendUtility.broadcastPacketAndReceive((VisibleObject)this.effector, (AionServerPacket)new SM_CASTSPELL(this.effector.getObjectId(), this.skillTemplate.getSkillId(), this.skillLevel, this.targetType, this.x, this.y, this.z, this.duration));
/*     */         break;
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
/*     */ 
/*     */ 
/*     */   
/*     */   private void endCast() {
/* 265 */     if (!this.effector.isCasting()) {
/*     */       return;
/*     */     }
/*     */     
/* 269 */     if (!checkEndCast())
/*     */     {
/* 271 */       if (this.effector instanceof Player) {
/*     */         
/* 273 */         Player player = (Player)this.effector;
/* 274 */         player.getController().cancelCurrentSkill();
/* 275 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ATTACK_TOO_FAR_FROM_TARGET());
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/* 281 */     this.effector.setCasting(null);
/*     */     
/* 283 */     if (!preUsageCheck()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 289 */     int spellStatus = 0;
/*     */     
/* 291 */     List<Effect> effects = new ArrayList<Effect>();
/* 292 */     if (this.skillTemplate.getEffects() != null)
/*     */     {
/* 294 */       for (Creature effected : this.effectedList) {
/*     */         
/* 296 */         Effect effect = new Effect(this.effector, effected, this.skillTemplate, this.skillLevel, 0, this.itemTemplate);
/* 297 */         effect.initialize();
/* 298 */         spellStatus = effect.getSpellStatus().getId();
/* 299 */         effects.add(effect);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 304 */     int chainProb = this.skillTemplate.getChainSkillProb();
/* 305 */     if (chainProb != 0)
/*     */     {
/* 307 */       if (Rnd.get(100) < chainProb) {
/* 308 */         this.chainSuccess = true;
/*     */       } else {
/* 310 */         this.chainSuccess = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (this.skillTemplate.isActive() || this.skillTemplate.isToggle())
/*     */     {
/* 318 */       sendCastspellEnd(spellStatus, effects);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     Actions skillActions = this.skillTemplate.getActions();
/* 325 */     if (skillActions != null)
/*     */     {
/* 327 */       for (Action action : skillActions.getActions())
/*     */       {
/* 329 */         action.act(this);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     for (Effect effect : effects)
/*     */     {
/* 338 */       effect.applyEffect();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 344 */     startPenaltySkill();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendCastspellEnd(int spellStatus, List<Effect> effects) {
/* 353 */     switch (this.targetType) {
/*     */       
/*     */       case 0:
/* 356 */         PacketSendUtility.broadcastPacketAndReceive((VisibleObject)this.effector, (AionServerPacket)new SM_CASTSPELL_END(this.effector, this.firstTarget, effects, this.skillTemplate.getSkillId(), this.skillLevel, this.skillTemplate.getCooldown(), this.chainSuccess, spellStatus));
/*     */         break;
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
/*     */       case 1:
/* 369 */         PacketSendUtility.broadcastPacketAndReceive((VisibleObject)this.effector, (AionServerPacket)new SM_CASTSPELL_END(this.effector, this.firstTarget, effects, this.skillTemplate.getSkillId(), this.skillLevel, this.skillTemplate.getCooldown(), this.chainSuccess, spellStatus, this.x, this.y, this.z));
/*     */         break;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void schedule(int delay) {
/* 387 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 391 */             Skill.this.endCast();
/*     */           }
/*     */         },  delay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean preCastCheck() {
/* 401 */     Conditions skillConditions = this.skillTemplate.getStartconditions();
/* 402 */     return checkConditions(skillConditions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean preUsageCheck() {
/* 410 */     Conditions skillConditions = this.skillTemplate.getUseconditions();
/* 411 */     return checkConditions(skillConditions);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkConditions(Conditions conditions) {
/* 416 */     if (conditions != null)
/*     */     {
/* 418 */       for (Condition condition : conditions.getConditions()) {
/*     */         
/* 420 */         if (!condition.verify(this))
/*     */         {
/* 422 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 426 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setProperties(Properties properties) {
/* 431 */     if (properties != null)
/*     */     {
/* 433 */       for (Property property : properties.getProperties()) {
/*     */         
/* 435 */         if (!property.set(this))
/*     */         {
/* 437 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/* 441 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkSkillSetException() {
/* 446 */     int setNumber = this.skillTemplate.getSkillSetException();
/* 447 */     if (this.effector instanceof Player) {
/*     */       
/* 449 */       Player player = (Player)this.effector;
/* 450 */       if (setNumber != 0) {
/* 451 */         player.getEffectController().removeEffectBySetNumber(setNumber);
/*     */       } else {
/* 453 */         player.getEffectController().removeEffectWithSetNumberReserved();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChangeMpConsumption(int value) {
/* 462 */     this.changeMpConsumptionValue = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChangeMpConsumption() {
/* 470 */     return this.changeMpConsumptionValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkEndCast() {
/* 478 */     if (this.firstTargetRange == 0.0F) {
/* 479 */       return true;
/*     */     }
/* 481 */     if (this.effector == this.firstTarget)
/* 482 */       return true; 
/* 483 */     if (!MathUtil.isIn3dRange((VisibleObject)this.effector, (VisibleObject)this.firstTarget, this.firstTargetRange + 4.0F))
/* 484 */       return false; 
/* 485 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstTargetRange(float value) {
/* 493 */     this.firstTargetRange = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Creature> getEffectedList() {
/* 501 */     return this.effectedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getEffector() {
/* 509 */     return this.effector;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillLevel() {
/* 517 */     return this.skillLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillStackLvl() {
/* 525 */     return this.skillStackLvl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StartMovingListener getConditionChangeListener() {
/* 533 */     return this.conditionChangeListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillTemplate getSkillTemplate() {
/* 541 */     return this.skillTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getFirstTarget() {
/* 549 */     return this.firstTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstTarget(Creature firstTarget) {
/* 557 */     this.firstTarget = firstTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPassive() {
/* 565 */     return (this.skillTemplate.getActivationAttribute() == ActivationAttribute.PASSIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirstTargetRangeCheck() {
/* 573 */     return this.firstTargetRangeCheck;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFirstTargetRangeCheck(boolean firstTargetRangeCheck) {
/* 581 */     this.firstTargetRangeCheck = firstTargetRangeCheck;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemTemplate(ItemTemplate itemTemplate) {
/* 589 */     this.itemTemplate = itemTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetType(int targetType, float x, float y, float z) {
/* 600 */     this.targetType = targetType;
/* 601 */     this.x = x;
/* 602 */     this.y = y;
/* 603 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\Skill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */