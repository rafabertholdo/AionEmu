/*    */ package com.aionemu.gameserver.model.templates.item;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ @XmlAccessorType(XmlAccessType.NONE)
/*    */ @XmlRootElement(name = "Stigma")
/*    */ public class Stigma
/*    */ {
/*    */   @XmlElement(name = "require_skill")
/*    */   protected List<RequireSkill> requireSkill;
/*    */   @XmlAttribute
/*    */   protected int skillid;
/*    */   @XmlAttribute
/*    */   protected int skilllvl;
/*    */   @XmlAttribute
/*    */   protected int shard;
/*    */   
/*    */   public int getSkillid() {
/* 49 */     return this.skillid;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSkilllvl() {
/* 56 */     return this.skilllvl;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getShard() {
/* 63 */     return this.shard;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<RequireSkill> getRequireSkill() {
/* 68 */     if (this.requireSkill == null) {
/* 69 */       this.requireSkill = new ArrayList<RequireSkill>();
/*    */     }
/* 71 */     return this.requireSkill;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\Stigma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */