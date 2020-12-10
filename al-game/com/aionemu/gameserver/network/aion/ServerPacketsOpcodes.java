/*     */ package com.aionemu.gameserver.network.aion;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CHAT_INIT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CREATE_CHARACTER;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_PACKET;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DP_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_ADD_KINAH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_ADD_MEMBER;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_SEND_EMBLEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_SELF_INTRO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_UPDATE_TITLE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LOGIN_QUEUE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MANTRA_EFFECT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_NICKNAME_CHECK_RESPONSE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PING_RESPONSE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SEARCH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_DP;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_MP;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_SELECTED;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ServerPacketsOpcodes {
/*  37 */   private static Map<Class<? extends AionServerPacket>, Integer> opcodes = new HashMap<Class<? extends AionServerPacket>, Integer>();
/*     */ 
/*     */   
/*     */   static {
/*  41 */     Set<Integer> idSet = new HashSet<Integer>();
/*     */     
/*  43 */     addPacketOpcode((Class)SM_STATUPDATE_MP.class, 0, idSet);
/*  44 */     addPacketOpcode((Class)SM_STATUPDATE_HP.class, 1, idSet);
/*  45 */     addPacketOpcode((Class)SM_CHAT_INIT.class, 2, idSet);
/*  46 */     addPacketOpcode((Class)SM_CHANNEL_INFO.class, 3, idSet);
/*  47 */     addPacketOpcode((Class)SM_MACRO_RESULT.class, 4, idSet);
/*  48 */     addPacketOpcode((Class)SM_MACRO_LIST.class, 5, idSet);
/*  49 */     addPacketOpcode((Class)SM_NICKNAME_CHECK_RESPONSE.class, 7, idSet);
/*  50 */     addPacketOpcode((Class)SM_RIFT_ANNOUNCE.class, 8, idSet);
/*  51 */     addPacketOpcode((Class)SM_SET_BIND_POINT.class, 9, idSet);
/*  52 */     addPacketOpcode((Class)SM_ABYSS_RANK.class, 11, idSet);
/*  53 */     addPacketOpcode((Class)SM_FRIEND_UPDATE.class, 12, idSet);
/*  54 */     addPacketOpcode((Class)SM_PETITION.class, 13, idSet);
/*  55 */     addPacketOpcode((Class)SM_RECIPE_DELETE.class, 14, idSet);
/*  56 */     addPacketOpcode((Class)SM_LEARN_RECIPE.class, 15, idSet);
/*  57 */     addPacketOpcode((Class)SM_FLY_TIME.class, 16, idSet);
/*  58 */     addPacketOpcode((Class)SM_DELETE.class, 18, idSet);
/*  59 */     addPacketOpcode((Class)SM_PLAYER_MOVE.class, 19, idSet);
/*  60 */     addPacketOpcode((Class)SM_MESSAGE.class, 20, idSet);
/*  61 */     addPacketOpcode((Class)SM_LOGIN_QUEUE.class, 21, idSet);
/*  62 */     addPacketOpcode((Class)SM_INVENTORY_INFO.class, 22, idSet);
/*  63 */     addPacketOpcode((Class)SM_SYSTEM_MESSAGE.class, 23, idSet);
/*  64 */     addPacketOpcode((Class)SM_DELETE_ITEM.class, 24, idSet);
/*  65 */     addPacketOpcode((Class)SM_ADD_ITEMS.class, 25, idSet);
/*  66 */     addPacketOpcode((Class)SM_UI_SETTINGS.class, 26, idSet);
/*  67 */     addPacketOpcode((Class)SM_UPDATE_ITEM.class, 27, idSet);
/*  68 */     addPacketOpcode((Class)SM_PLAYER_INFO.class, 28, idSet);
/*  69 */     addPacketOpcode((Class)SM_GATHER_STATUS.class, 30, idSet);
/*  70 */     addPacketOpcode((Class)SM_CASTSPELL.class, 31, idSet);
/*  71 */     addPacketOpcode((Class)SM_UPDATE_PLAYER_APPEARANCE.class, 32, idSet);
/*  72 */     addPacketOpcode((Class)SM_GATHER_UPDATE.class, 33, idSet);
/*  73 */     addPacketOpcode((Class)SM_STATUPDATE_DP.class, 34, idSet);
/*  74 */     addPacketOpcode((Class)SM_ATTACK_STATUS.class, 35, idSet);
/*  75 */     addPacketOpcode((Class)SM_STATUPDATE_EXP.class, 36, idSet);
/*  76 */     addPacketOpcode((Class)SM_DP_INFO.class, 37, idSet);
/*  77 */     addPacketOpcode((Class)SM_LEGION_TABS.class, 40, idSet);
/*  78 */     addPacketOpcode((Class)SM_LEGION_UPDATE_NICKNAME.class, 41, idSet);
/*  79 */     addPacketOpcode((Class)SM_NPC_INFO.class, 42, idSet);
/*  80 */     addPacketOpcode((Class)SM_ENTER_WORLD_CHECK.class, 43, idSet);
/*  81 */     addPacketOpcode((Class)SM_PLAYER_SPAWN.class, 45, idSet);
/*  82 */     addPacketOpcode((Class)SM_GATHERABLE_INFO.class, 47, idSet);
/*  83 */     addPacketOpcode((Class)SM_TELEPORT_LOC.class, 48, idSet);
/*  84 */     addPacketOpcode((Class)SM_ATTACK.class, 50, idSet);
/*  85 */     addPacketOpcode((Class)SM_MOVE.class, 53, idSet);
/*  86 */     addPacketOpcode((Class)SM_TRANSFORM.class, 54, idSet);
/*  87 */     addPacketOpcode((Class)SM_DIALOG_WINDOW.class, 56, idSet);
/*  88 */     addPacketOpcode((Class)SM_SELL_ITEM.class, 58, idSet);
/*  89 */     addPacketOpcode((Class)SM_VIEW_PLAYER_DETAILS.class, 63, idSet);
/*  90 */     addPacketOpcode((Class)SM_PLAYER_STATE.class, 64, idSet);
/*  91 */     addPacketOpcode((Class)SM_WEATHER.class, 65, idSet);
/*  92 */     addPacketOpcode((Class)SM_GAME_TIME.class, 66, idSet);
/*  93 */     addPacketOpcode((Class)SM_EMOTION.class, 67, idSet);
/*  94 */     addPacketOpcode((Class)SM_LOOKATOBJECT.class, 68, idSet);
/*  95 */     addPacketOpcode((Class)SM_TIME_CHECK.class, 69, idSet);
/*  96 */     addPacketOpcode((Class)SM_SKILL_CANCEL.class, 70, idSet);
/*  97 */     addPacketOpcode((Class)SM_TARGET_SELECTED.class, 71, idSet);
/*  98 */     addPacketOpcode((Class)SM_SKILL_LIST.class, 72, idSet);
/*  99 */     addPacketOpcode((Class)SM_CASTSPELL_END.class, 73, idSet);
/* 100 */     addPacketOpcode((Class)SM_SKILL_ACTIVATION.class, 74, idSet);
/* 101 */     addPacketOpcode((Class)SM_SKILL_REMOVE.class, 75, idSet);
/* 102 */     addPacketOpcode((Class)SM_ABNORMAL_EFFECT.class, 78, idSet);
/* 103 */     addPacketOpcode((Class)SM_ABNORMAL_STATE.class, 79, idSet);
/* 104 */     addPacketOpcode((Class)SM_QUESTION_WINDOW.class, 80, idSet);
/* 105 */     addPacketOpcode((Class)SM_SKILL_COOLDOWN.class, 81, idSet);
/* 106 */     addPacketOpcode((Class)SM_INFLUENCE_RATIO.class, 83, idSet);
/* 107 */     addPacketOpcode((Class)SM_NAME_CHANGE.class, 84, idSet);
/* 108 */     addPacketOpcode((Class)SM_GROUP_INFO.class, 86, idSet);
/* 109 */     addPacketOpcode((Class)SM_SHOW_NPC_ON_MAP.class, 87, idSet);
/* 110 */     addPacketOpcode((Class)SM_GROUP_MEMBER_INFO.class, 89, idSet);
/* 111 */     addPacketOpcode((Class)SM_QUIT_RESPONSE.class, 94, idSet);
/* 112 */     addPacketOpcode((Class)SM_LEVEL_UPDATE.class, 98, idSet);
/* 113 */     addPacketOpcode((Class)SM_KEY.class, 100, idSet);
/* 114 */     addPacketOpcode((Class)SM_EXCHANGE_REQUEST.class, 102, idSet);
/* 115 */     addPacketOpcode((Class)SM_SUMMON_PANEL_REMOVE.class, 103, idSet);
/* 116 */     addPacketOpcode((Class)SM_EXCHANGE_ADD_ITEM.class, 105, idSet);
/* 117 */     addPacketOpcode((Class)SM_EXCHANGE_CONFIRMATION.class, 106, idSet);
/* 118 */     addPacketOpcode((Class)SM_EXCHANGE_ADD_KINAH.class, 107, idSet);
/* 119 */     addPacketOpcode((Class)SM_EMOTION_LIST.class, 109, idSet);
/* 120 */     addPacketOpcode((Class)SM_TARGET_UPDATE.class, 111, idSet);
/* 121 */     addPacketOpcode((Class)SM_LEGION_UPDATE_SELF_INTRO.class, 117, idSet);
/* 122 */     addPacketOpcode((Class)SM_RIFT_STATUS.class, 118, idSet);
/* 123 */     addPacketOpcode((Class)SM_QUEST_ACCEPTED.class, 120, idSet);
/* 124 */     addPacketOpcode((Class)SM_QUEST_LIST.class, 121, idSet);
/* 125 */     addPacketOpcode((Class)SM_PING_RESPONSE.class, 124, idSet);
/* 126 */     addPacketOpcode((Class)SM_NEARBY_QUESTS.class, 125, idSet);
/* 127 */     addPacketOpcode((Class)SM_CUBE_UPDATE.class, 126, idSet);
/* 128 */     addPacketOpcode((Class)SM_FRIEND_LIST.class, 128, idSet);
/* 129 */     addPacketOpcode((Class)SM_UPDATE_NOTE.class, 132, idSet);
/* 130 */     addPacketOpcode((Class)SM_ITEM_COOLDOWN.class, 133, idSet);
/* 131 */     addPacketOpcode((Class)SM_PLAY_MOVIE.class, 135, idSet);
/* 132 */     addPacketOpcode((Class)SM_LEGION_INFO.class, 138, idSet);
/* 133 */     addPacketOpcode((Class)SM_LEGION_LEAVE_MEMBER.class, 140, idSet);
/* 134 */     addPacketOpcode((Class)SM_LEGION_ADD_MEMBER.class, 141, idSet);
/* 135 */     addPacketOpcode((Class)SM_LEGION_UPDATE_TITLE.class, 142, idSet);
/* 136 */     addPacketOpcode((Class)SM_LEGION_UPDATE_MEMBER.class, 143, idSet);
/* 137 */     addPacketOpcode((Class)SM_BROKER_REGISTRATION_SERVICE.class, 147, idSet);
/* 138 */     addPacketOpcode((Class)SM_BROKER_SETTLED_LIST.class, 149, idSet);
/* 139 */     addPacketOpcode((Class)SM_SUMMON_OWNER_REMOVE.class, 150, idSet);
/* 140 */     addPacketOpcode((Class)SM_SUMMON_PANEL.class, 151, idSet);
/* 141 */     addPacketOpcode((Class)SM_SUMMON_UPDATE.class, 153, idSet);
/* 142 */     addPacketOpcode((Class)SM_LEGION_EDIT.class, 154, idSet);
/* 143 */     addPacketOpcode((Class)SM_LEGION_MEMBERLIST.class, 155, idSet);
/* 144 */     addPacketOpcode((Class)SM_SUMMON_USESKILL.class, 158, idSet);
/* 145 */     addPacketOpcode((Class)SM_MAIL_SERVICE.class, 159, idSet);
/* 146 */     addPacketOpcode((Class)SM_PRIVATE_STORE.class, 162, idSet);
/* 147 */     addPacketOpcode((Class)SM_ABYSS_RANK_UPDATE.class, 164, idSet);
/* 148 */     addPacketOpcode((Class)SM_GROUP_LOOT.class, 165, idSet);
/* 149 */     addPacketOpcode((Class)SM_MAY_LOGIN_INTO_GAME.class, 167, idSet);
/* 150 */     addPacketOpcode((Class)SM_PONG.class, 170, idSet);
/* 151 */     addPacketOpcode((Class)SM_PLAYER_ID.class, 171, idSet);
/* 152 */     addPacketOpcode((Class)SM_KISK_UPDATE.class, 172, idSet);
/* 153 */     addPacketOpcode((Class)SM_BROKER_ITEMS.class, 174, idSet);
/* 154 */     addPacketOpcode((Class)SM_PRIVATE_STORE_NAME.class, 175, idSet);
/* 155 */     addPacketOpcode((Class)SM_BROKER_REGISTERED_LIST.class, 177, idSet);
/* 156 */     addPacketOpcode((Class)SM_ASCENSION_MORPH.class, 178, idSet);
/* 157 */     addPacketOpcode((Class)SM_CRAFT_UPDATE.class, 179, idSet);
/* 158 */     addPacketOpcode((Class)SM_CUSTOM_SETTINGS.class, 180, idSet);
/* 159 */     addPacketOpcode((Class)SM_ITEM_USAGE_ANIMATION.class, 181, idSet);
/* 160 */     addPacketOpcode((Class)SM_DUEL.class, 183, idSet);
/* 161 */     addPacketOpcode((Class)SM_RESURRECT.class, 190, idSet);
/* 162 */     addPacketOpcode((Class)SM_DIE.class, 191, idSet);
/* 163 */     addPacketOpcode((Class)SM_TELEPORT_MAP.class, 192, idSet);
/* 164 */     addPacketOpcode((Class)SM_FORCED_MOVE.class, 193, idSet);
/* 165 */     addPacketOpcode((Class)SM_WAREHOUSE_INFO.class, 196, idSet);
/* 166 */     addPacketOpcode((Class)SM_DELETE_WAREHOUSE_ITEM.class, 198, idSet);
/* 167 */     addPacketOpcode((Class)SM_WAREHOUSE_UPDATE.class, 199, idSet);
/* 168 */     addPacketOpcode((Class)SM_UPDATE_WAREHOUSE_ITEM.class, 201, idSet);
/* 169 */     addPacketOpcode((Class)SM_TITLE_LIST.class, 204, idSet);
/* 170 */     addPacketOpcode((Class)SM_TITLE_SET.class, 207, idSet);
/* 171 */     addPacketOpcode((Class)SM_CRAFT_ANIMATION.class, 208, idSet);
/* 172 */     addPacketOpcode((Class)SM_TITLE_UPDATE.class, 209, idSet);
/* 173 */     addPacketOpcode((Class)SM_LEGION_SEND_EMBLEM.class, 211, idSet);
/* 174 */     addPacketOpcode((Class)SM_LEGION_UPDATE_EMBLEM.class, 213, idSet);
/* 175 */     addPacketOpcode((Class)SM_FRIEND_RESPONSE.class, 218, idSet);
/* 176 */     addPacketOpcode((Class)SM_BLOCK_LIST.class, 220, idSet);
/* 177 */     addPacketOpcode((Class)SM_BLOCK_RESPONSE.class, 221, idSet);
/* 178 */     addPacketOpcode((Class)SM_FRIEND_NOTIFY.class, 223, idSet);
/* 179 */     addPacketOpcode((Class)SM_USE_OBJECT.class, 227, idSet);
/* 180 */     addPacketOpcode((Class)SM_CHARACTER_LIST.class, 228, idSet);
/* 181 */     addPacketOpcode((Class)SM_L2AUTH_LOGIN_CHECK.class, 229, idSet);
/* 182 */     addPacketOpcode((Class)SM_DELETE_CHARACTER.class, 230, idSet);
/* 183 */     addPacketOpcode((Class)SM_CREATE_CHARACTER.class, 231, idSet);
/* 184 */     addPacketOpcode((Class)SM_TARGET_IMMOBILIZE.class, 232, idSet);
/* 185 */     addPacketOpcode((Class)SM_RESTORE_CHARACTER.class, 233, idSet);
/* 186 */     addPacketOpcode((Class)SM_LOOT_ITEMLIST.class, 234, idSet);
/* 187 */     addPacketOpcode((Class)SM_LOOT_STATUS.class, 235, idSet);
/* 188 */     addPacketOpcode((Class)SM_MANTRA_EFFECT.class, 236, idSet);
/* 189 */     addPacketOpcode((Class)SM_RECIPE_LIST.class, 237, idSet);
/* 190 */     addPacketOpcode((Class)SM_SIEGE_LOCATION_INFO.class, 239, idSet);
/* 191 */     addPacketOpcode((Class)SM_PLAYER_SEARCH.class, 241, idSet);
/* 192 */     addPacketOpcode((Class)SM_ALLIANCE_MEMBER_INFO.class, 242, idSet);
/* 193 */     addPacketOpcode((Class)SM_ALLIANCE_INFO.class, 243, idSet);
/* 194 */     addPacketOpcode((Class)SM_LEAVE_GROUP_MEMBER.class, 245, idSet);
/* 195 */     addPacketOpcode((Class)SM_ALLIANCE_READY_CHECK.class, 246, idSet);
/* 196 */     addPacketOpcode((Class)SM_SHOW_BRAND.class, 247, idSet);
/* 197 */     addPacketOpcode((Class)SM_PRICES.class, 248, idSet);
/* 198 */     addPacketOpcode((Class)SM_TRADELIST.class, 251, idSet);
/* 199 */     addPacketOpcode((Class)SM_VERSION_CHECK.class, 252, idSet);
/* 200 */     addPacketOpcode((Class)SM_RECONNECT_KEY.class, 253, idSet);
/* 201 */     addPacketOpcode((Class)SM_STATS_INFO.class, 255, idSet);
/* 202 */     addPacketOpcode((Class)SM_QUESTIONNAIRE.class, 189, idSet);
/* 203 */     addPacketOpcode((Class)SM_CUSTOM_PACKET.class, 99999, idSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getOpcode(Class<? extends AionServerPacket> packetClass) {
/* 215 */     Integer opcode = opcodes.get(packetClass);
/* 216 */     if (opcode == null) {
/* 217 */       throw new IllegalArgumentException("There is no opcode for " + packetClass + " defined.");
/*     */     }
/* 219 */     return opcode.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addPacketOpcode(Class<? extends AionServerPacket> packetClass, int opcode, Set<Integer> idSet) {
/* 224 */     if (opcode < 0) {
/*     */       return;
/*     */     }
/* 227 */     if (idSet.contains(Integer.valueOf(opcode))) {
/* 228 */       throw new IllegalArgumentException(String.format("There already exists another packet with id 0x%02X", new Object[] { Integer.valueOf(opcode) }));
/*     */     }
/*     */     
/* 231 */     idSet.add(Integer.valueOf(opcode));
/* 232 */     opcodes.put(packetClass, Integer.valueOf(opcode));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\ServerPacketsOpcodes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */