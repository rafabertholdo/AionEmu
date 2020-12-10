package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dao.DropListDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.drop.DropList;
import com.aionemu.gameserver.model.drop.DropTemplate;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.DropNpc;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.templates.item.ItemQuality;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_LOOT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOT_ITEMLIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOOT_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.stats.DropRewardEnum;
import com.aionemu.gameserver.world.World;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javolution.util.FastMap;
import org.apache.log4j.Logger;




















public class DropService
{
  private static final Logger log = Logger.getLogger(DropService.class);
  
  private DropList dropList;
  
  private Map<Integer, Set<DropItem>> currentDropMap = (Map<Integer, Set<DropItem>>)(new FastMap()).shared();
  private Map<Integer, DropNpc> dropRegistrationMap = (Map<Integer, DropNpc>)(new FastMap()).shared();

  
  public static final DropService getInstance() {
    return SingletonHolder.instance;
  }

  
  private DropService() {
    this.dropList = ((DropListDAO)DAOManager.getDAO(DropListDAO.class)).load();
    log.info(this.dropList.getSize() + " npc drops loaded");
  }




  
  public DropList getDropList() {
    return this.dropList;
  }







  
  public void registerDrop(Npc npc, Player player, int lvl) {
    if (player == null) {
      return;
    }
    int npcUniqueId = npc.getObjectId();
    int npcTemplateId = npc.getObjectTemplate().getTemplateId();
    
    Set<DropItem> droppedItems = new HashSet<DropItem>();
    Set<DropTemplate> templates = this.dropList.getDropsFor(npcTemplateId);
    
    int index = 1;
    
    int dropPercentage = 100;
    if (!CustomConfig.DISABLE_DROP_REDUCTION) {
      dropPercentage = DropRewardEnum.dropRewardFrom(npc.getLevel() - lvl);
    }
    float dropRate = (player.getRates().getDropRate() * dropPercentage) / 100.0F;
    
    if (templates != null)
    {
      for (DropTemplate dropTemplate : templates) {
        
        DropItem dropItem = new DropItem(dropTemplate);
        dropItem.calculateCount(dropRate);
        
        if (dropItem.getCount() > 0L) {
          
          dropItem.setIndex(index++);
          droppedItems.add(dropItem);
        } 
      } 
    }
    
    QuestService.getQuestDrop(droppedItems, index, npc, player);
    this.currentDropMap.put(Integer.valueOf(npcUniqueId), droppedItems);
    
    List<Player> dropPlayers = new ArrayList<Player>();
    if (player.isInAlliance()) {

      
      List<Integer> dropMembers = new ArrayList<Integer>();
      for (PlayerAllianceMember member : player.getPlayerAlliance().getMembers()) {
        
        dropMembers.add(Integer.valueOf(member.getObjectId()));
        dropPlayers.add(member.getPlayer());
      } 
      this.dropRegistrationMap.put(Integer.valueOf(npcUniqueId), new DropNpc(dropMembers));
      ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcUniqueId))).setGroupSize(player.getPlayerAlliance().size());
    }
    else if (player.isInGroup()) {
      
      this.dropRegistrationMap.put(Integer.valueOf(npcUniqueId), new DropNpc(GroupService.getInstance().getMembersToRegistrateByRules(player, player.getPlayerGroup(), npc)));

      
      DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcUniqueId));
      dropNpc.setInRangePlayers(GroupService.getInstance().getInRangePlayers());
      dropNpc.setGroupSize(dropNpc.getInRangePlayers().size());
      for (Player member : player.getPlayerGroup().getMembers()) {
        
        if (dropNpc.containsKey(member.getObjectId())) {
          dropPlayers.add(member);
        }
      } 
    } else {
      
      List<Integer> singlePlayer = new ArrayList<Integer>();
      singlePlayer.add(Integer.valueOf(player.getObjectId()));
      dropPlayers.add(player);
      this.dropRegistrationMap.put(Integer.valueOf(npcUniqueId), new DropNpc(singlePlayer));
    } 
    
    for (Player p : dropPlayers)
    {
      PacketSendUtility.sendPacket(p, (AionServerPacket)new SM_LOOT_STATUS(npcUniqueId, 0));
    }
  }






  
  public void unregisterDrop(Npc npc) {
    int npcUniqueId = npc.getObjectId();
    this.currentDropMap.remove(Integer.valueOf(npcUniqueId));
    if (this.dropRegistrationMap.containsKey(Integer.valueOf(npcUniqueId)))
    {
      this.dropRegistrationMap.remove(Integer.valueOf(npcUniqueId));
    }
  }







  
  public void requestDropList(Player player, int npcId) {
    if (player == null || !this.dropRegistrationMap.containsKey(Integer.valueOf(npcId))) {
      return;
    }
    DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
    if (!dropNpc.containsKey(player.getObjectId())) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_LOOT_NO_RIGHT());
      
      return;
    } 
    if (dropNpc.isBeingLooted()) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_LOOT_FAIL_ONLOOTING());
      
      return;
    } 
    dropNpc.setBeingLooted(player);
    
    Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
    
    if (dropItems == null)
    {
      dropItems = Collections.emptySet();
    }
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_ITEMLIST(npcId, dropItems, player));
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_STATUS(npcId, 2));
    player.unsetState(CreatureState.ACTIVE);
    player.setState(CreatureState.LOOTING);
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, npcId), true);
  }







  
  public void requestDropList(Player player, int npcId, boolean close) {
    if (!this.dropRegistrationMap.containsKey(Integer.valueOf(npcId))) {
      return;
    }
    DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
    dropNpc.setBeingLooted(null);
    
    player.unsetState(CreatureState.LOOTING);
    player.setState(CreatureState.ACTIVE);
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_LOOT, 0, npcId), true);
    
    Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
    Npc npc = (Npc)World.getInstance().findAionObject(npcId);
    if (npc != null) {
      
      if (dropItems == null || dropItems.size() == 0) {
        
        npc.getController().onDespawn(true);
        
        return;
      } 
      PacketSendUtility.broadcastPacket((VisibleObject)npc, (AionServerPacket)new SM_LOOT_STATUS(npcId, 0));
      dropNpc.setFreeLooting();
    } 
  }

  
  public void requestDropItem(Player player, int npcId, int itemIndex) {
    Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
    
    DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));

    
    if (dropItems == null || dropNpc == null) {
      return;
    }



    
    DropItem requestedItem = null;
    
    synchronized (dropItems) {
      
      for (DropItem dropItem : dropItems) {
        
        if (dropItem.getIndex() == itemIndex) {
          
          requestedItem = dropItem;
          
          break;
        } 
      } 
    } 
    if (requestedItem != null) {
      
      int itemId = requestedItem.getDropTemplate().getItemId();
      
      ItemQuality quality = DataManager.ITEM_DATA.getItemTemplate(itemId).getItemQuality();
      
      if (!requestedItem.isDistributeItem() && !requestedItem.isFreeForAll()) {
        
        if (player.isInGroup()) {
          
          if (dropNpc.getGroupSize() > 1) {
            dropNpc.setDistributionType(player.getPlayerGroup().getLootGroupRules().getQualityRule(quality));
          } else {
            dropNpc.setDistributionType(0);
          } 
          if (dropNpc.getDistributionType() > 1 && !dropNpc.isInUse()) {
            
            dropNpc.setCurrentIndex(itemIndex);
            for (Player member : dropNpc.getInRangePlayers()) {
              
              if (member.isOnline()) {
                
                dropNpc.addPlayerStatus(member);
                PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_LOOT(member.getPlayerGroup().getGroupId(), itemId, npcId, dropNpc.getDistributionType()));
              } 
            } 
            
            dropNpc.isInUse(true);
          } 
        } 
        if (player.isInAlliance()) {
          
          dropNpc.setDistributionType(0);
          
          if (dropNpc.getDistributionType() > 1 && !dropNpc.isInUse()) {
            
            dropNpc.setCurrentIndex(itemIndex);
            for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
              
              Player member = allianceMember.getPlayer();
              if (member.isOnline()) {
                
                dropNpc.addPlayerStatus(member);
                PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_GROUP_LOOT(member.getPlayerAlliance().getPlayerAllianceGroupForMember(member.getObjectId()).getAllianceId(), itemId, npcId, dropNpc.getDistributionType()));
              } 
            } 
            
            dropNpc.isInUse(true);
          } 
        } 
      } 


      
      if ((!player.isInGroup() && !player.isInAlliance()) || dropNpc.getDistributionType() == 0 || requestedItem.isFreeForAll())
      {
        
        if (ItemService.addItem(player, itemId, requestedItem.getCount())) {
          requestedItem.setCount(0L);
        }
      }
      
      if (requestedItem.isDistributeItem()) {
        
        dropNpc.isInUse(false);
        
        if (player != requestedItem.getWinningPlayer() && requestedItem.isItemWonNotCollected()) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_ANOTHER_OWNER_ITEM());
          return;
        } 
        if (requestedItem.getWinningPlayer().getInventory().isFull()) {
          
          PacketSendUtility.sendPacket(requestedItem.getWinningPlayer(), (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_INVEN_ERROR);
          requestedItem.isItemWonNotCollected(true);
          
          return;
        } 
        if (ItemService.addItem(requestedItem.getWinningPlayer(), itemId, requestedItem.getCount())) {
          requestedItem.setCount(0L);
        }
        switch (dropNpc.getDistributionType()) {
          
          case 2:
            winningRollActions(requestedItem.getWinningPlayer(), itemId, npcId);
            break;
          case 3:
            winningBidActions(requestedItem.getWinningPlayer(), itemId, npcId, requestedItem.getHighestValue());
            break;
        } 
      } 
      if (requestedItem.getCount() == 0L)
      {
        dropItems.remove(requestedItem);
      }

      
      resendDropList(dropNpc.getBeingLooted(), npcId, dropItems);
    } 
  }

  
  private void resendDropList(Player player, int npcId, Set<DropItem> dropItems) {
    if (dropItems.size() != 0) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_ITEMLIST(npcId, dropItems, player));
    }
    else {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LOOT_STATUS(npcId, 3));
      player.unsetState(CreatureState.LOOTING);
      player.setState(CreatureState.ACTIVE);
      PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_LOOT, 0, npcId), true);
    } 
  }



  
  public void handleRoll(Player player, int roll, int itemId, int npcId) {
    int luck;
    switch (roll) {
      
      case 0:
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_GIVEUP_ME());
        if (player.isInGroup())
        {
          for (Player member : ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).getInRangePlayers()) {
            
            if (!player.equals(member))
              PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_GIVEUP_OTHER(player.getName())); 
          } 
        }
        if (player.isInAlliance())
        {
          for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
            
            Player member = allianceMember.getPlayer();
            if (!player.equals(member))
              PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_GIVEUP_OTHER(player.getName())); 
          } 
        }
        distributeLoot(player, 0L, itemId, npcId);
        break;
      case 1:
        luck = Rnd.get(1, 100);
        PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_RESULT_ME(luck));
        if (player.isInGroup())
        {
          for (Player member : ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).getInRangePlayers()) {
            
            if (!player.equals(member))
              PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_RESULT_OTHER(player.getName(), luck)); 
          } 
        }
        if (player.isInAlliance())
        {
          for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
            
            Player member = allianceMember.getPlayer();
            if (!player.equals(member))
              PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_RESULT_OTHER(player.getName(), luck)); 
          } 
        }
        distributeLoot(player, luck, itemId, npcId);
        break;
    } 
  }




  
  public void handleBid(Player player, long bid, int itemId, int npcId) {
    long kinahAmount = player.getInventory().getKinahItem().getItemCount();
    if (bid > 0L) {
      
      if (kinahAmount < bid)
      {
        bid = 0L;
      }
      distributeLoot(player, bid, itemId, npcId);
    } else {
      
      distributeLoot(player, 0L, itemId, npcId);
    } 
  }



  
  private void distributeLoot(Player player, long luckyPlayer, int itemId, int npcId) {
    DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
    
    Set<DropItem> dropItems = this.currentDropMap.get(Integer.valueOf(npcId));
    DropItem requestedItem = null;
    
    synchronized (dropItems) {
      
      for (DropItem dropItem : dropItems) {
        
        if (dropItem.getIndex() == dropNpc.getCurrentIndex()) {
          
          requestedItem = dropItem;
          
          break;
        } 
      } 
    } 
    
    if (dropNpc.containsPlayerStatus(player))
    {
      dropNpc.delPlayerStatus(player);
    }
    
    if (luckyPlayer > requestedItem.getHighestValue()) {
      
      requestedItem.setHighestValue(luckyPlayer);
      requestedItem.setWinningPlayer(player);
    } 
    
    if (dropNpc.getPlayerStatus().size() != 0) {
      return;
    }
    
    if (requestedItem.getWinningPlayer() == null) {
      
      requestedItem.isFreeForAll(true);
      ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).isInUse(false);
      return;
    } 
    requestedItem.isDistributeItem(true);
    requestDropItem(player, npcId, dropNpc.getCurrentIndex());
  }




  
  private void winningRollActions(Player player, int itemId, int npcId) {
    int nameId = DataManager.ITEM_DATA.getItemTemplate(itemId).getNameId();
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_GET_ITEM_ME(new DescriptionId(nameId)));
    
    if (player.isInGroup())
    {
      for (Player member : ((DropNpc)this.dropRegistrationMap.get(Integer.valueOf(npcId))).getInRangePlayers()) {
        
        if (!player.equals(member)) {
          PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_GET_ITEM_OTHER(player.getName(), new DescriptionId(nameId)));
        }
      } 
    }
    if (player.isInAlliance())
    {
      for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
        
        Player member = allianceMember.getPlayer();
        if (!player.equals(member)) {
          PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_LOOT_GET_ITEM_OTHER(player.getName(), new DescriptionId(nameId)));
        }
      } 
    }
  }





  
  private void winningBidActions(Player player, int itemId, int npcId, long highestValue) {
    DropNpc dropNpc = this.dropRegistrationMap.get(Integer.valueOf(npcId));
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_ACCOUNT_ME(highestValue));
    ItemService.decreaseKinah(player, highestValue);
    
    if (player.isInGroup())
    {
      for (Player member : dropNpc.getInRangePlayers()) {
        
        if (!player.equals(member)) {
          
          PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_ACCOUNT_OTHER(player.getName(), highestValue));
          long distributeKinah = highestValue / (dropNpc.getGroupSize() - 1);
          ItemService.increaseKinah(member, distributeKinah);
          PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_DISTRIBUTE(highestValue, dropNpc.getGroupSize() - 1, distributeKinah));
        } 
      } 
    }
    if (player.isInAlliance())
    {
      for (PlayerAllianceMember allianceMember : player.getPlayerAlliance().getMembers()) {
        
        Player member = allianceMember.getPlayer();
        if (!player.equals(member)) {
          
          PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_ACCOUNT_OTHER(player.getName(), highestValue));
          long distributeKinah = highestValue / (dropNpc.getGroupSize() - 1);
          ItemService.increaseKinah(member, distributeKinah);
          PacketSendUtility.sendPacket(member, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_PAY_DISTRIBUTE(highestValue, dropNpc.getGroupSize() - 1, distributeKinah));
        } 
      } 
    }
  }


  
  private static class SingletonHolder
  {
    protected static final DropService instance = new DropService();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\DropService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
