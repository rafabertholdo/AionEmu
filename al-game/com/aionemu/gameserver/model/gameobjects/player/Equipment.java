package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.listeners.ItemEquipmentListener;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.EquipType;
import com.aionemu.gameserver.model.templates.item.ItemRace;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.model.templates.itemset.ItemSetTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.StigmaService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldPosition;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javolution.util.FastList;
import org.apache.log4j.Logger;








public class Equipment
{
  private SortedMap<Integer, Item> equipment = new TreeMap<Integer, Item>();
  private Player owner;
  private static final Logger log = Logger.getLogger(Equipment.class);
  private Set<Integer> markedFreeSlots = new HashSet<Integer>();
  private PersistentState persistentState = PersistentState.UPDATED;

  
  public Equipment(Player player) {
    this.owner = player;
  }







  
  public Item equipItem(int itemUniqueId, int slot) {
    Item item = this.owner.getInventory().getItemByObjId(itemUniqueId);
    
    if (item == null) {
      return null;
    }
    ItemTemplate itemTemplate = item.getItemTemplate();

    
    if (itemTemplate.getLevel() > this.owner.getCommonData().getLevel()) {
      
      PacketSendUtility.sendPacket(this.owner, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_USE_ITEM_TOO_LOW_LEVEL_MUST_BE_THIS_LEVEL(itemTemplate.getLevel(), new DescriptionId(Integer.parseInt(item.getName()))));
      
      return null;
    } 
    
    if (this.owner.getAccessLevel() == 0) {
      
      switch (itemTemplate.getRace()) {
        
        case ARROW:
          if (this.owner.getCommonData().getRace() != Race.ASMODIANS)
            return null; 
          break;
        case SHIELD:
          if (this.owner.getCommonData().getRace() != Race.ELYOS) {
            return null;
          }
          break;
      } 
      if (!itemTemplate.isAllowedFor(this.owner.getCommonData().getPlayerClass(), this.owner.getLevel())) {
        return null;
      }
    } 
    int itemSlotToEquip = 0;
    
    synchronized (this.equipment) {
      
      this.markedFreeSlots.clear();

      
      switch (item.getEquipmentType()) {
        
        case ARROW:
          if (!validateEquippedArmor(item, true))
            return null; 
          break;
        case SHIELD:
          if (!validateEquippedWeapon(item, true)) {
            return null;
          }
          break;
      } 
      
      int itemSlotMask = 0;
      switch (item.getEquipmentType()) {
        
        case null:
          itemSlotMask = slot;
          break;
        default:
          itemSlotMask = itemTemplate.getItemSlot();
          break;
      } 
      
      if (!StigmaService.notifyEquipAction(this.owner, item)) {
        return null;
      }
      FastList<ItemSlot> fastList = ItemSlot.getSlotsFor(itemSlotMask);
      for (ItemSlot possibleSlot : fastList) {
        
        int slotId = possibleSlot.getSlotIdMask();
        if (this.equipment.get(Integer.valueOf(slotId)) == null || this.markedFreeSlots.contains(Integer.valueOf(slotId))) {
          
          itemSlotToEquip = slotId;
          
          break;
        } 
      } 
      
      if (itemSlotToEquip == 0)
      {
        itemSlotToEquip = ((ItemSlot)fastList.get(0)).getSlotIdMask();
      }
    } 
    
    if (itemSlotToEquip == 0) {
      return null;
    }
    if (itemTemplate.isSoulBound() && !item.isSoulBound()) {
      
      soulBindItem(this.owner, item, itemSlotToEquip);
      return null;
    } 

    
    return equip(itemSlotToEquip, item);
  }







  
  private Item equip(int itemSlotToEquip, Item item) {
    synchronized (this.equipment) {

      
      ItemService.removeItem(this.owner, this.owner.getInventory(), item, false, false);
      
      Item equippedItem = this.equipment.get(Integer.valueOf(itemSlotToEquip));
      if (equippedItem != null) {
        unEquip(itemSlotToEquip);
      }
      switch (item.getEquipmentType()) {
        
        case ARROW:
          validateEquippedArmor(item, false);
          break;
        case SHIELD:
          validateEquippedWeapon(item, false);
          break;
      } 
      
      if (this.equipment.get(Integer.valueOf(itemSlotToEquip)) != null) {
        
        log.error("CHECKPOINT : putting item to already equiped slot. Info slot: " + itemSlotToEquip + " new item: " + item.getItemTemplate().getTemplateId() + " old item: " + ((Item)this.equipment.get(Integer.valueOf(itemSlotToEquip))).getItemTemplate().getTemplateId());

        
        return null;
      } 

      
      this.equipment.put(Integer.valueOf(itemSlotToEquip), item);
      item.setEquipped(true);
      item.setEquipmentSlot(itemSlotToEquip);
      PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item));

      
      ItemEquipmentListener.onItemEquipment(item, this.owner);
      this.owner.getObserveController().notifyItemEquip(item, this.owner);
      this.owner.getLifeStats().updateCurrentStats();
      setPersistentState(PersistentState.UPDATE_REQUIRED);
      return item;
    } 
  }










  
  public Item unEquipItem(int itemUniqueId, int slot) {
    if (this.owner.getInventory().isFull()) {
      return null;
    }
    synchronized (this.equipment) {
      
      Item itemToUnequip = null;
      
      for (Item item : this.equipment.values()) {
        
        if (item.getObjectId() == itemUniqueId)
        {
          itemToUnequip = item;
        }
      } 
      
      if (itemToUnequip == null || !itemToUnequip.isEquipped()) {
        return null;
      }
      
      if (itemToUnequip.getItemTemplate().getWeaponType() == WeaponType.BOW) {
        
        Item possibleArrows = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
        if (possibleArrows != null && possibleArrows.getItemTemplate().getArmorType() == ArmorType.ARROW) {

          
          if (this.owner.getInventory().getNumberOfFreeSlots() < 1)
            return null; 
          unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
        } 
      } 
      
      if (itemToUnequip.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask()) {
        
        Item ohWeapon = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
        if (ohWeapon != null && ohWeapon.getItemTemplate().isWeapon()) {
          
          if (this.owner.getInventory().getNumberOfFreeSlots() < 2) {
            return null;
          }
          unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
        } 
      } 

      
      if (itemToUnequip.getItemTemplate().isArmor() && itemToUnequip.getItemTemplate().getArmorType() == ArmorType.SHARD) {
        
        this.owner.unsetState(CreatureState.POWERSHARD);
        PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_EMOTION(this.owner, EmotionType.POWERSHARD_OFF, 0, 0));
      } 
      
      if (!StigmaService.notifyUnequipAction(this.owner, itemToUnequip)) {
        return null;
      }
      unEquip(itemToUnequip.getEquipmentSlot());
      return itemToUnequip;
    } 
  }

  
  private void unEquip(int slot) {
    Item item = this.equipment.remove(Integer.valueOf(slot));
    if (item == null) {
      return;
    }

    
    item.setEquipped(false);
    
    ItemEquipmentListener.onItemUnequipment(item, this.owner);
    this.owner.getObserveController().notifyItemUnEquip(item, this.owner);
    
    this.owner.getLifeStats().updateCurrentStats();
    this.owner.getInventory().putToBag(item);
    PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item));
  }










  
  private boolean validateEquippedWeapon(Item item, boolean validateOnly) {
    Item possibleArrows;
    int[] requiredSkills = item.getItemTemplate().getWeaponType().getRequiredSkills();
    
    if (!checkAvaialbeEquipSkills(requiredSkills)) {
      return false;
    }
    Item itemInMainHand = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
    Item itemInSubHand = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
    
    int requiredSlots = 0;
    switch (item.getItemTemplate().getWeaponType().getRequiredSlots()) {
      
      case 2:
        switch (item.getItemTemplate().getWeaponType()) {

          
          case ARROW:
            if (itemInSubHand != null && itemInSubHand.getItemTemplate().getArmorType() != ArmorType.ARROW) {

              
              if (validateOnly) {
                
                requiredSlots++;
                this.markedFreeSlots.add(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
                break;
              } 
              unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
            } 
            break;
          
          default:
            if (itemInSubHand != null) {
              
              if (validateOnly) {
                
                requiredSlots++;
                this.markedFreeSlots.add(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
                break;
              } 
              unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
            } 
            break;
        } 
      case 1:
        if (itemInMainHand != null && !this.owner.getSkillList().isSkillPresent(19) && !this.owner.getSkillList().isSkillPresent(360)) {
          
          if (validateOnly) {
            
            requiredSlots++;
            this.markedFreeSlots.add(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
          } else {
            
            unEquip(ItemSlot.MAIN_HAND.getSlotIdMask());
          }
        
        } else if (itemInMainHand != null && itemInMainHand.getItemTemplate().getWeaponType().getRequiredSlots() == 2) {
          
          if (validateOnly) {
            
            requiredSlots++;
            this.markedFreeSlots.add(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
          } else {
            
            unEquip(ItemSlot.MAIN_HAND.getSlotIdMask());
          } 
        } 
        
        possibleArrows = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
        if (possibleArrows != null && possibleArrows.getItemTemplate().getArmorType() == ArmorType.ARROW) {
          
          if (validateOnly) {
            
            requiredSlots++;
            this.markedFreeSlots.add(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
            break;
          } 
          unEquip(ItemSlot.SUB_HAND.getSlotIdMask());
        } 
        break;
    } 

    
    return (this.owner.getInventory().getNumberOfFreeSlots() >= requiredSlots - 1);
  }






  
  private boolean checkAvaialbeEquipSkills(int[] requiredSkills) {
    boolean isSkillPresent = false;

    
    if (requiredSkills.length == 0) {
      return true;
    }
    for (int skill : requiredSkills) {
      
      if (this.owner.getSkillList().isSkillPresent(skill)) {
        
        isSkillPresent = true;
        break;
      } 
    } 
    return isSkillPresent;
  }









  
  private boolean validateEquippedArmor(Item item, boolean validateOnly) {
    ArmorType armorType = item.getItemTemplate().getArmorType();
    if (armorType == null) {
      return true;
    }
    
    int[] requiredSkills = armorType.getRequiredSkills();
    if (!checkAvaialbeEquipSkills(requiredSkills)) {
      return false;
    }
    Item itemInMainHand = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
    switch (item.getItemTemplate().getArmorType()) {
      
      case ARROW:
        if (itemInMainHand == null || itemInMainHand.getItemTemplate().getWeaponType() != WeaponType.BOW)
        {
          
          if (validateOnly)
            return false; 
        }
        break;
      case SHIELD:
        if (itemInMainHand != null && itemInMainHand.getItemTemplate().getWeaponType().getRequiredSlots() == 2) {

          
          if (validateOnly) {
            
            if (this.owner.getInventory().isFull())
              return false; 
            this.markedFreeSlots.add(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
            
            break;
          } 
          
          unEquip(ItemSlot.MAIN_HAND.getSlotIdMask());
        } 
        break;
    } 
    
    return true;
  }







  
  public Item getEquippedItemByObjId(int value) {
    synchronized (this.equipment) {
      
      for (Item item : this.equipment.values()) {
        
        if (item.getObjectId() == value) {
          return item;
        }
      } 
    } 
    return null;
  }






  
  public List<Item> getEquippedItemsByItemId(int value) {
    List<Item> equippedItemsById = new ArrayList<Item>();
    synchronized (this.equipment) {
      
      for (Item item : this.equipment.values()) {
        
        if (item.getItemTemplate().getTemplateId() == value) {
          equippedItemsById.add(item);
        }
      } 
    } 
    return equippedItemsById;
  }





  
  public List<Item> getEquippedItems() {
    List<Item> equippedItems = new ArrayList<Item>();
    equippedItems.addAll(this.equipment.values());
    
    return equippedItems;
  }





  
  public List<Item> getEquippedItemsWithoutStigma() {
    List<Item> equippedItems = new ArrayList<Item>();
    for (Item item : this.equipment.values()) {
      
      if (item.getEquipmentSlot() < ItemSlot.STIGMA1.getSlotIdMask()) {
        equippedItems.add(item);
      }
    } 
    return equippedItems;
  }





  
  public List<Item> getEquippedItemsStigma() {
    List<Item> equippedItems = new ArrayList<Item>();
    for (Item item : this.equipment.values()) {
      
      if (item.getEquipmentSlot() >= ItemSlot.STIGMA1.getSlotIdMask()) {
        equippedItems.add(item);
      }
    } 
    return equippedItems;
  }




  
  public int itemSetPartsEquipped(int itemSetTemplateId) {
    int number = 0;
    
    for (Item item : this.equipment.values()) {
      
      ItemSetTemplate setTemplate = item.getItemTemplate().getItemSet();
      if (setTemplate != null && setTemplate.getId() == itemSetTemplateId)
      {
        number++;
      }
    } 
    
    return number;
  }






  
  public void onLoadHandler(Item item) {
    if (this.equipment.containsKey(Integer.valueOf(item.getEquipmentSlot()))) {
      
      log.warn("Duplicate equipped item in slot : " + item.getEquipmentSlot() + " " + this.owner.getObjectId());
      return;
    } 
    item.setOwnerId(this.owner.getObjectId());
    this.equipment.put(Integer.valueOf(item.getEquipmentSlot()), item);
  }





  
  public void onLoadApplyEquipmentStats() {
    for (Item item : this.equipment.values()) {
      
      if (this.owner.getGameStats() != null)
      {
        if (item.getEquipmentSlot() != ItemSlot.MAIN_OFF_HAND.getSlotIdMask() && item.getEquipmentSlot() != ItemSlot.SUB_OFF_HAND.getSlotIdMask())
        {
          ItemEquipmentListener.onItemEquipment(item, this.owner); } 
      }
      if (this.owner.getLifeStats() != null)
      {
        if (item.getEquipmentSlot() != ItemSlot.MAIN_OFF_HAND.getSlotIdMask() && item.getEquipmentSlot() != ItemSlot.SUB_OFF_HAND.getSlotIdMask())
        {
          this.owner.getLifeStats().synchronizeWithMaxStats();
        }
      }
    } 
  }



  
  public boolean isShieldEquipped() {
    Item subHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
    return (subHandItem != null && subHandItem.getItemTemplate().getArmorType() == ArmorType.SHIELD);
  }





  
  public WeaponType getMainHandWeaponType() {
    Item mainHandItem = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
    if (mainHandItem == null) {
      return null;
    }
    return mainHandItem.getItemTemplate().getWeaponType();
  }





  
  public WeaponType getOffHandWeaponType() {
    Item offHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
    if (offHandItem != null && offHandItem.getItemTemplate().isWeapon()) {
      return offHandItem.getItemTemplate().getWeaponType();
    }
    return null;
  }


  
  public boolean isPowerShardEquipped() {
    Item leftPowershard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_LEFT.getSlotIdMask()));
    if (leftPowershard != null) {
      return true;
    }
    Item rightPowershard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_RIGHT.getSlotIdMask()));
    if (rightPowershard != null) {
      return true;
    }
    return false;
  }

  
  public Item getMainHandPowerShard() {
    Item mainHandPowerShard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_RIGHT.getSlotIdMask()));
    if (mainHandPowerShard != null) {
      return mainHandPowerShard;
    }
    return null;
  }

  
  public Item getOffHandPowerShard() {
    Item offHandPowerShard = this.equipment.get(Integer.valueOf(ItemSlot.POWER_SHARD_LEFT.getSlotIdMask()));
    if (offHandPowerShard != null) {
      return offHandPowerShard;
    }
    return null;
  }





  
  public void usePowerShard(Item powerShardItem, int count) {
    decreaseEquippedItemCount(powerShardItem.getObjectId(), count);
    
    if (powerShardItem.getItemCount() <= 0L) {
      
      List<Item> powerShardStacks = this.owner.getInventory().getItemsByItemId(powerShardItem.getItemTemplate().getTemplateId());
      if (powerShardStacks.size() != 0) {
        
        equipItem(((Item)powerShardStacks.get(0)).getObjectId(), powerShardItem.getEquipmentSlot());
      }
      else {
        
        PacketSendUtility.sendPacket(this.owner, (AionServerPacket)SM_SYSTEM_MESSAGE.NO_POWER_SHARD_LEFT());
        this.owner.unsetState(CreatureState.POWERSHARD);
      } 
    } 
  }

  
  public void useArrow() {
    Item arrow = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
    
    if (arrow == null || (!arrow.getItemTemplate().isArmor() && arrow.getItemTemplate().getArmorType() != ArmorType.ARROW)) {
      return;
    }
    decreaseEquippedItemCount(arrow.getObjectId(), 1);
  }


  
  private void decreaseEquippedItemCount(int itemObjId, int count) {
    Item equippedItem = getEquippedItemByObjId(itemObjId);

    
    if (equippedItem.getItemTemplate().getArmorType() != ArmorType.SHARD && equippedItem.getItemTemplate().getArmorType() != ArmorType.ARROW) {
      return;
    }
    
    if (equippedItem.getItemCount() >= count) {
      equippedItem.decreaseItemCount(count);
    } else {
      equippedItem.decreaseItemCount(equippedItem.getItemCount());
    } 
    if (equippedItem.getItemCount() == 0L) {
      
      this.equipment.remove(Integer.valueOf(equippedItem.getEquipmentSlot()));
      PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_DELETE_ITEM(equippedItem.getObjectId()));
    } 
    
    PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(equippedItem));
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }




  
  public void switchHands() {
    Item mainHandItem = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
    Item subHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
    Item mainOffHandItem = this.equipment.get(Integer.valueOf(ItemSlot.MAIN_OFF_HAND.getSlotIdMask()));
    Item subOffHandItem = this.equipment.get(Integer.valueOf(ItemSlot.SUB_OFF_HAND.getSlotIdMask()));
    
    List<Item> equippedWeapon = new ArrayList<Item>();
    
    if (mainHandItem != null)
      equippedWeapon.add(mainHandItem); 
    if (subHandItem != null)
      equippedWeapon.add(subHandItem); 
    if (mainOffHandItem != null)
      equippedWeapon.add(mainOffHandItem); 
    if (subOffHandItem != null) {
      equippedWeapon.add(subOffHandItem);
    }
    for (Item item : equippedWeapon) {
      
      this.equipment.remove(Integer.valueOf(item.getEquipmentSlot()));
      item.setEquipped(false);
      PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item, true));
      if (this.owner.getGameStats() != null)
      {
        if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask() || item.getEquipmentSlot() == ItemSlot.SUB_HAND.getSlotIdMask())
        {
          ItemEquipmentListener.onItemUnequipment(item, this.owner);
        }
      }
    } 
    
    for (Item item : equippedWeapon) {
      
      if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask()) {
        item.setEquipmentSlot(ItemSlot.MAIN_OFF_HAND.getSlotIdMask()); continue;
      } 
      if (item.getEquipmentSlot() == ItemSlot.SUB_HAND.getSlotIdMask()) {
        item.setEquipmentSlot(ItemSlot.SUB_OFF_HAND.getSlotIdMask()); continue;
      } 
      if (item.getEquipmentSlot() == ItemSlot.MAIN_OFF_HAND.getSlotIdMask()) {
        item.setEquipmentSlot(ItemSlot.MAIN_HAND.getSlotIdMask()); continue;
      } 
      if (item.getEquipmentSlot() == ItemSlot.SUB_OFF_HAND.getSlotIdMask()) {
        item.setEquipmentSlot(ItemSlot.SUB_HAND.getSlotIdMask());
      }
    } 
    for (Item item : equippedWeapon) {
      
      this.equipment.put(Integer.valueOf(item.getEquipmentSlot()), item);
      item.setEquipped(true);
      PacketSendUtility.sendPacket(this.owner, (AionServerPacket)new SM_UPDATE_ITEM(item, true));
    } 
    
    if (this.owner.getGameStats() != null)
    {
      for (Item item : equippedWeapon) {
        
        if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask() || item.getEquipmentSlot() == ItemSlot.SUB_HAND.getSlotIdMask())
        {
          ItemEquipmentListener.onItemEquipment(item, this.owner);
        }
      } 
    }
    this.owner.getLifeStats().updateCurrentStats();
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }




  
  public boolean isWeaponEquipped(WeaponType weaponType) {
    if (this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask())) != null && ((Item)this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()))).getItemTemplate().getWeaponType() == weaponType)
    {
      
      return true;
    }
    if (this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask())) != null && ((Item)this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()))).getItemTemplate().getWeaponType() == weaponType)
    {
      
      return true;
    }
    return false;
  }




  
  public boolean isArmorEquipped(ArmorType armorType) {
    int[] armorSlots = { ItemSlot.BOOTS.getSlotIdMask(), ItemSlot.GLOVES.getSlotIdMask(), ItemSlot.HELMET.getSlotIdMask(), ItemSlot.PANTS.getSlotIdMask(), ItemSlot.SHOULDER.getSlotIdMask(), ItemSlot.TORSO.getSlotIdMask() };


    
    for (int slot : armorSlots) {
      
      if (this.equipment.get(Integer.valueOf(slot)) != null && ((Item)this.equipment.get(Integer.valueOf(slot))).getItemTemplate().getArmorType() != armorType)
        return false; 
    } 
    return true;
  }

  
  public Item getMainHandWeapon() {
    return this.equipment.get(Integer.valueOf(ItemSlot.MAIN_HAND.getSlotIdMask()));
  }

  
  public Item getOffHandWeapon() {
    return this.equipment.get(Integer.valueOf(ItemSlot.SUB_HAND.getSlotIdMask()));
  }




  
  public PersistentState getPersistentState() {
    return this.persistentState;
  }




  
  public void setPersistentState(PersistentState persistentState) {
    this.persistentState = persistentState;
  }




  
  public void setOwner(Player player) {
    this.owner = player;
  }







  
  private boolean soulBindItem(final Player player, final Item item, final int slot) {
    RequestResponseHandler responseHandler = new RequestResponseHandler(player)
      {
        
        public void acceptRequest(Creature requester, Player responder)
        {
          PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemId(), 5000, 4), true);

          
          final WorldPosition position = player.getCommonData().getPosition().clone();

          
          ThreadPoolManager.getInstance().schedule(new Runnable()
              {
                public void run()
                {
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), item.getItemId(), 0, 6), true);
                  
                  if (!position.equals(player.getCommonData().getPosition())) {
                    return;
                  }
                  
                  PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SOUL_BOUND_ITEM_SUCCEED(new DescriptionId(item.getNameID())));
                  
                  item.setSoulBound(true);
                  Equipment.this.equip(slot, item);
                  PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), Equipment.this.getEquippedItemsWithoutStigma()), true);
                }
              }5100L);
        }



        
        public void denyRequest(Creature requester, Player responder) {
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.SOUL_BOUND_ITEM_CANCELED(new DescriptionId(item.getNameID())));
        }
      };

    
    boolean requested = player.getResponseRequester().putRequest(95006, responseHandler);
    
    if (requested)
    {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(95006, 0, new Object[] { new DescriptionId(item.getNameID()) }));
    }

    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Equipment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
