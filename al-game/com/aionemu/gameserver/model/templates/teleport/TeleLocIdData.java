/*    */ package com.aionemu.gameserver.model.templates.teleport;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlRootElement(name = "locations")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class TeleLocIdData
/*    */ {
/*    */   @XmlElement(name = "telelocation")
/*    */   private List<TeleportLocation> locids;
/*    */   
/*    */   public List<TeleportLocation> getTelelocations() {
/* 42 */     return this.locids;
/*    */   }
/*    */ 
/*    */   
/*    */   public TeleportLocation getTeleportLocation(int value) {
/* 47 */     for (TeleportLocation t : this.locids) {
/*    */       
/* 49 */       if (t != null && t.getLocId() == value)
/*    */       {
/* 51 */         return t;
/*    */       }
/*    */     } 
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TeleLocIdData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */