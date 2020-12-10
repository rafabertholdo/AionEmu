/*    */ package com.aionemu.gameserver.model.templates.npcskill;
/*    */ 
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "npcskills")
/*    */ public class NpcSkillList
/*    */ {
/*    */   @XmlAttribute(name = "npcid")
/*    */   protected int npcId;
/*    */   @XmlAttribute(name = "skill_count")
/*    */   protected int count;
/*    */   @XmlElement(name = "npcskill")
/*    */   protected List<NpcSkillTemplate> npcSkills;
/*    */   
/*    */   public int getNpcId() {
/* 47 */     return this.npcId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 55 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<NpcSkillTemplate> getNpcSkills() {
/* 63 */     return this.npcSkills;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\npcskill\NpcSkillList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */