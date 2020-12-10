/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.MonsterInfo;
/*    */ import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations.QuestOperations;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "OnKillEvent", propOrder = {"monsterInfos", "complite"})
/*    */ public class OnKillEvent
/*    */   extends QuestEvent
/*    */ {
/*    */   @XmlElement(name = "monster_infos")
/*    */   protected List<MonsterInfo> monsterInfos;
/*    */   protected QuestOperations complite;
/*    */   
/*    */   public List<MonsterInfo> getMonsterInfos() {
/* 51 */     if (this.monsterInfos == null)
/*    */     {
/* 53 */       this.monsterInfos = new ArrayList<MonsterInfo>();
/*    */     }
/* 55 */     return this.monsterInfos;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean operate(QuestEnv env) {
/* 61 */     if (this.monsterInfos == null || !(env.getVisibleObject() instanceof Npc)) {
/* 62 */       return false;
/*    */     }
/* 64 */     QuestState qs = env.getPlayer().getQuestStateList().getQuestState(env.getQuestId().intValue());
/* 65 */     if (qs == null) {
/* 66 */       return false;
/*    */     }
/* 68 */     Npc npc = (Npc)env.getVisibleObject();
/* 69 */     for (MonsterInfo monsterInfo : this.monsterInfos) {
/*    */       
/* 71 */       if (monsterInfo.getNpcId() == npc.getNpcId()) {
/*    */         
/* 73 */         int var = qs.getQuestVarById(monsterInfo.getVarId());
/* 74 */         if (var >= ((monsterInfo.getMinVarValue() == null) ? 0 : monsterInfo.getMinVarValue().intValue()) && var < monsterInfo.getMaxKill()) {
/*    */           
/* 76 */           qs.setQuestVarById(monsterInfo.getVarId(), var + 1);
/* 77 */           PacketSendUtility.sendPacket(env.getPlayer(), (AionServerPacket)new SM_QUEST_ACCEPTED(env.getQuestId().intValue(), qs.getStatus(), qs.getQuestVars().getQuestVars()));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 83 */     if (this.complite != null) {
/*    */       
/* 85 */       for (MonsterInfo monsterInfo : this.monsterInfos) {
/*    */         
/* 87 */         if (qs.getQuestVarById(monsterInfo.getVarId()) != qs.getQuestVarById(monsterInfo.getVarId()))
/* 88 */           return false; 
/*    */       } 
/* 90 */       this.complite.operate(env);
/*    */     } 
/* 92 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\events\OnKillEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */