/*    */ package com.aionemu.gameserver.model.templates.tribe;
/*    */ 
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
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
/*    */ @XmlType(name = "Tribe")
/*    */ public class Tribe
/*    */ {
/*    */   public static final String GUARD_DARK = "GUARD_DARK";
/*    */   public static final String GUARD_LIGHT = "GUARD";
/*    */   @XmlElement(name = "aggro")
/*    */   protected AggroRelations aggroRelations;
/*    */   @XmlElement(name = "friend")
/*    */   protected FriendlyRelations friendlyRelations;
/*    */   @XmlElement(name = "support")
/*    */   protected SupportRelations supportRelations;
/*    */   @XmlElement(name = "neutral")
/*    */   protected NeutralRelations neutralRelations;
/*    */   @XmlElement(name = "hostile")
/*    */   protected HostileRelations hostileRelations;
/*    */   @XmlAttribute(required = true)
/*    */   protected String name;
/*    */   @XmlAttribute
/*    */   protected String base;
/*    */   
/*    */   public AggroRelations getAggroRelations() {
/* 55 */     return this.aggroRelations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SupportRelations getSupportRelations() {
/* 62 */     return this.supportRelations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FriendlyRelations getFriendlyRelations() {
/* 69 */     return this.friendlyRelations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NeutralRelations getNeutralRelations() {
/* 77 */     return this.neutralRelations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HostileRelations getHostileRelations() {
/* 84 */     return this.hostileRelations;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 91 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBase() {
/* 98 */     return this.base;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\tribe\Tribe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */