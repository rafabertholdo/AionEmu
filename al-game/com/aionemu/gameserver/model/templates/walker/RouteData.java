/*    */ package com.aionemu.gameserver.model.templates.walker;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
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
/*    */ @XmlRootElement(name = "routes")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class RouteData
/*    */ {
/*    */   @XmlElement(name = "routestep")
/*    */   private List<RouteStep> stepids;
/*    */   
/*    */   public List<RouteStep> getRouteSteps() {
/* 39 */     return this.stepids;
/*    */   }
/*    */ 
/*    */   
/*    */   public RouteStep getRouteStep(int value) {
/* 44 */     for (RouteStep t : this.stepids) {
/*    */       
/* 46 */       if (t != null && t.getRouteStep() == value)
/*    */       {
/* 48 */         return t;
/*    */       }
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\walker\RouteData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */