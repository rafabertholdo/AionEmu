/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _2022CrushingtheConspiracy
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2022;
/*     */   
/*     */   public _2022CrushingtheConspiracy() {
/*  44 */     super(Integer.valueOf(2022));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(2022);
/*  51 */     this.qe.setNpcQuestData(203557).addOnTalkEvent(2022);
/*  52 */     this.qe.setNpcQuestData(700140).addOnTalkEvent(2022);
/*  53 */     this.qe.setNpcQuestData(700142).addOnTalkEvent(2022);
/*  54 */     this.qe.setNpcQuestData(210753).addOnKillEvent(2022);
/*  55 */     this.qe.setNpcQuestData(700141).addOnTalkEvent(2022);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     final Player player = env.getPlayer();
/*  62 */     int targetId = 0;
/*  63 */     if (env.getVisibleObject() instanceof Npc)
/*  64 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(2022);
/*  66 */     if (qs == null) {
/*     */       
/*  68 */       if (targetId == 203557)
/*     */       {
/*  70 */         if (env.getDialogId().intValue() == 25) {
/*  71 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  73 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  76 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  78 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 203557:
/*  82 */           if (qs.getQuestVarById(0) == 0) {
/*     */             
/*  84 */             if (env.getDialogId().intValue() == 25)
/*  85 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  86 */             if (env.getDialogId().intValue() == 1009) {
/*     */               
/*  88 */               qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  89 */               updateQuestStatus(player, qs);
/*  90 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               
/*  92 */               return true;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 700140:
/*  99 */           if (qs.getQuestVarById(0) == 1) {
/*     */             
/* 101 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 102 */             updateQuestStatus(player, qs);
/* 103 */             TeleportService.teleportTo(player, 320030000, 275.68F, 164.03F, 205.19F, 34);
/* 104 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 105 */             return true;
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 700142:
/* 111 */           if (qs.getQuestVarById(0) == 2) {
/*     */             
/* 113 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 114 */             updateQuestStatus(player, qs);
/* 115 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 116 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 120 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), 700142, 3000, 0));
/* 121 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, 700142), true);
/* 122 */                     QuestService.addNewSpawn(320030000, 1, 210753, 260.12F, 234.93F, 216.0F, (byte)90, true);
/*     */                   }
/*     */                 }3000L);
/* 125 */             return true;
/*     */           } 
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 700141:
/* 132 */           if (qs.getQuestVarById(0) == 4) {
/*     */             
/* 134 */             qs.setStatus(QuestStatus.REWARD);
/* 135 */             updateQuestStatus(player, qs);
/* 136 */             TeleportService.teleportTo(player, 220030000, 2453.0F, 2553.2F, 316.3F, 26);
/* 137 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 138 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 143 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 145 */       if (targetId == 203557)
/* 146 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 154 */     Player player = env.getPlayer();
/* 155 */     QuestState qs = player.getQuestStateList().getQuestState(2022);
/* 156 */     if (qs == null) {
/* 157 */       return false;
/*     */     }
/* 159 */     int var = qs.getQuestVarById(0);
/* 160 */     int targetId = 0;
/* 161 */     if (env.getVisibleObject() instanceof Npc) {
/* 162 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 164 */     if (qs.getStatus() != QuestStatus.START)
/* 165 */       return false; 
/* 166 */     switch (targetId) {
/*     */       
/*     */       case 210753:
/* 169 */         if (var >= 3 && var < 4) {
/*     */           
/* 171 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 172 */           updateQuestStatus(player, qs);
/* 173 */           return true;
/*     */         }  break;
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 182 */     Player player = env.getPlayer();
/* 183 */     QuestState qs = player.getQuestStateList().getQuestState(2022);
/* 184 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 185 */       return false; 
/* 186 */     int[] quests = { 2200, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021 };
/* 187 */     for (int id : quests) {
/*     */       
/* 189 */       QuestState qs2 = player.getQuestStateList().getQuestState(id);
/* 190 */       if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
/* 191 */         return false;
/*     */       }
/*     */     } 
/* 194 */     qs.setStatus(QuestStatus.START);
/* 195 */     updateQuestStatus(player, qs);
/* 196 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2022CrushingtheConspiracy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */