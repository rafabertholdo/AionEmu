/*     */ package com.aionemu.gameserver.network.aion.clientpackets;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.InventoryDAO;
/*     */ import com.aionemu.gameserver.model.Gender;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.account.Account;
/*     */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
/*     */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CREATE_CHARACTER;
/*     */ import com.aionemu.gameserver.services.PlayerService;
/*     */ import com.aionemu.gameserver.utils.Util;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import java.sql.Timestamp;
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
/*     */ public class CM_CREATE_CHARACTER
/*     */   extends AionClientPacket
/*     */ {
/*     */   private PlayerAppearance playerAppearance;
/*     */   private PlayerCommonData playerCommonData;
/*     */   
/*     */   public CM_CREATE_CHARACTER(int opcode) {
/*  60 */     super(opcode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readImpl() {
/*  70 */     int playOk2 = readD();
/*     */     
/*  72 */     String someShit = readS();
/*     */     
/*  74 */     this.playerCommonData = new PlayerCommonData(IDFactory.getInstance().nextId());
/*  75 */     String name = Util.convertName(readS());
/*     */     
/*  77 */     this.playerCommonData.setName(name);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     byte[] shit = readB(42 - name.length() * 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     this.playerCommonData.setLevel(1);
/*  91 */     this.playerCommonData.setGender((readD() == 0) ? Gender.MALE : Gender.FEMALE);
/*  92 */     this.playerCommonData.setRace((readD() == 0) ? Race.ELYOS : Race.ASMODIANS);
/*  93 */     this.playerCommonData.setPlayerClass(PlayerClass.getPlayerClassById((byte)readD()));
/*     */     
/*  95 */     this.playerAppearance = new PlayerAppearance();
/*     */     
/*  97 */     this.playerAppearance.setVoice(readD());
/*  98 */     this.playerAppearance.setSkinRGB(readD());
/*  99 */     this.playerAppearance.setHairRGB(readD());
/* 100 */     this.playerAppearance.setEyeRGB(readD());
/*     */     
/* 102 */     this.playerAppearance.setLipRGB(readD());
/* 103 */     this.playerAppearance.setFace(readC());
/* 104 */     this.playerAppearance.setHair(readC());
/* 105 */     this.playerAppearance.setDeco(readC());
/* 106 */     this.playerAppearance.setTattoo(readC());
/*     */     
/* 108 */     readC();
/*     */     
/* 110 */     this.playerAppearance.setFaceShape(readC());
/* 111 */     this.playerAppearance.setForehead(readC());
/*     */     
/* 113 */     this.playerAppearance.setEyeHeight(readC());
/* 114 */     this.playerAppearance.setEyeSpace(readC());
/* 115 */     this.playerAppearance.setEyeWidth(readC());
/* 116 */     this.playerAppearance.setEyeSize(readC());
/* 117 */     this.playerAppearance.setEyeShape(readC());
/* 118 */     this.playerAppearance.setEyeAngle(readC());
/*     */     
/* 120 */     this.playerAppearance.setBrowHeight(readC());
/* 121 */     this.playerAppearance.setBrowAngle(readC());
/* 122 */     this.playerAppearance.setBrowShape(readC());
/*     */     
/* 124 */     this.playerAppearance.setNose(readC());
/* 125 */     this.playerAppearance.setNoseBridge(readC());
/* 126 */     this.playerAppearance.setNoseWidth(readC());
/* 127 */     this.playerAppearance.setNoseTip(readC());
/*     */     
/* 129 */     this.playerAppearance.setCheek(readC());
/* 130 */     this.playerAppearance.setLipHeight(readC());
/* 131 */     this.playerAppearance.setMouthSize(readC());
/* 132 */     this.playerAppearance.setLipSize(readC());
/* 133 */     this.playerAppearance.setSmile(readC());
/* 134 */     this.playerAppearance.setLipShape(readC());
/* 135 */     this.playerAppearance.setJawHeigh(readC());
/* 136 */     this.playerAppearance.setChinJut(readC());
/* 137 */     this.playerAppearance.setEarShape(readC());
/* 138 */     this.playerAppearance.setHeadSize(readC());
/*     */     
/* 140 */     this.playerAppearance.setNeck(readC());
/* 141 */     this.playerAppearance.setNeckLength(readC());
/*     */     
/* 143 */     this.playerAppearance.setShoulderSize(readC());
/*     */     
/* 145 */     this.playerAppearance.setTorso(readC());
/* 146 */     this.playerAppearance.setChest(readC());
/* 147 */     this.playerAppearance.setWaist(readC());
/*     */     
/* 149 */     this.playerAppearance.setHips(readC());
/* 150 */     this.playerAppearance.setArmThickness(readC());
/*     */     
/* 152 */     this.playerAppearance.setHandSize(readC());
/* 153 */     this.playerAppearance.setLegThicnkess(readC());
/*     */     
/* 155 */     this.playerAppearance.setFootSize(readC());
/* 156 */     this.playerAppearance.setFacialRate(readC());
/*     */ 
/*     */     
/* 159 */     byte unk1 = (byte)readC();
/* 160 */     this.playerAppearance.setArmLength(readC());
/* 161 */     this.playerAppearance.setLegLength(readC());
/* 162 */     this.playerAppearance.setShoulders(readC());
/*     */     
/* 164 */     byte unk2 = (byte)readC();
/* 165 */     readC();
/* 166 */     this.playerAppearance.setHeight(readF());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void runImpl() {
/* 175 */     AionConnection client = (AionConnection)getConnection();
/*     */ 
/*     */     
/* 178 */     if (!PlayerService.isValidName(this.playerCommonData.getName())) {
/*     */       
/* 180 */       client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 5));
/* 181 */       IDFactory.getInstance().releaseId(this.playerCommonData.getPlayerObjId());
/*     */       return;
/*     */     } 
/* 184 */     if (!PlayerService.isFreeName(this.playerCommonData.getName())) {
/*     */       
/* 186 */       client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 10));
/* 187 */       IDFactory.getInstance().releaseId(this.playerCommonData.getPlayerObjId());
/*     */       return;
/*     */     } 
/* 190 */     if (!this.playerCommonData.getPlayerClass().isStartingClass()) {
/*     */       
/* 192 */       client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 1));
/* 193 */       IDFactory.getInstance().releaseId(this.playerCommonData.getPlayerObjId());
/*     */       
/*     */       return;
/*     */     } 
/* 197 */     Player player = PlayerService.newPlayer(this.playerCommonData, this.playerAppearance);
/*     */     
/* 199 */     Account account = client.getAccount();
/*     */     
/* 201 */     if (!PlayerService.storeNewPlayer(player, account.getName(), account.getId())) {
/*     */       
/* 203 */       client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(null, 2));
/*     */     }
/*     */     else {
/*     */       
/* 207 */       List<Item> equipment = ((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).loadEquipment(player.getObjectId());
/* 208 */       PlayerAccountData accPlData = new PlayerAccountData(this.playerCommonData, this.playerAppearance, equipment, null);
/*     */       
/* 210 */       accPlData.setCreationDate(new Timestamp(System.currentTimeMillis()));
/* 211 */       PlayerService.storeCreationTime(player.getObjectId(), accPlData.getCreationDate());
/*     */       
/* 213 */       account.addPlayerAccountData(accPlData);
/* 214 */       client.sendPacket((AionServerPacket)new SM_CREATE_CHARACTER(accPlData, 0));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CREATE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */