/*    */ package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;
/*    */ 
/*    */ import com.aionemu.gameserver.model.EmotionType;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "ActionItemUseOperation", propOrder = {"finish"})
/*    */ public class ActionItemUseOperation
/*    */   extends QuestOperation
/*    */ {
/*    */   @XmlElement(required = true)
/*    */   protected QuestOperations finish;
/*    */   
/*    */   public void doOperate(final QuestEnv env) {
/*    */     final Npc npc;
/* 54 */     final Player player = env.getPlayer();
/*    */     
/* 56 */     if (env.getVisibleObject() instanceof Npc) {
/* 57 */       npc = (Npc)env.getVisibleObject();
/*    */     } else {
/*    */       return;
/* 60 */     }  int defaultUseTime = 3000;
/* 61 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), npc.getObjectId(), 3000, 1));
/*    */     
/* 63 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, npc.getObjectId()), true);
/* 64 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 68 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), npc.getObjectId(), 3000, 0));
/*    */             
/* 70 */             ActionItemUseOperation.this.finish.operate(env);
/*    */           }
/*    */         }3000L);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\ActionItemUseOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */