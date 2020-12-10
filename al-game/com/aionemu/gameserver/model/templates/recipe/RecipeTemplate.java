/*     */ package com.aionemu.gameserver.model.templates.recipe;
/*     */ 
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillRace;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ @XmlType(name = "RecipeTemplate")
/*     */ public class RecipeTemplate
/*     */ {
/*     */   protected List<Component> component;
/*     */   protected List<ComboProduct> comboproduct;
/*     */   @XmlAttribute
/*     */   protected int tasktype;
/*     */   @XmlAttribute
/*     */   protected int componentquantity;
/*     */   @XmlAttribute
/*     */   protected int quantity;
/*     */   @XmlAttribute
/*     */   protected int productid;
/*     */   @XmlAttribute
/*     */   protected int autolearn;
/*     */   @XmlAttribute
/*     */   protected int dp;
/*     */   @XmlAttribute
/*     */   protected int skillpoint;
/*     */   @XmlAttribute
/*     */   protected SkillRace race;
/*     */   @XmlAttribute
/*     */   protected int skillid;
/*     */   @XmlAttribute
/*     */   protected int itemid;
/*     */   @XmlAttribute
/*     */   protected int nameid;
/*     */   @XmlAttribute
/*     */   protected int id;
/*     */   
/*     */   public List<Component> getComponent() {
/*  88 */     if (this.component == null) {
/*  89 */       this.component = new ArrayList<Component>();
/*     */     }
/*  91 */     return this.component;
/*     */   }
/*     */   
/*     */   public Integer getComboProduct() {
/*  95 */     if (this.comboproduct == null)
/*     */     {
/*  97 */       return null;
/*     */     }
/*  99 */     return Integer.valueOf(((ComboProduct)this.comboproduct.get(0)).getItemid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTasktype() {
/* 110 */     return Integer.valueOf(this.tasktype);
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
/*     */   public Integer getComponentquantity() {
/* 122 */     return Integer.valueOf(this.componentquantity);
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
/*     */   public Integer getQuantity() {
/* 134 */     return Integer.valueOf(this.quantity);
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
/*     */   public Integer getProductid() {
/* 146 */     return Integer.valueOf(this.productid);
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
/*     */   public Integer getAutolearn() {
/* 158 */     return Integer.valueOf(this.autolearn);
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
/*     */   public Integer getDp() {
/* 170 */     return Integer.valueOf(this.dp);
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
/*     */   public Integer getSkillpoint() {
/* 182 */     return Integer.valueOf(this.skillpoint);
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
/*     */   public SkillRace getRace() {
/* 194 */     return this.race;
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
/*     */   public Integer getSkillid() {
/* 206 */     return Integer.valueOf(this.skillid);
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
/*     */   public Integer getItemid() {
/* 218 */     return Integer.valueOf(this.itemid);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameid() {
/* 226 */     return this.nameid;
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
/*     */   public Integer getId() {
/* 238 */     return Integer.valueOf(this.id);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\recipe\RecipeTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */