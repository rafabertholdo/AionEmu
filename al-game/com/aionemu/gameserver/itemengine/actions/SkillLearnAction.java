/*     */ package com.aionemu.gameserver.itemengine.actions;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillClass;
/*     */ import com.aionemu.gameserver.skillengine.model.learn.SkillRace;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "SkillLearnAction")
/*     */ public class SkillLearnAction
/*     */   extends AbstractItemAction
/*     */ {
/*     */   @XmlAttribute
/*     */   protected int skillid;
/*     */   @XmlAttribute
/*     */   protected int level;
/*     */   @XmlAttribute(name = "class")
/*     */   protected SkillClass playerClass;
/*     */   @XmlAttribute
/*     */   protected SkillRace race;
/*     */   
/*     */   public boolean canAct(Player player, Item parentItem, Item targetItem) {
/*  54 */     return validateConditions(player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void act(Player player, Item parentItem, Item targetItem) {
/*  60 */     if (!ItemService.removeItemFromInventory(player, parentItem)) {
/*     */       return;
/*     */     }
/*  63 */     ItemTemplate itemTemplate = parentItem.getItemTemplate();
/*     */     
/*  65 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), itemTemplate.getTemplateId()), true);
/*     */ 
/*     */     
/*  68 */     player.getSkillList().addSkill(player, this.skillid, 1, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validateConditions(Player player) {
/*  74 */     if (player.getCommonData().getLevel() < this.level) {
/*  75 */       return false;
/*     */     }
/*  77 */     PlayerClass pc = player.getCommonData().getPlayerClass();
/*     */     
/*  79 */     if (!validateClass(pc)) {
/*  80 */       return false;
/*     */     }
/*     */     
/*  83 */     if (player.getCommonData().getRace().ordinal() != this.race.ordinal() && this.race != SkillRace.ALL)
/*     */     {
/*  85 */       return false;
/*     */     }
/*  87 */     if (player.getSkillList().isSkillPresent(this.skillid)) {
/*  88 */       return false;
/*     */     }
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validateClass(PlayerClass pc) {
/*  95 */     boolean result = false;
/*     */     
/*  97 */     if (!pc.isStartingClass() && PlayerClass.getStartingClassFor(pc).ordinal() == this.playerClass.ordinal()) {
/*  98 */       result = true;
/*     */     }
/* 100 */     if (pc.ordinal() == this.playerClass.ordinal() || this.playerClass == SkillClass.ALL)
/*     */     {
/* 102 */       result = true;
/*     */     }
/* 104 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\SkillLearnAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */