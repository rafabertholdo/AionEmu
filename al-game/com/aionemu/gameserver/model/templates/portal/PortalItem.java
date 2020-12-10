/*    */ package com.aionemu.gameserver.model.templates.portal;
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
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "PortalItem")
/*    */ public class PortalItem
/*    */ {
/*    */   @XmlAttribute(name = "id")
/*    */   protected int id;
/*    */   @XmlAttribute(name = "itemid")
/*    */   protected int itemid;
/*    */   @XmlAttribute(name = "quantity")
/*    */   protected int quantity;
/*    */   
/*    */   public int getId() {
/* 45 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemid() {
/* 53 */     return this.itemid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuantity() {
/* 61 */     return this.quantity;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\portal\PortalItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */