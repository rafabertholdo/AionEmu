/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.model.items.GodStone;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
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
/*     */ public abstract class PlayerInfo
/*     */   extends AionServerPacket
/*     */ {
/*     */   protected void writePlayerInfo(ByteBuffer buf, PlayerAccountData accPlData) {
/*  44 */     PlayerCommonData pbd = accPlData.getPlayerCommonData();
/*  45 */     int raceId = pbd.getRace().getRaceId();
/*  46 */     int genderId = pbd.getGender().getGenderId();
/*  47 */     PlayerAppearance playerAppearance = accPlData.getAppereance();
/*  48 */     writeD(buf, pbd.getPlayerObjId());
/*  49 */     writeS(buf, pbd.getName());
/*     */ 
/*     */ 
/*     */     
/*  53 */     int size = 44 - pbd.getName().length() * 2 + 2;
/*  54 */     byte[] stupidNc = new byte[size];
/*  55 */     writeB(buf, stupidNc);
/*  56 */     writeD(buf, genderId);
/*  57 */     writeD(buf, raceId);
/*  58 */     writeD(buf, pbd.getPlayerClass().getClassId());
/*  59 */     writeD(buf, playerAppearance.getVoice());
/*  60 */     writeD(buf, playerAppearance.getSkinRGB());
/*  61 */     writeD(buf, playerAppearance.getHairRGB());
/*  62 */     writeD(buf, playerAppearance.getEyeRGB());
/*  63 */     writeD(buf, playerAppearance.getLipRGB());
/*  64 */     writeC(buf, playerAppearance.getFace());
/*  65 */     writeC(buf, playerAppearance.getHair());
/*  66 */     writeC(buf, playerAppearance.getDeco());
/*  67 */     writeC(buf, playerAppearance.getTattoo());
/*  68 */     writeC(buf, 4);
/*  69 */     writeC(buf, playerAppearance.getFaceShape());
/*  70 */     writeC(buf, playerAppearance.getForehead());
/*  71 */     writeC(buf, playerAppearance.getEyeHeight());
/*  72 */     writeC(buf, playerAppearance.getEyeSpace());
/*  73 */     writeC(buf, playerAppearance.getEyeWidth());
/*  74 */     writeC(buf, playerAppearance.getEyeSize());
/*  75 */     writeC(buf, playerAppearance.getEyeShape());
/*  76 */     writeC(buf, playerAppearance.getEyeAngle());
/*  77 */     writeC(buf, playerAppearance.getBrowHeight());
/*  78 */     writeC(buf, playerAppearance.getBrowAngle());
/*  79 */     writeC(buf, playerAppearance.getBrowShape());
/*  80 */     writeC(buf, playerAppearance.getNose());
/*  81 */     writeC(buf, playerAppearance.getNoseBridge());
/*  82 */     writeC(buf, playerAppearance.getNoseWidth());
/*  83 */     writeC(buf, playerAppearance.getNoseTip());
/*  84 */     writeC(buf, playerAppearance.getCheek());
/*  85 */     writeC(buf, playerAppearance.getLipHeight());
/*  86 */     writeC(buf, playerAppearance.getMouthSize());
/*  87 */     writeC(buf, playerAppearance.getLipSize());
/*  88 */     writeC(buf, playerAppearance.getSmile());
/*  89 */     writeC(buf, playerAppearance.getLipShape());
/*  90 */     writeC(buf, playerAppearance.getJawHeigh());
/*  91 */     writeC(buf, playerAppearance.getChinJut());
/*  92 */     writeC(buf, playerAppearance.getEarShape());
/*  93 */     writeC(buf, playerAppearance.getHeadSize());
/*     */ 
/*     */     
/*  96 */     writeC(buf, playerAppearance.getNeck());
/*  97 */     writeC(buf, playerAppearance.getNeckLength());
/*  98 */     writeC(buf, playerAppearance.getShoulderSize());
/*     */     
/* 100 */     writeC(buf, playerAppearance.getTorso());
/* 101 */     writeC(buf, playerAppearance.getChest());
/* 102 */     writeC(buf, playerAppearance.getWaist());
/* 103 */     writeC(buf, playerAppearance.getHips());
/* 104 */     writeC(buf, playerAppearance.getArmThickness());
/* 105 */     writeC(buf, playerAppearance.getHandSize());
/* 106 */     writeC(buf, playerAppearance.getLegThicnkess());
/* 107 */     writeC(buf, playerAppearance.getFootSize());
/* 108 */     writeC(buf, playerAppearance.getFacialRate());
/* 109 */     writeC(buf, 0);
/* 110 */     writeC(buf, playerAppearance.getArmLength());
/* 111 */     writeC(buf, playerAppearance.getLegLength());
/* 112 */     writeC(buf, playerAppearance.getShoulders());
/* 113 */     writeC(buf, 0);
/* 114 */     writeC(buf, 0);
/*     */     
/* 116 */     writeF(buf, playerAppearance.getHeight());
/* 117 */     int raceSex = 100000 + raceId * 2 + genderId;
/* 118 */     writeD(buf, raceSex);
/* 119 */     writeD(buf, pbd.getPosition().getMapId());
/* 120 */     writeF(buf, pbd.getPosition().getX());
/* 121 */     writeF(buf, pbd.getPosition().getY());
/* 122 */     writeF(buf, pbd.getPosition().getZ());
/* 123 */     writeD(buf, pbd.getPosition().getHeading());
/* 124 */     writeD(buf, pbd.getLevel());
/* 125 */     writeD(buf, pbd.getTitleId());
/* 126 */     writeD(buf, accPlData.isLegionMember() ? accPlData.getLegion().getLegionId() : 0);
/* 127 */     writeD(buf, 0);
/* 128 */     writeD(buf, 0);
/* 129 */     writeD(buf, 0);
/* 130 */     writeD(buf, 0);
/* 131 */     writeD(buf, 0);
/* 132 */     writeD(buf, 0);
/* 133 */     writeD(buf, 0);
/* 134 */     writeD(buf, 0);
/* 135 */     writeD(buf, 0);
/* 136 */     writeD(buf, 0);
/* 137 */     writeD(buf, 0);
/* 138 */     writeD(buf, 0);
/* 139 */     writeD(buf, 0);
/* 140 */     writeD(buf, 0);
/* 141 */     writeD(buf, 0);
/* 142 */     writeD(buf, 0);
/* 143 */     writeD(buf, 0);
/* 144 */     writeD(buf, 0);
/*     */     
/* 146 */     int itemsDataSize = 0;
/*     */     
/* 148 */     List<Item> items = accPlData.getEquipment();
/*     */     
/* 150 */     for (Item item : items) {
/*     */       
/* 152 */       if (itemsDataSize < 208 && item.getItemTemplate().getItemSlot() <= ItemSlot.PANTS.getSlotIdMask()) {
/*     */         
/* 154 */         writeC(buf, 1);
/* 155 */         writeD(buf, item.getItemSkinTemplate().getTemplateId());
/* 156 */         GodStone godStone = item.getGodStone();
/* 157 */         writeD(buf, (godStone != null) ? godStone.getItemId() : 0);
/* 158 */         writeD(buf, item.getItemColor());
/*     */         
/* 160 */         itemsDataSize += 13;
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     stupidNc = new byte[208 - itemsDataSize];
/* 165 */     writeB(buf, stupidNc);
/* 166 */     writeD(buf, accPlData.getDeletionTimeInSeconds());
/* 167 */     writeD(buf, 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\PlayerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */