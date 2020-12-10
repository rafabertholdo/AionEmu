package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_OWNER_REMOVE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_PANEL_REMOVE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.services.LifeStatsRestoreService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.List;






















public class SummonController
  extends CreatureController<Summon>
{
  private long lastAttackMilis = 0L;


  
  public void notSee(VisibleObject object, boolean isOutOfRange) {
    super.notSee(object, isOutOfRange);
    if (getOwner().getMaster() == null) {
      return;
    }
    if (object.getObjectId() == getOwner().getMaster().getObjectId())
    {
      release(UnsummonType.DISTANCE);
    }
  }


  
  public Summon getOwner() {
    return (Summon)super.getOwner();
  }




  
  public void release(final UnsummonType unsummonType) {
    final Summon owner = getOwner();
    
    if (owner.getMode() == Summon.SummonMode.RELEASE)
      return; 
    owner.setMode(Summon.SummonMode.RELEASE);
    
    final Player master = owner.getMaster();
    final int summonObjId = owner.getObjectId();
    
    switch (unsummonType) {
      
      case COMMAND:
        PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_UNSUMMON(getOwner().getNameId()));
        PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
        break;
      case DISTANCE:
        PacketSendUtility.sendPacket(getOwner().getMaster(), (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_UNSUMMON_BY_TOO_DISTANCE());
        
        PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
        break;
    } 



    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          
          public void run()
          {
            owner.setMaster(null);
            master.setSummon(null);
            owner.getController().delete();
            
            switch (unsummonType) {
              
              case COMMAND:
              case DISTANCE:
              case UNSPECIFIED:
                PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_DISMISSED(SummonController.this.getOwner().getNameId()));
                
                PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_OWNER_REMOVE(summonObjId));

                
                PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_PANEL_REMOVE());
                break;
            } 
          }
        }5000L);
  }






  
  public void restMode() {
    getOwner().setMode(Summon.SummonMode.REST);
    Player master = getOwner().getMaster();
    PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_RESTMODE(getOwner().getNameId()));
    PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
    checkCurrentHp();
  }

  
  private void checkCurrentHp() {
    if (!getOwner().getLifeStats().isFullyRestoredHp()) {
      getOwner().getController().addNewTask(TaskId.RESTORE, LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(getOwner().getLifeStats()));
    }
  }




  
  public void guardMode() {
    getOwner().setMode(Summon.SummonMode.GUARD);
    Player master = getOwner().getMaster();
    PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_GUARDMODE(getOwner().getNameId()));
    PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
    checkCurrentHp();
  }




  
  public void attackMode() {
    getOwner().setMode(Summon.SummonMode.ATTACK);
    Player master = getOwner().getMaster();
    PacketSendUtility.sendPacket(master, (AionServerPacket)SM_SYSTEM_MESSAGE.SUMMON_ATTACKMODE(getOwner().getNameId()));
    PacketSendUtility.sendPacket(master, (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
    getOwner().getController().cancelTask(TaskId.RESTORE);
  }


  
  public void attackTarget(Creature target) {
    Summon summon = getOwner();
    Player master = getOwner().getMaster();
    if (!summon.canAttack()) {
      return;
    }
    if (!RestrictionsManager.canAttack(master, (VisibleObject)target)) {
      return;
    }
    if (!summon.isEnemy((VisibleObject)target)) {
      return;
    }
    int attackSpeed = summon.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED);
    long milis = System.currentTimeMillis();
    if (milis - this.lastAttackMilis < attackSpeed) {
      return;
    }



    
    this.lastAttackMilis = milis;



    
    super.attackTarget(target);
    
    List<AttackResult> attackList = AttackUtil.calculateAttackResult((Creature)summon, target);
    
    int damage = 0;
    for (AttackResult result : attackList)
    {
      damage += result.getDamage();
    }
    
    int attackType = 0;
    PacketSendUtility.broadcastPacket((VisibleObject)summon, (AionServerPacket)new SM_ATTACK((Creature)summon, target, summon.getGameStats().getAttackCounter(), 274, attackType, attackList));

    
    target.getController().onAttack((Creature)summon, damage);
    summon.getGameStats().increaseAttackCounter();
  }



  
  public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
    if (getOwner().getLifeStats().isAlreadyDead()) {
      return;
    }
    
    if (getOwner().getMode() == Summon.SummonMode.RELEASE) {
      return;
    }
    super.onAttack(creature, skillId, type, damage);
    getOwner().getLifeStats().reduceHp(damage, creature);
    PacketSendUtility.broadcastPacket((VisibleObject)getOwner(), (AionServerPacket)new SM_ATTACK_STATUS((Creature)getOwner(), SM_ATTACK_STATUS.TYPE.REGULAR, 0, damage));
    
    PacketSendUtility.sendPacket(getOwner().getMaster(), (AionServerPacket)new SM_SUMMON_UPDATE(getOwner()));
  }


  
  public void onDie(Creature lastAttacker) {
    super.onDie(lastAttacker);
    release(UnsummonType.UNSPECIFIED);
    Summon owner = getOwner();
    PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.DIE, 0, (lastAttacker == null) ? 0 : lastAttacker.getObjectId()));
  }


  
  public void useSkill(int skillId, Creature target) {
    Summon summon = getOwner();
    
    Skill skill = SkillEngine.getInstance().getSkill((Creature)summon, skillId, 1, (VisibleObject)target);
    if (skill != null)
    {
      skill.useSkill();
    }
  }
  
  public enum UnsummonType
  {
    LOGOUT,
    DISTANCE,
    COMMAND,
    UNSPECIFIED;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\SummonController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
