/*     */ package com.aionemu.gameserver.controllers.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABNORMAL_EFFECT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABNORMAL_STATE;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectId;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillType;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
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
/*     */ public class EffectController
/*     */ {
/*     */   private Creature owner;
/*  45 */   protected Map<String, Effect> passiveEffectMap = (Map<String, Effect>)(new FastMap()).shared();
/*  46 */   protected Map<String, Effect> noshowEffects = (Map<String, Effect>)(new FastMap()).shared();
/*  47 */   protected Map<String, Effect> abnormalEffectMap = (Map<String, Effect>)(new FastMap()).shared();
/*     */   
/*     */   protected int abnormals;
/*     */ 
/*     */   
/*     */   public EffectController(Creature owner) {
/*  53 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getOwner() {
/*  61 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEffect(Effect effect) {
/*  70 */     Map<String, Effect> mapToUpdate = getMapForEffect(effect);
/*     */     
/*  72 */     Effect existingEffect = mapToUpdate.get(effect.getStack());
/*  73 */     if (existingEffect != null) {
/*     */ 
/*     */       
/*  76 */       if (existingEffect.getSkillStackLvl() > effect.getSkillStackLvl()) {
/*     */         return;
/*     */       }
/*  79 */       if (existingEffect.getSkillStackLvl() == effect.getSkillStackLvl() && existingEffect.getSkillLevel() > effect.getSkillLevel()) {
/*     */         return;
/*     */       }
/*     */       
/*  83 */       existingEffect.endEffect();
/*     */     } 
/*     */     
/*  86 */     if (effect.isToggle() && mapToUpdate.size() >= 3) {
/*     */       
/*  88 */       Iterator<Effect> iter = mapToUpdate.values().iterator();
/*  89 */       Effect nextEffect = iter.next();
/*  90 */       nextEffect.endEffect();
/*  91 */       iter.remove();
/*     */     } 
/*     */     
/*  94 */     mapToUpdate.put(effect.getStack(), effect);
/*  95 */     effect.startEffect(false);
/*     */     
/*  97 */     if (!effect.isPassive())
/*     */     {
/*  99 */       broadCastEffects();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, Effect> getMapForEffect(Effect effect) {
/* 110 */     if (effect.isPassive()) {
/* 111 */       return this.passiveEffectMap;
/*     */     }
/* 113 */     if (effect.isToggle()) {
/* 114 */       return this.noshowEffects;
/*     */     }
/* 116 */     return this.abnormalEffectMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Effect getAnormalEffect(String stack) {
/* 126 */     return this.abnormalEffectMap.get(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadCastEffects() {
/* 131 */     this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.BROAD_CAST_EFFECTS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadCastEffectsImp() {
/* 139 */     List<Effect> effects = getAbnormalEffects();
/* 140 */     PacketSendUtility.broadcastPacket((VisibleObject)getOwner(), (AionServerPacket)new SM_ABNORMAL_EFFECT(getOwner().getObjectId(), this.abnormals, effects));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEffectIconsTo(Player player) {
/* 151 */     List<Effect> effects = getAbnormalEffects();
/* 152 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ABNORMAL_EFFECT(getOwner().getObjectId(), this.abnormals, effects));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearEffect(Effect effect) {
/* 162 */     this.abnormalEffectMap.remove(effect.getStack());
/* 163 */     broadCastEffects();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEffect(int skillid) {
/* 173 */     for (Effect effect : this.abnormalEffectMap.values()) {
/* 174 */       if (effect.getSkillId() == skillid) {
/* 175 */         effect.endEffect();
/* 176 */         this.abnormalEffectMap.remove(effect.getStack());
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
/*     */   public void removeEffectBySetNumber(int setNumber) {
/* 188 */     for (Effect effect : this.abnormalEffectMap.values()) {
/*     */       
/* 190 */       if (effect.getSkillSetException() == setNumber) {
/*     */         
/* 192 */         effect.endEffect();
/* 193 */         this.abnormalEffectMap.remove(effect.getStack());
/*     */       } 
/*     */     } 
/* 196 */     for (Effect effect : this.passiveEffectMap.values()) {
/*     */       
/* 198 */       if (effect.getSkillSetException() == setNumber) {
/*     */         
/* 200 */         effect.endEffect();
/* 201 */         this.passiveEffectMap.remove(effect.getStack());
/*     */       } 
/*     */     } 
/* 204 */     for (Effect effect : this.noshowEffects.values()) {
/*     */       
/* 206 */       if (effect.getSkillSetException() == setNumber) {
/*     */         
/* 208 */         effect.endEffect();
/* 209 */         this.noshowEffects.remove(effect.getStack());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEffectWithSetNumberReserved() {
/* 220 */     removeEffectBySetNumber(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEffectByEffectId(int effectId) {
/* 229 */     for (Effect effect : this.abnormalEffectMap.values()) {
/* 230 */       if (effect.containsEffectId(effectId)) {
/* 231 */         effect.endEffect();
/* 232 */         this.abnormalEffectMap.remove(effect.getStack());
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
/*     */   public void removeEffectByTargetSlot(SkillTargetSlot targetSlot, int count) {
/* 244 */     for (Effect effect : this.abnormalEffectMap.values()) {
/*     */       
/* 246 */       if (count == 0) {
/*     */         break;
/*     */       }
/* 249 */       if (effect.getTargetSlot() == targetSlot.ordinal()) {
/*     */         
/* 251 */         effect.endEffect();
/* 252 */         this.abnormalEffectMap.remove(effect.getStack());
/* 253 */         count--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEffectBySkillType(SkillType skillType, int value) {
/* 264 */     for (Effect effect : this.abnormalEffectMap.values()) {
/*     */       
/* 266 */       if (value == 0) {
/*     */         break;
/*     */       }
/* 269 */       if (effect.getSkillType() == skillType) {
/*     */         
/* 271 */         effect.endEffect();
/* 272 */         this.abnormalEffectMap.remove(effect.getStack());
/* 273 */         value--;
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
/*     */   
/*     */   public void removeEffectBySkillTypeAndTargetSlot(SkillType skillType, SkillTargetSlot targetSlot, int value) {
/* 286 */     for (Effect effect : this.abnormalEffectMap.values()) {
/*     */       
/* 288 */       if (value == 0) {
/*     */         break;
/*     */       }
/* 291 */       if (effect.getSkillType() == skillType && effect.getTargetSlot() == targetSlot.ordinal()) {
/*     */         
/* 293 */         effect.endEffect();
/* 294 */         this.abnormalEffectMap.remove(effect.getStack());
/* 295 */         value--;
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
/*     */   public void removePassiveEffect(int skillid) {
/* 307 */     for (Effect effect : this.passiveEffectMap.values()) {
/* 308 */       if (effect.getSkillId() == skillid) {
/* 309 */         effect.endEffect();
/* 310 */         this.passiveEffectMap.remove(effect.getStack());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNoshowEffect(int skillid) {
/* 321 */     for (Effect effect : this.noshowEffects.values()) {
/* 322 */       if (effect.getSkillId() == skillid) {
/* 323 */         effect.endEffect();
/* 324 */         this.noshowEffects.remove(effect.getStack());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAbnormalEffectsByTargetSlot(SkillTargetSlot targetSlot) {
/* 335 */     for (Effect effect : this.abnormalEffectMap.values()) {
/* 336 */       if (effect.getTargetSlot() == targetSlot.ordinal()) {
/* 337 */         effect.endEffect();
/* 338 */         this.abnormalEffectMap.remove(effect.getStack());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllEffects() {
/* 348 */     for (Effect effect : this.abnormalEffectMap.values())
/*     */     {
/* 350 */       effect.endEffect();
/*     */     }
/* 352 */     this.abnormalEffectMap.clear();
/* 353 */     for (Effect effect : this.noshowEffects.values())
/*     */     {
/* 355 */       effect.endEffect();
/*     */     }
/* 357 */     this.noshowEffects.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEffectIcons() {
/* 362 */     getOwner().addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_EFFECT_ICONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEffectIconsImpl() {
/* 367 */     List<Effect> effects = getAbnormalEffects();
/*     */     
/* 369 */     PacketSendUtility.sendPacket((Player)this.owner, (AionServerPacket)new SM_ABNORMAL_STATE(effects, this.abnormals));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Effect> getAbnormalEffects() {
/* 378 */     List<Effect> effects = new ArrayList<Effect>();
/* 379 */     Iterator<Effect> iterator = iterator();
/* 380 */     while (iterator.hasNext()) {
/*     */       
/* 382 */       Effect effect = iterator.next();
/* 383 */       if (effect != null)
/* 384 */         effects.add(effect); 
/*     */     } 
/* 386 */     return effects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAbnormal(int mask) {
/* 395 */     this.abnormals |= mask;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsetAbnormal(int mask) {
/* 400 */     this.abnormals &= mask ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbnoramlSet(EffectId effectId) {
/* 411 */     return ((this.abnormals & effectId.getEffectId()) == effectId.getEffectId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAbnormalState(EffectId effectId) {
/* 422 */     int state = this.abnormals & effectId.getEffectId();
/* 423 */     return (state > 0 && state <= effectId.getEffectId());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAbnormals() {
/* 428 */     return this.abnormals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Effect> iterator() {
/* 437 */     return this.abnormalEffectMap.values().iterator();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\effect\EffectController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */