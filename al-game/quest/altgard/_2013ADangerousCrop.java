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
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _2013ADangerousCrop
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2013;
/*     */   
/*     */   public _2013ADangerousCrop() {
/*  50 */     super(Integer.valueOf(2013));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  56 */     this.qe.addQuestLvlUp(2013);
/*  57 */     this.qe.setNpcQuestData(203605).addOnTalkEvent(2013);
/*  58 */     this.qe.setNpcQuestData(700096).addOnTalkEvent(2013);
/*  59 */     this.qe.setQuestEnterZone(ZoneName.MUMU_FARMLAND_220030000).add(2013);
/*  60 */     this.deletebleItems = new int[] { 182203012 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  66 */     final Player player = env.getPlayer();
/*  67 */     final QuestState qs = player.getQuestStateList().getQuestState(2013);
/*  68 */     if (qs == null) {
/*  69 */       return false;
/*     */     }
/*  71 */     final int var = qs.getQuestVarById(0);
/*  72 */     int targetId = 0;
/*  73 */     if (env.getVisibleObject() instanceof Npc) {
/*  74 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  76 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  78 */       switch (targetId) {
/*     */         
/*     */         case 203605:
/*  81 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  84 */               if (var == 0)
/*  85 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  86 */               if (var == 2)
/*  87 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  88 */               if (var == 8)
/*  89 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  90 */               if (var == 9)
/*  91 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */             case 1012:
/*  93 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 61));
/*     */               break;
/*     */             case 10000:
/*     */             case 10001:
/*     */             case 10002:
/*  98 */               if (var == 0 || var == 2 || var == 8) {
/*     */                 
/* 100 */                 if (var == 2 && 
/* 101 */                   !ItemService.addItems(player, Collections.singletonList(new QuestItems(182203012, 1))))
/* 102 */                   return true; 
/* 103 */                 if (var == 8)
/* 104 */                   ItemService.removeItemFromInventoryByItemId(player, 182203012); 
/* 105 */                 qs.setQuestVarById(0, var + 1);
/* 106 */                 updateQuestStatus(player, qs);
/* 107 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 108 */                 return true;
/*     */               } 
/*     */             case 33:
/* 111 */               if (var == 9) {
/*     */                 
/* 113 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/* 115 */                   qs.setStatus(QuestStatus.REWARD);
/* 116 */                   updateQuestStatus(player, qs);
/* 117 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */                 } 
/*     */                 
/* 120 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 700096:
/* 125 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case -1:
/* 128 */               if (var >= 3 && var < 6) {
/*     */                 
/* 130 */                 final int targetObjectId = env.getVisibleObject().getObjectId();
/* 131 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */                 
/* 133 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */                 
/* 135 */                 ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 139 */                         if (!player.isTargeting(targetObjectId))
/*     */                           return; 
/* 141 */                         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                         
/* 143 */                         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                         
/* 145 */                         if (var == 5) {
/* 146 */                           qs.setQuestVarById(0, 8);
/*     */                         } else {
/* 148 */                           qs.setQuestVarById(0, var + 1);
/* 149 */                         }  _2013ADangerousCrop.this.updateQuestStatus(player, qs);
/*     */                       }
/*     */                     }3000L);
/*     */                 
/* 153 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 158 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 160 */       if (targetId == 203605)
/* 161 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 163 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 169 */     if (zoneName != ZoneName.MUMU_FARMLAND_220030000)
/* 170 */       return false; 
/* 171 */     Player player = env.getPlayer();
/* 172 */     QuestState qs = player.getQuestStateList().getQuestState(2013);
/* 173 */     if (qs == null)
/* 174 */       return false; 
/* 175 */     if (qs.getQuestVarById(0) == 1) {
/*     */       
/* 177 */       qs.setQuestVarById(0, 2);
/* 178 */       updateQuestStatus(player, qs);
/* 179 */       return true;
/*     */     } 
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 187 */     Player player = env.getPlayer();
/* 188 */     QuestState qs = player.getQuestStateList().getQuestState(2013);
/* 189 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 190 */       return false; 
/* 191 */     qs.setStatus(QuestStatus.START);
/* 192 */     updateQuestStatus(player, qs);
/* 193 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2013ADangerousCrop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */