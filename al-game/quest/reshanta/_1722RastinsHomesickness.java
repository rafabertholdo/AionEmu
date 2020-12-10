/*     */ package quest.reshanta;
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
/*     */ public class _1722RastinsHomesickness
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1722;
/*  39 */   private static final int[] npc_ids = new int[] { 278547, 278560, 278517, 278544, 278532, 278539, 278524, 278555, 278567 };
/*     */ 
/*     */   
/*     */   public _1722RastinsHomesickness() {
/*  43 */     super(Integer.valueOf(1722));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(278547).addOnQuestStart(1722);
/*  50 */     for (int npc_id : npc_ids) {
/*  51 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1722);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1722);
/*  62 */     if (targetId == 278547)
/*     */     {
/*  64 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  66 */         if (env.getDialogId().intValue() == 25) {
/*  67 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
/*     */         }
/*  69 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*  72 */     if (qs == null) {
/*  73 */       return false;
/*     */     }
/*  75 */     int var = qs.getQuestVarById(0);
/*  76 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  78 */       if (targetId == 278547)
/*  79 */         ItemService.removeItemFromInventoryByItemId(player, 182202101); 
/*  80 */       return defaultQuestEndDialog(env);
/*     */     } 
/*  82 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/*  84 */       return false;
/*     */     }
/*  86 */     if (targetId == 278560) {
/*     */       
/*  88 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  91 */           if (var == 0)
/*  92 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */         case 10000:
/*  94 */           if (var == 0) {
/*     */             
/*  96 */             qs.setQuestVarById(0, var + 1);
/*  97 */             updateQuestStatus(player, qs);
/*  98 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  99 */             return true;
/*     */           } 
/* 101 */           return false;
/*     */       } 
/*     */     
/* 104 */     } else if (targetId == 278517) {
/*     */       
/* 106 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 109 */           if (var == 1)
/* 110 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 112 */           if (var == 1) {
/*     */             
/* 114 */             qs.setQuestVarById(0, var + 1);
/* 115 */             updateQuestStatus(player, qs);
/* 116 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 117 */             return true;
/*     */           } 
/* 119 */           return false;
/*     */       } 
/*     */     
/* 122 */     } else if (targetId == 278544) {
/*     */       
/* 124 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 127 */           if (var == 2)
/* 128 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 1009:
/* 130 */           if (var == 2) {
/*     */             
/* 132 */             qs.setQuestVarById(0, var + 1);
/* 133 */             updateQuestStatus(player, qs);
/* 134 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 135 */             return true;
/*     */           } 
/* 137 */           return false;
/*     */       } 
/*     */     
/* 140 */     } else if (targetId == 278532) {
/*     */       
/* 142 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 145 */           if (var == 3)
/* 146 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 10003:
/* 148 */           if (var == 3) {
/*     */             
/* 150 */             qs.setQuestVarById(0, var + 1);
/* 151 */             updateQuestStatus(player, qs);
/* 152 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 153 */             return true;
/*     */           } 
/* 155 */           return false;
/*     */       } 
/*     */     
/* 158 */     } else if (targetId == 278539) {
/*     */       
/* 160 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 163 */           if (var == 4)
/* 164 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 10004:
/* 166 */           if (var == 4) {
/*     */             
/* 168 */             qs.setQuestVarById(0, var + 1);
/* 169 */             updateQuestStatus(player, qs);
/* 170 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 171 */             return true;
/*     */           } 
/* 173 */           return false;
/*     */       } 
/*     */     
/* 176 */     } else if (targetId == 278524) {
/*     */       
/* 178 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 181 */           if (var == 5)
/* 182 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 10005:
/* 184 */           if (var == 5) {
/*     */             
/* 186 */             qs.setQuestVarById(0, var + 1);
/* 187 */             updateQuestStatus(player, qs);
/* 188 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 189 */             return true;
/*     */           } 
/* 191 */           return false;
/*     */       } 
/*     */     
/* 194 */     } else if (targetId == 278555) {
/*     */       
/* 196 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 199 */           if (var == 6)
/* 200 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */         case 10006:
/* 202 */           if (var == 6) {
/*     */             
/* 204 */             qs.setQuestVarById(0, var + 1);
/* 205 */             updateQuestStatus(player, qs);
/* 206 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 207 */             return true;
/*     */           } 
/* 209 */           return false;
/*     */       } 
/*     */     
/* 212 */     } else if (targetId == 278567) {
/*     */       
/* 214 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 217 */           if (var == 7)
/* 218 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/*     */         case 10255:
/* 220 */           if (var == 7) {
/*     */             
/* 222 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182202101, 1)));
/* 223 */             qs.setStatus(QuestStatus.REWARD);
/* 224 */             updateQuestStatus(player, qs);
/* 225 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 226 */             return true;
/*     */           } 
/* 228 */           return false;
/*     */       } 
/*     */     } 
/* 231 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_1722RastinsHomesickness.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */