/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _2021KnowYourEnemy
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2021;
/*     */   
/*     */   public _2021KnowYourEnemy() {
/*  44 */     super(Integer.valueOf(2021));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.addQuestLvlUp(2021);
/*  51 */     this.qe.setNpcQuestData(203669).addOnTalkEvent(2021);
/*  52 */     this.qe.setQuestEnterZone(ZoneName.BLACK_CLAW_OUTPOST_220030000).add(2021);
/*  53 */     this.qe.setNpcQuestData(700099).addOnKillEvent(2021);
/*  54 */     this.qe.setNpcQuestData(203557).addOnTalkEvent(2021);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  60 */     final Player player = env.getPlayer();
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(2021);
/*  62 */     if (qs == null) {
/*  63 */       return false;
/*     */     }
/*  65 */     int var = qs.getQuestVarById(0);
/*  66 */     int targetId = 0;
/*  67 */     if (env.getVisibleObject() instanceof Npc) {
/*  68 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  70 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  72 */       switch (targetId) {
/*     */         
/*     */         case 203669:
/*  75 */           switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/*  78 */               if (var == 0)
/*  79 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  80 */               if (var == 2) {
/*     */                 
/*  82 */                 player.setTransformedModelId(0);
/*  83 */                 PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
/*  84 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               } 
/*  86 */               if (var == 6)
/*  87 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */               break;
/*     */             case 1012:
/*  90 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 65));
/*     */               break;
/*     */             case 10000:
/*  93 */               if (var == 0) {
/*     */                 
/*  95 */                 player.setTransformedModelId(202501);
/*  96 */                 PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
/*  97 */                 qs.setQuestVarById(0, var + 1);
/*  98 */                 updateQuestStatus(player, qs);
/*  99 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 100 */                 ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                     {
/*     */                       public void run()
/*     */                       {
/* 104 */                         if (player == null || player.getTransformedModelId() == 0)
/*     */                           return; 
/* 106 */                         player.setTransformedModelId(0);
/* 107 */                         PacketSendUtility.broadcastPacketAndReceive((VisibleObject)player, (AionServerPacket)new SM_TRANSFORM((Creature)player));
/*     */                       }
/*     */                     }300000L);
/* 110 */                 return true;
/*     */               } 
/*     */               break;
/*     */             case 10001:
/* 114 */               if (var == 2) {
/*     */                 
/* 116 */                 qs.setQuestVarById(0, var + 1);
/* 117 */                 updateQuestStatus(player, qs);
/* 118 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 119 */                 return true;
/*     */               } 
/*     */             case 10002:
/* 122 */               if (var == 6) {
/*     */                 
/* 124 */                 qs.setStatus(QuestStatus.REWARD);
/* 125 */                 updateQuestStatus(player, qs);
/* 126 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 127 */                 return true;
/*     */               }  break;
/*     */           } 
/*     */           break;
/*     */       } 
/* 132 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 134 */       if (targetId == 203557) {
/*     */         
/* 136 */         if (env.getDialogId().intValue() == -1) {
/* 137 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
/*     */         }
/* 139 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 148 */     Player player = env.getPlayer();
/* 149 */     QuestState qs = player.getQuestStateList().getQuestState(2021);
/* 150 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 151 */       return false;
/*     */     }
/* 153 */     int var = qs.getQuestVarById(0);
/* 154 */     int targetId = 0;
/* 155 */     if (env.getVisibleObject() instanceof Npc) {
/* 156 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 158 */     if (targetId == 700099 && var >= 3 && var < 6) {
/*     */       
/* 160 */       qs.setQuestVarById(0, var + 1);
/* 161 */       updateQuestStatus(player, qs);
/* 162 */       return true;
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
/* 170 */     if (zoneName != ZoneName.BLACK_CLAW_OUTPOST_220030000)
/* 171 */       return false; 
/* 172 */     Player player = env.getPlayer();
/* 173 */     QuestState qs = player.getQuestStateList().getQuestState(2021);
/* 174 */     if (qs == null)
/* 175 */       return false; 
/* 176 */     if (qs.getQuestVarById(0) == 1) {
/*     */       
/* 178 */       qs.setQuestVarById(0, 2);
/* 179 */       updateQuestStatus(player, qs);
/* 180 */       return true;
/*     */     } 
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 188 */     Player player = env.getPlayer();
/* 189 */     QuestState qs = player.getQuestStateList().getQuestState(2021);
/* 190 */     boolean lvlCheck = QuestService.checkLevelRequirement(2021, player.getCommonData().getLevel());
/* 191 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
/* 192 */       return false; 
/* 193 */     qs.setStatus(QuestStatus.START);
/* 194 */     updateQuestStatus(player, qs);
/* 195 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2021KnowYourEnemy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */