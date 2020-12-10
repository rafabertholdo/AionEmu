/*    */ package com.aionemu.gameserver.dataholders;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.QuestTemplate;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlRootElement(name = "quests")
/*    */ public class QuestsData
/*    */ {
/*    */   @XmlElement(name = "quest", required = true)
/*    */   protected List<QuestTemplate> questsData;
/* 43 */   private TIntObjectHashMap<QuestTemplate> questData = new TIntObjectHashMap();
/*    */ 
/*    */   
/*    */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 47 */     this.questData.clear();
/* 48 */     for (QuestTemplate quest : this.questsData)
/*    */     {
/* 50 */       this.questData.put(quest.getId(), quest);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public QuestTemplate getQuestById(int id) {
/* 56 */     return (QuestTemplate)this.questData.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 61 */     return this.questData.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<QuestTemplate> getQuestsData() {
/* 69 */     return this.questsData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setQuestsData(List<QuestTemplate> questsData) {
/* 77 */     this.questsData = questsData;
/* 78 */     afterUnmarshal(null, null);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\QuestsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */