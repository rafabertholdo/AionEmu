package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.ClassChangeService;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class CM_DIALOG_SELECT extends AionClientPacket {
  private int targetObjectId;
  private int dialogId;
  private int unk1;
  private int lastPage;
  private int questId;
  private static final Logger log = Logger.getLogger(CM_DIALOG_SELECT.class);

  public CM_DIALOG_SELECT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetObjectId = readD();
    this.dialogId = readH();
    this.unk1 = readH();
    this.lastPage = readH();
    this.questId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (player == null) {
      return;
    }
    if (this.targetObjectId == 0) {

      if (QuestEngine.getInstance()
          .onDialog(new QuestEnv(null, player, Integer.valueOf(this.questId), Integer.valueOf(this.dialogId)))) {
        return;
      }
      ClassChangeService.changeClassToSelection(player, this.dialogId);

      return;
    }
    AionObject object = World.getInstance().findAionObject(this.targetObjectId);

    if (object instanceof Creature) {

      Creature creature = (Creature) object;
      creature.getController().onDialogSelect(this.dialogId, player, this.questId);
    }
  }
}
