/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _1036KaidanPrisoner
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1036;
/*  41 */   private static final int[] npc_ids = new int[] { 203904, 204045, 204003, 204004, 204020, 203901 };
/*     */ 
/*     */   
/*     */   public _1036KaidanPrisoner() {
/*  45 */     super(Integer.valueOf(1036));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  51 */     this.qe.addQuestLvlUp(1036);
/*  52 */     for (int npc_id : npc_ids) {
/*  53 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1036);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(1036);
/*  61 */     boolean lvlCheck = QuestService.checkLevelRequirement(1036, player.getCommonData().getLevel());
/*  62 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  63 */       return false;
/*     */     }
/*  65 */     qs.setStatus(QuestStatus.START);
/*  66 */     updateQuestStatus(player, qs);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  73 */     Player player = env.getPlayer();
/*  74 */     QuestState qs = player.getQuestStateList().getQuestState(1036);
/*  75 */     if (qs == null) {
/*  76 */       return false;
/*     */     }
/*  78 */     int var = qs.getQuestVarById(0);
/*  79 */     int targetId = 0;
/*  80 */     if (env.getVisibleObject() instanceof Npc) {
/*  81 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  83 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  85 */       if (targetId == 203901) {
/*  86 */         return defaultQuestEndDialog(env);
/*     */       }
/*  88 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  90 */       return false;
/*     */     } 
/*  92 */     if (targetId == 203904) {
/*     */       
/*  94 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  97 */           if (var == 0)
/*  98 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/* 100 */           if (var == 0) {
/*     */             
/* 102 */             qs.setQuestVarById(0, var + 1);
/* 103 */             updateQuestStatus(player, qs);
/* 104 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 105 */             return true;
/*     */           } 
/* 107 */           return false;
/*     */       } 
/*     */     
/* 110 */     } else if (targetId == 204045) {
/*     */       
/* 112 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 115 */           if (var == 1)
/* 116 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 1354:
/* 118 */           if (var == 1)
/* 119 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 32)); 
/*     */           break;
/*     */         case 10001:
/* 122 */           if (var == 1) {
/*     */             
/* 124 */             qs.setQuestVarById(0, var + 1);
/* 125 */             updateQuestStatus(player, qs);
/* 126 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 127 */             return true;
/*     */           } 
/* 129 */           return false;
/*     */       } 
/*     */     
/* 132 */     } else if (targetId == 204003) {
/*     */       
/* 134 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 137 */           if (var == 2)
/* 138 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 139 */           if (var == 3 && QuestService.collectItemCheck(env, true)) {
/* 140 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */           }
/* 142 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */         case 10002:
/* 144 */           if (var == 2) {
/*     */             
/* 146 */             qs.setQuestVarById(0, var + 1);
/* 147 */             updateQuestStatus(player, qs);
/* 148 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 149 */             return true;
/*     */           } 
/*     */         case 10003:
/* 152 */           if (var == 3) {
/*     */             
/* 154 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 50));
/* 155 */             qs.setQuestVarById(0, var + 1);
/* 156 */             updateQuestStatus(player, qs);
/* 157 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 158 */             return true;
/*     */           } 
/* 160 */           return false;
/*     */       } 
/*     */     
/* 163 */     } else if (targetId == 204004) {
/*     */       
/* 165 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 168 */           if (var == 4)
/* 169 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 10004:
/* 171 */           if (var == 4) {
/*     */             
/* 173 */             qs.setQuestVarById(0, var + 1);
/* 174 */             updateQuestStatus(player, qs);
/* 175 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 176 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201004, 1)));
/* 177 */             return true;
/*     */           } 
/* 179 */           return false;
/*     */       } 
/*     */     
/* 182 */     } else if (targetId == 204020) {
/*     */       
/* 184 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 187 */           if (var == 5)
/* 188 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 2717:
/* 190 */           ItemService.decreaseItemCountByItemId(player, 182201004, 1L);
/*     */         case 10004:
/* 192 */           if (var == 5) {
/*     */             
/* 194 */             qs.setQuestVarById(0, var + 1);
/* 195 */             qs.setStatus(QuestStatus.REWARD);
/* 196 */             updateQuestStatus(player, qs);
/* 197 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 198 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182201005, 1)));
/* 199 */             return true;
/*     */           } 
/* 201 */           return false;
/*     */       } 
/*     */     
/* 204 */     } else if (targetId == 203901) {
/*     */       
/* 206 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 209 */           if (var == 6)
/* 210 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 1009:
/* 212 */           if (var == 6) {
/*     */             
/* 214 */             ItemService.decreaseItemCountByItemId(player, 182201005, 1L);
/* 215 */             qs.setStatus(QuestStatus.REWARD);
/* 216 */             qs.setQuestVarById(0, var + 1);
/* 217 */             updateQuestStatus(player, qs);
/* 218 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/* 220 */           return false;
/*     */       } 
/*     */     } 
/* 223 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1036KaidanPrisoner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */