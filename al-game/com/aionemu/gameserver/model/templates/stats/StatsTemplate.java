/*     */ package com.aionemu.gameserver.model.templates.stats;
/*     */ 
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
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
/*     */ @XmlRootElement(name = "stats_template")
/*     */ public abstract class StatsTemplate
/*     */ {
/*     */   @XmlAttribute(name = "maxHp")
/*     */   private int maxHp;
/*     */   @XmlAttribute(name = "maxMp")
/*     */   private int maxMp;
/*     */   @XmlAttribute(name = "walk_speed")
/*     */   private float walkSpeed;
/*     */   @XmlAttribute(name = "run_speed")
/*     */   private float runSpeed;
/*     */   @XmlAttribute(name = "fly_speed")
/*     */   private float flySpeed;
/*     */   @XmlAttribute(name = "attack_speed")
/*     */   private float attackSpeed;
/*     */   @XmlAttribute(name = "evasion")
/*     */   private int evasion;
/*     */   @XmlAttribute(name = "block")
/*     */   private int block;
/*     */   @XmlAttribute(name = "parry")
/*     */   private int parry;
/*     */   @XmlAttribute(name = "main_hand_attack")
/*     */   private int mainHandAttack;
/*     */   @XmlAttribute(name = "main_hand_accuracy")
/*     */   private int mainHandAccuracy;
/*     */   @XmlAttribute(name = "main_hand_crit_rate")
/*     */   private int mainHandCritRate;
/*     */   @XmlAttribute(name = "magic_accuracy")
/*     */   private int magicAccuracy;
/*     */   
/*     */   public int getMaxHp() {
/*  71 */     return this.maxHp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxMp() {
/*  76 */     return this.maxMp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWalkSpeed() {
/*  84 */     return this.walkSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRunSpeed() {
/*  89 */     return this.runSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFlySpeed() {
/*  94 */     return this.flySpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAttackSpeed() {
/*  99 */     return this.attackSpeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEvasion() {
/* 106 */     return this.evasion;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlock() {
/* 111 */     return this.block;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParry() {
/* 116 */     return this.parry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMainHandAttack() {
/* 123 */     return this.mainHandAttack;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMainHandAccuracy() {
/* 128 */     return this.mainHandAccuracy;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMainHandCritRate() {
/* 133 */     return this.mainHandCritRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMagicAccuracy() {
/* 140 */     return this.magicAccuracy;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\stats\StatsTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */