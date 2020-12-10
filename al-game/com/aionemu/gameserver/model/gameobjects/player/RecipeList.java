/*    */ package com.aionemu.gameserver.model.gameobjects.player;
/*    */ 
/*    */ import com.aionemu.commons.database.dao.DAOManager;
/*    */ import com.aionemu.gameserver.dao.PlayerRecipesDAO;
/*    */ import com.aionemu.gameserver.dataholders.DataManager;
/*    */ import com.aionemu.gameserver.model.DescriptionId;
/*    */ import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEARN_RECIPE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_RECIPE_DELETE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeList
/*    */ {
/* 38 */   private Set<Integer> recipeList = new HashSet<Integer>();
/*    */ 
/*    */   
/*    */   public RecipeList(HashSet<Integer> recipeList) {
/* 42 */     this.recipeList = recipeList;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Integer> getRecipeList() {
/* 47 */     return this.recipeList;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addRecipe(Player player, RecipeTemplate recipeTemplate) {
/* 52 */     int recipeId = recipeTemplate.getId().intValue();
/* 53 */     if (!this.recipeList.contains(Integer.valueOf(recipeId))) {
/*    */       
/* 55 */       this.recipeList.add(Integer.valueOf(recipeId));
/* 56 */       ((PlayerRecipesDAO)DAOManager.getDAO(PlayerRecipesDAO.class)).addRecipe(player.getObjectId(), recipeId);
/* 57 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_LEARN_RECIPE(recipeId));
/* 58 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.CRAFT_RECIPE_LEARN(new DescriptionId(recipeTemplate.getNameid())));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void deleteRecipe(Player player, int recipeId) {
/* 64 */     if (this.recipeList.contains(Integer.valueOf(recipeId))) {
/*    */       
/* 66 */       this.recipeList.remove(Integer.valueOf(recipeId));
/* 67 */       ((PlayerRecipesDAO)DAOManager.getDAO(PlayerRecipesDAO.class)).deleteRecipe(player.getObjectId(), recipeId);
/* 68 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_RECIPE_DELETE(recipeId));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void autoLearnRecipe(Player player, int skillId, int skillLvl) {
/* 74 */     for (RecipeTemplate recipe : DataManager.RECIPE_DATA.getRecipeIdFor(player.getCommonData().getRace(), skillId, skillLvl))
/*    */     {
/* 76 */       player.getRecipeList().addRecipe(player, recipe);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRecipePresent(int recipeId) {
/* 82 */     return this.recipeList.contains(Integer.valueOf(recipeId));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\RecipeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */