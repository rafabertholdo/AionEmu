package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
import com.aionemu.gameserver.model.templates.item.RequireSkill;
import com.aionemu.gameserver.model.templates.item.Stigma;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class StigmaService {
  private static final Logger log = Logger.getLogger(StigmaService.class);

  public static boolean notifyEquipAction(Player player, Item resultItem) {
    if (resultItem.getItemTemplate().isStigma()) {

      int currentStigmaCount = player.getEquipment().getEquippedItemsStigma().size();

      int lvl = player.getLevel();

      if (lvl / 10 + player.getCommonData().getAdvencedStigmaSlotSize() <= currentStigmaCount) {

        log.info("[AUDIT]Possible client hack stigma count big :O player: " + player.getName());
        return false;
      }

      if (!resultItem.getItemTemplate().isClassSpecific(player.getCommonData().getPlayerClass())) {

        log.info("[AUDIT]Possible client hack not valid for class. player: " + player.getName());
        return false;
      }

      Stigma stigmaInfo = resultItem.getItemTemplate().getStigma();

      if (stigmaInfo == null) {

        log.warn("Stigma info missing for item: " + resultItem.getItemTemplate().getTemplateId());
        return false;
      }

      int skillId = stigmaInfo.getSkillid();
      int shardCount = stigmaInfo.getShard();
      if (player.getInventory().getItemCountByItemId(141000001) < shardCount) {

        log.info("[AUDIT]Possible client hack stigma shard count low player: " + player.getName());
        return false;
      }
      int needSkill = stigmaInfo.getRequireSkill().size();
      for (RequireSkill rs : stigmaInfo.getRequireSkill()) {

        Iterator<Integer> i$ = rs.getSkillId().iterator();
        if (i$.hasNext()) {
          int id = ((Integer) i$.next()).intValue();

          if (player.getSkillList().isSkillPresent(id)) {
            needSkill--;
          }
        }

      }
      if (needSkill != 0) {
        log.info("[AUDIT]Possible client hack advenced stigma skill player: " + player.getName());
      }

      ItemService.decreaseItemCountByItemId(player, 141000001, shardCount);
      SkillListEntry skill = new SkillListEntry(skillId, true, stigmaInfo.getSkilllvl(), PersistentState.NOACTION);
      player.getSkillList().addSkill(skill);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SKILL_LIST(skill, 1300401));
    }
    return true;
  }

  public static boolean notifyUnequipAction(Player player, Item resultItem) {
    if (resultItem.getItemTemplate().isStigma()) {

      Stigma stigmaInfo = resultItem.getItemTemplate().getStigma();
      int skillId = stigmaInfo.getSkillid();
      for (Item item : player.getEquipment().getEquippedItemsStigma()) {

        Stigma si = item.getItemTemplate().getStigma();
        if (resultItem == item || si == null)
          continue;
        for (RequireSkill rs : si.getRequireSkill()) {

          if (rs.getSkillId().contains(Integer.valueOf(skillId))) {

            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_SYSTEM_MESSAGE(1300410,
                    new Object[] { new DescriptionId(resultItem.getItemTemplate().getNameId()),
                        new DescriptionId(item.getItemTemplate().getNameId()) }));
            return false;
          }
        }
      }
      player.getSkillList().removeSkill(player, skillId);
      int nameId = DataManager.SKILL_DATA.getSkillTemplate(skillId).getNameId();
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_SYSTEM_MESSAGE(1300403, new Object[] { new DescriptionId(nameId) }));
    }
    return true;
  }

  public static void onPlayerLogin(Player player) {
    List<Item> equippedItems = player.getEquipment().getEquippedItemsStigma();
    for (Item item : equippedItems) {

      if (item.getItemTemplate().isStigma()) {

        Stigma stigmaInfo = item.getItemTemplate().getStigma();

        if (stigmaInfo == null) {

          log.warn("Stigma info missing for item: " + item.getItemTemplate().getTemplateId());
          return;
        }
        int skillId = stigmaInfo.getSkillid();
        SkillListEntry skill = new SkillListEntry(skillId, true, stigmaInfo.getSkilllvl(), PersistentState.NOACTION);
        player.getSkillList().addSkill(skill);
      }
    }

    for (Item item : equippedItems) {

      if (item.getItemTemplate().isStigma()) {

        int currentStigmaCount = player.getEquipment().getEquippedItemsStigma().size();

        int lvl = player.getLevel();

        if (lvl / 10 + player.getCommonData().getAdvencedStigmaSlotSize() < currentStigmaCount) {

          log.info("[AUDIT]Possible client hack stigma count big :O player: " + player.getName());
          player.getEquipment().unEquipItem(item.getObjectId(), 0);

          continue;
        }
        Stigma stigmaInfo = item.getItemTemplate().getStigma();

        if (stigmaInfo == null) {

          log.warn("Stigma info missing for item: " + item.getItemTemplate().getTemplateId());
          player.getEquipment().unEquipItem(item.getObjectId(), 0);

          continue;
        }
        int needSkill = stigmaInfo.getRequireSkill().size();
        for (RequireSkill rs : stigmaInfo.getRequireSkill()) {

          for (Iterator<Integer> i$ = rs.getSkillId().iterator(); i$.hasNext();) {
            int id = ((Integer) i$.next()).intValue();

            if (player.getSkillList().isSkillPresent(id)) {
              needSkill--;
            }
          }

        }

        if (needSkill != 0) {

          log.info("[AUDIT]Possible client hack advenced stigma skill player: " + player.getName());
          player.getEquipment().unEquipItem(item.getObjectId(), 0);
          continue;
        }
        if (!item.getItemTemplate().isClassSpecific(player.getCommonData().getPlayerClass())) {

          log.info("[AUDIT]Possible client hack not valid for class. player: " + player.getName());
          player.getEquipment().unEquipItem(item.getObjectId(), 0);
        }
      }
    }
  }
}
