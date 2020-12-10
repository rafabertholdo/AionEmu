package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CREATE_CHARACTER;
import com.aionemu.gameserver.services.PlayerService;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import java.sql.Timestamp;
import java.util.List;






























public class CM_CREATE_CHARACTER
  extends AionClientPacket
{
  private PlayerAppearance playerAppearance;
  private PlayerCommonData playerCommonData;
  
  public CM_CREATE_CHARACTER(int opcode) {
    super(opcode);
  }






  
  protected void readImpl() {
    int playOk2 = readD();
    
    String someShit = readS();
    
    this.playerCommonData = new PlayerCommonData(IDFactory.getInstance().nextId());
    String name = Util.convertName(readS());
    
    this.playerCommonData.setName(name);



    
    byte[] shit = readB(42 - name.length() * 2);






    
    this.playerCommonData.setLevel(1);
    this.playerCommonData.setGender((readD() == 0) ? Gender.MALE : Gender.FEMALE);
    this.playerCommonData.setRace((readD() == 0) ? Race.ELYOS : Race.ASMODIANS);
    this.playerCommonData.setPlayerClass(PlayerClass.getPlayerClassById((byte)readD()));
    
    this.playerAppearance = new PlayerAppearance();
    
    this.playerAppearance.setVoice(readD());
    this.playerAppearance.setSkinRGB(readD());
    this.playerAppearance.setHairRGB(readD());
    this.playerAppearance.setEyeRGB(readD());
    
    this.playerAppearance.setLipRGB(readD());
    this.playerAppearance.setFace(readC());
    this.playerAppearance.setHair(readC());
    this.playerAppearance.setDeco(readC());
    this.playerAppearance.setTattoo(readC());
    
    readC();
    
    this.playerAppearance.setFaceShape(readC());
    this.playerAppearance.setForehead(readC());
    
    this.playerAppearance.setEyeHeight(readC());
    this.playerAppearance.setEyeSpace(readC());
    this.playerAppearance.setEyeWidth(readC());
    this.playerAppearance.setEyeSize(readC());
    this.playerAppearance.setEyeShape(readC());
    this.playerAppearance.setEyeAngle(readC());
    
    this.playerAppearance.setBrowHeight(readC());
    this.playerAppearance.setBrowAngle(readC());
    this.playerAppearance.setBrowShape(readC());
    
    this.playerAppearance.setNose(readC());
    this.playerAppearance.setNoseBridge(readC());
    this.playerAppearance.setNoseWidth(readC());
    this.playerAppearance.setNoseTip(readC());
    
    this.playerAppearance.setCheek(readC());
    this.playerAppearance.setLipHeight(readC());
    this.playerAppearance.setMouthSize(readC());
    this.playerAppearance.setLipSize(readC());
    this.playerAppearance.setSmile(readC());
    this.playerAppearance.setLipShape(readC());
    this.playerAppearance.setJawHeigh(readC());
    this.playerAppearance.setChinJut(readC());
    this.playerAppearance.setEarShape(readC());
    this.playerAppearance.setHeadSize(readC());
    
    this.playerAppearance.setNeck(readC());
    this.playerAppearance.setNeckLength(readC());
    
    this.playerAppearance.setShoulderSize(readC());
    
    this.playerAppearance.setTorso(readC());
    this.playerAppearance.setChest(readC());
    this.playerAppearance.setWaist(readC());
    
    this.playerAppearance.setHips(readC());
    this.playerAppearance.setArmThickness(readC());
    
    this.playerAppearance.setHandSize(readC());
    this.playerAppearance.setLegThicnkess(readC());
    
    this.playerAppearance.setFootSize(readC());
    this.playerAppearance.setFacialRate(readC());

    
    byte unk1 = (byte)readC();
    this.playerAppearance.setArmLength(readC());
    this.playerAppearance.setLegLength(readC());
    this.playerAppearance.setShoulders(readC());
    
    byte unk2 = (byte)readC();
    readC();
    this.playerAppearance.setHeight(readF());
  }





  
  protected void runImpl() {
    AionConnection client = (AionConnection)getConnection();

    
    if (!PlayerService.isValidName(this.playerCommonData.getName())) {
      
      client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 5));
      IDFactory.getInstance().releaseId(this.playerCommonData.getPlayerObjId());
      return;
    } 
    if (!PlayerService.isFreeName(this.playerCommonData.getName())) {
      
      client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 10));
      IDFactory.getInstance().releaseId(this.playerCommonData.getPlayerObjId());
      return;
    } 
    if (!this.playerCommonData.getPlayerClass().isStartingClass()) {
      
      client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 1));
      IDFactory.getInstance().releaseId(this.playerCommonData.getPlayerObjId());
      
      return;
    } 
    Player player = PlayerService.newPlayer(this.playerCommonData, this.playerAppearance);
    
    Account account = client.getAccount();
    
    if (!PlayerService.storeNewPlayer(player, account.getName(), account.getId())) {
      
      client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 2));
    }
    else {
      
      List<Item> equipment = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadEquipment(player.getObjectId());
      PlayerAccountData accPlData = new PlayerAccountData(this.playerCommonData, this.playerAppearance, equipment, null);
      
      accPlData.setCreationDate(new Timestamp(System.currentTimeMillis()));
      PlayerService.storeCreationTime(player.getObjectId(), accPlData.getCreationDate());
      
      account.addPlayerAccountData(accPlData);
      client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(accPlData, 0));
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CREATE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
