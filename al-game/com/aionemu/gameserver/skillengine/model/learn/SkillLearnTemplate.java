/*     */ package com.aionemu.gameserver.skillengine.model.learn;
/*     */ 
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "skill")
/*     */ public class SkillLearnTemplate
/*     */ {
/*     */   @XmlAttribute(name = "classId", required = true)
/*     */   private SkillClass classId;
/*     */   @XmlAttribute(name = "skillId", required = true)
/*     */   private int skillId;
/*     */   @XmlAttribute(name = "skillLevel", required = true)
/*     */   private int skillLevel;
/*     */   @XmlAttribute(name = "name", required = true)
/*     */   private String name;
/*     */   @XmlAttribute(name = "type", required = true)
/*     */   private SkillUsageType type;
/*     */   @XmlAttribute(name = "race", required = true)
/*     */   private SkillRace race;
/*     */   @XmlAttribute(name = "minLevel", required = true)
/*     */   private int minLevel;
/*     */   @XmlAttribute
/*     */   private boolean autolearn;
/*     */   @XmlAttribute
/*     */   private boolean stigma = false;
/*     */   
/*     */   public SkillClass getClassId() {
/*  57 */     return this.classId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillId() {
/*  64 */     return this.skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillLevel() {
/*  71 */     return this.skillLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  78 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillUsageType getType() {
/*  85 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinLevel() {
/*  92 */     return this.minLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillRace getRace() {
/*  99 */     return this.race;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutolearn() {
/* 106 */     return this.autolearn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStigma() {
/* 114 */     return this.stigma;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\learn\SkillLearnTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */