/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
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
/*     */ public class SM_EMOTION
/*     */   extends AionServerPacket
/*     */ {
/*     */   private int senderObjectId;
/*     */   private EmotionType emotionType;
/*     */   private int emotion;
/*     */   private int targetObjectId;
/*  59 */   private float speed = 6.0F;
/*     */ 
/*     */   
/*     */   private int state;
/*     */ 
/*     */   
/*     */   private int baseAttackSpeed;
/*     */ 
/*     */   
/*     */   private int currentAttackSpeed;
/*     */ 
/*     */   
/*     */   private float x;
/*     */ 
/*     */   
/*     */   private float y;
/*     */   
/*     */   private float z;
/*     */   
/*     */   private byte heading;
/*     */ 
/*     */   
/*     */   public SM_EMOTION(Creature creature, EmotionType emotionType) {
/*  82 */     this(creature, emotionType, 0, 0);
/*     */   }
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
/*     */   public SM_EMOTION(Creature creature, EmotionType emotionType, int emotion, int targetObjectId) {
/*  99 */     this.senderObjectId = creature.getObjectId();
/* 100 */     this.emotionType = emotionType;
/* 101 */     this.emotion = emotion;
/* 102 */     this.targetObjectId = targetObjectId;
/* 103 */     this.state = creature.getState();
/* 104 */     this.baseAttackSpeed = creature.getGameStats().getBaseStat(StatEnum.ATTACK_SPEED);
/* 105 */     this.currentAttackSpeed = creature.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED);
/*     */     
/* 107 */     if (creature.isInState(CreatureState.FLYING)) {
/* 108 */       this.speed = creature.getGameStats().getCurrentStat(StatEnum.FLY_SPEED) / 1000.0F;
/*     */     } else {
/* 110 */       this.speed = creature.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_EMOTION(int doorId) {
/* 120 */     this.senderObjectId = doorId;
/* 121 */     this.emotionType = EmotionType.SWITCH_DOOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_EMOTION(Player player, EmotionType emotionType, int emotion, float x, float y, float z, byte heading, int targetObjectId) {
/* 130 */     this.senderObjectId = player.getObjectId();
/* 131 */     this.emotionType = emotionType;
/* 132 */     this.emotion = emotion;
/* 133 */     this.x = x;
/* 134 */     this.y = y;
/* 135 */     this.z = z;
/* 136 */     this.heading = heading;
/* 137 */     this.targetObjectId = targetObjectId;
/*     */     
/* 139 */     if (player.isInState(CreatureState.FLYING)) {
/* 140 */       this.speed = player.getGameStats().getCurrentStat(StatEnum.FLY_SPEED) / 1000.0F;
/*     */     } else {
/* 142 */       this.speed = player.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F;
/*     */     } 
/* 144 */     this.state = player.getState();
/* 145 */     this.baseAttackSpeed = player.getGameStats().getBaseStat(StatEnum.ATTACK_SPEED);
/* 146 */     this.currentAttackSpeed = player.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 155 */     writeD(buf, this.senderObjectId);
/* 156 */     writeC(buf, this.emotionType.getTypeId());
/* 157 */     switch (this.emotionType) {
/*     */ 
/*     */       
/*     */       case SELECT_TARGET:
/* 161 */         writeH(buf, this.state);
/* 162 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case JUMP:
/* 166 */         writeH(buf, this.state);
/* 167 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case SIT:
/* 171 */         writeH(buf, this.state);
/* 172 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case STAND:
/* 176 */         writeH(buf, this.state);
/* 177 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case CHAIR_SIT:
/* 181 */         writeH(buf, this.state);
/* 182 */         writeF(buf, this.speed);
/* 183 */         writeF(buf, this.x);
/* 184 */         writeF(buf, this.y);
/* 185 */         writeF(buf, this.z);
/* 186 */         writeC(buf, this.heading);
/*     */         return;
/*     */       
/*     */       case CHAIR_UP:
/* 190 */         writeH(buf, this.state);
/* 191 */         writeF(buf, this.speed);
/* 192 */         writeF(buf, this.x);
/* 193 */         writeF(buf, this.y);
/* 194 */         writeF(buf, this.z);
/* 195 */         writeC(buf, this.heading);
/*     */         return;
/*     */       
/*     */       case START_FLYTELEPORT:
/* 199 */         writeH(buf, this.state);
/* 200 */         writeF(buf, this.speed);
/* 201 */         writeD(buf, this.emotion);
/*     */         return;
/*     */       
/*     */       case LAND_FLYTELEPORT:
/* 205 */         writeH(buf, this.state);
/* 206 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case FLY:
/* 210 */         writeH(buf, this.state);
/* 211 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case LAND:
/* 215 */         writeH(buf, this.state);
/* 216 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case DIE:
/* 220 */         writeH(buf, this.state);
/* 221 */         writeF(buf, this.speed);
/* 222 */         writeD(buf, this.targetObjectId);
/*     */         return;
/*     */       
/*     */       case RESURRECT:
/* 226 */         writeH(buf, this.state);
/* 227 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case EMOTE:
/* 231 */         writeH(buf, this.state);
/* 232 */         writeF(buf, this.speed);
/* 233 */         writeD(buf, this.targetObjectId);
/* 234 */         writeH(buf, this.emotion);
/* 235 */         writeC(buf, 1);
/*     */         return;
/*     */       
/*     */       case ATTACKMODE:
/* 239 */         writeH(buf, this.state);
/* 240 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case NEUTRALMODE:
/* 244 */         writeH(buf, this.state);
/* 245 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case WALK:
/* 249 */         writeH(buf, this.state);
/* 250 */         writeF(buf, this.speed - this.speed * 75.0F / 100.0F);
/*     */         return;
/*     */       
/*     */       case RUN:
/* 254 */         writeH(buf, this.state);
/* 255 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case SWITCH_DOOR:
/* 259 */         writeH(buf, 9);
/* 260 */         writeD(buf, 0);
/*     */         return;
/*     */       
/*     */       case START_EMOTE:
/* 264 */         writeH(buf, this.state);
/* 265 */         writeF(buf, this.speed);
/* 266 */         writeH(buf, this.baseAttackSpeed);
/* 267 */         writeH(buf, this.currentAttackSpeed);
/*     */         return;
/*     */       
/*     */       case OPEN_PRIVATESHOP:
/* 271 */         writeH(buf, this.state);
/* 272 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case CLOSE_PRIVATESHOP:
/* 276 */         writeH(buf, this.state);
/* 277 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case START_EMOTE2:
/* 281 */         writeH(buf, this.state);
/* 282 */         writeF(buf, this.speed);
/* 283 */         writeH(buf, this.baseAttackSpeed);
/* 284 */         writeH(buf, this.currentAttackSpeed);
/*     */         return;
/*     */       
/*     */       case POWERSHARD_ON:
/* 288 */         writeH(buf, this.state);
/* 289 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case POWERSHARD_OFF:
/* 293 */         writeH(buf, this.state);
/* 294 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case ATTACKMODE2:
/* 298 */         writeH(buf, this.state);
/* 299 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case NEUTRALMODE2:
/* 303 */         writeH(buf, this.state);
/* 304 */         writeF(buf, this.speed);
/*     */         return;
/*     */       
/*     */       case START_LOOT:
/* 308 */         writeH(buf, this.state);
/* 309 */         writeF(buf, this.speed);
/* 310 */         writeD(buf, this.targetObjectId);
/*     */         return;
/*     */       
/*     */       case END_LOOT:
/* 314 */         writeH(buf, this.state);
/* 315 */         writeF(buf, this.speed);
/* 316 */         writeD(buf, this.targetObjectId);
/*     */         return;
/*     */       
/*     */       case START_QUESTLOOT:
/* 320 */         writeH(buf, this.state);
/* 321 */         writeF(buf, this.speed);
/* 322 */         writeD(buf, this.targetObjectId);
/*     */         return;
/*     */       
/*     */       case END_QUESTLOOT:
/* 326 */         writeH(buf, this.state);
/* 327 */         writeF(buf, this.speed);
/* 328 */         writeD(buf, this.targetObjectId);
/*     */         return;
/*     */     } 
/* 331 */     writeH(buf, this.state);
/* 332 */     writeF(buf, this.speed);
/* 333 */     if (this.targetObjectId != 0)
/*     */     {
/* 335 */       writeD(buf, this.targetObjectId);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_EMOTION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */