package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import java.nio.ByteBuffer;

public class SM_SYSTEM_MESSAGE extends AionServerPacket {
  public static SM_SYSTEM_MESSAGE CURRENT_LOCATION(int worldId, float x, float y, float z) {
    return new SM_SYSTEM_MESSAGE(230038,
        new Object[] { Integer.valueOf(worldId), Float.valueOf(x), Float.valueOf(y), Float.valueOf(z) });
  }

  public static final SM_SYSTEM_MESSAGE BUDDYLIST_BUSY = new SM_SYSTEM_MESSAGE(900847, new Object[0]);

  public static SM_SYSTEM_MESSAGE PLAYER_IS_OFFLINE(String playerName) {
    return new SM_SYSTEM_MESSAGE(1300627, new Object[] { playerName });
  }

  public static SM_SYSTEM_MESSAGE USE_ITEM(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300423, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE REQUEST_TRADE(String playerName) {
    return new SM_SYSTEM_MESSAGE(1300353, new Object[] { playerName });
  }

  public static SM_SYSTEM_MESSAGE DIE = new SM_SYSTEM_MESSAGE(1340000, new Object[0]);

  public static SM_SYSTEM_MESSAGE REVIVE = new SM_SYSTEM_MESSAGE(1300738, new Object[0]);

  public static SM_SYSTEM_MESSAGE EXP(String _exp) {
    return new SM_SYSTEM_MESSAGE(1370002, new Object[] { _exp });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(String nameA, String nameB) {
    return new SM_SYSTEM_MESSAGE(1350001, new Object[] { nameA, nameB });
  }

  public static SM_SYSTEM_MESSAGE GATHER_SKILL_POINT_UP(String skillName, int newLevel) {
    return new SM_SYSTEM_MESSAGE(1330005, new Object[] { skillName, Integer.valueOf(newLevel) });
  }

  public static SM_SYSTEM_MESSAGE GATHER_SUCCESS_GETEXP() {
    return new SM_SYSTEM_MESSAGE(1330058, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_START_1_BASIC(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1330077, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_SUCCESS_1_BASIC(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1330078, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_FAIL_1_BASIC(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1330079, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_CANCEL_1_BASIC() {
    return new SM_SYSTEM_MESSAGE(1330080, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_INVENTORY_IS_FULL() {
    return new SM_SYSTEM_MESSAGE(1330081, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_SUCCESS_GETEXP() {
    return new SM_SYSTEM_MESSAGE(1330082, new Object[0]);
  }

  public static final SM_SYSTEM_MESSAGE REQUEST_TRADE = new SM_SYSTEM_MESSAGE(1300353, new Object[0]);

  public static final SM_SYSTEM_MESSAGE BUDDYLIST_LIST_FULL = new SM_SYSTEM_MESSAGE(1300887, new Object[0]);

  public static final SM_SYSTEM_MESSAGE BUDDYLIST_NOT_IN_LIST = new SM_SYSTEM_MESSAGE(1300889, new Object[0]);

  public static SM_SYSTEM_MESSAGE SERVER_SHUTDOWN(int seconds) {
    return new SM_SYSTEM_MESSAGE(1300642, new Object[] { Integer.toString(seconds) });
  }

  public static SM_SYSTEM_MESSAGE BLOCKLIST_NO_BUDDY = new SM_SYSTEM_MESSAGE(1300891, new Object[0]);

  public static SM_SYSTEM_MESSAGE BLOCKLIST_ALREADY_BLOCKED = new SM_SYSTEM_MESSAGE(1300894, new Object[0]);

  public static SM_SYSTEM_MESSAGE BLOCKLIST_NOT_BLOCKED = new SM_SYSTEM_MESSAGE(1300897, new Object[0]);

  public static SM_SYSTEM_MESSAGE YOU_ARE_BLOCKED_BY(String blocker) {
    return new SM_SYSTEM_MESSAGE(1300628, new Object[] { blocker });
  }

  public static SM_SYSTEM_MESSAGE DUEL_ASKED_BY(String player) {
    return new SM_SYSTEM_MESSAGE(1301065, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_ASKED_TO(String player) {
    return new SM_SYSTEM_MESSAGE(1300094, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_REJECTED_BY(String player) {
    return new SM_SYSTEM_MESSAGE(1300097, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_YOU_WON_AGAINST(String player) {
    return new SM_SYSTEM_MESSAGE(1300098, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_YOU_LOST_AGAINST(String player) {
    return new SM_SYSTEM_MESSAGE(1300099, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_START = new SM_SYSTEM_MESSAGE(1300770, new Object[0]);

  public static SM_SYSTEM_MESSAGE DUEL_END = new SM_SYSTEM_MESSAGE(1300771, new Object[0]);

  public static SM_SYSTEM_MESSAGE DUEL_STARTING_WITH(String player) {
    return new SM_SYSTEM_MESSAGE(1300777, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_REJECT_DUEL_OF(String player) {
    return new SM_SYSTEM_MESSAGE(1301064, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_CANCEL_DUEL_BY(String player) {
    return new SM_SYSTEM_MESSAGE(1300134, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_CANCEL_DUEL_WITH(String player) {
    return new SM_SYSTEM_MESSAGE(1300135, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DUEL_PARTNER_INVALID(String partner) {
    return new SM_SYSTEM_MESSAGE(1300091, new Object[] { partner });
  }

  public static SM_SYSTEM_MESSAGE SOUL_BOUND_ITEM_SUCCEED(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300485, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE SOUL_BOUND_ITEM_CANCELED(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300487, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_DICE_RESULT_ME(int dice) {
    return new SM_SYSTEM_MESSAGE(1390162, new Object[] { Integer.valueOf(dice) });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_DICE_RESULT_OTHER(String player, int dice) {
    return new SM_SYSTEM_MESSAGE(1390163, new Object[] { player, Integer.valueOf(dice) });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_DICE_GIVEUP_ME() {
    return new SM_SYSTEM_MESSAGE(1390164, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_DICE_GIVEUP_OTHER(String player) {
    return new SM_SYSTEM_MESSAGE(1390165, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_GET_ITEM_ME(DescriptionId itemDesc) {
    return new SM_SYSTEM_MESSAGE(1390180, new Object[] { itemDesc });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_GET_ITEM_OTHER(String player, DescriptionId itemDesc) {
    return new SM_SYSTEM_MESSAGE(1390181, new Object[] { player, itemDesc });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_PAY_ACCOUNT_ME(long kinah) {
    return new SM_SYSTEM_MESSAGE(1390185, new Object[] { Long.valueOf(kinah) });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_PAY_ACCOUNT_OTHER(String player, long kinah) {
    return new SM_SYSTEM_MESSAGE(1390186, new Object[] { player, Long.valueOf(kinah) });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_PAY_DISTRIBUTE(long kinah, int count, long Kinah) {
    return new SM_SYSTEM_MESSAGE(1390187,
        new Object[] { Long.valueOf(kinah), Integer.valueOf(count), Long.valueOf(Kinah) });
  }

  public static SM_SYSTEM_MESSAGE REQUEST_GROUP_INVITE(String player) {
    return new SM_SYSTEM_MESSAGE(1300173, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE PARTY_HE_BECOME_OFFLINE(String player) {
    return new SM_SYSTEM_MESSAGE(1300175, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE REJECT_GROUP_INVITE(String player) {
    return new SM_SYSTEM_MESSAGE(1300161, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE PLAYER_IN_ANOTHER_GROUP(String player) {
    return new SM_SYSTEM_MESSAGE(1300169, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE INVITED_PLAYER_OFFLINE() {
    return new SM_SYSTEM_MESSAGE(1300159, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE MEMBER_LEFT_GROUP(String player) {
    return new SM_SYSTEM_MESSAGE(1300168, new Object[] { player });
  }

  public static SM_SYSTEM_MESSAGE DISBAND_GROUP() {
    return new SM_SYSTEM_MESSAGE(1300167, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE YOU_LEFT_GROUP() {
    return new SM_SYSTEM_MESSAGE(1300043, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SELECTED_TARGET_DEAD() {
    return new SM_SYSTEM_MESSAGE(1300044, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE DURING_FLYING_PATH_NOT_LEFT_GROUP() {
    return new SM_SYSTEM_MESSAGE(1300047, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE FULL_GROUP() {
    return new SM_SYSTEM_MESSAGE(1300152, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE CHANGE_GROUP_LEADER() {
    return new SM_SYSTEM_MESSAGE(1300155, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE ONLY_GROUP_LEADER_CAN_INVITE() {
    return new SM_SYSTEM_MESSAGE(1300160, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE CANNOT_INVITE_YOURSELF() {
    return new SM_SYSTEM_MESSAGE(1300162, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE CANNOT_INVITE_BECAUSE_YOU_DEAD() {
    return new SM_SYSTEM_MESSAGE(1300163, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE INVITED_ANOTHER_GROUP_MEMBER(String player) {
    return new SM_SYSTEM_MESSAGE(1300169, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE INVITED_YOUR_GROUP_MEMBER(String player) {
    return new SM_SYSTEM_MESSAGE(1300170, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_CANT_INVITE_OTHER_RACE() {
    return new SM_SYSTEM_MESSAGE(1300188, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEVEL_NOT_ENOUGH_FOR_SEARCH(String level) {
    return new SM_SYSTEM_MESSAGE(1400341, new Object[] { level });
  }

  public static SM_SYSTEM_MESSAGE LEVEL_NOT_ENOUGH_FOR_WHISPER(String level) {
    return new SM_SYSTEM_MESSAGE(1310004, new Object[] { level });
  }

  public static SM_SYSTEM_MESSAGE SOUL_HEALED() {
    return new SM_SYSTEM_MESSAGE(1300674, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE DONT_HAVE_RECOVERED_EXP() {
    return new SM_SYSTEM_MESSAGE(1300682, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_INVITED_HIS_PARTY(String name) {
    return new SM_SYSTEM_MESSAGE(1300189, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_CHANGE_LEADER(String name, String name2) {
    return new SM_SYSTEM_MESSAGE(1300986, new Object[] { name, name2 });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_PROMOTE_MANAGER(String name) {
    return new SM_SYSTEM_MESSAGE(1300984, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_DEMOTE_MANAGER(String name) {
    return new SM_SYSTEM_MESSAGE(1300985, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_CAN_NOT_INVITE_SELF() {
    return new SM_SYSTEM_MESSAGE(1301006, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_REJECT_INVITATION(String name) {
    return new SM_SYSTEM_MESSAGE(1300190, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CANT_INVITE_WHEN_HE_IS_ASKED_QUESTION(String name) {
    return new SM_SYSTEM_MESSAGE(1300191, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OTHER_ALLIANCE(String name) {
    return new SM_SYSTEM_MESSAGE(1300192, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OUR_ALLIANCE(String name) {
    return new SM_SYSTEM_MESSAGE(1300193, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CAN_NOT_INVITE_HIM_HE_IS_NOT_PARTY_LEADER(String name) {
    return new SM_SYSTEM_MESSAGE(1300194, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CAN_NOT_INVITE_HIM(String name) {
    return new SM_SYSTEM_MESSAGE(1300195, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CANT_ADD_NEW_MEMBER() {
    return new SM_SYSTEM_MESSAGE(1300196, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_ONLY_PARTY_LEADER_CAN_LEAVE_ALLIANCE() {
    return new SM_SYSTEM_MESSAGE(1300197, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_YOUR_PARTY_IS_NOT_ALLIANCE_MEMBER() {
    return new SM_SYSTEM_MESSAGE(1300198, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HIS_PARTY_LEAVE_ALLIANCE(String name) {
    return new SM_SYSTEM_MESSAGE(1300199, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_MY_PARTY_LEAVE_ALLIANCE() {
    return new SM_SYSTEM_MESSAGE(1300200, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_DISPERSED() {
    return new SM_SYSTEM_MESSAGE(1300201, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_LEAVED_PARTY(String name) {
    return new SM_SYSTEM_MESSAGE(1300202, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_LEAVED_PARTY_OFFLINE_TIMEOUT(String name) {
    return new SM_SYSTEM_MESSAGE(1300203, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_BANISHED(String name) {
    return new SM_SYSTEM_MESSAGE(1300204, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_BECOME_PARTY_LEADER(String name) {
    return new SM_SYSTEM_MESSAGE(1300205, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_ENTERED_FORCE() {
    return new SM_SYSTEM_MESSAGE(1390263, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_INVITED_HIM(String name) {
    return new SM_SYSTEM_MESSAGE(1301017, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_HE_ENTERED_FORCE(String name) {
    return new SM_SYSTEM_MESSAGE(1400013, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_BAN_ME(String name) {
    return new SM_SYSTEM_MESSAGE(1300979, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_BAN_HIM(String name, String name2) {
    return new SM_SYSTEM_MESSAGE(1300980, new Object[] { name, name2 });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_OFFLINE_MEMBER() {
    return new SM_SYSTEM_MESSAGE(1301008, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_NO_USER_TO_INVITE() {
    return new SM_SYSTEM_MESSAGE(1301003, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_CANT_INVITE_WHEN_DEAD() {
    return new SM_SYSTEM_MESSAGE(1301007, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_PARTY_HIM(String s0, String s1) {
    return new SM_SYSTEM_MESSAGE(1300969, new Object[] { s0, s1 });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_ALREADY_OTHER_FORCE(String name) {
    return new SM_SYSTEM_MESSAGE(1300974, new Object[] { name });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_FAILED_NOT_ENOUGH_SLOT() {
    return new SM_SYSTEM_MESSAGE(1300975, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_PARTY(String s0, String s1) {
    return new SM_SYSTEM_MESSAGE(1300968, new Object[] { s0, s1 });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_HE_BECOME_OFFLINE(String s0) {
    return new SM_SYSTEM_MESSAGE(1301019, new Object[] { s0 });
  }

  public static SM_SYSTEM_MESSAGE STR_FORCE_HE_BECOME_OFFLINE_TIMEOUT(String s0) {
    return new SM_SYSTEM_MESSAGE(1300981, new Object[] { s0 });
  }

  public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_TOO_FAR_FROM_NPC() {
    return new SM_SYSTEM_MESSAGE(1300305, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATE_TOO_FAR_FROM_NPC() {
    return new SM_SYSTEM_MESSAGE(1300229, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_INCORRECT_TARGET() {
    return new SM_SYSTEM_MESSAGE(1300627, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_DISPLAY_ANNOUNCEMENT(String announcement, long unixTime, int type) {
    return new SM_SYSTEM_MESSAGE(1400019, new Object[] { announcement, Long.valueOf(unixTime), Integer.valueOf(type) });
  }

  public static SM_SYSTEM_MESSAGE LEGION_WRITE_NOTICE_DONE() {
    return new SM_SYSTEM_MESSAGE(1300277, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_MEMBER_ONLINE(String charName) {
    return new SM_SYSTEM_MESSAGE(1400133, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE NEW_MEMBER_JOINED(String charName) {
    return new SM_SYSTEM_MESSAGE(1300260, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_MEMBER_LEFT(String charName) {
    return new SM_SYSTEM_MESSAGE(900699, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_NEW_MASTER() {
    return new SM_SYSTEM_MESSAGE(900701, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SEND_INVITE_REQUEST(String charName) {
    return new SM_SYSTEM_MESSAGE(1300258, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE REJECTED_INVITE_REQUEST(String charName) {
    return new SM_SYSTEM_MESSAGE(1300259, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATE_INVALID_NAME() {
    return new SM_SYSTEM_MESSAGE(1300228, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATE_NAME_EXISTS() {
    return new SM_SYSTEM_MESSAGE(1300233, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_WRITE_INTRO_DONE() {
    return new SM_SYSTEM_MESSAGE(1300282, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_LEVEL_UP(int legionLevel) {
    return new SM_SYSTEM_MESSAGE(900700, new Object[] { Integer.valueOf(legionLevel) });
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_CANT_LEVEL_UP() {
    return new SM_SYSTEM_MESSAGE(1300316, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGED_EMBLEM() {
    return new SM_SYSTEM_MESSAGE(1390137, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATE_ALREADY_MEMBER() {
    return new SM_SYSTEM_MESSAGE(1300232, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATE_NOT_ENOUGH_KINAH() {
    return new SM_SYSTEM_MESSAGE(1300231, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATE_LAST_DAY_CHECK() {
    return new SM_SYSTEM_MESSAGE(1300234, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CREATED(String legionName) {
    return new SM_SYSTEM_MESSAGE(1300235, new Object[] { legionName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_POINT() {
    return new SM_SYSTEM_MESSAGE(1300317, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_MEMBER() {
    return new SM_SYSTEM_MESSAGE(1300318, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_KINAH() {
    return new SM_SYSTEM_MESSAGE(1300319, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_TARGET_BUSY() {
    return new SM_SYSTEM_MESSAGE(1300325, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CANT_INVITE_WHILE_DEAD() {
    return new SM_SYSTEM_MESSAGE(1300250, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CAN_NOT_INVITE_SELF() {
    return new SM_SYSTEM_MESSAGE(1300254, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_HE_IS_MY_GUILD_MEMBER(String charName) {
    return new SM_SYSTEM_MESSAGE(1300255, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_HE_IS_OTHER_GUILD_MEMBER(String charName) {
    return new SM_SYSTEM_MESSAGE(1300256, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_CAN_NOT_ADD_MEMBER_ANY_MORE() {
    return new SM_SYSTEM_MESSAGE(1300257, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_NO_USER_TO_INVITE() {
    return new SM_SYSTEM_MESSAGE(1300253, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CANT_LEAVE_BEFORE_CHANGE_MASTER() {
    return new SM_SYSTEM_MESSAGE(1300238, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CANT_KICK_YOURSELF() {
    return new SM_SYSTEM_MESSAGE(1300243, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_KICKED_BY(String charName) {
    return new SM_SYSTEM_MESSAGE(1300246, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_CANT_KICK_BRIGADE_GENERAL() {
    return new SM_SYSTEM_MESSAGE(1300249, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT() {
    return new SM_SYSTEM_MESSAGE(1300262, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_ERROR_SELF() {
    return new SM_SYSTEM_MESSAGE(1300263, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_NO_USER() {
    return new SM_SYSTEM_MESSAGE(1300264, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_ERROR_SELF() {
    return new SM_SYSTEM_MESSAGE(1300271, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_NO_SUCH_USER() {
    return new SM_SYSTEM_MESSAGE(1300270, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_SENT_OFFER_MSG_TO_HIM(String charName) {
    return new SM_SYSTEM_MESSAGE(1300330, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_SENT_CANT_OFFER_WHEN_HE_IS_QUESTION_ASKED() {
    return new SM_SYSTEM_MESSAGE(1300331, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_HE_DECLINE_YOUR_OFFER(String charName) {
    return new SM_SYSTEM_MESSAGE(1300332, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE() {
    return new SM_SYSTEM_MESSAGE(1300300, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_REQUESTED(int unixTime) {
    return new SM_SYSTEM_MESSAGE(1300303, new Object[] { Integer.valueOf(unixTime) });
  }

  public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_ALREADY_REQUESTED() {
    return new SM_SYSTEM_MESSAGE(1300304, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_WAREHOUSE_CANT_USE_WHILE_DISPERSE() {
    return new SM_SYSTEM_MESSAGE(1300333, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_CANT_DISPERSE_GUILD_STORE_ITEM_IN_WAREHOUSE() {
    return new SM_SYSTEM_MESSAGE(1390212, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_NOTIFY_LOGIN_GUILD(String charName) {
    return new SM_SYSTEM_MESSAGE(1400133, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_CANNOT_USE_ITEM_WHILE_PRIVATE_STORE = new SM_SYSTEM_MESSAGE(1300048,
      new Object[] { new DescriptionId(2800123) });

  public static SM_SYSTEM_MESSAGE STR_SKILL_RESTRICTION_FLY_ONLY = new SM_SYSTEM_MESSAGE(1300113, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_ITEM_ERROR = new SM_SYSTEM_MESSAGE(1300514, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_ITEM_CANT_USE_UNTIL_DELAY_TIME = new SM_SYSTEM_MESSAGE(1300494, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_FLYING_FORBIDDEN_HERE = new SM_SYSTEM_MESSAGE(1300960, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_AIRPORT_WHEN_FLYING = new SM_SYSTEM_MESSAGE(1300696, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_CANNOT_MOVE_TO_AIRPORT_WRONG_NPC = new SM_SYSTEM_MESSAGE(1300692, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_CANNOT_MOVE_TO_AIRPORT_NO_ROUTE = new SM_SYSTEM_MESSAGE(1300691, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE() {
    return new SM_SYSTEM_MESSAGE(1300686, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_ALREADY_REGISTER_THIS_RESURRECT_POINT() {
    return new SM_SYSTEM_MESSAGE(1300688, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_DEATH_REGISTER_RESURRECT_POINT() {
    return new SM_SYSTEM_MESSAGE(1300670, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_ATTACK_TOO_FAR_FROM_TARGET() {
    return new SM_SYSTEM_MESSAGE(1300032, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE NO_POWER_SHARD_EQUIPPED() {
    return new SM_SYSTEM_MESSAGE(1300490, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE ACTIVATE_THE_POWER_SHARD() {
    return new SM_SYSTEM_MESSAGE(1300491, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE DEACTIVATE_THE_POWER_SHARD() {
    return new SM_SYSTEM_MESSAGE(1300492, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE NO_POWER_SHARD_LEFT() {
    return new SM_SYSTEM_MESSAGE(1400075, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE ADDITIONAL_PLACES_IN_WAREHOUSE() {
    return new SM_SYSTEM_MESSAGE(1300433, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE EARNED_ABYSS_POINT(String count) {
    return new SM_SYSTEM_MESSAGE(1320000, new Object[] { count });
  }

  public static SM_SYSTEM_MESSAGE STR_SKILL_CANCELED() {
    return new SM_SYSTEM_MESSAGE(1300023, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE INVALID_TARGET() {
    return new SM_SYSTEM_MESSAGE(1300013, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SEARCH_NOT_EXIST() {
    return new SM_SYSTEM_MESSAGE(1310019, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE QUEST_ACQUIRE_ERROR_INVENTORY_ITEM(int count) {
    return new SM_SYSTEM_MESSAGE(1300594, new Object[] { Integer.valueOf(count) });
  }

  public static SM_SYSTEM_MESSAGE NOT_ENOUGH_KINAH(long kinah) {
    return new SM_SYSTEM_MESSAGE(901285, new Object[] { Long.valueOf(kinah) });
  }

  public static final SM_SYSTEM_MESSAGE MSG_FULL_INVENTORY = new SM_SYSTEM_MESSAGE(1300762, new Object[0]);

  public static final AionServerPacket CUBEEXPAND_NOT_ENOUGH_KINAH = new SM_SYSTEM_MESSAGE(1300831, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_OPTION_SUCCEED(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300462, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_OPTION_FAILED(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300463, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE STR_ENCHANT_ITEM_SUCCEED(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300455, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE STR_ENCHANT_ITEM_FAILED(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300456, new Object[] { itemDescId });
  }

  public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_ITEM_TOO_LOW_LEVEL_MUST_BE_THIS_LEVEL(int itemLevel,
      DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300372, new Object[] { Integer.valueOf(itemLevel), itemDescId });
  }

  public static SM_SYSTEM_MESSAGE STR_DELETE_CHARACTER_IN_LEGION() {
    return new SM_SYSTEM_MESSAGE(1300306, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SUMMON_ATTACKMODE(int nameId) {
    return new SM_SYSTEM_MESSAGE(1200008, new Object[] { new DescriptionId(nameId * 2 + 1) });
  }

  public static SM_SYSTEM_MESSAGE SUMMON_GUARDMODE(int nameId) {
    return new SM_SYSTEM_MESSAGE(1200009, new Object[] { new DescriptionId(nameId * 2 + 1) });
  }

  public static SM_SYSTEM_MESSAGE SUMMON_RESTMODE(int nameId) {
    return new SM_SYSTEM_MESSAGE(1200010, new Object[] { new DescriptionId(nameId * 2 + 1) });
  }

  public static SM_SYSTEM_MESSAGE SUMMON_UNSUMMON(int nameId) {
    return new SM_SYSTEM_MESSAGE(1200011, new Object[] { new DescriptionId(nameId * 2 + 1) });
  }

  public static SM_SYSTEM_MESSAGE SUMMON_DISMISSED(int nameId) {
    return new SM_SYSTEM_MESSAGE(1200006, new Object[] { new DescriptionId(nameId * 2 + 1) });
  }

  public static SM_SYSTEM_MESSAGE SUMMON_INVALID_TARGET() {
    return new SM_SYSTEM_MESSAGE(1300088, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SUMMON_ALREADY_HAVE_FOLLOWER() {
    return new SM_SYSTEM_MESSAGE(1300072, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SUMMON_UNSUMMON_BY_TOO_DISTANCE() {
    return new SM_SYSTEM_MESSAGE(1300073, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE SUMMON_CANT_ORDER_BY_TOO_DISTANCE() {
    return new SM_SYSTEM_MESSAGE(1300074, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_LOOT_NO_RIGHT() {
    return new SM_SYSTEM_MESSAGE(901338, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_LOOT_FAIL_ONLOOTING() {
    return new SM_SYSTEM_MESSAGE(1300829, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_ANOTHER_OWNER_ITEM() {
    return new SM_SYSTEM_MESSAGE(1390220, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE CRAFT_RECIPE_LEARN(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1330061, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE MSG_DONT_GET_PRODUCTION_EXP(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1390221, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_WATCH(String charName) {
    return new SM_SYSTEM_MESSAGE(1390114, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_TRADE(String charName) {
    return new SM_SYSTEM_MESSAGE(1390115, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_PARTY(String charName) {
    return new SM_SYSTEM_MESSAGE(1390116, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_FORCE(String charName) {
    return new SM_SYSTEM_MESSAGE(1390117, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_GUILD(String charName) {
    return new SM_SYSTEM_MESSAGE(1390118, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_FRIEND(String charName) {
    return new SM_SYSTEM_MESSAGE(1390119, new Object[] { charName });
  }

  public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_DUEL(String charName) {
    return new SM_SYSTEM_MESSAGE(1390120, new Object[] { charName });
  }

  public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_FAR_FROM_NPC = new SM_SYSTEM_MESSAGE(1300475,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT = new SM_SYSTEM_MESSAGE(1300476,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NO_TARGET_ITEM = new SM_SYSTEM_MESSAGE(1300477,
      new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_SKIN_CHANGABLE_ITEM(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1300478, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_SAME_EQUIP_SLOT(DescriptionId keepNameId,
      DescriptionId skinNameId) {
    return new SM_SYSTEM_MESSAGE(1300479, new Object[] { keepNameId, skinNameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(DescriptionId keepNameId,
      DescriptionId skinNameId) {
    return new SM_SYSTEM_MESSAGE(1300480, new Object[] { keepNameId, skinNameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1300481, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_CAN_NOT_REMOVE_SKIN_ITEM(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1300482, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_SUCCEED(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1300483, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_INVALID_STANCE(DescriptionId nameId) {
    return new SM_SYSTEM_MESSAGE(1300484, new Object[] { nameId });
  }

  public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_001() {
    return new SM_SYSTEM_MESSAGE(390270, true, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_002() {
    return new SM_SYSTEM_MESSAGE(390271, true, new Object[0]);
  }

  public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_003() {
    return new SM_SYSTEM_MESSAGE(390272, true, new Object[0]);
  }

  public static final SM_SYSTEM_MESSAGE STR_MOVE_PORTAL_ERROR_INVALID_RACE = new SM_SYSTEM_MESSAGE(901354,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_MSG_CANT_INSTANCE_ENTER_LEVEL = new SM_SYSTEM_MESSAGE(1400179,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_MSG_ENTER_ONLY_PARTY_DON = new SM_SYSTEM_MESSAGE(1390256, new Object[0]);

  public static final SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_NO_PROC_GIVE_ITEM = new SM_SYSTEM_MESSAGE(1300505,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_CANNOT_GIVE_PROC_TO_EQUIPPED_ITEM = new SM_SYSTEM_MESSAGE(
      1300503, new Object[0]);

  public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_ENCHANTED_TARGET_ITEM(DescriptionId itemDescId) {
    return new SM_SYSTEM_MESSAGE(1300508, new Object[] { itemDescId });
  }

  public static final SM_SYSTEM_MESSAGE STR_MSG_DICE_INVEN_ERROR = new SM_SYSTEM_MESSAGE(1390182, new Object[0]);
  public static final SM_SYSTEM_MESSAGE COMBINE_INVENTORY_IS_FULL = new SM_SYSTEM_MESSAGE(1330037, new Object[0]);

  public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY = new SM_SYSTEM_MESSAGE(1300799,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_FAR_FROM_NPC = new SM_SYSTEM_MESSAGE(1300800,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_DESTROYED = new SM_SYSTEM_MESSAGE(1300802, new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_REMOVED = new SM_SYSTEM_MESSAGE(1300803, new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_BINDSTONE_ITEM_NOT_PROPER_AREA = new SM_SYSTEM_MESSAGE(1300804,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_BINDSTONE_ITEM_WHILE_FLYING = new SM_SYSTEM_MESSAGE(1300806,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_DESTROYED = new SM_SYSTEM_MESSAGE(1390158, new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_REGISTER = new SM_SYSTEM_MESSAGE(1390159, new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_ALREADY_INSTALLED = new SM_SYSTEM_MESSAGE(1390160, new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_ALREADY_REGISTERED = new SM_SYSTEM_MESSAGE(1390161,
      new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_ATTACKED = new SM_SYSTEM_MESSAGE(1390166, new Object[0]);
  public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_FULL = new SM_SYSTEM_MESSAGE(1400247,
      new Object[0]);

  public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_MAIN_REQUIRE_HIGHER_LEVEL = new SM_SYSTEM_MESSAGE(1400288,
      new Object[0]);

  public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_NOT_AVAILABLE(int nameId) {
    return new SM_SYSTEM_MESSAGE(1400289, new Object[] { new DescriptionId(nameId) });
  }

  public static final SM_SYSTEM_MESSAGE STR_COMPOUNDED_ITEM_DECOMPOUND_SUCCESS(int nameId) {
    return new SM_SYSTEM_MESSAGE(1400335, new Object[] { new DescriptionId(nameId) });
  }

  public static final SM_SYSTEM_MESSAGE STR_COMPOUND_SUCCESS(int nameId, int nameId2) {
    return new SM_SYSTEM_MESSAGE(1400336, new Object[] { new DescriptionId(nameId), new DescriptionId(nameId2) });
  }

  public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_NOT_ENOUGH_MONEY(int nameId, int nameId2) {
    return new SM_SYSTEM_MESSAGE(1400337, new Object[] { new DescriptionId(nameId), new DescriptionId(nameId2) });
  }

  public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_DIFFERENT_TYPE = new SM_SYSTEM_MESSAGE(1400364,
      new Object[0]);
  private final int code;

  public static final SM_SYSTEM_MESSAGE STR_DECOMPOUND_ERROR_NOT_AVAILABLE(int nameId) {
    return new SM_SYSTEM_MESSAGE(1400373, new Object[] { new DescriptionId(nameId) });
  }

  private final Object[] params;

  private boolean npcShout = false;

  public SM_SYSTEM_MESSAGE(int code, Object... params) {
    this.code = code;
    this.params = params;
  }

  public SM_SYSTEM_MESSAGE(int code, boolean npcShout, Object... params) {
    this.code = code;
    this.npcShout = npcShout;
    this.params = params;
  }

  public SM_SYSTEM_MESSAGE(SystemMessageId sm, Object... params) {
    this.code = sm.getId();
    this.params = params;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (!this.npcShout) {
      writeH(buf, 19);
    } else {

      writeC(buf, 1);
      writeC(buf, 1);
    }
    writeD(buf, 0);
    writeD(buf, this.code);
    writeC(buf, this.params.length);

    for (Object param : this.params) {

      if (param instanceof DescriptionId) {

        writeH(buf, 36);
        writeD(buf, ((DescriptionId) param).getValue());
        writeH(buf, 0);
      } else {

        writeS(buf, String.valueOf(param));
      }
    }
    writeC(buf, 0);
  }
}
