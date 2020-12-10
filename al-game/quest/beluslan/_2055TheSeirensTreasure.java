/*     */ package quest.beluslan;
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
/*     */ 
/*     */ public class _2055TheSeirensTreasure
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2055;
/*  42 */   private static final int[] npc_ids = new int[] { 204768, 204743, 204808 };
/*     */ 
/*     */   
/*     */   public _2055TheSeirensTreasure() {
/*  46 */     super(Integer.valueOf(2055));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.addQuestLvlUp(2055);
/*  53 */     for (int npc_id : npc_ids) {
/*  54 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2055);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  60 */     Player player = env.getPlayer();
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(2055);
/*  62 */     boolean lvlCheck = QuestService.checkLevelRequirement(2055, player.getCommonData().getLevel());
/*  63 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/*  64 */       return false; 
/*  65 */     QuestState qs2 = player.getQuestStateList().getQuestState(2054);
/*  66 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  67 */       return false; 
/*  68 */     qs.setStatus(QuestStatus.START);
/*  69 */     updateQuestStatus(player, qs);
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  76 */     Player player = env.getPlayer();
/*  77 */     QuestState qs = player.getQuestStateList().getQuestState(2055);
/*  78 */     if (qs == null) {
/*  79 */       return false;
/*     */     }
/*  81 */     int var = qs.getQuestVarById(0);
/*  82 */     int targetId = 0;
/*  83 */     if (env.getVisibleObject() instanceof Npc) {
/*  84 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  86 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  88 */       if (targetId == 204768)
/*     */       {
/*  90 */         return defaultQuestEndDialog(env);
/*     */       }
/*  92 */       return false;
/*     */     } 
/*  94 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  96 */       return false;
/*     */     }
/*  98 */     if (targetId == 204768) {
/*     */       
/* 100 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 103 */           if (var == 0)
/* 104 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 105 */           if (var == 2)
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 107 */           if (var == 6)
/* 108 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 1012:
/* 110 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 239));
/*     */           break;
/*     */         case 10000:
/* 113 */           if (var == 0) {
/*     */             
/* 115 */             qs.setQuestVarById(0, var + 1);
/* 116 */             updateQuestStatus(player, qs);
/* 117 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204310, 1)));
/* 118 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 119 */             return true;
/*     */           } 
/*     */         case 10002:
/* 122 */           if (var == 2) {
/*     */             
/* 124 */             qs.setQuestVarById(0, var + 1);
/* 125 */             updateQuestStatus(player, qs);
/* 126 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 127 */             return true;
/*     */           } 
/*     */         case 1009:
/* 130 */           if (var == 6) {
/*     */             
/* 132 */             ItemService.removeItemFromInventoryByItemId(player, 182204321);
/* 133 */             qs.setStatus(QuestStatus.REWARD);
/* 134 */             updateQuestStatus(player, qs);
/* 135 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */         case 10006:
/* 138 */           if (var == 6) {
/*     */             
/* 140 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 241));
/* 141 */             ItemService.removeItemFromInventoryByItemId(player, 182204321);
/* 142 */             qs.setStatus(QuestStatus.REWARD);
/* 143 */             updateQuestStatus(player, qs);
/* 144 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           break;
/*     */       } 
/* 148 */     } else if (targetId == 204743) {
/*     */       
/* 150 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 153 */           if (var == 1)
/* 154 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 156 */           if (var == 1) {
/*     */             
/* 158 */             qs.setQuestVarById(0, var + 1);
/* 159 */             updateQuestStatus(player, qs);
/* 160 */             ItemService.removeItemFromInventoryByItemId(player, 182204310);
/* 161 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204311, 1)));
/* 162 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 163 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 167 */     } else if (targetId == 204808) {
/*     */       
/* 169 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 172 */           if (var == 3)
/* 173 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 174 */           if (var == 4)
/* 175 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 176 */           if (var == 5)
/* 177 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 2035:
/* 179 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 240));
/*     */           break;
/*     */         case 10003:
/* 182 */           if (var == 3) {
/*     */             
/* 184 */             qs.setQuestVarById(0, var + 1);
/* 185 */             updateQuestStatus(player, qs);
/* 186 */             ItemService.removeItemFromInventoryByItemId(player, 182204311);
/* 187 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 188 */             return true;
/*     */           } 
/*     */         case 10005:
/* 191 */           if (var == 5) {
/*     */             
/* 193 */             qs.setQuestVarById(0, var + 1);
/* 194 */             updateQuestStatus(player, qs);
/* 195 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204321, 1)));
/* 196 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 197 */             return true;
/*     */           } 
/*     */         case 33:
/* 200 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 202 */             qs.setQuestVarById(0, var + 1);
/* 203 */             updateQuestStatus(player, qs);
/* 204 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 207 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */       } 
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2055TheSeirensTreasure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */