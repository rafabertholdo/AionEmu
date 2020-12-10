package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PETITION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.PetitionService;

public class CM_PETITION extends AionClientPacket {
  private int action;
  private String title = "";
  private String text = "";
  private String additionalData = "";

  public CM_PETITION(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.action = readH();
    if (this.action == 2) {

      readD();
    } else {

      String data = readS();
      String[] dataArr = data.split("/", 3);
      this.title = dataArr[0];
      this.text = dataArr[1];
      this.additionalData = dataArr[2];
    }
  }

  protected void runImpl() {
    int playerObjId = ((AionConnection) getConnection()).getActivePlayer().getObjectId();
    if (this.action == 2) {
      if (PetitionService.getInstance().hasRegisteredPetition(playerObjId)) {

        int petitionId = PetitionService.getInstance().getPetition(playerObjId).getPetitionId();
        PetitionService.getInstance().deletePetition(playerObjId);
        sendPacket((AionServerPacket) new SM_SYSTEM_MESSAGE(1300552, new Object[] { Integer.valueOf(petitionId) }));
        sendPacket((AionServerPacket) new SM_SYSTEM_MESSAGE(1300553, new Object[] { Integer.valueOf(49) }));

        return;
      }
    }

    if (!PetitionService.getInstance()
        .hasRegisteredPetition(((AionConnection) getConnection()).getActivePlayer().getObjectId())) {

      Petition petition = PetitionService.getInstance().registerPetition(
          ((AionConnection) getConnection()).getActivePlayer(), this.action, this.title, this.text,
          this.additionalData);
      sendPacket((AionServerPacket) new SM_PETITION(petition));
    }
  }
}
