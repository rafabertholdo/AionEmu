package com.aionemu.gameserver.controllers;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.controllers.movement.StartMovingListener;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.GatherableTemplate;
import com.aionemu.gameserver.model.templates.gather.Material;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.RespawnService;
import com.aionemu.gameserver.skillengine.task.GatheringTask;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;




















public class GatherableController
  extends VisibleObjectController<Gatherable>
{
  private int gatherCount;
  private int currentGatherer;
  private GatheringTask task;
  
  public enum GatherState
  {
    GATHERED,
    GATHERING,
    IDLE;
  }
  
  private GatherState state = GatherState.IDLE;







  
  public void onStartUse(final Player player) {
    GatherableTemplate template = getOwner().getObjectTemplate();
    
    if (!checkPlayerSkill(player, template)) {
      return;
    }
    List<Material> materials = template.getMaterials().getMaterial();
    
    int index = 0;
    Material material = materials.get(index);
    int count = materials.size();
    
    if (count < 1) {
      return;
    }


    
    if (count != 1) {





      
      if (player.getInventory().isFull()) {
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EXTRACT_GATHER_INVENTORY_IS_FULL());
        
        return;
      } 
      int gatherRate = 1;
      float maxrate = 0.0F;
      int rate = 0;
      int i = 0;

      
      SortedMap<Integer, Integer> hasMat = new TreeMap<Integer, Integer>();
      for (Material mat : materials) {
        
        maxrate += mat.getRate().intValue();
        hasMat.put(mat.getRate(), Integer.valueOf(i));
        i++;
      } 
      
      Iterator<Integer> it = hasMat.keySet().iterator();
      while (it.hasNext()) {
        
        rate = ((Integer)it.next()).intValue();
        float percent = Rnd.get() * 100.0F;
        float chance = rate / maxrate * 100.0F * gatherRate;

        
        if (percent < chance) {
          
          index = ((Integer)hasMat.get(Integer.valueOf(rate))).intValue();
          material = materials.get(index);
          
          break;
        } 
      } 
    } 
    Material finalMaterial = material;
    
    if (this.state != GatherState.GATHERING) {
      
      this.state = GatherState.GATHERING;
      this.currentGatherer = player.getObjectId();
      player.getObserveController().attach((ActionObserver)new StartMovingListener()
          {
            
            public void moved()
            {
              GatherableController.this.finishGathering(player);
            }
          });
      int skillLvlDiff = player.getSkillList().getSkillLevel(template.getHarvestSkill()) - template.getSkillLevel();
      this.task = new GatheringTask(player, getOwner(), finalMaterial, skillLvlDiff);
      this.task.start();
    } 
  }








  
  private boolean checkPlayerSkill(Player player, GatherableTemplate template) {
    int harvestSkillId = template.getHarvestSkill();

    
    if (!player.getSkillList().isSkillPresent(harvestSkillId))
    {
      
      return false;
    }
    if (player.getSkillList().getSkillLevel(harvestSkillId) < template.getSkillLevel())
    {
      
      return false;
    }
    return true;
  }

  
  public void completeInteraction() {
    this.state = GatherState.IDLE;
    this.gatherCount++;
    if (this.gatherCount == getOwner().getObjectTemplate().getHarvestCount()) {
      onDie();
    }
  }
  
  public void rewardPlayer(Player player) {
    if (player != null) {
      
      int skillLvl = getOwner().getObjectTemplate().getSkillLevel();
      int xpReward = (int)((0.008D * (skillLvl + 100) * (skillLvl + 100) + 60.0D) * player.getRates().getGatheringXPRate());
      
      if (player.getSkillList().addSkillXp(player, getOwner().getObjectTemplate().getHarvestSkill(), xpReward)) {
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EXTRACT_GATHER_SUCCESS_GETEXP());
        player.getCommonData().addExp(xpReward);
      } else {
        
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_DONT_GET_PRODUCTION_EXP(new DescriptionId(DataManager.SKILL_DATA.getSkillTemplate(getOwner().getObjectTemplate().getHarvestSkill()).getNameId())));
      } 
    } 
  }







  
  public void finishGathering(Player player) {
    if (this.currentGatherer == player.getObjectId()) {
      
      if (this.state == GatherState.GATHERING)
      {
        this.task.abort();
      }
      this.currentGatherer = 0;
      this.state = GatherState.IDLE;
    } 
  }

  
  private void onDie() {
    Gatherable owner = getOwner();
    RespawnService.scheduleRespawnTask((VisibleObject)owner);
    World.getInstance().despawn((VisibleObject)owner);
  }



  
  public void onRespawn() {
    this.gatherCount = 0;
  }


  
  public Gatherable getOwner() {
    return super.getOwner();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\GatherableController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
