package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.SkillList;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
import com.aionemu.gameserver.skillengine.model.learn.SkillLearnTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class SkillLearnService {
  public static void addNewSkills(Player player, boolean isNewCharacter) {
    int level = player.getCommonData().getLevel();
    PlayerClass playerClass = player.getCommonData().getPlayerClass();
    Race playerRace = player.getCommonData().getRace();

    if (isNewCharacter) {
      player.setSkillList(new SkillList());
    }

    addSkills(player, level, playerClass, playerRace, isNewCharacter);
  }

  public static void addMissingSkills(Player player) {
    int level = player.getCommonData().getLevel();
    PlayerClass playerClass = player.getCommonData().getPlayerClass();
    Race playerRace = player.getCommonData().getRace();

    for (int i = 0; i <= level; i++) {
      addSkills(player, i, playerClass, playerRace, false);
    }

    if (!playerClass.isStartingClass()) {

      PlayerClass startinClass = PlayerClass.getStartingClassFor(playerClass);

      for (int j = 1; j < 10; j++) {
        addSkills(player, j, startinClass, playerRace, false);
      }

      if (player.getSkillList().getSkillEntry(30001) != null) {

        int skillLevel = player.getSkillList().getSkillLevel(30001);
        player.getSkillList().removeSkill(player, 30001);
        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SKILL_LIST(player));
        player.getSkillList().addSkill(player, 30002, skillLevel, true);
      }
    }
  }

  private static void addSkills(Player player, int level, PlayerClass playerClass, Race playerRace,
      boolean isNewCharacter) {
    SkillLearnTemplate[] skillTemplates = DataManager.SKILL_TREE_DATA.getTemplatesFor(playerClass, level, playerRace);

    SkillList playerSkillList = player.getSkillList();

    for (SkillLearnTemplate template : skillTemplates) {

      if (checkLearnIsPossible(playerSkillList, template)) {

        playerSkillList.addSkill(player, template.getSkillId(), template.getSkillLevel(), !isNewCharacter);
      }
    }
  }

  private static boolean checkLearnIsPossible(SkillList playerSkillList, SkillLearnTemplate template) {
    if (playerSkillList.isSkillPresent(template.getSkillId())) {
      return true;
    }
    if ((CustomConfig.SKILL_AUTOLEARN && !template.isStigma())
        || (CustomConfig.STIGMA_AUTOLEARN && template.isStigma())) {
      return true;
    }
    if (template.isAutolearn()) {
      return true;
    }
    return false;
  }
}
