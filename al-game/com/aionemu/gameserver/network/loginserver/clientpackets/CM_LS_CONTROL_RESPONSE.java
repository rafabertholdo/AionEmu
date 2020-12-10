package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.rates.Rates;
import com.aionemu.gameserver.world.World;























public class CM_LS_CONTROL_RESPONSE
  extends LsClientPacket
{
  private int type;
  private boolean result;
  private String playerName;
  private byte param;
  private String adminName;
  private int accountId;
  
  public CM_LS_CONTROL_RESPONSE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.type = readC();
    this.result = (readC() == 1);
    this.adminName = readS();
    this.playerName = readS();
    this.param = (byte)readC();
    this.accountId = readD();
  }





  
  protected void runImpl() {
    World world = World.getInstance();
    Player admin = world.findPlayer(Util.convertName(this.adminName));
    Player player = world.findPlayer(Util.convertName(this.playerName));
    LoginServer.getInstance().accountUpdate(this.accountId, this.param, this.type);
    switch (this.type) {
      
      case 1:
        if (!this.result) {
          
          if (admin != null)
            PacketSendUtility.sendMessage(admin, this.playerName + " has been promoted Administrator with role " + this.param); 
          if (player != null)
          {
            PacketSendUtility.sendMessage(player, "You have been promoted Administrator with role " + this.param + " by " + this.adminName);
          }
          
          break;
        } 
        if (admin != null) {
          PacketSendUtility.sendMessage(admin, " Abnormal, the operation failed! ");
        }
        break;
      case 2:
        if (!this.result) {
          
          if (admin != null)
            PacketSendUtility.sendMessage(admin, this.playerName + " has been promoted membership with level " + this.param); 
          if (player != null) {
            
            player.setRates(Rates.getRatesFor(this.param));
            PacketSendUtility.sendMessage(player, "You have been promoted membership with level " + this.param + " by " + this.adminName);
          } 
          
          break;
        } 
        if (admin != null)
          PacketSendUtility.sendMessage(admin, " Abnormal, the operation failed! "); 
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_LS_CONTROL_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
