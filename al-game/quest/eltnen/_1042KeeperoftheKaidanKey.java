/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class _1042KeeperoftheKaidanKey
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1042;
/*  39 */   private static final int[] npc_ids = new int[] { 203989, 203901 };
/*  40 */   private static final int[] mob_ids = new int[] { 212029, 212033 };
/*     */ 
/*     */   
/*     */   public _1042KeeperoftheKaidanKey() {
/*  44 */     super(Integer.valueOf(1042));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(1042);
/*  51 */     for (int npc_id : npc_ids)
/*  52 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1042); 
/*  53 */     this.qe.setQuestItemIds(182201018).add(1042);
/*  54 */     for (int mob_id : mob_ids) {
/*  55 */       this.qe.setNpcQuestData(mob_id).addOnKillEvent(1042);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1042);
/*  63 */     boolean lvlCheck = QuestService.checkLevelRequirement(1042, player.getCommonData().getLevel());
/*  64 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/*  65 */       return false; 
/*  66 */     QuestState qs2 = player.getQuestStateList().getQuestState(1040);
/*  67 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  68 */       return false; 
/*  69 */     qs.setStatus(QuestStatus.START);
/*  70 */     updateQuestStatus(player, qs);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  77 */     Player player = env.getPlayer();
/*  78 */     QuestState qs = player.getQuestStateList().getQuestState(1042);
/*  79 */     if (qs == null)
/*  80 */       return false; 
/*  81 */     int var = qs.getQuestVarById(0);
/*  82 */     int targetId = 0;
/*  83 */     if (env.getVisibleObject() instanceof Npc) {
/*  84 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  86 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  88 */       if (targetId == 203901) {
/*     */         
/*  90 */         if (env.getDialogId().intValue() == -1)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  92 */         if (env.getDialogId().intValue() == 1009)
/*  93 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  94 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  96 */       return false;
/*     */     } 
/*  98 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 100 */       return false;
/*     */     }
/* 102 */     if (targetId == 203989) {
/*     */       
/* 104 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 107 */           if (var == 0)
/* 108 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 1012:
/* 110 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 185));
/*     */           break;
/*     */         case 10000:
/* 113 */           if (var == 0) {
/*     */             
/* 115 */             qs.setQuestVarById(0, var + 1);
/* 116 */             updateQuestStatus(player, qs);
/* 117 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 118 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 122 */     } else if (targetId == 203901) {
/*     */       
/* 124 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 127 */           if (var == 2)
/* 128 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 33:
/* 130 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 132 */             ItemService.decreaseItemCountByItemId(player, 182201018, 1L);
/* 133 */             qs.setStatus(QuestStatus.REWARD);
/* 134 */             updateQuestStatus(player, qs);
/* 135 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/* 138 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
/*     */       } 
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 147 */     Player player = env.getPlayer();
/* 148 */     int id = item.getItemTemplate().getTemplateId();
/* 149 */     int itemObjId = item.getObjectId();
/* 150 */     if (id != 182201018)
/* 151 */       return false; 
/* 152 */     QuestState qs = player.getQuestStateList().getQuestState(1042);
/* 153 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
/* 154 */     SetQuestStatus2(player, env, qs);
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 161 */     Player player = env.getPlayer();
/* 162 */     QuestState qs = player.getQuestStateList().getQuestState(1042);
/* 163 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 164 */       return false; 
/* 165 */     int var = qs.getQuestVarById(0);
/* 166 */     int targetId = 0;
/* 167 */     if (env.getVisibleObject() instanceof Npc)
/* 168 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 169 */     switch (targetId) {
/*     */ 
/*     */       
/*     */       case 212029:
/* 173 */         if (var < 2) {
/*     */           
/* 175 */           SetQuestStatus2(player, env, qs);
/* 176 */           return true;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 212033:
/* 181 */         if (var < 2) {
/*     */           
/* 183 */           SetQuestStatus2(player, env, qs);
/* 184 */           return true;
/*     */         } 
/*     */         break;
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   private void SetQuestStatus2(Player player, QuestEnv env, QuestState qs) {
/* 192 */     qs.setQuestVarById(0, 2);
/* 193 */     updateQuestStatus(player, qs);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1042KeeperoftheKaidanKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */