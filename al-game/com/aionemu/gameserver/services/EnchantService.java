package com.aionemu.gameserver.services;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EnchantsConfig;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.gameobjects.stats.id.EnchantStatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.listeners.ItemEquipmentListener;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.AddModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.RateModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemQuality;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;




















public class EnchantService
{
  private static final Logger log = Logger.getLogger(EnchantService.class);







  
  public static void breakItem(Player player, Item targetItem, Item parentItem) {
    ItemTemplate itemTemplate = targetItem.getItemTemplate();
    ItemQuality quality = itemTemplate.getItemQuality();
    
    int number = 0;
    int level = 0;
    switch (quality) {
      
      case BOOK_2H:
      case DAGGER_1H:
        number = Rnd.get(1, 2);
        level = Rnd.get(0, 5);
        break;
      case BOW:
        number = Rnd.get(1, 3);
        level = Rnd.get(0, 10);
        break;
      case ORB_2H:
      case STAFF_2H:
        number = Rnd.get(1, 3);
        level = Rnd.get(0, 15);
        break;
      case SWORD_1H:
      case SWORD_2H:
        number = Rnd.get(1, 3);
        level = Rnd.get(0, 20);
        break;
    } 
    
    int enchantItemLevel = targetItem.getItemTemplate().getLevel() + level;
    int enchantItemId = 166000000 + enchantItemLevel;
    
    ItemService.removeItemFromInventory(player, targetItem);
    ItemService.decreaseItemCount(player, parentItem, 1L);
    
    ItemService.addItem(player, enchantItemId, number);
  }






  
  public static boolean enchantItem(Player player, Item parentItem, Item targetItem, Item supplementItem) {
    int enchantStoneLevel = parentItem.getItemTemplate().getLevel();
    int targetItemLevel = targetItem.getItemTemplate().getLevel();
    
    if (targetItemLevel > enchantStoneLevel) {
      return false;
    }
    int qualityCap = 0;
    
    ItemQuality quality = targetItem.getItemTemplate().getItemQuality();
    
    switch (quality) {
      
      case BOOK_2H:
      case DAGGER_1H:
        qualityCap = 0;
        break;
      case BOW:
        qualityCap = 5;
        break;
      case ORB_2H:
      case STAFF_2H:
        qualityCap = 10;
        break;
      case SWORD_1H:
      case SWORD_2H:
        qualityCap = 15;
        break;
    } 
    
    int success = 50;
    
    int levelDiff = enchantStoneLevel - targetItemLevel;
    
    int extraSuccess = levelDiff - qualityCap;
    if (extraSuccess > 0)
    {
      success += extraSuccess * 5;
    }
    
    if (supplementItem != null) {
      
      int supplementUseCount = 1;
      int addsuccessRate = 10;
      int supplementId = supplementItem.getItemTemplate().getTemplateId();
      int enchantstoneLevel = parentItem.getItemTemplate().getLevel();
      int enchantitemLevel = targetItem.getEnchantLevel() + 1;
      
      switch (supplementId) {

        
        case 166100000:
        case 166100003:
        case 166100006:
          addsuccessRate = EnchantsConfig.LSUP;
          break;

        
        case 166100001:
        case 166100004:
        case 166100007:
          addsuccessRate = EnchantsConfig.RSUP;
          break;

        
        case 166100002:
        case 166100005:
        case 166100008:
          addsuccessRate = EnchantsConfig.GSUP;
          break;
      } 
      
      if (enchantstoneLevel > 30 && enchantstoneLevel < 41) {
        supplementUseCount = 5;
      }
      if (enchantstoneLevel > 40 && enchantstoneLevel < 51) {
        supplementUseCount = 10;
      }
      if (enchantstoneLevel > 50 && enchantstoneLevel < 61) {
        supplementUseCount = 25;
      }
      if (enchantstoneLevel > 60 && enchantstoneLevel < 71) {
        supplementUseCount = 55;
      }
      if (enchantstoneLevel > 70 && enchantstoneLevel < 81) {
        supplementUseCount = 85;
      }
      if (enchantstoneLevel > 80 && enchantstoneLevel < 91) {
        supplementUseCount = 115;
      }
      if (enchantstoneLevel > 90) {
        supplementUseCount = 145;
      }
      
      if (enchantitemLevel > 10) {
        supplementUseCount *= 2;
      }
      ItemService.decreaseItemCount(player, supplementItem, supplementUseCount);

      
      success += addsuccessRate;
    } 
    
    if (success >= 95) {
      success = 95;
    }
    boolean result = false;
    
    if (Rnd.get(0, 100) < success) {
      result = true;
    }
    int currentEnchant = targetItem.getEnchantLevel();
    
    if (!result) {


      
      if (currentEnchant > 10) {
        currentEnchant = 10;
      } else if (currentEnchant > 0) {
        currentEnchant--;
      }
    
    } else {
      
      ItemQuality targetQuality = targetItem.getItemTemplate().getItemQuality();
      if (targetQuality == ItemQuality.UNIQUE || targetQuality == ItemQuality.EPIC) {
        
        if (currentEnchant < 15) {
          currentEnchant++;
        
        }
      }
      else if (currentEnchant < 10) {
        currentEnchant++;
      } 
    } 
    
    if (targetItem.isEquipped()) {
      onItemUnequip(player, targetItem);
    }
    targetItem.setEnchantLevel(currentEnchant);
    
    if (targetItem.isEquipped()) {
      onItemEquip(player, targetItem);
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(targetItem));
    
    if (targetItem.isEquipped()) {
      player.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
    } else {
      player.getInventory().setPersistentState(PersistentState.UPDATE_REQUIRED);
    } 
    if (result) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_SUCCEED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
    
    }
    else {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_FAILED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
    } 
    
    ItemService.decreaseItemCount(player, parentItem, 1L);
    
    return result;
  }






  
  public static boolean socketManastone(Player player, Item parentItem, Item targetItem, Item supplementItem) {
    boolean result = false;
    int successRate = 76;
    
    int stoneCount = targetItem.getItemStones().size();
    switch (stoneCount) {
      
      case 1:
        successRate = EnchantsConfig.MSPERCENT;
        break;
      case 2:
        successRate = EnchantsConfig.MSPERCENT1;
        break;
      case 3:
        successRate = EnchantsConfig.MSPERCENT2;
        break;
      case 4:
        successRate = EnchantsConfig.MSPERCENT3;
        break;
      case 5:
        successRate = EnchantsConfig.MSPERCENT4;
        break;
    } 
    
    if (stoneCount >= 6) {
      successRate = EnchantsConfig.MSPERCENT5;
    }
    if (supplementItem != null) {
      
      int supplementUseCount = 1;
      int addsuccessRate = 10;
      int supplementId = supplementItem.getItemTemplate().getTemplateId();
      int manastoneId = parentItem.getItemTemplate().getTemplateId();
      int manastoneLevel = parentItem.getItemTemplate().getLevel();
      int manastoneCount = targetItem.getItemStones().size() + 1;

      
      if (supplementId == 166100000 || supplementId == 166100003 || supplementId == 166100006) {
        addsuccessRate = 10;
      }
      if (supplementId == 166100001 || supplementId == 166100004 || supplementId == 166100007) {
        addsuccessRate = 15;
      }
      if (supplementId == 166100002 || supplementId == 166100005 || supplementId == 166100008) {
        addsuccessRate = 20;
      }
      
      if (manastoneLevel > 30) {
        supplementUseCount++;
      }
      if (manastoneLevel > 40) {
        supplementUseCount++;
      }
      if (manastoneLevel > 50) {
        supplementUseCount++;
      }
      
      switch (manastoneId) {
        
        case 167000230:
        case 167000235:
        case 167000267:
        case 167000294:
        case 167000299:
          supplementUseCount = 5;
          break;
        case 167000331:
          supplementUseCount = 10;
          break;
        case 167000358:
        case 167000363:
          supplementUseCount = 15;
          break;
        case 167000550:
          supplementUseCount = 20;
          break;
        case 167000427:
        case 167000454:
        case 167000459:
          supplementUseCount = 25;
          break;
        case 167000491:
          supplementUseCount = 50;
          break;
        case 167000518:
        case 167000522:
          supplementUseCount = 75;
          break;
      } 

      
      if (stoneCount > 0) {
        supplementUseCount *= manastoneCount;
      }
      if (ItemService.decreaseItemCount(player, supplementItem, supplementUseCount) != 0L) {
        return false;
      }
      
      successRate += addsuccessRate;
    } 
    
    if (Rnd.get(0, 100) < successRate)
      result = true; 
    if (result) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_SUCCEED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
      
      ManaStone manaStone = ItemService.addManaStone(targetItem, parentItem.getItemTemplate().getTemplateId());
      if (targetItem.isEquipped())
      {
        ItemEquipmentListener.addStoneStats(manaStone, (CreatureGameStats)player.getGameStats());
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
      }
    
    } else {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_FAILED(new DescriptionId(Integer.parseInt(targetItem.getName()))));
      
      Set<ManaStone> manaStones = targetItem.getItemStones();
      if (targetItem.isEquipped()) {
        
        ItemEquipmentListener.removeStoneStats(manaStones, (CreatureGameStats)player.getGameStats());
        PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
      } 
      ItemService.removeAllManastone(player, targetItem);
    } 
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(targetItem));
    ItemService.decreaseItemCount(player, parentItem, 1L);
    return result;
  }







  
  public static void onItemEquip(Player player, Item item) {
    try {
      int enchantLevel = item.getEnchantLevel();
      
      if (enchantLevel == 0) {
        return;
      }
      boolean isWeapon = item.getItemTemplate().isWeapon();
      boolean isArmor = item.getItemTemplate().isArmor();
      if (isWeapon) {
        
        TreeSet<StatModifier> modifiers = getWeaponModifiers(item);
        
        if (modifiers == null) {
          return;
        }
        EnchantStatEffectId statId = EnchantStatEffectId.getInstance(item.getObjectId(), item.getEquipmentSlot());

        
        player.getGameStats().addModifiers((StatEffectId)statId, modifiers);
        
        return;
      } 
      if (isArmor)
      {
        TreeSet<StatModifier> modifiers = getArmorModifiers(item);
        
        if (modifiers == null) {
          return;
        }
        EnchantStatEffectId statId = EnchantStatEffectId.getInstance(item.getObjectId(), item.getEquipmentSlot());
        
        player.getGameStats().addModifiers((StatEffectId)statId, modifiers);
      }
    
    } catch (Exception ex) {
      
      log.error((ex.getCause() != null) ? ex.getCause().getMessage() : "Error on item equip.");
    } 
  }







  
  public static void onItemUnequip(Player player, Item item) {
    try {
      int enchantLevel = item.getEnchantLevel();
      
      if (enchantLevel == 0) {
        return;
      }
      EnchantStatEffectId statId = EnchantStatEffectId.getInstance(item.getObjectId(), item.getEquipmentSlot());
      
      if (player.getGameStats().effectAlreadyAdded((StatEffectId)statId)) {
        player.getGameStats().endEffect((StatEffectId)statId);
      }
    }
    catch (Exception ex) {
      
      log.error((ex.getCause() != null) ? ex.getCause().getMessage() : null);
    } 
  }






  
  private static TreeSet<StatModifier> getArmorModifiers(Item item) {
    TreeSet<StatModifier> modifiers = null;
    
    ArmorType armorType = item.getItemTemplate().getArmorType();
    if (armorType == null) {
      return null;
    }
    int enchantLevel = item.getEnchantLevel();
    
    switch (armorType) {
      
      case BOOK_2H:
        switch (item.getEquipmentSlot()) {
          
          case 8:
            modifiers = EnchantWeapon.DEF3.getModifiers(enchantLevel);
            break;
          case 32:
            modifiers = EnchantWeapon.DEF1.getModifiers(enchantLevel);
            break;
          case 2048:
            modifiers = EnchantWeapon.DEF1.getModifiers(enchantLevel);
            break;
          case 4096:
            modifiers = EnchantWeapon.DEF2.getModifiers(enchantLevel);
            break;
          case 16:
            modifiers = EnchantWeapon.DEF1.getModifiers(enchantLevel);
            break;
        } 
        break;
      case DAGGER_1H:
        switch (item.getEquipmentSlot()) {
          
          case 8:
            modifiers = EnchantWeapon.DEF6.getModifiers(enchantLevel);
            break;
          case 32:
            modifiers = EnchantWeapon.DEF4.getModifiers(enchantLevel);
            break;
          case 2048:
            modifiers = EnchantWeapon.DEF4.getModifiers(enchantLevel);
            break;
          case 4096:
            modifiers = EnchantWeapon.DEF5.getModifiers(enchantLevel);
            break;
          case 16:
            modifiers = EnchantWeapon.DEF4.getModifiers(enchantLevel);
            break;
        } 
        break;
      case BOW:
        switch (item.getEquipmentSlot()) {
          
          case 8:
            modifiers = EnchantWeapon.DEF9.getModifiers(enchantLevel);
            break;
          case 32:
            modifiers = EnchantWeapon.DEF7.getModifiers(enchantLevel);
            break;
          case 2048:
            modifiers = EnchantWeapon.DEF7.getModifiers(enchantLevel);
            break;
          case 4096:
            modifiers = EnchantWeapon.DEF8.getModifiers(enchantLevel);
            break;
          case 16:
            modifiers = EnchantWeapon.DEF7.getModifiers(enchantLevel);
            break;
        } 
        break;
      case ORB_2H:
        switch (item.getEquipmentSlot()) {
          
          case 8:
            modifiers = EnchantWeapon.DEF12.getModifiers(enchantLevel);
            break;
          case 32:
            modifiers = EnchantWeapon.DEF10.getModifiers(enchantLevel);
            break;
          case 2048:
            modifiers = EnchantWeapon.DEF10.getModifiers(enchantLevel);
            break;
          case 4096:
            modifiers = EnchantWeapon.DEF11.getModifiers(enchantLevel);
            break;
          case 16:
            modifiers = EnchantWeapon.DEF10.getModifiers(enchantLevel);
            break;
        } 
        break;
    } 
    return modifiers;
  }






  
  private static TreeSet<StatModifier> getWeaponModifiers(Item item) {
    WeaponType weaponType = item.getItemTemplate().getWeaponType();
    
    if (weaponType == null) {
      return null;
    }
    int enchantLevel = item.getEnchantLevel();
    
    TreeSet<StatModifier> modifiers = null;
    switch (weaponType) {
      
      case BOOK_2H:
        modifiers = EnchantWeapon.SPELLBOOK.getModifiers(enchantLevel);
        break;
      case DAGGER_1H:
        modifiers = EnchantWeapon.DAGGER.getModifiers(enchantLevel);
        break;
      case BOW:
        modifiers = EnchantWeapon.BOW.getModifiers(enchantLevel);
        break;
      case ORB_2H:
        modifiers = EnchantWeapon.ORB.getModifiers(enchantLevel);
        break;
      case STAFF_2H:
        modifiers = EnchantWeapon.STAFF.getModifiers(enchantLevel);
        break;
      case SWORD_1H:
        modifiers = EnchantWeapon.SWORD.getModifiers(enchantLevel);
        break;
      case SWORD_2H:
        modifiers = EnchantWeapon.GREATSWORD.getModifiers(enchantLevel);
        break;
      case MACE_1H:
        modifiers = EnchantWeapon.MACE.getModifiers(enchantLevel);
        break;
      case POLEARM_2H:
        modifiers = EnchantWeapon.POLEARM.getModifiers(enchantLevel);
        break;
    } 
    return modifiers;
  }



  
  private enum EnchantWeapon
  {
    DAGGER
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 2 * level, true));
        return mod;
      }
    },
    
    SWORD
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 2 * level, true));
        return mod;
      }
    },
    
    GREATSWORD
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 4 * level, true));
        return mod;
      }
    },
    
    POLEARM
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 4 * level, true));
        return mod;
      }
    },
    
    BOW
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 4 * level, true));
        return mod;
      }
    },
    
    MACE
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
        return mod;
      }
    },
    
    STAFF
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
        return mod;
      }
    },
    
    SPELLBOOK
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
        return mod;
      }
    },
    
    ORB
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_ATTACK, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.BOOST_MAGICAL_SKILL, 20 * level, true));
        return mod;
      }
    },
    
    SHIELD
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(RateModifier.newInstance(StatEnum.BLOCK, 2 * level, true));
        return mod;
      }
    },
    
    DEF1
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 1 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 10 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
        return mod;
      }
    },
    
    DEF2
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 2 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 12 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
        return mod;
      }
    },
    
    DEF3
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 14 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
        return mod;
      }
    },
    
    DEF4
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 8 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
        return mod;
      }
    },
    
    DEF5
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 5 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 10 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
        return mod;
      }
    },
    
    DEF6
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 4 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 12 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
        return mod;
      }
    },
    
    DEF7
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 3 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 6 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
        return mod;
      }
    },
    
    DEF8
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 4 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 8 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
        return mod;
      }
    },
    
    DEF9
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 5 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 10 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
        return mod;
      }
    },
    
    DEF10
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 4 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 4 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 2 * level, true));
        return mod;
      }
    },
    
    DEF11
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 5 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 6 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 3 * level, true));
        return mod;
      }
    },
    
    DEF12
    {
      
      public TreeSet<StatModifier> getModifiers(int level)
      {
        TreeSet<StatModifier> mod = new TreeSet<StatModifier>();
        mod.add(AddModifier.newInstance(StatEnum.PHYSICAL_DEFENSE, 6 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.MAXHP, 8 * level, true));
        mod.add(AddModifier.newInstance(StatEnum.CRITICAL_RESIST, 4 * level, true));
        return mod;
      }
    };
    
    public abstract TreeSet<StatModifier> getModifiers(int param1Int);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\EnchantService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
