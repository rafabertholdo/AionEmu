package com.aionemu.gameserver.skillengine.effect;





















public enum EffectId
{
  BUFF(0),
  POISON(1),
  BLEED(2),
  PARALYZE(4),
  SLEEP(8),
  ROOT(16),
  BLIND(32),
  UNKNOWN(64),
  DISEASE(128),
  SILENCE(256),
  FEAR(512),
  CURSE(1024),
  CHAOS(2056),
  STUN(4096),
  PETRIFICATION(8192),
  STUMBLE(16384),
  STAGGER(32768),
  OPENAERIAL(65536),
  SNARE(131072),
  SLOW(262144),
  SPIN(524288),
  BLOCKADE(1048576),
  UNKNOWN2(2097152),
  CANNOT_MOVE(4194304),
  SHAPECHANGE(8388608),
  KNOCKBACK(16777216),
  INVISIBLE_RELATED(536870912),



  
  CANT_ATTACK_STATE(SPIN.effectId | SLEEP.effectId | STUN.effectId | STUMBLE.effectId | STAGGER.effectId | OPENAERIAL.effectId | PARALYZE.effectId | FEAR.effectId | CANNOT_MOVE.effectId),









  
  CANT_MOVE_STATE(SPIN.effectId | ROOT.effectId | SLEEP.effectId | STUMBLE.effectId | STUN.effectId | STAGGER.effectId | OPENAERIAL.effectId | PARALYZE.effectId | CANNOT_MOVE.effectId);
  
  private int effectId;

  
  EffectId(int effectId) {
    this.effectId = effectId;
  }

  
  public int getEffectId() {
    return this.effectId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\EffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
