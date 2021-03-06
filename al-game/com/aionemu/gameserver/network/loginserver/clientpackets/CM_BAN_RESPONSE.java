package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

public class CM_BAN_RESPONSE extends LsClientPacket {
  private byte type;
  private int accountId;
  private String ip;
  private int time;
  private int adminObjId;
  private boolean result;

  public CM_BAN_RESPONSE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.type = (byte) readC();
    this.accountId = readD();
    this.ip = readS();
    this.time = readD();
    this.adminObjId = readD();
    this.result = (readC() == 1);
  }

  protected void runImpl() {
    Player admin = World.getInstance().findPlayer(this.adminObjId);

    if (admin == null) {
      return;
    }

    if (this.type == 1 || this.type == 3) {
      String message;
      if (this.result) {

        if (this.time < 0) {
          message = "Account ID " + this.accountId + " was successfully unbanned";
        } else if (this.time == 0) {
          message = "Account ID " + this.accountId + " was successfully banned";
        } else {
          message = "Account ID " + this.accountId + " was successfully banned for " + this.time + " minutes";
        }
      } else {
        message = "Error occurred while banning player's account";
      }
      PacketSendUtility.sendMessage(admin, message);
    }
    if (this.type == 2 || this.type == 3) {
      String message;
      if (this.result) {

        if (this.time < 0) {
          message = "IP mask " + this.ip + " was successfully removed from block list";
        } else if (this.time == 0) {
          message = "IP mask " + this.ip + " was successfully added to block list";
        } else {
          message = "IP mask " + this.ip + " was successfully added to block list for " + this.time + " minutes";
        }
      } else {
        message = "Error occurred while adding IP mask " + this.ip;
      }
      PacketSendUtility.sendMessage(admin, message);
    }
  }
}
