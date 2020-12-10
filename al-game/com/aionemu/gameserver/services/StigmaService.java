/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
/*     */ import com.aionemu.gameserver.model.templates.item.RequireSkill;
/*     */ import com.aionemu.gameserver.model.templates.item.Stigma;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class StigmaService
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(StigmaService.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean notifyEquipAction(Player player, Item resultItem) {
/*  47 */     if (resultItem.getItemTemplate().isStigma()) {
/*     */       
/*  49 */       int currentStigmaCount = player.getEquipment().getEquippedItemsStigma().size();
/*     */       
/*  51 */       int lvl = player.getLevel();
/*     */       
/*  53 */       if (lvl / 10 + player.getCommonData().getAdvencedStigmaSlotSize() <= currentStigmaCount) {
/*     */         
/*  55 */         log.info("[AUDIT]Possible client hack stigma count big :O player: " + player.getName());
/*  56 */         return false;
/*     */       } 
/*     */       
/*  59 */       if (!resultItem.getItemTemplate().isClassSpecific(player.getCommonData().getPlayerClass())) {
/*     */         
/*  61 */         log.info("[AUDIT]Possible client hack not valid for class. player: " + player.getName());
/*  62 */         return false;
/*     */       } 
/*     */       
/*  65 */       Stigma stigmaInfo = resultItem.getItemTemplate().getStigma();
/*     */       
/*  67 */       if (stigmaInfo == null) {
/*     */         
/*  69 */         log.warn("Stigma info missing for item: " + resultItem.getItemTemplate().getTemplateId());
/*  70 */         return false;
/*     */       } 
/*     */       
/*  73 */       int skillId = stigmaInfo.getSkillid();
/*  74 */       int shardCount = stigmaInfo.getShard();
/*  75 */       if (player.getInventory().getItemCountByItemId(141000001) < shardCount) {
/*     */         
/*  77 */         log.info("[AUDIT]Possible client hack stigma shard count low player: " + player.getName());
/*  78 */         return false;
/*     */       } 
/*  80 */       int needSkill = stigmaInfo.getRequireSkill().size();
/*  81 */       for (RequireSkill rs : stigmaInfo.getRequireSkill()) {
/*     */         
/*  83 */         Iterator<Integer> i$ = rs.getSkillId().iterator(); if (i$.hasNext()) { int id = ((Integer)i$.next()).intValue();
/*     */           
/*  85 */           if (player.getSkillList().isSkillPresent(id)) {
/*  86 */             needSkill--;
/*     */           } }
/*     */       
/*     */       } 
/*  90 */       if (needSkill != 0)
/*     */       {
/*  92 */         log.info("[AUDIT]Possible client hack advenced stigma skill player: " + player.getName());
/*     */       }
/*     */       
/*  95 */       ItemService.decreaseItemCountByItemId(player, 141000001, shardCount);
/*  96 */       SkillListEntry skill = new SkillListEntry(skillId, true, stigmaInfo.getSkilllvl(), PersistentState.NOACTION);
/*  97 */       player.getSkillList().addSkill(skill);
/*  98 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SKILL_LIST(skill, 1300401));
/*     */     } 
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean notifyUnequipAction(Player player, Item resultItem) {
/* 108 */     if (resultItem.getItemTemplate().isStigma()) {
/*     */ 
/*     */       
/* 111 */       Stigma stigmaInfo = resultItem.getItemTemplate().getStigma();
/* 112 */       int skillId = stigmaInfo.getSkillid();
/* 113 */       for (Item item : player.getEquipment().getEquippedItemsStigma()) {
/*     */         
/* 115 */         Stigma si = item.getItemTemplate().getStigma();
/* 116 */         if (resultItem == item || si == null)
/*     */           continue; 
/* 118 */         for (RequireSkill rs : si.getRequireSkill()) {
/*     */           
/* 120 */           if (rs.getSkillId().contains(Integer.valueOf(skillId))) {
/*     */             
/* 122 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300410, new Object[] { new DescriptionId(resultItem.getItemTemplate().getNameId()), new DescriptionId(item.getItemTemplate().getNameId()) }));
/* 123 */             return false;
/*     */           } 
/*     */         } 
/*     */       } 
/* 127 */       player.getSkillList().removeSkill(player, skillId);
/* 128 */       int nameId = DataManager.SKILL_DATA.getSkillTemplate(skillId).getNameId();
/* 129 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300403, new Object[] { new DescriptionId(nameId) }));
/*     */     } 
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPlayerLogin(Player player) {
/* 140 */     List<Item> equippedItems = player.getEquipment().getEquippedItemsStigma();
/* 141 */     for (Item item : equippedItems) {
/*     */       
/* 143 */       if (item.getItemTemplate().isStigma()) {
/*     */         
/* 145 */         Stigma stigmaInfo = item.getItemTemplate().getStigma();
/*     */         
/* 147 */         if (stigmaInfo == null) {
/*     */           
/* 149 */           log.warn("Stigma info missing for item: " + item.getItemTemplate().getTemplateId());
/*     */           return;
/*     */         } 
/* 152 */         int skillId = stigmaInfo.getSkillid();
/* 153 */         SkillListEntry skill = new SkillListEntry(skillId, true, stigmaInfo.getSkilllvl(), PersistentState.NOACTION);
/* 154 */         player.getSkillList().addSkill(skill);
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     for (Item item : equippedItems) {
/*     */       
/* 160 */       if (item.getItemTemplate().isStigma()) {
/*     */         
/* 162 */         int currentStigmaCount = player.getEquipment().getEquippedItemsStigma().size();
/*     */         
/* 164 */         int lvl = player.getLevel();
/*     */         
/* 166 */         if (lvl / 10 + player.getCommonData().getAdvencedStigmaSlotSize() < currentStigmaCount) {
/*     */           
/* 168 */           log.info("[AUDIT]Possible client hack stigma count big :O player: " + player.getName());
/* 169 */           player.getEquipment().unEquipItem(item.getObjectId(), 0);
/*     */           
/*     */           continue;
/*     */         } 
/* 173 */         Stigma stigmaInfo = item.getItemTemplate().getStigma();
/*     */         
/* 175 */         if (stigmaInfo == null) {
/*     */           
/* 177 */           log.warn("Stigma info missing for item: " + item.getItemTemplate().getTemplateId());
/* 178 */           player.getEquipment().unEquipItem(item.getObjectId(), 0);
/*     */           
/*     */           continue;
/*     */         } 
/* 182 */         int needSkill = stigmaInfo.getRequireSkill().size();
/* 183 */         for (RequireSkill rs : stigmaInfo.getRequireSkill()) {
/*     */           
/* 185 */           for (Iterator<Integer> i$ = rs.getSkillId().iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
/*     */             
/* 187 */             if (player.getSkillList().isSkillPresent(id))
/*     */             {
/* 189 */               needSkill--;
/*     */             } }
/*     */         
/*     */         } 
/*     */         
/* 194 */         if (needSkill != 0) {
/*     */           
/* 196 */           log.info("[AUDIT]Possible client hack advenced stigma skill player: " + player.getName());
/* 197 */           player.getEquipment().unEquipItem(item.getObjectId(), 0);
/*     */           continue;
/*     */         } 
/* 200 */         if (!item.getItemTemplate().isClassSpecific(player.getCommonData().getPlayerClass())) {
/*     */           
/* 202 */           log.info("[AUDIT]Possible client hack not valid for class. player: " + player.getName());
/* 203 */           player.getEquipment().unEquipItem(item.getObjectId(), 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\StigmaService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */