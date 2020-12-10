/*    */ package com.aionemu.gameserver.model.templates.gather;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
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
/*    */ @XmlType(name = "Materials", propOrder = {"material"})
/*    */ public class Materials
/*    */ {
/*    */   protected List<Material> material;
/*    */   
/*    */   public List<Material> getMaterial() {
/* 43 */     if (this.material == null) {
/* 44 */       this.material = new ArrayList<Material>();
/*    */     }
/* 46 */     return this.material;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\gather\Materials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */