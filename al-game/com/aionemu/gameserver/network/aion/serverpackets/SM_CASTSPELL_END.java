/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
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
/*     */ public class SM_CASTSPELL_END
/*     */   extends AionServerPacket
/*     */ {
/*     */   private Creature attacker;
/*     */   private Creature target;
/*     */   private int spellid;
/*     */   private int level;
/*     */   private int cooldown;
/*     */   private List<Effect> effects;
/*     */   private int spellStatus;
/*     */   private float x;
/*     */   private float y;
/*     */   private float z;
/*     */   private int targetType;
/*     */   private boolean chainSuccess;
/*     */   
/*     */   public SM_CASTSPELL_END(Creature attacker, Creature target, List<Effect> effects, int spellid, int level, int cooldown, boolean chainSuccess, int spellStatus) {
/*  51 */     this.attacker = attacker;
/*  52 */     this.target = target;
/*  53 */     this.spellid = spellid;
/*  54 */     this.level = level;
/*  55 */     this.effects = effects;
/*  56 */     this.cooldown = cooldown;
/*  57 */     this.spellStatus = spellStatus;
/*  58 */     this.chainSuccess = chainSuccess;
/*  59 */     this.targetType = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_CASTSPELL_END(Creature attacker, Creature target, List<Effect> effects, int spellid, int level, int cooldown, boolean chainSuccess, int spellStatus, float x, float y, float z) {
/*  65 */     this(attacker, target, effects, spellid, level, cooldown, chainSuccess, spellStatus);
/*  66 */     this.x = x;
/*  67 */     this.y = y;
/*  68 */     this.z = z;
/*  69 */     this.targetType = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  79 */     writeD(buf, this.attacker.getObjectId());
/*  80 */     writeC(buf, this.targetType);
/*  81 */     switch (this.targetType) {
/*     */       
/*     */       case 0:
/*  84 */         writeD(buf, this.target.getObjectId());
/*     */         break;
/*     */       case 1:
/*  87 */         writeF(buf, this.x);
/*  88 */         writeF(buf, this.y);
/*  89 */         writeF(buf, this.z + 0.4F);
/*     */         break;
/*     */     } 
/*  92 */     writeH(buf, this.spellid);
/*  93 */     writeC(buf, this.level);
/*  94 */     writeD(buf, this.cooldown);
/*  95 */     writeH(buf, 560);
/*  96 */     writeC(buf, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (this.chainSuccess) {
/* 104 */       writeH(buf, 32);
/*     */     } else {
/* 106 */       writeH(buf, 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     writeC(buf, 0);
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
/* 132 */     writeH(buf, this.effects.size());
/* 133 */     for (Effect effect : this.effects) {
/*     */       
/* 135 */       writeD(buf, effect.getEffected().getObjectId());
/* 136 */       writeC(buf, 0);
/*     */       
/* 138 */       int attackerMaxHp = this.attacker.getLifeStats().getMaxHp();
/* 139 */       int attackerCurrHp = this.attacker.getLifeStats().getCurrentHp();
/* 140 */       int targetMaxHp = this.target.getLifeStats().getMaxHp();
/* 141 */       int targetCurrHp = this.target.getLifeStats().getCurrentHp();
/*     */       
/* 143 */       writeC(buf, 100 * targetCurrHp / targetMaxHp);
/* 144 */       writeC(buf, 100 * attackerCurrHp / attackerMaxHp);
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
/* 160 */       writeC(buf, this.spellStatus);
/*     */       
/* 162 */       switch (this.spellStatus) {
/*     */         
/*     */         case 1:
/*     */         case 2:
/*     */         case 4:
/*     */         case 8:
/* 168 */           writeF(buf, this.target.getX());
/* 169 */           writeF(buf, this.target.getY());
/* 170 */           writeF(buf, this.target.getZ() + 0.4F);
/*     */           break;
/*     */         case 16:
/* 173 */           writeC(buf, this.target.getHeading());
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 179 */       writeC(buf, 16);
/* 180 */       writeC(buf, 0);
/*     */       
/* 182 */       writeC(buf, 1);
/* 183 */       writeC(buf, 0);
/* 184 */       writeD(buf, effect.getReserved1());
/* 185 */       writeC(buf, effect.getAttackStatus().getId());
/* 186 */       writeC(buf, effect.getShieldDefense());
/*     */       
/* 188 */       switch (effect.getShieldDefense()) {
/*     */         
/*     */         case 1:
/* 191 */           writeD(buf, 0);
/* 192 */           writeD(buf, 0);
/* 193 */           writeD(buf, 0);
/* 194 */           writeD(buf, 0);
/* 195 */           writeD(buf, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CASTSPELL_END.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */