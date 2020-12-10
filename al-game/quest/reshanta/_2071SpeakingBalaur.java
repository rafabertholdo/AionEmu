/*     */ package quest.reshanta;
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
/*     */ import com.aionemu.gameserver.utils.MathUtil;
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
/*     */ public class _2071SpeakingBalaur
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2071;
/*  43 */   private static final int[] npc_ids = new int[] { 278003, 278086, 278039, 279027, 204210 };
/*     */ 
/*     */   
/*     */   public _2071SpeakingBalaur() {
/*  47 */     super(Integer.valueOf(2071));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  53 */     this.qe.addQuestLvlUp(2071);
/*  54 */     this.qe.setNpcQuestData(253610).addOnAttackEvent(2071);
/*  55 */     for (int npc_id : npc_ids) {
/*  56 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2071);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  62 */     Player player = env.getPlayer();
/*  63 */     QuestState qs = player.getQuestStateList().getQuestState(2071);
/*  64 */     boolean lvlCheck = QuestService.checkLevelRequirement(2071, player.getCommonData().getLevel());
/*  65 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  66 */       return false;
/*     */     }
/*  68 */     QuestState qs2 = player.getQuestStateList().getQuestState(2701);
/*  69 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  70 */       return false; 
/*  71 */     qs.setStatus(QuestStatus.START);
/*  72 */     updateQuestStatus(player, qs);
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  79 */     Player player = env.getPlayer();
/*  80 */     QuestState qs = player.getQuestStateList().getQuestState(2071);
/*  81 */     if (qs == null) {
/*  82 */       return false;
/*     */     }
/*  84 */     int var = qs.getQuestVarById(0);
/*  85 */     int targetId = 0;
/*  86 */     if (env.getVisibleObject() instanceof Npc) {
/*  87 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  89 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  91 */       if (targetId == 278003) {
/*     */         
/*  93 */         if (env.getDialogId().intValue() == -1)
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/*  95 */         if (env.getDialogId().intValue() == 1009)
/*  96 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/*  97 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  99 */       return false;
/*     */     } 
/* 101 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 103 */       return false;
/*     */     }
/* 105 */     if (targetId == 278003) {
/*     */       
/* 107 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 110 */           if (var == 0)
/* 111 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
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
/* 122 */     } else if (targetId == 278086) {
/*     */       
/* 124 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 127 */           if (var == 1)
/* 128 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 130 */           if (var == 1) {
/*     */             
/* 132 */             qs.setQuestVarById(0, var + 1);
/* 133 */             updateQuestStatus(player, qs);
/* 134 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 135 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 139 */     } else if (targetId == 278039) {
/*     */       
/* 141 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 144 */           if (var == 3)
/* 145 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10003:
/* 147 */           if (var == 3) {
/*     */             
/* 149 */             qs.setQuestVarById(0, var + 1);
/* 150 */             updateQuestStatus(player, qs);
/* 151 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 152 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 156 */     } else if (targetId == 279027) {
/*     */       
/* 158 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 161 */           if (var == 4)
/* 162 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 163 */           if (var == 6)
/* 164 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 3058:
/* 166 */           ItemService.removeItemFromInventoryByItemId(player, 182205501);
/* 167 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 293));
/*     */           break;
/*     */         case 10004:
/* 170 */           if (var == 4) {
/*     */             
/* 172 */             qs.setQuestVarById(0, var + 1);
/* 173 */             updateQuestStatus(player, qs);
/* 174 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 175 */             return true;
/*     */           } 
/*     */         case 10255:
/* 178 */           if (var == 6) {
/*     */             
/* 180 */             qs.setStatus(QuestStatus.REWARD);
/* 181 */             updateQuestStatus(player, qs);
/* 182 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 183 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 187 */     } else if (targetId == 204210) {
/*     */       
/* 189 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 192 */           if (var == 5)
/* 193 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 10005:
/* 195 */           if (var == 5) {
/*     */             
/* 197 */             qs.setQuestVarById(0, var + 1);
/* 198 */             updateQuestStatus(player, qs);
/* 199 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182205501, 1)));
/* 200 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 201 */             return true;
/*     */           }  break;
/*     */       } 
/*     */     } 
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onAttackEvent(QuestEnv env) {
/* 211 */     Player player = env.getPlayer();
/* 212 */     QuestState qs = player.getQuestStateList().getQuestState(2071);
/*     */     
/* 214 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 2) {
/* 215 */       return false;
/*     */     }
/* 217 */     int targetId = 0;
/* 218 */     if (env.getVisibleObject() instanceof Npc)
/* 219 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 220 */     if (targetId != 253610) {
/* 221 */       return false;
/*     */     }
/* 223 */     Npc npc = (Npc)env.getVisibleObject();
/*     */     
/* 225 */     if (MathUtil.getDistance(1172.0F, 1959.0F, 1605.0F, npc.getX(), npc.getY(), npc.getZ()) > 15.0D) {
/* 226 */       return false;
/*     */     }
/* 228 */     if (npc.getLifeStats().getCurrentHp() < npc.getLifeStats().getMaxHp() / 3) {
/*     */       
/* 230 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 289));
/* 231 */       npc.getController().onDie(null);
/* 232 */       npc.getController().onDespawn(true);
/* 233 */       qs.setQuestVarById(0, 3);
/* 234 */       updateQuestStatus(player, qs);
/*     */     } else {
/*     */       
/* 237 */       return false;
/*     */     } 
/* 239 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_2071SpeakingBalaur.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */