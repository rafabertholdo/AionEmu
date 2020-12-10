package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.NpcGameStats;
import com.aionemu.gameserver.model.templates.TradeListTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOKATOBJECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SELL_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRADELIST;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.services.CraftSkillUpdateService;
import com.aionemu.gameserver.services.CubeExpandService;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.RespawnService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.services.TradeService;
import com.aionemu.gameserver.services.WarehouseService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.util.List;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

























public class NpcController
  extends CreatureController<Npc>
{
  private static final Logger log = Logger.getLogger(NpcController.class);


  
  public void notSee(VisibleObject object, boolean isOutOfRange) {
    super.notSee(object, isOutOfRange);
    if (object instanceof Player || object instanceof com.aionemu.gameserver.model.gameobjects.Summon) {
      getOwner().getAi().handleEvent(Event.NOT_SEE_PLAYER);
    }
  }

  
  public void see(VisibleObject object) {
    super.see(object);
    Npc owner = getOwner();
    owner.getAi().handleEvent(Event.SEE_CREATURE);
    if (object instanceof Player) {
      
      owner.getAi().handleEvent(Event.SEE_PLAYER);

      
      if (owner.getMoveController().isWalking()) {
        PacketSendUtility.sendPacket((Player)object, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.WALK));
      }
    } else if (object instanceof com.aionemu.gameserver.model.gameobjects.Summon) {
      
      owner.getAi().handleEvent(Event.SEE_PLAYER);
    } 
  }


  
  public void onRespawn() {
    super.onRespawn();
    
    cancelTask(TaskId.DECAY);
    Npc owner = getOwner();

    
    if (owner.getObjectTemplate().getState() != 0) {
      owner.setState(owner.getObjectTemplate().getState());
    } else {
      owner.setState(CreatureState.NPC_IDLE);
    } 
    owner.getLifeStats().setCurrentHpPercent(100);
    if (owner.getSpawn().getNpcFlyState() != 0)
    {
      owner.setState(CreatureState.FLYING);
    }
  }

  
  public void onDespawn(boolean forced) {
    if (forced) {
      cancelTask(TaskId.DECAY);
    }
    Npc owner = getOwner();
    if (owner == null || !owner.isSpawned()) {
      return;
    }
    World.getInstance().despawn((VisibleObject)owner);
  }


  
  public void onDie(Creature lastAttacker) {
    super.onDie(lastAttacker);
    Npc owner = getOwner();
    
    addTask(TaskId.DECAY, RespawnService.scheduleDecayTask(getOwner()));
    scheduleRespawn();
    
    PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_EMOTION((Creature)owner, EmotionType.DIE, 0, (lastAttacker == null) ? 0 : lastAttacker.getObjectId()));


    
    doReward();
    
    owner.getAi().stop();

    
    owner.setTarget(null);
    PacketSendUtility.broadcastPacket((VisibleObject)owner, (AionServerPacket)new SM_LOOKATOBJECT((VisibleObject)owner));
  }


  
  public Npc getOwner() {
    return (Npc)super.getOwner();
  }


  
  public void onDialogRequest(Player player) {
    getOwner().getAi().handleEvent(Event.TALK);
    
    if (QuestEngine.getInstance().onDialog(new QuestEnv((VisibleObject)getOwner(), player, Integer.valueOf(0), Integer.valueOf(-1)))) {
      return;
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(getOwner().getObjectId(), 10));
  }




  
  public void onDelete() {
    if (getOwner().isInWorld()) {
      
      getOwner().getAi().clearDesires();
      onDespawn(true);
      delete();
    } 
  }

  
  public void onDialogSelect(int dialogId, final Player player, int questId) {
    TradeListTemplate tradeListTemplate;
    final long expLost;
    double factor;
    final int price;
    RequestResponseHandler responseHandler;
    Npc npc = getOwner();
    if (!MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F))
      return; 
    int targetObjectId = npc.getObjectId();
    
    if (QuestEngine.getInstance().onDialog(new QuestEnv((VisibleObject)npc, player, Integer.valueOf(questId), Integer.valueOf(dialogId))))
      return; 
    switch (dialogId) {
      
      case 2:
        tradeListTemplate = DataManager.TRADE_LIST_DATA.getTradeListTemplate(npc.getNpcId());
        if (tradeListTemplate == null) {
          
          PacketSendUtility.sendMessage(player, "Buy list is missing!!");
        } else {
          
          int tradeModifier = tradeListTemplate.getSellPriceRate();
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TRADELIST(npc, TradeService.getTradeListData().getTradeListTemplate(npc.getNpcId()), player.getPrices().getVendorBuyModifier() * tradeModifier / 100));
        } 

      
      case 3:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SELL_ITEM(targetObjectId, player.getPrices().getVendorSellModifier()));

      
      case 4:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 1));

      
      case 5:
        if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 2));
        }
        else {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_CREATE_TOO_FAR_FROM_NPC());
        } 

      
      case 6:
        if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
          
          LegionService.getInstance().requestDisbandLegion((Creature)npc, player);
        }
        else {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_TOO_FAR_FROM_NPC());
        } 

      
      case 7:
        if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {

          
          LegionService.getInstance().recreateLegion(npc, player);
        }
        else {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.LEGION_DISPERSE_TOO_FAR_FROM_NPC());
        } 

      
      case 20:
        if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {

          
          if (!RestrictionsManager.canUseWarehouse(player)) {
            return;
          }
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 26));
          WarehouseService.sendWarehouseInfo(player, true);
        } 

      
      case 27:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 13));

      
      case 29:
        expLost = player.getCommonData().getExpRecoverable();
        factor = (expLost < 1000000L) ? (0.25D - 1.5E-7D * expLost) : 0.1D;

        
        price = (int)(expLost * factor);
        
        responseHandler = new RequestResponseHandler((Creature)npc)
          {
            public void acceptRequest(Creature requester, Player responder)
            {
              if (ItemService.decreaseKinah(player, price)) {
                
                PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EXP(String.valueOf(expLost)));
                PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SOUL_HEALED());
                player.getCommonData().resetRecoverableExp();
              }
              else {
                
                PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(price));
              } 
            }




            
            public void denyRequest(Creature requester, Player responder) {}
          };
        if (player.getCommonData().getExpRecoverable() > 0L) {
          
          boolean result = player.getResponseRequester().putRequest(160011, responseHandler);
          
          if (result)
          {
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(160011, 0, new Object[] { String.valueOf(price) }));
          
          }
        
        }
        else {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.DONT_HAVE_RECOVERED_EXP());
        } 
      
      case 30:
        switch (npc.getNpcId()) {
          
          case 204089:
            TeleportService.teleportTo(player, 120010000, 1, 984.0F, 1543.0F, 222.1F, 0);
            break;
          case 203764:
            TeleportService.teleportTo(player, 110010000, 1, 1462.5F, 1326.1F, 564.1F, 0);
            break;
          case 203981:
            TeleportService.teleportTo(player, 210020000, 1, 439.3F, 422.2F, 274.3F, 0);
            break;
        } 
      
      case 31:
        switch (npc.getNpcId()) {
          
          case 204087:
            TeleportService.teleportTo(player, 120010000, 1, 1005.1F, 1528.9F, 222.1F, 0);
            break;
          case 203875:
            TeleportService.teleportTo(player, 110010000, 1, 1470.3F, 1343.5F, 563.7F, 21);
            break;
          case 203982:
            TeleportService.teleportTo(player, 210020000, 1, 446.2F, 431.1F, 274.5F, 0);
            break;
        } 
      
      
      case 35:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 21));

      
      case 36:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 20));

      
      case 37:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 19));

      
      case 38:
        TeleportService.showMap(player, targetObjectId, npc.getNpcId());


      
      case 39:
      case 40:
        CraftSkillUpdateService.getInstance().learnSkill(player, npc);

      
      case 41:
        CubeExpandService.expandCube(player, npc);
      
      case 42:
        WarehouseService.expandWarehouse(player, npc);

      
      case 47:
        if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F)) {
          LegionService.getInstance().openLegionWarehouse(player);
        }
      
      case 50:
        return;
      case 52:
        if (MathUtil.isInRange((VisibleObject)npc, (VisibleObject)player, 10.0F))
        {
          PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 28));
        }

      
      case 53:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "This feature is not available yet", ChatType.ANNOUNCEMENTS));


      
      case 60:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 29));

      
      case 61:
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, 30));
    } 
    
    if (questId > 0) {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, dialogId, questId));
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjectId, dialogId));
  }




  
  public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
    if (getOwner().getLifeStats().isAlreadyDead()) {
      return;
    }
    super.onAttack(creature, skillId, type, damage);
    
    Npc npc = getOwner();
    
    Creature actingCreature = creature.getActingCreature();
    if (actingCreature instanceof Player && 
      QuestEngine.getInstance().onAttack(new QuestEnv((VisibleObject)npc, (Player)actingCreature, Integer.valueOf(0), Integer.valueOf(0)))) {
      return;
    }
    AI<?> ai = npc.getAi();
    if (ai instanceof com.aionemu.gameserver.ai.npcai.DummyAi) {
      
      log.warn("CHECKPOINT: npc attacked without ai " + npc.getObjectTemplate().getTemplateId());
      return;
    } 
    for (VisibleObject obj : getOwner().getKnownList().getKnownObjects().values()) {
      
      if (obj instanceof Npc) {
        
        Npc tmp = (Npc)obj;
        if (getOwner().isSupportFrom(tmp) && MathUtil.isInRange((VisibleObject)getOwner(), obj, 10.0F))
        {
          tmp.getAggroList().addHate(creature, 10);
        }
      } 
    } 
    
    npc.getLifeStats().reduceHp(damage, actingCreature);
    
    PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_ATTACK_STATUS((Creature)npc, type, skillId, damage));
  }


  
  public void attackTarget(Creature target) {
    Npc npc = getOwner();



    
    if (npc == null || npc.getLifeStats().isAlreadyDead() || !npc.isSpawned()) {
      return;
    }
    if (!npc.canAttack()) {
      return;
    }
    AI<?> ai = npc.getAi();
    NpcGameStats gameStats = npc.getGameStats();
    
    if (target == null || target.getLifeStats().isAlreadyDead()) {
      
      ai.handleEvent(Event.MOST_HATED_CHANGED);

      
      return;
    } 

    
    super.attackTarget(target);



    
    List<AttackResult> attackList = AttackUtil.calculateAttackResult((Creature)npc, target);
    
    int damage = 0;
    for (AttackResult result : attackList)
    {
      damage += result.getDamage();
    }
    
    int attackType = 0;
    PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_ATTACK((Creature)npc, target, gameStats.getAttackCounter(), 274, attackType, attackList));

    
    target.getController().onAttack((Creature)npc, damage);
    gameStats.increaseAttackCounter();
  }





  
  public void scheduleRespawn() {
    if (getOwner().isInInstance()) {
      return;
    }
    int instanceId = getOwner().getInstanceId();
    if (!getOwner().getSpawn().isNoRespawn(instanceId)) {
      
      Future<?> respawnTask = RespawnService.scheduleRespawnTask((VisibleObject)getOwner());
      addTask(TaskId.RESPAWN, respawnTask);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\NpcController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
