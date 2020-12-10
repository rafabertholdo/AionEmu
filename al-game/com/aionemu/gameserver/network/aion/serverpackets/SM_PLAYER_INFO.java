/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.items.GodStone;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SM_PLAYER_INFO
/*     */   extends AionServerPacket
/*     */ {
/*     */   private final Player player;
/*     */   private boolean enemy;
/*     */   
/*     */   public SM_PLAYER_INFO(Player player, boolean enemy) {
/*  54 */     this.player = player;
/*  55 */     this.enemy = enemy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  64 */     PlayerCommonData pcd = this.player.getCommonData();
/*     */     
/*  66 */     int raceId = pcd.getRace().getRaceId();
/*  67 */     int genderId = pcd.getGender().getGenderId();
/*  68 */     PlayerAppearance playerAppearance = this.player.getPlayerAppearance();
/*     */     
/*  70 */     writeF(buf, this.player.getX());
/*  71 */     writeF(buf, this.player.getY());
/*  72 */     writeF(buf, this.player.getZ());
/*  73 */     writeD(buf, this.player.getObjectId());
/*     */ 
/*     */ 
/*     */     
/*  77 */     writeD(buf, pcd.getTemplateId());
/*     */ 
/*     */ 
/*     */     
/*  81 */     writeD(buf, (this.player.getTransformedModelId() == 0) ? pcd.getTemplateId() : this.player.getTransformedModelId());
/*     */     
/*  83 */     writeC(buf, this.enemy ? 0 : 38);
/*     */     
/*  85 */     writeC(buf, raceId);
/*  86 */     writeC(buf, pcd.getPlayerClass().getClassId());
/*  87 */     writeC(buf, genderId);
/*  88 */     writeH(buf, this.player.getState());
/*     */     
/*  90 */     byte[] unk = { 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */     
/*  92 */     writeB(buf, unk);
/*     */     
/*  94 */     writeC(buf, this.player.getHeading());
/*     */     
/*  96 */     writeS(buf, this.player.getName());
/*     */     
/*  98 */     writeD(buf, pcd.getTitleId());
/*  99 */     writeC(buf, 0);
/* 100 */     writeH(buf, this.player.getCastingSkillId());
/* 101 */     writeH(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionId() : 0);
/*     */     
/* 103 */     writeH(buf, 0);
/* 104 */     writeH(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getEmblemId() : 0);
/*     */     
/* 106 */     writeC(buf, 255);
/* 107 */     writeC(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getColor_r() : 0);
/*     */     
/* 109 */     writeC(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getColor_g() : 0);
/*     */     
/* 111 */     writeC(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionEmblem().getColor_b() : 0);
/*     */     
/* 113 */     writeS(buf, this.player.isLegionMember() ? this.player.getLegion().getLegionName() : "");
/*     */ 
/*     */     
/* 116 */     int maxHp = this.player.getLifeStats().getMaxHp();
/* 117 */     int currHp = this.player.getLifeStats().getCurrentHp();
/* 118 */     writeC(buf, 100 * currHp / maxHp);
/* 119 */     writeH(buf, pcd.getDp());
/* 120 */     writeC(buf, 0);
/*     */     
/* 122 */     List<Item> items = this.player.getEquipment().getEquippedItemsWithoutStigma();
/* 123 */     short mask = 0;
/* 124 */     for (Item item : items)
/*     */     {
/* 126 */       mask = (short)(mask | item.getEquipmentSlot());
/*     */     }
/*     */     
/* 129 */     writeH(buf, mask);
/*     */     
/* 131 */     for (Item item : items) {
/*     */       
/* 133 */       if (item.getEquipmentSlot() < 65534) {
/*     */         
/* 135 */         writeD(buf, item.getItemSkinTemplate().getTemplateId());
/* 136 */         GodStone godStone = item.getGodStone();
/* 137 */         writeD(buf, (godStone != null) ? godStone.getItemId() : 0);
/* 138 */         writeD(buf, item.getItemColor());
/* 139 */         writeH(buf, 0);
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     writeD(buf, playerAppearance.getSkinRGB());
/* 144 */     writeD(buf, playerAppearance.getHairRGB());
/* 145 */     writeD(buf, playerAppearance.getEyeRGB());
/* 146 */     writeD(buf, playerAppearance.getLipRGB());
/* 147 */     writeC(buf, playerAppearance.getFace());
/* 148 */     writeC(buf, playerAppearance.getHair());
/* 149 */     writeC(buf, playerAppearance.getDeco());
/* 150 */     writeC(buf, playerAppearance.getTattoo());
/*     */     
/* 152 */     writeC(buf, 5);
/*     */     
/* 154 */     writeC(buf, playerAppearance.getFaceShape());
/* 155 */     writeC(buf, playerAppearance.getForehead());
/*     */     
/* 157 */     writeC(buf, playerAppearance.getEyeHeight());
/* 158 */     writeC(buf, playerAppearance.getEyeSpace());
/* 159 */     writeC(buf, playerAppearance.getEyeWidth());
/* 160 */     writeC(buf, playerAppearance.getEyeSize());
/* 161 */     writeC(buf, playerAppearance.getEyeShape());
/* 162 */     writeC(buf, playerAppearance.getEyeAngle());
/*     */     
/* 164 */     writeC(buf, playerAppearance.getBrowHeight());
/* 165 */     writeC(buf, playerAppearance.getBrowAngle());
/* 166 */     writeC(buf, playerAppearance.getBrowShape());
/*     */     
/* 168 */     writeC(buf, playerAppearance.getNose());
/* 169 */     writeC(buf, playerAppearance.getNoseBridge());
/* 170 */     writeC(buf, playerAppearance.getNoseWidth());
/* 171 */     writeC(buf, playerAppearance.getNoseTip());
/*     */     
/* 173 */     writeC(buf, playerAppearance.getCheek());
/* 174 */     writeC(buf, playerAppearance.getLipHeight());
/* 175 */     writeC(buf, playerAppearance.getMouthSize());
/* 176 */     writeC(buf, playerAppearance.getLipSize());
/* 177 */     writeC(buf, playerAppearance.getSmile());
/* 178 */     writeC(buf, playerAppearance.getLipShape());
/* 179 */     writeC(buf, playerAppearance.getJawHeigh());
/* 180 */     writeC(buf, playerAppearance.getChinJut());
/* 181 */     writeC(buf, playerAppearance.getEarShape());
/* 182 */     writeC(buf, playerAppearance.getHeadSize());
/*     */ 
/*     */     
/* 185 */     writeC(buf, playerAppearance.getNeck());
/* 186 */     writeC(buf, playerAppearance.getNeckLength());
/* 187 */     writeC(buf, playerAppearance.getShoulderSize());
/*     */     
/* 189 */     writeC(buf, playerAppearance.getTorso());
/* 190 */     writeC(buf, playerAppearance.getChest());
/* 191 */     writeC(buf, playerAppearance.getWaist());
/*     */     
/* 193 */     writeC(buf, playerAppearance.getHips());
/* 194 */     writeC(buf, playerAppearance.getArmThickness());
/* 195 */     writeC(buf, playerAppearance.getHandSize());
/* 196 */     writeC(buf, playerAppearance.getLegThicnkess());
/*     */     
/* 198 */     writeC(buf, playerAppearance.getFootSize());
/* 199 */     writeC(buf, playerAppearance.getFacialRate());
/*     */     
/* 201 */     writeC(buf, 0);
/* 202 */     writeC(buf, playerAppearance.getArmLength());
/* 203 */     writeC(buf, playerAppearance.getLegLength());
/* 204 */     writeC(buf, playerAppearance.getShoulders());
/* 205 */     writeC(buf, 0);
/* 206 */     writeC(buf, 0);
/*     */     
/* 208 */     writeC(buf, playerAppearance.getVoice());
/*     */     
/* 210 */     writeF(buf, playerAppearance.getHeight());
/* 211 */     writeF(buf, 0.25F);
/* 212 */     writeF(buf, 2.0F);
/* 213 */     writeF(buf, this.player.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F);
/*     */     
/* 215 */     writeH(buf, this.player.getGameStats().getBaseStat(StatEnum.ATTACK_SPEED));
/* 216 */     writeH(buf, this.player.getGameStats().getCurrentStat(StatEnum.ATTACK_SPEED));
/* 217 */     writeC(buf, 2);
/*     */     
/* 219 */     writeS(buf, this.player.hasStore() ? this.player.getStore().getStoreMessage() : "");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     writeF(buf, 0.0F);
/* 225 */     writeF(buf, 0.0F);
/* 226 */     writeF(buf, 0.0F);
/*     */     
/* 228 */     writeF(buf, this.player.getX());
/* 229 */     writeF(buf, this.player.getY());
/* 230 */     writeF(buf, this.player.getZ());
/* 231 */     writeC(buf, 0);
/*     */     
/* 233 */     if (this.player.isUsingFlyTeleport()) {
/*     */       
/* 235 */       writeD(buf, this.player.getFlightTeleportId());
/* 236 */       writeD(buf, this.player.getFlightDistance());
/*     */     } 
/*     */     
/* 239 */     writeC(buf, this.player.getVisualState());
/* 240 */     writeS(buf, this.player.getCommonData().getNote());
/*     */     
/* 242 */     writeH(buf, this.player.getLevel());
/* 243 */     writeH(buf, this.player.getPlayerSettings().getDisplay());
/* 244 */     writeH(buf, this.player.getPlayerSettings().getDeny());
/* 245 */     writeH(buf, this.player.getAbyssRank().getRank().getId());
/* 246 */     writeH(buf, 0);
/* 247 */     writeD(buf, (this.player.getTarget() == null) ? 0 : this.player.getTarget().getObjectId());
/* 248 */     writeC(buf, 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */