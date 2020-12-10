/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
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
/*     */ public class SM_ATTACK
/*     */   extends AionServerPacket
/*     */ {
/*     */   private int attackno;
/*     */   private int time;
/*     */   private int type;
/*     */   private List<AttackResult> attackList;
/*     */   private Creature attacker;
/*     */   private Creature target;
/*     */   
/*     */   public SM_ATTACK(Creature attacker, Creature target, int attackno, int time, int type, List<AttackResult> attackList) {
/*  43 */     this.attacker = attacker;
/*  44 */     this.target = target;
/*  45 */     this.attackno = attackno;
/*  46 */     this.time = time;
/*  47 */     this.type = type;
/*  48 */     this.attackList = attackList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  57 */     writeD(buf, this.attacker.getObjectId());
/*  58 */     writeC(buf, this.attackno);
/*  59 */     writeH(buf, this.time);
/*  60 */     writeC(buf, this.type);
/*  61 */     writeD(buf, this.target.getObjectId());
/*     */     
/*  63 */     int attackerMaxHp = this.attacker.getLifeStats().getMaxHp();
/*  64 */     int attackerCurrHp = this.attacker.getLifeStats().getCurrentHp();
/*  65 */     int targetMaxHp = this.target.getLifeStats().getMaxHp();
/*  66 */     int targetCurrHp = this.target.getLifeStats().getCurrentHp();
/*     */     
/*  68 */     writeC(buf, 100 * targetCurrHp / targetMaxHp);
/*  69 */     writeC(buf, 100 * attackerCurrHp / attackerMaxHp);
/*     */ 
/*     */     
/*  72 */     switch (((AttackResult)this.attackList.get(0)).getAttackStatus().getId()) {
/*     */       
/*     */       case -60:
/*     */       case 4:
/*  76 */         writeH(buf, 32);
/*     */         break;
/*     */       case -62:
/*     */       case 2:
/*  80 */         writeH(buf, 64);
/*     */         break;
/*     */       case -64:
/*     */       case 0:
/*  84 */         writeH(buf, 128);
/*     */         break;
/*     */       case -58:
/*     */       case 6:
/*  88 */         writeH(buf, 256);
/*     */         break;
/*     */       default:
/*  91 */         writeH(buf, 0);
/*     */         break;
/*     */     } 
/*     */     
/*  95 */     writeC(buf, this.attackList.size());
/*  96 */     for (AttackResult attack : this.attackList) {
/*     */       
/*  98 */       writeD(buf, attack.getDamage());
/*  99 */       writeC(buf, attack.getAttackStatus().getId());
/* 100 */       writeC(buf, attack.getShieldType());
/*     */       
/* 102 */       switch (attack.getShieldType()) {
/*     */         
/*     */         case 1:
/* 105 */           writeD(buf, 0);
/* 106 */           writeD(buf, 0);
/* 107 */           writeD(buf, 0);
/* 108 */           writeD(buf, 0);
/* 109 */           writeD(buf, 0);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 116 */     writeC(buf, 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ATTACK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */