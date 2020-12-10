/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _2014ScoutitOut
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2014;
/*     */   
/*     */   public _2014ScoutitOut() {
/*  47 */     super(Integer.valueOf(2014));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  53 */     this.qe.addQuestLvlUp(2014);
/*  54 */     this.qe.setNpcQuestData(203606).addOnTalkEvent(2014);
/*  55 */     this.qe.setNpcQuestData(700009).addOnTalkEvent(2014);
/*  56 */     this.qe.setNpcQuestData(203633).addOnTalkEvent(2014);
/*  57 */     this.qe.setNpcQuestData(700135).addOnKillEvent(2014);
/*  58 */     this.qe.setNpcQuestData(203631).addOnTalkEvent(2014);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  64 */     final Player player = env.getPlayer();
/*  65 */     final QuestState qs = player.getQuestStateList().getQuestState(2014);
/*  66 */     if (qs == null) {
/*  67 */       return false;
/*     */     }
/*  69 */     int var = qs.getQuestVarById(0);
/*  70 */     int targetId = 0;
/*  71 */     if (env.getVisibleObject() instanceof Npc) {
/*  72 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  74 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  76 */       switch (targetId) {
/*     */         
/*     */         case 203606:
/*  79 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  82 */               if (var == 0)
/*  83 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  84 */               if (var == 1 || var == 2) {
/*  85 */                 if (player.getInventory().getItemCountByItemId(182203015) == 0L) {
/*  86 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
/*     */                 }
/*  88 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               }  break;
/*     */             case 10000:
/*     */             case 10001:
/*  92 */               if (var == 0 || var == 1 || var == 2) {
/*     */                 
/*  94 */                 if (var == 1 || var == 2) {
/*     */                   
/*  96 */                   ItemService.removeItemFromInventoryByItemId(player, 182203015);
/*  97 */                   qs.setQuestVarById(0, 3);
/*     */                 }
/*     */                 else {
/*     */                   
/* 101 */                   qs.setQuestVarById(0, 1);
/* 102 */                 }  updateQuestStatus(player, qs);
/* 103 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 104 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 700009:
/* 109 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case -1:
/* 112 */               if (var == 1) {
/*     */                 
/* 114 */                 final int targetObjectId = env.getVisibleObject().getObjectId();
/* 115 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */                 
/* 117 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */                 
/* 119 */                 ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 123 */                         if (!player.isTargeting(targetObjectId))
/*     */                           return; 
/* 125 */                         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                         
/* 127 */                         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                         
/* 129 */                         if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203015, 1)))) {
/*     */                           
/* 131 */                           qs.setQuestVarById(0, 2);
/* 132 */                           _2014ScoutitOut.this.updateQuestStatus(player, qs);
/*     */                         } 
/*     */                       }
/*     */                     }3000L);
/*     */                 
/* 137 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203633:
/* 142 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 145 */               if (var == 3)
/* 146 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */               break;
/*     */             case 10002:
/* 149 */               qs.setQuestVarById(0, var + 1);
/* 150 */               updateQuestStatus(player, qs);
/* 151 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 152 */               return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 156 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 158 */       if (targetId == 203631)
/*     */       {
/* 160 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 169 */     Player player = env.getPlayer();
/* 170 */     QuestState qs = player.getQuestStateList().getQuestState(2014);
/* 171 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 172 */       return false;
/*     */     }
/* 174 */     int var = qs.getQuestVarById(0);
/* 175 */     int targetId = 0;
/* 176 */     if (env.getVisibleObject() instanceof Npc) {
/* 177 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 179 */     if (targetId == 700135 && var == 4) {
/*     */       
/* 181 */       qs.setStatus(QuestStatus.REWARD);
/* 182 */       updateQuestStatus(player, qs);
/* 183 */       return true;
/*     */     } 
/* 185 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 191 */     Player player = env.getPlayer();
/* 192 */     QuestState qs = player.getQuestStateList().getQuestState(2014);
/* 193 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 194 */       return false; 
/* 195 */     qs.setStatus(QuestStatus.START);
/* 196 */     updateQuestStatus(player, qs);
/* 197 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2014ScoutitOut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */