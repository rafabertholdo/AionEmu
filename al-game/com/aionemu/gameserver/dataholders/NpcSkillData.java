/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.npcskill.NpcSkillList;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlRootElement;
/*    */ import org.apache.log4j.Logger;
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
/*    */ @XmlRootElement(name = "npc_skill_templates")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class NpcSkillData
/*    */ {
/*    */   @XmlElement(name = "npcskills")
/*    */   private List<NpcSkillList> npcSkills;
/* 45 */   private TIntObjectHashMap<NpcSkillList> npcSkillData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 49 */     for (NpcSkillList npcSkill : this.npcSkills) {
/*    */       
/* 51 */       this.npcSkillData.put(npcSkill.getNpcId(), npcSkill);
/*    */       
/* 53 */       if (npcSkill.getNpcSkills() == null) {
/* 54 */         Logger.getLogger(NpcSkillData.class).error("NO SKILL");
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 61 */     return this.npcSkillData.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public NpcSkillList getNpcSkillList(int id) {
/* 66 */     return (NpcSkillList)this.npcSkillData.get(id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\NpcSkillData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */