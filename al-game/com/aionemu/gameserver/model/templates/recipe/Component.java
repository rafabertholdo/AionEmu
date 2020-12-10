/*    */ package com.aionemu.gameserver.model.templates.recipe;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "Component")
/*    */ public class Component
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int itemid;
/*    */   @XmlAttribute
/*    */   protected int quantity;
/*    */   
/*    */   public Integer getItemid() {
/* 42 */     return Integer.valueOf(this.itemid);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getQuantity() {
/* 49 */     return Integer.valueOf(this.quantity);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\recipe\Component.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */