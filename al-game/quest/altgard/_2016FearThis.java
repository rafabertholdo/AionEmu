/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
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
/*     */ import com.aionemu.gameserver.services.ZoneService;
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
/*     */ 
/*     */ public class _2016FearThis
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2016;
/*     */   
/*     */   public _2016FearThis() {
/*  50 */     super(Integer.valueOf(2016));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  56 */     this.qe.addQuestLvlUp(2016);
/*  57 */     this.qe.setNpcQuestData(203631).addOnTalkEvent(2016);
/*  58 */     this.qe.setNpcQuestData(210455).addOnKillEvent(2016);
/*  59 */     this.qe.setNpcQuestData(210458).addOnKillEvent(2016);
/*  60 */     this.qe.setNpcQuestData(214032).addOnKillEvent(2016);
/*  61 */     this.qe.setNpcQuestData(203621).addOnTalkEvent(2016);
/*  62 */     this.qe.setQuestItemIds(182203019).add(2016);
/*  63 */     this.deletebleItems = new int[] { 182203019 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  69 */     Player player = env.getPlayer();
/*  70 */     QuestState qs = player.getQuestStateList().getQuestState(2016);
/*  71 */     if (qs == null) {
/*  72 */       return false;
/*     */     }
/*  74 */     int var = qs.getQuestVarById(0);
/*  75 */     int targetId = 0;
/*  76 */     if (env.getVisibleObject() instanceof Npc) {
/*  77 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  79 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  81 */       switch (targetId) {
/*     */         
/*     */         case 203631:
/*  84 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  87 */               if (var == 0)
/*  88 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  89 */               if (var == 6)
/*  90 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */             case 1012:
/*  92 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 63));
/*     */               break;
/*     */             case 10000:
/*     */             case 10001:
/*  96 */               if (var == 0 || var == 6) {
/*     */                 
/*  98 */                 qs.setQuestVarById(0, var + 1);
/*  99 */                 updateQuestStatus(player, qs);
/* 100 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 101 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */         case 203621:
/* 106 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 109 */               if (var == 7)
/* 110 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 111 */               if (var == 8)
/* 112 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */               break;
/*     */             case 10002:
/*     */             case 10003:
/* 116 */               if (var == 7 || var == 9) {
/*     */                 
/* 118 */                 if (var == 9 && 
/* 119 */                   !ItemService.addItems(player, Collections.singletonList(new QuestItems(182203019, 1))))
/* 120 */                   return true; 
/* 121 */                 qs.setQuestVarById(0, var + 1);
/* 122 */                 updateQuestStatus(player, qs);
/* 123 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 124 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 33:
/* 128 */               if (var == 8) {
/*     */                 
/* 130 */                 if (QuestService.collectItemCheck(env, true)) {
/*     */                   
/* 132 */                   qs.setQuestVarById(0, var + 1);
/* 133 */                   updateQuestStatus(player, qs);
/* 134 */                   return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
/*     */                 } 
/*     */                 
/* 137 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */               } 
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 143 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 145 */       if (targetId == 203631) {
/*     */         
/* 147 */         if (env.getDialogId().intValue() == -1) {
/* 148 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/* 150 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/* 159 */     final Player player = env.getPlayer();
/* 160 */     final int id = item.getItemTemplate().getTemplateId();
/* 161 */     final int itemObjId = item.getObjectId();
/*     */     
/* 163 */     if (id != 182203019)
/* 164 */       return false; 
/* 165 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.Q2016))
/* 166 */       return false; 
/* 167 */     final QuestState qs = player.getQuestStateList().getQuestState(2016);
/* 168 */     if (qs == null)
/* 169 */       return false; 
/* 170 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 171 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 175 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 176 */             ItemService.removeItemFromInventory(player, item);
/* 177 */             qs.setStatus(QuestStatus.REWARD);
/* 178 */             _2016FearThis.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 187 */     Player player = env.getPlayer();
/* 188 */     QuestState qs = player.getQuestStateList().getQuestState(2016);
/* 189 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     int targetId = 0;
/* 194 */     int var = 0;
/* 195 */     if (env.getVisibleObject() instanceof Npc)
/* 196 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 197 */     switch (targetId) {
/*     */       
/*     */       case 210455:
/*     */       case 210458:
/*     */       case 214032:
/* 202 */         var = qs.getQuestVarById(0);
/* 203 */         if (var < 6) {
/*     */           
/* 205 */           qs.setQuestVarById(0, var + 1);
/* 206 */           updateQuestStatus(player, qs);
/*     */         } 
/*     */         break;
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 215 */     Player player = env.getPlayer();
/* 216 */     QuestState qs = player.getQuestStateList().getQuestState(2016);
/* 217 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 218 */       return false; 
/* 219 */     qs.setStatus(QuestStatus.START);
/* 220 */     updateQuestStatus(player, qs);
/* 221 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2016FearThis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */