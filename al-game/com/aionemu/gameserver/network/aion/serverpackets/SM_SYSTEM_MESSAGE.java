/*      */ package com.aionemu.gameserver.network.aion.serverpackets;
/*      */ 
/*      */ import com.aionemu.gameserver.model.DescriptionId;
/*      */ import com.aionemu.gameserver.network.aion.AionConnection;
/*      */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*      */ import com.aionemu.gameserver.network.aion.SystemMessageId;
/*      */ import java.nio.ByteBuffer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SM_SYSTEM_MESSAGE
/*      */   extends AionServerPacket
/*      */ {
/*      */   public static SM_SYSTEM_MESSAGE CURRENT_LOCATION(int worldId, float x, float y, float z) {
/*   55 */     return new SM_SYSTEM_MESSAGE(230038, new Object[] { Integer.valueOf(worldId), Float.valueOf(x), Float.valueOf(y), Float.valueOf(z) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   61 */   public static final SM_SYSTEM_MESSAGE BUDDYLIST_BUSY = new SM_SYSTEM_MESSAGE(900847, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE PLAYER_IS_OFFLINE(String playerName) {
/*   72 */     return new SM_SYSTEM_MESSAGE(1300627, new Object[] { playerName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE USE_ITEM(DescriptionId itemDescId) {
/*   80 */     return new SM_SYSTEM_MESSAGE(1300423, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE REQUEST_TRADE(String playerName) {
/*   85 */     return new SM_SYSTEM_MESSAGE(1300353, new Object[] { playerName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   91 */   public static SM_SYSTEM_MESSAGE DIE = new SM_SYSTEM_MESSAGE(1340000, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   public static SM_SYSTEM_MESSAGE REVIVE = new SM_SYSTEM_MESSAGE(1300738, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXP(String _exp) {
/*  103 */     return new SM_SYSTEM_MESSAGE(1370002, new Object[] { _exp });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(String nameA, String nameB) {
/*  108 */     return new SM_SYSTEM_MESSAGE(1350001, new Object[] { nameA, nameB });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE GATHER_SKILL_POINT_UP(String skillName, int newLevel) {
/*  117 */     return new SM_SYSTEM_MESSAGE(1330005, new Object[] { skillName, Integer.valueOf(newLevel) });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE GATHER_SUCCESS_GETEXP() {
/*  122 */     return new SM_SYSTEM_MESSAGE(1330058, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_START_1_BASIC(DescriptionId nameId) {
/*  127 */     return new SM_SYSTEM_MESSAGE(1330077, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_SUCCESS_1_BASIC(DescriptionId nameId) {
/*  132 */     return new SM_SYSTEM_MESSAGE(1330078, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_FAIL_1_BASIC(DescriptionId nameId) {
/*  137 */     return new SM_SYSTEM_MESSAGE(1330079, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_CANCEL_1_BASIC() {
/*  142 */     return new SM_SYSTEM_MESSAGE(1330080, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_INVENTORY_IS_FULL() {
/*  147 */     return new SM_SYSTEM_MESSAGE(1330081, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EXTRACT_GATHER_SUCCESS_GETEXP() {
/*  152 */     return new SM_SYSTEM_MESSAGE(1330082, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  158 */   public static final SM_SYSTEM_MESSAGE REQUEST_TRADE = new SM_SYSTEM_MESSAGE(1300353, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  163 */   public static final SM_SYSTEM_MESSAGE BUDDYLIST_LIST_FULL = new SM_SYSTEM_MESSAGE(1300887, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  168 */   public static final SM_SYSTEM_MESSAGE BUDDYLIST_NOT_IN_LIST = new SM_SYSTEM_MESSAGE(1300889, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SERVER_SHUTDOWN(int seconds) {
/*  175 */     return new SM_SYSTEM_MESSAGE(1300642, new Object[] { Integer.toString(seconds) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  181 */   public static SM_SYSTEM_MESSAGE BLOCKLIST_NO_BUDDY = new SM_SYSTEM_MESSAGE(1300891, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  186 */   public static SM_SYSTEM_MESSAGE BLOCKLIST_ALREADY_BLOCKED = new SM_SYSTEM_MESSAGE(1300894, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  191 */   public static SM_SYSTEM_MESSAGE BLOCKLIST_NOT_BLOCKED = new SM_SYSTEM_MESSAGE(1300897, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE YOU_ARE_BLOCKED_BY(String blocker) {
/*  198 */     return new SM_SYSTEM_MESSAGE(1300628, new Object[] { blocker });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_ASKED_BY(String player) {
/*  206 */     return new SM_SYSTEM_MESSAGE(1301065, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_ASKED_TO(String player) {
/*  214 */     return new SM_SYSTEM_MESSAGE(1300094, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_REJECTED_BY(String player) {
/*  222 */     return new SM_SYSTEM_MESSAGE(1300097, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_YOU_WON_AGAINST(String player) {
/*  230 */     return new SM_SYSTEM_MESSAGE(1300098, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_YOU_LOST_AGAINST(String player) {
/*  238 */     return new SM_SYSTEM_MESSAGE(1300099, new Object[] { player });
/*      */   }
/*      */   
/*  241 */   public static SM_SYSTEM_MESSAGE DUEL_START = new SM_SYSTEM_MESSAGE(1300770, new Object[0]);
/*      */   
/*  243 */   public static SM_SYSTEM_MESSAGE DUEL_END = new SM_SYSTEM_MESSAGE(1300771, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_STARTING_WITH(String player) {
/*  250 */     return new SM_SYSTEM_MESSAGE(1300777, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_REJECT_DUEL_OF(String player) {
/*  258 */     return new SM_SYSTEM_MESSAGE(1301064, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_CANCEL_DUEL_BY(String player) {
/*  266 */     return new SM_SYSTEM_MESSAGE(1300134, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_CANCEL_DUEL_WITH(String player) {
/*  274 */     return new SM_SYSTEM_MESSAGE(1300135, new Object[] { player });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DUEL_PARTNER_INVALID(String partner) {
/*  282 */     return new SM_SYSTEM_MESSAGE(1300091, new Object[] { partner });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SOUL_BOUND_ITEM_SUCCEED(DescriptionId itemDescId) {
/*  289 */     return new SM_SYSTEM_MESSAGE(1300485, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SOUL_BOUND_ITEM_CANCELED(DescriptionId itemDescId) {
/*  296 */     return new SM_SYSTEM_MESSAGE(1300487, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_DICE_RESULT_ME(int dice) {
/*  304 */     return new SM_SYSTEM_MESSAGE(1390162, new Object[] { Integer.valueOf(dice) });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_DICE_RESULT_OTHER(String player, int dice) {
/*  308 */     return new SM_SYSTEM_MESSAGE(1390163, new Object[] { player, Integer.valueOf(dice) });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_DICE_GIVEUP_ME() {
/*  312 */     return new SM_SYSTEM_MESSAGE(1390164, new Object[0]);
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_DICE_GIVEUP_OTHER(String player) {
/*  316 */     return new SM_SYSTEM_MESSAGE(1390165, new Object[] { player });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_GET_ITEM_ME(DescriptionId itemDesc) {
/*  320 */     return new SM_SYSTEM_MESSAGE(1390180, new Object[] { itemDesc });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_GET_ITEM_OTHER(String player, DescriptionId itemDesc) {
/*  324 */     return new SM_SYSTEM_MESSAGE(1390181, new Object[] { player, itemDesc });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_PAY_ACCOUNT_ME(long kinah) {
/*  328 */     return new SM_SYSTEM_MESSAGE(1390185, new Object[] { Long.valueOf(kinah) });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_PAY_ACCOUNT_OTHER(String player, long kinah) {
/*  332 */     return new SM_SYSTEM_MESSAGE(1390186, new Object[] { player, Long.valueOf(kinah) });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_PAY_DISTRIBUTE(long kinah, int count, long Kinah) {
/*  336 */     return new SM_SYSTEM_MESSAGE(1390187, new Object[] { Long.valueOf(kinah), Integer.valueOf(count), Long.valueOf(Kinah) });
/*      */   }
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE REQUEST_GROUP_INVITE(String player) {
/*  340 */     return new SM_SYSTEM_MESSAGE(1300173, new Object[] { player });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE PARTY_HE_BECOME_OFFLINE(String player) {
/*  345 */     return new SM_SYSTEM_MESSAGE(1300175, new Object[] { player });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE REJECT_GROUP_INVITE(String player) {
/*  350 */     return new SM_SYSTEM_MESSAGE(1300161, new Object[] { player });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE PLAYER_IN_ANOTHER_GROUP(String player) {
/*  355 */     return new SM_SYSTEM_MESSAGE(1300169, new Object[] { player });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE INVITED_PLAYER_OFFLINE() {
/*  360 */     return new SM_SYSTEM_MESSAGE(1300159, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE MEMBER_LEFT_GROUP(String player) {
/*  365 */     return new SM_SYSTEM_MESSAGE(1300168, new Object[] { player });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DISBAND_GROUP() {
/*  370 */     return new SM_SYSTEM_MESSAGE(1300167, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE YOU_LEFT_GROUP() {
/*  375 */     return new SM_SYSTEM_MESSAGE(1300043, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SELECTED_TARGET_DEAD() {
/*  380 */     return new SM_SYSTEM_MESSAGE(1300044, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DURING_FLYING_PATH_NOT_LEFT_GROUP() {
/*  385 */     return new SM_SYSTEM_MESSAGE(1300047, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE FULL_GROUP() {
/*  390 */     return new SM_SYSTEM_MESSAGE(1300152, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE CHANGE_GROUP_LEADER() {
/*  395 */     return new SM_SYSTEM_MESSAGE(1300155, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE ONLY_GROUP_LEADER_CAN_INVITE() {
/*  400 */     return new SM_SYSTEM_MESSAGE(1300160, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE CANNOT_INVITE_YOURSELF() {
/*  405 */     return new SM_SYSTEM_MESSAGE(1300162, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE CANNOT_INVITE_BECAUSE_YOU_DEAD() {
/*  410 */     return new SM_SYSTEM_MESSAGE(1300163, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE INVITED_ANOTHER_GROUP_MEMBER(String player) {
/*  415 */     return new SM_SYSTEM_MESSAGE(1300169, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE INVITED_YOUR_GROUP_MEMBER(String player) {
/*  420 */     return new SM_SYSTEM_MESSAGE(1300170, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_CANT_INVITE_OTHER_RACE() {
/*  425 */     return new SM_SYSTEM_MESSAGE(1300188, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEVEL_NOT_ENOUGH_FOR_SEARCH(String level) {
/*  430 */     return new SM_SYSTEM_MESSAGE(1400341, new Object[] { level });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEVEL_NOT_ENOUGH_FOR_WHISPER(String level) {
/*  435 */     return new SM_SYSTEM_MESSAGE(1310004, new Object[] { level });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SOUL_HEALED() {
/*  440 */     return new SM_SYSTEM_MESSAGE(1300674, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DONT_HAVE_RECOVERED_EXP() {
/*  445 */     return new SM_SYSTEM_MESSAGE(1300682, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_INVITED_HIS_PARTY(String name) {
/*  456 */     return new SM_SYSTEM_MESSAGE(1300189, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_CHANGE_LEADER(String name, String name2) {
/*  463 */     return new SM_SYSTEM_MESSAGE(1300986, new Object[] { name, name2 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_PROMOTE_MANAGER(String name) {
/*  470 */     return new SM_SYSTEM_MESSAGE(1300984, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_DEMOTE_MANAGER(String name) {
/*  477 */     return new SM_SYSTEM_MESSAGE(1300985, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_CAN_NOT_INVITE_SELF() {
/*  484 */     return new SM_SYSTEM_MESSAGE(1301006, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_REJECT_INVITATION(String name) {
/*  491 */     return new SM_SYSTEM_MESSAGE(1300190, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CANT_INVITE_WHEN_HE_IS_ASKED_QUESTION(String name) {
/*  498 */     return new SM_SYSTEM_MESSAGE(1300191, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OTHER_ALLIANCE(String name) {
/*  505 */     return new SM_SYSTEM_MESSAGE(1300192, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_ALREADY_MEMBER_OF_OUR_ALLIANCE(String name) {
/*  512 */     return new SM_SYSTEM_MESSAGE(1300193, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CAN_NOT_INVITE_HIM_HE_IS_NOT_PARTY_LEADER(String name) {
/*  519 */     return new SM_SYSTEM_MESSAGE(1300194, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CAN_NOT_INVITE_HIM(String name) {
/*  526 */     return new SM_SYSTEM_MESSAGE(1300195, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_CANT_ADD_NEW_MEMBER() {
/*  533 */     return new SM_SYSTEM_MESSAGE(1300196, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_ONLY_PARTY_LEADER_CAN_LEAVE_ALLIANCE() {
/*  540 */     return new SM_SYSTEM_MESSAGE(1300197, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_YOUR_PARTY_IS_NOT_ALLIANCE_MEMBER() {
/*  547 */     return new SM_SYSTEM_MESSAGE(1300198, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HIS_PARTY_LEAVE_ALLIANCE(String name) {
/*  554 */     return new SM_SYSTEM_MESSAGE(1300199, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_MY_PARTY_LEAVE_ALLIANCE() {
/*  561 */     return new SM_SYSTEM_MESSAGE(1300200, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_DISPERSED() {
/*  568 */     return new SM_SYSTEM_MESSAGE(1300201, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_LEAVED_PARTY(String name) {
/*  575 */     return new SM_SYSTEM_MESSAGE(1300202, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_LEAVED_PARTY_OFFLINE_TIMEOUT(String name) {
/*  582 */     return new SM_SYSTEM_MESSAGE(1300203, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_IS_BANISHED(String name) {
/*  589 */     return new SM_SYSTEM_MESSAGE(1300204, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_PARTY_ALLIANCE_HE_BECOME_PARTY_LEADER(String name) {
/*  596 */     return new SM_SYSTEM_MESSAGE(1300205, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_ENTERED_FORCE() {
/*  603 */     return new SM_SYSTEM_MESSAGE(1390263, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_INVITED_HIM(String name) {
/*  610 */     return new SM_SYSTEM_MESSAGE(1301017, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_HE_ENTERED_FORCE(String name) {
/*  617 */     return new SM_SYSTEM_MESSAGE(1400013, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_BAN_ME(String name) {
/*  624 */     return new SM_SYSTEM_MESSAGE(1300979, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_BAN_HIM(String name, String name2) {
/*  631 */     return new SM_SYSTEM_MESSAGE(1300980, new Object[] { name, name2 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_OFFLINE_MEMBER() {
/*  638 */     return new SM_SYSTEM_MESSAGE(1301008, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_NO_USER_TO_INVITE() {
/*  645 */     return new SM_SYSTEM_MESSAGE(1301003, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_CANT_INVITE_WHEN_DEAD() {
/*  652 */     return new SM_SYSTEM_MESSAGE(1301007, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_PARTY_HIM(String s0, String s1) {
/*  659 */     return new SM_SYSTEM_MESSAGE(1300969, new Object[] { s0, s1 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_ALREADY_OTHER_FORCE(String name) {
/*  666 */     return new SM_SYSTEM_MESSAGE(1300974, new Object[] { name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_FAILED_NOT_ENOUGH_SLOT() {
/*  673 */     return new SM_SYSTEM_MESSAGE(1300975, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_INVITE_PARTY(String s0, String s1) {
/*  680 */     return new SM_SYSTEM_MESSAGE(1300968, new Object[] { s0, s1 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_HE_BECOME_OFFLINE(String s0) {
/*  687 */     return new SM_SYSTEM_MESSAGE(1301019, new Object[] { s0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_FORCE_HE_BECOME_OFFLINE_TIMEOUT(String s0) {
/*  694 */     return new SM_SYSTEM_MESSAGE(1300981, new Object[] { s0 });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_TOO_FAR_FROM_NPC() {
/*  706 */     return new SM_SYSTEM_MESSAGE(1300305, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATE_TOO_FAR_FROM_NPC() {
/*  712 */     return new SM_SYSTEM_MESSAGE(1300229, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_INCORRECT_TARGET() {
/*  718 */     return new SM_SYSTEM_MESSAGE(1300627, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_DISPLAY_ANNOUNCEMENT(String announcement, long unixTime, int type) {
/*  724 */     return new SM_SYSTEM_MESSAGE(1400019, new Object[] { announcement, Long.valueOf(unixTime), Integer.valueOf(type) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_WRITE_NOTICE_DONE() {
/*  731 */     return new SM_SYSTEM_MESSAGE(1300277, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_MEMBER_ONLINE(String charName) {
/*  737 */     return new SM_SYSTEM_MESSAGE(1400133, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE NEW_MEMBER_JOINED(String charName) {
/*  743 */     return new SM_SYSTEM_MESSAGE(1300260, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_MEMBER_LEFT(String charName) {
/*  749 */     return new SM_SYSTEM_MESSAGE(900699, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_NEW_MASTER() {
/*  755 */     return new SM_SYSTEM_MESSAGE(900701, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SEND_INVITE_REQUEST(String charName) {
/*  762 */     return new SM_SYSTEM_MESSAGE(1300258, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE REJECTED_INVITE_REQUEST(String charName) {
/*  768 */     return new SM_SYSTEM_MESSAGE(1300259, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATE_INVALID_NAME() {
/*  775 */     return new SM_SYSTEM_MESSAGE(1300228, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATE_NAME_EXISTS() {
/*  781 */     return new SM_SYSTEM_MESSAGE(1300233, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_WRITE_INTRO_DONE() {
/*  787 */     return new SM_SYSTEM_MESSAGE(1300282, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_LEVEL_UP(int legionLevel) {
/*  794 */     return new SM_SYSTEM_MESSAGE(900700, new Object[] { Integer.valueOf(legionLevel) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_CANT_LEVEL_UP() {
/*  800 */     return new SM_SYSTEM_MESSAGE(1300316, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGED_EMBLEM() {
/*  805 */     return new SM_SYSTEM_MESSAGE(1390137, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATE_ALREADY_MEMBER() {
/*  812 */     return new SM_SYSTEM_MESSAGE(1300232, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATE_NOT_ENOUGH_KINAH() {
/*  818 */     return new SM_SYSTEM_MESSAGE(1300231, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATE_LAST_DAY_CHECK() {
/*  824 */     return new SM_SYSTEM_MESSAGE(1300234, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CREATED(String legionName) {
/*  830 */     return new SM_SYSTEM_MESSAGE(1300235, new Object[] { legionName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_POINT() {
/*  837 */     return new SM_SYSTEM_MESSAGE(1300317, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_MEMBER() {
/*  843 */     return new SM_SYSTEM_MESSAGE(1300318, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_LEVEL_NOT_ENOUGH_KINAH() {
/*  849 */     return new SM_SYSTEM_MESSAGE(1300319, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_TARGET_BUSY() {
/*  856 */     return new SM_SYSTEM_MESSAGE(1300325, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CANT_INVITE_WHILE_DEAD() {
/*  862 */     return new SM_SYSTEM_MESSAGE(1300250, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CAN_NOT_INVITE_SELF() {
/*  868 */     return new SM_SYSTEM_MESSAGE(1300254, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_HE_IS_MY_GUILD_MEMBER(String charName) {
/*  874 */     return new SM_SYSTEM_MESSAGE(1300255, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_HE_IS_OTHER_GUILD_MEMBER(String charName) {
/*  880 */     return new SM_SYSTEM_MESSAGE(1300256, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CAN_NOT_ADD_MEMBER_ANY_MORE() {
/*  886 */     return new SM_SYSTEM_MESSAGE(1300257, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_NO_USER_TO_INVITE() {
/*  892 */     return new SM_SYSTEM_MESSAGE(1300253, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CANT_LEAVE_BEFORE_CHANGE_MASTER() {
/*  899 */     return new SM_SYSTEM_MESSAGE(1300238, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CANT_KICK_YOURSELF() {
/*  906 */     return new SM_SYSTEM_MESSAGE(1300243, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_KICKED_BY(String charName) {
/*  912 */     return new SM_SYSTEM_MESSAGE(1300246, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CANT_KICK_BRIGADE_GENERAL() {
/*  918 */     return new SM_SYSTEM_MESSAGE(1300249, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_DONT_HAVE_RIGHT() {
/*  925 */     return new SM_SYSTEM_MESSAGE(1300262, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_ERROR_SELF() {
/*  931 */     return new SM_SYSTEM_MESSAGE(1300263, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MEMBER_RANK_NO_USER() {
/*  937 */     return new SM_SYSTEM_MESSAGE(1300264, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_ERROR_SELF() {
/*  944 */     return new SM_SYSTEM_MESSAGE(1300271, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_NO_SUCH_USER() {
/*  950 */     return new SM_SYSTEM_MESSAGE(1300270, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_SENT_OFFER_MSG_TO_HIM(String charName) {
/*  956 */     return new SM_SYSTEM_MESSAGE(1300330, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_SENT_CANT_OFFER_WHEN_HE_IS_QUESTION_ASKED() {
/*  962 */     return new SM_SYSTEM_MESSAGE(1300331, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_CHANGE_MASTER_HE_DECLINE_YOUR_OFFER(String charName) {
/*  968 */     return new SM_SYSTEM_MESSAGE(1300332, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_ONLY_MASTER_CAN_DISPERSE() {
/*  975 */     return new SM_SYSTEM_MESSAGE(1300300, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_REQUESTED(int unixTime) {
/*  981 */     return new SM_SYSTEM_MESSAGE(1300303, new Object[] { Integer.valueOf(unixTime) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_ALREADY_REQUESTED() {
/*  987 */     return new SM_SYSTEM_MESSAGE(1300304, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_WAREHOUSE_CANT_USE_WHILE_DISPERSE() {
/*  993 */     return new SM_SYSTEM_MESSAGE(1300333, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE LEGION_DISPERSE_CANT_DISPERSE_GUILD_STORE_ITEM_IN_WAREHOUSE() {
/*  999 */     return new SM_SYSTEM_MESSAGE(1390212, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_NOTIFY_LOGIN_GUILD(String charName) {
/* 1007 */     return new SM_SYSTEM_MESSAGE(1400133, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1013 */   public static SM_SYSTEM_MESSAGE STR_MSG_CANNOT_USE_ITEM_WHILE_PRIVATE_STORE = new SM_SYSTEM_MESSAGE(1300048, new Object[] { new DescriptionId(2800123) });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1018 */   public static SM_SYSTEM_MESSAGE STR_SKILL_RESTRICTION_FLY_ONLY = new SM_SYSTEM_MESSAGE(1300113, new Object[0]);
/*      */   
/* 1020 */   public static SM_SYSTEM_MESSAGE STR_ITEM_ERROR = new SM_SYSTEM_MESSAGE(1300514, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1025 */   public static SM_SYSTEM_MESSAGE STR_ITEM_CANT_USE_UNTIL_DELAY_TIME = new SM_SYSTEM_MESSAGE(1300494, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1030 */   public static SM_SYSTEM_MESSAGE STR_FLYING_FORBIDDEN_HERE = new SM_SYSTEM_MESSAGE(1300960, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1035 */   public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_AIRPORT_WHEN_FLYING = new SM_SYSTEM_MESSAGE(1300696, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1040 */   public static SM_SYSTEM_MESSAGE STR_CANNOT_MOVE_TO_AIRPORT_WRONG_NPC = new SM_SYSTEM_MESSAGE(1300692, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1045 */   public static SM_SYSTEM_MESSAGE STR_CANNOT_MOVE_TO_AIRPORT_NO_ROUTE = new SM_SYSTEM_MESSAGE(1300691, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE() {
/* 1052 */     return new SM_SYSTEM_MESSAGE(1300686, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_ALREADY_REGISTER_THIS_RESURRECT_POINT() {
/* 1057 */     return new SM_SYSTEM_MESSAGE(1300688, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_DEATH_REGISTER_RESURRECT_POINT() {
/* 1062 */     return new SM_SYSTEM_MESSAGE(1300670, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_ATTACK_TOO_FAR_FROM_TARGET() {
/* 1067 */     return new SM_SYSTEM_MESSAGE(1300032, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE NO_POWER_SHARD_EQUIPPED() {
/* 1072 */     return new SM_SYSTEM_MESSAGE(1300490, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE ACTIVATE_THE_POWER_SHARD() {
/* 1077 */     return new SM_SYSTEM_MESSAGE(1300491, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE DEACTIVATE_THE_POWER_SHARD() {
/* 1082 */     return new SM_SYSTEM_MESSAGE(1300492, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE NO_POWER_SHARD_LEFT() {
/* 1087 */     return new SM_SYSTEM_MESSAGE(1400075, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE ADDITIONAL_PLACES_IN_WAREHOUSE() {
/* 1092 */     return new SM_SYSTEM_MESSAGE(1300433, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE EARNED_ABYSS_POINT(String count) {
/* 1097 */     return new SM_SYSTEM_MESSAGE(1320000, new Object[] { count });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_SKILL_CANCELED() {
/* 1102 */     return new SM_SYSTEM_MESSAGE(1300023, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE INVALID_TARGET() {
/* 1107 */     return new SM_SYSTEM_MESSAGE(1300013, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SEARCH_NOT_EXIST() {
/* 1112 */     return new SM_SYSTEM_MESSAGE(1310019, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE QUEST_ACQUIRE_ERROR_INVENTORY_ITEM(int count) {
/* 1117 */     return new SM_SYSTEM_MESSAGE(1300594, new Object[] { Integer.valueOf(count) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE NOT_ENOUGH_KINAH(long kinah) {
/* 1125 */     return new SM_SYSTEM_MESSAGE(901285, new Object[] { Long.valueOf(kinah) });
/*      */   }
/*      */   
/* 1128 */   public static final SM_SYSTEM_MESSAGE MSG_FULL_INVENTORY = new SM_SYSTEM_MESSAGE(1300762, new Object[0]);
/*      */   
/* 1130 */   public static final AionServerPacket CUBEEXPAND_NOT_ENOUGH_KINAH = new SM_SYSTEM_MESSAGE(1300831, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_OPTION_SUCCEED(DescriptionId itemDescId) {
/* 1137 */     return new SM_SYSTEM_MESSAGE(1300462, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_OPTION_FAILED(DescriptionId itemDescId) {
/* 1142 */     return new SM_SYSTEM_MESSAGE(1300463, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_ENCHANT_ITEM_SUCCEED(DescriptionId itemDescId) {
/* 1150 */     return new SM_SYSTEM_MESSAGE(1300455, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_ENCHANT_ITEM_FAILED(DescriptionId itemDescId) {
/* 1155 */     return new SM_SYSTEM_MESSAGE(1300456, new Object[] { itemDescId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CANNOT_USE_ITEM_TOO_LOW_LEVEL_MUST_BE_THIS_LEVEL(int itemLevel, DescriptionId itemDescId) {
/* 1164 */     return new SM_SYSTEM_MESSAGE(1300372, new Object[] { Integer.valueOf(itemLevel), itemDescId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_DELETE_CHARACTER_IN_LEGION() {
/* 1172 */     return new SM_SYSTEM_MESSAGE(1300306, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_ATTACKMODE(int nameId) {
/* 1180 */     return new SM_SYSTEM_MESSAGE(1200008, new Object[] { new DescriptionId(nameId * 2 + 1) });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_GUARDMODE(int nameId) {
/* 1185 */     return new SM_SYSTEM_MESSAGE(1200009, new Object[] { new DescriptionId(nameId * 2 + 1) });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_RESTMODE(int nameId) {
/* 1190 */     return new SM_SYSTEM_MESSAGE(1200010, new Object[] { new DescriptionId(nameId * 2 + 1) });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_UNSUMMON(int nameId) {
/* 1195 */     return new SM_SYSTEM_MESSAGE(1200011, new Object[] { new DescriptionId(nameId * 2 + 1) });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_DISMISSED(int nameId) {
/* 1200 */     return new SM_SYSTEM_MESSAGE(1200006, new Object[] { new DescriptionId(nameId * 2 + 1) });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_INVALID_TARGET() {
/* 1205 */     return new SM_SYSTEM_MESSAGE(1300088, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_ALREADY_HAVE_FOLLOWER() {
/* 1210 */     return new SM_SYSTEM_MESSAGE(1300072, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_UNSUMMON_BY_TOO_DISTANCE() {
/* 1215 */     return new SM_SYSTEM_MESSAGE(1300073, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE SUMMON_CANT_ORDER_BY_TOO_DISTANCE() {
/* 1220 */     return new SM_SYSTEM_MESSAGE(1300074, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_LOOT_NO_RIGHT() {
/* 1229 */     return new SM_SYSTEM_MESSAGE(901338, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_LOOT_FAIL_ONLOOTING() {
/* 1235 */     return new SM_SYSTEM_MESSAGE(1300829, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_LOOT_ANOTHER_OWNER_ITEM() {
/* 1241 */     return new SM_SYSTEM_MESSAGE(1390220, new Object[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE CRAFT_RECIPE_LEARN(DescriptionId nameId) {
/* 1246 */     return new SM_SYSTEM_MESSAGE(1330061, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE MSG_DONT_GET_PRODUCTION_EXP(DescriptionId nameId) {
/* 1251 */     return new SM_SYSTEM_MESSAGE(1390221, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_WATCH(String charName) {
/* 1259 */     return new SM_SYSTEM_MESSAGE(1390114, new Object[] { charName });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_TRADE(String charName) {
/* 1264 */     return new SM_SYSTEM_MESSAGE(1390115, new Object[] { charName });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_PARTY(String charName) {
/* 1269 */     return new SM_SYSTEM_MESSAGE(1390116, new Object[] { charName });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_FORCE(String charName) {
/* 1274 */     return new SM_SYSTEM_MESSAGE(1390117, new Object[] { charName });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_INVITE_GUILD(String charName) {
/* 1279 */     return new SM_SYSTEM_MESSAGE(1390118, new Object[] { charName });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_FRIEND(String charName) {
/* 1284 */     return new SM_SYSTEM_MESSAGE(1390119, new Object[] { charName });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_MSG_REJECTED_DUEL(String charName) {
/* 1289 */     return new SM_SYSTEM_MESSAGE(1390120, new Object[] { charName });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1295 */   public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_FAR_FROM_NPC = new SM_SYSTEM_MESSAGE(1300475, new Object[0]);
/* 1296 */   public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT = new SM_SYSTEM_MESSAGE(1300476, new Object[0]);
/* 1297 */   public static final SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NO_TARGET_ITEM = new SM_SYSTEM_MESSAGE(1300477, new Object[0]);
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_SKIN_CHANGABLE_ITEM(DescriptionId nameId) {
/* 1301 */     return new SM_SYSTEM_MESSAGE(1300478, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_SAME_EQUIP_SLOT(DescriptionId keepNameId, DescriptionId skinNameId) {
/* 1306 */     return new SM_SYSTEM_MESSAGE(1300479, new Object[] { keepNameId, skinNameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(DescriptionId keepNameId, DescriptionId skinNameId) {
/* 1311 */     return new SM_SYSTEM_MESSAGE(1300480, new Object[] { keepNameId, skinNameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(DescriptionId nameId) {
/* 1316 */     return new SM_SYSTEM_MESSAGE(1300481, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_CAN_NOT_REMOVE_SKIN_ITEM(DescriptionId nameId) {
/* 1321 */     return new SM_SYSTEM_MESSAGE(1300482, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_SUCCEED(DescriptionId nameId) {
/* 1326 */     return new SM_SYSTEM_MESSAGE(1300483, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHANGE_ITEM_SKIN_INVALID_STANCE(DescriptionId nameId) {
/* 1331 */     return new SM_SYSTEM_MESSAGE(1300484, new Object[] { nameId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_001() {
/* 1340 */     return new SM_SYSTEM_MESSAGE(390270, true, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_002() {
/* 1346 */     return new SM_SYSTEM_MESSAGE(390271, true, new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_CHAT_FARMER_003() {
/* 1352 */     return new SM_SYSTEM_MESSAGE(390272, true, new Object[0]);
/*      */   }
/*      */   
/* 1355 */   public static final SM_SYSTEM_MESSAGE STR_MOVE_PORTAL_ERROR_INVALID_RACE = new SM_SYSTEM_MESSAGE(901354, new Object[0]);
/* 1356 */   public static final SM_SYSTEM_MESSAGE STR_MSG_CANT_INSTANCE_ENTER_LEVEL = new SM_SYSTEM_MESSAGE(1400179, new Object[0]);
/* 1357 */   public static final SM_SYSTEM_MESSAGE STR_MSG_ENTER_ONLY_PARTY_DON = new SM_SYSTEM_MESSAGE(1390256, new Object[0]);
/*      */   
/* 1359 */   public static final SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_NO_PROC_GIVE_ITEM = new SM_SYSTEM_MESSAGE(1300505, new Object[0]);
/* 1360 */   public static final SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_CANNOT_GIVE_PROC_TO_EQUIPPED_ITEM = new SM_SYSTEM_MESSAGE(1300503, new Object[0]);
/*      */   
/*      */   public static SM_SYSTEM_MESSAGE STR_GIVE_ITEM_PROC_ENCHANTED_TARGET_ITEM(DescriptionId itemDescId) {
/* 1363 */     return new SM_SYSTEM_MESSAGE(1300508, new Object[] { itemDescId });
/*      */   }
/*      */   
/* 1366 */   public static final SM_SYSTEM_MESSAGE STR_MSG_DICE_INVEN_ERROR = new SM_SYSTEM_MESSAGE(1390182, new Object[0]);
/* 1367 */   public static final SM_SYSTEM_MESSAGE COMBINE_INVENTORY_IS_FULL = new SM_SYSTEM_MESSAGE(1330037, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1374 */   public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY = new SM_SYSTEM_MESSAGE(1300799, new Object[0]);
/* 1375 */   public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_FAR_FROM_NPC = new SM_SYSTEM_MESSAGE(1300800, new Object[0]);
/* 1376 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_DESTROYED = new SM_SYSTEM_MESSAGE(1300802, new Object[0]);
/* 1377 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_REMOVED = new SM_SYSTEM_MESSAGE(1300803, new Object[0]);
/* 1378 */   public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_BINDSTONE_ITEM_NOT_PROPER_AREA = new SM_SYSTEM_MESSAGE(1300804, new Object[0]);
/* 1379 */   public static final SM_SYSTEM_MESSAGE STR_CANNOT_USE_BINDSTONE_ITEM_WHILE_FLYING = new SM_SYSTEM_MESSAGE(1300806, new Object[0]);
/* 1380 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_DESTROYED = new SM_SYSTEM_MESSAGE(1390158, new Object[0]);
/* 1381 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_REGISTER = new SM_SYSTEM_MESSAGE(1390159, new Object[0]);
/* 1382 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_ALREADY_INSTALLED = new SM_SYSTEM_MESSAGE(1390160, new Object[0]);
/* 1383 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_ALREADY_REGISTERED = new SM_SYSTEM_MESSAGE(1390161, new Object[0]);
/* 1384 */   public static final SM_SYSTEM_MESSAGE STR_BINDSTONE_IS_ATTACKED = new SM_SYSTEM_MESSAGE(1390166, new Object[0]);
/* 1385 */   public static final SM_SYSTEM_MESSAGE STR_CANNOT_REGISTER_BINDSTONE_FULL = new SM_SYSTEM_MESSAGE(1400247, new Object[0]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1391 */   public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_MAIN_REQUIRE_HIGHER_LEVEL = new SM_SYSTEM_MESSAGE(1400288, new Object[0]);
/*      */   
/*      */   public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_NOT_AVAILABLE(int nameId) {
/* 1394 */     return new SM_SYSTEM_MESSAGE(1400289, new Object[] { new DescriptionId(nameId) });
/*      */   }
/*      */   
/*      */   public static final SM_SYSTEM_MESSAGE STR_COMPOUNDED_ITEM_DECOMPOUND_SUCCESS(int nameId) {
/* 1398 */     return new SM_SYSTEM_MESSAGE(1400335, new Object[] { new DescriptionId(nameId) });
/*      */   }
/*      */   
/*      */   public static final SM_SYSTEM_MESSAGE STR_COMPOUND_SUCCESS(int nameId, int nameId2) {
/* 1402 */     return new SM_SYSTEM_MESSAGE(1400336, new Object[] { new DescriptionId(nameId), new DescriptionId(nameId2) });
/*      */   }
/*      */   
/*      */   public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_NOT_ENOUGH_MONEY(int nameId, int nameId2) {
/* 1406 */     return new SM_SYSTEM_MESSAGE(1400337, new Object[] { new DescriptionId(nameId), new DescriptionId(nameId2) });
/*      */   }
/* 1408 */   public static final SM_SYSTEM_MESSAGE STR_COMPOUND_ERROR_DIFFERENT_TYPE = new SM_SYSTEM_MESSAGE(1400364, new Object[0]); private final int code;
/*      */   
/*      */   public static final SM_SYSTEM_MESSAGE STR_DECOMPOUND_ERROR_NOT_AVAILABLE(int nameId) {
/* 1411 */     return new SM_SYSTEM_MESSAGE(1400373, new Object[] { new DescriptionId(nameId) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Object[] params;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean npcShout = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SM_SYSTEM_MESSAGE(int code, Object... params) {
/* 1427 */     this.code = code;
/* 1428 */     this.params = params;
/*      */   }
/*      */ 
/*      */   
/*      */   public SM_SYSTEM_MESSAGE(int code, boolean npcShout, Object... params) {
/* 1433 */     this.code = code;
/* 1434 */     this.npcShout = npcShout;
/* 1435 */     this.params = params;
/*      */   }
/*      */ 
/*      */   
/*      */   public SM_SYSTEM_MESSAGE(SystemMessageId sm, Object... params) {
/* 1440 */     this.code = sm.getId();
/* 1441 */     this.params = params;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 1450 */     if (!this.npcShout) {
/* 1451 */       writeH(buf, 19);
/*      */     } else {
/*      */       
/* 1454 */       writeC(buf, 1);
/* 1455 */       writeC(buf, 1);
/*      */     } 
/* 1457 */     writeD(buf, 0);
/* 1458 */     writeD(buf, this.code);
/* 1459 */     writeC(buf, this.params.length);
/*      */     
/* 1461 */     for (Object param : this.params) {
/*      */       
/* 1463 */       if (param instanceof DescriptionId) {
/*      */         
/* 1465 */         writeH(buf, 36);
/* 1466 */         writeD(buf, ((DescriptionId)param).getValue());
/* 1467 */         writeH(buf, 0);
/*      */       } else {
/*      */         
/* 1470 */         writeS(buf, String.valueOf(param));
/*      */       } 
/* 1472 */     }  writeC(buf, 0);
/*      */   }
/*      */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SYSTEM_MESSAGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */