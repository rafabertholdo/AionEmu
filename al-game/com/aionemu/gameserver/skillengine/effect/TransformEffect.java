/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
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
/*     */ @XmlType(name = "TransformEffect")
/*     */ public class TransformEffect
/*     */   extends EffectTemplate
/*     */ {
/*     */   @XmlAttribute
/*     */   protected int model;
/*     */   
/*     */   public void applyEffect(Effect effect) {
/*  45 */     Creature effected = effect.getEffected();
/*  46 */     boolean transformed = false;
/*  47 */     if (effected instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
/*     */       
/*  49 */       transformed = (effected.getTransformedModelId() == effected.getObjectTemplate().getTemplateId());
/*     */     }
/*  51 */     else if (effected instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
/*     */       
/*  53 */       transformed = (effected.getTransformedModelId() != 0);
/*     */     } 
/*  55 */     if (transformed)
/*     */     {
/*  57 */       for (Effect tmp : effected.getEffectController().getAbnormalEffects()) {
/*     */         
/*  59 */         if (effect.getSkillId() == tmp.getSkillId())
/*     */           continue; 
/*  61 */         boolean abort = false;
/*  62 */         for (EffectTemplate template : tmp.getEffectTemplates()) {
/*     */           
/*  64 */           if (template instanceof TransformEffect) {
/*     */             
/*  66 */             abort = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*  70 */         if (abort)
/*  71 */           tmp.endEffect(); 
/*     */       } 
/*     */     }
/*  74 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/*  81 */     effect.addSucessEffect(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {
/*  87 */     Creature effected = effect.getEffected();
/*  88 */     effected.getEffectController().unsetAbnormal(EffectId.SHAPECHANGE.getEffectId());
/*     */     
/*  90 */     if (effected instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
/*     */       
/*  92 */       effected.setTransformedModelId(effected.getObjectTemplate().getTemplateId());
/*     */     }
/*  94 */     else if (effected instanceof com.aionemu.gameserver.model.gameobjects.player.Player) {
/*     */       
/*  96 */       effected.setTransformedModelId(0);
/*     */     } 
/*  98 */     PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_TRANSFORM(effected));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEffect(Effect effect) {
/* 104 */     Creature effected = effect.getEffected();
/* 105 */     switch (effect.getSkillId()) {
/*     */       case 689:
/*     */       case 690:
/*     */       case 780:
/*     */       case 781:
/*     */       case 782:
/*     */       case 789:
/*     */       case 790:
/*     */       case 791:
/*     */       case 9737:
/*     */       case 9738:
/*     */       case 9739:
/*     */       case 9740:
/*     */       case 9741:
/*     */       case 9742:
/*     */       case 9743:
/*     */       case 9744:
/*     */       case 9745:
/*     */       case 9746:
/*     */         break;
/*     */       
/*     */       default:
/* 127 */         effected.getEffectController().setAbnormal(EffectId.SHAPECHANGE.getEffectId()); break;
/*     */     } 
/* 129 */     effected.setTransformedModelId(this.model);
/* 130 */     PacketSendUtility.broadcastPacketAndReceive((VisibleObject)effected, (AionServerPacket)new SM_TRANSFORM(effected));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\TransformEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */