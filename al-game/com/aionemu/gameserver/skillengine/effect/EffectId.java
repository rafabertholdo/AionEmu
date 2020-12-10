/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EffectId
/*    */ {
/* 25 */   BUFF(0),
/* 26 */   POISON(1),
/* 27 */   BLEED(2),
/* 28 */   PARALYZE(4),
/* 29 */   SLEEP(8),
/* 30 */   ROOT(16),
/* 31 */   BLIND(32),
/* 32 */   UNKNOWN(64),
/* 33 */   DISEASE(128),
/* 34 */   SILENCE(256),
/* 35 */   FEAR(512),
/* 36 */   CURSE(1024),
/* 37 */   CHAOS(2056),
/* 38 */   STUN(4096),
/* 39 */   PETRIFICATION(8192),
/* 40 */   STUMBLE(16384),
/* 41 */   STAGGER(32768),
/* 42 */   OPENAERIAL(65536),
/* 43 */   SNARE(131072),
/* 44 */   SLOW(262144),
/* 45 */   SPIN(524288),
/* 46 */   BLOCKADE(1048576),
/* 47 */   UNKNOWN2(2097152),
/* 48 */   CANNOT_MOVE(4194304),
/* 49 */   SHAPECHANGE(8388608),
/* 50 */   KNOCKBACK(16777216),
/* 51 */   INVISIBLE_RELATED(536870912),
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   CANT_ATTACK_STATE(SPIN.effectId | SLEEP.effectId | STUN.effectId | STUMBLE.effectId | STAGGER.effectId | OPENAERIAL.effectId | PARALYZE.effectId | FEAR.effectId | CANNOT_MOVE.effectId),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   CANT_MOVE_STATE(SPIN.effectId | ROOT.effectId | SLEEP.effectId | STUMBLE.effectId | STUN.effectId | STAGGER.effectId | OPENAERIAL.effectId | PARALYZE.effectId | CANNOT_MOVE.effectId);
/*    */   
/*    */   private int effectId;
/*    */ 
/*    */   
/*    */   EffectId(int effectId) {
/* 73 */     this.effectId = effectId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEffectId() {
/* 78 */     return this.effectId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\EffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */