/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.petskill.PetSkillTemplate;
/*    */ import gnu.trove.TIntIntHashMap;
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
/*    */ @XmlRootElement(name = "pet_skill_templates")
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ public class PetSkillData
/*    */ {
/*    */   @XmlElement(name = "pet_skill")
/*    */   private List<PetSkillTemplate> petSkills;
/* 44 */   private TIntObjectHashMap<TIntIntHashMap> petSkillData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 48 */     for (PetSkillTemplate petSkill : this.petSkills) {
/*    */       
/* 50 */       TIntIntHashMap orderSkillMap = (TIntIntHashMap)this.petSkillData.get(petSkill.getOrderSkill());
/* 51 */       if (orderSkillMap == null) {
/*    */         
/* 53 */         orderSkillMap = new TIntIntHashMap();
/* 54 */         this.petSkillData.put(petSkill.getOrderSkill(), orderSkillMap);
/*    */       } 
/*    */       
/* 57 */       orderSkillMap.put(petSkill.getPetId(), petSkill.getSkillId());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 63 */     return this.petSkillData.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPetOrderSkill(int orderSkill, int petNpcId) {
/* 68 */     return ((TIntIntHashMap)this.petSkillData.get(orderSkill)).get(petNpcId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\PetSkillData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */