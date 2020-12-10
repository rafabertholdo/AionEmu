/*    */ package com.aionemu.gameserver.model.templates.siegelocation;
/*    */ 
/*    */ import com.aionemu.gameserver.model.siege.SiegeType;
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
/*    */ @XmlType(name = "siegelocation")
/*    */ public class SiegeLocationTemplate
/*    */ {
/*    */   @XmlAttribute(name = "id")
/*    */   protected int id;
/*    */   @XmlAttribute(name = "type")
/*    */   protected SiegeType type;
/*    */   @XmlAttribute(name = "world")
/*    */   protected int world;
/*    */   
/*    */   public int getId() {
/* 46 */     return this.id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SiegeType getType() {
/* 54 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWorldId() {
/* 62 */     return this.world;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\siegelocation\SiegeLocationTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */