/*    */ package com.aionemu.gameserver.model.templates.zone;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "Points")
/*    */ public class Points
/*    */ {
/*    */   @XmlElement(required = true)
/*    */   protected List<Point2D> point;
/*    */   @XmlAttribute(name = "top")
/*    */   protected float top;
/*    */   @XmlAttribute(name = "bottom")
/*    */   protected float bottom;
/*    */   @XmlAttribute
/*    */   protected String type;
/*    */   
/*    */   public List<Point2D> getPoint() {
/* 52 */     if (this.point == null)
/*    */     {
/* 54 */       this.point = new ArrayList<Point2D>();
/*    */     }
/* 56 */     return this.point;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getTop() {
/* 64 */     return this.top;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBottom() {
/* 72 */     return this.bottom;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getType() {
/* 80 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\zone\Points.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */