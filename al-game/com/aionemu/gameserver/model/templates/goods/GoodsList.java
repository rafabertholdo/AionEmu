/*     */ package com.aionemu.gameserver.model.templates.goods;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "GoodsList")
/*     */ public class GoodsList
/*     */ {
/*     */   protected List<Item> item;
/*     */   @XmlAttribute
/*     */   protected int id;
/*     */   protected List<Integer> itemIdList;
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  45 */     this.itemIdList = new ArrayList<Integer>();
/*     */     
/*  47 */     if (this.item == null) {
/*     */       return;
/*     */     }
/*  50 */     for (Item it : this.item)
/*     */     {
/*  52 */       this.itemIdList.add(Integer.valueOf(it.getId()));
/*     */     }
/*  54 */     this.item = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  61 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> getItemIdList() {
/*  70 */     return this.itemIdList;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlAccessorType(XmlAccessType.FIELD)
/*     */   @XmlType(name = "")
/*     */   public static class Item
/*     */   {
/*     */     @XmlAttribute
/*     */     protected int id;
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
/*     */     public int getId() {
/* 105 */       return this.id;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\goods\GoodsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */