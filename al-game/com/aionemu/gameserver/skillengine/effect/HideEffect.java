/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "HideEffect")
/*     */ public class HideEffect
/*     */   extends BufEffect
/*     */ {
/*     */   @XmlAttribute
/*     */   protected int value;
/*     */   
/*     */   public void applyEffect(Effect effect) {
/*  48 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/*  55 */     effect.addSucessEffect(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {
/*     */     CreatureVisualState visualState;
/*  61 */     super.endEffect(effect);
/*     */     
/*  63 */     Creature effected = effect.getEffected();
/*  64 */     effected.getEffectController().unsetAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());
/*     */ 
/*     */ 
/*     */     
/*  68 */     switch (this.value) {
/*     */       
/*     */       case 1:
/*  71 */         visualState = CreatureVisualState.HIDE1;
/*     */         break;
/*     */       case 2:
/*  74 */         visualState = CreatureVisualState.HIDE2;
/*     */         break;
/*     */       case 3:
/*  77 */         visualState = CreatureVisualState.HIDE3;
/*     */         break;
/*     */       case 10:
/*  80 */         visualState = CreatureVisualState.HIDE10;
/*     */         break;
/*     */       case 13:
/*  83 */         visualState = CreatureVisualState.HIDE13;
/*     */         break;
/*     */       case 20:
/*  86 */         visualState = CreatureVisualState.HIDE20;
/*     */         break;
/*     */       default:
/*  89 */         visualState = CreatureVisualState.VISIBLE;
/*     */         break;
/*     */     } 
/*  92 */     effected.unsetVisualState(visualState);
/*     */     
/*  94 */     if (effected instanceof Player)
/*     */     {
/*  96 */       PacketSendUtility.broadcastPacket((Player)effected, (AionServerPacket)new SM_PLAYER_STATE((Player)effected), true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void startEffect(final Effect effect) {
/*     */     CreatureVisualState visualState;
/* 103 */     super.startEffect(effect);
/*     */     
/* 105 */     final Creature effected = effect.getEffected();
/* 106 */     effected.getEffectController().setAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());
/*     */ 
/*     */ 
/*     */     
/* 110 */     switch (this.value) {
/*     */       
/*     */       case 1:
/* 113 */         visualState = CreatureVisualState.HIDE1;
/*     */         break;
/*     */       case 2:
/* 116 */         visualState = CreatureVisualState.HIDE2;
/*     */         break;
/*     */       case 3:
/* 119 */         visualState = CreatureVisualState.HIDE3;
/*     */         break;
/*     */       case 10:
/* 122 */         visualState = CreatureVisualState.HIDE10;
/*     */         break;
/*     */       case 13:
/* 125 */         visualState = CreatureVisualState.HIDE13;
/*     */         break;
/*     */       case 20:
/* 128 */         visualState = CreatureVisualState.HIDE20;
/*     */         break;
/*     */       default:
/* 131 */         visualState = CreatureVisualState.VISIBLE;
/*     */         break;
/*     */     } 
/* 134 */     effected.setVisualState(visualState);
/*     */     
/* 136 */     if (effected instanceof Player)
/*     */     {
/* 138 */       PacketSendUtility.broadcastPacket((Player)effected, (AionServerPacket)new SM_PLAYER_STATE((Player)effected), true);
/*     */     }
/*     */ 
/*     */     
/* 142 */     effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.SKILLUSE)
/*     */         {
/*     */ 
/*     */           
/*     */           public void skilluse(Skill skill)
/*     */           {
/* 148 */             effected.getEffectController().removeEffect(effect.getSkillId());
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 154 */     effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACKED)
/*     */         {
/*     */ 
/*     */           
/*     */           public void attacked(Creature creature)
/*     */           {
/* 160 */             effected.getEffectController().removeEffect(effect.getSkillId());
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 166 */     effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACK)
/*     */         {
/*     */ 
/*     */           
/*     */           public void attack(Creature creature)
/*     */           {
/* 172 */             effected.getEffectController().removeEffect(effect.getSkillId());
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\HideEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */