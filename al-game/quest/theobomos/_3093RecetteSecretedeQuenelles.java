/*     */ package quest.theobomos;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _3093RecetteSecretedeQuenelles
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3093;
/*     */   
/*     */   public _3093RecetteSecretedeQuenelles() {
/*  43 */     super(Integer.valueOf(3093));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(798185).addOnQuestStart(3093);
/*  50 */     this.qe.setNpcQuestData(798185).addOnTalkEvent(3093);
/*  51 */     this.qe.setNpcQuestData(798177).addOnTalkEvent(3093);
/*  52 */     this.qe.setNpcQuestData(798179).addOnTalkEvent(3093);
/*  53 */     this.qe.setNpcQuestData(203784).addOnTalkEvent(3093);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     int targetId = 0;
/*  61 */     if (env.getVisibleObject() instanceof Npc)
/*  62 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  63 */     QuestState qs = player.getQuestStateList().getQuestState(3093);
/*  64 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  66 */       if (targetId == 798185) {
/*     */         
/*  68 */         if (env.getDialogId().intValue() == 25)
/*  69 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  70 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  72 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  73 */           updateQuestStatus(player, qs);
/*  74 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  76 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206062, 1))));
/*  77 */           return true;
/*     */         } 
/*     */ 
/*     */         
/*  81 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } else {
/*  84 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/*  86 */         if (env.getDialogId().intValue() == 25)
/*  87 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  88 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  90 */           qs.setQuestVar(4);
/*  91 */           qs.setStatus(QuestStatus.REWARD);
/*  92 */           updateQuestStatus(player, qs);
/*  93 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  96 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/*  99 */       if (targetId == 798177) {
/*     */ 
/*     */         
/* 102 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */         {
/* 104 */           if (env.getDialogId().intValue() == 25)
/* 105 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 106 */           if (env.getDialogId().intValue() == 10000) {
/*     */             
/* 108 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 109 */             updateQuestStatus(player, qs);
/* 110 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 112 */             return true;
/*     */           } 
/*     */           
/* 115 */           return defaultQuestStartDialog(env);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 120 */       else if (targetId == 798179) {
/*     */         
/* 122 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */         {
/* 124 */           if (env.getDialogId().intValue() == 25)
/* 125 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 126 */           if (env.getDialogId().intValue() == 10001) {
/*     */             
/* 128 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 129 */             updateQuestStatus(player, qs);
/* 130 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 132 */             return true;
/*     */           } 
/*     */           
/* 135 */           return defaultQuestStartDialog(env);
/*     */         }
/*     */       
/*     */       }
/* 139 */       else if (targetId == 203784) {
/*     */         
/* 141 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
/*     */           
/* 143 */           if (env.getDialogId().intValue() == 25)
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 145 */           if (env.getDialogId().intValue() == 10002) {
/*     */             
/* 147 */             if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182208052, 1))));
/* 148 */             qs.setStatus(QuestStatus.REWARD);
/* 149 */             updateQuestStatus(player, qs);
/* 150 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 152 */             return true;
/*     */           } 
/*     */           
/* 155 */           return defaultQuestStartDialog(env);
/*     */         } 
/*     */       } 
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3093RecetteSecretedeQuenelles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */