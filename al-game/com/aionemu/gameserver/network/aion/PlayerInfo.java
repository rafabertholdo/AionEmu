package com.aionemu.gameserver.network.aion;

import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ItemSlot;
import java.nio.ByteBuffer;
import java.util.List;

public abstract class PlayerInfo extends AionServerPacket {
  protected void writePlayerInfo(ByteBuffer buf, PlayerAccountData accPlData) {
    PlayerCommonData pbd = accPlData.getPlayerCommonData();
    int raceId = pbd.getRace().getRaceId();
    int genderId = pbd.getGender().getGenderId();
    PlayerAppearance playerAppearance = accPlData.getAppereance();
    writeD(buf, pbd.getPlayerObjId());
    writeS(buf, pbd.getName());

    int size = 44 - pbd.getName().length() * 2 + 2;
    byte[] stupidNc = new byte[size];
    writeB(buf, stupidNc);
    writeD(buf, genderId);
    writeD(buf, raceId);
    writeD(buf, pbd.getPlayerClass().getClassId());
    writeD(buf, playerAppearance.getVoice());
    writeD(buf, playerAppearance.getSkinRGB());
    writeD(buf, playerAppearance.getHairRGB());
    writeD(buf, playerAppearance.getEyeRGB());
    writeD(buf, playerAppearance.getLipRGB());
    writeC(buf, playerAppearance.getFace());
    writeC(buf, playerAppearance.getHair());
    writeC(buf, playerAppearance.getDeco());
    writeC(buf, playerAppearance.getTattoo());
    writeC(buf, 4);
    writeC(buf, playerAppearance.getFaceShape());
    writeC(buf, playerAppearance.getForehead());
    writeC(buf, playerAppearance.getEyeHeight());
    writeC(buf, playerAppearance.getEyeSpace());
    writeC(buf, playerAppearance.getEyeWidth());
    writeC(buf, playerAppearance.getEyeSize());
    writeC(buf, playerAppearance.getEyeShape());
    writeC(buf, playerAppearance.getEyeAngle());
    writeC(buf, playerAppearance.getBrowHeight());
    writeC(buf, playerAppearance.getBrowAngle());
    writeC(buf, playerAppearance.getBrowShape());
    writeC(buf, playerAppearance.getNose());
    writeC(buf, playerAppearance.getNoseBridge());
    writeC(buf, playerAppearance.getNoseWidth());
    writeC(buf, playerAppearance.getNoseTip());
    writeC(buf, playerAppearance.getCheek());
    writeC(buf, playerAppearance.getLipHeight());
    writeC(buf, playerAppearance.getMouthSize());
    writeC(buf, playerAppearance.getLipSize());
    writeC(buf, playerAppearance.getSmile());
    writeC(buf, playerAppearance.getLipShape());
    writeC(buf, playerAppearance.getJawHeigh());
    writeC(buf, playerAppearance.getChinJut());
    writeC(buf, playerAppearance.getEarShape());
    writeC(buf, playerAppearance.getHeadSize());

    writeC(buf, playerAppearance.getNeck());
    writeC(buf, playerAppearance.getNeckLength());
    writeC(buf, playerAppearance.getShoulderSize());

    writeC(buf, playerAppearance.getTorso());
    writeC(buf, playerAppearance.getChest());
    writeC(buf, playerAppearance.getWaist());
    writeC(buf, playerAppearance.getHips());
    writeC(buf, playerAppearance.getArmThickness());
    writeC(buf, playerAppearance.getHandSize());
    writeC(buf, playerAppearance.getLegThicnkess());
    writeC(buf, playerAppearance.getFootSize());
    writeC(buf, playerAppearance.getFacialRate());
    writeC(buf, 0);
    writeC(buf, playerAppearance.getArmLength());
    writeC(buf, playerAppearance.getLegLength());
    writeC(buf, playerAppearance.getShoulders());
    writeC(buf, 0);
    writeC(buf, 0);

    writeF(buf, playerAppearance.getHeight());
    int raceSex = 100000 + raceId * 2 + genderId;
    writeD(buf, raceSex);
    writeD(buf, pbd.getPosition().getMapId());
    writeF(buf, pbd.getPosition().getX());
    writeF(buf, pbd.getPosition().getY());
    writeF(buf, pbd.getPosition().getZ());
    writeD(buf, pbd.getPosition().getHeading());
    writeD(buf, pbd.getLevel());
    writeD(buf, pbd.getTitleId());
    writeD(buf, accPlData.isLegionMember() ? accPlData.getLegion().getLegionId() : 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);

    int itemsDataSize = 0;

    List<Item> items = accPlData.getEquipment();

    for (Item item : items) {

      if (itemsDataSize < 208 && item.getItemTemplate().getItemSlot() <= ItemSlot.PANTS.getSlotIdMask()) {

        writeC(buf, 1);
        writeD(buf, item.getItemSkinTemplate().getTemplateId());
        GodStone godStone = item.getGodStone();
        writeD(buf, (godStone != null) ? godStone.getItemId() : 0);
        writeD(buf, item.getItemColor());

        itemsDataSize += 13;
      }
    }

    stupidNc = new byte[208 - itemsDataSize];
    writeB(buf, stupidNc);
    writeD(buf, accPlData.getDeletionTimeInSeconds());
    writeD(buf, 0);
  }
}
