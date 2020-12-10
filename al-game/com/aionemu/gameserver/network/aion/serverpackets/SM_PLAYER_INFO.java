package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.List;


































public class SM_PLAYER_INFO
  extends AionServerPacket
{
  private final Player player;
  private boolean enemy;
  
  public SM_PLAYER_INFO(Player player, boolean enemy) {
    this.player = player;
    this.enemy = enemy;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    PlayerCommonData pcd = this.player.getCommonData();
    
    int raceId = pcd.getRace().getRaceId();
    int genderId = pcd.getGender().getGenderId();
    PlayerAppearance playerAppearance = this.player.getPlayerAppearance();
    
    writeF(buf, this.player.getX());
    writeF(buf, this.player.getY());
    writeF(buf, this.player.getZ());
    writeD(buf, this.player.getObjectId());


    
    writeD(buf, pcd.getTemplateId());


    
    writeD(buf, (this.player.getTransformedModelId() == 0) ? pcd.getTemplateId() : this.player.getTransformedModelId());
    
    writeC(buf, this.enemy ? 0 : 38);
    
    writeC(buf, raceId);
    writeC(buf, pcd.getPlayerClass().getClassId());
    writeC(buf, genderId);
    writeH(buf, this.player.getState());
    
    byte[] unk = { 0, 0, 0, 0, 0, 0, 0, 0 };
    
    writeB(buf, unk);
    
    writeC(buf, this.player.getHeading());
    
    writeS(buf, this.player.getName());
    
    writeD(buf, pcd.getTitleId());
    writeC(buf, 0);
    writeH(buf, this.player.getCastingSkillId());
    writeH(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionId() : 0);
    
    writeH(buf, 0);
    writeH(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getEmblemId() : 0);
    
    writeC(buf, 255);
    writeC(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getColor_r() : 0);
    
    writeC(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getColor_g() : 0);
    
    writeC(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getColor_b() : 0);
    
    writeS(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionName() : "");

    
    int maxHp = this.player.getLifeStats().getMaxHp();
    int currHp = this.player.getLifeStats().getCurrentHp();
    writeC(buf, 100 * currHp / maxHp);
    writeH(buf, pcd.getDp());
    writeC(buf, 0);
    
    List<Item> items = this.player.getEquipment().getEquippedItemsWithoutStigma();
    short mask = 0;
    for (Item item : items)
    {
      mask = (short)(mask | item.getEquipmentSlot());
    }
    
    writeH(buf, mask);
    
    for (Item item : items) {
      
      if (item.getEquipmentSlot() < 65534) {
        
        writeD(buf, item.getItemSkinTemplate().getTemplateId());
        GodStone godStone = item.getGodStone();
        writeD(buf, (godStone != null) ? godStone.getItemId() : 0);
        writeD(buf, item.getItemColor());
        writeH(buf, 0);
      } 
    } 
    
    writeD(buf, playerAppearance.getSkinRGB());
    writeD(buf, playerAppearance.getHairRGB());
    writeD(buf, playerAppearance.getEyeRGB());
    writeD(buf, playerAppearance.getLipRGB());
    writeC(buf, playerAppearance.getFace());
    writeC(buf, playerAppearance.getHair());
    writeC(buf, playerAppearance.getDeco());
    writeC(buf, playerAppearance.getTattoo());
    
    writeC(buf, 5);
    
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
    
    writeC(buf, playerAppearance.getVoice());
    
    writeF(buf, playerAppearance.getHeight());
    writeF(buf, 0.25F);
    writeF(buf, 2.0F);
    writeF(buf, this.player.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F);
    
    writeH(buf, this.player.getGameStats().getBaseStat(StatEnum.ATTACK_SPEED));
    writeH(buf, this.player.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED));
    writeC(buf, 2);
    
    writeS(buf, this.player.hasStore() ? this.player.getStore().getStoreMessage() : "");



    
    writeF(buf, 0.0F);
    writeF(buf, 0.0F);
    writeF(buf, 0.0F);
    
    writeF(buf, this.player.getX());
    writeF(buf, this.player.getY());
    writeF(buf, this.player.getZ());
    writeC(buf, 0);
    
    if (this.player.isUsingFlyTeleport()) {
      
      writeD(buf, this.player.getFlightTeleportId());
      writeD(buf, this.player.getFlightDistance());
    } 
    
    writeC(buf, this.player.getVisualState());
    writeS(buf, this.player.getCommonData().getNote());
    
    writeH(buf, this.player.getLevel());
    writeH(buf, this.player.getPlayerSettings().getDisplay());
    writeH(buf, this.player.getPlayerSettings().getDeny());
    writeH(buf, this.player.getAbyssRank().getRank().getId());
    writeH(buf, 0);
    writeD(buf, (this.player.getTarget() == null) ? 0 : this.player.getTarget().getObjectId());
    writeC(buf, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
