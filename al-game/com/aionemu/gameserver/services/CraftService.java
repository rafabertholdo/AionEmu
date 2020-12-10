/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.StaticObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.recipe.Component;
/*     */ import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.skillengine.task.CraftingTask;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ public class CraftService
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(CraftService.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void finishCrafting(Player player, RecipeTemplate recipetemplate, boolean critical) {
/*  50 */     int productItemId = 0;
/*     */     
/*  52 */     if (critical && recipetemplate.getComboProduct() != null) {
/*  53 */       productItemId = recipetemplate.getComboProduct().intValue();
/*     */     } else {
/*  55 */       productItemId = recipetemplate.getProductid().intValue();
/*     */     } 
/*  57 */     if (productItemId != 0) {
/*     */       
/*  59 */       int xpReward = (int)((0.008D * (recipetemplate.getSkillpoint().intValue() + 100) * (recipetemplate.getSkillpoint().intValue() + 100) + 60.0D) * player.getRates().getCraftingXPRate());
/*  60 */       ItemService.addItem(player, productItemId, recipetemplate.getQuantity().intValue());
/*     */       
/*  62 */       if (player.getSkillList().addSkillXp(player, recipetemplate.getSkillid().intValue(), xpReward)) {
/*  63 */         player.getCommonData().addExp(xpReward);
/*     */       } else {
/*  65 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_DONT_GET_PRODUCTION_EXP(new DescriptionId(recipetemplate.getSkillid().intValue())));
/*     */       } 
/*  67 */     }  player.setCraftingTask(null);
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
/*     */   public static void startCrafting(Player player, int targetTemplateId, int recipeId, int targetObjId) {
/*  79 */     if (player.getCraftingTask() != null && player.getCraftingTask().isInProgress()) {
/*     */       return;
/*     */     }
/*  82 */     RecipeTemplate recipeTemplate = DataManager.RECIPE_DATA.getRecipeTemplateById(recipeId);
/*     */     
/*  84 */     if (recipeTemplate != null) {
/*     */ 
/*     */       
/*  87 */       int skillId = recipeTemplate.getSkillid().intValue();
/*  88 */       AionObject target = World.getInstance().findAionObject(targetObjId);
/*     */ 
/*     */       
/*  91 */       if (skillId != 40009 && (target == null || !(target instanceof StaticObject))) {
/*     */         
/*  93 */         log.info("[AUDIT] Player " + player.getName() + " tried to craft incorrect target.");
/*     */         
/*     */         return;
/*     */       } 
/*  97 */       if (recipeTemplate.getDp() != null && player.getCommonData().getDp() < recipeTemplate.getDp().intValue()) {
/*     */         
/*  99 */         log.info("[AUDIT] Player " + player.getName() + " modded her/his client.");
/*     */         return;
/*     */       } 
/* 102 */       if (player.getInventory().isFull()) {
/*     */         
/* 104 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.COMBINE_INVENTORY_IS_FULL);
/*     */         
/*     */         return;
/*     */       } 
/* 108 */       for (Component component : recipeTemplate.getComponent()) {
/*     */         
/* 110 */         if (player.getInventory().getItemCountByItemId(component.getItemid().intValue()) < component.getQuantity().intValue()) {
/*     */           
/* 112 */           log.info("[AUDIT] Player " + player.getName() + " modded her/his client.");
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */       
/* 119 */       ItemTemplate critItemTemplate = null;
/* 120 */       ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(recipeTemplate.getProductid().intValue());
/* 121 */       if (itemTemplate == null) {
/*     */         return;
/*     */       }
/* 124 */       if (recipeTemplate.getComboProduct() != null) {
/* 125 */         critItemTemplate = DataManager.ITEM_DATA.getItemTemplate(recipeTemplate.getComboProduct().intValue());
/*     */       }
/* 127 */       if (critItemTemplate == null) {
/* 128 */         critItemTemplate = itemTemplate;
/*     */       }
/* 130 */       if (recipeTemplate.getDp() != null) {
/* 131 */         player.getCommonData().addDp(-recipeTemplate.getDp().intValue());
/*     */       }
/* 133 */       for (Component component : recipeTemplate.getComponent())
/*     */       {
/* 135 */         ItemService.decreaseItemCountByItemId(player, component.getItemid().intValue(), component.getQuantity().intValue());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 140 */       int skillLvlDiff = player.getSkillList().getSkillLevel(skillId) - recipeTemplate.getSkillpoint().intValue();
/* 141 */       player.setCraftingTask(new CraftingTask(player, (StaticObject)target, recipeTemplate, itemTemplate, critItemTemplate, skillLvlDiff));
/* 142 */       player.getCraftingTask().start();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\CraftService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */