package com.aionemu.gameserver.network.factories;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionPacketHandler;
import com.aionemu.gameserver.network.aion.clientpackets.CM_BLOCK_SET_REASON;
import com.aionemu.gameserver.network.aion.clientpackets.CM_BREAK_WEAPONS;
import com.aionemu.gameserver.network.aion.clientpackets.CM_BROKER_SETTLE_ACCOUNT;
import com.aionemu.gameserver.network.aion.clientpackets.CM_BROKER_SETTLE_LIST;
import com.aionemu.gameserver.network.aion.clientpackets.CM_CHARACTER_LIST;
import com.aionemu.gameserver.network.aion.clientpackets.CM_CHAT_MESSAGE_PUBLIC;
import com.aionemu.gameserver.network.aion.clientpackets.CM_CLIENT_COMMAND_ROLL;
import com.aionemu.gameserver.network.aion.clientpackets.CM_DELETE_QUEST;
import com.aionemu.gameserver.network.aion.clientpackets.CM_DIALOG_SELECT;
import com.aionemu.gameserver.network.aion.clientpackets.CM_ENTER_WORLD;
import com.aionemu.gameserver.network.aion.clientpackets.CM_EXCHANGE_ADD_ITEM;
import com.aionemu.gameserver.network.aion.clientpackets.CM_EXCHANGE_LOCK;
import com.aionemu.gameserver.network.aion.clientpackets.CM_FRIEND_DEL;
import com.aionemu.gameserver.network.aion.clientpackets.CM_GROUP_DISTRIBUTION;
import com.aionemu.gameserver.network.aion.clientpackets.CM_GROUP_LOOT;
import com.aionemu.gameserver.network.aion.clientpackets.CM_GROUP_RESPONSE;
import com.aionemu.gameserver.network.aion.clientpackets.CM_L2AUTH_LOGIN_CHECK;
import com.aionemu.gameserver.network.aion.clientpackets.CM_MACRO_CREATE;
import com.aionemu.gameserver.network.aion.clientpackets.CM_OPEN_STATICDOOR;
import com.aionemu.gameserver.network.aion.clientpackets.CM_PLAYER_SEARCH;
import com.aionemu.gameserver.network.aion.clientpackets.CM_PRIVATE_STORE;
import com.aionemu.gameserver.network.aion.clientpackets.CM_REGISTER_BROKER_ITEM;
import com.aionemu.gameserver.network.aion.clientpackets.CM_REPLACE_ITEM;
import com.aionemu.gameserver.network.aion.clientpackets.CM_RESTORE_CHARACTER;
import com.aionemu.gameserver.network.aion.clientpackets.CM_SEND_MAIL;
import com.aionemu.gameserver.network.aion.clientpackets.CM_SET_NOTE;
import com.aionemu.gameserver.network.aion.clientpackets.CM_SHOW_BLOCKLIST;
import com.aionemu.gameserver.network.aion.clientpackets.CM_SKILL_DEACTIVATE;
import com.aionemu.gameserver.network.aion.clientpackets.CM_SUMMON_EMOTION;
import com.aionemu.gameserver.network.aion.clientpackets.CM_TIME_CHECK;
import com.aionemu.gameserver.network.aion.clientpackets.CM_TITLE_SET;

public class AionPacketHandlerFactory {
  public static final AionPacketHandlerFactory getInstance() {
    return SingletonHolder.instance;
  }

  private AionPacketHandler handler;

  private AionPacketHandlerFactory() {
    this.handler = new AionPacketHandler();
    addPacket((AionClientPacket) new CM_CRAFT(0), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CLIENT_COMMAND_LOC(1),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_RESTORE_CHARACTER(4),
        new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_START_LOOT(5), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_LOOT_ITEM(6), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MOVE_ITEM(7), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_L2AUTH_LOGIN_CHECK(8),
        new AionConnection.State[] { AionConnection.State.CONNECTED });
    addPacket((AionClientPacket) new CM_CHARACTER_LIST(9), new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_CREATE_CHARACTER(10),
        new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_DELETE_CHARACTER(11),
        new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_LEGION_UPLOAD_EMBLEM(12),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SPLIT_ITEM(16), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PLAYER_SEARCH(18), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_LEGION_UPLOAD_INFO(19),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_FRIEND_STATUS(21), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CHANGE_CHANNEL(23),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BLOCK_ADD(25), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BLOCK_DEL(26), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SHOW_BLOCKLIST(27),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CHECK_NICKNAME(28), new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_REPLACE_ITEM(29), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BLOCK_SET_REASON(30),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MAC_ADDRESS2(33), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MACRO_CREATE(34), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MACRO_DELETE(35), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DISTRIBUTION_SETTINGS(36),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MAY_LOGIN_INTO_GAME(37),
        new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_SHOW_BRAND(40), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_RECONNECT_AUTH(42), new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_GROUP_LOOT(43), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SHOW_MAP(47), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MAC_ADDRESS(48), new AionConnection.State[] { AionConnection.State.CONNECTED,
        AionConnection.State.AUTHED, AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_REPORT_PLAYER(50), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_GROUP_RESPONSE(51),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SUMMON_MOVE(52), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SUMMON_EMOTION(53),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SUMMON_ATTACK(54), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DELETE_QUEST(67), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_ITEM_REMODEL(69), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_GODSTONE_SOCKET(70),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_INVITE_TO_GROUP(76),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_ALLIANCE_GROUP_CHANGE(77),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_VIEW_PLAYER_DETAILS(79),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PLAYER_STATUS_INFO(83),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CLIENT_COMMAND_ROLL(86),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_GROUP_DISTRIBUTION(87),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PING_REQUEST(90), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DUEL_REQUEST(93), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DELETE_ITEM(95), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SHOW_FRIENDLIST(97),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_FRIEND_ADD(98), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_FRIEND_DEL(99), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SUMMON_COMMAND(100),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BROKER_LIST(102), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PRIVATE_STORE(106),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PRIVATE_STORE_NAME(107),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BROKER_SETTLE_LIST(108),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BROKER_SETTLE_ACCOUNT(109),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SEND_MAIL(111), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BROKER_REGISTERED(112),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BUY_BROKER_ITEM(113),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_REGISTER_BROKER_ITEM(114),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BROKER_CANCEL_REGISTERED(115),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DELETE_MAIL(116), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_TITLE_SET(118), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_READ_MAIL(121), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_GET_MAIL_ATTACHMENT(123),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_TELEPORT_SELECT(127),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PETITION(133), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CHAT_MESSAGE_PUBLIC(134),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CHAT_MESSAGE_WHISPER(135),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_OPEN_STATICDOOR(138),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CASTSPELL(140), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SKILL_DEACTIVATE(141),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_REMOVE_ALTERED_STATE(142),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_TARGET_SELECT(146),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_ATTACK(147), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EMOTION(150), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PING(151),
        new AionConnection.State[] { AionConnection.State.AUTHED, AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_USE_ITEM(152), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EQUIP_ITEM(153), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_FLIGHT_TELEPORT(156),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_QUESTION_RESPONSE(157),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BUY_ITEM(158), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SHOW_DIALOG(159), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_LEGION(160), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MOVE(163), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SET_NOTE(165), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_LEGION_MODIFY_EMBLEM(166),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CLOSE_DIALOG(168), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DIALOG_SELECT(169),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_LEGION_TABS(170), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EXCHANGE_ADD_KINAH(173),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EXCHANGE_LOCK(174),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EXCHANGE_OK(175), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EXCHANGE_REQUEST(178),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EXCHANGE_ADD_ITEM(179),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MANASTONE(181), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_EXCHANGE_CANCEL(184),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_PLAY_MOVIE_END(188),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_SUMMON_CASTSPELL(192),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_FUSION_WEAPONS(193),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_BREAK_WEAPONS(194),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_LEGION_SEND_EMBLEM(211),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_DISCONNECT(237), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_QUIT(238),
        new AionConnection.State[] { AionConnection.State.AUTHED, AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_MAY_QUIT(239),
        new AionConnection.State[] { AionConnection.State.AUTHED, AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_VERSION_CHECK(243),
        new AionConnection.State[] { AionConnection.State.CONNECTED });
    addPacket((AionClientPacket) new CM_LEVEL_READY(244), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_UI_SETTINGS(245), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_OBJECT_SEARCH(246),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_CUSTOM_SETTINGS(247),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_REVIVE(248), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_ENTER_WORLD(251), new AionConnection.State[] { AionConnection.State.AUTHED });
    addPacket((AionClientPacket) new CM_TIME_CHECK(253), new AionConnection.State[] { AionConnection.State.CONNECTED,
        AionConnection.State.AUTHED, AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_GATHER(254), new AionConnection.State[] { AionConnection.State.IN_GAME });
    addPacket((AionClientPacket) new CM_QUESTIONNAIRE(124),
        new AionConnection.State[] { AionConnection.State.IN_GAME });
  }

  public AionPacketHandler getPacketHandler() {
    return this.handler;
  }

  private void addPacket(AionClientPacket prototype, AionConnection.State... states) {
    this.handler.addPacketPrototype(prototype, states);
  }

  private static class SingletonHolder {
    protected static final AionPacketHandlerFactory instance = new AionPacketHandlerFactory();
  }
}
