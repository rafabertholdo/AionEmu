/*     */ package com.aionemu.gameserver.skillengine.properties;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Monster;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
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
/*     */ @XmlType(name = "FirstTargetProperty")
/*     */ public class FirstTargetProperty
/*     */   extends Property
/*     */ {
/*     */   @XmlAttribute(required = true)
/*     */   protected FirstTargetAttribute value;
/*     */   
/*     */   public FirstTargetAttribute getValue() {
/*  55 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(Skill skill) {
/*     */     Creature effector;
/*  61 */     switch (this.value) {
/*     */       
/*     */       case ME:
/*  64 */         skill.setFirstTarget(skill.getEffector());
/*     */         break;
/*     */       case TARGETORME:
/*  67 */         if (skill.getFirstTarget() == null) {
/*  68 */           skill.setFirstTarget(skill.getEffector()); break;
/*  69 */         }  if (skill.getFirstTarget() instanceof Monster) {
/*     */           
/*  71 */           Monster monsterEffected = (Monster)skill.getFirstTarget();
/*  72 */           Player playerEffector = (Player)skill.getEffector();
/*  73 */           if (monsterEffected.isEnemy((VisibleObject)playerEffector))
/*  74 */             skill.setFirstTarget(skill.getEffector());  break;
/*     */         } 
/*  76 */         if (skill.getFirstTarget() instanceof Player && skill.getEffector() instanceof Player) {
/*     */           
/*  78 */           Player playerEffected = (Player)skill.getFirstTarget();
/*  79 */           Player playerEffector = (Player)skill.getEffector();
/*  80 */           if (playerEffected.getCommonData().getRace().getRaceId() != playerEffector.getCommonData().getRace().getRaceId())
/*  81 */             skill.setFirstTarget(skill.getEffector());  break;
/*     */         } 
/*  83 */         if (skill.getFirstTarget() instanceof Npc) {
/*     */           
/*  85 */           Npc npcEffected = (Npc)skill.getFirstTarget();
/*  86 */           Player playerEffector = (Player)skill.getEffector();
/*  87 */           if (npcEffected.isEnemy((VisibleObject)playerEffector))
/*  88 */             skill.setFirstTarget(skill.getEffector());  break;
/*     */         } 
/*  90 */         if (skill.getFirstTarget() instanceof Summon && skill.getEffector() instanceof Player) {
/*     */           
/*  92 */           Summon summon = (Summon)skill.getFirstTarget();
/*  93 */           Player playerEffected = summon.getMaster();
/*  94 */           Player playerEffector = (Player)skill.getEffector();
/*  95 */           if (playerEffected.getCommonData().getRace().getRaceId() != playerEffector.getCommonData().getRace().getRaceId())
/*  96 */             skill.setFirstTarget(skill.getEffector()); 
/*     */         } 
/*     */         break;
/*     */       case TARGET:
/* 100 */         if (skill.getFirstTarget() == null)
/* 101 */           return false; 
/*     */         break;
/*     */       case MYPET:
/* 104 */         effector = skill.getEffector();
/* 105 */         if (effector instanceof Player) {
/*     */           
/* 107 */           Summon summon = ((Player)effector).getSummon();
/* 108 */           if (summon != null) {
/* 109 */             skill.setFirstTarget((Creature)summon); break;
/*     */           } 
/* 111 */           return false;
/*     */         } 
/*     */ 
/*     */         
/* 115 */         return false;
/*     */ 
/*     */       
/*     */       case PASSIVE:
/* 119 */         skill.setFirstTarget(skill.getEffector());
/*     */         break;
/*     */       
/*     */       case POINT:
/* 123 */         skill.setFirstTargetRangeCheck(false);
/* 124 */         return true;
/*     */     } 
/*     */     
/* 127 */     if (skill.getFirstTarget() != null)
/* 128 */       skill.getEffectedList().add(skill.getFirstTarget()); 
/* 129 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\FirstTargetProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */