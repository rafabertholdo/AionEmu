package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class CraftSkillUpdateService {
  private static final Logger log = Logger.getLogger(CraftSkillUpdateService.class);

  private static final Map<Integer, LearnTemplate> npcBySkill = new HashMap<Integer, LearnTemplate>();
  private static final Map<Integer, Integer> cost = new HashMap<Integer, Integer>();
  private static final List<Integer> craftingSkillIds = new ArrayList<Integer>();

  public static final CraftSkillUpdateService getInstance() {
    return SingletonHolder.instance;
  }

  private CraftSkillUpdateService() {
    npcBySkill.put(Integer.valueOf(204096), new LearnTemplate(30002, false, "Extract Vitality"));
    npcBySkill.put(Integer.valueOf(204257), new LearnTemplate(30003, false, "Extract Aether"));

    npcBySkill.put(Integer.valueOf(204100), new LearnTemplate(40001, true, "Cooking"));
    npcBySkill.put(Integer.valueOf(204104), new LearnTemplate(40002, true, "Weaponsmithing"));
    npcBySkill.put(Integer.valueOf(204106), new LearnTemplate(40003, true, "Armorsmithing"));
    npcBySkill.put(Integer.valueOf(204110), new LearnTemplate(40004, true, "Tailoring"));
    npcBySkill.put(Integer.valueOf(204102), new LearnTemplate(40007, true, "Alchemy"));
    npcBySkill.put(Integer.valueOf(204108), new LearnTemplate(40008, true, "Handicrafting"));

    npcBySkill.put(Integer.valueOf(203780), new LearnTemplate(30002, false, "Extract Vitality"));
    npcBySkill.put(Integer.valueOf(203782), new LearnTemplate(30003, false, "Extract Aether"));

    npcBySkill.put(Integer.valueOf(203784), new LearnTemplate(40001, true, "Cooking"));
    npcBySkill.put(Integer.valueOf(203788), new LearnTemplate(40002, true, "Weaponsmithing"));
    npcBySkill.put(Integer.valueOf(203790), new LearnTemplate(40003, true, "Armorsmithing"));
    npcBySkill.put(Integer.valueOf(203793), new LearnTemplate(40004, true, "Tailoring"));
    npcBySkill.put(Integer.valueOf(203786), new LearnTemplate(40007, true, "Alchemy"));
    npcBySkill.put(Integer.valueOf(203792), new LearnTemplate(40008, true, "Handicrafting"));

    cost.put(Integer.valueOf(0), Integer.valueOf(3500));
    cost.put(Integer.valueOf(99), Integer.valueOf(17000));
    cost.put(Integer.valueOf(199), Integer.valueOf(115000));
    cost.put(Integer.valueOf(299), Integer.valueOf(460000));
    cost.put(Integer.valueOf(399), Integer.valueOf(1500000));

    craftingSkillIds.add(Integer.valueOf(40001));
    craftingSkillIds.add(Integer.valueOf(40002));
    craftingSkillIds.add(Integer.valueOf(40003));
    craftingSkillIds.add(Integer.valueOf(40004));
    craftingSkillIds.add(Integer.valueOf(40007));
    craftingSkillIds.add(Integer.valueOf(40008));

    log.info("CraftSkillUpdateService: Initialized.");
  }

  class LearnTemplate {
    private int skillId;

    private boolean isCraftSkill;

    public boolean isCraftSkill() {
      return this.isCraftSkill;
    }

    LearnTemplate(int skillId, boolean isCraftSkill, String skillName) {
      this.skillId = skillId;
      this.isCraftSkill = isCraftSkill;
    }

    public int getSkillId() {
      return this.skillId;
    }
  }

  public void learnSkill(Player player, Npc npc) {
    if (player.getLevel() < 10)
      return;
    LearnTemplate template = npcBySkill.get(Integer.valueOf(npc.getNpcId()));
    if (template == null)
      return;
    final int skillId = template.getSkillId();
    if (skillId == 0) {
      return;
    }
    int skillLvl = 0;
    if (player.getSkillList().isSkillPresent(skillId)) {
      skillLvl = player.getSkillList().getSkillLevel(skillId);
    }
    if (!cost.containsKey(Integer.valueOf(skillLvl))) {
      return;
    }

    if (isCraftingSkill(skillId) && !canLearnMoreMasterCraftingSkill(player) && skillLvl == 399) {

      PacketSendUtility.sendMessage(player, "You can only master 2 craft skill.");

      return;
    }
    final int price = ((Integer) cost.get(Integer.valueOf(skillLvl))).intValue();
    Item kinahItem = player.getInventory().getKinahItem();
    if (price > kinahItem.getItemCount()) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_MESSAGE(0, null, "You don't have enough Kinah.", ChatType.ANNOUNCEMENTS));
      return;
    }
    final int skillLevel = skillLvl;
    RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) npc) {

      public void acceptRequest(Creature requester, Player responder) {
        if (ItemService.decreaseKinah(responder, price)) {

          responder.getSkillList().addSkill(responder, skillId, skillLevel + 1, true);
          responder.getRecipeList().autoLearnRecipe(responder, skillId, skillLevel + 1);
        }
      }

      public void denyRequest(Creature requester, Player responder) {
      }
    };
    boolean result = player.getResponseRequester().putRequest(900852, responseHandler);
    if (result) {
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_QUESTION_WINDOW(900852, 0, new Object[] {
          new DescriptionId(DataManager.SKILL_DATA.getSkillTemplate(skillId).getNameId()), String.valueOf(price) }));
    }
  }

  private static boolean isCraftingSkill(int skillId) {
    for (Iterator<Integer> i$ = craftingSkillIds.iterator(); i$.hasNext();) {
      int i = ((Integer) i$.next()).intValue();

      if (i == skillId)
        return true;
    }

    return false;
  }

  private static int getTotalMasterCraftingSkills(Player player) {
    int mastered = 0;

    for (Iterator<Integer> i$ = craftingSkillIds.iterator(); i$.hasNext();) {
      int i = ((Integer) i$.next()).intValue();

      int skillId = i;
      int skillLvl = 0;
      if (player.getSkillList().isSkillPresent(skillId)) {

        skillLvl = player.getSkillList().getSkillLevel(skillId);
        if (skillLvl > 399) {
          mastered++;
        }
      }
    }

    return mastered;
  }

  private static boolean canLearnMoreMasterCraftingSkill(Player player) {
    if (getTotalMasterCraftingSkills(player) < 2) {
      return true;
    }
    return false;
  }

  private static class SingletonHolder {
    protected static final CraftSkillUpdateService instance = new CraftSkillUpdateService();
  }
}
