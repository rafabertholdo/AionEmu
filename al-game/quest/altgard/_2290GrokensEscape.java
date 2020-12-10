/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
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
/*     */ 
/*     */ public class _2290GrokensEscape
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2290;
/*     */   
/*     */   public _2290GrokensEscape() {
/*  47 */     super(Integer.valueOf(2290));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  53 */     this.qe.setNpcQuestData(203608).addOnQuestStart(2290);
/*  54 */     this.qe.setNpcQuestData(203608).addOnTalkEvent(2290);
/*  55 */     this.qe.setNpcQuestData(700178).addOnTalkEvent(2290);
/*  56 */     this.qe.setNpcQuestData(203607).addOnTalkEvent(2290);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  62 */     final Player player = env.getPlayer();
/*  63 */     int targetId = 0;
/*  64 */     if (env.getVisibleObject() instanceof Npc)
/*  65 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  66 */     final QuestState qs = player.getQuestStateList().getQuestState(2290);
/*  67 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  69 */       if (targetId == 203608) {
/*     */         
/*  71 */         if (env.getDialogId().intValue() == 25)
/*  72 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  73 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  75 */           if (QuestService.startQuest(env, QuestStatus.START)) {
/*     */             
/*  77 */             Npc npc = (Npc)env.getVisibleObject();
/*  78 */             npc.getMoveController().setNewDirection(1219.15F, 1212.0F, 247.37F);
/*  79 */             npc.getMoveController().schedule();
/*  80 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1003);
/*     */           } 
/*     */         } else {
/*     */           
/*  84 */           return defaultQuestStartDialog(env);
/*     */         } 
/*     */       } 
/*  87 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  89 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700178:
/*  93 */           if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/*     */             
/*  95 */             for (VisibleObject obj : player.getKnownList().getKnownObjects().values()) {
/*     */               
/*  97 */               if (!(obj instanceof Npc))
/*     */                 continue; 
/*  99 */               if (((Npc)obj).getNpcId() != 203608)
/*     */                 continue; 
/* 101 */               if (MathUtil.getDistance(player.getX(), player.getY(), player.getZ(), obj.getX(), obj.getY(), obj.getZ()) > 4.0D)
/* 102 */                 return false; 
/* 103 */               ((Npc)obj).getController().onDie(null);
/* 104 */               ((Npc)obj).getController().onDespawn(false);
/*     */             } 
/* 106 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 107 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 69));
/* 108 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 110 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 112 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 116 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 118 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 120 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/* 122 */                     qs.setQuestVarById(0, 3);
/* 123 */                     _2290GrokensEscape.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */ 
/*     */         
/*     */         case 203607:
/* 131 */           if (qs.getQuestVarById(0) == 3) {
/*     */             
/* 133 */             if (env.getDialogId().intValue() == 25)
/* 134 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 135 */             if (env.getDialogId().intValue() == 1009) {
/*     */               
/* 137 */               ItemService.removeItemFromInventoryByItemId(player, 182203208);
/* 138 */               qs.setStatus(QuestStatus.REWARD);
/* 139 */               updateQuestStatus(player, qs);
/* 140 */               return defaultQuestEndDialog(env);
/*     */             } 
/*     */             
/* 143 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 148 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 150 */       if (targetId == 203607)
/* 151 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2290GrokensEscape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */