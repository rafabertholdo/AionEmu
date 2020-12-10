/*     */ package com.aionemu.gameserver.model.templates;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.expand.Expand;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import java.util.List;
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
/*     */ @XmlRootElement(name = "warehouse_npc")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class WarehouseExpandTemplate
/*     */ {
/*     */   @XmlElement(name = "expand", required = true)
/*     */   protected List<Expand> warehouseExpands;
/*     */   @XmlAttribute(name = "id", required = true)
/*     */   protected int id;
/*     */   @XmlAttribute(name = "name", required = true)
/*  49 */   protected String name = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNpcId() {
/*  54 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Expand> getWarehouseExpand() {
/*  62 */     return this.warehouseExpands;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  73 */     return Util.convertName(this.name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int level) {
/*  82 */     for (Expand expand : this.warehouseExpands) {
/*     */       
/*  84 */       if (expand.getLevel() == level)
/*  85 */         return true; 
/*     */     } 
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Expand get(int level) {
/*  96 */     for (Expand expand : this.warehouseExpands) {
/*     */       
/*  98 */       if (expand.getLevel() == level)
/*  99 */         return expand; 
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\WarehouseExpandTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */