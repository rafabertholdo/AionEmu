/*     */ package quest.reshanta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class _2722TheComfortsofHome
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2722;
/*     */   
/*     */   public _2722TheComfortsofHome() {
/*  21 */     super(Integer.valueOf(2722));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(278047).addOnQuestStart(2722);
/*  28 */     this.qe.setNpcQuestData(278047).addOnTalkEvent(2722);
/*  29 */     this.qe.setNpcQuestData(278056).addOnTalkEvent(2722);
/*  30 */     this.qe.setNpcQuestData(278126).addOnTalkEvent(2722);
/*  31 */     this.qe.setNpcQuestData(278043).addOnTalkEvent(2722);
/*  32 */     this.qe.setNpcQuestData(278032).addOnTalkEvent(2722);
/*  33 */     this.qe.setNpcQuestData(278037).addOnTalkEvent(2722);
/*  34 */     this.qe.setNpcQuestData(278040).addOnTalkEvent(2722);
/*  35 */     this.qe.setNpcQuestData(278068).addOnTalkEvent(2722);
/*  36 */     this.qe.setNpcQuestData(278066).addOnTalkEvent(2722);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  42 */     Player player = env.getPlayer();
/*  43 */     int targetId = 0;
/*  44 */     if (env.getVisibleObject() instanceof Npc)
/*  45 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  46 */     QuestState qs = player.getQuestStateList().getQuestState(2722);
/*  47 */     if (targetId == 278047) {
/*     */       
/*  49 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  51 */         if (env.getDialogId().intValue() == 25) {
/*  52 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */         }
/*  54 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  56 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  58 */         if (env.getDialogId().intValue() == 25)
/*  59 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  60 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  62 */           qs.setStatus(QuestStatus.REWARD);
/*  63 */           updateQuestStatus(player, qs);
/*  64 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  65 */           return true;
/*     */         } 
/*     */         
/*  68 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  72 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  75 */     else if (targetId == 278056) {
/*     */       
/*  77 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  79 */         if (env.getDialogId().intValue() == 25)
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  81 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  83 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  84 */           updateQuestStatus(player, qs);
/*  85 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  86 */           return true;
/*     */         } 
/*     */         
/*  89 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  92 */     } else if (targetId == 278126) {
/*     */       
/*  94 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  96 */         if (env.getDialogId().intValue() == 25)
/*  97 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  98 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 100 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 101 */           updateQuestStatus(player, qs);
/* 102 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 103 */           return true;
/*     */         } 
/*     */         
/* 106 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 109 */     } else if (targetId == 278043) {
/*     */       
/* 111 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 113 */         if (env.getDialogId().intValue() == 25)
/* 114 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 115 */         if (env.getDialogId().intValue() == 10003) {
/*     */           
/* 117 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 118 */           updateQuestStatus(player, qs);
/* 119 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 120 */           return true;
/*     */         } 
/*     */         
/* 123 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 126 */     } else if (targetId == 278032) {
/*     */       
/* 128 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3)
/*     */       {
/* 130 */         if (env.getDialogId().intValue() == 25)
/* 131 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 132 */         if (env.getDialogId().intValue() == 10004) {
/*     */           
/* 134 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 135 */           updateQuestStatus(player, qs);
/* 136 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 137 */           return true;
/*     */         } 
/*     */         
/* 140 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 143 */     } else if (targetId == 278037) {
/*     */       
/* 145 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
/*     */       {
/* 147 */         if (env.getDialogId().intValue() == 25)
/* 148 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 149 */         if (env.getDialogId().intValue() == 10005) {
/*     */           
/* 151 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 152 */           updateQuestStatus(player, qs);
/* 153 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */           return true;
/*     */         } 
/*     */         
/* 157 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 160 */     } else if (targetId == 278040) {
/*     */       
/* 162 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
/*     */       {
/* 164 */         if (env.getDialogId().intValue() == 25)
/* 165 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 166 */         if (env.getDialogId().intValue() == 10006) {
/*     */           
/* 168 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 169 */           updateQuestStatus(player, qs);
/* 170 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 171 */           return true;
/*     */         } 
/*     */         
/* 174 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 177 */     } else if (targetId == 278068) {
/*     */       
/* 179 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 6)
/*     */       {
/* 181 */         if (env.getDialogId().intValue() == 25)
/* 182 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 183 */         if (env.getDialogId().intValue() == 10255) {
/*     */           
/* 185 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 186 */           updateQuestStatus(player, qs);
/* 187 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 188 */           return true;
/*     */         } 
/*     */         
/* 191 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 194 */     } else if (targetId == 278066) {
/*     */       
/* 196 */       if (qs != null) {
/*     */         
/* 198 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 199 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 200 */         if (env.getDialogId().intValue() == 17) {
/*     */           
/* 202 */           qs.setQuestVar(3);
/* 203 */           qs.setStatus(QuestStatus.REWARD);
/* 204 */           updateQuestStatus(player, qs);
/* 205 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 208 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_2722TheComfortsofHome.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */