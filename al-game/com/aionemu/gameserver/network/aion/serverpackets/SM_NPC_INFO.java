/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.NpcType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.items.ItemSlot;
/*     */ import com.aionemu.gameserver.model.items.NpcEquippedGear;
/*     */ import com.aionemu.gameserver.model.templates.NpcTemplate;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
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
/*     */ public class SM_NPC_INFO
/*     */   extends AionServerPacket
/*     */ {
/*     */   private Creature npc;
/*     */   private NpcTemplate npcTemplate;
/*     */   private int npcId;
/*     */   private int masterObjId;
/*  52 */   private String masterName = "";
/*  53 */   private float speed = 0.3F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int npcTypeId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_NPC_INFO(Npc npc, Player player) {
/*  67 */     this.npc = (Creature)npc;
/*  68 */     this.npcTemplate = npc.getObjectTemplate();
/*  69 */     this.npcTypeId = player.isAggroIconTo(npc.getTribe()) ? NpcType.AGGRESSIVE.getId() : this.npcTemplate.getNpcType().getId();
/*     */     
/*  71 */     this.npcId = npc.getNpcId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_NPC_INFO(Player player, Kisk kisk) {
/*  83 */     this.npc = (Creature)kisk;
/*  84 */     this.npcTypeId = kisk.isAggroFrom(player) ? NpcType.ATTACKABLE.getId() : NpcType.NON_ATTACKABLE.getId();
/*     */     
/*  86 */     this.npcTemplate = kisk.getObjectTemplate();
/*  87 */     this.npcId = kisk.getNpcId();
/*     */     
/*  89 */     this.masterObjId = kisk.getOwnerObjectId();
/*  90 */     this.masterName = kisk.getOwnerName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_NPC_INFO(Summon summon) {
/*  99 */     this.npc = (Creature)summon;
/* 100 */     this.npcTemplate = summon.getObjectTemplate();
/* 101 */     this.npcTypeId = this.npcTemplate.getNpcType().getId();
/* 102 */     this.npcId = summon.getNpcId();
/* 103 */     Player owner = summon.getMaster();
/* 104 */     if (owner != null) {
/*     */       
/* 106 */       this.masterObjId = owner.getObjectId();
/* 107 */       this.masterName = owner.getName();
/* 108 */       this.speed = owner.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F;
/*     */     }
/*     */     else {
/*     */       
/* 112 */       this.masterName = "LOST";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 122 */     writeF(buf, this.npc.getX());
/* 123 */     writeF(buf, this.npc.getY());
/* 124 */     writeF(buf, this.npc.getZ());
/* 125 */     writeD(buf, this.npc.getObjectId());
/* 126 */     writeD(buf, this.npcId);
/* 127 */     writeD(buf, this.npcId);
/*     */     
/* 129 */     writeC(buf, this.npcTypeId);
/*     */     
/* 131 */     writeH(buf, this.npc.getState());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     writeC(buf, this.npc.getHeading());
/* 140 */     writeD(buf, this.npcTemplate.getNameId());
/* 141 */     writeD(buf, this.npcTemplate.getTitleId());
/*     */     
/* 143 */     writeH(buf, 0);
/* 144 */     writeC(buf, 0);
/* 145 */     writeD(buf, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 150 */     writeD(buf, this.masterObjId);
/* 151 */     writeS(buf, this.masterName);
/*     */     
/* 153 */     int maxHp = this.npc.getLifeStats().getMaxHp();
/* 154 */     int currHp = this.npc.getLifeStats().getCurrentHp();
/* 155 */     writeC(buf, 100 * currHp / maxHp);
/* 156 */     writeD(buf, this.npc.getGameStats().getCurrentStat(StatEnum.MAXHP));
/* 157 */     writeC(buf, this.npc.getLevel());
/*     */     
/* 159 */     NpcEquippedGear gear = this.npcTemplate.getEquipment();
/* 160 */     if (gear == null) {
/* 161 */       writeH(buf, 0);
/*     */     } else {
/*     */       
/* 164 */       writeH(buf, gear.getItemsMask());
/* 165 */       for (Map.Entry<ItemSlot, ItemTemplate> item : (Iterable<Map.Entry<ItemSlot, ItemTemplate>>)gear.getItems()) {
/*     */         
/* 167 */         writeD(buf, ((ItemTemplate)item.getValue()).getTemplateId());
/* 168 */         writeD(buf, 0);
/* 169 */         writeD(buf, 0);
/* 170 */         writeH(buf, 0);
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     writeF(buf, 1.5F);
/* 175 */     writeF(buf, this.npcTemplate.getHeight());
/* 176 */     writeF(buf, this.npc.getMoveController().getSpeed());
/*     */     
/* 178 */     writeH(buf, 2000);
/* 179 */     writeH(buf, 2000);
/*     */     
/* 181 */     writeC(buf, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     writeF(buf, this.npc.getX());
/* 187 */     writeF(buf, this.npc.getY());
/* 188 */     writeF(buf, this.npc.getZ());
/* 189 */     writeC(buf, 0);
/* 190 */     SpawnTemplate spawn = this.npc.getSpawn();
/* 191 */     if (spawn == null) {
/* 192 */       writeH(buf, 0);
/*     */     } else {
/* 194 */       writeH(buf, spawn.getStaticid());
/* 195 */     }  writeC(buf, 0);
/* 196 */     writeC(buf, 0);
/* 197 */     writeC(buf, 0);
/* 198 */     writeC(buf, 0);
/* 199 */     writeC(buf, 0);
/* 200 */     writeC(buf, 0);
/* 201 */     writeC(buf, 0);
/* 202 */     writeC(buf, 0);
/* 203 */     writeC(buf, this.npc.getVisualState());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     writeH(buf, this.npc.getNpcObjectType().getId());
/* 212 */     writeC(buf, 0);
/* 213 */     writeD(buf, (this.npc.getTarget() == null) ? 0 : this.npc.getTarget().getObjectId());
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_NPC_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */