/*     */ package com.aionemu.gameserver.skillengine.properties;
/*     */ 
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Trap;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "TargetRangeProperty")
/*     */ public class TargetRangeProperty
/*     */   extends Property
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(TargetRangeProperty.class);
/*     */ 
/*     */   
/*     */   @XmlAttribute(required = true)
/*     */   protected TargetRangeAttribute value;
/*     */ 
/*     */   
/*     */   @XmlAttribute
/*     */   protected int distance;
/*     */ 
/*     */   
/*     */   @XmlAttribute
/*     */   protected int maxcount;
/*     */ 
/*     */   
/*     */   public TargetRangeAttribute getValue() {
/*  62 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(Skill skill) {
/*     */     Creature firstTarget;
/*  68 */     List<Creature> effectedList = skill.getEffectedList();
/*  69 */     int counter = 0;
/*  70 */     switch (this.value) {
/*     */ 
/*     */ 
/*     */       
/*     */       case AREA:
/*  75 */         firstTarget = skill.getFirstTarget();
/*  76 */         if (firstTarget == null) {
/*     */           
/*  78 */           log.warn("CHECKPOINT: first target is null for skillid " + skill.getSkillTemplate().getSkillId());
/*  79 */           return false;
/*     */         } 
/*     */         
/*  82 */         for (VisibleObject nextCreature : firstTarget.getKnownList().getKnownObjects().values()) {
/*     */           
/*  84 */           if (counter >= this.maxcount) {
/*     */             break;
/*     */           }
/*     */           
/*  88 */           if (firstTarget == nextCreature) {
/*     */             continue;
/*     */           }
/*     */           
/*  92 */           if (skill.getEffector() instanceof Trap && ((Trap)skill.getEffector()).getCreator() == nextCreature) {
/*     */             continue;
/*     */           }
/*     */           
/*  96 */           if (nextCreature instanceof Creature && MathUtil.isIn3dRange((VisibleObject)firstTarget, nextCreature, (this.distance + 4))) {
/*     */ 
/*     */             
/*  99 */             effectedList.add((Creature)nextCreature);
/* 100 */             counter++;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       case PARTY:
/* 105 */         if (skill.getEffector() instanceof Player) {
/*     */           
/* 107 */           Player effector = (Player)skill.getEffector();
/* 108 */           if (effector.isInAlliance()) {
/*     */             
/* 110 */             effectedList.clear();
/* 111 */             for (PlayerAllianceMember allianceMember : effector.getPlayerAlliance().getMembersForGroup(effector.getObjectId())) {
/*     */               
/* 113 */               if (!allianceMember.isOnline())
/* 114 */                 continue;  Player member = allianceMember.getPlayer();
/* 115 */               if (MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)member, (this.distance + 4)))
/* 116 */                 effectedList.add(member); 
/*     */             }  break;
/*     */           } 
/* 119 */           if (effector.isInGroup()) {
/*     */             
/* 121 */             effectedList.clear();
/* 122 */             for (Player member : effector.getPlayerGroup().getMembers()) {
/*     */ 
/*     */               
/* 125 */               if (member != null && MathUtil.isIn3dRange((VisibleObject)effector, (VisibleObject)member, (this.distance + 4))) {
/* 126 */                 effectedList.add(member);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\TargetRangeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */