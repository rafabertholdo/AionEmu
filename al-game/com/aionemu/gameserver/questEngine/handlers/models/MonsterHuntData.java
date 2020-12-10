/*    */ package com.aionemu.gameserver.questEngine.handlers.models;
/*    */ 
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*    */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*    */ import com.aionemu.gameserver.questEngine.handlers.template.MonsterHunt;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlElement;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ import javolution.util.FastMap;
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
/*    */ @XmlType(name = "MonsterHuntData", propOrder = {"monsterInfos"})
/*    */ public class MonsterHuntData
/*    */   extends QuestScriptData
/*    */ {
/*    */   @XmlElement(name = "monster_infos", required = true)
/*    */   protected List<MonsterInfo> monsterInfos;
/*    */   @XmlAttribute(name = "start_npc_id")
/*    */   protected int startNpcId;
/*    */   @XmlAttribute(name = "end_npc_id")
/*    */   protected int endNpcId;
/*    */   
/*    */   public void register(QuestEngine questEngine) {
/* 51 */     FastMap<Integer, MonsterInfo> monsterInfo = new FastMap();
/* 52 */     for (MonsterInfo mi : this.monsterInfos)
/* 53 */       monsterInfo.put(Integer.valueOf(mi.getNpcId()), mi); 
/* 54 */     MonsterHunt template = new MonsterHunt(this.id, this.startNpcId, this.endNpcId, monsterInfo);
/* 55 */     questEngine.addQuestHandler((QuestHandler)template);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\MonsterHuntData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */