/*     */ package quest.altgard;
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
/*     */ 
/*     */ public class _2019SecuringtheSupplyRoute
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2019;
/*     */   
/*     */   public _2019SecuringtheSupplyRoute() {
/*  44 */     super(Integer.valueOf(2019));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(2019);
/*  51 */     this.qe.setNpcQuestData(798033).addOnTalkEvent(2019);
/*  52 */     this.qe.setNpcQuestData(210492).addOnKillEvent(2019);
/*  53 */     this.qe.setNpcQuestData(210493).addOnKillEvent(2019);
/*  54 */     this.qe.setNpcQuestData(203673).addOnTalkEvent(2019);
/*  55 */     this.deletebleItems = new int[] { 182203024 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     Player player = env.getPlayer();
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(2019);
/*  63 */     if (qs == null) {
/*  64 */       return false;
/*     */     }
/*  66 */     int var = qs.getQuestVarById(0);
/*  67 */     int targetId = 0;
/*  68 */     if (env.getVisibleObject() instanceof Npc) {
/*  69 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  71 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  73 */       switch (targetId) {
/*     */         
/*     */         case 798033:
/*  76 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  79 */               if (var == 0)
/*  80 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  81 */               if (var == 4)
/*  82 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */               break;
/*     */             case 10000:
/*  85 */               if (var == 0) {
/*     */                 
/*  87 */                 qs.setQuestVarById(0, var + 1);
/*  88 */                 updateQuestStatus(player, qs);
/*  89 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  90 */                 return true;
/*     */               } 
/*     */             case 10001:
/*  93 */               if (var == 4) {
/*     */                 
/*  95 */                 if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203024, 1))))
/*  96 */                   return true; 
/*  97 */                 qs.setQuestVarById(0, var + 1);
/*  98 */                 updateQuestStatus(player, qs);
/*  99 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 100 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */         case 203673:
/* 104 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 107 */               if (var == 5)
/* 108 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */             case 1009:
/* 110 */               if (var == 5) {
/*     */                 
/* 112 */                 ItemService.removeItemFromInventoryByItemId(player, 182203024);
/* 113 */                 qs.setStatus(QuestStatus.REWARD);
/* 114 */                 updateQuestStatus(player, qs);
/* 115 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 121 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 123 */       if (targetId == 203673)
/* 124 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 132 */     Player player = env.getPlayer();
/* 133 */     QuestState qs = player.getQuestStateList().getQuestState(2019);
/* 134 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 135 */       return false;
/*     */     }
/* 137 */     int var = qs.getQuestVarById(0);
/* 138 */     int targetId = 0;
/* 139 */     if (env.getVisibleObject() instanceof Npc) {
/* 140 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 142 */     switch (targetId) {
/*     */       
/*     */       case 210492:
/*     */       case 210493:
/* 146 */         if (var >= 1 && var < 4) {
/*     */           
/* 148 */           qs.setQuestVarById(0, var + 1);
/* 149 */           updateQuestStatus(player, qs);
/* 150 */           return true;
/*     */         }  break;
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 159 */     Player player = env.getPlayer();
/* 160 */     QuestState qs = player.getQuestStateList().getQuestState(2019);
/* 161 */     boolean lvlCheck = QuestService.checkLevelRequirement(2019, player.getCommonData().getLevel());
/* 162 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/* 163 */       return false; 
/* 164 */     qs.setStatus(QuestStatus.START);
/* 165 */     updateQuestStatus(player, qs);
/* 166 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2019SecuringtheSupplyRoute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */