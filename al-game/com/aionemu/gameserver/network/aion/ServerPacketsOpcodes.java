package com.aionemu.gameserver.network.aion;

import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHAT_INIT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CREATE_CHARACTER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_PACKET;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DP_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_ADD_KINAH;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_ADD_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_SEND_EMBLEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_SELF_INTRO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_TITLE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOGIN_QUEUE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MANTRA_EFFECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NICKNAME_CHECK_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PING_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SEARCH;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_DP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_MP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_SELECTED;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import java.util.Map;
import java.util.Set;

public class ServerPacketsOpcodes {
  private static Map<Class<? extends AionServerPacket>, Integer> opcodes = new HashMap<Class<? extends AionServerPacket>, Integer>();

  static {
    Set<Integer> idSet = new HashSet<Integer>();

    addPacketOpcode((Class) SM_STATUPDATE_MP.class, 0, idSet);
    addPacketOpcode((Class) SM_STATUPDATE_HP.class, 1, idSet);
    addPacketOpcode((Class) SM_CHAT_INIT.class, 2, idSet);
    addPacketOpcode((Class) SM_CHANNEL_INFO.class, 3, idSet);
    addPacketOpcode((Class) SM_MACRO_RESULT.class, 4, idSet);
    addPacketOpcode((Class) SM_MACRO_LIST.class, 5, idSet);
    addPacketOpcode((Class) SM_NICKNAME_CHECK_RESPONSE.class, 7, idSet);
    addPacketOpcode((Class) SM_RIFT_ANNOUNCE.class, 8, idSet);
    addPacketOpcode((Class) SM_SET_BIND_POINT.class, 9, idSet);
    addPacketOpcode((Class) SM_ABYSS_RANK.class, 11, idSet);
    addPacketOpcode((Class) SM_FRIEND_UPDATE.class, 12, idSet);
    addPacketOpcode((Class) SM_PETITION.class, 13, idSet);
    addPacketOpcode((Class) SM_RECIPE_DELETE.class, 14, idSet);
    addPacketOpcode((Class) SM_LEARN_RECIPE.class, 15, idSet);
    addPacketOpcode((Class) SM_FLY_TIME.class, 16, idSet);
    addPacketOpcode((Class) SM_DELETE.class, 18, idSet);
    addPacketOpcode((Class) SM_PLAYER_MOVE.class, 19, idSet);
    addPacketOpcode((Class) SM_MESSAGE.class, 20, idSet);
    addPacketOpcode((Class) SM_LOGIN_QUEUE.class, 21, idSet);
    addPacketOpcode((Class) SM_INVENTORY_INFO.class, 22, idSet);
    addPacketOpcode((Class) SM_SYSTEM_MESSAGE.class, 23, idSet);
    addPacketOpcode((Class) SM_DELETE_ITEM.class, 24, idSet);
    addPacketOpcode((Class) SM_ADD_ITEMS.class, 25, idSet);
    addPacketOpcode((Class) SM_UI_SETTINGS.class, 26, idSet);
    addPacketOpcode((Class) SM_UPDATE_ITEM.class, 27, idSet);
    addPacketOpcode((Class) SM_PLAYER_INFO.class, 28, idSet);
    addPacketOpcode((Class) SM_GATHER_STATUS.class, 30, idSet);
    addPacketOpcode((Class) SM_CASTSPELL.class, 31, idSet);
    addPacketOpcode((Class) SM_UPDATE_PLAYER_APPEARANCE.class, 32, idSet);
    addPacketOpcode((Class) SM_GATHER_UPDATE.class, 33, idSet);
    addPacketOpcode((Class) SM_STATUPDATE_DP.class, 34, idSet);
    addPacketOpcode((Class) SM_ATTACK_STATUS.class, 35, idSet);
    addPacketOpcode((Class) SM_STATUPDATE_EXP.class, 36, idSet);
    addPacketOpcode((Class) SM_DP_INFO.class, 37, idSet);
    addPacketOpcode((Class) SM_LEGION_TABS.class, 40, idSet);
    addPacketOpcode((Class) SM_LEGION_UPDATE_NICKNAME.class, 41, idSet);
    addPacketOpcode((Class) SM_NPC_INFO.class, 42, idSet);
    addPacketOpcode((Class) SM_ENTER_WORLD_CHECK.class, 43, idSet);
    addPacketOpcode((Class) SM_PLAYER_SPAWN.class, 45, idSet);
    addPacketOpcode((Class) SM_GATHERABLE_INFO.class, 47, idSet);
    addPacketOpcode((Class) SM_TELEPORT_LOC.class, 48, idSet);
    addPacketOpcode((Class) SM_ATTACK.class, 50, idSet);
    addPacketOpcode((Class) SM_MOVE.class, 53, idSet);
    addPacketOpcode((Class) SM_TRANSFORM.class, 54, idSet);
    addPacketOpcode((Class) SM_DIALOG_WINDOW.class, 56, idSet);
    addPacketOpcode((Class) SM_SELL_ITEM.class, 58, idSet);
    addPacketOpcode((Class) SM_VIEW_PLAYER_DETAILS.class, 63, idSet);
    addPacketOpcode((Class) SM_PLAYER_STATE.class, 64, idSet);
    addPacketOpcode((Class) SM_WEATHER.class, 65, idSet);
    addPacketOpcode((Class) SM_GAME_TIME.class, 66, idSet);
    addPacketOpcode((Class) SM_EMOTION.class, 67, idSet);
    addPacketOpcode((Class) SM_LOOKATOBJECT.class, 68, idSet);
    addPacketOpcode((Class) SM_TIME_CHECK.class, 69, idSet);
    addPacketOpcode((Class) SM_SKILL_CANCEL.class, 70, idSet);
    addPacketOpcode((Class) SM_TARGET_SELECTED.class, 71, idSet);
    addPacketOpcode((Class) SM_SKILL_LIST.class, 72, idSet);
    addPacketOpcode((Class) SM_CASTSPELL_END.class, 73, idSet);
    addPacketOpcode((Class) SM_SKILL_ACTIVATION.class, 74, idSet);
    addPacketOpcode((Class) SM_SKILL_REMOVE.class, 75, idSet);
    addPacketOpcode((Class) SM_ABNORMAL_EFFECT.class, 78, idSet);
    addPacketOpcode((Class) SM_ABNORMAL_STATE.class, 79, idSet);
    addPacketOpcode((Class) SM_QUESTION_WINDOW.class, 80, idSet);
    addPacketOpcode((Class) SM_SKILL_COOLDOWN.class, 81, idSet);
    addPacketOpcode((Class) SM_INFLUENCE_RATIO.class, 83, idSet);
    addPacketOpcode((Class) SM_NAME_CHANGE.class, 84, idSet);
    addPacketOpcode((Class) SM_GROUP_INFO.class, 86, idSet);
    addPacketOpcode((Class) SM_SHOW_NPC_ON_MAP.class, 87, idSet);
    addPacketOpcode((Class) SM_GROUP_MEMBER_INFO.class, 89, idSet);
    addPacketOpcode((Class) SM_QUIT_RESPONSE.class, 94, idSet);
    addPacketOpcode((Class) SM_LEVEL_UPDATE.class, 98, idSet);
    addPacketOpcode((Class) SM_KEY.class, 100, idSet);
    addPacketOpcode((Class) SM_EXCHANGE_REQUEST.class, 102, idSet);
    addPacketOpcode((Class) SM_SUMMON_PANEL_REMOVE.class, 103, idSet);
    addPacketOpcode((Class) SM_EXCHANGE_ADD_ITEM.class, 105, idSet);
    addPacketOpcode((Class) SM_EXCHANGE_CONFIRMATION.class, 106, idSet);
    addPacketOpcode((Class) SM_EXCHANGE_ADD_KINAH.class, 107, idSet);
    addPacketOpcode((Class) SM_EMOTION_LIST.class, 109, idSet);
    addPacketOpcode((Class) SM_TARGET_UPDATE.class, 111, idSet);
    addPacketOpcode((Class) SM_LEGION_UPDATE_SELF_INTRO.class, 117, idSet);
    addPacketOpcode((Class) SM_RIFT_STATUS.class, 118, idSet);
    addPacketOpcode((Class) SM_QUEST_ACCEPTED.class, 120, idSet);
    addPacketOpcode((Class) SM_QUEST_LIST.class, 121, idSet);
    addPacketOpcode((Class) SM_PING_RESPONSE.class, 124, idSet);
    addPacketOpcode((Class) SM_NEARBY_QUESTS.class, 125, idSet);
    addPacketOpcode((Class) SM_CUBE_UPDATE.class, 126, idSet);
    addPacketOpcode((Class) SM_FRIEND_LIST.class, 128, idSet);
    addPacketOpcode((Class) SM_UPDATE_NOTE.class, 132, idSet);
    addPacketOpcode((Class) SM_ITEM_COOLDOWN.class, 133, idSet);
    addPacketOpcode((Class) SM_PLAY_MOVIE.class, 135, idSet);
    addPacketOpcode((Class) SM_LEGION_INFO.class, 138, idSet);
    addPacketOpcode((Class) SM_LEGION_LEAVE_MEMBER.class, 140, idSet);
    addPacketOpcode((Class) SM_LEGION_ADD_MEMBER.class, 141, idSet);
    addPacketOpcode((Class) SM_LEGION_UPDATE_TITLE.class, 142, idSet);
    addPacketOpcode((Class) SM_LEGION_UPDATE_MEMBER.class, 143, idSet);
    addPacketOpcode((Class) SM_BROKER_REGISTRATION_SERVICE.class, 147, idSet);
    addPacketOpcode((Class) SM_BROKER_SETTLED_LIST.class, 149, idSet);
    addPacketOpcode((Class) SM_SUMMON_OWNER_REMOVE.class, 150, idSet);
    addPacketOpcode((Class) SM_SUMMON_PANEL.class, 151, idSet);
    addPacketOpcode((Class) SM_SUMMON_UPDATE.class, 153, idSet);
    addPacketOpcode((Class) SM_LEGION_EDIT.class, 154, idSet);
    addPacketOpcode((Class) SM_LEGION_MEMBERLIST.class, 155, idSet);
    addPacketOpcode((Class) SM_SUMMON_USESKILL.class, 158, idSet);
    addPacketOpcode((Class) SM_MAIL_SERVICE.class, 159, idSet);
    addPacketOpcode((Class) SM_PRIVATE_STORE.class, 162, idSet);
    addPacketOpcode((Class) SM_ABYSS_RANK_UPDATE.class, 164, idSet);
    addPacketOpcode((Class) SM_GROUP_LOOT.class, 165, idSet);
    addPacketOpcode((Class) SM_MAY_LOGIN_INTO_GAME.class, 167, idSet);
    addPacketOpcode((Class) SM_PONG.class, 170, idSet);
    addPacketOpcode((Class) SM_PLAYER_ID.class, 171, idSet);
    addPacketOpcode((Class) SM_KISK_UPDATE.class, 172, idSet);
    addPacketOpcode((Class) SM_BROKER_ITEMS.class, 174, idSet);
    addPacketOpcode((Class) SM_PRIVATE_STORE_NAME.class, 175, idSet);
    addPacketOpcode((Class) SM_BROKER_REGISTERED_LIST.class, 177, idSet);
    addPacketOpcode((Class) SM_ASCENSION_MORPH.class, 178, idSet);
    addPacketOpcode((Class) SM_CRAFT_UPDATE.class, 179, idSet);
    addPacketOpcode((Class) SM_CUSTOM_SETTINGS.class, 180, idSet);
    addPacketOpcode((Class) SM_ITEM_USAGE_ANIMATION.class, 181, idSet);
    addPacketOpcode((Class) SM_DUEL.class, 183, idSet);
    addPacketOpcode((Class) SM_RESURRECT.class, 190, idSet);
    addPacketOpcode((Class) SM_DIE.class, 191, idSet);
    addPacketOpcode((Class) SM_TELEPORT_MAP.class, 192, idSet);
    addPacketOpcode((Class) SM_FORCED_MOVE.class, 193, idSet);
    addPacketOpcode((Class) SM_WAREHOUSE_INFO.class, 196, idSet);
    addPacketOpcode((Class) SM_DELETE_WAREHOUSE_ITEM.class, 198, idSet);
    addPacketOpcode((Class) SM_WAREHOUSE_UPDATE.class, 199, idSet);
    addPacketOpcode((Class) SM_UPDATE_WAREHOUSE_ITEM.class, 201, idSet);
    addPacketOpcode((Class) SM_TITLE_LIST.class, 204, idSet);
    addPacketOpcode((Class) SM_TITLE_SET.class, 207, idSet);
    addPacketOpcode((Class) SM_CRAFT_ANIMATION.class, 208, idSet);
    addPacketOpcode((Class) SM_TITLE_UPDATE.class, 209, idSet);
    addPacketOpcode((Class) SM_LEGION_SEND_EMBLEM.class, 211, idSet);
    addPacketOpcode((Class) SM_LEGION_UPDATE_EMBLEM.class, 213, idSet);
    addPacketOpcode((Class) SM_FRIEND_RESPONSE.class, 218, idSet);
    addPacketOpcode((Class) SM_BLOCK_LIST.class, 220, idSet);
    addPacketOpcode((Class) SM_BLOCK_RESPONSE.class, 221, idSet);
    addPacketOpcode((Class) SM_FRIEND_NOTIFY.class, 223, idSet);
    addPacketOpcode((Class) SM_USE_OBJECT.class, 227, idSet);
    addPacketOpcode((Class) SM_CHARACTER_LIST.class, 228, idSet);
    addPacketOpcode((Class) SM_L2AUTH_LOGIN_CHECK.class, 229, idSet);
    addPacketOpcode((Class) SM_DELETE_CHARACTER.class, 230, idSet);
    addPacketOpcode((Class) SM_CREATE_CHARACTER.class, 231, idSet);
    addPacketOpcode((Class) SM_TARGET_IMMOBILIZE.class, 232, idSet);
    addPacketOpcode((Class) SM_RESTORE_CHARACTER.class, 233, idSet);
    addPacketOpcode((Class) SM_LOOT_ITEMLIST.class, 234, idSet);
    addPacketOpcode((Class) SM_LOOT_STATUS.class, 235, idSet);
    addPacketOpcode((Class) SM_MANTRA_EFFECT.class, 236, idSet);
    addPacketOpcode((Class) SM_RECIPE_LIST.class, 237, idSet);
    addPacketOpcode((Class) SM_SIEGE_LOCATION_INFO.class, 239, idSet);
    addPacketOpcode((Class) SM_PLAYER_SEARCH.class, 241, idSet);
    addPacketOpcode((Class) SM_ALLIANCE_MEMBER_INFO.class, 242, idSet);
    addPacketOpcode((Class) SM_ALLIANCE_INFO.class, 243, idSet);
    addPacketOpcode((Class) SM_LEAVE_GROUP_MEMBER.class, 245, idSet);
    addPacketOpcode((Class) SM_ALLIANCE_READY_CHECK.class, 246, idSet);
    addPacketOpcode((Class) SM_SHOW_BRAND.class, 247, idSet);
    addPacketOpcode((Class) SM_PRICES.class, 248, idSet);
    addPacketOpcode((Class) SM_TRADELIST.class, 251, idSet);
    addPacketOpcode((Class) SM_VERSION_CHECK.class, 252, idSet);
    addPacketOpcode((Class) SM_RECONNECT_KEY.class, 253, idSet);
    addPacketOpcode((Class) SM_STATS_INFO.class, 255, idSet);
    addPacketOpcode((Class) SM_QUESTIONNAIRE.class, 189, idSet);
    addPacketOpcode((Class) SM_CUSTOM_PACKET.class, 99999, idSet);
  }

  static int getOpcode(Class<? extends AionServerPacket> packetClass) {
    Integer opcode = opcodes.get(packetClass);
    if (opcode == null) {
      throw new IllegalArgumentException("There is no opcode for " + packetClass + " defined.");
    }
    return opcode.intValue();
  }

  private static void addPacketOpcode(Class<? extends AionServerPacket> packetClass, int opcode, Set<Integer> idSet) {
    if (opcode < 0) {
      return;
    }
    if (idSet.contains(Integer.valueOf(opcode))) {
      throw new IllegalArgumentException(String.format("There already exists another packet with id 0x%02X",
          new Object[] { Integer.valueOf(opcode) }));
    }

    idSet.add(Integer.valueOf(opcode));
    opcodes.put(packetClass, Integer.valueOf(opcode));
  }
}
