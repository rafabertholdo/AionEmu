/*    */ package com.aionemu.gameserver.model.templates;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.expand.Expand;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ @XmlRootElement(name = "cube_npc")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class CubeExpandTemplate
/*    */ {
/*    */   @XmlElement(name = "expand", required = true)
/*    */   protected List<Expand> cubeExpands;
/*    */   @XmlAttribute(name = "id", required = true)
/*    */   private int Id;
/*    */   @XmlAttribute(name = "name", required = true)
/* 32 */   private String name = "";
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNpcId() {
/* 42 */     return this.Id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean contains(int level) {
/* 51 */     for (Expand expand : this.cubeExpands) {
/*    */       
/* 53 */       if (expand.getLevel() == level)
/* 54 */         return true; 
/*    */     } 
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Expand get(int level) {
/* 65 */     for (Expand expand : this.cubeExpands) {
/*    */       
/* 67 */       if (expand.getLevel() == level)
/* 68 */         return expand; 
/*    */     } 
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\CubeExpandTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */