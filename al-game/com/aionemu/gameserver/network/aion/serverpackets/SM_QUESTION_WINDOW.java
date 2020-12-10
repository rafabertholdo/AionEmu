package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_QUESTION_WINDOW extends AionServerPacket {
  public static final int STR_BUDDYLIST_ADD_BUDDY_REQUETS = 900841;
  public static final int STR_EXCHANGE_DO_YOU_ACCEPT_EXCHANGE = 90001;
  public static final int STR_EXCHANGE_HE_REJECTED_EXCHANGE = 1300354;
  public static final int STR_DUEL_DO_YOU_CONFIRM_DUEL = 50030;
  public static final int STR_DUEL_DO_YOU_ACCEPT_DUEL = 50028;
  public static final int STR_SOUL_HEALING = 160011;
  public static final int STR_BIND_TO_LOCATION = 160012;
  public static final int STR_REQUEST_GROUP_INVITE = 60000;
  public static final int STR_REQUEST_ALLIANCE_INVITE = 70004;
  public static final int STR_WAREHOUSE_EXPAND_WARNING = 900686;
  public static final int STR_USE_RIFT = 160019;
  public static final int STR_LEGION_INVITE = 80001;
  public static final int STR_LEGION_DISBAND = 80008;
  public static final int STR_LEGION_DISBAND_CANCEL = 80009;
  public static final int STR_LEGION_CHANGE_MASTER = 80011;
  public static final int STR_CRAFT_ADDSKILL_CONFIRM = 900852;
  public static final int STR_BIND_TO_KISK = 160018;
  public static final int STR_SOUL_BOUND_ITEM_DO_YOU_WANT_SOUL_BOUND = 95006;
  public static final int STR_ASK_GROUP_GATE_DO_YOU_ACCEPT_MOVE = 160014;
  private int code;
  private int senderId;
  private Object[] params;

  public SM_QUESTION_WINDOW(int code, int senderId, Object... params) {
    this.code = code;
    this.senderId = senderId;
    this.params = params;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.code);

    for (Object param : this.params) {

      if (param instanceof DescriptionId) {

        writeH(buf, 36);
        writeD(buf, ((DescriptionId) param).getValue());
        writeH(buf, 0);
      } else {

        writeS(buf, String.valueOf(param));
      }
    }
    writeD(buf, 0);
    writeH(buf, 0);
    writeC(buf, 1);
    writeD(buf, this.senderId);
    writeD(buf, 6);
  }
}
