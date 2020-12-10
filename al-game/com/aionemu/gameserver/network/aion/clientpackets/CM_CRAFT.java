package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.CraftService;

public class CM_CRAFT extends AionClientPacket {
  private int unk;
  private int targetTemplateId;
  private int recipeId;
  private int targetObjId;

  public CM_CRAFT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.unk = readC();
    this.targetTemplateId = readD();
    this.recipeId = readD();
    this.targetObjId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (player == null || !player.isSpawned()) {
      return;
    }

    if (player.getController().isInShutdownProgress()) {
      return;
    }
    CraftService.startCrafting(player, this.targetTemplateId, this.recipeId, this.targetObjId);
  }
}
