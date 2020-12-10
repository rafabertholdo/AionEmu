package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.TradeListTemplate;
import com.aionemu.gameserver.model.trade.TradeList;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.PrivateStoreService;
import com.aionemu.gameserver.services.TradeService;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;
























public class CM_BUY_ITEM
  extends AionClientPacket
{
  private int sellerObjId;
  private int unk1;
  private int amount;
  private int itemId;
  private int count;
  public int unk2;
  
  public CM_BUY_ITEM(int opcode) {
    super(opcode);
  }



  
  private static final Logger log = Logger.getLogger(CM_BUY_ITEM.class);


  
  private TradeList tradeList;



  
  protected void readImpl() {
    this.sellerObjId = readD();
    this.unk1 = readH();
    this.amount = readH();
    
    this.tradeList = new TradeList();
    this.tradeList.setSellerObjId(this.sellerObjId);
    
    for (int i = 0; i < this.amount; i++) {
      
      this.itemId = readD();
      this.count = readD();
      this.unk2 = readD();

      
      if (this.count >= 1)
      {
        
        if (this.unk1 == 13 || this.unk1 == 14) {
          
          this.tradeList.addBuyItem(this.itemId, this.count);
        }
        else if (this.unk1 == 0 || this.unk1 == 1) {
          
          this.tradeList.addSellItem(this.itemId, this.count);
        } 
      }
    } 
  }

  
  protected void runImpl() {
    Player targetPlayer;
    Npc npc;
    TradeListTemplate tlist;
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    switch (this.unk1) {
      
      case 0:
        targetPlayer = (Player)World.getInstance().findAionObject(this.sellerObjId);
        PrivateStoreService.sellStoreItem(targetPlayer, player, this.tradeList);
        return;
      
      case 1:
        TradeService.performSellToShop(player, this.tradeList);
        return;
      
      case 13:
        TradeService.performBuyFromShop(player, this.tradeList);
        return;
      
      case 14:
        npc = (Npc)World.getInstance().findAionObject(this.sellerObjId);
        tlist = DataManager.TRADE_LIST_DATA.getTradeListTemplate(npc.getNpcId());
        if (tlist.isAbyss()) {
          TradeService.performBuyFromAbyssShop(player, this.tradeList);
        }
        return;
    } 
    log.info(String.format("Unhandle shop action unk1: %d", new Object[] { Integer.valueOf(this.unk1) }));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BUY_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
