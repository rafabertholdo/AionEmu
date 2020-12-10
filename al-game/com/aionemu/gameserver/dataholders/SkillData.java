/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.Unmarshaller;
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
/*    */ 
/*    */ @XmlRootElement(name = "skill_data")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class SkillData
/*    */ {
/*    */   @XmlElement(name = "skill_template")
/*    */   private List<SkillTemplate> skillTemplates;
/* 44 */   private TIntObjectHashMap<SkillTemplate> skillData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 48 */     this.skillData.clear();
/* 49 */     for (SkillTemplate skillTempalte : this.skillTemplates)
/*    */     {
/* 51 */       this.skillData.put(skillTempalte.getSkillId(), skillTempalte);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SkillTemplate getSkillTemplate(int skillId) {
/* 61 */     return (SkillTemplate)this.skillData.get(skillId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int size() {
/* 69 */     return this.skillData.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<SkillTemplate> getSkillTemplates() {
/* 77 */     return this.skillTemplates;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSkillTemplates(List<SkillTemplate> skillTemplates) {
/* 85 */     this.skillTemplates = skillTemplates;
/* 86 */     afterUnmarshal(null, null);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SkillData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */