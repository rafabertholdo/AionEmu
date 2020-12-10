/*     */ package com.aionemu.gameserver.model.templates;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.gather.Materials;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
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
/*     */ @XmlRootElement(name = "gatherable_template")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class GatherableTemplate
/*     */   extends VisibleObjectTemplate
/*     */ {
/*     */   @XmlElement(required = true)
/*     */   protected Materials materials;
/*     */   @XmlAttribute
/*     */   protected int aerialAdj;
/*     */   @XmlAttribute
/*     */   protected int failureAdj;
/*     */   @XmlAttribute
/*     */   protected int successAdj;
/*     */   @XmlAttribute
/*     */   protected int harvestSkill;
/*     */   @XmlAttribute
/*     */   protected int skillLevel;
/*     */   @XmlAttribute
/*     */   protected int harvestCount;
/*     */   @XmlAttribute
/*     */   protected String sourceType;
/*     */   @XmlAttribute
/*     */   protected int nameId;
/*     */   @XmlAttribute
/*     */   protected String name;
/*     */   @XmlAttribute
/*     */   protected String desc;
/*     */   @XmlAttribute
/*     */   protected int id;
/*     */   
/*     */   public Materials getMaterials() {
/*  70 */     return this.materials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTemplateId() {
/*  78 */     return this.id;
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
/*     */   public int getAerialAdj() {
/*  90 */     return this.aerialAdj;
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
/*     */   public int getFailureAdj() {
/* 102 */     return this.failureAdj;
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
/*     */   public int getSuccessAdj() {
/* 114 */     return this.successAdj;
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
/*     */   public int getHarvestSkill() {
/* 126 */     return this.harvestSkill;
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
/*     */   public int getSkillLevel() {
/* 138 */     return this.skillLevel;
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
/*     */   public int getHarvestCount() {
/* 150 */     return this.harvestCount;
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
/*     */   public String getSourceType() {
/* 162 */     return this.sourceType;
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
/*     */   public String getName() {
/* 175 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameId() {
/* 184 */     return this.nameId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDesc() {
/* 191 */     return this.desc;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\GatherableTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */